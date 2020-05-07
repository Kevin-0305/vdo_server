package com.daily.english.app.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserIndex {
  @ApiModelProperty(value = "主键用户ID", required = true)
  private Long id;
  private String word;
  private String question;
  private String favourite;
  private String download;


}
