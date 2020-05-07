package com.daily.english.app.service;

import com.daily.english.app.domain.Questions;
import com.daily.english.app.mapper.QuestionsMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class QuestionsService {
    @Autowired
    private QuestionsMapper questionsMapper;

    public Questions getQuestionsById(String id) {
        return questionsMapper.findQuestionsById(id);
    }
    public List<Questions> getQuestionsListById(List<String> ids) {
        return questionsMapper.findQuestionsListByIds(ids);
    }
}
