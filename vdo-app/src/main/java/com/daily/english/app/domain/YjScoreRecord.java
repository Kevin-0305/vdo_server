package com.daily.english.app.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * class_name: YjScoreRecord
 * package: com.vdoenglish.core.entity
 * describe: 评分记录表
 * creat_user: liuzd@chinadailyhk.com
 * creat_date: 2019-08-20
 * creat_time: 15:39
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class YjScoreRecord implements Serializable {
    /**
     * @Fields serialVersionUID:序列化ID
     */
    private static final long serialVersionUID = 1L;
    /**
     * ID
     */
    @ApiModelProperty(value = "主键ID", required = false)
    private Integer id;
    /**
     * 评分用户ID
     */
    @ApiModelProperty(value = "评分用户ID-提交时必填", required = false)
    private String userId;
    /**
     * 报名信息ID
     */
    @ApiModelProperty(value = "报名信息ID-提交时必填", required = false)
    private String bmId;
    /**
     * 评分数
     */
    @ApiModelProperty(value = "评分数-提交时必填", required = false)
    private Double score;
    /**
     * 评分日期
     */
    @ApiModelProperty(value = "评分日期-提交时不需填写", required = false)
    private String createTime;

    /**
     * 分组：1.初小 2.高小 3.初中 4.高中
     */
    @ApiModelProperty(value = "非该表DB字段-分组：1.初小 2.高小 3.初中 4.高中", required = false)
    private Integer groupType;

    /**
     * 演讲稿内容
     */
    @ApiModelProperty(value = "非该表DB字段-演讲稿内容", required = false)
    private String content;
    /**
     * 评分日期
     */
    @ApiModelProperty(value = "非该表DB字段-视频链接", required = false)
    private String videoUrl;
    /**
     * 注册码
     */
    @ApiModelProperty(value = "非该表DB字段-注册码", required = false)
    private String registerCode;
    /**
     * 评分人名称
     */
    @ApiModelProperty(value = "评分人名称", required = false)
    private String userName;
}
