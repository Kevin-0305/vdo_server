package com.daily.english.app.service;

import com.daily.english.app.domain.*;
import com.daily.english.app.mapper.*;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class UserExamInfoService {

    @Autowired
    private MapMapper mapMapper;
    @Autowired
    private AnswerMapper answerMapper;
    @Autowired
    private UserExamInfoMapper userExamInfoMapper;
    @Autowired
    private UserMapService userMapService;
    @Autowired
    private UserStudyService userStudyService;
    @Autowired
    private ExamInfoService examInfoService;


//    public void getUserExamInfo(String userId, String examId) {
//        ExamInfo examInfo = examInfoMapper.findExamInfoByExamId(examId);
//        ExamInfoVo examInfoVo = mapper.map(examInfo, ExamInfoVo.class);
//
//        UserExamInfo userExamInfo = userExamInfoMapper.findUserExamInfoByExamId(userId, examId);
//        if (userExamInfo != null) {
//            UserExamInfoVo userExamInfoVo = mapper.map(userExamInfo, UserExamInfoVo.class);
//            userExamInfoVo.setExamInfoVo(examInfoVo);
//            examInfoVo.setUserIsSbmit(1);
//            examInfoVo.setScore(userExamInfoVo.getScore());
//        } else {
//            return null;
//        }
//    }
     @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void addUserExamInfo(UserExamInfo userExamInfo) {
        userExamInfoMapper.insertUserExamInfo(userExamInfo);
        addUserMapScore(userExamInfo);
         //计算平均分用
         UserStudy userStudy = userStudyService.findUserMapById(userExamInfo.getUserId());
         average(true,userStudy,userExamInfo);
    }
    //计算本周平均分 flag true 新增的关卡本周
    private void average(boolean flag,UserStudy userStudy,UserExamInfo userExamInfo){
        Long scoreWeek = userStudy.getAverageScoreWeek();
        BigDecimal average = new BigDecimal(scoreWeek == null ? 0:scoreWeek).add(new BigDecimal(userExamInfo.getScore())).divide(new BigDecimal(2));

        UserStudy userStudy1 = UserStudy.builder().averageScoreWeek(average.longValue()).id(Long.parseLong(userExamInfo.getUserId()))
                .finishChallengeWeek(userStudy.getFinishChallengeWeek() + 1)
                .finishChallengeAll(userStudy.getFinishChallengeAll() +1)
                .build();
        if(flag){
            ExamInfo examInfo = examInfoService.getExamInfo(userExamInfo.getExamInfoId());
            userStudy1.setQuestionAmoutToday(userStudy.getQuestionAmoutToday() + examInfo.getQuestionNum().longValue());
            userStudy1.setQuestionAmoutAll(userStudy.getQuestionAmoutAll() +  examInfo.getQuestionNum().longValue());
        }
        userStudyService.modifyUserStudy(userStudy1);
    }

    public void updateUserExamInfo(UserExamInfo userExamInfo) {
        userExamInfoMapper.modifyUserExamInfo(userExamInfo);
        addUserMapScore(userExamInfo);
        //计算平均分用
        UserStudy userStudy = userStudyService.findUserMapById(userExamInfo.getUserId());
        average(false,userStudy,userExamInfo);
    }

    public UserExamInfo getUserExamInfo(String userId, String examId) {
        return userExamInfoMapper.findUserExamInfoByExamId(userId, examId);
    }

    public List<UserExamInfo> getUserExamInfoListByExamId(String examId) {
        return userExamInfoMapper.findUserExamInfoListByExamId(examId);
    }
    public void delUserExamInfo(String userId,String examInfoId){
        answerMapper.delAnswer(userId,examInfoId);
        userExamInfoMapper.delUserExamInfo(userId,examInfoId);
    }
    private  void addUserMapScore(UserExamInfo userExamInfo){
        Map map = mapMapper.findMapByExamId(userExamInfo.getExamInfoId());
        UserMap userMap = userMapService.findUserMapById(userExamInfo.getUserId());
        String score = userExamInfo.getScore();
        if(map == null){
            return;
        }
        if("2".equals(map.getType())){
            score =  "-1";
        }
        Map initMap = Map.builder().level(map.getLevel()).examId(map.getExamId()).build();
        int count = mapMapper.findMapExamIdCount(initMap);

        userMap = insertScore(count,map.getLevel(),userMap,score);
        userMapService.modifyUserMap(userMap);
    }
    //mapcores 历史成绩
    private String  compareScore(int location,String  mapcores ,String score){
        String scoreString = "";
        if(Strings.isEmpty(mapcores)){
            return score;
        }
        List<String> scores = Arrays.asList(mapcores.split(","));
        if(scores.size() >= location){
            //更新
            String dscore = scores.get(location - 1);
            if(dscore.equals("")){
                  //score = dscore;

            }else if(Integer.parseInt(dscore) > Integer.parseInt(score)){
                score = dscore;
            }
            scores.set(location -1,score);
            for (int i = 0; i< scores.size();i++){
                if (i == 0) {
                    scoreString = scores.get(i);
                }else{
                    scoreString += ","+ scores.get(i);
                }
            }
        }else if((location - scores.size()) == 1 ){
            //新增一个
            scoreString = mapcores + ","+ score;
        }else{
            //漏了几个补几个
                int sub = location - scores.size() - 1;
                for(int z=sub ; z > 0;z --){
                    scoreString += ",";
                }
                scoreString = mapcores + scoreString + ","+ score;
        }

        return scoreString;
    }
    private UserMap insertScore(int source,String type,UserMap userMap,String score){

        switch (type){
            case "S1":
                String scoreString = compareScore(source,userMap.getS1MapScore(),score);
                userMap.setS1MapScore(scoreString);
                break;
            case "S2":
                String scoreStrings2 =  compareScore(source,userMap.getS2MapScore(),score);
                userMap.setS2MapScore(scoreStrings2);
                break;
            case "S3":
                String scoreStrings3 = compareScore(source,userMap.getS3MapScore(),score);
                userMap.setS3MapScore(scoreStrings3);
                break;
            case "S4":
                String scoreStrings4 = compareScore(source,userMap.getS4MapScore(),score);
                userMap.setS4MapScore(scoreStrings4);
                break;
            case "S5":
                String scoreStrings5 = compareScore(source,userMap.getS5MapScore(),score);
                userMap.setS5MapScore(scoreStrings5);
                break;
            case "S6":
                String scoreStrings6 = compareScore(source,userMap.getS6MapScore(),score);
                userMap.setS6MapScore(scoreStrings6);
                break;
                default:
        }
        return userMap;
    }
}
