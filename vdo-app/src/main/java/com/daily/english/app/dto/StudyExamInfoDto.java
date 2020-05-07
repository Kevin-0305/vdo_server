package com.daily.english.app.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudyExamInfoDto extends PageSearchDto implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "老师Id", required = false)
    private String uid;
    @ApiModelProperty(value = "学校ID(学校负责人传学校ID查询)")
    private String schoolId;
    private String userId;
    @ApiModelProperty(value = "分数范围1", required = false)
    private String scoreRangeStart;
    @ApiModelProperty(value = "分数范围2", required = false)
    private String scoreRangeEnd;
    @ApiModelProperty(value = "分数类型", required = false)
    private String scorceType;
    @ApiModelProperty(value = "关卡查询1", required = false,example = "0")
    private String checkpointsStart;
    @ApiModelProperty(value = "关卡查询2", required = false,example = "4")
    private String checkpointsEnd;
    @ApiModelProperty(value = "性别查询", required = false)
    private  String gender;
    @ApiModelProperty(value = "班级ID", required = false)
    private  String classs;
    @ApiModelProperty(value = "年级1-6", required = false)
    private  String grade;
    @ApiModelProperty(value = "1-6", required = false)
    private  String level;
    @ApiModelProperty(value = "排序 0=score分数从高到低 ;1=score分数从低到高进行排列", required = false)
    private Integer sort;
    private List<String> ids;

}
