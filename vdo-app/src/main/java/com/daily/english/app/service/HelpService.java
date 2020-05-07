package com.daily.english.app.service;

import com.alibaba.fastjson.JSON;
import com.daily.english.app.domain.Help;
import com.daily.english.app.mapper.HelpMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
public class  HelpService {

    @Autowired
    private HelpMapper helpMapper;
    public void deleteHelpById(Long id){
        helpMapper.deleteHelpById(id);
    }

    public void saveHelp(Help help) {
        String answersJson = JSON.toJSONString(help.getFormatAnswer());
        String answersJson_en = JSON.toJSONString(help.getFormatAnswer_en());
        String answersJson_hk = JSON.toJSONString(help.getFormatAnswer_hk());
        help.setAnswersJson(answersJson);
        help.setAnswersJson_en(answersJson_en);
        help.setAnswersJson_hk(answersJson_hk);
        helpMapper.insert(help);
    }
    public Help getHelpById(Long id) {
        Help help = helpMapper.findHelpById(id);
        if(help == null){
            return null;
        }
        help = formatAnswer(help);
        /*String answersJson = help.getAnswersJson();
        List<String> answerEntryList= JSON.parseArray(answersJson, String.class);
        help.setFormatAnswer(answerEntryList);*/
        return help;
    }

    public List<Help> getHelpList() {
        List<Help> helpList = helpMapper.findHelpList();
        List helpNewList= helpMapper.findHelpList();
        for (Help help : helpList) {
            help = formatAnswer(help);
        }
        return helpList;
    }
    private Help formatAnswer(Help help){
        String answersJson = help.getAnswersJson();
        String answersJson_en = help.getAnswersJsonEn();
        String answersJson_hk = help.getAnswersJsonHk();
        String question_en = help.getQuestionEn();
        String question_hk = help.getQuestionHk();
        List<String> answerEntryList= JSON.parseArray(answersJson, String.class);
        List<String> answerEntryList_en = JSON.parseArray(answersJson_en, String.class);
        List<String> answerEntryList_hk = JSON.parseArray(answersJson_hk, String.class);
        help.setQuestion_en(question_en);
        help.setQuestion_hk(question_hk);
        help.setFormatAnswer(answerEntryList);
        help.setFormatAnswer_en(answerEntryList_en);
        help.setFormatAnswer_hk(answerEntryList_hk);
        return help;
    }

}
