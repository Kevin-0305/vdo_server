package com.daily.english.app.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 音轨字幕
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Subtitle {

    private Long id;

    private Long videoId;

    private String title;

    @ApiModelProperty(value = "开始时间", required = true, example = "0:01:09")
    private String beginTime;

    @ApiModelProperty(value = "结束时间", required = true, example = "0:01:11")
    private String endTime;

    @ApiModelProperty(value = "英文", required = true, example = "So can you tell us a bit about the Stone Houses?")
    private String english;

    @ApiModelProperty(value = "简体中文", required = true, example = "你能告诉我们更多关于石屋家园的事吗？")
    private String simpChinese;

    @ApiModelProperty(value = "繁体中文", required = true, example = "你能告訴我們更多關於石屋家園的事嗎？")
    private String tradChinese;

    private String status;

    private String timeSs;
}
