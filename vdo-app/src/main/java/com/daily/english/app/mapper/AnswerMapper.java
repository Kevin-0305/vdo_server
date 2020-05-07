package com.daily.english.app.mapper;

import com.daily.english.app.domain.Answer;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface AnswerMapper {

    @Insert("insert into ed_answer(examId, userId, answersJson,createTime) " +
            "values(#{examId}, #{userId}, #{answersJson},#{createTime})")
    @Options(useGeneratedKeys = true, keyProperty="id", keyColumn = "id")
    int insert(Answer answer);

    @Select("select id, examId, userId, answersJson " +
            "from ed_answer where id = #{id}")
    Answer findAnswerById(@Param("id") Long id);

    @Delete("delete from ed_answer where userId = #{userId} and  examId =#{examId}")
    void delAnswer(@Param("userId") String userId,@Param("examId") String examId);
    @Select("select id, examId, userId, answersJson " +
            "from ed_answer")
    List<Answer> findAnswerList();

    @Select("select id, examId, userId, answersJson " +
            "from ed_answer where examId = #{examId} and userId = #{userId}  order by createTime asc limit 1")
    Answer findFirstAnswer(Answer answer);

    @Insert("insert into ed_sure_answer(examId, userId, answersJson,createTime) " +
            "values(#{examId}, #{userId}, #{answersJson},#{createTime})")
    @Options(useGeneratedKeys = true, keyProperty="id", keyColumn = "id")
    int insertSureAnswer(Answer answer);
    @Insert("insert into ed_error_first_answer(examId, userId, answersJson,createTime) " +
            "values(#{examId}, #{userId}, #{answersJson},#{createTime})")
    @Options(useGeneratedKeys = true, keyProperty="id", keyColumn = "id")
    int insertErrorFirstAnswer(Answer answer);

	@Select("select   answersJson   " +
			" from ed_sure_answer  where   examId = #{id} and userId = #{userId}   order by createTime  limit 1")
	Answer fingAnswerByExamInfoId(@Param("id")  String id,@Param("userId") String userId);
}
