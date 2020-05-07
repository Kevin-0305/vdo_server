package com.daily.english.app.json;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 图片类JSON
 *
 * @author
 * @create 2019-08-06 09:44
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PicJson implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "图片类集合")
    private List<PicRecord> content;


    /**
     * class_name: PicRecord
     * package: com.daily.english.pojo.yj.json
     * describe: 图片集合中实体
     * creat_user: liuzd@chinadailyhk.com
     * creat_date: 2019-08-06
     * creat_time: 09:48
     **/
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PicRecord implements Serializable {
        private static final long serialVersionUID = 1L;

        @ApiModelProperty(value = "图片地址")
        private String fileUrl;//文件路径
        @ApiModelProperty(value = "类型：/有类型给类型，无类型的在提交是不设置")
        private Integer type;

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public String getFileUrl() {
            return fileUrl;
        }

        public void setFileUrl(String fileUrl) {
            this.fileUrl = fileUrl;
        }
    }

    public List<PicRecord> getContent() {
        return content;
    }

    public void setContent(List<PicRecord> content) {
        this.content = content;
    }
}
