package com.daily.english.app.domain;

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
public class StudyExamInfo {
	private  Long id;
	@ApiModelProperty(value = "name", required = true)
	private String name;
	@ApiModelProperty(value = "班级", required = true)
	private String classId;
	@ApiModelProperty(value = "班级名称", required = true)
	private String className;
	@ApiModelProperty(value = "性别", required = true)
	private String gender;
	@ApiModelProperty(value = "First score 平均分", required = true)
	private String firstScore;
	@ApiModelProperty(value = "首考Part1分数的平均分", required = true)
	private String comprehension;
	@ApiModelProperty(value = "首考Part2分数的平均分", required = true)
	private String vocabulary;
	@ApiModelProperty(value = "首考Part3分数的平均分", required = true)
	private String grammar;
	@ApiModelProperty(value = "最高分数", required = true)
	private String highestScore;
	@ApiModelProperty(value = "Part1最高分数", required = true)
	private String highestComprehension;
	@ApiModelProperty(value = "Part2分数", required = true)
	private String highestVocabulary;
	@ApiModelProperty(value = "Part3最高分数", required = true)
	private String highestGrammar;
	@ApiModelProperty(value = "学生ID", required = false)
	private String userId;
	@ApiModelProperty(value = "班级卡片", required = false)
	private String  checkpoint;
	@ApiModelProperty(value = "班级排名", required = false)
	private int  ranking;
	private String level;
	private String examId;
	private int  nodeNum;
}
