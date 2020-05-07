package com.daily.english.app.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * class_name: PartnerClass
 * package: com.vdoenglish.core.entity
 * describe: 合作方班级
 * creat_user: liuzd@chinadailyhk.com
 * creat_date: 2019-09-05
 * creat_time: 15:39
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PartnerClass implements Serializable {
    /**
     * @Fields serialVersionUID:序列化ID
     */
    private static final long serialVersionUID = 1L;
    /**
     * ID
     */
	@ApiModelProperty(value = "ID")
    private Integer id;
    /**
     * 班级名称
     */
	@ApiModelProperty(value = "班别名称")
    private String name;
    /**
     * 班级
     */
    @ApiModelProperty(value = "班级（用数字表示S1-S6）用1-6数字")
    private Integer grade;
    /**
     * 老师codeId
     */
	@ApiModelProperty(value = "老师codeId")
    private Integer teacherCodeId;
    /**
     * 老师名称
     */
    @ApiModelProperty(value = "老师名称")
    private String teacherName;
    /**
     * 开通权限：S1、S2、S3
     */
	@ApiModelProperty(value = "开通权限：S1、S2、S3多个以逗号隔开(,)")
    private String level;
    /**
     * 学校ID
     */
	@ApiModelProperty(value = "学校ID")
    private Integer schoolId;
    /**
     * 学校名称
     */
    @ApiModelProperty(value = "学校名称")
    private String schoolName;
    /**
     * 生成code数量
     */
	@ApiModelProperty(value = "生成code数量")
    private Integer numCount;
    /**
     * 开始时间
     */
    @ApiModelProperty(value = "开始时间(格式：yyyy-MM-dd)")
    private String startTime;
    /**
     * 结束时间
     */
    @ApiModelProperty(value = "结束时间(格式：yyyy-MM-dd)")
    private String endTime;
    /**
     * 创建日期
     */
	@ApiModelProperty(value = "创建日期")
    private String createTime;
    /**
     * 创建人
     */
	@ApiModelProperty(value = "创建人")
    private String createUser;
    /**
     * 更新日期
     */
	@ApiModelProperty(value = "更新日期")
    private String updateTime;
    /**
     * 更新人
     */
	@ApiModelProperty(value = "更新人")
    private String updateUser;
    /**
     * 学生code列表数据
     */
    @ApiModelProperty(value = "非该表BD字段-学生code列表数据")
    private List<PartnerCode> studentCodeList;
}
