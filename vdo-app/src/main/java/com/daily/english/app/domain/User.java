package com.daily.english.app.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 登录账号/用户
 *
 * @author Gin
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    @ApiModelProperty(value = "主键ID", required = true)
    private String id;
    /**
     * 用户名
     */
    @ApiModelProperty(value = "登录名(邮箱)", required = true)
    private String username;        
    /**
     * 用户密码
     */
    @ApiModelProperty(value = "用户密码", required = true)
    private String password;    
    /**
     * 姓名
     */
    @ApiModelProperty(value = "用户名", required = true)
    private String name;    
    /**
     * 角色
     */
    @ApiModelProperty(value = "用户角色：学生=1,认证老师=2，特邀老师=3，管理员=4，游客=5", required = true)
    private String role;    
    /**
     * 昵称
     */
    @ApiModelProperty(value = "用户昵称", required = true)
    private String nickname;
    /**
     * 性别
     */
    @ApiModelProperty(value = "男性=male，女性=female", required = true)
    private String sex;
    /**
     * 用户图像地址
     */
    @ApiModelProperty(value = "用户头像", required = true)
    private String userImg;
    /**
     * facebook账号UID
     */
    @ApiModelProperty(value = "facebook账号", required = true)
    private String facebookUID;

    @ApiModelProperty(value = "创建时间", required = true)
    private String createTime;
    /**
     * 位置
     */
    @ApiModelProperty(value = "位置", required = true)
    private String location;
    /**
     * 0:普通用户 1:
     */
    @ApiModelProperty(value = "用户类型.0:普通用户 1:", required = true)
    private String userType; 
    /**
     * 会员到期时间
     */
    @ApiModelProperty(value = "会员到期时间", required = true)
    private String vipEndDate;
    /**
     * 个人简介、签名
     */
    @ApiModelProperty(value = "个人简介、签名", required = false)
    private String introduction;
    /**
     * 生日
     */
    @ApiModelProperty(value = "生日", required = true)
    private String birthday;
    /**
     * 学习目的
     */
    @ApiModelProperty(value = "学习目的", required = true)
    private Integer purpose;
    /**
     * 语言 语言偏好，默认2 繁体中文。简体中文=1，繁体中文=2，英文=3
     */
    @ApiModelProperty(value = "语言 简体中文=1，繁体中文=2，英文=3", required = true)
    private Integer language;
    /**
     * 开通级别
     */
    @ApiModelProperty(value = "开通级别：S1,S2(多个以逗号隔开)", required = true)
    private String orderMap;

}
