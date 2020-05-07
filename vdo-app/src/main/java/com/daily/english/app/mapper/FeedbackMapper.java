package com.daily.english.app.mapper;

import com.daily.english.app.domain.Feedback;
import com.daily.english.app.dto.FeedbackDto;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;


@Mapper
@Component
public interface FeedbackMapper {

    @Insert("<script>" +
            " INSERT INTO ed_feedback " +
            "        <trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\"> " +
            "            <if test=\"id != null\"> " +
            "                id, " +
            "            </if> " +
            "            <if test=\"fileJson != null\"> " +
            "                file_json, " +
            "            </if> " +
            "            <if test=\"contact != null\"> " +
            "                contact, " +
            "            </if> " +
            "            <if test=\"content != null\"> " +
            "                content, " +
            "            </if> " +
            "            <if test=\"createTime != null\"> " +
            "                create_time, " +
            "            </if> " +
            "            <if test=\"status != null\"> " +
            "                status, " +
            "            </if> " +
            "            <if test=\"questionType != null\"> " +
            "                questionType, " +
            "            </if> " +
            "            <if test=\"sourceType != null\"> " +
            "                sourceType, " +
            "            </if> " +
            "            <if test=\"processingTime != null\"> " +
            "                processingTime, " +
            "            </if> " +
            "            <if test=\"userId != null\"> " +
            "                user_id, " +
            "            </if> " +
            "        </trim> " +
            "        <trim prefix=\"VALUES (\" suffix=\")\" suffixOverrides=\",\"> " +
            "            <if test=\"id != null\"> " +
            "                #{id,jdbcType=INTEGER}, " +
            "            </if> " +
            "            <if test=\"fileJson != null\"> " +
            "                #{fileJson,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"contact != null\"> " +
            "                #{contact,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"content != null\"> " +
            "                #{content,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"createTime != null\"> " +
            "                #{createTime,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"status != null\"> " +
            "                #{status,jdbcType=INTEGER}, " +
            "            </if> " +
            "            <if test=\"questionType != null\"> " +
            "                #{questionType}, " +
            "            </if> " +
            "            <if test=\"sourceType != null\"> " +
            "                #{sourceType}, " +
            "            </if> " +
            "            <if test=\"processingTime != null\"> " +
            "                #{processingTime}, " +
            "            </if> " +
            "            <if test=\"userId != null\"> " +
            "                #{userId}, " +
            "            </if> " +
            "        </trim>" +
            "</script>")
    void insertFeedback(Feedback eventNews);

    //根据ID删除
    @Delete("DELETE " +
            "        FROM ed_feedback " +
            "        WHERE id = #{id,jdbcType=INTEGER}")
    public int deleteById(@Param("id") String id);

    @Update("<script>" +
            "UPDATE ed_feedback " +
            "        <set> " +
            "            <if test=\"id != null\"> " +
            "                id = #{id,jdbcType=INTEGER}, " +
            "            </if> " +
            "            <if test=\"fileJson != null\"> " +
            "                file_json = #{fileJson,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"contact != null\"> " +
            "                contact = #{contact,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"content != null\"> " +
            "                content = #{content,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"createTime != null\"> " +
            "                create_time = #{createTime,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"status != null\"> " +
            "                status = #{status,jdbcType=INTEGER}, " +
            "            </if> " +
            "            <if test=\"processingTime != null\"> " +
            "                processingTime = #{processingTime}, " +
            "            </if> " +
            "            <if test=\"userId != null\"> " +
            "                user_id = #{userId}, " +
            "            </if> " +
            "        </set> " +
            "        WHERE " +
            "        id = #{id,jdbcType=INTEGER}" +
            "</script>")
    int updateFeedback(Feedback eventNews);

    @Select("<script>" +
            "SELECT ef.id, ef.file_json, ef.contact, ef.content, ef.create_time, ef.status, ef.sourceType, ef.questionType,\n" +
            "    ef.processingTime, ef.user_id\n" +
            "FROM ed_feedback ef\n" +
            " WHERE 1=1" +
            "</script>")
    List<Feedback> selectList(Feedback feedback);

    @Select("<script>" +
            "SELECT ef.id, ef.file_json, ef.contact, ef.content, ef.create_time, ef.status, ef.sourceType, ef.questionType,\n" +
            "    ef.processingTime, ef.user_id, eu.useraccount, eu.nickname\n" +
            "FROM ed_feedback ef\n" +
            "         LEFT JOIN ed_user eu ON eu.id = ef.user_id\n" +
            "WHERE 1=1 " +
            " <if test=\"status != null\"> " +
            "  AND ef.status = #{status,jdbcType=INTEGER} " +
            " </if> " +
            " <if test=\"questionType != null\"> " +
            "  AND ef.questionType = #{questionType,jdbcType=INTEGER} " +
            " </if> " +
            " <if test=\"userId != null\"> " +
            "  AND ef.user_id = #{userId,jdbcType=INTEGER} " +
            " </if> " +
            " <if test=\"sourceType != null\"> " +
            "  AND ef.sourceType = #{sourceType,jdbcType=INTEGER} " +
            " </if> " +
            " <if test=\"searchDate != null\"> " +
            "  AND date(ef.create_time) = #{searchDate} " +
            " </if> " +
            "</script>")
    List<Feedback> selectListPage(FeedbackDto feedbackDto);

    @Select("SELECT ef.id, ef.file_json, ef.contact, ef.content, ef.create_time, ef.status, ef.sourceType, ef.questionType,\n" +
            "    ef.processingTime, ef.user_id, eu.useraccount, eu.nickname\n" +
            "FROM ed_feedback ef\n" +
            "LEFT JOIN ed_user eu ON eu.id = ef.user_id\n" +
            "WHERE 1=1 " +
            "AND ef.id = #{id,jdbcType=INTEGER}")
    Feedback selectById(@Param("id") String id);

}
