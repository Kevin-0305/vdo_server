package com.daily.english.app.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Help {
    private Long id;
    private String question;
    private String answersJson;
    private List<String> formatAnswer;
    private String question_en;
    private  String questionEn;
    private  String answersJson_en;
    private  String answersJsonEn;
    private  List<String> formatAnswer_en;
    private  String question_hk;
    private  String questionHk;
    private  String answersJson_hk;
    private  String answersJsonHk;
    private  List<String> formatAnswer_hk;
}
