package com.daily.english.app.service;

import com.daily.english.app.domain.*;
import com.daily.english.app.dto.PageSearchDto;
import com.daily.english.app.mapper.*;
import com.daily.english.app.util.DateUtil;
import com.daily.english.app.util.RandomStringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class PartnerSchoolService {

    @Autowired
    private PartnerSchoolMapper partnerSchoolMapper;
    @Autowired
    private PartnerClassMapper partnerClassMapper;
    @Autowired
    private PartnerCodeMapper partnerCodeMapper;
    @Autowired
    private BaseCodeMapper baseCodeMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserMapMapper userMapMapper;


    public List<PartnerSchool> selectPartnerSchoolList(PartnerSchool partnerSchool) {
        return partnerSchoolMapper.selectList(partnerSchool);
    }

    public PartnerSchool selectPartnerSchoolById(String id) {
        PartnerSchool partnerSchool = partnerSchoolMapper.selectById(id);
        if (null != partnerSchool) {
            partnerSchool.setTeacherCodeList(partnerCodeMapper.selectTeacherBySchoolId(partnerSchool.getId().toString(), null));
        }
        return partnerSchool;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void savePartnerSchool(PartnerSchool partnerSchool) {
        if (null != partnerSchool.getId() && partnerSchool.getId() > 0) {
            List<PartnerCode> partnerCodes = partnerCodeMapper.selectTeacherBySchoolId(partnerSchool.getId().toString(), null);
            if (StringUtils.isNotBlank(partnerSchool.getEndTime()) && !DateUtil.timeIsBefore(partnerSchool.getEndTime())){
                for (PartnerCode partnerCode : partnerCodes) {
                    /** 更新用户开通地图级别 begin */
                    if (StringUtils.isNotBlank(partnerCode.getUserId())) {
                        User user = userMapper.findUserById(partnerCode.getUserId());
                        user.setOrderMap("S1,S2,S3,S4,S5,S6");
                        userMapper.updateUser(user);
                        UserMap userMap = UserMap.builder().id(Long.parseLong(partnerCode.getUserId())).unblock("S1,S2,S3,S4,S5,S6").build();
                        userMapMapper.updateUserMap(userMap);
                    }
                    /** 更新用户开通地图级别 end */
                    partnerCodeMapper.updateSelective(partnerCode);
                }
            }
            partnerSchool.setUpdateTime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
            partnerSchool.setNumCount(partnerCodes.size());
            partnerSchoolMapper.updateSelective(partnerSchool);
        } else {
            partnerSchool.setCreateTime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
            partnerSchoolMapper.insertSelective(partnerSchool);
            if (null != partnerSchool.getNumCount() && partnerSchool.getNumCount() > 0) {
                List<BaseCode> baseCodes = baseCodeMapper.selectUseCodeList(partnerSchool.getNumCount());
                for (Integer integer = 0; integer < partnerSchool.getNumCount(); integer++) {
                    BaseCode baseCode = baseCodes.get(integer);
                    baseCode.setStatus(1);//设置code码已使用状态
                    baseCodeMapper.updateSelective(baseCode);
                    PartnerCode code = new PartnerCode();
                    code.setIdentifyCode(baseCode.getCode());
                    code.setSchoolId(partnerSchool.getId());
                    code.setType(1);
                    partnerCodeMapper.insertSelective(code);
                }
            }
        }
    }

    public PageInfo<PartnerSchool> selectPartnerSchoolPage(PageSearchDto pageSearchDto) {
        PageHelper.startPage(pageSearchDto.getPageNo(), pageSearchDto.getPageSize());
        List<PartnerSchool> list = partnerSchoolMapper.selectPageList(pageSearchDto);
        PageInfo<PartnerSchool> pageInfo = new PageInfo(list, 5);
        return pageInfo;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deletePartnerSchoolById(String schoolId) {
        PartnerSchool partnerSchool = partnerSchoolMapper.selectById(schoolId);
        if (partnerSchool != null) {
            /** 删除所有班级 begin*/
            List<PartnerClass> partnerClasses = partnerClassMapper.selectList(PartnerClass.builder().schoolId(Integer.parseInt(schoolId)).build());
            if (partnerClasses.size()>0){
                for (PartnerClass partnerClass : partnerClasses) {
                    List<PartnerCode> classCodes = partnerCodeMapper.selectDeleteList(partnerClass.getId().toString(), null);
                    for (PartnerCode partnerCode : classCodes) {
                        /** 更新用户开通地图级别 begin */
                        if (StringUtils.isNotBlank(partnerCode.getUserId())) {
                            User user = userMapper.findUserById(partnerCode.getUserId());
                            user.setOrderMap(null);
                            userMapper.updateUser(user);
                            UserMap userMap = UserMap.builder().id(Long.parseLong(partnerCode.getUserId())).unblock(null).build();
                            userMapMapper.updateUserMapUnblock(userMap);
                        }
                        /** 更新用户开通地图级别 end */
                        partnerCodeMapper.deleteById(partnerCode.getId().toString());//彻底删除code
                    }
                    partnerClassMapper.deleteById(partnerClass.getId().toString());
                }
            }
            /** 删除所有班级 end*/

            /** 删除学校 begin*/
            List<PartnerCode> schoolCodes = partnerCodeMapper.selectDeleteList(null, schoolId);
            for (PartnerCode partnerCode : schoolCodes) {
                /** 更新用户开通地图级别 begin */
                if (StringUtils.isNotBlank(partnerCode.getUserId())) {
                    User user = userMapper.findUserById(partnerCode.getUserId());
                    if (null != user){
                        user.setOrderMap(null);
                        userMapper.updateUser(user);
                        UserMap userMap = UserMap.builder().id(Long.parseLong(partnerCode.getUserId())).unblock(null).build();
                        userMapMapper.updateUserMapUnblock(userMap);
                    }
                }
                /** 更新用户开通地图级别 end */
                partnerCodeMapper.deleteById(partnerCode.getId().toString());//彻底删除code
            }
            partnerSchoolMapper.deleteById(schoolId);
            /** 删除学校 end*/
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deletePartnerCodeById(String id) {
        PartnerCode partnerCode = partnerCodeMapper.selectById(id);
        PartnerSchool partnerSchool = partnerSchoolMapper.selectById(partnerCode.getSchoolId().toString());
        partnerSchool.setNumCount(partnerSchool.getNumCount() - 1);
        if (StringUtils.isNotBlank(partnerCode.getUserId())) {
            User user = userMapper.findUserById(partnerCode.getUserId());
            user.setOrderMap(null);//设置开通为空
            userMapper.updateUser(user);
            UserMap userMap = UserMap.builder().id(Long.parseLong(partnerCode.getUserId())).unblock(null).build();
            userMapMapper.updateUserMapUnblock(userMap);
        }
        partnerSchoolMapper.updateSelective(partnerSchool);
        partnerCodeMapper.deleteById(id);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addPartnerCode(Integer schoolId) {
        BaseCode baseCode = baseCodeMapper.selectUseCodeList(1).get(0);
        baseCode.setStatus(1);//设置code码已使用状态
        baseCodeMapper.updateSelective(baseCode);

        PartnerCode partnerCode = new PartnerCode();
        partnerCode.setSchoolId(schoolId);
        partnerCode.setType(1);//老师
        partnerCode.setIdentifyCode(baseCode.getCode());

        PartnerSchool partnerSchool = partnerSchoolMapper.selectById(schoolId.toString());
        partnerSchool.setNumCount(partnerSchool.getNumCount() + 1);
        partnerSchoolMapper.updateSelective(partnerSchool);
        partnerCodeMapper.insertSelective(partnerCode);
    }
}
