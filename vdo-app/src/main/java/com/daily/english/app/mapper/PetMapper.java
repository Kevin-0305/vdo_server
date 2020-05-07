package com.daily.english.app.mapper;

import java.util.List;

import com.daily.english.app.domain.Pet;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

/**
 * class_name: IPetDAO
 * package: com.vdoenglish.core.dao
 * describe: TODO
 * creat_user: liuzd@chinadailyhk.com
 * creat_date: 2019-09-17
 * creat_time: 15:39
 **/
@Mapper
@Component
public interface PetMapper {

    //根据ID查询
    @Select("SELECT * " +
            "        FROM ed_pet " +
            "        WHERE id = #{id,jdbcType=INTEGER}")
    public Pet selectById(@Param("id") Integer id);

    //根据ID查询
    @Select("SELECT " +
            "   ep.id, ep.user_id, ep.growth, ep.create_time, " +
            "   euv.bamboo AS 'bamboo', ep.level " +
            "FROM ed_pet ep " +
            "LEFT JOIN ed_user_value euv ON euv.id = ep.user_id"+
            "        WHERE ep.user_id = #{userId,jdbcType=INTEGER}")
    public Pet selectByUserId(@Param("userId") Integer userId);

    //根据ID删除
    @Delete("DELETE " +
            "        FROM ed_pet " +
            "        WHERE id = #{id,jdbcType=INTEGER}")
    public int deleteById(Integer id);

    //根据业务保存
    @Insert("<script>" +
            "INSERT INTO ed_pet " +
            "        <trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\"> " +
            "            <if test=\"userId != null\"> " +
            "                user_id, " +
            "            </if> " +
            "            <if test=\"growth != null\"> " +
            "                growth, " +
            "            </if> " +
            "            <if test=\"level != null\"> " +
            "                level, " +
            "            </if> " +
            "            <if test=\"createTime != null\"> " +
            "                create_time, " +
            "            </if> " +
            "        </trim> " +
            "        <trim prefix=\"VALUES (\" suffix=\")\" suffixOverrides=\",\"> " +
            "            <if test=\"userId != null\"> " +
            "                #{userId,jdbcType=INTEGER}, " +
            "            </if> " +
            "            <if test=\"growth != null\"> " +
            "                #{growth,jdbcType=INTEGER}, " +
            "            </if> " +
            "            <if test=\"level != null\"> " +
            "                #{level,jdbcType=INTEGER}, " +
            "            </if> " +
            "            <if test=\"createTime != null\"> " +
            "                #{createTime,jdbcType=VARCHAR}, " +
            "            </if> " +
            "        </trim>" +
            "</script>")
    public int insertSelective(Pet pet);

    //根据ID、业务更新
    @Update("<script>" +
            "UPDATE ed_pet " +
            "        <set> " +
            "            <if test=\"userId != null\"> " +
            "                user_id = #{userId,jdbcType=INTEGER}, " +
            "            </if> " +
            "            <if test=\"growth != null\"> " +
            "                growth = #{growth,jdbcType=INTEGER}, " +
            "            </if> " +
            "            <if test=\"level != null\"> " +
            "                level = #{level,jdbcType=INTEGER}, " +
            "            </if> " +
            "            <if test=\"createTime != null\"> " +
            "                create_time = #{createTime,jdbcType=VARCHAR}, " +
            "            </if> " +
            "        </set> " +
            "        WHERE " +
            "        id = #{id,jdbcType=INTEGER}" +
            "</script>")
    public int updateSelective(Pet pet);

    //根据根据条件查询
    @Select("<script>" +
            "SELECT " +
            "  * " +
            "FROM " +
            "ed_pet " +
            "WHERE " +
            "1=1 " +
            "ORDER BY create_time DESC " +
            "</script>")
    public List<Pet> selectList(Pet pet);
}