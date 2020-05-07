package com.daily.english.app.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * class_name: ExerciseDto
 * package: com.daily.english.app.dto
 * describe: 视频习题DTO
 * creat_user: liuzd@chinadailyhk.com
 * creat_date: 2019-08-26
 * creat_time: 15:23
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseDto extends PageSearchDto implements Serializable {

    @ApiModelProperty(value = "视频ID", required = false)
    private Integer vid;
}
