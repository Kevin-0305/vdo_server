package com.daily.english.app.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExamComment {

    @ApiModelProperty(value = " id", required = true)
    private Long id;
    @ApiModelProperty(value = "试卷ID", required = true)
    private String  examId;
    @ApiModelProperty(value = "点赞数", required = true)
    private String praise;
    @ApiModelProperty(value = " 用户 id", required = true)
    private Long userId;
    @ApiModelProperty(value = " 评论", required = true)
    private String content;
    private String createTime;
    @ApiModelProperty(value = " 是否点赞", required = false)
    private boolean praiseInfo;
    @ApiModelProperty(value = " 时间差", required = false)
    private String distanceTime;

    private String userName;
    private String userImg;
}
