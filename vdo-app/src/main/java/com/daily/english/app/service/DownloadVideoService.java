package com.daily.english.app.service;

import com.daily.english.app.condition.DownloadVideoCondition;
import com.daily.english.app.domain.DownloadVideo;
import com.daily.english.app.domain.Video;
import com.daily.english.app.mapper.DownloadVideoMapper;
import com.daily.english.app.mapper.VideoMapper;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * @author zhc
 **/
@Slf4j
@Service
public class DownloadVideoService {

    @Autowired
    private DownloadVideoMapper downloadVideoMapper;
    @Autowired
    private VideoMapper videoMapper;

    public void deleteDownloadVideoByIds(Integer userId,List<String> ids) {
        downloadVideoMapper.deleteBatchDownloadVideo(userId,ids);
    }

    public List<Video> getVideoCollectListByPage(DownloadVideoCondition dv){
        List<DownloadVideo> dvlist = downloadVideoMapper.findDownloadVideoListByPage(dv);
        List<Video> videoList = Lists.newArrayList();
        dvlist.forEach(download ->{
            videoList.add(videoMapper.findVideoById(Long.parseLong(download.getVideoId())));
        });
        return videoList;
    }
    public int getVideoCollectListByPageCount(DownloadVideoCondition dv){
        return downloadVideoMapper.findDownloadVideoListByPageCount(dv);
    }
    public void saveVideoDownload(DownloadVideo downloadVideo) {
        downloadVideo.setCreateTime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
        downloadVideoMapper.insert(downloadVideo);
    }
}
