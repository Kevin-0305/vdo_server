package com.daily.english.app.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用户返回列表查询Dto
 *
 * @author
 * @create 2019-08-14 10:48
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackDto extends PageSearchDto implements Serializable {

    @ApiModelProperty(value = "状态：-1.删除 0.默认 1.上架 2.下架", required = false)
    private Integer status;
    @ApiModelProperty(value = "平台 1.iphone 2.ipad 3.androidPhone 4.androidPad 5.PC 6.WEB")
    private Integer questionType;
    @ApiModelProperty(value = "1视频问题，2试卷问题 ,3其他,4.21世纪杯报名网站")
    private Integer sourceType;
    @ApiModelProperty(value = "用户ID")
    private Integer userId;
}
