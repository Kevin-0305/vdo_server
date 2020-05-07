package com.daily.english.app.json;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 图片类JSON
 *
 * @author
 * @create 2019-08-06 09:44
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SponsorsJson implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "简中图")
    private String picChUrl;
    @ApiModelProperty(value = "繁中图")
    private String picHkUrl;
    @ApiModelProperty(value = "英文图")
    private String picEnUrl;
}
