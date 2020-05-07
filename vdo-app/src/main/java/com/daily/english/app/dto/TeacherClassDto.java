package com.daily.english.app.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherClassDto extends PageSearchDto implements Serializable {

    /**
     * 学校ID
     */
    @ApiModelProperty(value = "学校ID(学校负责人传学校ID查询)")
    private Integer schoolId;
    /**
     * 老师ID
     */
    @ApiModelProperty(value = "老师ID(通过APP用户ID查询)")
    private String teacherUserId;

}
