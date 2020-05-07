package com.daily.english.app.service;

import com.daily.english.app.domain.UserValue;
import com.daily.english.app.mapper.UserValueMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserValueService {
    @Autowired
    private UserValueMapper userValueMapper;

    public UserValue findUserValueById(String userId) {
        UserValue uv = userValueMapper.findUserValueByUserId(userId);
        if(uv == null){
            uv = UserValue.builder().id(Long.parseLong(userId)).build();
            insertUserValue(uv);
            uv = userValueMapper.findUserValueByUserId(userId);
        }
        return uv;
    }
    public List<UserValue> findUserValueList(){
        return userValueMapper.findUserValueList();
    }
    public void insertUserValue(UserValue user) {
        userValueMapper.insertUserValue(user);
    }

    public void updateUserValue(UserValue uv){
        userValueMapper.updateUserValue(uv);
    }
}
