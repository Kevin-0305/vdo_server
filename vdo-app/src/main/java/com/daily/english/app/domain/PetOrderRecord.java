package com.daily.english.app.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
* class_name: PetOrderRecord
* package: com.vdoenglish.core.entity
* describe: 道具购买记录
* creat_user: liuzd@chinadailyhk.com
* creat_date: 2019-09-17
* creat_time: 15:39
**/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PetOrderRecord implements Serializable {
	/**
	 * @Fields serialVersionUID:序列化ID
	 */
	private static final long serialVersionUID = 1L;
	/** 主键 */
	@ApiModelProperty(value = "主键ID", required = true)
	private Integer id;
	/** 商品ID */
	@ApiModelProperty(value = "商品ID", required = true)
	private Integer goodsId;
	/** 宠物ID */
	@ApiModelProperty(value = "宠物ID", required = true)
	private Integer petId;
	/** 数量 */
	@ApiModelProperty(value = "数量", required = true)
	private Integer amount;
	/** 总价 */
	@ApiModelProperty(value = "总价", required = true)
	private Double totalValue;
	/** 购买日期 */
	@ApiModelProperty(value = "购买日期", required = true)
	private String createTime;
}
