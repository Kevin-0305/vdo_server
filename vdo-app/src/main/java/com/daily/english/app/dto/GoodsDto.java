package com.daily.english.app.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsDto extends PageSearchDto implements Serializable {

    @ApiModelProperty(value = "0.默认 1.上架 2.下架", required = false)
    private Integer status;

}
