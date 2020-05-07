package com.daily.english.app.condition;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 错题本
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WrongTopicCondition {
    @ApiModelProperty(value = "主键id", required = true)
    private Long id;
    private List<Long> ids;
    private String userId;
    private String examId;
    private String nodeId;
    private String node;
    private String answer;
    private String type;
    /**
     * 0:正常; 1: 回收站
     */
    private int state;

    private Integer offset;

    private Integer limit;
}
