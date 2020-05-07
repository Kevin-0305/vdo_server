package com.daily.english.app.service;

import com.daily.english.app.condition.WrongTopicCondition;
import com.daily.english.app.domain.WrongTopic;
import com.daily.english.app.mapper.WrongTopicMapper;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class WrongTopicService {
    @Autowired
    private WrongTopicMapper wrongTopicMapper;

    public void saveWrongTopic(WrongTopic wrongTopic) {
        wrongTopicMapper.insertWrongTopic(wrongTopic);
    }

    public void modifyWrongTopic(WrongTopic wrongTopic) {
        wrongTopicMapper.updateWrongTopic(wrongTopic);
    }

    public void removeWrongTopic(Long id) {
        wrongTopicMapper.deleteWrongTopicById(id);
    }

    public Integer getWrongTopicCount(WrongTopicCondition wrongTopicCondition) {
        return wrongTopicMapper.findWrongTopicCount(wrongTopicCondition);
    }

    public List<WrongTopic> getWrongTopicListByPage(WrongTopicCondition wrongTopicCondition) {
        List<WrongTopic> wrongTopicList = wrongTopicMapper.findWrongTopicListByPage(wrongTopicCondition);
        if (CollectionUtils.isNotEmpty(wrongTopicList)) {
            return wrongTopicList;
        } else {
            return Lists.newArrayList();
        }
    }

    public List<WrongTopic> getWrongTopicList(WrongTopicCondition wrongTopicCondition) {
        List<WrongTopic> wrongTopicList = wrongTopicMapper.findWrongTopicList(wrongTopicCondition);
        if (CollectionUtils.isNotEmpty(wrongTopicList)) {
            return wrongTopicList;
        } else {
            return Lists.newArrayList();
        }
    }
}
