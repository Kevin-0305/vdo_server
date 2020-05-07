package com.daily.english.app.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
* class_name: PetLog
* package: com.vdoenglish.core.entity
* describe: 宠物喂食活动记录
* creat_user: liuzd@chinadailyhk.com
* creat_date: 2019-09-17
* creat_time: 15:39
**/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PetLog implements Serializable {
	/**
	 * @Fields serialVersionUID:序列化ID
	 */
	private static final long serialVersionUID = 1L;
	/** 主键 */
	@ApiModelProperty(value = "主键ID", required = true)
	private Integer id;
	/** 活动类别：1.默认喂食 *其他后期考虑 */
	@ApiModelProperty(value = "活动类别：1.默认喂食 *其他后期考虑", required = true)
	private Integer actionType;
	/** 宠物ID */
	@ApiModelProperty(value = "宠物ID", required = true)
	private Integer petId;
	/** 操作时间 */
	@ApiModelProperty(value = "操作时间", required = true)
	private String createTime;

}
