package com.daily.english.app.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudyAverageVo {
    @ApiModelProperty(value = "Part1分数的平均分", required = true)
    private String comprehension;
    @ApiModelProperty(value = "Part2分数的平均分", required = true)
    private String vocabulary;
    @ApiModelProperty(value = "Part3分数的平均分", required = true)
    private String grammar;
    @ApiModelProperty(value = "平均分", required = true)
    private String average;
    @ApiModelProperty(value = "饼图0-49", required = true)
    private String pastry1;
    @ApiModelProperty(value = "饼图50-69", required = true)
    private String pastry2;
    @ApiModelProperty(value = "饼图70-89", required = true)
    private String pastry3;
    @ApiModelProperty(value = "饼图90-100", required = true)
    private String pastry4;
    @ApiModelProperty(value = "折线图数据value （红色为未完成人数，紫色为未及格人数，蓝色为及格人数，平均分）", required = true)
    private Map columnarMap;
}

