package com.daily.english.app.service;

import com.daily.english.app.domain.BannerImage;
import com.daily.english.app.dto.PageSearchDto;
import com.daily.english.app.mapper.BannerImageMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class BannerImageService {

    @Autowired
    private BannerImageMapper bannerImageMapper;

    public List<BannerImage> selectBannerImageList(BannerImage bannerImage) {
        return bannerImageMapper.selectList(bannerImage);
    }

    /**
     * 查看今日范围内有效的启动图
     * @return
     */
    public List<BannerImage> selectBannerImageByNowTime() {
        return bannerImageMapper.selectBannerImageByNowTime();
    }

    public BannerImage selectBannerImageById(String id) {
        return bannerImageMapper.selectById(id);
    }

    public void saveBannerImage(BannerImage bannerImage) {
        if (null != bannerImage.getId() && bannerImage.getId() > 0) {
            bannerImage.setUpdateTime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
            bannerImageMapper.update(bannerImage);
        } else {
            bannerImage.setCreateTime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
            bannerImageMapper.insert(bannerImage);
        }
    }

    public PageInfo<BannerImage> selectBannerImagePage(PageSearchDto pageSearchDto) {
        PageHelper.startPage(pageSearchDto.getPageNo(), pageSearchDto.getPageSize());
        List<BannerImage> list = bannerImageMapper.selectListPage(pageSearchDto);
        PageInfo<BannerImage> pageInfo = new PageInfo(list, 5);
        return pageInfo;
    }

    public void deleteBannerImageById(String id) {
        bannerImageMapper.deleteById(id);
    }


}
