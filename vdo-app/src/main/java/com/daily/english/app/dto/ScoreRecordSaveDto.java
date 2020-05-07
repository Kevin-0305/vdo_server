package com.daily.english.app.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * 实现了UserDetails的User，角色在这里被设置。
 *
 * @author fanggang
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ScoreRecordSaveDto {
    private static final long serialVersionUID = 2396654715019746670L;
    @ApiModelProperty(value = "报名信息ID", required = false)
    private String bmId;
    @ApiModelProperty(value = "评委用户ID", required = false)
    private String userId;
    @ApiModelProperty(value = "评分分数", required = false)
    private Double score;
}
