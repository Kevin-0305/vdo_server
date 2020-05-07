package com.daily.english.app.service;

import com.alibaba.fastjson.JSON;
import com.daily.english.app.domain.Answer;
import com.daily.english.app.domain.AnswerEntry;
import com.daily.english.app.mapper.AnswerMapper;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class AnswerService {

    @Autowired
    private AnswerMapper answerMapper;

    @Autowired
    private MapperFacade mapper;

    public void saveAnswer(Answer answer) {
        String answersJson = JSON.toJSONString(answer.getAnswers());
        answer.setCreateTime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
        answer.setAnswersJson(answersJson);
        answerMapper.insert(answer);
    }

    public Answer getAnswerById(Long id) {
        Answer answer = answerMapper.findAnswerById(id);
        String answersJson = answer.getAnswersJson();
        List<AnswerEntry> answerEntryList= JSON.parseArray(answersJson, AnswerEntry.class);
        answer.setAnswers(answerEntryList);
        return answer;
    }

    public List<Answer> getAnswerList() {
        List<Answer> answerList = answerMapper.findAnswerList();
        for (Answer answer : answerList) {
            String answersJson = answer.getAnswersJson();
            List<AnswerEntry> answerEntryList= JSON.parseArray(answersJson, AnswerEntry.class);
            answer.setAnswers(answerEntryList);
        }
        return answerList;
    }

    /**
     * userId examInfoId  首考用户答案
     * @param answer
     * @return
     */
    public Answer getFirstAnswerByAnswer(Answer answer){
       Answer a = answerMapper.findFirstAnswer(answer);
       if(a != null){
           a.setAnswers(JSON.parseArray(a.getAnswersJson(), AnswerEntry.class));
       }
       return a;
    }

    public void insertSureAnswer(Answer answer) {
        String answersJson = JSON.toJSONString(answer.getAnswers());
        answer.setCreateTime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
        answer.setAnswersJson(answersJson);
        answerMapper.insertSureAnswer(answer);
    }
    public void insertErrorFirstAnswer(Answer answer) {
        String answersJson = JSON.toJSONString(answer.getAnswers());
        answer.setCreateTime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
        answer.setAnswersJson(answersJson);
        answerMapper.insertErrorFirstAnswer(answer);
    }
	public List<AnswerEntry> getAnswerByIExamInfoId(String id,String userId) {
		Answer answer = answerMapper.fingAnswerByExamInfoId(id,userId);
		String answersJson = answer.getAnswersJson();
		List<AnswerEntry> answerEntryList= JSON.parseArray(answersJson, AnswerEntry.class);
		return answerEntryList;
	}
}
