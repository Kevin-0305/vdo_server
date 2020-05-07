package com.daily.english.app.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
* class_name: Pet
* package: com.vdoenglish.core.entity
* describe: 宠物属性表
* creat_user: liuzd@chinadailyhk.com
* creat_date: 2019-09-17
* creat_time: 15:39
**/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Pet implements Serializable {
	/**
	 * @Fields serialVersionUID:序列化ID
	 */
	private static final long serialVersionUID = 1L;
	/** 主键 */
	@ApiModelProperty(value = "宠物ID", required = false)
	private Integer id;
	/** 用户ID */
	@ApiModelProperty(value = "用户ID", required = false)
	private Integer userId;
	/** 成长值 */
	@ApiModelProperty(value = "成长值", required = false)
	private Integer growth;
	/** 宠物等级 */
	@ApiModelProperty(value = "宠物等级", required = false)
	private Integer level;
	/** 出生日期 */
	@ApiModelProperty(value = "出生日期", required = false)
	private String createTime;
	/** 竹币数量 */
	@ApiModelProperty(value = "非宠物表字段-竹币数量", required = false)
	private Double bamboo;
	/** 最后一次喂食时间 */
	@ApiModelProperty(value = "最后一次喂食时间", required = false)
	private String lastFeedTime;

}
