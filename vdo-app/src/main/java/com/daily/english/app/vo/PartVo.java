package com.daily.english.app.vo;

import com.daily.english.app.domain.Node;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 部分
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PartVo {
    @ApiModelProperty(value = "部分名称", required = false, example = "Part1 Comprehension")
    private String partName;

    @ApiModelProperty(value = "节点列表", required = false)
    private List<Node> nodeList;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", required = true)
    private String createTime;
    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人", required = true)
    private String createAccount;
    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人", required = true)
    private String updateAccount;
    /**
     * 更新日期
     */
    @ApiModelProperty(value = "更新日期", required = true)
    private String updateTime;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态", required = true)
    private Integer state;
}
