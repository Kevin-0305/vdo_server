package com.daily.english.app.service;

import com.daily.english.app.domain.Praise;
import com.daily.english.app.mapper.PraiseMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PraiseService {

    @Autowired
    private PraiseMapper praiseMapper;

    public void savePraise(Praise praise) {
        praiseMapper.insert(praise);
    }
}
