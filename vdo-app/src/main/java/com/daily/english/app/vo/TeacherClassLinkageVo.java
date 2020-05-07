package com.daily.english.app.vo;

import com.daily.english.app.domain.Subtitle;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeacherClassLinkageVo {


    @ApiModelProperty(value = "leval")
    private Set<String> leval;

    @ApiModelProperty(value = "当前等级下所有班级")
    private List<TeacherClassVo> teacherClassVos;
}
