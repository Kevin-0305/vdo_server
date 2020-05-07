package com.daily.english.app.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * question节点
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Node implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "节点Id", required = false, example = "3")
    private Long nodeId;

    @ApiModelProperty(value = "部分Id", required = false, example = "3")
    private Long partId;

    /**
     * 否则为questionId,那就是描述, 否则为text
     */
    @ApiModelProperty(value = "试题Id", required = false, example = "3")
    private Long questionId;

    /**
     * 如果有text,那就是描述, 否则为question
     */
    @ApiModelProperty(value = "试题描述", required = false, example = "Based on the information given in the video, " +
            "complete the following fact sheet on A taste of roast goose. " +
            "For Multiple-choice Questions, darken the circle of your choice. ")
    private String text;

    /**
     * 来自于questions的类型
     */
    @ApiModelProperty(value = "试题类型", required = false, example = "Choose")
    private String type;

    @ApiModelProperty(value = "试题子类型", required = false, example = "multi")
    private String chooseType;

    @ApiModelProperty(value = "试题内容", required = false, example = "The most popular roast meat of Beijing is:")
    private String titleText;

    @ApiModelProperty(value = "试题图片", required = false, example = "image_3.jpg")
    private String titleImg;

    private String optionListJson;
    @ApiModelProperty(value = "试题选型", required = false, example = "\"Goose\",\n" +
            "            \"Turkey\",\n" +
            "            \"Duck\"")
    private List<String> optionList;

    private String imgFillContentListJson;
    private List<FillContent> imgFillContentList;

    private List<String> promptList;

    private Integer number;

    private String leftJson;
    private List<String> left;

    private String rightJson;
    private List<String> right;

    @ApiModelProperty(value = "试题答案", required = false, example = "A,C")
    private String answer;

    @ApiModelProperty(value = "用户历史习题答案", required = false)
    private  List<ExamRecord> examRecordList;
    @ApiModelProperty(value = "排序", required = false, example = "2")
    private Integer orders;
    /**
     * 状态
     */
    @ApiModelProperty(value = "状态", required = true)
    private Integer status;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", required = true)
    private Date createTime;
    /**
     * 更新日期
     */
    @ApiModelProperty(value = "更新日期", required = true)
    private Date updateTime;
    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人", required = true)
    private Integer createAccount;
    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人", required = true)
    private Integer updateAccount;

    public Node(String text,Long questionId, String chooseType, String titleText, List<String> optionList, String answer) {
        this.questionId = questionId;
        this.chooseType = chooseType;
        this.titleText = titleText;
        this.optionList = optionList;
        this.answer = answer;
        this.text = text;
        this.type = "Choose";
    }

}
