package com.daily.english.app.controller;


import com.daily.english.app.domain.*;
import com.daily.english.app.dto.ExerciseDto;
import com.daily.english.app.service.ExerciseService;
import com.daily.english.app.service.UserService;
import com.daily.english.app.service.UserStudyService;
import com.daily.english.app.util.Body;
import com.daily.english.app.util.ResponseUtil;
import com.daily.english.app.vo.ExerciseAnswerVo;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 视频习题
 */
@Api(tags = "视频习题")
@Slf4j
@RestController
@RequestMapping(value = "/exercise")
public class ExerciseController {

    @Autowired
    private UserService userService;
    @Autowired
    private ExerciseService exerciseService;
    @Autowired
    private UserStudyService userStudyeService;

    @ApiOperation(value = "获取视频习题列表", notes = "获取视频习题列表(分页)")
    @GetMapping("/list")
    public ResponseEntity<Body<PageInfo<Exercise>>> getExercisePage(ExerciseDto exerciseDto) {
        return ResponseUtil.ok(exerciseService.selectExercisePage(exerciseDto));
    }

    @ApiOperation(value = "根据视频ID获取习题列表", notes = "根据视频ID获取习题列表")
    @GetMapping("/exercisesByVid")
    public ResponseEntity<Body<List<Exercise>>> getxercisesByVid(@ApiParam("视频ID") @RequestParam String vid) {
        Exercise exercise = new Exercise();
        exercise.setVid(Integer.parseInt(vid));
        List<Exercise> exercises = exerciseService.selectExerciseList(exercise);
        return ResponseUtil.ok(exercises);
    }

    @ApiOperation(value = "移动端根据视频ID获取习题列表", notes = "根据视频ID获取习题列表和已答过的")
    @GetMapping("/queryExercisesByVid/{id}/{userId}")
    public ResponseEntity<Body<ExerciseAnswer>> queryExercisesByVid(@ApiParam("视频ID") @PathVariable String id,
                                                                    @ApiParam("用户ID") @PathVariable String userId) {
        Exercise exercise = new Exercise();
        exercise.setVid(Integer.parseInt(id));
        List<Exercise> exercises = exerciseService.selectExerciseList(exercise);
        List<Node> nodes= exerciseService.exercisesToNodes(exercises);
        //获取之前答案
        User user = userService.findUserById(userId);
        ExerciseAnswer exerciseAnswer = ExerciseAnswer.builder().exerciseList(nodes).build();
        if(user != null){
        	 ExamRecord eRecord = ExamRecord.builder().uid(Integer.parseInt(user.getId())).vid(Integer.parseInt(id)).build();
        	 List<ExamRecord> examRecords = exerciseService.selectExerciseByUserIdAndVid(eRecord);
        	 exerciseAnswer.setExamRecords(examRecords);
        }
        return ResponseUtil.ok(exerciseAnswer);
    }


    @ApiOperation(value = "新增/更新视频习题", notes = "新增/更新视频习题, 不含id为新增, 含id为更新")
    @PostMapping("/save")
    public ResponseEntity<Body<Void>> saveExercise(
            @ApiParam("视频习题体") @RequestBody Exercise exercise) {
        exerciseService.saveExercise(exercise);
        return ResponseUtil.ok();
    }

    @ApiOperation(value = "批量新增/更新视频习题", notes = "批量新增/更新视频习题")
    @PostMapping("/batchSave")
    public ResponseEntity<Body<Void>> batchSaveMap(
            @ApiParam("习题集合") @RequestBody List<Exercise> exerciseList) {
            exerciseService.batchSaveExercise(exerciseList);
        return ResponseUtil.ok();
    }

    @ApiOperation(value = "获取视频习题详情", notes = "通过视频习题id获得视频习题详情")
    @GetMapping("/info/{id}")
    public ResponseEntity<Body<Exercise>> getExercise(
            @ApiParam("视频习题ID") @PathVariable String id) {
        Exercise exercise = exerciseService.selectExerciseById(id);
        return ResponseUtil.ok(exercise);
    }

    @ApiOperation(value = "删除视频习题", notes = "删除视频习题（数据硬删）")
    @DeleteMapping("/deleteExercise/{id}")
    public ResponseEntity<Body<Void>> deleteEventInfo(@ApiParam("新闻ID") @PathVariable String id) {
        exerciseService.deleteExerciseById(id);
        return ResponseUtil.ok();
    }

    @ApiOperation(value = "提交习题答案", notes = "提交习题答案 答案")
    @PostMapping("/submitExerciseAnswer")
    public ResponseEntity<Body<java.util.Map<String,Object>>> submitExerciseAnswer(@ApiParam("习题答案") @RequestBody ExerciseAnswerVo answer)  {
        try {
            ExamRecord eRecord = ExamRecord.builder().uid(Integer.parseInt(answer.getUserId())).vid(Integer.parseInt(answer.getVideoId())).build();
            if(exerciseService.selectExerciseByUserIdAndVid(eRecord).size() > 0){
                return ResponseUtil.of(HttpStatus.OK, "你已提交过习题答案");
            }
        	exerciseService.examRecordSave(answer);
            int rightNum = checkAnswer(answer.getAnswers(),Long.parseLong(answer.getVideoId()));
            java.util.Map<String,Object> map = Maps.newHashMap();
            map.put("rightNum",rightNum);
            map.put("bamboo",100);
            //更新做题数
            UserStudy userStudy = userStudyeService.findUserMapById(answer.getUserId());
            userStudy.setQuestionAmoutToday(userStudy.getQuestionAmoutToday() + answer.getAnswers().size());
            userStudy.setQuestionAmoutAll(userStudy.getQuestionAmoutAll() + answer.getAnswers().size());
            userStudyeService.modifyUserStudy(userStudy);

            return ResponseUtil.ok(map);
        } catch (Exception e) {
            return ResponseUtil.badRequest(e.toString());
        }
    }
     //返回对的题数
    private Integer checkAnswer(List<AnswerEntry> userAnswers,Long vid){
        int rightNum = 0;
        //习题正确答案
        List<Exercise> exercises = exerciseService.selectExerciseList(Exercise.builder().vid(vid.intValue()).build());
        for(Exercise e:exercises){
            for(AnswerEntry userAnswer: userAnswers){
                if(e.getId().equals(userAnswer.getId())  &&  userAnswer.getAnswer().equals(e.getAnswer())){
                    rightNum += 1;
                }
            }
        }
        return rightNum;
    }

}
