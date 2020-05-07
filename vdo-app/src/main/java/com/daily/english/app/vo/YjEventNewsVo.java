package com.daily.english.app.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 21世纪活动配置返回VO
 *
 * @author
 * @create 2019-08-14 14:54
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class YjEventNewsVo {
    /**
     * @Fields serialVersionUID:序列化ID
     */
    private static final long serialVersionUID = 1L;
    /**
     * ID
     */
    @ApiModelProperty(value = "ID", required = false)
    private Integer id;
    /**
     * 中文活动名称
     */
    @ApiModelProperty(value = "中文新闻标题", required = false)
    private String titleCh;
    /**
     *
     */
    @ApiModelProperty(value = "标题繁体", required = false)
    private String titleHk;
    /**
     *
     */
    @ApiModelProperty(value = "标题英文", required = false)
    private String titleEn;
    /**
     * 中文活动名称
     */
    @ApiModelProperty(value = "活动名称", required = false)
    private String eventName;
    /**
     * 新闻封面图
     */
    @ApiModelProperty(value = "新闻封面图", required = false)
    private String imgUrl;
    /**
     * 活动ID
     */
    @ApiModelProperty(value = "活动ID", required = false)
    private String eventId;
    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人", required = false)
    private String createUser;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", required = false)
    private String createTime;
    /**
     * 修改人
     */
    @ApiModelProperty(value = "修改人", required = false)
    private String updateUser;
    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间", required = false)
    private String updateTime;
}
