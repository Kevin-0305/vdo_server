package com.daily.english.app.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.daily.english.app.domain.ExamRecord;
import com.daily.english.app.domain.FillContent;
import com.daily.english.app.domain.Node;
import com.daily.english.app.domain.Node.NodeBuilder;

/**
 * class_name: ExamRecordDto
 * package: com.daily.english.app.dto
 * describe: 用户提交习题DTO
 * creat_user: liuzd@chinadailyhk.com
 * creat_date: 2019-08-26
 * creat_time: 15:21
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamRecordDto extends PageSearchDto implements Serializable {
    /**
     * 用户id
     */
    @ApiModelProperty(value = "习题ID", required = true)
    private Integer eid;
    /**
     *
     */
    @ApiModelProperty(value = "用户ID", required = true)
    private Integer uid;
    /**
     * 视频id
     */
    @ApiModelProperty(value = "视频ID", required = true)
    private Integer vid;
}
