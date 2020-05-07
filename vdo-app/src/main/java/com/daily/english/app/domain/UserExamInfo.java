package com.daily.english.app.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用做题信息
 *
 * @author
 * @create 2018-06-28 上午10:20
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserExamInfo implements Serializable {

    @ApiModelProperty(value = "主键id", required = true)
    private String id;
    /**
     * 试卷ID
     */
    @ApiModelProperty(value = "提交的试卷id", required = true)
    private String examInfoId;
    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户id", required = true)
    private String userId;
    /**
     * 答案id
     */
    @ApiModelProperty(value = "答案id", required = true)
    private Long answerId;
    /**
     * 用户提交做题JSON
     */
    @ApiModelProperty(value = "提交的试卷答案", required = true)
    @Builder.Default
    private String sbmitJson = "";
    /**
     * 分数
     */
    @ApiModelProperty(value = "提交的试卷分数", required = true)
    private String score;

    @ApiModelProperty(value = "重试次数", required = true)
    private int retryTime;
    @ApiModelProperty(value = "上次用时", required = true)
    private String useTime;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", required = true)
    private String createTime;
    /**
     * 更新日期
     */
    @ApiModelProperty(value = "更新时间", required = true)
    private String updateTime;
    /**
     * 1=未考试，2=首考未提交，3=首考并提交，4=重考1阶未提交，5=重考1阶并提交
     * 6=重考2阶未提交，7=重考2阶并提交，8=重新考试，9=其他情况
     */
    @ApiModelProperty(value = "1=未考试，2=首考未提交，3=首考并提交，4=重考1阶未提交，5=重考1阶并提交，6=重考2阶未提交，7=重考2阶并提交，8=重新考试，9=其他情况", required = true)
    private int type;
    @ApiModelProperty(value = "0 显示正确答案  1不显示正确答案 正确答案的显示", required = true)
    private int isReset;
    @ApiModelProperty(value = "1 显示第一次进入重考1阶 其他值都不是", required = true)
    private int flag;

}
