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
public class ScoreChartVo {
    @ApiModelProperty(value = "关卡", required = true)
    public String checkpoints;
    @ApiModelProperty(value = "分数", required = true)
    public String score;
    @ApiModelProperty(value = "排名")
    public String ranking;
}
