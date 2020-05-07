package com.daily.english.app.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 新闻查询接收参数
 *
 * @author
 * @create 2019-08-14 10:48
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class YjEventNewsDto extends PageSearchDto implements Serializable {

    @ApiModelProperty(value = "活动ID", required = false)
    private String eventId;

}
