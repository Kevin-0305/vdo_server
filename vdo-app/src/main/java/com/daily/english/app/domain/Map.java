package com.daily.english.app.domain;

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
public class Map {
    @ApiModelProperty(value = "主键ID", required = true)
    private Long id;

    @ApiModelProperty(value = "地图难度级别", required = true)
    private String level;

    @ApiModelProperty(value = "坐标x", required = true)
    private int x;

    @ApiModelProperty(value = "坐标y", required = true)
    private int y;

    @ApiModelProperty(value = "地图节点图片名称, 不含路径", required = true)
    private String source;

    @ApiModelProperty(value = "地图名称", required = true)
    private String cname;

    @ApiModelProperty(value = "地图类型; 1考试，2宝箱", required = true)
    private String type;

//    @ApiModelProperty(value = "用户id", required = true)
    private String userId;
    /**
     * 对应的宝箱(Box)
     */
    @ApiModelProperty(value = "当前节点宝箱id, 与exam互斥", required = true)
    private String boxId;

    /**'
     * 对应的试卷(ExamInfo)
     */
    @ApiModelProperty(value = "当前节点考卷id, 与box互斥", required = true)
    private String examId;

    @ApiModelProperty(value = " 考试类型", required = true)
    private String examType;

//    @ApiModelProperty(value = "当前节点考卷", required = true)
    private ExamInfo examInfo;

    @ApiModelProperty(value = "免费 1vip免费 2收费", required = true)
    private Integer free;

    @ApiModelProperty(value = "解锁需要的费用", required = true)
    private Double price;

    @ApiModelProperty(value = "考试的分数", required = true)
    private Integer score;

    @ApiModelProperty(value = "过关需要的分数", required = true)
    private Integer needScore;

    /**
     * 上一路径节点
     */
    @ApiModelProperty(value = "父节点列表", required = true)
    private List<Integer> inLevel;
    private String inLevelJson;

    /**
     * 子路径节点集合
     */
    @ApiModelProperty(value = "子节点列表", required = true)
    private List<Integer> nextLevel;
    private String nextLevelJson;

    /**
     * 排序
     */
    @ApiModelProperty(value = "当前节点排序序号", required = true)
    private Long orders;

    @ApiModelProperty(value = "当前节点状态 1锁定 2解锁 ", required = true)
    private Integer status;

    @ApiModelProperty(value = "网页坐标x", required = true)
    private int webx;

    @ApiModelProperty(value = "网页坐标y", required = true)
    private int weby;

}
