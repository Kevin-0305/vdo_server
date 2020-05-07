package com.daily.english.app.domain;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * class_name: YjJuryUser
 * package: com.vdoenglish.core.entity
 * describe: 评委属性表
 * creat_user: liuzd@chinadailyhk.com
 * creat_date: 2019-08-13
 * creat_time: 15:39
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CmsUser implements Serializable {
    /**
     * @Fields serialVersionUID:序列化ID
     */
    private static final long serialVersionUID = 1L;
    /**
     *
     */
    @ApiModelProperty(value = "主键ID", required = false)
    private Integer id;
    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名", required = false)
    private String name;
    /**
     * 角色类型：1.超管 2.运营 3.老师 4.评委
     */
    @ApiModelProperty(value = "角色类型：1.超管 2.运营 3.老师 4.评委 5.学校负责人")
    private Integer roleType;
    /**
     * 可评分次数
     */
    @ApiModelProperty(value = "可评分次数", required = false)
    private Integer execCount;
    /**
     * 账号
     */
    @ApiModelProperty(value = "账号", required = false)
    private String account;
    /**
     * 密码
     */
    @ApiModelProperty(value = "密码", required = false)
    private String password;
    /**
     * 状态：0.未开通 1.已开通
     */
    @ApiModelProperty(value = "状态：0.未开通 1.已开通", required = false)
    private Integer status;
    /**
     * 评分组别权限：0.未开通 1.初小 2.高小 3.初中 4.高中
     */
    @ApiModelProperty(value = "评分组别权限（多个以逗号隔开：1,2）：1.初小 2.高小 3.初中 4.高中 ", required = false)
    private String groupTypes;
    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人", required = false)
    private String createUser;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", required = false)
    private String createTime;
    /**
     * 修改人
     */
    @ApiModelProperty(value = "修改人", required = false)
    private String updateUser;
    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间", required = false)
    private String updateTime;

    /**
     * 性别：男：male  女：female
     */
    @ApiModelProperty(value = "性别：男：male  女：female", required = false)
    private String gender;
    /**
     * 生日，2019-02-12
     */
    @ApiModelProperty(value = "生日，2019-02-12", required = false)
    private String birthday;
    /**
     * 联系方式
     */
    @ApiModelProperty(value = "'联系方式'", required = false)
    private String contact;
    /**
     * 老师角色APP账号ID
     */
    @ApiModelProperty(value = "'老师角色APP账号ID'", required = false)
    private String teacherUserId;
    /**
     * 老师角色APP账号ID
     */
    @ApiModelProperty(value = "非DB字段-用户新增老师账号（获取详情时会返回对应账号）", required = false)
    private String teacherAppAccount;
    /**
     * 学校负责人关联合作方学校ID
     */
    @ApiModelProperty(value = "学校负责人关联合作方学校ID", required = false)
    private String schoolId;
    /**
     * 学校负责人关联合作方学校名称
     */
    @ApiModelProperty(value = "非DB字段-学校负责人关联合作方学校名称", required = false)
    private String schoolName;


}
