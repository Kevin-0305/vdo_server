package com.daily.english.app.domain;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**

 * describe: 视频收藏表
 * creat_user: zhc
 * creat_date: 2019-09-19
 * creat_time: 14:39
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Collect  implements Serializable {
	   /**
     * @Fields serialVersionUID:序列化ID
     */
    private static final long serialVersionUID = 1L;
	private Integer id;
    @ApiModelProperty(value = "用户ID", required = true, example = "265")
	private Integer uid;
    @ApiModelProperty(value = "视频ID", required = true, example = "10001")
	private Integer vid;
	private String createTime;
	@ApiModelProperty(value = "收藏/点赞 类型：collect 默认collect 收藏  ;praise 点赞", required = true,example = "collect")
	private String type;
	private List<String> ids;
	private int flag;
	private Long mapId;
	private String examId;
}
