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
public class AttachmentJson implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "附件集合")
    private List<AttachmentRecord> content;


    /**
     * class_name: AttachmentRecord
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
    public static class AttachmentRecord implements Serializable {
        private static final long serialVersionUID = 1L;
        @ApiModelProperty(value = "附件地址")
        private String fileUrl;//文件路径
        @ApiModelProperty(value = "附件名称")
        private String fileName;
    }

    public List<AttachmentRecord> getContent() {
        return content;
    }

    public void setContent(List<AttachmentRecord> content) {
        this.content = content;
    }
}
