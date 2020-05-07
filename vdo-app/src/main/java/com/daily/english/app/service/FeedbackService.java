package com.daily.english.app.service;

import com.daily.english.app.domain.Feedback;
import com.daily.english.app.dto.FeedbackDto;
import com.daily.english.app.mapper.FeedbackMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class FeedbackService {


    @Autowired
    private FeedbackMapper feedbackMapper;

    /**
     * 问题反馈保存/更新
     * @param feedback
     */
    public void saveFeedback(Feedback feedback) {
        if (null != feedback.getId() && feedback.getId() > 0){
            feedbackMapper.updateFeedback(feedback);
        }else {
            feedback.setCreateTime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
            feedbackMapper.insertFeedback(feedback);
        }
    }


    public void deleteFeedback(String id) {
        feedbackMapper.deleteById(id);
    }

    public List<Feedback> selectFeedbackList(Feedback goods) {
        return feedbackMapper.selectList(goods);
    }

    public Feedback selectFeedbackById(String id) {
        return feedbackMapper.selectById(id);
    }

    /**
     * 用户反馈分页查询
     * @param feedbackDto
     * @return
     */
    public PageInfo<Feedback> selectFeedbackPage(FeedbackDto feedbackDto){
        PageHelper.startPage(feedbackDto.getPageNo(), feedbackDto.getPageSize());
        List<Feedback> list = feedbackMapper.selectListPage(feedbackDto);
        PageInfo<Feedback> pageInfo = new PageInfo(list, 5);
        return pageInfo;
    }


}
