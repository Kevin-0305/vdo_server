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
public class ExamCommentPraise {

    @ApiModelProperty(value = " id", required = false)
    private Long id;
    private Long  commentId;
    @ApiModelProperty(value = " 用户 id", required = true)
    private Long userId;
}
