package com.daily.english.app.mapper;

import com.daily.english.app.condition.ExamInfoCondition;
import com.daily.english.app.domain.ExamInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ExamInfoMapper {

    @Insert("insert into ed_exam_info(id, setter, title, level, exam_type, video_id, " +
            "question_num, comprehension_num, vocabulary_num, grammar_num, " +
            "partListJson, create_time, update_time, create_account, update_account, state) " +
            "values(#{id}, #{setter}, #{title}, #{level}, #{examType}, #{videoId}, " +
            "#{questionNum}, #{comprehensionNum}, #{vocabularyNum}, #{grammarNum}, " +
            "#{partListJson}, #{createTime}, #{updateTime}, #{createAccount}, #{updateAccount}, #{state})")
    void insertExamInfo(ExamInfo examInfo);

    @Update("update ed_exam_info set setter=#{setter}, title=#{title}, level=#{level}, exam_type=#{examType}, " +
            "video_id=#{videoId}, question_num=#{questionNum}, comprehension_num=#{comprehensionNum}, " +
            "vocabulary_num=#{vocabularyNum}, grammar_num=#{grammarNum}, update_time=#{updateTime}, " +
            "partListJson=#{partListJson}, update_account=#{updateAccount}, state=#{state} " +
            "where id=#{id}")
    int updateExamInfo(ExamInfo examInfo);

    @Delete("delete from ed_exam_info where id = #{id}")
    void deleteExamInfoById(@Param("id") String id);

    @Select("select id, setter, title, level, exam_type, video_id, " +
            "question_num, comprehension_num, vocabulary_num, grammar_num, " +
            "partListJson, create_time, update_time, create_account, update_account, " +
            "state " +
            "from ed_exam_info where id = #{id}")
    ExamInfo findExamInfoByExamId(@Param("id") String id);

    @Select("select id, setter, title, level, exam_type, video_id, " +
            "question_num, comprehension_num, vocabulary_num, grammar_num, " +
            "partListJson, create_time, update_time, create_account, update_account, " +
            "state " +
            "from ed_exam_info order by create_time desc ")
    List<ExamInfo> findExamInfoList();

    @Select("<script>" +
            "select count(1) from ed_exam_info " +
            "where 1=1 " +
            "  <if test=\"examType != null and examType != '' \"> " +
            "    AND  exam_type = #{examType,jdbcType=VARCHAR} " +
            "  </if> " +
            "  <if test=\"title != null and title != '' \"> " +
            "    AND  title LIKE CONCAT('%', #{title, jdbcType=VARCHAR} , '%')" +
            "  </if> " +
            "  <if test=\"level != null and level != '' \"> " +
            "    AND  level = #{level,jdbcType=VARCHAR} " +
            "  </if> " +
            "</script>")
    Integer findExamInfoCount(ExamInfoCondition examInfoCondition);

    @Select("<script>" +
            "select id, setter, title, level, exam_type, video_id, " +
            "question_num, comprehension_num, vocabulary_num, grammar_num, " +
            "partListJson, create_time, update_time, create_account, update_account, " +
            "state " +
            "from ed_exam_info where 1=1 " +
            "  <if test=\"examType != null and examType != '' \"> " +
            "    AND  exam_type = #{examType,jdbcType=VARCHAR} " +
            "  </if> " +
            "  <if test=\"title != null and title != '' \"> " +
            "    AND  title LIKE CONCAT('%', #{title, jdbcType=VARCHAR} , '%')" +
            "  </if> " +
            "  <if test=\"level != null and level != '' \"> " +
            "    AND  level = #{level,jdbcType=VARCHAR} " +
            "  </if> " +
            "order by create_time desc limit #{offset}, #{limit}" +
            "</script>")
    List<ExamInfo> findExamInfoByPage(ExamInfoCondition examInfoCondition);

    @Select({"<script>" +
            "select id, setter, title, level, exam_type, video_id, " +
            "question_num, comprehension_num, vocabulary_num, grammar_num, " +
            "partListJson, create_time, update_time, create_account, update_account, " +
            "state " +
            "from ed_exam_info where id in " +
            "<foreach item='id' collection='ids' open='(' separator=',' close=')'>#{id}</foreach>" +
            "</script>"})
    List<ExamInfo> findExamInfoListByExamIds(@Param("ids") List<String> ids);

}
