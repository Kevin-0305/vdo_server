package com.daily.english.app.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 用户路点表
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserMap {
  @ApiModelProperty(value = "主键用户ID", required = true)

  private Long id;
  @ApiModelProperty(value = "S1按照路点map id顺序，每个路点的用户真实得分", required = true)
  private String s1MapScore;
  @ApiModelProperty(value = "S2按照路点mapid顺序，每个路点的用户真实得分", required = true)
  private String s2MapScore;
  @ApiModelProperty(value = "S3按照路点mapid顺序，每个路点的用户真实得分", required = true)
  private String s3MapScore;
  @ApiModelProperty(value = "S4按照路点mapid顺序，每个路点的用户真实得分", required = true)
  private String s4MapScore;
  @ApiModelProperty(value = "S5按照路点mapid顺序，每个路点的用户真实得分", required = true)
  private String s5MapScore;
  @ApiModelProperty(value = "S6按照路点mapid顺序，每个路点的用户真实得分", required = true)
  private String s6MapScore;
  @ApiModelProperty(value = "o 首次进入  1非首次", required = true)
  private Integer isfirst;
  @ApiModelProperty(value = "解锁关卡（s1,s2,s3）", required = true)
  private String unblock;

}
