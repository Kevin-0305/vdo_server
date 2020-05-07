package com.daily.english.app.service;

import com.daily.english.app.domain.Collect;
import com.daily.english.app.mapper.CollectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CollectService {

    @Autowired
    private CollectMapper collectMapper;

    public void saveCollect(Collect collect) {
        collectMapper.insert(collect);
    }
}
