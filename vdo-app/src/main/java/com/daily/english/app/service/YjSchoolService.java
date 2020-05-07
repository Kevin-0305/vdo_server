package com.daily.english.app.service;

import com.daily.english.app.domain.YjSchool;
import com.daily.english.app.dto.PageSearchDto;
import com.daily.english.app.mapper.YjSchoolMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class YjSchoolService {

    @Autowired
    private YjSchoolMapper yjSchoolMapper;

    public List<YjSchool> selectYjSchoolList(YjSchool yjSchool) {
        return yjSchoolMapper.selectList(yjSchool);
    }

    public YjSchool selectYjSchoolById(String id) {
        return yjSchoolMapper.selectById(id);
    }

    public String saveYjSchool(YjSchool yjSchool) {
        if (yjSchool.getDistinctHk().equals("香港及離島")) {
            yjSchool.setDistinctEn("HK and Islands");
            yjSchool.setDistinct("香港及離島");
        }
        if (yjSchool.getDistinctHk().equals("九龍")) {
            yjSchool.setDistinctEn("Kowloon");
            yjSchool.setDistinctHk("九龍");
            yjSchool.setDistinct("九龍");
        }
        if (yjSchool.getDistinctHk().equals("新界")) {
            yjSchool.setDistinctEn("New Territories");
            yjSchool.setDistinctHk("新界");
            yjSchool.setDistinct("新界");
        }
        if (null != yjSchool.getId() && yjSchool.getId() > 0) {
            yjSchoolMapper.update(yjSchool);
        } else {
            yjSchoolMapper.insert(yjSchool);
        }
        return yjSchool.getId().toString();
    }

    public PageInfo<YjSchool> selectListPage(PageSearchDto pageSearchDto) {
        PageHelper.startPage(pageSearchDto.getPageNo(), pageSearchDto.getPageSize());
        List<YjSchool> list = yjSchoolMapper.selectListPage(pageSearchDto);
        PageInfo<YjSchool> pageInfo = new PageInfo(list, 5);
        return pageInfo;
    }

    public void deleteById(String id) {
        yjSchoolMapper.deleteById(id);
    }


}
