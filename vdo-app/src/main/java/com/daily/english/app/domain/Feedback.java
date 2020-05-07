package com.daily.english.app.domain;

import com.daily.english.app.json.PicJson;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
* class_name: Feedback
* package: com.vdoenglish.core.entity
* describe: 问题反馈
* creat_user: liuzd@chinadailyhk.com
* creat_date: 2019-08-22
* creat_time: 15:39
**/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Feedback implements Serializable {
	/**
	 * @Fields serialVersionUID:序列化ID
	 */

	private static final long serialVersionUID = 1L;
	/** 主键ID */
	@ApiModelProperty(value = "主键ID", required = true)
	private Integer id;
	/** 附件json */
	@ApiModelProperty(value = "附件json", required = true)
	private PicJson fileJson;
	/** 联系方式 */
	@ApiModelProperty(value = "联系方式", required = true)
	private String contact;
	/** 用户ID */
	@ApiModelProperty(value = "用户ID", required = true)
	private String userId;
	/** 用户ID */
	@ApiModelProperty(value = "非该表字段-用户账号", required = true)
	private String useraccount;
	/** 用户ID */
	@ApiModelProperty(value = "非该表字段-用户昵称", required = true)
	private String nickname;
	/** 反馈内容 */
	@ApiModelProperty(value = "反馈内容", required = true)
	private String content;
	/** 提交时间 */
	@ApiModelProperty(value = "提交时间", required = true)
	private String createTime;
	/** 处理时间 */
	@ApiModelProperty(value = "处理时间", required = true)
	private String processingTime;
	/** 处理状态：0.待处理 1.已处理 2.处理中 */
	@ApiModelProperty(value = "处理状态：0.待处理 1.已处理 2.处理中", required = true)
	private Integer status;
	@ApiModelProperty(value = "平台 1 iphone，2ipad，3androidPhone，4androidPad，5PC", required = true)
	private Integer questionType;
	@ApiModelProperty(value = "1视频问题，2试卷问题 ,3其他,4.21世纪杯报名网站", required = true)
	private Integer sourceType;


}
