package com.daily.english.app.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 评委查询条件参数
 *
 * @author
 * @create 2019-08-14 10:48
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CmsUserDto extends PageSearchDto implements Serializable {

    @ApiModelProperty(value = "状态：0.未开通 1.已开通", required = false)
    private Integer status;


    @ApiModelProperty(value = "角色类型：1.超管 2.运营 3.老师 4.评委 5.学校负责人", required = false)
    private Integer roleType;

    /**
     * 多条件分组
     */
    @ApiModelProperty(value = "多条件分组", required = false)
    private List<Integer> roleTypes;
}
