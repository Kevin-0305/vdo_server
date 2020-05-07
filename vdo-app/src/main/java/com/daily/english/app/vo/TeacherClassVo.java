package com.daily.english.app.vo;

import com.daily.english.app.domain.PartnerCode;
import com.google.common.collect.Maps;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeacherClassVo implements Serializable {
    /**
     * @Fields serialVersionUID:序列化ID
     */
    private static final long serialVersionUID = 1L;
    /**
     * 班级ID
     */
    @ApiModelProperty(value = "班级ID")
    private Integer classId;
    /**
     * 班级名称
     */
    @ApiModelProperty(value = "班别名称")
    private String className;
    /**
     * 级别：S1、S2、S3
     */
    @ApiModelProperty(value = "级别：S1、S2、S3多个以逗号隔开(,)")
    private String level;
    /**
     * 学生数量
     */
    @ApiModelProperty(value = "学生数量")
    private Integer studentNumber;
    /**
     * 进度
     */
    @ApiModelProperty(value = "S1进度")
    private Integer checkpointProgressS1;
    /**
     * 进度
     */
    @ApiModelProperty(value = "S2进度")
    private Integer checkpointProgressS2;
    /**
     * 进度
     */
    @ApiModelProperty(value = "S3进度")
    private Integer checkpointProgressS3;
    /**
     * 进度
     */
    @ApiModelProperty(value = "S4进度")
    private Integer checkpointProgressS4;
    /**
     * 进度
     */
    @ApiModelProperty(value = "S5进度")
    private Integer checkpointProgressS5;
    /**
     * 进度
     */
    @ApiModelProperty(value = "S6进度")
    private Integer checkpointProgressS6;

}
