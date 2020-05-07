package com.daily.english.app.condition;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * 生词本
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewWordCondition {
    @ApiModelProperty(value = "主键id", required = true, example = "0:01:09")
    private Long id;
    private String userId;

    private List<String> words;

    private String word;
    /**
     * 0:正常; 1: 回收站
     */
    private int state;

    /**
     * 查询时间范围, 最大日期
     */
    private Date maxDate;

    /**
     * 查询时间范围, 最小日期
     */
    private Date minDate;

    private Integer offset;

    private Integer limit;
}
