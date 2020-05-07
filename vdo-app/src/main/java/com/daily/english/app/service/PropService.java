package com.daily.english.app.service;

import com.daily.english.app.domain.Prop;
import com.daily.english.app.dto.PageSearchDto;
import com.daily.english.app.mapper.PropMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class PropService {

    @Autowired
    private PropMapper propMapper;

    public List<Prop> selectPropList(Prop prop) {
        return propMapper.selectList(prop);
    }

    public Prop selectPropById(String id) {
        return propMapper.selectById(id);
    }

    public void saveProp(Prop prop) {
        if (null != prop.getId() && prop.getId() > 0) {
            prop.setUpdateTime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
            propMapper.updateSelective(prop);
        } else {
            prop.setCreateTime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
            propMapper.insertSelective(prop);
        }
    }

    public PageInfo<Prop> selectPropPage(PageSearchDto pageSearchDto) {
        PageHelper.startPage(pageSearchDto.getPageNo(), pageSearchDto.getPageSize());
        List<Prop> list = propMapper.selectPageList(pageSearchDto);
        PageInfo<Prop> pageInfo = new PageInfo(list, 5);
        return pageInfo;
    }

    public void deletePropById(String id) {
        propMapper.deleteById(id);
    }


}
