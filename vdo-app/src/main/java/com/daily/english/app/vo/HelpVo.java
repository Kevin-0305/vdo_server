package com.daily.english.app.vo;

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
public class HelpVo {
    @ApiModelProperty(value = "ID", required = false)
    private Long id;
    @ApiModelProperty(value = "问题_简中", required = true)
    private String question;
    @ApiModelProperty(value = "答案list_简中", required = true)
    private List<String> formatAnswer;
    @ApiModelProperty(value = "问题_英文", required = true)
    private  String question_en;
    @ApiModelProperty(value = "问题_英文", required = true)
    private  String questionEn;
    @ApiModelProperty(value = "答案list_英文", required = true)
    private  List<String> formatAnswer_en;
    @ApiModelProperty(value = "问题_繁中", required = true)
    private  String question_hk;
    @ApiModelProperty(value = "问题_繁中", required = true)
    private  String questionHk;
    @ApiModelProperty(value = "答案list_繁中", required = true)
    private  List<String> formatAnswer_hk;
}
