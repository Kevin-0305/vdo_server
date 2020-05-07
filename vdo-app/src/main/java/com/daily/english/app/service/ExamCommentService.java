package com.daily.english.app.service;

import com.daily.english.app.condition.ExamCommentCondition;
import com.daily.english.app.domain.ExamComment;
import com.daily.english.app.domain.ExamCommentPraise;
import com.daily.english.app.domain.User;
import com.daily.english.app.domain.UserExamInfo;
import com.daily.english.app.mapper.ExamCommentMapper;
import com.daily.english.app.mapper.UserExamInfoMapper;
import com.daily.english.app.mapper.UserMapper;
import com.daily.english.app.util.DateUtil;
import com.daily.english.app.vo.ExamCommentVo;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class ExamCommentService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ExamCommentMapper examCommentMapper;
    @Autowired
    private UserExamInfoMapper userExamInfoMapper;

    public List<ExamComment> findCommentListByExam(ExamCommentCondition
                                                         examCommentCondition,Long userId) {
        List<ExamComment> examComments  = examCommentMapper.findCommentListByExam(examCommentCondition);
        String dateFormatString = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatString);
        for (ExamComment e: examComments) {
            ExamCommentPraise  examCommentPraise= ExamCommentPraise.builder().userId(userId).commentId(e.getId()).build();
            int count = examCommentMapper.queryPraiseCountByUserId(examCommentPraise);
            if(count == 0){
                   e.setPraiseInfo(false);
            }else{
                    e.setPraiseInfo(true);
            }
           e.setDistanceTime(DateUtil.dateDiff(e.getCreateTime(),dateFormat.format(new Date()),dateFormatString));
            User user = userMapper.findUserById(""+e.getUserId());
            e.setUserName(user.getNickname());
            e.setUserImg(user.getUserImg());
        }
        return examComments;
    }
    public int findCommentListByExamCount(ExamCommentCondition examCommentCondition){
        return examCommentMapper.findCommentListByExamCount(examCommentCondition);
    }
    public int queryPraiseCount(ExamCommentPraise examCommentPraise){
        return examCommentMapper.queryPraiseCountByUserId(examCommentPraise);
    }
    public void insertExamComment(ExamCommentVo examCommentVo){
        examCommentVo.setCreateTime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
        examCommentMapper.insert(examCommentVo);
        //更新试卷也为最新的
        UserExamInfo uei = userExamInfoMapper.findUserExamInfoByExamId(""+examCommentVo.getUserId(),examCommentVo.getExamId());
        uei.setUpdateTime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
        userExamInfoMapper.modifyUserExamInfo(uei);
    }
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public int insertPraise(ExamCommentPraise examCommentPraise){
        int count = examCommentMapper.queryPraiseCountByUserId(examCommentPraise);
        if(count > 0){
            return 0;
        }
        ExamComment ec =examCommentMapper.queryExamCommentById(examCommentPraise.getCommentId());
        if(ec != null){
            ec.setPraise(ec.getPraise()+1);
            examCommentMapper.updateComment(ec);
        }
        examCommentMapper.insertPraise(examCommentPraise);
        return 1;
    }

}
