package com.daily.english.app.service;

import com.daily.english.app.domain.*;
import com.daily.english.app.dto.PetPropDto;
import com.daily.english.app.mapper.*;
import com.daily.english.app.util.DateUtil;
import com.daily.english.app.vo.PetPropVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

@Slf4j
@Service
public class PetService {

    @Autowired
    private PetMapper petMapper;
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private PetPropMapper petPropMapper;
    @Autowired
    private PetOrderRecordMapper petOrderRecordMapper;
    @Autowired
    private PropMapper propMapper;
    @Autowired
    private PetLogMapper petLogMapper;
    @Autowired
    private WordPkRobotMapper wordPkRobotMapper;
    @Autowired
    private UserValueMapper userValueMapper;
    @Autowired
    private UserMapper userMapper;

    /**
     * 获取用户宠物与竹币信息，如果没有信息进行入库初始返回
     *
     * @param userId 用户ID
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Pet getUserPet(Integer userId) {
        Pet pet = petMapper.selectByUserId(userId);
        User user = userMapper.findUserById("" + userId);
        if (user == null) {
            return null;
        }
        if (pet == null) {
            UserValue userValue = userValueMapper.findUserValueByUserId(userId.toString());
            Pet add = Pet.builder().userId(userId)
                    .growth(0)
                    .createTime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"))
                    .bamboo(0d)
                    .build();
            petMapper.insertSelective(add);
            if (userValue == null) {
                userValue = UserValue.builder().id(userId.longValue()).build();
                userValueMapper.insertUserValue(userValue);
            }
            return add;
        }
        PetLog petLog = petLogMapper.selectRecentFeedByPetId(pet.getId());
        if (petLog != null){
            pet.setLastFeedTime(petLog.getCreateTime());
        }
        return pet;
    }

    /**
     * 检查用户是否满足5个竹子
     * 满足减去5个竹币
     *
     * @param userId 用户ID
     * @return 0.未满足 1.满足
     */
    public Integer checkUserWordCurrency(Integer userId) {
        UserValue userValue = userValueMapper.findUserValueByUserId(userId.toString());
        if (userValue.getBamboo() >= 5) {
            userValue.setBamboo(userValue.getBamboo() - 5);
            userValue.setUseBambooToday(userValue.getUseBambooToday() + 5);
            userValue.setUseBambooAll(userValue.getUseBambooAll() + 5);
            userValueMapper.updateUserValue(userValue);
            return 1;
        }
        return 0;
    }

    /**
     * 购买商品
     *
     * @param orderRecord
     * @return 成功返回1，竹子不够返回2，无此商品返回3
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Integer buyGoods(PetOrderRecord orderRecord) {
        Goods goods = goodsMapper.selectById(orderRecord.getGoodsId().toString());
        if (goods == null) {
            return 3;
        }
        Double totalPrice = 0d;//商品总价
        Pet pet = petMapper.selectById(orderRecord.getPetId());
        UserValue userValue = userValueMapper.findUserValueByUserId(pet.getUserId().toString());
        if (goods.getDiscountPrice() != null) {//折扣价不为null，则用折扣价购买
            totalPrice = goods.getDiscountPrice() * orderRecord.getAmount();
        } else {
            totalPrice = goods.getPrice() * orderRecord.getAmount();
        }
        if (userValue.getBamboo() < totalPrice) {
            return 2;
        }
        /** 添加宠物背包 begin */
        addPetProp(orderRecord, goods.getPropId());
        /** 添加宠物背包 end */

        /** 添加购买记录 begin */
        orderRecord.setTotalValue(totalPrice);
        orderRecord.setCreateTime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
        petOrderRecordMapper.insertSelective(orderRecord);
        /** 添加购买记录 end */

        /** 扣除竹币 begin */
        userValue.setBamboo((long) (userValue.getBamboo() - totalPrice));//扣除竹币
        userValueMapper.updateUserValue(userValue);//更新竹币信息
        /** 扣除竹币 end */

        return 1;
    }

    /**
     * 宠物喂食
     *
     * @param petId     宠物ID
     * @param petPropId 背包道具ID
     * @param amount    数量
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Integer feedPet(Integer petId, Integer petPropId, Integer amount) {
        //宠物背包道具
        PetProp petProp = petPropMapper.selectById(petPropId);
        if (null == petProp) {//无此道具
            return 3;
        }
        if (petProp.getAmount() < amount) {//道具不足
            return 2;
        }
        //道具属性表
        Prop prop = propMapper.selectById(petProp.getPropId().toString());
        //宠物信息
        Pet pet = petMapper.selectById(petId);
        /** 喂食添加宠物属性值 begin */
        int feedExp = Integer.parseInt(prop.getExp()) * amount;
        if (pet != null) {
            int feedValue = pet.getGrowth() + feedExp;
            setPetLevel(pet, feedValue);
            pet.setGrowth(feedValue);
        }
        petMapper.updateSelective(pet);
        /** 喂食添加宠物属性值 end */

        /** 宠物活动记录 begin */
        PetLog petLog = PetLog.builder().petId(petId).actionType(1)
                .createTime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss")).build();
        petLogMapper.insertSelective(petLog);
        /** 宠物活动记录 end */
        /** 检查该道具是否还有存库 begin */
        int outCount = petProp.getAmount() - amount;
        if (outCount <= 0) {
            petPropMapper.deleteById(petPropId);
        } else {//如果还有剩下道具
            petProp.setAmount(outCount);
            petPropMapper.updateSelective(petProp);
        }
        /** 检查该道具是否还有存库 end */
        return 1;
    }

    public PageInfo<PetPropVo> selectPetPropPage(PetPropDto petPropDto) {
        PageHelper.startPage(petPropDto.getPageNo(), petPropDto.getPageSize());
        List<PetPropVo> list = petPropMapper.selectPageList(petPropDto);
        PageInfo<PetPropVo> pageInfo = new PageInfo(list, 5);
        return pageInfo;
    }

    /**
     * 添加宠物背包道具
     *
     * @param orderRecord 订单信息
     * @param propId      道具ID
     */
    private void addPetProp(PetOrderRecord orderRecord, Integer propId) {
        //查询宠物背包对应道具
        PetProp petProp = petPropMapper.selectListByPetIdAndPetPropId(orderRecord.getPetId(), propId);
        if (petProp != null) {//存在做更新
            petProp.setAmount(petProp.getAmount() + orderRecord.getAmount());
            petPropMapper.updateSelective(petProp);
        } else {//不存在做插入
            PetProp add = new PetProp();
            add.setAmount(orderRecord.getAmount());
            add.setPetId(orderRecord.getPetId());
            add.setPropId(propId);
            petPropMapper.insertSelective(add);
        }
    }

    /**
     * 根据成长值设置宠物等级
     *
     * @param pet
     * @param feedValue
     */
    private void setPetLevel(Pet pet, int feedValue) {
        if (feedValue >= 0 && feedValue < 10) {
            pet.setLevel(1);
        } else if (feedValue >= 10 && feedValue < 50) {
            pet.setLevel(2);
        } else if (feedValue >= 50 && feedValue < 100) {
            pet.setLevel(3);
        } else if (feedValue >= 100 && feedValue < 150) {
            pet.setLevel(4);
        } else if (feedValue >= 150 && feedValue < 250) {
            pet.setLevel(5);
        } else if (feedValue >= 250 && feedValue < 350) {
            pet.setLevel(6);
        } else if (feedValue >= 350 && feedValue < 450) {
            pet.setLevel(7);
        } else if (feedValue >= 450 && feedValue < 550) {
            pet.setLevel(8);
        } else if (feedValue >= 550 && feedValue < 650) {
            pet.setLevel(9);
        } else if (feedValue >= 650 && feedValue < 900) {
            pet.setLevel(10);
        } else if (feedValue >= 900 && feedValue < 1150) {
            pet.setLevel(11);
        } else if (feedValue >= 1150 && feedValue < 1400) {
            pet.setLevel(12);
        } else if (feedValue >= 1400 && feedValue < 1650) {
            pet.setLevel(13);
        } else if (feedValue >= 1650 && feedValue < 1900) {
            pet.setLevel(14);
        } else if (feedValue >= 1900 && feedValue < 2400) {
            pet.setLevel(15);
        } else if (feedValue >= 2400 && feedValue < 2900) {
            pet.setLevel(16);
        } else if (feedValue >= 2900 && feedValue < 3400) {
            pet.setLevel(17);
        } else if (feedValue >= 3400 && feedValue < 3900) {
            pet.setLevel(18);
        } else if (feedValue >= 3900 && feedValue < 4400) {
            pet.setLevel(19);
        } else if (feedValue >= 4400) {
            pet.setLevel(20);
        }
    }

    public static void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map<String, Object> robotZipUpload(MultipartFile multipartFile, String uploadFolder, String nickNames) throws IOException {
        String[] nickNameList = nickNames.split(",");
        int count = 0;
        File file = null;
        InputStream ins = multipartFile.getInputStream();
        String zipFileUrl = uploadFolder + "workPkRobot/" + multipartFile.getOriginalFilename();
        file = new File(zipFileUrl);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        inputStreamToFile(ins, file);
        // 开始解压
        ZipFile zipFile = null;
        try {
            zipFile = new ZipFile(file);
            Enumeration<?> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                if (!entry.isDirectory()) {
                    String fileName = entry.getName();
                    String extension = FilenameUtils.getExtension(fileName);
                    fileName = DateUtil.getRandom() + "." + extension;
                    // 如果是文件，就先创建一个文件，然后用io流把内容copy过去
                    File targetFile = new File(uploadFolder + "workPkRobot/" + fileName);
                    // 保证这个文件的父文件夹必须要存在
                    if (!targetFile.getParentFile().exists()) {
                        targetFile.getParentFile().mkdirs();
                    }
                    // 将压缩文件内容写入到这个文件中
                    InputStream is = zipFile.getInputStream(entry);
                    FileUtils.copyInputStreamToFile(is, targetFile);
                    is.close();
                    String fileUrl = "/static/image" + "/workPkRobot/" + fileName;
                    /** 添加机器人信息入库 */
                    WordPkRobot wordPkRobot = WordPkRobot.builder().headUrl(fileUrl).robotName(nickNameList[count]).build();
                    wordPkRobotMapper.insertSelective(wordPkRobot);
                    count++;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("unzip error from ZipUtils", e);
        } finally {
            if (zipFile != null) {
                deleteFile(zipFileUrl);
                try {
                    zipFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 删除单个文件
     *
     * @param fileName 要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                System.out.println("删除单个文件" + fileName + "成功！");
                return true;
            } else {
                System.out.println("删除单个文件" + fileName + "失败！");
                return false;
            }
        } else {
            System.out.println("删除单个文件失败：" + fileName + "不存在！");
            return false;
        }
    }

}
