package com.daily.english.app.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 模型基类
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BaseDto implements Serializable {
    private static final long serialVersionUID = 1L;


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }


    /* 开始时间 */
    @ApiModelProperty(value = "开始时间:格式 (yyyy-MM-dd HH:mm:ss)(yyyy-MM-dd)", required = false)
    private String startDate;
    /* 结束时间 */
    @ApiModelProperty(value = "结束时间:格式 (yyyy-MM-dd HH:mm:ss)(yyyy-MM-dd)", required = false)
    private String endDate;

    @ApiModelProperty(value = "搜索日期(查询开始时间和结束时间之内的数据):格式 (yyyy-MM-dd HH:mm:ss)(yyyy-MM-dd)", required = false)
    private String searchDate;


    @ApiModelProperty(value = "搜索关键字", required = false)
    private String keywords;
}
