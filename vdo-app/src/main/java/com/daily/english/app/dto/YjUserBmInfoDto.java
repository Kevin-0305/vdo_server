package com.daily.english.app.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * class_name: YjUserBmInfoDto
 * package: com.daily.english.app.dto
 * describe: 报名查询参数DTO
 * creat_user: liuzd@chinadailyhk.com
 * creat_date: 2019-08-15
 * creat_time: 16:52
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class YjUserBmInfoDto extends PageSearchDto implements Serializable {

    /**
     * 报名类型：0.个人 1.老师
     */
    @ApiModelProperty(value = "报名类型：0.个人 1.老师", required = false)
    private Integer type;
    /**
     * 多条件分组
     */
    @ApiModelProperty(value = "多条件分组", required = false)
    private List<Integer> groupTypes;
    /**
     * 上传状态：0.未上传 1.已上传 2.未上传视频 3.未上传演讲稿
     */
    @ApiModelProperty(value = "上传状态：0.未上传 1.已上传 2.未上传视频 3.未上传演讲稿", required = false)
    private Integer uploadStatus;
    /**
     * 分数
     */
    @ApiModelProperty(value = "分数", required = false)
    private String score;
    /**
     * 评分状态：0.未评分 1.已评分
     */
    @ApiModelProperty(value = "评分状态：0.未评分 1.已评分", required = false)
    private Integer scoreStatus;

}
