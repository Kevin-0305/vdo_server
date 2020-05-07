package com.daily.english.app.domain;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * class_name: YjUserBmInfo
 * package: com.vdoenglish.core.entity
 * describe: 用户报名信息表
 * creat_user: liuzd@chinadailyhk.com
 * creat_date: 2019-08-13
 * creat_time: 15:39
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class YjUserBmInfo implements Serializable {
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
     * 老师称呼
     */
    @ApiModelProperty(value = "老师称谓：1.Ms. 、2.Mr. 、3.Mrs.", required = false)
    private Integer teacherCall;
    /**
     * 老师姓名
     */
    @ApiModelProperty(value = "老师姓名", required = false)
    private String teacherName;
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
    @ApiModelProperty(value = "学校区域", required = false)
    private String schoolArea;
    /**
     * 学校
     */
    @ApiModelProperty(value = "学校", required = false)
    private Integer schoolId;
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
     * 性别：0.女 1.男
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
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", required = false)
    private String createTime;
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
     * 报名类型：0.个人报名 1.老师报名
     */
    @ApiModelProperty(value = "报名类型：0.个人报名 1.老师报名", required = false)
    private Integer type;

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
     *
     */
    @ApiModelProperty(value = "多个评委平均分", required = false)
    private String score;
    /**
     * 评分次数
     */
    @ApiModelProperty(value = "评分进度", required = false)
    private Integer scoreCount;
    /**
     * 评分次数
     */
    @ApiModelProperty(value = "活动ID", required = false)
    private String eventId;
    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID", required = false)
    private String userId;
    /**
     * 老师用户ID
     */
    @ApiModelProperty(value = "老师用户ID", required = false)
    private String teacherUserId;

    /**
     * 注册码
     */
    @ApiModelProperty(value = "注册码", required = false)
    private String registerCode;
    /**
     * 评分加锁状态
     */
    @ApiModelProperty(value = "评分加锁状态：0.正常 1.正在评分", required = false)
    private Integer lockStatus;
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
     * 学校区域
     */
    @ApiModelProperty(value = "学校区域-非报名表字段", required = false)
    private String schoolDistinctEn;

    /**
     * 学校英文名称
     */
    @ApiModelProperty(value = "学校英文名称-非报名表字段", required = false)
    private String schoolNameEn;
    /**
     * 学校其他名称
     */
    @ApiModelProperty(value = "学校其他名称-用户在学校列表中选择了其他，旁边的输入框字段", required = false)
    private String schoolOtherName;

    /**
     * 评委评分记录集合
     */
    @ApiModelProperty(value = "非该表字段-评委评分记录集合", required = false)
    private List<YjScoreRecord> scoreRecordList;

}
