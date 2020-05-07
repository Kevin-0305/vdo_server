package com.daily.english.app.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExamCommentVo {
    @ApiModelProperty(value = " id", required = false)
    private Long id;
    @ApiModelProperty(value = "试卷ID", required = true)
    private String  examId;
    @ApiModelProperty(value = "点赞数", required = false)
    private Long praise;
    @ApiModelProperty(value = " 用户 id", required = true)
    private Long userId;
    @ApiModelProperty(value = " 评论", required = true)
    private String content;
    @ApiModelProperty(value = " 是否点赞", required = false)
    private boolean praiseInfo;
    @ApiModelProperty(value = " 评论时间", required = false)
    private String createTime;
    @ApiModelProperty(value = " 时间差", required = false)
    private String distanceTime;
    @ApiModelProperty(value = " 用户名称", required = false)
    private String userName;
    @ApiModelProperty(value = " 用户头像", required = false)
    private String userImg;
}
