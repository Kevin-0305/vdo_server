package com.daily.english.app.mapper;

import com.daily.english.app.domain.PetLog;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * class_name: IPetLogDAO
 * package: com.vdoenglish.core.dao
 * describe: TODO
 * creat_user: liuzd@chinadailyhk.com
 * creat_date: 2019-09-17
 * creat_time: 15:39
 **/
@Mapper
@Component
public interface PetLogMapper {

    //根据ID查询
    @Select("SELECT * " +
            "        FROM ed_pet_log " +
            "        WHERE id = #{id,jdbcType=INTEGER}")
    public PetLog selectById(Integer id);

    //根据ID删除
    @Delete("DELETE " +
            "        FROM ed_pet_log " +
            "        WHERE id = #{id,jdbcType=INTEGER}")
    public int deleteById(Integer id);

    //根据业务保存
    @Insert("<script>" +
            " INSERT INTO ed_pet_log " +
            "        <trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\"> " +
            "            <if test=\"actionType != null\"> " +
            "                action_type, " +
            "            </if> " +
            "            <if test=\"petId != null\"> " +
            "                pet_id, " +
            "            </if> " +
            "            <if test=\"createTime != null\"> " +
            "                create_time, " +
            "            </if> " +
            "        </trim> " +
            "        <trim prefix=\"VALUES (\" suffix=\")\" suffixOverrides=\",\"> " +
            "            <if test=\"actionType != null\"> " +
            "                #{actionType,jdbcType=INTEGER}, " +
            "            </if> " +
            "            <if test=\"petId != null\"> " +
            "                #{petId,jdbcType=INTEGER}, " +
            "            </if> " +
            "            <if test=\"createTime != null\"> " +
            "                #{createTime,jdbcType=VARCHAR}, " +
            "            </if> " +
            "        </trim>" +
            "</script>")
    public int insertSelective(PetLog petLog);

    //根据ID、业务更新
    @Update("<script>" +
            " UPDATE ed_pet_log " +
            "        <set> " +
            "            <if test=\"actionType != null\"> " +
            "                action_type = #{actionType,jdbcType=INTEGER}, " +
            "            </if> " +
            "            <if test=\"petId != null\"> " +
            "                pet_id = #{petId,jdbcType=INTEGER}, " +
            "            </if> " +
            "            <if test=\"createTime != null\"> " +
            "                create_time = #{createTime,jdbcType=VARCHAR}, " +
            "            </if> " +
            "        </set> " +
            "        WHERE " +
            "        id = #{id,jdbcType=INTEGER}" +
            "</script>")
    public int updateSelective(PetLog petLog);

    //根据根据条件查询
    @Select("<script>" +
            "SELECT " +
            "  * " +
            "FROM " +
            "ed_pet_log " +
            "WHERE " +
            "1=1 " +
            "ORDER BY create_time DESC " +
            "</script>")
    public List<PetLog> selectList(PetLog petLog);


    //根据根据条件查询
    @Select("<script>" +
            "SELECT " +
            "  * " +
            "FROM " +
            "ed_pet_log " +
            "WHERE " +
            "1=1 " +
            "AND pet_id = #{petId,jdbcType=INTEGER} " +
            "ORDER BY create_time DESC LIMIT 1 " +
            "</script>")
    public PetLog selectRecentFeedByPetId(@Param("petId")Integer petId);
}