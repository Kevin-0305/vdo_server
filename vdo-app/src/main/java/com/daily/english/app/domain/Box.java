package com.daily.english.app.domain;

import com.daily.english.app.json.DropJson;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
* class_name: Box
* package: com.vdoenglish.core.entity
* describe: 宝箱
* creat_user: liuzd@chinadailyhk.com
* creat_date: 2019-08-29
* creat_time: 15:39
**/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Box implements Serializable {
	/**
	 * @Fields serialVersionUID:序列化ID
	 */
	private static final long serialVersionUID = 1L;
	/** 宝箱ID */
	@ApiModelProperty(value = "宝箱ID", required = false)
	private Integer id;
	/** 宝箱类型 */
	@ApiModelProperty(value = "宝箱类型", required = false)
	private String boxType;
	/** 竹子数量 */
	@ApiModelProperty(value = "竹子数量", required = false)
	private String bamboo;
	/** 图标 */
	@ApiModelProperty(value = "图标", required = false)
	private String iconImg;
	/** 宝箱详图 */
	@ApiModelProperty(value = "宝箱详图", required = false)
	private String boxImg;
	/** 掉落物品json */
	@ApiModelProperty(value = "掉落物品json实体", required = false)
	private DropJson dropJson;
	/** 创建时间 */
	@ApiModelProperty(value = "创建时间", required = false)
	private String createTime;
	/** 创建人 */
	@ApiModelProperty(value = "创建人", required = false)
	private String createUser;
	/** 更新时间 */
	@ApiModelProperty(value = "更新时间", required = false)
	private String updateTime;
	/** 更新人 */
	@ApiModelProperty(value = "更新人", required = false)
	private String updateUser;
}
