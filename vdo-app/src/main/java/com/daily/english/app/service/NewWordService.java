package com.daily.english.app.service;

import com.daily.english.app.condition.NewWordCondition;
import com.daily.english.app.domain.NewWord;
import com.daily.english.app.domain.UserStudy;
import com.daily.english.app.mapper.NewWordMapper;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class NewWordService {
    @Autowired
    private NewWordMapper newWordMapper;
    @Autowired
    private UserStudyService userStudyService;
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void saveNewWord(NewWord newWord) {
        newWordMapper.insertNewWord(newWord);
        //学习生词总数量 +1
        UserStudy userStudy = userStudyService.findUserMapById(newWord.getUserId());
        UserStudy userStudy1 = UserStudy.builder().newWordsAll(userStudy.getNewWordsAll() +1 ).id(Long.parseLong(newWord.getUserId()))
                .newWordsWeek(userStudy.getNewWordsWeek()+1)
                .build();
        userStudyService.modifyUserStudy(userStudy1);
    }

    public void modifyNewWord(NewWord newWord) {
        newWordMapper.updateNewWord(newWord);
    }

    public void removeNewWord(Long id) {
        newWordMapper.deleteNewWordById(id);
    }

    public Integer getNewWordCount(NewWordCondition newWordCondition) {
        return newWordMapper.findNewWordCount(newWordCondition);
    }

    public Map<String, Object> getDate(NewWordCondition newWordCondition) {
        Date minDate = newWordMapper.getMinDate(newWordCondition);
        if (minDate == null) {
            return null;
        }
        newWordCondition.setMinDate(minDate);
        List<NewWord> wordList = newWordMapper.getByDate(newWordCondition);

        ArrayListMultimap<String, NewWord> dateMapList = ArrayListMultimap.create();
        for (NewWord newWord : wordList) {
            String dateStr = new DateTime(newWord.getAddDate().getTime()).toString("yyyy-MM-dd");
            dateMapList.put(dateStr, newWord);
        }
        Map<String, Object> dateMap = Maps.newHashMap();

        String minDateStr = new DateTime(minDate.getTime()).toString("yyyy-MM-dd");
        dateMap.put("minDate", minDateStr);
        dateMap.put("words", dateMapList);
        return dateMap;
    }

    public List<NewWord> getNewWordListByPage(NewWordCondition newWordCondition) {
        List<NewWord> newWordList = newWordMapper.findNewWordListByPage(newWordCondition);
        if (CollectionUtils.isNotEmpty(newWordList)) {
            return newWordList;
        } else {
            return Lists.newArrayList();
        }
    }

    public List<NewWord> getNewWordList(NewWordCondition newWordCondition) {
        List<NewWord> newWordList = newWordMapper.findNewWordList(newWordCondition);
        if (CollectionUtils.isNotEmpty(newWordList)) {
            return newWordList;
        } else {
            return Lists.newArrayList();
        }
    }

    public NewWord getNewWord(NewWordCondition newWordCondition) {
        NewWord newWord = newWordMapper.findNewWord(newWordCondition);
        return newWord;
    }
}
