package com.daily.english.app.domain;

import com.daily.english.app.json.DropJson;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * describe: 消息
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Notic implements Serializable {
    /**
     * @Fields serialVersionUID:序列化ID
     */
    private static final long serialVersionUID = 1L;
    /**
     * 消息ID
     */
    @ApiModelProperty(value = "消息ID", required = false)
    private Integer id;
    @ApiModelProperty(value = "标题", required = false)
    private String usTitle;
    @ApiModelProperty(value = "内容", required = false)
    private String usContent;
    @ApiModelProperty(value = "标题", required = false)
    private String cnTitle;
    @ApiModelProperty(value = "内容", required = false)
    private String cnContent;
    @ApiModelProperty(value = "标题", required = false)
    private String hkTitle;
    @ApiModelProperty(value = "内容", required = false)
    private String hkContent;
    @ApiModelProperty(value = "用户Id", required = false)
    private String userId;
    @ApiModelProperty(value = "url", required = false)
    private String url;
    @ApiModelProperty(value = "是否已读（1是，2否）", required = false)
    private String isRead;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", required = false)
    private String createTime;
    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人", required = false)
    private String createUser;
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间", required = false)
    private String updateTime;
    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人", required = false)
    private String updateUser;
}
