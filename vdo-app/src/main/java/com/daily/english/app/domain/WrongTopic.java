package com.daily.english.app.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 错题本
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WrongTopic {
    @ApiModelProperty(value = "主键id", required = true, example = "0:01:09")
    private Long id;
    private String userId;
    private String examId;
    private String nodeId;
    private String node;
    private String answer;
    private Node nodeFormat;
    @ApiModelProperty(value = "题型 Comprehension ", required = true)
    private String type;
    private Long videoId;
    /**
     * 0:正常; 1: 回收站
     */
    private int state;

    @ApiModelProperty(value = "创建日期", required = true, example = "2019-04-25 17:07:32")
    private Date addDate;
}
