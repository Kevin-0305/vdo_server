package com.daily.english.app.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * class_name: PartnerSchool
 * package: com.vdoenglish.core.entity
 * describe: 合作方学校
 * creat_user: liuzd@chinadailyhk.com
 * creat_date: 2019-09-05
 * creat_time: 15:39
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PartnerSchool implements Serializable {
    /**
     * @Fields serialVersionUID:序列化ID
     */
    private static final long serialVersionUID = 1L;
    /**
     * 主键ID
     */
	@ApiModelProperty(value = "主键ID")
    private Integer id;
    /**
     * 合作方名称
     */
	@ApiModelProperty(value = "合作方名称")
    private String name;
    /**
     * 联系方式
     */
	@ApiModelProperty(value = "联系方式")
    private String contact;
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
     * 创建时间
     */
	@ApiModelProperty(value = "创建时间")
    private String createTime;
    /**
     * 创建人
     */
	@ApiModelProperty(value = "创建人")
    private String createUser;
    /**
     * 更新时间
     */
	@ApiModelProperty(value = "更新时间")
    private String updateTime;
    /**
     * 更新人
     */
	@ApiModelProperty(value = "更新人")
    private String updateUser;
    /**
     * 生成code数量
     */
	@ApiModelProperty(value = "生成code数量")
    private Integer numCount;

    /**
     * 老师code列表数据
     */
    @ApiModelProperty(value = "非该表BD字段-老师code列表数据")
    private List<PartnerCode> TeacherCodeList;

}
