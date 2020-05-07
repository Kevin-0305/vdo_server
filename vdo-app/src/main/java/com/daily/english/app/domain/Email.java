package com.daily.english.app.domain;

import com.daily.english.app.json.AttachmentJson;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * class_name: Email
 * package: com.vdoenglish.core.entity
 * describe: 邮件信息
 * creat_user: liuzd@chinadailyhk.com
 * creat_date: 2019-08-23
 * creat_time: 15:39
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Email implements Serializable {
    /**
     * @Fields serialVersionUID:序列化ID
     */
    private static final long serialVersionUID = 1L;
    /**
     * 主键ID
     */
    @ApiModelProperty(value = "主键ID", required = false)
    private Integer id;
    /**
     * 邮件主题
     */
    @ApiModelProperty(value = "邮件主题", required = false)
    private String subject;
    /**
     * 邮件内容
     */
    @ApiModelProperty(value = "邮件内容", required = false)
    private String content;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", required = false)
    private String createTime;
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间", required = false)
    private String updateTime;
    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人", required = false)
    private String createUser;
    /**
     * 修改人
     */
    @ApiModelProperty(value = "修改人", required = false)
    private String updateUser;
    /**
     * 收件人
     */
    @ApiModelProperty(value = "收件人", required = false)
    private String addressees;
    /**
     * 状态：1.已发送 2.等待发送
     */
    @ApiModelProperty(value = "状态：-1.删除 1.已发送 2.等待发送", required = false)
    private Integer status;
    /**
     * 发送时间：定时任务设置
     */
    @ApiModelProperty(value = "发送时间：定时任务设置 格式（yyyy-MM-dd HH:mm）", required = false)
    private String sendTime;
    /**
     * 附件json
     */
    @ApiModelProperty(value = "附件json", required = false)
    private AttachmentJson attachmentJson;
}
