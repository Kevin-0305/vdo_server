package com.daily.english.app.service;

import com.daily.english.app.domain.UserStudy;
import com.daily.english.app.mapper.UserStudyMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class UserStudyService {
    @Autowired
    private UserStudyMapper userStudyMapper;
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public UserStudy findUserMapById(String id) {
        UserStudy us =userStudyMapper.findUserStudyByUserId(id);
        if(us == null){
            us = UserStudy.builder().id(Long.parseLong(id)).build();
            insertUserStudy(us);
            us = userStudyMapper.findUserStudyByUserId(id);
        }
        return us;
    }
    public List<UserStudy> queryUserStudy(){
        return userStudyMapper.findUserStudyList();
    }
    public void insertUserStudy(UserStudy user) {
        userStudyMapper.insertUserStudy(user);
    }

    public void modifyUserStudy(UserStudy user){
        userStudyMapper.updateUserStudy(user);
    }


}
