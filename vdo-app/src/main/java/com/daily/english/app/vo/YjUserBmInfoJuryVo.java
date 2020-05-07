package com.daily.english.app.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author
 * @create 2019-08-20 14:14
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class YjUserBmInfoJuryVo {
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
     * 分组:1.初小 2.高小 3.初中 4.高中
     */
    @ApiModelProperty(value = "分组:1.初小 2.高小 3.初中 4.高中", required = false)
    private Integer groupType;
    /**
     * 注册码
     */
    @ApiModelProperty(value = "注册码", required = false)
    private String registerCode;

    /**
     * 视频链接
     */
    @ApiModelProperty(value = "视频链接", required = false)
    private String videoUrl;

    /**
     * 演讲稿内容
     */
    @ApiModelProperty(value = "演讲稿内容", required = false)
    private String content;

    /**
     *  活动ID
     */
    @ApiModelProperty(value = "活动ID", required = false)
    private  String eventId;

    /**
     * 报名信息是否加锁
     */
    @ApiModelProperty(value = "报名信息是否加锁：0.未加锁 1.加锁", required = false)
    private Integer lockStatus;
}
