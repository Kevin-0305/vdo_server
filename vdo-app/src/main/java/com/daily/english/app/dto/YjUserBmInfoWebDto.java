package com.daily.english.app.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class YjUserBmInfoWebDto {
    /**
     * @Fields serialVersionUID:序列化ID
     */
    private static final long serialVersionUID = 1L;
    /**
     *
     */
    @ApiModelProperty(value = "主键ID", required = false)
    private Integer id;
    /**
     * 学校区域
     */
    @ApiModelProperty(value = "学校区域", required = false)
    private String schoolArea;
    /**
     * 学校
     */
    @ApiModelProperty(value = "学校", required = false)
    private Integer schoolId;
    /**
     * 学校其他名称
     */
    @ApiModelProperty(value = "学校其他名称-用户在学校列表中选择了其他，旁边的输入框字段", required = false)
    private String schoolOtherName;
    /**
     * 姓名中文
     */
    @ApiModelProperty(value = "姓名中文", required = false)
    private String nameCh;
    /**
     * 姓名英文
     */
    @ApiModelProperty(value = "姓名英文", required = false)
    private String nameEn;
    /**
     * 性别：1.男 2.女
     */
    @ApiModelProperty(value = "性别：1.男 2.女", required = false)
    private Integer sex;
    /**
     * 年级
     */
    @ApiModelProperty(value = "年级（1-12）", required = false)
    private Integer grade;
    /**
     * 分组
     */
    @ApiModelProperty(value = "分组：1.初小 2.高小 3.初中 4.高中", required = false)
    private Integer groupType;
    /**
     * 电子邮箱
     */
    @ApiModelProperty(value = "电子邮箱", required = false)
    private String email;
    /**
     * 电话
     */
    @ApiModelProperty(value = "电话", required = false)
    private String tel;
    /**
     * 演讲稿
     */
    @ApiModelProperty(value = "演讲稿", required = false)
    private String content;
    /**
     * 视频链接
     */
    @ApiModelProperty(value = "视频链接", required = false)
    private String videoUrl;
    /**
     * 活动ID
     */
    @ApiModelProperty(value = "活动ID-必填", required = false)
    private String eventId;
    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID-必填", required = false)
    private String userId;

}
