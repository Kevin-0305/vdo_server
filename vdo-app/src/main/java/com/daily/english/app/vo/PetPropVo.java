package com.daily.english.app.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * class_name: PetProp
 * package: com.vdoenglish.core.entity
 * describe: 宠物道具
 * creat_user: liuzd@chinadailyhk.com
 * creat_date: 2019-09-17
 * creat_time: 15:39
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PetPropVo implements Serializable {
    /**
     * @Fields serialVersionUID:序列化ID
     */
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "宠物背包道具ID", required = true)
    private Integer id;
    /**
     * 宠物ID
     */
    @ApiModelProperty(value = "宠物ID", required = true)
    private Integer petId;
    /**
     * 道具ID
     */
    @ApiModelProperty(value = "道具ID", required = true)
    private Integer propId;
    /**
     * 数量
     */
    @ApiModelProperty(value = "数量", required = true)
    private Integer amount;
    @ApiModelProperty(value = "道具名称", required = true)
    private String propName;
    @ApiModelProperty(value = "道具加成经验", required = true)
    private String propExp;
    @ApiModelProperty(value = "道具名称繁体", required = true)
    private String propNameHk;
    @ApiModelProperty(value = "道具名称英文", required = true)
    private String propNameEn;
    @ApiModelProperty(value = "道具图片", required = true)
    private String propImage;
    @ApiModelProperty(value = "道具描述", required = true)
    private String propDescription;
}
