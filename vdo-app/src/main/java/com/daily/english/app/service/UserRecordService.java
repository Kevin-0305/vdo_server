package com.daily.english.app.service;

import com.daily.english.app.domain.UserRecord;
import com.daily.english.app.mapper.UserRecordMapper;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class UserRecordService {
    @Autowired
    private UserRecordMapper userRecordMapper;

    public UserRecord findUserRecordById(String id){
        return userRecordMapper.findUserRecordById(id);
    }
    public List<UserRecord> findUserRecordList(UserRecord ur){
        return userRecordMapper.findUserRecordList(ur);
    }
    public String insertUserRecord(UserRecord ur) {
        String id = UUID.randomUUID().toString();
        ur.setId(id);
        ur.setCreateTime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
        userRecordMapper.insertUserRecord(ur);
        return id;
    }
    public  UserRecord findUserRecord(UserRecord ur){
        return userRecordMapper.findUserRecord(ur);
    }
    public void updateUserRecord(UserRecord uv){
        userRecordMapper.updateUserRecord(uv);
    }
}
