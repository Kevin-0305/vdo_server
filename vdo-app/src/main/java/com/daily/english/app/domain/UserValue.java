package com.daily.english.app.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户数值表
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserValue {
  @ApiModelProperty(value = "主键用户ID", required = true)
  private Long id;
  /**
   * 今日获得竹子数量
   */
  @ApiModelProperty(value = "今日获得竹子数量", required = true,example = "0")
  private Long getBambooToday = 0l;
  /**
   * 今日消耗竹子数量
   */
  @ApiModelProperty(value = "今日消耗竹子数量", required = true)
  private Long useBambooToday = 0l;
  /**
   *总获得竹子数量，即累计获得上限
   */
  @ApiModelProperty(value = "总获得竹子数量，即累计获得上限", required = true)
  private Long getBambooAll = 0l;
  /**
   * 总消耗竹子数量，即累积消耗上限
   */
  @ApiModelProperty(value = "总消耗竹子数量，即累积消耗上限", required = true)
  private Long useBambooAll = 0l;
  /**
   * 今日获得经验值
   */
  @ApiModelProperty(value = "今日获得经验值", required = true)
  private Long getExpToday = 0l;
  /**
   * 总经验值
   */
  @ApiModelProperty(value = "总经验值", required = true)
  private Long exp = 0l;

  /**
   * 当周总经验值
   */
  private Long sumWeekExp = 0l;
  /**
   * 当周总获取竹子
   */
  private Long sumWeekBamboo = 0l;
  /**
   * 上次经验排名（前天）
   */
  private Long lastExpRank;
  /**
   * 上次经验排名（上上周）
   */
  private Long lastExpWeekRank;
  /**
   * 上次竹子排名（前天）
   */
  private Long lastBambooRank;
  /**
   * 当周总竹子
   */
  private Long lastBambooWeekRank;
  /**
   * 经验榜排名
   */
  @ApiModelProperty(value = "经验榜排名", required = true)
  private Long expRank;
  /**
   * 经验榜上周排名
   */
  @ApiModelProperty(value = "经验榜上周排名", required = true)
  private Long expRankLastweek;
  /**
   * 竹子数量
   */
  @ApiModelProperty(value = "竹子数量", required = true)
  private Long bamboo = 0l;
  /**
   *竹子榜今日排名
   */
  @ApiModelProperty(value = "竹子榜今日排名", required = true)
  private Long bambooRank;
  /**
   * 竹子榜上周排名
   */
  @ApiModelProperty(value = "竹子榜上周排名", required = true)
  private Long bambooRankLastweek;
  @ApiModelProperty(value = "昨日经验", required = true)
  private Long getExpYesterday;
  @ApiModelProperty(value = "昨日竹子", required = true)
  private Long getBambooYesterday;

}
