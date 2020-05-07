package com.daily.english.app.json;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 嘉宾信息JSON
 *
 * @author
 * @create 2019-08-06 09:50
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GuestJson implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "人物信息集合")
    private List<GuestRecord> content;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GuestRecord  implements Serializable{
        private static final long serialVersionUID = 1L;

        @ApiModelProperty(value = "简体图")
        private String picChUrl;
        @ApiModelProperty(value = "繁体图")
        private String picHkUrl;
        @ApiModelProperty(value = "英文图")
        private String picEnUrl;
        @ApiModelProperty(value = "排版位置 （左，右）")
        private Integer showSide;
        @ApiModelProperty(value = "视频地址")
        private String videoUrl;
        @ApiModelProperty(value = "视频封面")
        private String videoPicUrl;
    }
}
