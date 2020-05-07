package com.daily.english.app.service;

import com.daily.english.app.domain.*;
import com.daily.english.app.dto.YjScoreRecordDto;
import com.daily.english.app.dto.YjUserBmInfoDto;
import com.daily.english.app.dto.YjUserBmInfoTeacherWebDto;
import com.daily.english.app.dto.YjUserBmInfoWebDto;
import com.daily.english.app.json.AttachmentJson;
import com.daily.english.app.mapper.*;
import com.daily.english.app.util.RandomStringUtil;
import com.daily.english.app.vo.YjUserBmInfoJuryVo;
import com.daily.english.app.vo.YjUserBmInfoVo;
import com.daily.english.app.vo.YjUserVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.hash.Hashing;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class YjUserBmInfoService {

    @Autowired
    private YjUserBmInfoMapper userBmInfoMapper;

    @Autowired
    private YjScoreRecordMapper scoreRecordMapper;
    @Autowired
    private CmsUserMapper cmsUserMapper;

    @Autowired
    private YjSchoolMapper schoolMapper;
    @Autowired
    private MailService mailService;
    @Autowired
    private MapperFacade mapper;
    @Autowired
    private UserService userService;

    public List<YjSchool> selectAllSchool() {
        List<YjSchool> schools = schoolMapper.selectList(null);
        return schools;
    }

    public PageInfo<YjUserBmInfoVo> selectUserBmInfoPage(YjUserBmInfoDto yjUserBmInfoDto) {
        PageHelper.startPage(yjUserBmInfoDto.getPageNo(), yjUserBmInfoDto.getPageSize());
        List<YjUserBmInfoVo> list = userBmInfoMapper.selectUserBmInfoPage(yjUserBmInfoDto);
        PageInfo<YjUserBmInfoVo> pageInfo = new PageInfo(list, 5);
        return pageInfo;
    }

    /**
     * 按照查询条件搜索
     *
     * @param yjUserBmInfoDto
     * @return
     */
    public List<YjUserBmInfoVo> queryAllBmInfo(YjUserBmInfoDto yjUserBmInfoDto) {
        List<YjUserBmInfoVo> list = userBmInfoMapper.selectUserBmInfoAll(yjUserBmInfoDto);
        return list;
    }

    public PageInfo<YjUserBmInfoJuryVo> selectUserBmInfoJuryListPage(YjScoreRecordDto scoreRecordDto) {
        //查询评委用户可评分组别的条件 begin
        CmsUser cmsUser = cmsUserMapper.selectById(scoreRecordDto.getUserId());
        if (null != cmsUser.getGroupTypes()) {
            String[] groups = cmsUser.getGroupTypes().split(",");
            List<Integer> groupTypes = new ArrayList<>();
            for (int i = 0; i < groups.length; i++) {
                groupTypes.add(Integer.parseInt(groups[i]));
            }
            scoreRecordDto.setGroupTypes(groupTypes);
        }
        //查询评委用户可评分组别的条件 end
        PageHelper.startPage(scoreRecordDto.getPageNo(), scoreRecordDto.getPageSize());
        List<YjUserBmInfoJuryVo> list = userBmInfoMapper.selectUserBmInfoJuryListPage(scoreRecordDto);
        PageInfo<YjUserBmInfoJuryVo> pageInfo = new PageInfo(list, 5);
        return pageInfo;
    }

    /**
     * @param id 报名ID
     * @class_name 评委点开报名信息详情
     * @describe: 评委点开报名信息详情, 获取详情信息时给报名信息加锁
     * @creat_user: liuzd@chinadailyhk.com
     * @creat_date: 2019-08-20
     * @creat_time: 14:36
     **/
    public YjUserBmInfoJuryVo selectUserBmInfoJuryDetail(String id) {
        YjUserBmInfoJuryVo yjUserBmInfoJuryVo = userBmInfoMapper.selectUserBmInfoJuryDetailById(id);
        return yjUserBmInfoJuryVo;
    }

    /**
     * 解锁报名信息
     *
     * @param id
     */
    public void unlockBmInfoById(String id) {
        YjUserBmInfo userBmInfo = new YjUserBmInfo();
        userBmInfo.setId(Integer.parseInt(id));
        userBmInfo.setLockStatus(0);
        userBmInfoMapper.updateUserBmInfo(userBmInfo);
    }


    public void lockUserBmInfo(String id) {
        YjUserBmInfo userBmInfo = new YjUserBmInfo();
        userBmInfo.setId(Integer.parseInt(id));
        userBmInfo.setLockStatus(1);
        userBmInfoMapper.updateUserBmInfo(userBmInfo);
    }

    public List<YjUserBmInfo> selectUserBmInfoList(YjUserBmInfo userBmInfo) {
        return userBmInfoMapper.selectUserBmInfoList(userBmInfo);
    }


    public YjUserBmInfo selectUserBmInfoById(String id) {
        YjUserBmInfo yjUserBmInfo = userBmInfoMapper.selectUserBmInfoById(id);
        if (yjUserBmInfo == null) {
            return null;
        }
        YjScoreRecordDto yjScoreRecord = new YjScoreRecordDto();
        yjScoreRecord.setBmId(id);
        List<YjScoreRecord> scoreRecords = scoreRecordMapper.selectList(yjScoreRecord);
        yjUserBmInfo.setScoreRecordList(scoreRecords);
        return yjUserBmInfo;
    }

    /**
     * 根据登录类型查找报名信息
     *
     * @param userVo  登录信息
     * @param eventId 活动ID
     * @return
     */
    public List<YjUserBmInfo> selectUserBmInfoLoginType(YjUserVo userVo, String eventId) {
        YjUserBmInfo yjUserBmInfo = new YjUserBmInfo();
        yjUserBmInfo.setEventId(eventId);
        if ("1".equals(userVo.getLoginType())) {//老师报名的信息登录查询
            yjUserBmInfo.setId(Integer.parseInt(userVo.getId()));
        } else {//个人、老师报名数据
            yjUserBmInfo.setUserId(userVo.getId());
        }
        List<YjUserBmInfo> yjUserBmInfos = userBmInfoMapper.selectUserBmInfoList(yjUserBmInfo);
        return yjUserBmInfos;
    }

    /**
     * 查找报名信息
     *
     * @param userId  用户信息
     * @param eventId 活动ID
     * @return
     */
    public List<YjUserBmInfo> checkUserBmInfo(String userId, String eventId) {
        YjUserBmInfo yjUserBmInfo = new YjUserBmInfo();
        yjUserBmInfo.setEventId(eventId);
        yjUserBmInfo.setUserId(userId);
        List<YjUserBmInfo> yjUserBmInfos = userBmInfoMapper.selectUserBmInfoListNew(yjUserBmInfo);
        return yjUserBmInfos;
    }

    public YjUserBmInfo selectWebBmLogin(String email, String registerCode) {
        return userBmInfoMapper.selectUserBmInfoByEmailAndRegisterCode(email, registerCode);
    }

    public void saveUserBmInfo(YjUserBmInfo userBmInfo) {
        if (null != userBmInfo.getId() && userBmInfo.getId() > 0) {
            setUploadStatus(userBmInfo);
            userBmInfoMapper.updateUserBmInfo(userBmInfo);
        } else {
            setGradeGroupType(userBmInfo);
            setUploadStatus(userBmInfo);
            userBmInfo.setCreateTime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
            userBmInfoMapper.insertUserBmInfo(userBmInfo);
        }
    }


    @Transactional
    public void saveWebUserBmInfo(YjUserBmInfoWebDto userBmInfoDto) {
        YjUserBmInfo userBmInfo = mapper.map(userBmInfoDto, YjUserBmInfo.class);
        userBmInfo.setRegisterCode(RandomStringUtil.getRandomString(8));

        setGradeGroupType(userBmInfo);
        setUploadStatus(userBmInfo);
        userBmInfo.setCreateTime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
        userBmInfoMapper.insertUserBmInfo(userBmInfo);
        //发送报名成功邮件
        log.info("个人用户报名-发送邮件---------------------begin");
        if (userBmInfoDto.getEventId() == "2019") {
            mailService.sendAttachmentMail(userBmInfoDto.getEmail(), "來自VDO English 21世纪杯报名提醒", setBmEmailContent(null), (AttachmentJson) null);
//        MailUtil.sendBmUser(userBmInfoDto.getEmail(), null);
        }
        log.info("个人用户报名-发送邮件---------------------end");
    }


    /**
     * 老师提交报名
     *
     * @param teacherWebDto 提交数据
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void saveTeacherUserBmInfoNew(YjUserBmInfoTeacherWebDto teacherWebDto) {
        List<YjUserBmInfo> userBmInfoList = new ArrayList<>();
        for (YjUserBmInfoWebDto yjUserBmInfoWebDto : teacherWebDto.getUserBmInfoList()) {
            YjUserBmInfo add = mapper.map(yjUserBmInfoWebDto, YjUserBmInfo.class);
            String password = RandomStringUtil.getRandomString(8);
            /** 转换老师报名数据为系统用户 begin*/
            User user = new User();
            user.setUsername(add.getEmail().trim());
            String hashPassword = Hashing.md5().hashString(password, StandardCharsets.UTF_8).toString();
            user.setPassword(hashPassword);
            user.setNickname("");
            String dateTimeStr = DateTime.now().toString("yyyy-MM-dd HH:mm:ss");
            user.setCreateTime(dateTimeStr);
            userService.insertUser(user);
            /** 转换老师报名数据为系统用户 end*/
            add.setType(1);
            add.setUserId(user.getId());//生成的用户ID
            add.setEventId(teacherWebDto.getEventId());
            add.setTeacherCall(teacherWebDto.getTeacherCall());
            add.setTeacherEmail(teacherWebDto.getTeacherEmail());
            add.setTeacherName(teacherWebDto.getTeacherName());
            add.setTeacherTel(teacherWebDto.getTeacherTel());
            add.setSchoolId(teacherWebDto.getSchoolId());
            add.setSchoolArea(teacherWebDto.getSchoolArea());
            add.setSchoolOtherName(teacherWebDto.getSchoolOtherName());
            add.setTeacherUserId(teacherWebDto.getUserId());
            setGradeGroupType(add);
            setUploadStatus(add);
            add.setRegisterCode(password);
            add.setCreateTime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
            userBmInfoMapper.insertUserBmInfo(add);
            userBmInfoList.add(add);
        }
        log.info("报名信息转换用户保存---------------------begin");
        mailService.sendAttachmentMail(teacherWebDto.getTeacherEmail(), "來自VDO English 21世纪杯报名提醒", setBmEmailContent(userBmInfoList), (AttachmentJson) null);
//        MailUtil.sendBmUser(teacherWebDto.getTeacherEmail(), userBmInfoList);
        log.info("报名信息转换用户保存---------------------end");
    }

    /**
     * 老师提交报名
     *
     * @param teacherWebDto 提交数据
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void saveTeacherUserBmInfo(YjUserBmInfoTeacherWebDto teacherWebDto) {
        List<YjUserBmInfo> userBmInfoList = new ArrayList<>();
        for (YjUserBmInfoWebDto yjUserBmInfoWebDto : teacherWebDto.getUserBmInfoList()) {
            YjUserBmInfo add = mapper.map(yjUserBmInfoWebDto, YjUserBmInfo.class);
            add.setType(1);
            add.setUserId(teacherWebDto.getUserId());
            add.setEventId(teacherWebDto.getEventId());
            add.setTeacherCall(teacherWebDto.getTeacherCall());
            add.setTeacherEmail(teacherWebDto.getTeacherEmail());
            add.setTeacherName(teacherWebDto.getTeacherName());
            add.setTeacherTel(teacherWebDto.getTeacherTel());
            add.setSchoolId(teacherWebDto.getSchoolId());
            add.setSchoolArea(teacherWebDto.getSchoolArea());
            add.setSchoolOtherName(teacherWebDto.getSchoolOtherName());
            setGradeGroupType(add);
            setUploadStatus(add);
            add.setRegisterCode(RandomStringUtil.getRandomString(8));
            add.setCreateTime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
            userBmInfoMapper.insertUserBmInfo(add);
            userBmInfoList.add(add);
        }
        log.info("报名信息转换用户保存---------------------begin");
        if (teacherWebDto.getEventId() == "2019") {
            mailService.sendAttachmentMail(teacherWebDto.getTeacherEmail(), "來自VDO English 21世纪杯报名提醒", setBmEmailContent(userBmInfoList), (AttachmentJson) null);
        }
//        MailUtil.sendBmUser(teacherWebDto.getTeacherEmail(), userBmInfoList);
        log.info("报名信息转换用户保存---------------------end");
    }

    /**
     * @param
     * @class_name
     * @describe: 上传状态设置：根据用户演讲稿、用户上传视频资料设置
     * @creat_user: liuzd@chinadailyhk.com
     * @creat_date: 2019-08-16
     * @creat_time: 09:14
     **/
    private void setUploadStatus(YjUserBmInfo userBmInfo) {
        //上传状态：0.未上传 1.已上传 2.未上传视频 3.未上传演讲稿
        if (StringUtils.isBlank(userBmInfo.getVideoUrl()) && StringUtils.isBlank(userBmInfo.getContent())) {
            userBmInfo.setUploadStatus(0);//未上传
            return;
        }
        if (StringUtils.isNotBlank(userBmInfo.getVideoUrl()) && StringUtils.isNotBlank(userBmInfo.getContent())) {
            userBmInfo.setUploadStatus(1);//已上传
            return;
        }
        if (StringUtils.isBlank(userBmInfo.getVideoUrl()) && StringUtils.isNotBlank(userBmInfo.getContent())) {
            userBmInfo.setUploadStatus(2);//未上传视频
            return;
        }
        if (StringUtils.isBlank(userBmInfo.getContent()) && StringUtils.isNotBlank(userBmInfo.getVideoUrl())) {
            userBmInfo.setUploadStatus(3);//未上传演讲稿
            return;
        }
    }

    /**
     * @param userBmInfo
     * @class_name
     * @describe: 获取年级分组信息
     * @creat_user: liuzd@chinadailyhk.com
     * @creat_date: 2019-08-15
     * @creat_time: 17:47
     **/
    private void setGradeGroupType(YjUserBmInfo userBmInfo) {
        if (1 == userBmInfo.getGrade() || 2 == userBmInfo.getGrade() || 3 == userBmInfo.getGrade()) {
            userBmInfo.setGroupType(1);//初小
            return;
        }
        if (4 == userBmInfo.getGrade() || 5 == userBmInfo.getGrade() || 6 == userBmInfo.getGrade()) {
            userBmInfo.setGroupType(2);//高小
            return;
        }
        if (7 == userBmInfo.getGrade() || 8 == userBmInfo.getGrade() || 9 == userBmInfo.getGrade()) {
            userBmInfo.setGroupType(3);//初中
            return;
        }
        if (10 == userBmInfo.getGrade() || 11 == userBmInfo.getGrade() || 12 == userBmInfo.getGrade()) {
            userBmInfo.setGroupType(4);//高中
            return;
        }
        return;
    }


    private static String setBmEmailContent(List<YjUserBmInfo> userBmInfos) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("<html><body>" +
                "Dear contestant,<br>" +
                "Congratulations! Your online application has been successfully received. Next, " +
                "please pay the registration fee ($100, non-refundable, no cancellations), by posting a crossed cheque payable to" +
                " “The English-Speaking Union (Hong Kong) Ltd” within seven working days, or making a bank transfer.<br>" +
                "Post the cheque to the following address:  <br>" +
                "P. O. Box No. 70602 Kowloon Central Post Office, Kowloon<br>" +
                "Please give the following details at the back of the cheque:<br>" +
                "-Name of the applicant (both Chinese and English)<br>" +
                "-Name of the school (both Chinese and English)<br>" +
                "-Class, Contact Number and Email<br>" +
                "An official receipt will be sent to each applicant by email.<br>" +
                "<br>" +
                "For bank transfer:<br>" +
                "Bank account: HSBC <br>" +
                "502-003734-001<br>" +
                "The English Speaking Union (HK) Ltd<br>" +
                "Please email your bank receipt to info@vdoenglish.com and include the following details: <br>" +
                "-Name of the applicant (both Chinese and English)<br>" +
                "-Name of the school (both Chinese and English)<br>" +
                "-Class and Contact Number <br>");
        if (userBmInfos != null) {
            buffer.append("Your login information is as follows:<br>");
            for (YjUserBmInfo userBmInfo : userBmInfos) {
                buffer.append("Student Name:" + userBmInfo.getNameEn() + "<br>" +
                        "Login I.D.: " + userBmInfo.getEmail() + "<br>" +
                        "Password: " + userBmInfo.getRegisterCode() + "<br>" +
                        "<br>");
            }
        }
        buffer.append("Please upload you video and script on our website <a href='https://www.vdoenglish.com'>www.vdoenglish.com</a><br>" +
                "After we receive your registration fee, you will be eligible for the preliminary round.  " +
                "Semi-Finalists will be selected by a panel of expert judges based on their evaluation of contestants’ video materials.  " +
                "Qualified Semi-Finalists will receive notification by email and/or a phone call in November. " +
                " For updated information and the latest news on the competition, please refer to our website <a href='https://www.vdoenglish.com'>www.vdoenglish.com</a>.<br>" +
                "We wish you every success!<br>");
        buffer.append("<br>");

        buffer.append("親愛的參賽者：<br>" +
                "您已成功在網上完成報名。請於七個工作天內，" +
                "以郵寄方式支付報名費（港幣一百元正的劃線支票，不可退款，不可取消），" +
                "支票抬頭請填上 \"The English-Speaking Union (Hong Kong) Ltd\"，或銀行轉帳。<br>" +
                "請將支票寄到以下地址： <br>" +
                "九龍中央郵政局信箱號碼 70602<br>" +
                "參加者須在支票背後寫上自己中英文全名、 中英文學校名字、就讀班級、聯絡電話及電郵地址。<br>" +
                "我們並會透過電郵向每位申請人發出正式收據。<br>" +
                "銀行轉帳：<br>" +
                "銀行户口：HSBC <br>" +
                "502-003734-001<br>" +
                "The English Speaking Union(HK) Ltd<br>" +
                "請將轉帳收據電郵至 info@vdoenglish.com ，並在電郵內註明中英文全名、 中英文學校名字、就讀班級、及聯絡電話。<br>");
        if (userBmInfos != null) {
            buffer.append("以下是參賽者的登入資料：<br>");
            for (YjUserBmInfo userBmInfo : userBmInfos) {
                buffer.append("學生名稱：" + userBmInfo.getNameCh() + "<br>" +
                        "帳號：" + userBmInfo.getEmail() + "<br>" +
                        "密碼：" + userBmInfo.getRegisterCode() + "<br>" +
                        "<br>");
            }
        }
        buffer.append("請將比賽視頻及講稿上載到<a href='https://www.vdoenglish.com'>www.vdoenglish.com</a>。<br>" +
                "我們收到您的報名費後，您將獲得參加預賽的資格。專業評判團會根據所有預賽選手所提交的片段進行評" +
                "分並選出初賽選手。合格的初賽選手將於11月以電郵和/或電話收到有關通知。" +
                "有關比賽的最新資訊和最新消息，請瀏覽網站<a href='https://www.vdoenglish.com'>www.vdoenglish.com</a>。<br>" +
                "<br>" +
                "   祝<br>" +
                "一切順利！<br>");
        buffer.append("</body></html>");

        return buffer.toString();
    }

    /**
     * 报名信息转换用户保存
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void bmInfoConvertUser() {
        log.info("报名信息转换用户保存---------------------begin");
        YjUserBmInfo yjUserBmInfo = new YjUserBmInfo();
        yjUserBmInfo.setEventId("2019");
        List<YjUserBmInfo> yjUserBmInfos = userBmInfoMapper.selectUserBmInfoList(yjUserBmInfo);
        for (YjUserBmInfo userBmInfo : yjUserBmInfos) {
            if (userBmInfo.getType().equals(1)){//老师报名数据
                if (StringUtils.isBlank(userBmInfo.getTeacherUserId())){//如果老师ID为空就进行转换
                    log.info("userName：{}",userBmInfo.getEmail());
                    /** 转换老师报名数据为系统用户 begin*/
                    User user = new User();
                    user.setUsername(userBmInfo.getEmail().trim());
                    String hashPassword = Hashing.md5().hashString(userBmInfo.getRegisterCode(), StandardCharsets.UTF_8).toString();
                    user.setPassword(hashPassword);
                    user.setNickname("");
                    String dateTimeStr = DateTime.now().toString("yyyy-MM-dd HH:mm:ss");
                    user.setCreateTime(dateTimeStr);
                    userService.insertUser(user);
                    /** 转换老师报名数据为系统用户 end*/
                    String tuId = userBmInfo.getUserId();//老师ID
                    userBmInfo.setUserId(user.getId());//转成添加的用户ID
                    userBmInfo.setTeacherUserId(tuId);
                    userBmInfoMapper.updateUserBmInfo(userBmInfo);
                }
            }
        }
        log.info("报名信息转换用户保存---------------------end");
    }

}
