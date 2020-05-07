package com.daily.english.app.vo;

import com.daily.english.app.domain.Subtitle;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VideoVo {
    @ApiModelProperty(value = "主键id", required = true, example = "0:01:09")
    private Long id;

    @ApiModelProperty(value = "标题", required = true, example = "Young explorers: a brief history of writing")
    private String title;

    @ApiModelProperty(value = "浏览次数", required = true, example = "265")
    private Integer browse;

    @ApiModelProperty(value = "点赞次数", required = true, example = "6")
    private Integer praise;
    @ApiModelProperty(value = "当前用户是否点赞", required = true, example = "0")
    private Integer isPraise;
    @ApiModelProperty(value = "收藏次数", required = true, example = "0")
    private Integer collect;
    @ApiModelProperty(value = "当前用户是否收藏", required = true, example = "0")
    private Integer isCollect;
    @ApiModelProperty(value = "类型", required = true, example = "13")
    private String type;

    @ApiModelProperty(value = "视频短链接", required = true, example = "https://youtu.be/j7nM3YOwu00")
    private String videoUrl;

    @ApiModelProperty(value = "下载链接", required = true, example = "")
    private String downloadUrl;

    @ApiModelProperty(value = "国内云视频服务器地址", required = true, example = "http://1252065688.vod2.myqcloud.com/d7dc3e4avodgzp1252065688/92e2d0fd7447398154708757585/RgrsNPZVRvUA.mp4")
    private String cloudUrl;

    @ApiModelProperty(value = "图片路径", required = true, example = "http://106.52.102.224/vdocms/videoImg/15561826744361343.jpg")
    private String imgUrl;

    @ApiModelProperty(value = "视频截图链接", required = true, example = "http://106.52.102.224/vdocms/videoThumb/15561826744361343.jpg")
    private String videoThumb;

    @ApiModelProperty(value = "视频截图链接", required = true, example = "http://106.52.102.224/vdocms/videoThumbBigger/15561826744361343.jpg")
    private String videoThumbBigger;

    @ApiModelProperty(value = "编辑者", required = true, example = "kin")
    private String editor;

    @ApiModelProperty(value = "是否热门", required = true, example = "0")
    private String hot;

    @ApiModelProperty(value = "每日精选", required = true, example = "")
    private String editorPick;

    @ApiModelProperty(value = "是否发布", required = true, example = "1")
    private Integer isRelease;

    @ApiModelProperty(value = "是否可看.0登录可看，1不登录可看", required = true, example = "0:01:09")
    private String isOpen;

    @ApiModelProperty(value = "是否测试.0没有测试，1有测试", required = true, example = "0:01:09")
    private String isQuiz;

    @ApiModelProperty(value = "创建日期", required = true, example = "2019-04-25 17:07:32")
    private String addDate;

    @ApiModelProperty(value = "总时长", required = true, example = "05:59")
    private String timeLength;

    @ApiModelProperty(value = "状态", required = true, example = "1")
    private Integer status;

    @ApiModelProperty(value = "摘要", required = true, example = "")
    private String remark;

    @ApiModelProperty(value = "难度级别", required = true, example = "2")
    private String level;

    private String pubTime;

    private String lastEditor;

    private String modifyTime;

    private List<Subtitle> subtitleList;

    @ApiModelProperty(value = "mapId", required = false)
    private Long mapId;
    @ApiModelProperty(value = "examId 试卷ID", required = false)
    private String examId;
}
