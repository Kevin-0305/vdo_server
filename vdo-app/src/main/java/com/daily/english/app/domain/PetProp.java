package com.daily.english.app.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
* class_name: PetProp
* package: com.vdoenglish.core.entity
* describe: 宠物道具
* creat_user: liuzd@chinadailyhk.com
* creat_date: 2019-09-17
* creat_time: 15:39
**/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PetProp implements Serializable {
	/**
	 * @Fields serialVersionUID:序列化ID
	 */
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "主键ID", required = true)
	private Integer id;
	/** 宠物ID */
	@ApiModelProperty(value = "宠物ID", required = true)
	private Integer petId;
	/** 道具ID */
	@ApiModelProperty(value = "道具ID", required = true)
	private Integer propId;
	/** 数量 */
	@ApiModelProperty(value = "数量", required = true)
	private Integer amount;
}
