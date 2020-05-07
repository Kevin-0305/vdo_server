package com.daily.english.app.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 新闻查询接收参数
 *
 * @author
 * @create 2019-08-14 10:48
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class YjScoreRecordDto extends PageSearchDto implements Serializable {

    @ApiModelProperty(value = "报名ID", required = false)
    private String bmId;

    @ApiModelProperty(value = "评委用户ID", required = false)
    private String userId;

    //@ApiModelProperty(value = "活动ID", required = false)
    //private   String eventId;

    @ApiModelProperty(value = "分组", required = false)
    private Integer groupType;

    @ApiModelProperty(value = "评委用户查询可评分组别", required = false)
    private List<Integer> groupTypes;

}
