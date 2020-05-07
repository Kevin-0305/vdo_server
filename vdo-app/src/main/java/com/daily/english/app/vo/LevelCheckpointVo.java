package com.daily.english.app.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LevelCheckpointVo implements Serializable {
    @ApiModelProperty(value = "关卡no", required = true)
    private Integer nodeNum;
    @ApiModelProperty(value = "关卡已做过做题人数", required = true)
    private Integer sumNum;
}
