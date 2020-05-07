package com.daily.english.app.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppVersion {

    private Long id;
    /**
     * 1 = ios  2 = android
     */
    @ApiModelProperty(value = " 1 = ios  2 = android", required = true)
    private int type;
    @ApiModelProperty(value = " 最新版本号", required = true)
    private String newVersion;
    @ApiModelProperty(value = "下载URL", required = true)
    private String apkUrl;
    @ApiModelProperty(value = "更新文案", required = true)
    private String updateDescription;
    @ApiModelProperty(value = "1强制更新，0不用", required = true)
    private Integer forceUpdate;
    @ApiModelProperty(value = "强制更新到版本号", required = true)
    private String forceVersion;


}
