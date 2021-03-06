package com.daily.english.app.condition;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CollectCondition {
    private String uid;
    private String vid;
    private String type;

    private Integer offset;
    private Integer limit;
}
