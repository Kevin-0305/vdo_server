package com.daily.english.app.service;

import com.daily.english.app.domain.YjEventNews;
import com.daily.english.app.dto.YjEventNewsDto;
import com.daily.english.app.mapper.YjEventNewsMapper;
import com.daily.english.app.vo.YjEventNewsVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class YjEventNewsService {

    @Autowired
    private YjEventNewsMapper eventNewsMapper;

    public List<YjEventNews> selectEventNewsList(YjEventNews eventNews) {
        return eventNewsMapper.findEventNewsList();
    }

    public YjEventNews selectEventNewsById(String id) {
        return eventNewsMapper.findEventNewsById(id);
    }

    public String saveEventNews(YjEventNews eventNews) {
        if (null != eventNews.getId() && eventNews.getId() > 0) {
            eventNews.setUpdateTime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
            eventNewsMapper.updateEventNews(eventNews);
        } else {
            eventNews.setCreateTime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
            eventNewsMapper.insertEventNews(eventNews);
        }
        return eventNews.getId().toString();
    }

    public PageInfo<YjEventNewsVo> selectEventNewsPage(YjEventNewsDto yjEventNewsDto) {
        PageHelper.startPage(yjEventNewsDto.getPageNo(), yjEventNewsDto.getPageSize());
        List<YjEventNewsVo> list = eventNewsMapper.findEventNewsPage(yjEventNewsDto);
        PageInfo<YjEventNewsVo> pageInfo = new PageInfo(list, 5);
        return pageInfo;
    }

    public void deleteEventNewsById(String id) {
        eventNewsMapper.deleteEventNewsById(id);
    }


}
