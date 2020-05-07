package com.daily.english.app.mapper;

import com.daily.english.app.domain.Exercise;
import com.daily.english.app.dto.ExerciseDto;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ExerciseMapper {

    @Insert("<script>" +
            "INSERT INTO ed_exercise " +
            "        <trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\"> " +
            "            <if test=\"vid != null\"> " +
            "                vid, " +
            "            </if> " +
            "            <if test=\"question != null\"> " +
            "                question, " +
            "            </if> " +
            "            <if test=\"chooseA != null\"> " +
            "                chooseA, " +
            "            </if> " +
            "            <if test=\"chooseB != null\"> " +
            "                chooseB, " +
            "            </if> " +
            "            <if test=\"chooseC != null\"> " +
            "                chooseC, " +
            "            </if> " +
            "            <if test=\"chooseD != null\"> " +
            "                chooseD, " +
            "            </if> " +
            "            <if test=\"answer != null\"> " +
            "                answer, " +
            "            </if> " +
            "            <if test=\"explan != null\"> " +
            "                explan, " +
            "            </if> " +
            "        </trim> " +
            "        <trim prefix=\"VALUES (\" suffix=\")\" suffixOverrides=\",\"> " +
            "            <if test=\"vid != null\"> " +
            "                #{vid,jdbcType=INTEGER}, " +
            "            </if> " +
            "            <if test=\"question != null\"> " +
            "                #{question,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"chooseA != null\"> " +
            "                #{chooseA,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"chooseB != null\"> " +
            "                #{chooseB,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"chooseC != null\"> " +
            "                #{chooseC,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"chooseD != null\"> " +
            "                #{chooseD,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"answer != null\"> " +
            "                #{answer,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"explan != null\"> " +
            "                #{explan,jdbcType=VARCHAR}, " +
            "            </if> " +
            "        </trim>" +
            "</script>")
    void insertExercise(Exercise exercise);

    @Update("<script>" +
            "UPDATE ed_exercise " +
            "        <set> " +
            "            <if test=\"vid != null\"> " +
            "                vid = #{vid,jdbcType=INTEGER}, " +
            "            </if> " +
            "            <if test=\"question != null\"> " +
            "                question = #{question,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"chooseA != null\"> " +
            "                chooseA = #{chooseA,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"chooseB != null\"> " +
            "                chooseB = #{chooseB,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"chooseC != null\"> " +
            "                chooseC = #{chooseC,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"chooseD != null\"> " +
            "                chooseD = #{chooseD,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"answer != null\"> " +
            "                answer = #{answer,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"explan != null\"> " +
            "                explan = #{explan,jdbcType=VARCHAR}, " +
            "            </if> " +
            "        </set> " +
            "        WHERE " +
            "        id = #{id,jdbcType=INTEGER}" +
            "</script>")
    int updateExercise(Exercise exercise);

    @Delete("delete from ed_exercise where id = #{id}")
    void deleteExerciseById(@Param("id") String id);

    @Select("SELECT " +
            " * " +
            " FROM " +
            " ed_exercise " +
            " WHERE " +
            " 1=1" +
            " AND id  =#{id}")
    Exercise selectExerciseById(@Param("id") String id);

    @Select("<script>" +
            "SELECT " +
            " * " +
            " FROM " +
            " ed_exercise " +
            " WHERE " +
            " 1=1 " +
            "<if test=\"vid != null\"> " +
            "   AND vid = #{vid,jdbcType=INTEGER} " +
            "</if> " +
            "</script>")
    List<Exercise> selectExerciseList(Exercise exercise);

    @Select("<script>" +
            "SELECT id, vid, question, chooseA, chooseB, chooseC, chooseD, answer, explan " +
            "FROM ed_exercise " +
            "WHERE 1=1 " +
            "<if test=\"vid != null\"> " +
            "   AND vid = #{vid,jdbcType=INTEGER} " +
            "</if> " +
            "</script>")
    List<Exercise> selectExercisePage(ExerciseDto exerciseDto);


}
