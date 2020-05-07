package com.daily.english.app.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * class_name: Goods
 * package: com.vdoenglish.core.entity
 * describe: 商品
 * creat_user: liuzd@chinadailyhk.com
 * creat_date: 2019-08-29
 * creat_time: 15:39
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Goods implements Serializable {
    /**
     * @Fields serialVersionUID:序列化ID
     */
    private static final long serialVersionUID = 1L;
    /**
     * 商品ID
     */
    @ApiModelProperty(value = "商品ID", required = false)
    private Integer id;
    /**
     * 道具ID
     */
    @ApiModelProperty(value = "道具ID", required = false)
    private Integer propId;
    /**
     * 序号
     */
    @ApiModelProperty(value = "序号", required = false)
    private String sortNo;
    /**
     * 商品角标
     */
    @ApiModelProperty(value = "商品角标", required = false)
    private String goodsBadge;
    /**
     * 折扣
     */
    @ApiModelProperty(value = "折扣", required = false)
    private Double discount;
    /**
     * 商品原价
     */
    @ApiModelProperty(value = "商品原价", required = false)
    private Double price;
    /**
     * 折后价
     */
    @ApiModelProperty(value = "折后价", required = false)
    private Double discountPrice;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", required = false)
    private String createTime;
    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人", required = false)
    private String createUser;
    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人", required = false)
    private String updateUser;
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间", required = false)
    private String updateTime;
    /**
     * 发布状态：0.默认 1.上架 2.下架
     */
    @ApiModelProperty(value = "发布状态：0.默认 1.上架 2.下架", required = false)
    private Integer status;

    @ApiModelProperty(value = "新品角标设置：0.默认 1.勾选", required = false)
    private Integer newBadge;
    @ApiModelProperty(value = "热卖角标设置：0.默认 1.勾选", required = false)
    private Integer hotBadge;
    @ApiModelProperty(value = "活动角标设置：0.默认 1.勾选", required = false)
    private Integer activityBadge;
    @ApiModelProperty(value = "折扣角标设置：0.默认 1.勾选", required = false)
    private Integer discountBadge;

}
