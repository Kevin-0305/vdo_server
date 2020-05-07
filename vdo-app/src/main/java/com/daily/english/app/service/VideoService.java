package com.daily.english.app.service;

import com.daily.english.app.condition.CollectCondition;
import com.daily.english.app.condition.VideoCondition;
import com.daily.english.app.domain.Collect;
import com.daily.english.app.domain.Subtitle;
import com.daily.english.app.domain.Video;
import com.daily.english.app.mapper.CollectMapper;
import com.daily.english.app.mapper.SubtitleMapper;
import com.daily.english.app.mapper.VideoMapper;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class VideoService {

    @Autowired
    private CollectService collectService;

    @Autowired
    private PraiseService praiseService;

    @Autowired
    private VideoMapper videoMapper;
    @Autowired
    private CollectMapper collectMapper;

    @Autowired
    private SubtitleMapper subtitleMapper;

    public void saveVideo(Video video) {
        video.setAddDate(new Date());
        videoMapper.insertVideo(video);
        List<Subtitle> subtitleList = video.getSubtitleList();
        if (CollectionUtils.isNotEmpty(subtitleList)) {
            for (Subtitle subtitle : subtitleList) {
                subtitle.setVideoId(video.getId());
                subtitleMapper.insertSubtitle(subtitle);
            }
        }
    }

    public void modifyVideo(Video video) {
        video.setModifyTime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
        videoMapper.updateVideo(video);

        subtitleMapper.deleteSubtitleByVideoId(video.getId());

        List<Subtitle> subtitleList = video.getSubtitleList();
        if (CollectionUtils.isNotEmpty(subtitleList)) {
            for (Subtitle subtitle : subtitleList) {
                subtitle.setVideoId(video.getId());
                subtitleMapper.insertSubtitle(subtitle);
            }
        }
    }
    public void updateVideoBrowse(Video video) {
        videoMapper.updateVideoBrowse(video);
    }

    public void updateVideoIsRelease(Video video) {
        videoMapper.updateVideoIsRelease(video);
    }

    public void updateVideoHot(Video video) {
        videoMapper.updateVideoHot(video);
    }

    public void updateVideoEditorPick(Video video) {
        videoMapper.updateVideoEditorPick(video);
    }

    public void removeVideo(Long id) {
        subtitleMapper.deleteSubtitleByVideoId(id);
        videoMapper.deleteVideoById(id);
    }

    public List<Video> getVideoListByHintTitle(String title) {
        List<Video> videoList = videoMapper.findVideoByHintTitle(title);
        if (CollectionUtils.isNotEmpty(videoList)) {
            for (Video video : videoList) {
                List<Subtitle> subtitleList = subtitleMapper.findSubtitleListByVideoId(video.getId());
                video.setSubtitleList(subtitleList);
            }
            return videoList;
        } else {
            return Lists.newArrayList();
        }
    }

    public Integer getVideoCount(VideoCondition videoCondition) {
        return videoMapper.findVideoCount(videoCondition);
    }

    public List<Video> getVideoListByPage(VideoCondition videoCondition) {
        List<Video> videoList = videoMapper.findVideoListByPage(videoCondition);
        if (CollectionUtils.isNotEmpty(videoList)) {
            for (Video video : videoList) {
                List<Subtitle> subtitleList = subtitleMapper.findSubtitleListByVideoId(video.getId());
                video.setSubtitleList(subtitleList);
            }
            return videoList;
        } else {
            return Lists.newArrayList();
        }
    }

    public Video getVideoById(Long id) {
        Video video = videoMapper.findVideoById(id);
        if (video == null) {
            return video;
        }
        List<Subtitle> subtitleList = subtitleMapper.findSubtitleListByVideoId(video.getId());
        video.setSubtitleList(subtitleList);
        return video;
    }

    public List<Video> getVideoListByIds(List<Long> ids) {
        if (CollectionUtils.isNotEmpty(ids)) {
            List<Video> videoList = videoMapper.findVideoListByIds(ids);
            if (CollectionUtils.isNotEmpty(videoList)) {
                for (Video video : videoList) {
                    List<Subtitle> subtitleList = subtitleMapper.findSubtitleListByVideoId(video.getId());
                    video.setSubtitleList(subtitleList);
                }
            }
            return videoList;
        } else {
            return Lists.newArrayList();
        }
    }
    public int getVideoCollectCount(Collect collect){
        return videoMapper.findVideoCollectCount(collect,backTable(collect.getType()));

    }
    private String backTable(String type){
        String table = "ed_collect";
        if("praise".equals(type)){
            table = "ed_praise";
        }
        return table;
    }
    public int saveVideoCollect(Collect collect) {
        if(videoMapper.findVideoCollectCount(collect,backTable(collect.getType())) > 0) {
            return 0;
        }
        collect.setCreateTime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
        if("collect".equals(collect.getType())){
            collect.setType("video");
            if(collect.getFlag() == 2) {
                collect.setType("challenge");
            }
            collectMapper.insertVideoCollect(collect);
        }else{
            videoMapper.insertVideoCollect(collect);
        }
        updateVideoCollect(collect,true);
        return 1;
    }
    public int removeVideoCollect(Collect collect) {
        if(videoMapper.findVideoCollectCount(collect,backTable(collect.getType())) == 0) {
            return 0;
        }
        videoMapper.deleteVideoCollect(collect,backTable(collect.getType()));
        updateVideoCollect(collect,false);
        return 1;
    }
    //ture 加
    private void updateVideoCollect(Collect collect,boolean flag){
        //更新视频收藏数
        Video video = videoMapper.findVideoById(collect.getVid().longValue());
        if(video == null) {
            return;
        }
        if("collect".equals(collect.getType())){
            Integer cnum = video.getCollect() == null ? 0 : video.getCollect();
            if(flag){
                video.setCollect(cnum += 1);
            }else {
                video.setCollect(cnum -= 1);
            }
        }
        if("praise".equals(collect.getType())){
            Integer praisenum = video.getPraise() == null ? 0 : video.getPraise();
            if(flag){
                video.setPraise(praisenum += 3);
            } else {
                video.setPraise(praisenum -= 3);
            }
        }
        videoMapper.updateVideo(video);
    }
    public List<Video> getVideoCollectListByPage(CollectCondition
                                                         collectCondition) {
        List<Collect> collectList = collectMapper.findVideoCollectListByPage(collectCondition);
        List<Video> videos =  Lists.newArrayList();
        for (Collect c : collectList) {
            Video video = videoMapper.findVideoById(c.getVid().longValue());
            if(video == null){
                continue;
            }
            video.setMapId(c.getMapId());
            video.setExamId(c.getExamId());
            videos.add(video);
        }
        return videos;
    }
    public int findVideoCollectListByPageCount(CollectCondition collect){
        return collectMapper.findVideoCollectListByPageCount(collect);
    }
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void removeBatchVideoCollect(Collect c) {
        collectMapper.deleteBatchVideoCollect(c.getUid(),c.getIds());
        //视频收藏总数减少
        c.getIds().forEach(e ->{
            updateVideoCollect(Collect.builder().vid(Integer.parseInt(e)).type(c.getType()).build(),false);
        });

    }
}
