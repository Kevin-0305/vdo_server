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
public class EmailDto extends PageSearchDto implements Serializable {

    @ApiModelProperty(value = "状态：0.未发送 1.已发送 2.等待发送", required = false)
    private Integer status;

}
