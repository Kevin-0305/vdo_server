package com.daily.english.app.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRankListVo {

	@ApiModelProperty(value = "用户名称", required = true)
	private String userName;
	@ApiModelProperty(value = "竹子", required = false)
	private Long bamboo;
	@ApiModelProperty(value = "经验", required = false)
	private Long exp;
	@ApiModelProperty(value = "排名", required = true)
	private Long ranks;
	@ApiModelProperty(value = "类型(天 = day，周 = week ", required = true)
	private  String type;
	@ApiModelProperty(value = "用户头像", required = true)
	private String userImg;
	@ApiModelProperty(value = "用户ID", required = false)
	private Long userId;
	@ApiModelProperty(value = "正为升，负为降", required = true)
	private int rankChange;
}
