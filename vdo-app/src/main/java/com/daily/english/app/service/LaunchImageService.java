package com.daily.english.app.service;

import com.daily.english.app.domain.LaunchImage;
import com.daily.english.app.dto.PageSearchDto;
import com.daily.english.app.mapper.LaunchImageMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class LaunchImageService {

    @Autowired
    private LaunchImageMapper launchImageMapper;

    public List<LaunchImage> selectLaunchImageList(LaunchImage launchImage) {
        return launchImageMapper.selectList(launchImage);
    }

    /**
     * 查看今日范围内有效的启动图
     * @return
     */
    public List<LaunchImage> appLaunchImageData() {
        return launchImageMapper.appLaunchImageData();
    }

    public LaunchImage selectLaunchImageById(String id) {
        return launchImageMapper.selectById(id);
    }

    public void saveLaunchImage(LaunchImage launchImage) {
        if (null != launchImage.getId() && launchImage.getId() > 0) {
            launchImage.setUpdateTime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
            launchImageMapper.update(launchImage);
        } else {
            launchImage.setCreateTime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
            launchImageMapper.insert(launchImage);
        }
    }

    public PageInfo<LaunchImage> selectLaunchImagePage(PageSearchDto pageSearchDto) {
        PageHelper.startPage(pageSearchDto.getPageNo(), pageSearchDto.getPageSize());
        List<LaunchImage> list = launchImageMapper.selectListPage(pageSearchDto);
        PageInfo<LaunchImage> pageInfo = new PageInfo(list, 5);
        return pageInfo;
    }

    public void deleteLaunchImageById(String id) {
        launchImageMapper.deleteById(id);
    }


}
