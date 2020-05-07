package com.daily.english.app.domain;

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
public class UserRecord {
    private String id;
    private  Long userId;
    @ApiModelProperty(value = "exp 或 bamboo", required = true)
    private String type;
    private String content;
    @ApiModelProperty(value = "变化后值", required = true)
    private Long targetValue;
    @ApiModelProperty(value = "开始值", required = true)
    private Long beginValue;
    @ApiModelProperty(value = "变化值", required = true)
    private Long changeValue;
    private String createTime;
    private String sourceId;
    @ApiModelProperty(value = "0未领取，1已领取", required = true)
    private Long isTake;

}
