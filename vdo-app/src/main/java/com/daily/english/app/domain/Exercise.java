package com.daily.english.app.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
* class_name: Exercise
* package: com.vdoenglish.core.entity
* describe: 
* creat_user: liuzd@chinadailyhk.com
* creat_date: 2019-08-26
* creat_time: 15:39
**/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Exercise implements Serializable {
	/**
	 * @Fields serialVersionUID:序列化ID
	 */
	private static final long serialVersionUID = 1L;
	/** id */
	@ApiModelProperty(value = "主键ID", required = false)
	private Integer id;
	/** 视频id */
	@ApiModelProperty(value = "视频id", required = true)
	private Integer vid;
	/** 问题 */
	@ApiModelProperty(value = "问题", required = true)
	private String question;
	/** 选项A */
	@ApiModelProperty(value = "选项A", required = true)
	private String chooseA;
	/** 选项B */
	@ApiModelProperty(value = "选项B", required = true)
	private String chooseB;
	/** 选项C */
	@ApiModelProperty(value = "选项C", required = true)
	private String chooseC;
	/** 选项D */
	@ApiModelProperty(value = "选项D", required = true)
	private String chooseD;
	/** 答案选项 */
	@ApiModelProperty(value = "答案选项", required = true)
	private String answer;
	/** 答案解释 */
	@ApiModelProperty(value = "答案解释", required = true)
	private String explan;

}
