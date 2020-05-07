package com.daily.english.app.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhc
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DownloadVideo {

    @ApiModelProperty(value = "视频下载id", required = true)
    private long id;

    @ApiModelProperty(value = "视频 id", required = true)
    private String videoId;

    @ApiModelProperty(value = " 用户 id", required = true)
    private String userId;

    private String createTime;
}
