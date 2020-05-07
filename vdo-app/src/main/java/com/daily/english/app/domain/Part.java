package com.daily.english.app.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * 部分
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Part {
    @ApiModelProperty(value = "部分Id", required = false, example = "3")
    private Long partId;

    @ApiModelProperty(value = "试卷Id", required = false, example = "3")
    private String examInfoId;

    @ApiModelProperty(value = "部分名称", required = false, example = "Part1 Comprehension")
    private String partName;

    private List<Node> nodeList;

    @ApiModelProperty(value = "排序", required = false, example = "2")
    private Integer orders;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", required = true)
    private Date createTime;
    /**
     * 更新日期
     */
    @ApiModelProperty(value = "更新日期", required = true)
    private Date updateTime;
    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人", required = true)
    private String createAccount;
    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人", required = true)
    private String updateAccount;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态", required = true)
    private Integer status;
}
