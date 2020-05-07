package com.daily.english.app.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Answer {

    @ApiModelProperty(value = "答案 id", required = true)
    private Long id;

    @ApiModelProperty(value = "试卷 id", required = true)
    private String examId;

    @ApiModelProperty(value = " 用户 id", required = true)
    private String userId;

    @ApiModelProperty(value = " 答案对", required = true)
    private List<AnswerEntry> answers;

    private String answersJson;

    @ApiModelProperty(value = "这次用时", required = true)
    private String useTime;

    private String createTime;

}
