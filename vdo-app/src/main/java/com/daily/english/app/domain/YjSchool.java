package com.daily.english.app.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * class_name: YjSchool
 * package: com.vdoenglish.core.entity
 * describe: 演讲学校表
 * creat_user: liuzd@chinadailyhk.com
 * creat_date: 2019-08-17
 * creat_time: 23:39
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class YjSchool implements Serializable {
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
     * 地区
     */
    @ApiModelProperty(value = "地区中文", required = false)
    private String distinct;
    /**
     * 英文地区
     */
    @ApiModelProperty(value = "地区英文", required = false)
    private String distinctEn;
    /**
     * 学校名称中文
     */
    @ApiModelProperty(value = "学校名称中文", required = false)
    private String nameCh;
    /**
     * 学校名称英文
     */
    @ApiModelProperty(value = "学校名称英文", required = false)
    private String nameEn;
    /**
     * 学校类型
     */
    @ApiModelProperty(value = "学校类型：0.其他 1.中学 2.小学 3.中小学", required = false)
    private Integer schoolType;
    /**
     * 繁体地区
     */
    @ApiModelProperty(value = "地区繁体", required = false)
    private String distinctHk;
}
