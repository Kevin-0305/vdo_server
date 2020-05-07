package com.daily.english.app.vo;

import com.daily.english.app.domain.PartnerClass;
import com.daily.english.app.domain.StudyExamInfo;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudyInfoVo {
    @ApiModelProperty(value = "分页数据", required = false)
    public PageInfo<StudyExamInfo> pageInfo;
    @ApiModelProperty(value = "班级", required = false)
    private List<PartnerClass> classList;
    @ApiModelProperty(value = "图表信息", required = false)
    private StudyAverageVo studyAverageVo;

}
