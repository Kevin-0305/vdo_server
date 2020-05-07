package com.daily.english.app.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 用户流水相关的竹子经验
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRecordVo {
    @ApiModelProperty(value = "领取相当值要用ID", required = true)
    private String id;
    private  Long userId;
    @ApiModelProperty(value = "exp 或 bamboo", required = true)
    private String type;
    @ApiModelProperty(value = "变化值", required = false)
    private Long changeValue;
    @ApiModelProperty(value = "0未领取，1已领取", required = false)
    private Long isTake;

}

