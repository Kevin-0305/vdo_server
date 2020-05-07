package com.daily.english.app.condition;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VideoCondition {

    private String title;

    private String browse;

    private String praise;

    private String collect;

    private String type;

    private String level;

    private String editor;

    private String hot;

    private String editorPick;

    private Integer isRelease;

    private String isOpen;

    private String isQuiz;

    private String timeLength;

    private String status;

    private String remark;

    private String lastEditor;

    private Integer offset;

    private Integer limit;
}
