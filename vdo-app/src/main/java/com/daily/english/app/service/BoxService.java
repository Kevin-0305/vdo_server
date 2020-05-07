package com.daily.english.app.service;

import com.daily.english.app.domain.Box;
import com.daily.english.app.domain.Prop;
import com.daily.english.app.dto.PageSearchDto;
import com.daily.english.app.json.DropJson;
import com.daily.english.app.mapper.BoxMapper;
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
public class BoxService {

    @Autowired
    private BoxMapper boxMapper;
    @Autowired
    private PropMapper propMapper;

    public List<Box> selectBoxList(Box box) {
        return boxMapper.selectList(box);
    }

    public Box selectBoxById(String id) {
        Box box = boxMapper.selectById(id);
        if (null != box) {
            for (DropJson.DropRecord dropRecord : box.getDropJson().getContent()) {
                Prop prop = propMapper.selectById(dropRecord.getPropId());
                dropRecord.setProp(prop);
            }
        }
        return box;
    }

    public void saveBox(Box box) {
        if (null != box.getId() && box.getId() > 0) {
            box.setUpdateTime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
            boxMapper.updateSelective(box);
        } else {
            box.setCreateTime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
            boxMapper.insertSelective(box);
        }
    }

    public PageInfo<Box> selectBoxPage(PageSearchDto pageSearchDto) {
        PageHelper.startPage(pageSearchDto.getPageNo(), pageSearchDto.getPageSize());
        List<Box> list = boxMapper.selectPageList(pageSearchDto);
        if (null != list) {
            for (Box box : list) {
                for (DropJson.DropRecord dropRecord : box.getDropJson().getContent()) {
                    Prop prop = propMapper.selectById(dropRecord.getPropId());
                    dropRecord.setProp(prop);
                }
            }
        }
        PageInfo<Box> pageInfo = new PageInfo(list, 5);
        return pageInfo;
    }

    public void deleteBoxById(String id) {
        boxMapper.deleteById(id);
    }


}
