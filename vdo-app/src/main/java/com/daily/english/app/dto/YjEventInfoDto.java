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
public class YjEventInfoDto extends PageSearchDto implements Serializable {

    @ApiModelProperty(value = "状态：-1.删除 0.默认 1.上架 2.下架", required = false)
    private Integer status;

}
