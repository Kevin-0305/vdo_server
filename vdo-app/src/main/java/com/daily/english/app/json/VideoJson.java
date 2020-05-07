package com.daily.english.app.json;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * class_name: VideoJson
 * package: com.daily.english.pojo.yj.json
 * describe: 视频配置JSON
 * creat_user: liuzd@chinadailyhk.com
 * creat_date: 2019-08-06
 * creat_time: 10:03
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VideoJson implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "视频配置集合")
    private List<VideoRecord> content;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class VideoRecord implements Serializable {
        private static final long serialVersionUID = 1L;

        @ApiModelProperty(value = "类型:1、香港赛区总决赛演讲视频回顾\n" +
                "2、全国总决赛演讲视频回顾\n" +
                "3、演讲技巧资源\n" +
                "4、主页视频")
        private Integer type;
        @ApiModelProperty(value = "标题中文")
        private String titleCh;
        @ApiModelProperty(value = "标题繁体")
        private String titleHk;
        @ApiModelProperty(value = "标题英文")
        private String titleEn;
        @ApiModelProperty(value = "视频地址")
        private String videoUrl;
        @ApiModelProperty(value = "视频封面图地址")
        private String imgUrl;
    }
}
