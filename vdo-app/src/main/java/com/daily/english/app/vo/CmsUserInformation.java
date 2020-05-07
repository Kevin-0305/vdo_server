package com.daily.english.app.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CmsUserInformation {
    @ApiModelProperty(value = "主键用户ID", required = true)
    private String id;
    @ApiModelProperty(value = "用户昵称", required = false)
    private String name;
    /**
     * 性别：男：male  女：female
     */
    @ApiModelProperty(value = "男性->male，女性->female", required = false)
    private String gender;
    /**
     * 生日，2019-02-12
     */
    @ApiModelProperty(value = "生日，2019-02-12", required = false)
    private String birthday;
    /**
     * 联系方式
     */
    @ApiModelProperty(value = "联系方式", required = false)
    private String contact;
}
