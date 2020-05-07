package com.daily.english.app.vo;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 试题表
 *
 * @author
 * @create 2018-06-07 下午2:45
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionsVo {
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键", required = true)
    private String id;
    /**
     * 标题
     */
    @ApiModelProperty(value = "标题", required = true)
    private String title;
    /**
     * html标题
     */
    @ApiModelProperty(value = "html标题", required = true)
    private String webTitle;
    /**
     * 总分
     */
    @ApiModelProperty(value = "总分", required = true)
    private String sumCount;
    /**
     * 类型
     */
    @ApiModelProperty(value = "类型", required = true)
    private Integer type;
    /**
     * 5W选择题
     */
    @ApiModelProperty(value = "5W选择题", required = true)
    private String select5wJson;
    /**
     * 选择题
     */
    @ApiModelProperty(value = "选择题", required = true)
    private String optionJson;
    /**
     * 填空题
     */
    @ApiModelProperty(value = "填空题", required = true)
    private String filloutJson;
    /**
     * 图文匹配题
     */
    @ApiModelProperty(value = "图文匹配题", required = true)
    private String graphicJson;
    /**
     * 填写句子题
     */
    @ApiModelProperty(value = "填写句子题", required = true)
    private String filloutSentJson;
    /**
     * 连线
     */
    @ApiModelProperty(value = "连线", required = true)
    private String matchingJson;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", required = true)
    private String createTime;
    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人", required = true)
    private String createAccount;
    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人", required = true)
    private String updateAccount;
    /**
     * 更新日期
     */
    @ApiModelProperty(value = "更新日期", required = true)
    private String updateTime;

    @ApiModelProperty(value = "", required = true)
    private JSONObject jsonObj;

}
