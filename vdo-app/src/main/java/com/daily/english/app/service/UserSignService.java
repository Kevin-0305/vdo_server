package com.daily.english.app.service;

import com.daily.english.app.domain.*;
import com.daily.english.app.mapper.UserSignMapper;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class UserSignService {

    @Autowired
    private UserRecordService userRecordService ;
    @Autowired
    private UserValueService userValueService ;
    @Autowired
    private UserSignMapper userSignMapper;
    private static  long SIGNBAMBOO = 20;
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public UserSign querySignByUserId(Long userId){
        return userSignMapper.findUserSignByUserId(userId);
    }
    public Integer findUserSignByUserId(Long userId) {
        UserSign userSign = userSignMapper.findUserSignByUserId(userId);
        String nowTime = DateTime.now().toString("yyyy-MM-dd");
        if(userSign == null){
            userSign = UserSign.builder()
                    .createTime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"))
                    .lastModifyTime(nowTime)
                    .count(1)
                    .userId(userId)
                    .signCount(1)
                    .build();
            userSignMapper.insertSelective(userSign);
            addBambooByUserId(userId,false);
            return 1;
        }else{
             //今天已经签到过了
            if (userSign.getLastModifyTime().equals(nowTime)) {
                return userSign.getSignCount();
            }

            //是否连续签到
            if (DateTime.now().plusDays(-1).toString("yyyy-MM-dd").equals(userSign.getLastModifyTime()) && userSign.getSignCount() != 7) {
                //"连续签到"
                UserSign newUserSign = UserSign.builder()
                        .lastModifyTime(nowTime)
                        .count(userSign.getCount() + 1)
                        .userId(userId)
                        .signCount(userSign.getSignCount() + 1)
                        .build();
                userSignMapper.updateUserSign(newUserSign);
                 //userSign.getSignCount() == 6加双倍
                addBambooByUserId(userId,newUserSign.getSignCount() == 7);
                return newUserSign.getSignCount();
            } else {
                //签到次数重新开始  第7天双倍40
                UserSign newUserSign = UserSign.builder()
                        .lastModifyTime(nowTime)
                        .count(userSign.getCount() + 1)
                        .userId(userId)
                        .signCount(1)
                        .build();
                userSignMapper.updateUserSign(newUserSign);
                addBambooByUserId(userId,false);
                return newUserSign.getSignCount();
            }
        }
    }

    private void addBambooByUserId(Long userId,boolean flag){
        //用户加竹子
        Long nowBamaoo = SIGNBAMBOO;
        if(flag){
            nowBamaoo = nowBamaoo*2;
        }
        UserValue userValue = userValueService.findUserValueById(userId.toString());
        UserRecord userRecord = UserRecord.builder().userId(userId).type("bamboo").changeValue(nowBamaoo)
                .beginValue(userValue.getBamboo())
                .targetValue(userValue.getBamboo() + nowBamaoo)
                .content("sign_签到")
                .sourceId("sign")
                .isTake(1l)
                .build();
        userRecordService.insertUserRecord(userRecord);

        UserValue nowUserValue = UserValue.builder().bamboo(userValue.getBamboo()+nowBamaoo).getBambooToday(userValue.getGetBambooToday()+nowBamaoo)
                .getBambooAll(userValue.getGetBambooToday()+nowBamaoo)
                .sumWeekBamboo(userValue.getSumWeekBamboo()+nowBamaoo)
                .id(userValue.getId())
                .build();
        userValueService.updateUserValue(nowUserValue);
    }
}
