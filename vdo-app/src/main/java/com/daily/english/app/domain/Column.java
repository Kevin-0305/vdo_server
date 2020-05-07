package com.daily.english.app.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Column {

    private Long columnID;

    private Integer index;

    private String showFont;

    private Integer status;
}
