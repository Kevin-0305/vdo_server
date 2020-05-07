package com.daily.english.app.condition;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * @author zhc
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DownloadVideoCondition {

    private String videoId;
    private String userId;

    private Integer offset;
    private Integer limit;
}
