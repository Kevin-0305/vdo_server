package com.daily.english.app.mapper;
import com.daily.english.app.domain.Help;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import java.util.List;

@Mapper
@Component
public interface HelpMapper {

    @Insert("insert into ed_help(question,answersJson,questionEn,answersJsonEn,questionHk,answersJsonHk) " +
            "values(#{question}, #{answersJson},#{question_en},#{answersJson_en},#{question_hk},#{answersJson_hk})")
    int insert(Help help);

    @Select("select id,question,answersJson,question_en,answersJson_en,question_hk,answersJson_hk " +
            "from ed_help where id = #{id}")
    Help findHelpById(@Param("id") Long id);

    @Select("select * " +
            "from ed_help")
    List<Help> findHelpList();
    @Delete("DELETE " +
            "        FROM ed_help " +
            "        WHERE id = #{id}")
    void deleteHelpById(@Param("id") Long id);
}
