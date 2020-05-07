package com.daily.english.app.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
* class_name: WordPkRecord
* package: com.vdoenglish.core.entity
* describe: 用户单词pk记录
* creat_user: liuzd@chinadailyhk.com
* creat_date: 2019-09-16
* creat_time: 15:39
**/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WordPkRecord implements Serializable {
	/**
	 * @Fields serialVersionUID:序列化ID
	 */
	private static final long serialVersionUID = 1L;
	/** 主键 */
	@ApiModelProperty(value = "主键ID", required = false)
	private Integer id;
	/** 用户ID */
	@ApiModelProperty(value = "用户ID", required = false)
	private Integer userId;
	/** 胜负：1.胜利 0.失败 */
	@ApiModelProperty(value = "胜负：1.胜利 0.失败", required = false)
	private Integer isVictory;
	/** 领取竹子状态：1.领取 0.未领取 */
	@ApiModelProperty(value = "领取竹子状态：1.领取 0.未领取", required = false)
	private Integer isReceive;
	/** 竹子奖励 */
	@ApiModelProperty(value = "竹子奖励", required = false)
	private Integer bambooReward;
	/** PK时间 */
	@ApiModelProperty(value = "PK时间(由服务器添加)", required = false)
	private String createTime;
}
