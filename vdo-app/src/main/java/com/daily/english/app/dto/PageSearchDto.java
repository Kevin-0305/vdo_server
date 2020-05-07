package com.daily.english.app.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 分页查询对象。
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageSearchDto extends BaseDto implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "当前页", required = false)
    private int pageNo = 1;
    @ApiModelProperty(value = "分页大小", required = false)
    private int pageSize = 10;


}
