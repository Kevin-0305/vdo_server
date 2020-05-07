package com.daily.english.app.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 用户数值学习表
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserStudy {
  @ApiModelProperty(value = "主键用户ID", required = true)

  private Long id;
  /**
   * 今日学习时间，精确到分钟，每日数据凌晨4点重置
   */
  @ApiModelProperty(value = "今日学习时间 分钟", required = true)
  private Long studyTimeToday;
  /**
   * 本周学习时间，精确到分钟，每周数据周一凌晨4点重置
   */
  @ApiModelProperty(value = "本周学习时间 分钟", required = true)
  private Long studyTimeWeek;
  /**
   * 总学习时间，精确到分钟
   */
  @ApiModelProperty(value = "总学习时间 分钟", required = true)
  private Long studyTimeAll;
  /**
   * 总学习天数，精确到天，即每天学习1次就算做这天学习了
   */
  @ApiModelProperty(value = "总学习天数，精确到天", required = true)
  private Long studyDayAll;
  /**
   * 连续学习天数，精确到天，遇到没学习的，重置从0开始。
   */
  @ApiModelProperty(value = "连续学习天数，精确到天", required = true)
  private Long studyDayContinue;
  /**
   * 本周学习最长学习时间，精确到分钟
   */
  @ApiModelProperty(value = "本周学习最长学习时间 分钟", required = true)
  private Long studyTimeWeekMax;
  /**
   * 达成本周学习时长最久的日期，2019-02-12
   */
  @ApiModelProperty(value = "达成本周学习时长最久的日期，2019-02-12", required = true)
  private String studyTimeWeekMaxDate;
  /**
   * 本周完成阅读VDO首页视频的数量
   */
  @ApiModelProperty(value = "本周完成阅读VDO首页视频的数量", required = true)
  private Long finishVdoWeek;
  /**
   * 本周学习生词数量
   */
  @ApiModelProperty(value = "本周学习生词数量", required = true)
  private Long newWordsWeek;
  /**
   * 本周完成challenge关卡数量
   */
  @ApiModelProperty(value = "本周完成challenge关卡数量", required = true)
  private Long finishChallengeWeek;
  /**
   * 本周challenge平均得分
   */
  @ApiModelProperty(value = "本周challenge平均得分", required = true)
  private Long averageScoreWeek;
  /**
   * 史上学习时长最久，精确到分钟
   */
  @ApiModelProperty(value = "史上学习时长最久 分钟", required = true)
  private Long studyTimeMax;
  /**
   * 达成史上学习时长最久的日期，2019-02-12
   */
  @ApiModelProperty(value = "达成史上学习时长最久的日期，2019-02-12", required = true)
  private String studyTimeMaxDate;
  /**
   * 完成阅读VDO首页视频的总数量
   */
  @ApiModelProperty(value = "完成阅读VDO首页视频的总数量", required = true)
  private Long finishVdoAll;
  /**
   * 学习生词总数量
   */
  @ApiModelProperty(value = "学习生词总数量", required = true)
  private Long newWordsAll;
  /**
   * 完成challenge关卡总数量
   */
  @ApiModelProperty(value = "完成challenge关卡总数量", required = true)
  private Long finishChallengeAll;
  /**
   * 今日做题目数量
   */
  @ApiModelProperty(value = "今日做题目数量", required = true)
  private Long questionAmoutToday;
  /**
   * 总做题目数量
   */
  @ApiModelProperty(value = "总做题目数量", required = true)

  private Long questionAmoutAll;
  @ApiModelProperty(value = "记录本周每天学习时长(/小时)", required = false ,example = "1,20,50,60,80,10,20")
  private String weekHour;


}
