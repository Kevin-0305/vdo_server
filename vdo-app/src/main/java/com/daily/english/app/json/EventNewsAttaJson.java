package com.daily.english.app.json;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventNewsAttaJson implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "新闻附件集合")
    private List<NewsAttaRecord> content;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class NewsAttaRecord implements Serializable {
        private static final long serialVersionUID = 1L;

        @ApiModelProperty(value = "图片地址")
        private String picUrl;
        @ApiModelProperty(value = "视频地址")
        private String videoUrl;
        @ApiModelProperty(value = "视频封面图")
        private String videoPicUrl;
        @ApiModelProperty(value = "类型")
        private Integer type;
    }
}
