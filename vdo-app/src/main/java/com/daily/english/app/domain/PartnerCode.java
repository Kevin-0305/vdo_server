package com.daily.english.app.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * class_name: PartnerCode
 * package: com.vdoenglish.core.entity
 * describe: 合作方code码
 * creat_user: liuzd@chinadailyhk.com
 * creat_date: 2019-09-05
 * creat_time: 15:39
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PartnerCode implements Serializable {
    /**
     * @Fields serialVersionUID:序列化ID
     */
    private static final long serialVersionUID = 1L;
    /**
     * 主键ID
     */
    @ApiModelProperty(value = "ID")
    private Integer id;
    /**
     * 验证码
     */
    @ApiModelProperty(value = "验证码")
    private String identifyCode;
    /**
     * 状态：0.未使用 1.已使用
     */
    @ApiModelProperty(value = "状态：0.未使用 1.已使用")
    private Integer status;
    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID（激活用户ID）")
    private String userId;
    @ApiModelProperty(value = "APP用户名称--非该表数据")
    private String userName;
    /**
     * 合作学校ID
     */
    @ApiModelProperty(value = "合作学校ID")
    private Integer schoolId;
    /**
     * 合作学校班级ID
     */
    @ApiModelProperty(value = "合作学校班级ID")
    private Integer classId;
    /**
     * 类型：0.学生 1.老师（老师码在合作方页面生成查看）
     */
    @ApiModelProperty(value = "类型：0.学生 1.老师（老师码在合作方页面生成查看）")
    private Integer type;
    /**
     * 激活日期
     */
    @ApiModelProperty(value = "激活日期")
    private String activateTime;
    /**
     * 性别：男：male  女：female
     */
    @ApiModelProperty(value = "性别：男：male  女：female", required = false)
    private String gender;
    /**
     * 学生姓名
     */
    @ApiModelProperty(value = "学生姓名", required = false)
    private String studentName;

}
