package com.daily.english.app.controller;

import com.daily.english.app.domain.*;
import com.daily.english.app.service.*;
import com.daily.english.app.util.Body;
import com.daily.english.app.util.ResponseUtil;
import com.daily.english.app.vo.ExamInfoVo;
import com.daily.english.app.vo.UserExamInfoVo;
import com.daily.english.app.vo.UserRecordVo;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Map;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Api(tags = "用户试卷")
@Slf4j
@RestController
@RequestMapping(value = "/path/exam")
public class UserExamInfoController {
    @Autowired
    private ExamInfoService examInfoService;
    @Autowired
    private FirstExamService firstExamService;
    @Autowired
    private UserRecordService userRecordService;
    @Autowired
    private UserValueService userValueService;
    @Autowired
    private UserExamInfoService userExamInfoService;
    @Autowired
    private AnswerService answerService;
    @Autowired
    private MapService mapService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserStudyService userStudyeService;

    @Autowired
    private MapperFacade mapper;

    @ApiOperation(value = "重新考试", notes = "标识清空后不会返回历史答案了")
    @GetMapping("/changeExamIsReset/{userId}/{examId}")
    public ResponseEntity<Body<String>> changeExamIsReset(
            @ApiParam("用户id") @PathVariable String userId,
            @ApiParam("试卷ID") @PathVariable String examId) {
        ExamInfo examInfo = examInfoService.getExamInfo(examId);
        if (examInfo == null) {
            return ResponseUtil.of(HttpStatus.OK, "试卷不存在");
        }
        ExamInfoVo examInfoVo = mapper.map(examInfo, ExamInfoVo.class);
        UserExamInfo userExamInfo = userExamInfoService.getUserExamInfo(userId, examId);
        if (userExamInfo != null) {
            userExamInfo.setIsReset(1);
            userExamInfo = getExamInfoDisposeType(userExamInfo);
            userExamInfoService.updateUserExamInfo(userExamInfo);
        }
        return ResponseUtil.ok("");
    }

    @ApiOperation(value = "查看考卷信息", notes = "获得考卷信息,3 5 6 含成绩")
    @GetMapping("/info/{userId}/{examId}")
    public ResponseEntity<Body<UserExamInfoVo>> getExam(
            @ApiParam("用户id") @PathVariable String userId,
            @ApiParam("试卷ID") @PathVariable String examId) {
        ExamInfo examInfo = examInfoService.getExamInfo(examId);
        if (examInfo == null) {
            return ResponseUtil.of(HttpStatus.OK, "试卷不存在");
        }
        ExamInfoVo examInfoVo = mapper.map(examInfo, ExamInfoVo.class);
        UserExamInfo userExamInfo = userExamInfoService.getUserExamInfo(userId, examId);
        if (userExamInfo == null) {
            String dateTimeStr = DateTime.now().toString("yyyy-MM-dd HH:mm:ss");
            //首考
            userExamInfo = UserExamInfo.builder().id(UUID.randomUUID().toString()).examInfoId(examInfo.getId())
                    .score("0")
                    .userId(userId)
                    .retryTime(0)
                    .createTime(dateTimeStr)
                    .updateTime(dateTimeStr)
                    .type(2)
                    .isReset(1)
                    .build();
            userExamInfoService.addUserExamInfo(userExamInfo);
            examInfoVo.setScore("0");
            examInfoVo.setUserIsSbmit(0);
        }
        if (userExamInfo.getType() == 1) {
            userExamInfo.setType(2);
            userExamInfoService.updateUserExamInfo(userExamInfo);
        }
        UserExamInfoVo userExamInfoVo = mapper.map(userExamInfo, UserExamInfoVo.class);
        userExamInfoVo.setExamInfoVo(examInfoVo);
        if (userExamInfo.getIsReset() == 0 || userExamInfoVo.getType() == 3 || userExamInfoVo.getType() == 4 || userExamInfoVo.getType() == 5 || userExamInfoVo.getType() == 6) {
            Long answerId = userExamInfo.getAnswerId();
            Answer answer = answerService.getAnswerById(answerId);
            if (answer != null) {
                userExamInfoVo.setAnswers(answer.getAnswers());
            }
        }

        return ResponseUtil.ok(userExamInfoVo);
    }

    /**
     * 针对获取试卷时type状态变更
     */
    private UserExamInfo getExamInfoDisposeType(UserExamInfo userExamInfo) {
        /**  1=未考试，2=首考未提交，3=首考并提交，4=重考1阶未提交，5=重考1阶并提交
         *   6=重考2阶未提交，7=重考2阶并提交，9=其他情况
         * */
        int type = userExamInfo.getType();
        //首考和重考进入一阶循环
        if (type == 3) {
            userExamInfo.setType(4);
            //userExamInfo.setFlag(0);
        }
        if (type == 5 && Integer.parseInt(userExamInfo.getScore()) < 70) {
            userExamInfo.setType(6);
            userExamInfo.setFlag(1);
        } else if (type == 5) {
            userExamInfo.setType(4);
            userExamInfo.setFlag(0);
        }
        if (type == 7) {
            userExamInfo.setType(6);
            userExamInfo.setFlag(0);
        }
        if (type != userExamInfo.getType()) {
            userExamInfoService.updateUserExamInfo(userExamInfo);
        }
        return userExamInfo;
    }

    @ApiOperation(value = "查看已提交的考卷信息", notes = "获得已提交的考卷信息,含成绩")
    @GetMapping("/show/{userId}/{examId}")
    public ResponseEntity<Body<UserExamInfoVo>> showExam(
            @ApiIgnore HttpSession httpSession,
            @ApiParam("用户id") @PathVariable String userId,
            @ApiParam("试卷ID") @PathVariable String examId) {
        User user = userService.findUserById(userId);
        if (user == null) {
            return ResponseUtil.of(HttpStatus.OK, "Re-login please.");
        }
        Map<String, Object> resultMap = Maps.newHashMap();
        ExamInfo examInfo = examInfoService.getExamInfo(examId);
        if (examInfo == null) {
            return ResponseUtil.of(HttpStatus.OK, "试卷不存在");
        }
        ExamInfoVo examInfoVo = mapper.map(examInfo, ExamInfoVo.class);
        UserExamInfo userExamInfo = userExamInfoService.getUserExamInfo(userId, examId);
        if (userExamInfo == null) {
            String dateTimeStr = DateTime.now().toString("yyyy-MM-dd HH:mm:ss");
            userExamInfo = UserExamInfo.builder().id(UUID.randomUUID().toString()).examInfoId(examInfo.getId())
                    .score("0")
                    .userId(userId)
                    .retryTime(0)
                    .createTime(dateTimeStr)
                    .updateTime(dateTimeStr)
                    .build();
            userExamInfoService.addUserExamInfo(userExamInfo);
            examInfoVo.setScore("0");
            examInfoVo.setUserIsSbmit(0);
        }
        UserExamInfoVo userExamInfoVo = mapper.map(userExamInfo, UserExamInfoVo.class);
        userExamInfoVo.setExamInfoVo(examInfoVo);

        return ResponseUtil.ok(userExamInfoVo);
    }

    /**
     * 答题json
     */
    @ApiOperation(value = "提交考试答案", notes = "提交考试答案")
    @PostMapping("/exam/submit")
    public ResponseEntity<Body<AnalysisResult>> submitExamInfoFrom(
            @ApiIgnore HttpSession httpSession,
            @ApiParam("答题结果") @RequestBody Answer answer) {
        try {
            // 保存答案
            String userId = answer.getUserId();
            answerService.saveAnswer(answer);

            // 获得试卷
            ExamInfo examInfo = examInfoService.getExamInfo(answer.getExamId());
            if (examInfo == null) {
                return ResponseUtil.of(HttpStatus.OK, "试卷不存在");
            }

            AnalysisResult analysisResult = AnalysisResult.builder().userId(userId)
                    .examInfoId(examInfo.getId()).answerId(answer.getId())
                    .useTime(answer.getUseTime())
                    .comprehensionNum(examInfo.getComprehensionNum()).grammarNum(examInfo.getGrammarNum())
                    .vocabularyNum(examInfo.getVocabularyNum()).questionNum(examInfo.getQuestionNum())
                    .build();
            {
                // 分析结果,获得正确数
                this.parseAnswer(examInfo, answer, analysisResult);
                // 返回本次正确数比例
                double curScore = analysisResult.getQuestionResultNum() * 100 / analysisResult.getQuestionNum();
                analysisResult.setScore(Long.toString(Math.round(curScore)));

                // 获取做过该题的学员数
                List<UserExamInfo> userExamInfoList = userExamInfoService.getUserExamInfoListByExamId(examInfo.getId());
                // key userId, value score.
                // 计算做过改卷子的用户数(key)和该用户做完后的最高分(value)
                Map<String, String> userMap = Maps.newHashMap();
                for (UserExamInfo userExamInfo1 : userExamInfoList) {
                    String mapScoreStr = userMap.get(userExamInfo1.getUserId());
                    String userScoreStr = userExamInfo1.getScore();
                    if (StringUtils.isBlank(mapScoreStr)) {
                        mapScoreStr = "0";
                    }
                    if (StringUtils.isBlank(userScoreStr)) {
                        userScoreStr = "0";
                    }
                    int mapScore = Integer.parseInt(mapScoreStr);
                    int userScore = Integer.parseInt(userScoreStr);
                    if (mapScore < userScore) {
                        userMap.put(userExamInfo1.getUserId(), userScoreStr);
                    }
                }

                // 做该试卷总用户数
                int totalUserNum = userMap.keySet().size();
                // 大于多少用户分数统计器
                int count = 0;
                Collection<String> scoreStrList = userMap.values();
                for (String scoreStr : scoreStrList) {
                    if (StringUtils.isBlank(scoreStr)) {
                        scoreStr = "0";
                    }
                    int score = Integer.parseInt(scoreStr);
                    if (score <= curScore) {
                        count++;
                    }
                }
                // 用户百分比
                double countDouble = Double.valueOf(count);
                double userPercent = 0D;
                if (totalUserNum != 0) {
                    userPercent = countDouble / totalUserNum;
                }

                NumberFormat nf = NumberFormat.getPercentInstance();
                nf.setMaximumFractionDigits(2);
                String userPercentStr = nf.format(userPercent);
                analysisResult.setUserPercent(userPercentStr);
            }
            //每题分数
    /*         int questionScore = 100 / analysisResult.getQuestionNum();
            //若是首考保存到首考记录里  以每题分数计算
            StudyExamInfo studyExamInfo = StudyExamInfo.builder().userId(userId).firstScore(analysisResult.getScore()).highestScore(analysisResult.getScore())
                    .comprehension(Long.toString(Math.round(analysisResult.getComprehensionResultNum() * questionScore)))
                    .vocabulary((Long.toString(Math.round(analysisResult.getVocabularyResultNum() * questionScore))))
                    .grammar((Long.toString(Math.round(analysisResult.getGrammarResultNum() * questionScore ))))
                    .nodeNum(mapService.findMapExamIdCount(com.daily.english.app.domain.Map.builder().examId(answer.getExamId()).level(examInfo.getLevel()).build()))
                    .examId(answer.getExamId()).level(examInfo.getLevel())
                    .build();*/
            //以百分数计算
            StudyExamInfo studyExamInfo = StudyExamInfo.builder().userId(userId).firstScore(analysisResult.getScore()).highestScore(analysisResult.getScore())
                    //.comprehension(new BigDecimal(analysisResult.getComprehensionResultNum() * 100.00 / analysisResult.getComprehensionNum()).setScale(2, BigDecimal.ROUND_HALF_UP).toString())
                    //.grammar((Long.toString(Math.round(analysisResult.getGrammarResultNum() * 100 / analysisResult.getGrammarNum()))))
                    .nodeNum(mapService.findMapExamIdCount(com.daily.english.app.domain.Map.builder().examId(answer.getExamId()).level(examInfo.getLevel()).build()))
                    .examId(answer.getExamId()).level(examInfo.getLevel())
                    .build();
            if (analysisResult.getComprehensionNum() == 0) {
                studyExamInfo.setComprehension("0");
            } else {
                studyExamInfo.setComprehension(new BigDecimal(analysisResult.getComprehensionResultNum() * 100.00 / analysisResult.getComprehensionNum()).setScale(0, BigDecimal.ROUND_HALF_UP).toString());
            }
            if (analysisResult.getGrammarNum() == 0) {
                studyExamInfo.setGrammar("0");
            } else {
                studyExamInfo.setGrammar(Long.toString(Math.round(analysisResult.getGrammarResultNum() * 100 / analysisResult.getGrammarNum())));
            }
            if (analysisResult.getVocabularyNum() == 0) {
                studyExamInfo.setVocabulary("0");
            } else {
                studyExamInfo.setVocabulary(Long.toString(Math.round(analysisResult.getVocabularyResultNum() * 100 / analysisResult.getVocabularyNum())));
            }
            StudyExamInfo studyExamInfoAgo = firstExamService.queryExamUserInfo(studyExamInfo);
            if (studyExamInfoAgo == null) {
                studyExamInfo.setHighestComprehension(studyExamInfo.getComprehension());
                studyExamInfo.setHighestGrammar(studyExamInfo.getGrammar());
                studyExamInfo.setHighestVocabulary(studyExamInfo.getVocabulary());
                firstExamService.saveUserExamFirst(studyExamInfo);
            } else if (Integer.parseInt(studyExamInfoAgo.getHighestScore()) < Integer.parseInt(analysisResult.getScore())) {
                //比历史成绩高
                studyExamInfoAgo.setHighestScore(analysisResult.getScore());
                studyExamInfoAgo.setHighestComprehension(studyExamInfo.getComprehension());
                studyExamInfoAgo.setHighestGrammar(studyExamInfo.getGrammar());
                studyExamInfoAgo.setHighestVocabulary(studyExamInfo.getVocabulary());
                firstExamService.updateHightScore(studyExamInfoAgo);
            }
            //更新做题数
            UserStudy userStudy = userStudyeService.findUserMapById(answer.getUserId());
            userStudy.setQuestionAmoutToday(userStudy.getQuestionAmoutToday() + examInfo.getQuestionNum());
            userStudy.setFinishChallengeAll(userStudy.getFinishChallengeAll() + examInfo.getQuestionNum());
            userStudy.setFinishChallengeWeek(userStudy.getFinishChallengeWeek() + examInfo.getQuestionNum());
            userStudy.setQuestionAmoutAll(userStudy.getQuestionAmoutAll() + examInfo.getQuestionNum());
            userStudyeService.modifyUserStudy(userStudy);


            // 保存用户这次提交的结果
            UserExamInfo userExamInfo = userExamInfoService.getUserExamInfo(userId, answer.getExamId());
            if (userExamInfo == null) {
                String dateTimeStr = DateTime.now().toString("yyyy-MM-dd HH:mm:ss");
                userExamInfo = UserExamInfo.builder().id(UUID.randomUUID().toString()).examInfoId(examInfo.getId())
                        .userId(userId)
                        .answerId(answer.getId())
                        .retryTime(0)
                        .createTime(dateTimeStr)
                        .updateTime(dateTimeStr)
                        .score(analysisResult.getScore())
                        .type(3)
                        .build();
                if (Double.parseDouble(analysisResult.getScore()) >= 70) {
                    userExamInfo.setIsReset(0);
                } else {
                    //小于70不给答案
                    userExamInfo.setIsReset(1);
                }
                userExamInfo.setUseTime(answer.getUseTime());
                userExamInfoService.addUserExamInfo(userExamInfo);
                answerService.insertErrorFirstAnswer(Answer.builder().examId(examInfo.getId()).userId(userId).answers(analysisResult.getMistakesList()).build());
            } else {
                //不是首考
                userExamInfo.setUseTime(answer.getUseTime());
                int retryTime = userExamInfo.getRetryTime();
                String dateTimeStr = DateTime.now().toString("yyyy-MM-dd HH:mm:ss");
                userExamInfo.setAnswerId(answer.getId());
                userExamInfo.setRetryTime(retryTime + 1);
                userExamInfo.setUpdateTime(dateTimeStr);
                userExamInfo.setScore(analysisResult.getScore());
                //更新当前type
                userExamInfo = submitExamInfoDisposeType(userExamInfo);
                userExamInfoService.updateUserExamInfo(userExamInfo);
            }
            Answer sureAnswer = Answer.builder().examId(examInfo.getId()).userId(userId).answers(analysisResult.getSureList()).build();
            answerService.insertSureAnswer(sureAnswer);
            //>= 70 没有领取过在节点
            List<UserRecord> userRecordList = userRecordService.findUserRecordList(UserRecord.builder().sourceId(userExamInfo.getExamInfoId()).userId(Long.parseLong(userExamInfo.getUserId())).build());
            if (userExamInfo.getType() < 6) {
                if (userRecordList.size() == 0 && Integer.parseInt(analysisResult.getScore()) >= 70) {
                    if (userExamInfo.getType() >= 1 && userExamInfo.getType() < 4) {
                        record(100, 100, userId, userExamInfo, analysisResult);
                    } else if (userExamInfo.getType() >= 4 && userExamInfo.getType() < 6) {
                        record(50, 50, userId, userExamInfo, analysisResult);
                    }
              /*      long exp = 100;
                    long bamboo = 100;
                    UserValue userValue= userValueService.findUserValueById(userId) ;
                    UserRecord userRecord = UserRecord.builder().userId(Long.parseLong(userId)).beginValue(userValue.getExp()).changeValue(exp)
                            .targetValue(userValue.getExp()+ exp)
                            .sourceId(""+userExamInfo.getExamInfoId())
                            .type("exp")
                            .build();
                    String urid = userRecordService.insertUserRecord(userRecord);
                    analysisResult.setExp(mapper.map(UserRecord.builder().id(urid).changeValue(exp).build(), UserRecordVo.class));
                    userRecord = UserRecord.builder().userId(Long.parseLong(userId)).beginValue(userValue.getBamboo()).changeValue(bamboo)
                            .targetValue(userValue.getBamboo()+ bamboo)
                            .sourceId(""+userExamInfo.getExamInfoId())
                            .type("bamboo")
                            .build();
                    String bambooid = userRecordService.insertUserRecord(userRecord);
                    analysisResult.setBamboo(mapper.map(UserRecord.builder().id(bambooid).changeValue(bamboo).build(), UserRecordVo.class));*/
                } else if (Integer.parseInt(analysisResult.getScore()) >= 70) {
                    UserRecord expRecord = userRecordService.findUserRecord(UserRecord.builder().userId(Long.parseLong(userExamInfo.getUserId())).sourceId("" + userExamInfo.getExamInfoId()).type("exp").build());
                    analysisResult.setExp(mapper.map(expRecord, UserRecordVo.class));
                    UserRecord bambooRecord = userRecordService.findUserRecord(UserRecord.builder().userId(Long.parseLong(userExamInfo.getUserId())).sourceId("" + userExamInfo.getExamInfoId()).type("bamboo").build());
                    analysisResult.setBamboo(mapper.map(bambooRecord, UserRecordVo.class));
                }
            }
            ExamInfoVo examInfoVo = mapper.map(examInfo, ExamInfoVo.class);
            examInfoVo.setScore(analysisResult.getScore());
            examInfoVo.setUserIsSbmit(1);

            UserExamInfoVo userExamInfoVo = mapper.map(userExamInfo, UserExamInfoVo.class);
            userExamInfoVo.setExamInfoVo(examInfoVo);
            analysisResult.setUserExamInfoVo(userExamInfoVo);

            return ResponseUtil.ok(analysisResult);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.badRequest(e.toString());
        }
    }

    private void record(long exp, long bamboo, String userId, UserExamInfo userExamInfo, AnalysisResult analysisResult) {
        UserValue userValue = userValueService.findUserValueById(userId);
        UserRecord userRecord = UserRecord.builder().userId(Long.parseLong(userId)).beginValue(userValue.getExp()).changeValue(exp)
                .targetValue(userValue.getExp() + exp)
                .sourceId("" + userExamInfo.getExamInfoId())
                .type("exp")
                .build();
        String urid = userRecordService.insertUserRecord(userRecord);
        analysisResult.setExp(mapper.map(UserRecord.builder().id(urid).changeValue(exp).build(), UserRecordVo.class));
        userRecord = UserRecord.builder().userId(Long.parseLong(userId)).beginValue(userValue.getBamboo()).changeValue(bamboo)
                .targetValue(userValue.getBamboo() + bamboo)
                .sourceId("" + userExamInfo.getExamInfoId())
                .type("bamboo")
                .build();
        String bambooid = userRecordService.insertUserRecord(userRecord);
        analysisResult.setBamboo(mapper.map(UserRecord.builder().id(bambooid).changeValue(bamboo).build(), UserRecordVo.class));
    }

    /**
     * 针对提交试卷时type状态变更
     */
    private UserExamInfo submitExamInfoDisposeType(UserExamInfo userExamInfo) {
        /**  1=未考试，2=首考未提交，3=首考并提交，4=重考1阶未提交，5=重考1阶并提交
         *   6=重考2阶未提交，7=重考2阶并提交，8=重新考试，9=其他情况
         * */
        int type = userExamInfo.getType();

        switch (type) {
            case 2:
                userExamInfo.setType(3);
                if (Double.parseDouble(userExamInfo.getScore()) >= 70) {
                    userExamInfo.setIsReset(0);
                    userExamInfo.setFlag(0);
                } else {
                    userExamInfo.setIsReset(1);
                    userExamInfo.setFlag(1);
                }
                break;
            case 4:
                userExamInfo.setType(5);
                if (Double.parseDouble(userExamInfo.getScore()) >= 70) {
                    userExamInfo.setIsReset(0);
                    userExamInfo.setFlag(0);
                } else {
                    userExamInfo.setIsReset(1);
                    userExamInfo.setFlag(1);
                }
                break;
            case 6:
                userExamInfo.setFlag(0);
                userExamInfo.setType(7);
                userExamInfo.setIsReset(0);
                break;
        }
        //userExamInfoService.updateUserExamInfo(userExamInfo);
        return userExamInfo;
    }

    @ApiOperation(value = "查看已提交的考卷分析统计", notes = "查看已提交的考卷分析统计")
    @GetMapping("/analysis/{userId}/{examId}")
    public ResponseEntity<Body<AnalysisResult>> showExamAnalysis(
            @ApiIgnore HttpSession httpSession,
            @ApiParam("用户id") @PathVariable String userId,
            @ApiParam("试卷ID") @PathVariable String examId) {
        Map<String, Object> resultMap = Maps.newHashMap();
        ExamInfo examInfo = examInfoService.getExamInfo(examId);
        if (examInfo == null) {
            return ResponseUtil.of(HttpStatus.OK, "试卷不存在");
        }
        ExamInfoVo examInfoVo = mapper.map(examInfo, ExamInfoVo.class);
        // 获取答案和成绩
        UserExamInfo userExamInfo = userExamInfoService.getUserExamInfo(userId, examId);

        if (userExamInfo == null) {
            return ResponseUtil.of(HttpStatus.OK, "试卷未提交");
        }
        Long answerId = userExamInfo.getAnswerId();
        Answer answer = answerService.getAnswerById(answerId);
        if (answer == null) {
            return ResponseUtil.of(HttpStatus.OK, "试卷未提交");
        }
        AnalysisResult analysisResult = AnalysisResult.builder().userId(userId).examInfoId(examId).answerId(answerId)
                .comprehensionNum(examInfo.getComprehensionNum()).grammarNum(examInfo.getGrammarNum())
                .type(userExamInfo.getType())
                .useTime(userExamInfo.getUseTime())
                .vocabularyNum(examInfo.getVocabularyNum()).questionNum(examInfo.getQuestionNum())
                .sureList(answerService.getAnswerByIExamInfoId(examId, userId))
                .build();
        this.parseAnswer(examInfo, answer, analysisResult);
        //奖励
        List<UserRecord> urs = userRecordService.findUserRecordList(UserRecord.builder().userId(Long.parseLong(userId)).sourceId(examId).build());
        if (urs.size() == 0) {
            analysisResult.setBamboo(null);
            analysisResult.setExp(null);
        }
        for (UserRecord ur : urs) {
            if ("exp".equals(ur.getType())) {
                analysisResult.setExp(mapper.map(ur, UserRecordVo.class));
            }
            if ("bamboo".equals(ur.getType())) {
                analysisResult.setBamboo(mapper.map(ur, UserRecordVo.class));
            }
        }
        double curScore = analysisResult.getQuestionResultNum() * 100 / (analysisResult.getQuestionNum() + 0.01);
        analysisResult.setScore(Long.toString(Math.round(curScore)));
        // 获取做过该题的学员数
        List<UserExamInfo> userExamInfoList = userExamInfoService.getUserExamInfoListByExamId(examId);
        // key userId, value score.
        // 计算做过改卷子的用户数(key)和该用户做完后的最高分(value)
        Map<String, String> userMap = Maps.newHashMap();
        for (UserExamInfo userExamInfo1 : userExamInfoList) {
            String mapScoreStr = userMap.get(userExamInfo1.getUserId());
            String userScoreStr = userExamInfo1.getScore();
            if (StringUtils.isBlank(mapScoreStr)) {
                mapScoreStr = "0";
            }
            if (StringUtils.isBlank(userScoreStr)) {
                userScoreStr = "0";
            }
            int mapScore = Integer.parseInt(mapScoreStr);
            int userScore = Integer.parseInt(userScoreStr);
            if (mapScore < userScore) {
                userMap.put(userExamInfo1.getUserId(), userScoreStr);
            }
        }

        // 做该试卷总用户数
        int totalUserNum = userMap.keySet().size();
        // 大于多少用户分数统计器
        int count = 0;
        Collection<String> scoreStrList = userMap.values();
        for (String scoreStr : scoreStrList) {
            if (StringUtils.isBlank(scoreStr)) {
                scoreStr = "0";
            }
            int score = Integer.parseInt(scoreStr);
            if (score <= curScore) {
                count++;
            }
        }
        // 用户百分比
        double countDouble = Double.valueOf(count);
        double userPercent = 0D;
        if (totalUserNum != 0) {
            userPercent = countDouble / totalUserNum;
        }

        NumberFormat nf = NumberFormat.getPercentInstance();
        nf.setMaximumFractionDigits(2);
        String userPercentStr = nf.format(userPercent);
        analysisResult.setUserPercent(userPercentStr);

        // 判断如果对象为空,则置空对象.
        if (analysisResult.getAnswer() == null) {
            analysisResult.setAnswer(Answer.builder().build());
        }
        if (analysisResult.getBamboo() == null) {
            analysisResult.setBamboo(UserRecordVo.builder().build());
        }
        if (analysisResult.getExp() == null) {
            analysisResult.setExp(UserRecordVo.builder().build());
        }
        return ResponseUtil.ok(analysisResult);
    }

    private String comprehension = "Comprehension";
    private String vocabulary = "Vocabulary";
    private String grammar = "Grammar";

    /**
     * 分析试卷与答案,并返回判断结果的统计数
     *
     * @param examInfo       试卷
     * @param answer         答案
     * @param analysisResult 结果
     */
    private void parseAnswer(ExamInfo examInfo, Answer answer, AnalysisResult analysisResult) {
        List<Part> partList = examInfo.getPartList();
        ArrayListMultimap<String, AnswerEntry> answerEntryMap = ArrayListMultimap.create();
        ArrayListMultimap<String, List<Node>> nodeEntryMap = ArrayListMultimap.create();
        // 判断各考卷子类型
        for (Part part : partList) {
            // 判断当前是什么类型
            String partName = part.getPartName();
            List<Node> nodeList = part.getNodeList();
            String key = "";
            if (partName.contains(comprehension)) {
                key = comprehension;

            } else if (partName.contains(vocabulary)) {
                key = vocabulary;
            } else if (partName.contains(grammar)) {
                key = grammar;
            } else {
                key = "other";
            }
            nodeEntryMap.put(key, nodeList);
            for (Node node : nodeList) {
                if (node.getQuestionId() == null) {
                    continue;
                }
                String answerTemp = node.getAnswer();
                if ("ImgFill".equals(node.getType())) {
                    List<FillContent> fillContents = node.getImgFillContentList();
                    for (int i = 0; i < fillContents.size(); i++) {
                        if (i == 0) {
                            answerTemp = fillContents.get(i).getAnswer();
                        } else {
                            answerTemp += "," + fillContents.get(i).getAnswer();
                        }
                    }
                }
                if ("Match".equals(node.getType())) {
                    answerTemp = node.getAnswer().replaceAll("\\d+", "");
                }
                AnswerEntry answerEntry = AnswerEntry.builder()
                        .id(node.getQuestionId().toString())
                        .answer(answerTemp)
                        .build();
                answerEntryMap.put(key, answerEntry);
            }
        }
        AtomicInteger grammarNum = new AtomicInteger();
        AtomicInteger comprehensionNum = new AtomicInteger();
        AtomicInteger vocabularyNum = new AtomicInteger();

        AtomicInteger grammarResultNum = new AtomicInteger();
        AtomicInteger comprehensionResultNum = new AtomicInteger();
        AtomicInteger vocabularyResultNum = new AtomicInteger();
        AtomicInteger correctQuestionNum = new AtomicInteger();
        List<AnswerEntry> aelist = new Vector();
        List<AnswerEntry> sure = new Vector();
        // 统计各类型题目的正确结果数和总题目结果数
        {
            List<AnswerEntry> answerEntryList = answerEntryMap.get(comprehension);
            //统计总的题目数
            List<List<Node>> nodeEntryList = nodeEntryMap.get(comprehension);
            for (List<Node> nodes : nodeEntryList) {
                for (Node node : nodes) {
                    int count = returnNumByNodeSummary(node);
                    if (count != 0) {
                        comprehensionNum.getAndAdd(count);
                    }
                }
            }

            for (AnswerEntry examAnswer : answerEntryList) {
                for (AnswerEntry submitAnswer : answer.getAnswers()) {
                    if (examAnswer.getId().equals(submitAnswer.getId())) {
                        //统计正确数  根据正确数判断
                        for (List<Node> nodes : nodeEntryList) {
                            Node node = returnNodeById(examAnswer.getId(), nodes);
                            ResultModel resultModel = returnNumByNode(node, submitAnswer.getAnswer());
                            if (!resultModel.isFlag()) {
                                aelist.add(submitAnswer);
                            }
                            if (resultModel.getCount() != 0) {
                                sure.add(AnswerEntry.builder().id(resultModel.getAnswers()).build());
                                comprehensionResultNum.getAndAdd(resultModel.getCount());
                                correctQuestionNum.getAndAdd(resultModel.getCount());
                            }
                        }
                    }
                }
            }
        }
        {
            List<AnswerEntry> answerEntryList = answerEntryMap.get(vocabulary);
            List<List<Node>> nodeEntryList = nodeEntryMap.get(vocabulary);
            for (List<Node> nodes : nodeEntryList) {
                for (Node node : nodes) {
                    int count = returnNumByNodeSummary(node);
                    if (count != 0) {
                        vocabularyNum.getAndAdd(count);
                    }
                }
            }
            for (AnswerEntry examAnswer : answerEntryList) {
                for (AnswerEntry submitAnswer : answer.getAnswers()) {
                    if (examAnswer.getId().equals(submitAnswer.getId())) {
                        for (List<Node> nodes : nodeEntryList) {
                            Node node = returnNodeById(examAnswer.getId(), nodes);
                            ResultModel resultModel = returnNumByNode(node, submitAnswer.getAnswer());
                            if (!resultModel.isFlag()) {
                                aelist.add(submitAnswer);
                            }
                            if (resultModel.getCount() != 0) {
                                sure.add(AnswerEntry.builder().id(resultModel.getAnswers()).build());
                                vocabularyResultNum.getAndAdd(resultModel.getCount());
                                correctQuestionNum.getAndAdd(resultModel.getCount());
                            }
                        }
                    }
                }
            }
        }
        {
            List<AnswerEntry> answerEntryList = answerEntryMap.get(grammar);
            List<List<Node>> nodeEntryList = nodeEntryMap.get(grammar);
            for (List<Node> nodes : nodeEntryList) {
                for (Node node : nodes) {
                    int count = returnNumByNodeSummary(node);
                    if (count != 0) {
                        grammarNum.getAndAdd(count);
                    }
                }
            }
            for (AnswerEntry examAnswer : answerEntryList) {
                for (AnswerEntry submitAnswer : answer.getAnswers()) {
                    if (examAnswer.getId().equals(submitAnswer.getId())) {
                        for (List<Node> nodes : nodeEntryList) {
                            Node node = returnNodeById(examAnswer.getId(), nodes);
                            ResultModel resultModel = returnNumByNode(node, submitAnswer.getAnswer());
                            if (!resultModel.isFlag()) {
                                aelist.add(submitAnswer);
                            }
                            if (resultModel.getCount() != 0) {
                                sure.add(AnswerEntry.builder().id(resultModel.getAnswers()).build());
                                grammarResultNum.getAndAdd(resultModel.getCount());
                                correctQuestionNum.getAndAdd(resultModel.getCount());
                            }
                        }
                    }
                }
            }
        }
//       暂时统计
        analysisResult.setComprehensionNum(comprehensionNum.get());
        analysisResult.setGrammarNum(grammarNum.get());
        analysisResult.setVocabularyNum(vocabularyNum.get());
        analysisResult.setQuestionNum(grammarNum.get() + comprehensionNum.get() + vocabularyNum.get());
        analysisResult.setSureList(sure);
        analysisResult.setMistakesList(aelist);
        analysisResult.setGrammarResultNum(grammarResultNum.get());
        analysisResult.setComprehensionResultNum(comprehensionResultNum.get());
        analysisResult.setVocabularyResultNum(vocabularyResultNum.get());
        analysisResult.setQuestionResultNum(correctQuestionNum.get());
    }

    private Node returnNodeById(String id, List<Node> list) {
        for (Node node : list) {
            if (id.equals("" + node.getQuestionId())) {
                return node;
            }
        }
        return null;
    }

    private boolean returnIsSure(String answer, String submitAnswer) {
        boolean flag = true;
        String[] submitAnswers = submitAnswer.split(",");
        if (submitAnswers.length != answer.split(",").length) {
            flag = false;
        } else {
            for (int i = 0; i < submitAnswers.length; i++) {
                if (answer.indexOf(submitAnswers[i]) <= -1) {
                    flag = false;
                }
            }
        }
        return true;
    }

    private ResultModel returnNumByNode(Node node, String submitAnswer) {
        ResultModel resultModel = ResultModel.builder().flag(false).build();
        AtomicInteger rightNum = new AtomicInteger();
        if (node == null || Strings.isNotEmpty(node.getText()) || Strings.isEmpty(submitAnswer)) {
            return resultModel;
        }
        switch (node.getType()) {
            //选择
            case "Choose":
                if ("multi".equals(node.getChooseType())) {
                    //if(node.getAnswer().indexOf(submitAnswer) > -1){
                    if (returnIsSure(node.getAnswer(), submitAnswer)) {
                        resultModel.setFlag(true);
                        rightNum.getAndIncrement();
                        resultModel.setAnswers(node.getQuestionId().toString());
                    } else {
                        resultModel.setErrorAnswer(node.getQuestionId().toString());
                    }
                } else {
                    if (node.getAnswer().equals(submitAnswer)) {
                        resultModel.setAnswers(node.getQuestionId().toString());
                        resultModel.setFlag(true);
                        rightNum.getAndIncrement();
                    } else {
                        resultModel.setErrorAnswer(node.getQuestionId().toString());
                    }
                }
                break;
            //多重填空题 answer
            case "MulBlank":
                String[] answers = node.getAnswer().split(",");
                String[] submitanswers = submitAnswer.split(",");
                for (int i = 0; i < answers.length; i++) {
                    if (submitanswers.length <= i) {
                        break;
                    }
                    if (answers[i].indexOf(submitanswers[i]) > -1) {
                        rightNum.getAndIncrement();
                        if (resultModel.getAnswers() == null) {
                            resultModel.setAnswers(node.getQuestionId().toString() + "_" + i);
                        } else {
                            resultModel.setAnswers(resultModel.getAnswers() + "|" + i);
                        }
                    } else {
                        if (resultModel.getErrorAnswer() == null) {
                            resultModel.setErrorAnswer(node.getQuestionId().toString() + "_" + i);
                        } else {
                            resultModel.setErrorAnswer(resultModel.getAnswers() + "|" + i);
                        }
                    }
                }
                if (node.getAnswer().split(",").length == rightNum.get()) {
                    resultModel.setFlag(true);
                }
                break;
            //填充题
            case "Fill":
                String[] answersfill = node.getAnswer().split(",");
                String[] submitanswersfill = submitAnswer.split(",");
                for (int i = 0; i < answersfill.length; i++) {
                    if (submitanswersfill.length <= i) {
                        break;
                    }
                    boolean flag = false;
                    for (String s : answersfill[i].split("\\/")) {
                        if (s.equals(submitanswersfill[i])) {
                            flag = true;
                            break;
                        }
                    }
                    if (flag) {
                        rightNum.getAndIncrement();
                        if (resultModel.getAnswers() == null) {
                            resultModel.setAnswers(node.getQuestionId().toString() + "_" + i);
                        } else {
                            resultModel.setAnswers(resultModel.getAnswers() + "|" + i);
                        }
                    } else {
                        if (resultModel.getErrorAnswer() == null) {
                            resultModel.setErrorAnswer(node.getQuestionId().toString() + "_" + i);
                        } else {
                            resultModel.setErrorAnswer(resultModel.getAnswers() + "|" + i);
                        }
                    }
                }
                if (node.getAnswer().split(",").length == rightNum.get()) {
                    resultModel.setFlag(true);
                }
                break;
            case "Judge":
                if (node.getAnswer().equals(submitAnswer)) {
                    resultModel.setFlag(true);
                    rightNum.getAndIncrement();
                    resultModel.setAnswers(node.getQuestionId().toString());
                } else {
                    resultModel.setErrorAnswer(node.getQuestionId().toString());
                }
                break;
            //匹配题
            case "Match":
                String[] as = node.getAnswer().replaceAll("\\d+", "").split(",");
                String[] submit = submitAnswer.split(",");
                for (int i = 0; i < as.length; i++) {
                    if (submit.length <= i) {
                        break;
                    }
                    if (as[i].equals(submit[i])) {
                        rightNum.getAndIncrement();
                        if (resultModel.getAnswers() == null) {
                            resultModel.setAnswers(node.getQuestionId().toString() + "_" + i);
                        } else {
                            resultModel.setAnswers(resultModel.getAnswers() + "|" + i);
                        }
                    } else {
                        if (resultModel.getErrorAnswer() == null) {
                            resultModel.setErrorAnswer(node.getQuestionId().toString() + "_" + i);
                        } else {
                            resultModel.setErrorAnswer(resultModel.getAnswers() + "|" + i);
                        }
                    }
                }
                if (node.getAnswer().split(",").length == rightNum.get()) {
                    resultModel.setFlag(true);
                }
                break;
            case "ImgFill":
                List<FillContent> fillContents = node.getImgFillContentList();
                List<String> submitList = Arrays.asList(submitAnswer.split(","));
                for (int i = 0; i < fillContents.size(); i++) {
                    if (submitList.size() <= i) {
                        break;
                    }
                    if (fillContents.get(i).getAnswer().equals(submitList.get(i))) {
                        rightNum.getAndIncrement();
                        if (resultModel.getAnswers() == null) {
                            resultModel.setAnswers(node.getQuestionId().toString() + "_" + i);
                        } else {
                            resultModel.setAnswers(resultModel.getAnswers() + "|" + i);
                        }
                    } else {
                        if (resultModel.getErrorAnswer() == null) {
                            resultModel.setErrorAnswer(node.getQuestionId().toString() + "_" + i);
                        } else {
                            resultModel.setErrorAnswer(resultModel.getAnswers() + "|" + i);
                        }
                    }
                }
                if (fillContents.size() == rightNum.get()) {
                    resultModel.setFlag(true);
                }
                break;
            case "Blank":
                if ("multi".equals(node.getChooseType())) {
                    String[] multianswers = node.getAnswer().split(",");
                    String[] submitmultianswers = submitAnswer.split(",");
                    for (int i = 0; i < multianswers.length; i++) {
                        if (submitmultianswers.length <= i) {
                            break;
                        }
                        if (multianswers[i].indexOf((submitmultianswers[i])) > -1) {
                            rightNum.getAndIncrement();
                            if (resultModel.getAnswers() == null) {
                                resultModel.setAnswers(node.getQuestionId().toString() + "_" + i);
                            } else {
                                resultModel.setAnswers(resultModel.getAnswers() + "|" + i);
                            }
                        } else {
                            if (resultModel.getErrorAnswer() == null) {
                                resultModel.setErrorAnswer(node.getQuestionId().toString() + "_" + i);
                            } else {
                                resultModel.setErrorAnswer(resultModel.getAnswers() + "|" + i);
                            }
                        }
                    }
                    if (node.getAnswer().split(",").length == rightNum.get()) {
                        resultModel.setFlag(true);
                    }
                } else {
                    if (node.getAnswer().equals(submitAnswer)) {
                        resultModel.setFlag(true);
                        rightNum.getAndIncrement();
                        resultModel.setAnswers(node.getQuestionId().toString());
                    } else {
                        resultModel.setErrorAnswer(node.getQuestionId().toString());
                    }
                }
                break;
            default:
                resultModel.setCount(0);
        }
        resultModel.setCount(rightNum.get());
        return resultModel;
    }

    private Integer returnNumByNodeSummary(Node node) {
        String multi = "multi";
        Integer count = 0;
        if (node == null || Strings.isNotEmpty(node.getText())) {
            return count;
        }
        switch (node.getType()) {
            //多重填空题 answer
            case "MulBlank":
                count = node.getAnswer().split(",").length;
                break;
            //匹配题
            case "Match":
                count = node.getAnswer().split(",").length;
                break;
            //imgFillContentList size
            case "ImgFill":
                count = node.getImgFillContentList().size();
                break;
            case "Blank":
                if (multi.equals(node.getChooseType())) {
                    count = node.getAnswer().split(",").length;
                } else {
                    count = 1;
                }
                break;
            case "Fill":
                count = node.getAnswer().split(",").length;
                break;
            //选择
           /* case "Choose":
                if("multi".equals(node.getChooseType())){
                    count = node.getAnswer().split(",").length;
                }else{
                    count = 1;
                }
                break;*/
            default:
                count = 1;
        }
        return count;
    }

    @ApiOperation(value = "删除个人考试纪录", notes = "")
    @DeleteMapping("/delExamByUserId/{userId}/{examId}")
    public ResponseEntity<Body<String>> delExamByUserId(
            @ApiParam("用户id") @PathVariable String userId,
            @ApiParam("试卷ID") @PathVariable String examId) {
        ExamInfo examInfo = examInfoService.getExamInfo(examId);
        if (examInfo == null) {
            return ResponseUtil.of(HttpStatus.OK, "试卷不存在");
        }
        userExamInfoService.delUserExamInfo(userId, examId);
        return ResponseUtil.ok("");
    }
}
