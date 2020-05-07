package com.daily.english.app.vo;

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
public class YjUserVo implements Serializable {
    @ApiModelProperty(value = "主键ID", required = true)
    private String id;
    /**
     * 用户名
     */
    @ApiModelProperty(value = "登录名(邮箱)", required = true)
    private String username;        
    /**
     * 姓名
     */
    @ApiModelProperty(value = "用户名", required = true)
    private String name;
    /**
     * 昵称
     */
    @ApiModelProperty(value = "用户昵称", required = true)
    private String nickname; 
    /**
     * 用户图像地址
     */
    @ApiModelProperty(value = "用户头像", required = true)
    private String userImg;
    /**
     * 登录类型：0.真实用户 1.老师报名用户
     */
    @ApiModelProperty(value = "登录类型：0.真实用户 1.老师报名用户", required = true)
    private String loginType;

}
