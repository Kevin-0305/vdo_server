package com.daily.english.app.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * class_name: ExamRecord
 * package: com.vdoenglish.core.entity
 * describe: 用户提交视频习题实体
 * creat_user: liuzd@chinadailyhk.com
 * creat_date: 2019-08-26
 * creat_time: 15:39
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExamRecord implements Serializable {
    /**
     * @Fields serialVersionUID:序列化ID
     */
    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    @ApiModelProperty(value = "主键ID", required = false)
    private Integer id;
    /**
     * 用户id
     */
    @ApiModelProperty(value = "习题ID", required = true)
    private Integer eid;
    /**
     *
     */
    @ApiModelProperty(value = "用户ID", required = true)
    private Integer uid;
    /**
     * 视频id
     */
    @ApiModelProperty(value = "视频ID", required = true)
    private Integer vid;
    /**
     * 用户答案
     */
    @ApiModelProperty(value = "用户答案", required = true)
    private String userAnswer;
    /**
     * 提交时间
     */
    @ApiModelProperty(value = "提交时间", required = false)
    private String createTime;
}
