package com.daily.english.app.domain;

import com.daily.english.app.vo.UserExamInfoVo;
import com.daily.english.app.vo.UserRecordVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnalysisResult {
    /**
     * 试卷ID
     */
    @ApiModelProperty(value = "提交的试卷id", required = true)
    private String examInfoId;

    private UserExamInfoVo userExamInfoVo;
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

    private Answer answer;

    @ApiModelProperty(value = "试题数", required = false)
    private Integer questionNum;

    @ApiModelProperty(value = "阅读理解题数", required = false)
    private Integer comprehensionNum;

    @ApiModelProperty(value = "词汇题数", required = false)
    private Integer vocabularyNum;

    @ApiModelProperty(value = "语法题数", required = false)
    private Integer grammarNum;

    @ApiModelProperty(value = "阅读理解题数", required = false)
    private Integer comprehensionResultNum;

    @ApiModelProperty(value = "词汇题数", required = false)
    private Integer vocabularyResultNum;

    @ApiModelProperty(value = "语法题数", required = false)
    private Integer grammarResultNum;

    @ApiModelProperty(value = "答对题数", required = false)
    private Integer questionResultNum;

    @ApiModelProperty(value = "超过多少用户分数百分比", required = false)
    private String userPercent;

    @ApiModelProperty(value = "最后一次答题分数", required = false)
    private String score;

    @ApiModelProperty(value = "试卷状态1-3为首考，4-5=重考1阶，6-7=重考2阶，9=其他情况", required = true)
    private Integer type;

    @ApiModelProperty(value = "获得在经验值", required = false)
    private UserRecordVo exp;
    @ApiModelProperty(value = "获得在竹子数", required = false)
    private UserRecordVo bamboo;
    @ApiModelProperty(value = "错题id list", required = false)
    private List<AnswerEntry> mistakesList;
    @ApiModelProperty(value = "上次用时", required = false)
    private String useTime;
    @ApiModelProperty(value = "做正确题id list", required = false)
    private List<AnswerEntry> sureList;
}
