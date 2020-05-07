package com.daily.english.app.domain;

import java.io.Serializable;
import java.util.List;

import com.daily.english.app.json.EventNewsAttaJson;
import com.daily.english.app.vo.YjEventInfoVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * class_name: YjEventNews
 * package: com.vdoenglish.core.entity
 * describe: 活动新闻
 * creat_user: liuzd@chinadailyhk.com
 * creat_date: 2019-08-13
 * creat_time: 15:39
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class YjEventNews implements Serializable {
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
     * 标题
     */
    @ApiModelProperty(value = "标题", required = false)
    private String titleCh;
    /**
     *
     */
    @ApiModelProperty(value = "标题繁体", required = false)
    private String titleHk;
    /**
     *
     */
    @ApiModelProperty(value = "标题英文", required = false)
    private String titleEn;
    /**
     *
     */
    @ApiModelProperty(value = "内容繁体", required = false)
    private String contentHk;
    /**
     * 内容
     */
    @ApiModelProperty(value = "内容", required = false)
    private String contentCh;
    /**
     *
     */
    @ApiModelProperty(value = "内容英文", required = false)
    private String contentEn;
    /**
     * 活动ID
     */
    @ApiModelProperty(value = "活动ID", required = false)
    private String eventId;
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
     * 图片地址
     */
    @ApiModelProperty(value = "图片地址", required = false)
    private String imgUrl;
    /**
     * 新闻附件对象
     */
    @ApiModelProperty(value = "新闻附件对象", required = false)
    private EventNewsAttaJson newsAttaJson;

    /**
     * 活动集合-用于下拉列表-非新闻DB字段
     */
    @ApiModelProperty(value = "活动集合-用于下拉列表非新闻DB字段", required = false)
    private List<YjEventInfoVo> eventInfoList;
}
