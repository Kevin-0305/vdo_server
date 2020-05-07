package com.daily.english.app.job;

import com.daily.english.app.domain.*;
import com.daily.english.app.mapper.PartnerCodeMapper;
import com.daily.english.app.mapper.UserMapMapper;
import com.daily.english.app.mapper.UserMapper;
import com.daily.english.app.service.PartnerClassService;
import com.daily.english.app.service.PartnerSchoolService;
import com.daily.english.app.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Configuration
@EnableScheduling
@Slf4j
public class UserMapUnlockTask {
    @Autowired
    private PartnerClassService partnerClassService;
    @Autowired
    private PartnerSchoolService partnerSchoolService;
    @Autowired
    private PartnerCodeMapper partnerCodeMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserMapMapper userMapMapper;

    /**
     * 班级合作过期将所有学生解锁level重置
     */
    @Scheduled(cron = "0 1 0 * * *")
    private void classStudentMapUnlock() {
        List<PartnerClass> partnerClasses = partnerClassService.selectPartnerClassList(null);
        for (PartnerClass partnerClass : partnerClasses) {
            if (StringUtils.isBlank(partnerClass.getEndTime())){
               continue;
            }
            if (DateUtil.timeIsBefore(partnerClass.getEndTime())) {
                List<PartnerCode> students = partnerCodeMapper.selectStudentByClassId(partnerClass.getId().toString(), 1);
                for (PartnerCode student : students) {
                    /** 更新用户开通地图级别 begin */
                    if (StringUtils.isNotBlank(student.getUserId())) {
                        clearUserMapUnlock(student);
                    }
                    /** 更新用户开通地图级别 end */
                }
            }

        }
    }

    /**
     * 清除用户地图解锁字段
     * @param code
     */
    private void clearUserMapUnlock(PartnerCode code) {
        User user = userMapper.findUserById(code.getUserId());
        user.setOrderMap(null);
        userMapper.updateUser(user);
        UserMap userMap = UserMap.builder().id(Long.parseLong(code.getUserId())).unblock(null).build();
        userMapMapper.updateUserMapUnblock(userMap);
    }


    /**
     * 学校合作过期将所有老师解锁level重置
     */
    @Scheduled(cron = "0 1 0 * * *")
    private void schoolTeacherMapUnlock() {
        List<PartnerSchool> partnerSchools = partnerSchoolService.selectPartnerSchoolList(null);
        for (PartnerSchool partnerSchool : partnerSchools) {
            if (StringUtils.isBlank(partnerSchool.getEndTime())){
                continue;
            }
            if (DateUtil.timeIsBefore(partnerSchool.getEndTime())) {
                List<PartnerCode> teachers = partnerCodeMapper.selectTeacherBySchoolId(partnerSchool.getId().toString(), 1);
                for (PartnerCode teacher : teachers) {
                    /** 更新用户开通地图级别 begin */
                    if (StringUtils.isNotBlank(teacher.getUserId())) {
                        clearUserMapUnlock(teacher);
                    }
                    /** 更新用户开通地图级别 end */
                }
            }
        }
    }
}