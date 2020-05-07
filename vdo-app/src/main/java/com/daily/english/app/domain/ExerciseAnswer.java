package com.daily.english.app.domain;

import java.util.Date;
import java.util.List;

import com.daily.english.app.domain.Node.NodeBuilder;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseAnswer {
	@ApiModelProperty(value = "之前做的答案,", required = false, example = "")
	private List<ExamRecord> examRecords;
	@ApiModelProperty(value = "习题列表", required = true, example = "")
	private List<Node> exerciseList;
	

}
