package com.daily.english.app.service;

import com.daily.english.app.domain.UserMap;
import com.daily.english.app.mapper.UserMapMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserMapService {
    @Autowired
    private UserMapMapper userMapMapper;

    public UserMap findUserMapById(String id) {
        UserMap um = userMapMapper.findUserMapByUserId(id);
        if(um == null){
            um = UserMap.builder().id(Long.parseLong(id)).build();
            insertUserMap(um);
            um = userMapMapper.findUserMapByUserId(id);
        }
        if(um.getIsfirst() == 0){
            //首次
            userMapMapper.updateUserMap(UserMap.builder().id(um.getId()).isfirst(1).build());
        }
        return um;
    }

    public void insertUserMap(UserMap user) {
        userMapMapper.insertUserMap(user);
    }
    public void modifyUserMap(UserMap user) {
        userMapMapper.updateUserMap(user);
    }

}
