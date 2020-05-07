package com.daily.english.app.domain;

import java.io.Serializable;

import com.daily.english.app.json.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * class_name: YjEventInfo
 * package: com.vdoenglish.core.entity
 * describe: 活动信息
 * creat_user: liuzd@chinadailyhk.com
 * creat_date: 2019-08-13
 * creat_time: 15:39
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class YjEventInfo implements Serializable {
    /**
     * @Fields serialVersionUID:序列化ID
     */
    private static final long serialVersionUID = 1L;
    /**
     * ID
     */
    @ApiModelProperty(value = "ID", required = false)
    private Integer id;
    /**
     * 中文活动名称
     */
    @ApiModelProperty(value = "中文活动名称", required = false)
    private String nameCh;
    /**
     * 繁体活动名称
     */
    @ApiModelProperty(value = "繁体活动名称", required = false)
    private String nameHk;
    /**
     * 英文活动名称
     */
    @ApiModelProperty(value = "英文活动名称", required = false)
    private String nameEn;
    /**
     * banner图地址
     */
    @ApiModelProperty(value = "banner图地址", required = false)
    private String bannerUrl;
    /**
     * 正方图地址
     */
    @ApiModelProperty(value = "正方图地址", required = false)
    private String bannerSquareUrl;
//    /**
//     * 参赛须知
//     */
//    @ApiModelProperty(value = "参赛须知", required = false)
//    private PicJson signUpJson;
    /**
     * PDF文件信息
     */
    @ApiModelProperty(value = "须知PDF文件信息", required = false)
    private PdfJson pdfJson;
    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人", required = false)
    private String createUser;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", required = false)
    private String createTime;
    /**
     * 修改人
     */
    @ApiModelProperty(value = "修改人", required = false)
    private String updateUser;
    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间", required = false)
    private String updateTime;
    /**
     * 中文简介
     */
    @ApiModelProperty(value = "中文简介", required = false)
    private String descriptionCh;
    /**
     * 繁体简介
     */
    @ApiModelProperty(value = "繁体简介", required = false)
    private String descriptionHk;
    /**
     * 英文简介
     */
    @ApiModelProperty(value = "英文简介", required = false)
    private String descriptionEn;
    /**
     * 比赛简介视频封面图
     */
    @ApiModelProperty(value = "比赛简介视频封面图", required = false)
    private String descriptionPicUrl;
    /**
     * 比赛简介视频地址
     */
    @ApiModelProperty(value = "比赛简介视频地址", required = false)
    private String descriptionVideoUrl;
    /**
     * 历届比赛图片
     */
    @ApiModelProperty(value = "历届比赛图片", required = false)
    private CompetePicJson picJson;
    /**
     * 视频配置
     */
    @ApiModelProperty(value = "视频配置", required = false)
    private VideoJson videoJson;
    /**
     * 赞助商配置
     */
    @ApiModelProperty(value = "赞助商配置", required = false)
    private SponsorsJson sponsorsJson;
    /**
     * 嘉宾配置
     */
    @ApiModelProperty(value = "嘉宾配置", required = false)
    private GuestJson guestsJson;
    /**
     * 选手感言配置
     */
    @ApiModelProperty(value = "选手感言配置", required = false)
    private PlayersJson playersJson;
    /**
     * 评委结构配置
     */
    @ApiModelProperty(value = "评委结构配置", required = false)
    private PicJson juryOrganJson;
    /**
     * 状态
     */
    @ApiModelProperty(value = "状态：-1.删除 0.默认 1.上架 2.下架", required = false)
    private Integer status;
    /**
     * 活动开始时间
     */
    @ApiModelProperty(value = "活动开始时间", required = false)
    private String startTime;
    /**
     * 活动结束时间
     */
    @ApiModelProperty(value = "活动结束时间", required = false)
    private String endTime;
}
