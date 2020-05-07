package com.daily.english.app.mapper;

import com.daily.english.app.domain.ExamRecord;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * class_name: ExamRecordMapper
 * package: com.daily.english.app.mapper
 * describe: 视频试题习题用户提交记录
 * creat_user: liuzd@chinadailyhk.com
 * creat_date: 2019-08-28
 * creat_time: 09:54
 **/
@Mapper
@Component
public interface ExamRecordMapper {

    @Insert("<script>" +
            "INSERT INTO ed_exercise_answer " +
            "        <trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\"> " +
            "            <if test=\"eid != null\"> " +
            "                eid, " +
            "            </if> " +
            "            <if test=\"uid != null\"> " +
            "                uid, " +
            "            </if> " +
            "            <if test=\"vid != null\"> " +
            "                vid, " +
            "            </if> " +
            "            <if test=\"userAnswer != null\"> " +
            "                user_answer, " +
            "            </if> " +
            "            <if test=\"createTime != null\"> " +
            "                create_time, " +
            "            </if> " +
            "        </trim> " +
            "        <trim prefix=\"VALUES (\" suffix=\")\" suffixOverrides=\",\"> " +
            "            <if test=\"eid != null\"> " +
            "                #{eid,jdbcType=INTEGER}, " +
            "            </if> " +
            "            <if test=\"uid != null\"> " +
            "                #{uid,jdbcType=INTEGER}, " +
            "            </if> " +
            "            <if test=\"vid != null\"> " +
            "                #{vid,jdbcType=INTEGER}, " +
            "            </if> " +
            "            <if test=\"userAnswer != null\"> " +
            "                #{userAnswer,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"createTime != null\"> " +
            "                #{createTime,jdbcType=VARCHAR}, " +
            "            </if> " +
            "        </trim>" +
            "</script>")
    void insertExamRecord(ExamRecord examRecord);

    @Update("<script>" +
            " UPDATE ed_exercise_answer " +
            "        <set> " +
            "            <if test=\"eid != null\"> " +
            "                eid = #{eid,jdbcType=INTEGER}, " +
            "            </if> " +
            "            <if test=\"uid != null\"> " +
            "                uid = #{uid,jdbcType=INTEGER}, " +
            "            </if> " +
            "            <if test=\"vid != null\"> " +
            "                vid = #{vid,jdbcType=INTEGER}, " +
            "            </if> " +
            "            <if test=\"userAnswer != null\"> " +
            "                user_answer = #{userAnswer,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"createTime != null\"> " +
            "                create_time = #{createTime,jdbcType=VARCHAR}, " +
            "            </if> " +
            "        </set> " +
            "        WHERE " +
            "        id = #{id,jdbcType=INTEGER}" +
            "</script>")
    int updateExamRecord(ExamRecord examRecord);

    @Delete("delete from  ed_exercise_answer where id = #{id}")
    void deleteExamRecordById(@Param("id") String id);

    @Select("SELECT " +
            " * " +
            " FROM " +
            "  ed_exercise_answer " +
            " WHERE " +
            " 1=1" +
            " AND id  =#{id}")
    ExamRecord selectExamRecordById(@Param("id") String id);

    @Select("<script>" +
            "SELECT " +
            " * " +
            " FROM " +
            "  ed_exercise_answer " +
            " WHERE " +
            " 1=1 " +
            "<if test=\"eid != null\"> " +
            "   AND  eid = #{eid,jdbcType=INTEGER} " +
            "</if> " +
            "<if test=\"uid != null\"> " +
            "   AND  uid = #{uid,jdbcType=INTEGER} " +
            "</if> " +
            "<if test=\"vid != null\"> " +
            "   AND  vid = #{vid,jdbcType=INTEGER} " +
            "</if> " +
            "</script>")
    List<ExamRecord> selectExamRecordList(ExamRecord examRecord);


}
