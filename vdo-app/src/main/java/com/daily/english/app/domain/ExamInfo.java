package com.daily.english.app.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


/**
 * 试卷
 *
 * @author admin
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExamInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID", required = true)
    private String id;

    @ApiModelProperty(value = "作者", required = true, example = "Dr Joan Clarke")
    private String setter;
    /**
     * 试卷标题
     */
    @ApiModelProperty(value = "试卷标题", required = true)
    private String title;

    @ApiModelProperty(value = "等级", required = true, example = "S6")
    private String level;

    @ApiModelProperty(value = "试卷类型", required = true, example = "News")
    private String examType;

    @ApiModelProperty(value = "视频id", required = true, example = "0")
    private Long videoId;

    @ApiModelProperty(value = "试题数", required = false)
    private Integer questionNum;

    @ApiModelProperty(value = "阅读理解题数", required = false)
    private Integer comprehensionNum;

    @ApiModelProperty(value = "词汇题数", required = false)
    private Integer vocabularyNum;

    @ApiModelProperty(value = "语法题数", required = false)
    private Integer grammarNum;

    private List<Part> partList;

    private String partListJson;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", required = false)
    private String createTime;
    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人", required = false)
    private String createAccount;
    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人", required = false)
    private String updateAccount;
    /**
     * 更新日期
     */
    @ApiModelProperty(value = "更新日期", required = false)
    private String updateTime;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态", required = true)
    @Builder.Default
    private Integer state = 0;
}
