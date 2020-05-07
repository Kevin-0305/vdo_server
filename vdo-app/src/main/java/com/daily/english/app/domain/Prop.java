package com.daily.english.app.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * class_name: Prop
 * package: com.vdoenglish.core.entity
 * describe: 道具
 * creat_user: liuzd@chinadailyhk.com
 * creat_date: 2019-08-29
 * creat_time: 15:39
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Prop implements Serializable {
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
     * 道具名称
     */
    @ApiModelProperty(value = "道具名称", required = false)
    private String propName;
    /**
     * 道具名称繁体
     */
    @ApiModelProperty(value = "道具名称繁体", required = false)
    private String propNameHk;
    /**
     * 道具名称英文
     */
    @ApiModelProperty(value = "道具名称英文", required = false)
    private String propNameEn;
    /**
     * 道具价格
     */
    @ApiModelProperty(value = "道具价格", required = false)
    private String price;
    /**
     * 获得竹子
     */
    @ApiModelProperty(value = "获得竹子", required = false)
    private String bamboo;
    /**
     * 经验
     */
    @ApiModelProperty(value = "经验", required = false)
    private String exp;
    /**
     * 经验
     */
    @ApiModelProperty(value = "经验", required = false)
    private String imageUrl;
    /**
     * 道具描述
     */
    @ApiModelProperty(value = "道具描述", required = false)
    private String description;
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
}
