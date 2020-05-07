package com.daily.english.app.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
* class_name: WorkPkRobot
* package: com.vdoenglish.core.entity
* describe: 单词pk机器人信息
* creat_user: liuzd@chinadailyhk.com
* creat_date: 2019-09-19
* creat_time: 15:39
**/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WordPkRobot implements Serializable {
	/**
	 * @Fields serialVersionUID:序列化ID
	 */
	private static final long serialVersionUID = 1L;
	/** 主键ID */
	@ApiModelProperty(value = "主键ID", required = false)
	private Integer id;
	/** 机器人名称 */
	@ApiModelProperty(value = "机器人名称", required = false)
	private String robotName;
	/** 机器人头像 */
	@ApiModelProperty(value = "机器人头像", required = false)
	private String headUrl;
}
