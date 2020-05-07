package com.daily.english.app.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
* class_name: WordPk
* package: com.vdoenglish.core.entity
* describe: 单词PK
* creat_user: liuzd@chinadailyhk.com
* creat_date: 2019-09-02
* creat_time: 15:39
**/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WordPk implements Serializable {
	/**
	 * @Fields serialVersionUID:序列化ID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ID
	 */
	@ApiModelProperty(value = "ID", required = false)
	private Integer id;
	/** 标题 */
	@ApiModelProperty(value = "标题", required = false)
	private String title;
	/** 解释简体中文 */
	@ApiModelProperty(value = "解释简体中文", required = false)
	private String explainCh;
	/** 解释繁体中文 */
	@ApiModelProperty(value = "解释繁体中文", required = false)
	private String explainHk;
	/** 创建时间 */
	@ApiModelProperty(value = "创建时间", required = false)
	private String createTime;
	/** 更新时间 */
	@ApiModelProperty(value = "更新时间", required = false)
	private String updateTime;
	/** 创建人 */
	@ApiModelProperty(value = "创建人", required = false)
	private String createUser;
	/** 修改人 */
	@ApiModelProperty(value = "修改人", required = false)
	private String updateUser;
	/** 级别 */
	@ApiModelProperty(value = "级别", required = false)
	private String level;
}
