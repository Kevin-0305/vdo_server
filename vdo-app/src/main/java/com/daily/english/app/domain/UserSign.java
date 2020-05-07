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
public class UserSign {
    /**
     * 关联用户ID
     */
    @ApiModelProperty(value = "关联用户ID", required = true)
    private Long userId;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", required = true)
    private String createTime;
    /**
     * 最后签到时间
     */
    @ApiModelProperty(value = "最后签到时间", required = true)
    private String lastModifyTime;
    /**
     * 连续签到次数
     */
    @ApiModelProperty(value = "连续签到次数", required = true)
    private Integer signCount;
    /**
     * 签到次数
     */
    @ApiModelProperty(value = "总签到次数", required = true)
    private Integer count;
}