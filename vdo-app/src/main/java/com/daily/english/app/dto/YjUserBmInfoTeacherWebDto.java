package com.daily.english.app.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class YjUserBmInfoTeacherWebDto {
    /**
     * @Fields serialVersionUID:序列化ID
     */
    private static final long serialVersionUID = 1L;
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
     * 老师邮箱
     */
    @ApiModelProperty(value = "老师邮箱", required = false)
    private String teacherTel;
    /**
     * 活动ID
     */
    @ApiModelProperty(value = "活动ID", required = false)
    private String eventId;
    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID", required = false)
    private String userId;
    /**
     * 学生资料集合
     */
    @ApiModelProperty(value = "学生资料集合", required = false)
    private List<YjUserBmInfoWebDto> userBmInfoList;
}
