package com.daily.english.app.service;

import com.daily.english.app.domain.Notic;
import com.daily.english.app.mapper.NoticMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class NoticService {
    @Autowired
    private NoticMapper noticMapper;


    public List<Notic> getMyNoticList(String userId, Integer pageNo, Integer limit, String isRead) {
        List<Notic> noticList = noticMapper.getMyNoticList(userId, pageNo, limit, isRead);
        return noticList;
    }

    public Notic getMyNoticInfo(String noticId) {
        Notic notic = noticMapper.getMyNotic(noticId);
        return notic;
    }

    public Integer getMyNoticUnReadCount(String userId) {
        Integer noticCount = noticMapper.getMyNoticUnReadCount(userId);
        return noticCount;
    }

    public Integer getMyNoticListCount(String userId, String isRead) {
        Integer noticCount = noticMapper.getMyNoticListCount(userId,isRead);
        return noticCount;
    }

    public void updateById(Notic notic) {
        noticMapper.updateById( notic);
    }
}
