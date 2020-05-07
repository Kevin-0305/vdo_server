package com.daily.english.app.json;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 选手信息JSON
 *
 * @author
 * @create 2019-08-06 09:50
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlayersJson implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "人物信息集合")
    private List<PlayerRecord> content;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PlayerRecord  implements Serializable{
        private static final long serialVersionUID = 1L;

        @ApiModelProperty(value = "简中图")
        private String picChUrl;
        @ApiModelProperty(value = "繁中图")
        private String picHkUrl;
        @ApiModelProperty(value = "英文图")
        private String picEnUrl;

    }
}
