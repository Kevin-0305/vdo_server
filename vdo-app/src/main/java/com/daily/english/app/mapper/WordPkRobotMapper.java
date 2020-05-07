package com.daily.english.app.mapper;

import com.daily.english.app.domain.WordPkRobot;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * class_name: IWorkPkRobotDAO
 * package: com.vdoenglish.core.dao
 * describe: 单词PK机器人数据层
 * creat_user: liuzd@chinadailyhk.com
 * creat_date: 2019-08-29
 * creat_time: 15:39
 **/
@Mapper
@Component
public interface WordPkRobotMapper {

    //根据ID查询
    @Select("SELECT * " +
            "FROM ed_work_pk_robot " +
            "WHERE id = #{id,jdbcType=INTEGER}")
    public WordPkRobot selectById(@Param("id") String id);

    //根据ID删除
    @Delete("DELETE " +
            "FROM ed_work_pk_robot " +
            "WHERE id = #{id,jdbcType=INTEGER}")
    public int deleteById(@Param("id") String id);

    //根据业务保存
    @Insert("<script>" +
            "INSERT INTO ed_work_pk_robot " +
            "<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\"> " +
            "    <if test=\"robotName != null\"> " +
            "       robot_name, " +
            "    </if> " +
            "    <if test=\"headUrl != null\"> " +
            "       head_url, " +
            "    </if> " +
            "</trim> " +
            "<trim prefix=\"VALUES (\" suffix=\")\" suffixOverrides=\",\"> " +
            "    <if test=\"robotName != null\"> " +
            "       #{robotName,jdbcType=VARCHAR}, " +
            "    </if> " +
            "    <if test=\"headUrl != null\"> " +
            "       #{headUrl,jdbcType=VARCHAR}, " +
            "    </if> " +
            "</trim>" +
            "</script>")
    public int insertSelective(WordPkRobot wordPkRobot);

    //根据根据条件查询
    @Select("<script>" +
            "SELECT " +
            "  * " +
            "FROM " +
            "ed_work_pk_robot " +
            "WHERE " +
            "1=1 " +
            "</script>")
    public List<WordPkRobot> selectList(WordPkRobot wordPkRobot);

    @Select("<script>" +
            "SELECT * " +
            "FROM ed_work_pk_robot " +
            "WHERE 1 = 1 " +
            "ORDER BY RAND() " +
            "LIMIT 1" +
            "</script>")
    public WordPkRobot selectRandomRobot();
}