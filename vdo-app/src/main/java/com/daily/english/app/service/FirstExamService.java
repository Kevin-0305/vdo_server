package com.daily.english.app.service;

import com.daily.english.app.domain.StudyExamInfo;
import com.daily.english.app.dto.StudyExamInfoDto;
import com.daily.english.app.mapper.FirstExamMapper;
import com.daily.english.app.mapper.PartnerCodeMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class FirstExamService {

    @Autowired
    private FirstExamMapper firstExamMapper;
    @Autowired
    private PartnerCodeMapper partnerCodeMapper;

    public void saveUserExamFirst(StudyExamInfo studyExamInfo) {
        //用户的所在班级
        List<String> classIds = partnerCodeMapper.queryClassIdByUserId(studyExamInfo.getUserId());
        //除用户外所有班级用户
        if(classIds.size() == 0){
            firstExamMapper.insertFirstExam(studyExamInfo);
        }else{
            classIds.forEach(c->{
                int ranking = firstExamMapper.findUExamIdCount(c,studyExamInfo.getFirstScore(),studyExamInfo.getExamId());
                StudyExamInfo study = studyExamInfo;
                study.setRanking(ranking);
                study.setClassId(c);
                firstExamMapper.insertFirstExam(studyExamInfo);
            });
        }
    }

    public StudyExamInfo queryExamUserInfo(StudyExamInfo studyExamInfo) {
        return firstExamMapper.findExamByExamId(studyExamInfo);
    }
    public List<StudyExamInfo> queryStudyExamInfoList(StudyExamInfoDto studyExamInfoDto) {
        return firstExamMapper.findExamInfoList(studyExamInfoDto);
    }
    public void updateHightScore(StudyExamInfo studyExamInfo){
        firstExamMapper.updateHightScore(studyExamInfo);
    }


}
