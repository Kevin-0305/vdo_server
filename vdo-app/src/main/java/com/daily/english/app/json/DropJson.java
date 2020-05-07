package com.daily.english.app.json;

import com.daily.english.app.domain.Prop;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 掉落信息JSON
 *
 * @author
 * @create 2019-08-06 09:44
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DropJson implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "掉落信息集合")
    private List<DropRecord> content;


    /**
     * class_name: DropRecord
     * package: com.daily.english.pojo.yj.json
     * describe: 掉落信息
     * creat_user: liuzd@chinadailyhk.com
     * creat_date: 2019-08-06
     * creat_time: 09:48
     **/
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DropRecord implements Serializable {
        private static final long serialVersionUID = 1L;

        @ApiModelProperty(value = "道具ID")
        private String propId;
        @ApiModelProperty(value = "掉落数量")
        private String dropCount;
        @ApiModelProperty(value = "掉落权重")
        private String dropWeight;
        @ApiModelProperty(value = "概率")
        private String probability;
        @ApiModelProperty(value = "道具实体信息--只做数据查询时返回")
        private Prop prop;
    }

    public List<DropRecord> getContent() {
        return content;
    }

    public void setContent(List<DropRecord> content) {
        this.content = content;
    }
}
