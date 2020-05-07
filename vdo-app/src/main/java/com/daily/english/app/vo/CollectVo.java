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
public class CollectVo {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "用户ID", required = true, example = "265")
    private Integer uid;
    @ApiModelProperty(value = "视频ID", required = true, example = "10001")
    private Integer vid;
    @ApiModelProperty(value = "收藏/点赞 类型：collect 默认collect 收藏  ;praise 点赞", required = true,example = "collect")
    private String type;

    @ApiModelProperty(value = "1=video,2=challenge", required = false,example = "1")
    private int flag;
    @ApiModelProperty(value = "mapId", required = false)
    private Long mapId;
    @ApiModelProperty(value = "examId 试卷ID", required = false)
    private String examId;
}
