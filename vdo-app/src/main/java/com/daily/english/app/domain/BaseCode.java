package com.daily.english.app.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
* class_name: BaseCode
* package: com.vdoenglish.core.entity
* describe: code码库
* creat_user: liuzd@chinadailyhk.com
* creat_date: 2019-09-25
* creat_time: 15:39
**/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BaseCode implements Serializable {
	/**
	 * @Fields serialVersionUID:序列化ID
	 */
	private static final long serialVersionUID = 1L;
	/**  */
	private String code;
	/** 状态：0.未使用 1.已使用（已使用的code会关联到合作方code码中） */
	private Integer status;
	/** 生成时间 */
	private String createTime;
}
