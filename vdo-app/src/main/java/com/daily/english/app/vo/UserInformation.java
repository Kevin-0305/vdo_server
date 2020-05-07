package com.daily.english.app.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInformation {
    @ApiModelProperty(value = "主键用户ID", required = true)
    private String id;
    @ApiModelProperty(value = "用户昵称", required = false)
    private String nickname;
    /**
     * 个人简介、签名
     */
    @ApiModelProperty(value = "个人简介、签名", required = false)
    private String introduction;
    /**
     * 生日
     */
    @ApiModelProperty(value = "生日", required = false)
    private String birthday;
    /**
     * 学习目的
     */
    @ApiModelProperty(value = "学习目的(1 雅思考试，2托福考试，3提升英语成绩、4语言兴趣、5职业需要、6境外旅游) 默认提高 3 DSE。", required = false)
    private Integer purpose;
    /**
     * 性别
     */
    @ApiModelProperty(value = "男性->male，女性->female", required = false)
    private String sex;
}
