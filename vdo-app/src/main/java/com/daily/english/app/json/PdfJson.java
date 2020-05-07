package com.daily.english.app.json;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * PDF报名须知类JSON
 *
 * @author
 * @create 2019-08-06 09:44
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PdfJson implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "pdf集合")
    private List<PdfRecord> content;

    /**
     * class_name: PdfRecord
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
    public static class PdfRecord implements Serializable {
        private static final long serialVersionUID = 1L;

        @ApiModelProperty(value = "文件地址")
        private String fileUrl;
        @ApiModelProperty(value = "平台：1.web 2.app")
        private String platformType;
        @ApiModelProperty(value = "语言：1.简体 2.繁体 3.英文")
        private Integer languageType;
        @ApiModelProperty(value = "类型：1.个人报名 2.老师报名 3.小学比赛章程 4.中学比赛章程")
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

        public String getPlatformType() {
            return platformType;
        }

        public void setPlatformType(String platformType) {
            this.platformType = platformType;
        }

        public Integer getLanguageType() {
            return languageType;
        }

        public void setLanguageType(Integer languageType) {
            this.languageType = languageType;
        }

    }

    public List<PdfRecord> getContent() {
        return content;
    }

    public void setContent(List<PdfRecord> content) {
        this.content = content;
    }
}
