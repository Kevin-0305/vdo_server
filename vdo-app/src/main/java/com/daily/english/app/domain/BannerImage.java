package com.daily.english.app.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * class_name: LaunchImage
 * package: com.vdoenglish.core.entity
 * describe:  banner
 * creat_user: liuzd@chinadailyhk.com
 * creat_date: 2019-08-27
 * creat_time: 15:39
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BannerImage implements Serializable {
    /**
     * @Fields serialVersionUID:序列化ID
     */
    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键ID", required = true)
    private Integer id;
    /**
     * 标题
     */
    @ApiModelProperty(value = "标题", required = true)
    private String title;
    /**
     * 链接
     */
    @ApiModelProperty(value = "链接", required = true)
    private String link;
    /**
     * pc banner
     */
    @ApiModelProperty(value = "pc banner", required = true)
    private String pc;
    /**
     * iphone banner
     */
    @ApiModelProperty(value = "iphone banner", required = true)
    private String iphone;
    /**
     * IPad banner
     */
    @ApiModelProperty(value = "IPad banner", required = true)
    private String ipad;
    /**
     * 安卓 banner
     */
    @ApiModelProperty(value = "安卓 banner", required = true)
    private String android;
    /**
     * 开始时间
     */
    @ApiModelProperty(value = "开始时间", required = true)
    private String startTime;
    /**
     * 结束时间
     */
    @ApiModelProperty(value = "结束时间", required = true)
    private String endTime;
    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人", required = true)
    private String createUser;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", required = true)
    private String createTime;
    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人", required = true)
    private String updateUser;
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间", required = true)
    private String updateTime;
}
