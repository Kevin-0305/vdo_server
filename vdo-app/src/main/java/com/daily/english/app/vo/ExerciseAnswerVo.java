package com.daily.english.app.vo;

import com.daily.english.app.domain.AnswerEntry;
import com.daily.english.app.domain.ExamRecord;
import com.daily.english.app.domain.Node;
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
public class ExerciseAnswerVo {

    @ApiModelProperty(value = "视频ID", required = true)
    private String videoId;
    @ApiModelProperty(value = " 用户 id", required = true)
    private String userId;

    @ApiModelProperty(value = " 答案对", required = true)
    private List<AnswerEntry> answers;

}
