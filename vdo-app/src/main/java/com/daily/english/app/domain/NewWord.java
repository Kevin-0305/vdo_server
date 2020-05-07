package com.daily.english.app.domain;

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
public class NewWord {
    @ApiModelProperty(value = "主键id", required = true, example = "0:01:09")
    private Long id;
    private String userId;

    private List<String> words;

    private String word;
    /**
     * 0:正常; 1: 回收站
     */
    private int state;

    @ApiModelProperty(value = "创建日期", required = true, example = "2019-04-25 17:07:32")
    private Date addDate;
}
