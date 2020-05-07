package com.daily.english.app.mapper;

import com.daily.english.app.domain.Questions;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface QuestionsMapper {

    @Select("select id, title, sum_count, type, select5w_Json, option_Json, fillout_Json, graphic_Json, " +
            "filloutSent_Json, matching_Json, create_time, update_time, create_account, update_account, " +
            "web_title " +
            "from ed_questions where id = #{id}")
    Questions findQuestionsById(@Param("id") String id);

    @Select({"<script>" +
            "select id, title, sum_count, type, select5w_Json, option_Json, fillout_Json, graphic_Json, " +
            "filloutSent_Json, matching_Json, create_time, update_time, create_account, update_account, " +
            "web_title " +
            "from ed_questions where id in " +
            "<foreach item='id' collection='ids' open='(' separator=',' close=')'>#{id}</foreach>" +
            "</script>"})
    List<Questions> findQuestionsListByIds(@Param("ids") List<String> ids);
}
