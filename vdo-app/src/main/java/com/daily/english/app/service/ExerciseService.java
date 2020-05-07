package com.daily.english.app.service;

import com.daily.english.app.domain.*;
import com.daily.english.app.dto.ExamRecordDto;
import com.daily.english.app.dto.ExerciseDto;
import com.daily.english.app.mapper.ExamRecordMapper;
import com.daily.english.app.mapper.ExerciseMapper;
import com.daily.english.app.vo.ExerciseAnswerVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class ExerciseService {

    @Autowired
    private ExerciseMapper exerciseMapper;
    @Autowired
    private ExamRecordMapper examRecordMapper;

    public List<Exercise> selectExerciseList(Exercise exercise) {
        return exerciseMapper.selectExerciseList(exercise);
    }


    /**
     * 根据用户ID查询视频习题信息，并返回视频习题和用户提交记录
     *
     * @param examRecord
     */
    public List<ExamRecord> selectExerciseByUserIdAndVid(ExamRecord examRecord) {
       /* 不知道干嘛的注掉    
        * Exercise exercise = new Exercise();
        exercise.setVid(examRecordDto.getVid());
        List<Exercise> exercises = exerciseMapper.selectExerciseList(exercise);
        ExamRecord examRecord = new ExamRecord();
        examRecord.setUid(examRecordDto.getUid());
        examRecord.setVid(examRecordDto.getVid());*/
        List<ExamRecord> examRecords = examRecordMapper.selectExamRecordList(examRecord);
        return examRecords;
    }

    /**
     * 用户提交视频习题答案信息保存
     *
     * @param answer
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void examRecordSave(ExerciseAnswerVo answer) throws Exception{
        String nowTime = DateTime.now().toString("yyyy-MM-dd HH:mm:ss");
        List<AnswerEntry> list = answer.getAnswers();
        //可以改成SQL循环
        list.forEach(answerentry -> {
            ExamRecord examRecord = ExamRecord.builder().eid(Integer.parseInt(answerentry.getId())).createTime(nowTime)
                    .uid(Integer.parseInt(answer.getUserId()))
                    .userAnswer(answerentry.getAnswer())
                    .vid(Integer.parseInt(answer.getVideoId())).build();
            examRecordMapper.insertExamRecord(examRecord);
        });
    }

    public Exercise selectExerciseById(String id) {
        return exerciseMapper.selectExerciseById(id);
    }

    public void saveExercise(Exercise exercise) {
        if (null != exercise.getId() && exercise.getId() > 0) {
            exerciseMapper.updateExercise(exercise);
        } else {
            exerciseMapper.insertExercise(exercise);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void batchSaveExercise(List<Exercise> exerciseList) {
        exerciseList.forEach(exercise -> {
            if (null != exercise.getId() && exercise.getId() > 0) {
                exerciseMapper.updateExercise(exercise);
            } else {
                exerciseMapper.insertExercise(exercise);
            }
        });
    }

    public PageInfo<Exercise> selectExercisePage(ExerciseDto exerciseDto) {
        PageHelper.startPage(exerciseDto.getPageNo(), exerciseDto.getPageSize());
        List<Exercise> list = exerciseMapper.selectExercisePage(exerciseDto);
        PageInfo<Exercise> pageInfo = new PageInfo(list, 5);
        return pageInfo;
    }

    public void deleteExerciseById(String id) {
        exerciseMapper.deleteExerciseById(id);
    }
    //无选项不添加
    public List<Node> exercisesToNodes(List<Exercise> exercises){
        List<Node> nodes = new ArrayList<Node>();
        for(Exercise e:exercises){
            List<String> temp = new ArrayList<String>();
            if(StringUtils.isNotEmpty(e.getChooseA())){
            	temp.add(e.getChooseA());
            }   if(StringUtils.isNotEmpty(e.getChooseB())){
            	temp.add(e.getChooseB());
            }
            if(StringUtils.isNotEmpty(e.getChooseC())){
            	temp.add(e.getChooseC());
            }
            if(StringUtils.isNotEmpty(e.getChooseD())){
            	temp.add(e.getChooseD());
            }
            Node node = new Node(e.getExplan(),e.getId().longValue(),"sign",e.getQuestion(),temp,e.getAnswer());
            nodes.add(node);
        }
        return nodes;
    }

}
