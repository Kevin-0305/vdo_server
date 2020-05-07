package com.daily.english.app.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PartnerClassDto extends PageSearchDto implements Serializable {

    /**
     * 班级（用数字表示S1-S6）用1-6数字
     */
    @ApiModelProperty(value = "班级（用数字表示S1-S6）用1-6数字")
    private String grade;
    /**
     * 学校ID
     */
    @ApiModelProperty(value = "学校ID")
    private Integer schoolId;
    /**
     * 老师ID
     */
    @ApiModelProperty(value = "老师ID")
    private String teacherUserId;
    /**
     * 老师 codeId
     */
    @ApiModelProperty(value = "老师 codeId")
    private String teacherCodeId;



}
