package com.daily.english.app.condition;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 试卷
 *
 * @author admin
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExamInfoCondition {

    private static final long serialVersionUID = 1L;

    private String id;

    private String setter;
    /**
     * 试卷标题
     */
    private String title;

    private String level;

    private String examType;

    private Long videoId;

    private String createTime;

    private String createAccount;

    private String updateAccount;

    private String updateTime;

    private Integer state = 0;

    private Integer offset;

    private Integer limit;
}
