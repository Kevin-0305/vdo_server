package com.daily.english.app.service;

import com.daily.english.app.domain.YjEventInfo;
import com.daily.english.app.dto.YjEventInfoDto;
import com.daily.english.app.mapper.YjEventInfoMapper;
import com.daily.english.app.vo.YjEventInfoVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class YjEventInfoService {

    @Autowired
    private YjEventInfoMapper eventInfoMapper;

    public List<YjEventInfo> selectEventInfoList(YjEventInfo yjEventInfo) {
        return eventInfoMapper.findEventInfoList(yjEventInfo);
    }

    public List<YjEventInfoVo> selectEventInfoVoList(YjEventInfoDto yjEventInfoDto) {
        return eventInfoMapper.selectEventInfoList(yjEventInfoDto);
    }

    public YjEventInfo selectEventInfoById(String id) {
        return eventInfoMapper.findEventInfoById(id);
    }

    public void saveEventInfo(YjEventInfo yjEventInfo) {
        if (null != yjEventInfo.getId() && yjEventInfo.getId() > 0) {
            yjEventInfo.setUpdateTime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
            eventInfoMapper.updateEventInfo(yjEventInfo);
        } else {
            yjEventInfo.setCreateTime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
            eventInfoMapper.insertEventInfo(yjEventInfo);
        }

    }

    public void modifyEventInfo(YjEventInfo yjEventInfo) {
        yjEventInfo.setUpdateTime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
        eventInfoMapper.updateEventInfo(yjEventInfo);
    }

    public PageInfo<YjEventInfoVo> findEventInfoPage(YjEventInfoDto eventInfoDto) {
        PageHelper.startPage(eventInfoDto.getPageNo(), eventInfoDto.getPageSize());
        List<YjEventInfoVo> list = eventInfoMapper.selectEventInfoPage(eventInfoDto);
        PageInfo<YjEventInfoVo> pageInfo = new PageInfo(list, 5);
        return pageInfo;
    }

    /**
     * @param id 更新主键,status:状态：-1.删除 0.默认 1.上架 2.下架,
     * @class_name
     * @describe: TODO
     * @creat_user: liuzd@chinadailyhk.com
     * @creat_date: 2019-08-13
     * @creat_time: 10:44
     **/
    public void updateEventInfoStatus(String id, Integer status) {
        eventInfoMapper.updateEventInfoStatus(id, status);
    }

}
