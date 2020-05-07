package com.daily.english.app.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameGoodsDto extends PageSearchDto implements Serializable {

    @ApiModelProperty(value = "商品分组：1.（1-50） 2.(51-100) 3.(101-150) 以此类推（默认查询1）", required = false)
    private Integer sourceType;

}
