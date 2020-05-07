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
public class StudentMapVo {
    @ApiModelProperty(value = "用户ID")
    private String userId;
    @ApiModelProperty(value = "学生名称")
    private String studentName;
    @ApiModelProperty(value = "S1按照路点map id顺序，每个路点的用户真实得分")
    private String s1MapScore;
    @ApiModelProperty(value = "S2按照路点mapid顺序，每个路点的用户真实得分")
    private String s2MapScore;
    @ApiModelProperty(value = "S3按照路点mapid顺序，每个路点的用户真实得分")
    private String s3MapScore;
    @ApiModelProperty(value = "S4按照路点mapid顺序，每个路点的用户真实得分")
    private String s4MapScore;
    @ApiModelProperty(value = "S5按照路点mapid顺序，每个路点的用户真实得分")
    private String s5MapScore;
    @ApiModelProperty(value = "S6按照路点mapid顺序，每个路点的用户真实得分")
    private String s6MapScore;
}
