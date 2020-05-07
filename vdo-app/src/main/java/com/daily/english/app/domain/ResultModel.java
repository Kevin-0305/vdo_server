package com.daily.english.app.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResultModel {
	private boolean flag;
	private  int count;
	private String  answers;
	private String  errorAnswer;
}
