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
public class CompetePicJson implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "比赛照片类集合")
    private List<CompetePic> content;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CompetePic implements Serializable {
        private static final long serialVersionUID = 1L;

        @ApiModelProperty(value = "图片地址")
        private String picUrl;
        @ApiModelProperty(value = "缩略图")
        private String picThumbUrl;
    }

}
