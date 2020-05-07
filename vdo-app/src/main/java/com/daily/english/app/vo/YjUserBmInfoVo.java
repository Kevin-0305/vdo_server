package com.daily.english.app.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author
 * @create 2019-08-15 16:19
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class YjUserBmInfoVo implements Serializable {
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
     * 老师姓名
     */
    @ApiModelProperty(value = "老师姓名", required = false)
    private String teacherName;
    /**
     * 老师称呼
     */
    @ApiModelProperty(value = "老师称谓：1.Ms. 、2.Mr. 、3.Mrs.", required = false)
    private Integer teacherCall;
    /**
     * 老师邮箱
     */
    @ApiModelProperty(value = "老师邮箱", required = false)
    private String teacherEmail;
    /**
     * 老师电话
     */
    @ApiModelProperty(value = "老师电话", required = false)
    private String teacherTel;
    /**
     * 学校区域
     */
    @ApiModelProperty(value = "用户填写-学校区域", required = false)
    private String schoolArea;
    /**
     * 学校
     */
    @ApiModelProperty(value = "学校", required = false)
    private Integer schoolId;
    /**
     * 学校名称
     */
    @ApiModelProperty(value = "学校名称-非报名表字段", required = false)
    private String schoolName;
    /**
     * 学校区域
     */
    @ApiModelProperty(value = "学校区域-非报名表字段", required = false)
    private String schoolDistinct;
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
     * 分组:1.初小 2.高小 3.初中 4.高中
     */
    @ApiModelProperty(value = "分组:1.初小 2.高小 3.初中 4.高中", required = false)
    private Integer groupType;
    /**
     * 电子邮箱
     */
    @ApiModelProperty(value = "电子邮箱", required = false)
    private String email;
    /**
     * 上传状态：0.未上传 1.已上传 2.未上传视频 3.未上传演讲稿
     */
    @ApiModelProperty(value = "上传状态：0.未上传 1.已上传 2.未上传视频 3.未上传演讲稿", required = false)
    private Integer uploadStatus;
    /**
     * 评分状态：0.未评分 1.已评分
     */
    @ApiModelProperty(value = "评分状态：0.未评分 1.已评分", required = false)
    private Integer scoreStatus;
    /**
     * 多个评委平均分
     */
    @ApiModelProperty(value = "多个评委平均分", required = false)
    private String score;
    /**
     * 评分进度
     */
    @ApiModelProperty(value = "评分进度", required = false)
    private Integer scoreCount;
    /**
     * 活动ID
     */
    @ApiModelProperty(value = "活动ID", required = false)
    private String eventId;
    /**
     * 评分次数
     */
    @ApiModelProperty(value = "报名类型：0.个人报名 1.老师报名", required = false)
    private Integer type;
    /**
     * 注册码
     */
    @ApiModelProperty(value = "注册码-对应列表中密码列", required = false)
    private String registerCode;
    /**
     * 学校其他名称
     */
    @ApiModelProperty(value = "学校其他名称-非报名表字段", required = false)
    private String schoolOtherName;
    /**
     * 学校英文名称
     */
    @ApiModelProperty(value = "学校英文名称-非报名表字段", required = false)
    private String schoolNameEn;
    /**
     * 电话
     */
    @ApiModelProperty(value = "电话", required = false)
    private String tel;
    /**
     * 年级
     */
    @ApiModelProperty(value = "年级（1-12）", required = false)
    private Integer grade;
    /**
     * 性别：0.女 1.男
     */
    @ApiModelProperty(value = "性别：1.男 2.女", required = false)
    private Integer sex;
}
