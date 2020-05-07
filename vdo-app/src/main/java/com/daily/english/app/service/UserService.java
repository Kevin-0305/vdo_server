package com.daily.english.app.service;

import com.daily.english.app.domain.*;
import com.daily.english.app.enums.CodeStateEnum;
import com.daily.english.app.mapper.*;
import com.daily.english.app.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
public class UserService {
    @Autowired
    private PartnerCodeMapper partnerCodeMapper;
    @Autowired
    private PartnerSchoolMapper partnerSchoolMapper;
    @Autowired
    private PartnerClassMapper partnerClassMapper;
    @Autowired
    private UserValueMapper userValueMapper;
    @Autowired
    private UserStudyMapper userStudyMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserMapMapper userMapMapper;
    @Autowired
    private UserRecordMapper userRecordMapper;
    @Autowired
    private FirstExamMapper firstExamMapper;
    /**
     * 新注册用户的起始竹子量是299
     */
    private static long bambooInit = 299;

    public User findUserById(String id) {
        return userMapper.findUserById(id);
    }

    public User findUserByName(String name) {
        return userMapper.findUserByName(name);
    }

    public void modifyUser(User user) {
        userMapper.updateUser(user);
    }

    public void modifyUserInformation(User user) {
        userMapper.updateUserInformation(user);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void insertUser(User user) {
        userMapper.insertUser(user);
        UserValue userValue = UserValue.builder().id(Long.parseLong(user.getId()))
                .bamboo(bambooInit)
                .getBambooAll(bambooInit)
                .getBambooToday(bambooInit).build();
        userValueMapper.insertUserValue(userValue);
        //流水
        UserRecord record = UserRecord.builder().changeValue(bambooInit)
                .id(UUID.randomUUID().toString())
                .userId(Long.parseLong(user.getId()))
                .targetValue(bambooInit)
                .isTake(1L)
                .beginValue(0L).type("bamboo")
                .content("新注册用户赠送" + bambooInit)
                .build();
        userRecordMapper.insertUserRecord(record);
        userStudyMapper.insertUserStudy(UserStudy.builder().id(Long.parseLong(user.getId())).build());
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public CodeStateEnum updateIdentifyCode(String userId, String level, String code) {
        PartnerCode partnerCode = partnerCodeMapper.selectByIdentifyCode(code);
        if (partnerCode == null) {
            return CodeStateEnum.REDEEM_ERROR;
        } else if (partnerCode.getStatus() == 1) {
            return CodeStateEnum.REDEEM_USED;
        } else {
            PartnerSchool partnerSchool = partnerSchoolMapper.selectById(partnerCode.getSchoolId().toString());
            PartnerClass partnerClass;
            String endTimeStr;//学校班级截止开通时间
            if (partnerCode.getType().equals(1)) {
                partnerClass = null;
                endTimeStr = partnerSchool.getEndTime();
            } else {
                partnerClass = partnerClassMapper.selectById(partnerCode.getClassId().toString());
                endTimeStr = partnerClass.getEndTime();
            }
            if (DateUtil.timeIsBefore(endTimeStr)) {
                return CodeStateEnum.REDEEM_EXPIRE;
            } else {
                //1.将code删除，
                //2.并赋予新code权限
                PartnerCode resetCode = partnerCodeMapper.selectUserOpenCode(userId);
                if (resetCode != null) {
                    partnerCodeMapper.deleteById(resetCode.getId().toString());
                }
                saveUnblock(userId, partnerCode, partnerClass);
                if (null != partnerCode.getClassId()){
                    firstExamMapper.updateClassIdByUserId(partnerCode.getClassId().toString(),userId);
                }
                partnerCode.setActivateTime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
                partnerCode.setUserId(userId);
                partnerCode.setStatus(1);
                partnerCodeMapper.updateSelective(partnerCode);
                return CodeStateEnum.REDEEM_SUCCESS;
            }
        }
    }

    /**
     * 保存用户解锁权限逻辑
     *
     * @param userId
     * @param partnerCode
     * @param partnerClass
     */
    private void saveUnblock(String userId, PartnerCode partnerCode, PartnerClass partnerClass) {
        if (partnerCode.getType().equals(1)) {//老师
            User user = userMapper.findUserById(userId);
            user.setOrderMap("S1,S2,S3,S4,S5,S6");
            userMapper.updateUser(user);
        } else {//学生
            User user = userMapper.findUserById(userId);
            user.setOrderMap(partnerClass.getLevel());
            userMapper.updateUser(user);
        }
        /** 添加解锁等级到用户地图 begin */
        UserMap userMap = userMapMapper.findUserMapByUserId(userId);
        if (userMap == null) {
            if (partnerCode.getType().equals(1)) {//老师
                userMap = UserMap.builder().id(Long.parseLong(userId)).unblock("S1,S2,S3,S4,S5,S6").build();
            } else {//学生
                userMap = UserMap.builder().id(Long.parseLong(userId)).unblock(partnerClass.getLevel()).build();
            }
            userMapMapper.insertUserMap(userMap);
        } else {
            if (partnerCode.getType().equals(1)) {//老师
                userMap.setUnblock("S1,S2,S3,S4,S5,S6");
            } else {//学生
                userMap.setUnblock(partnerClass.getLevel());
            }
            userMapMapper.updateUserMap(userMap);
        }
        /** 添加解锁等级到用户地图 end */
    }

}
