package com.daily.english.app.mapper;

import com.daily.english.app.domain.Prop;
import com.daily.english.app.dto.PageSearchDto;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * class_name: IPropDAO
 * package: com.vdoenglish.core.dao
 * describe: TODO
 * creat_user: liuzd@chinadailyhk.com
 * creat_date: 2019-08-29
 * creat_time: 15:39
 **/
@Mapper
@Component
public interface PropMapper {

    //根据ID查询
    @Select("SELECT * " +
            "        FROM ed_prop " +
            "        WHERE id = #{id,jdbcType=INTEGER}")
    public Prop selectById(@Param("id") String id);

    //根据ID删除
    @Delete("DELETE " +
            "        FROM ed_prop " +
            "        WHERE id = #{id,jdbcType=INTEGER}")
    public int deleteById(@Param("id") String id);

    //根据业务保存
    @Insert("<script>" +
            "INSERT INTO ed_prop " +
            "        <trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\"> " +
            "            <if test=\"id != null\"> " +
            "                id, " +
            "            </if> " +
            "            <if test=\"imageUrl != null\"> " +
            "                image_url, " +
            "            </if> " +
            "            <if test=\"propName != null\"> " +
            "                prop_name, " +
            "            </if> " +
            "            <if test=\"propNameHk != null\"> " +
            "                prop_name_hk, " +
            "            </if> " +
            "            <if test=\"propNameEn != null\"> " +
            "                prop_name_en, " +
            "            </if> " +
            "            <if test=\"price != null\"> " +
            "                price, " +
            "            </if> " +
            "            <if test=\"bamboo != null\"> " +
            "                bamboo, " +
            "            </if> " +
            "            <if test=\"exp != null\"> " +
            "                exp, " +
            "            </if> " +
            "            <if test=\"description != null\"> " +
            "                description, " +
            "            </if> " +
            "            <if test=\"createTime != null\"> " +
            "                create_time, " +
            "            </if> " +
            "            <if test=\"createUser != null\"> " +
            "                create_user, " +
            "            </if> " +
            "            <if test=\"updateTime != null\"> " +
            "                update_time, " +
            "            </if> " +
            "            <if test=\"updateUser != null\"> " +
            "                update_user, " +
            "            </if> " +
            "        </trim> " +
            "        <trim prefix=\"VALUES (\" suffix=\")\" suffixOverrides=\",\"> " +
            "            <if test=\"id != null\"> " +
            "                #{id,jdbcType=INTEGER}, " +
            "            </if> " +
            "            <if test=\"imageUrl != null\"> " +
            "                #{imageUrl,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"propName != null\"> " +
            "                #{propName,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"propNameHk != null\"> " +
            "                #{propNameHk,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"propNameEn != null\"> " +
            "                #{propNameEn,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"price != null\"> " +
            "                #{price,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"bamboo != null\"> " +
            "                #{bamboo,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"exp != null\"> " +
            "                #{exp,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"description != null\"> " +
            "                #{description,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"createTime != null\"> " +
            "                #{createTime,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"createUser != null\"> " +
            "                #{createUser,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"updateTime != null\"> " +
            "                #{updateTime,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"updateUser != null\"> " +
            "                #{updateUser,jdbcType=VARCHAR}, " +
            "            </if> " +
            "        </trim>" +
            "</script>")
    public int insertSelective(Prop prop);

    //根据ID、业务更新
    @Update("<script>" +
            "UPDATE ed_prop " +
            "        <set> " +
            "            <if test=\"id != null\"> " +
            "                id = #{id,jdbcType=INTEGER}, " +
            "            </if> " +
            "            <if test=\"imageUrl != null\"> " +
            "                image_url = #{imageUrl,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"propName != null\"> " +
            "                prop_name = #{propName,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"propNameHk != null\"> " +
            "                prop_name_hk = #{propNameHk,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"propNameEn != null\"> " +
            "                prop_name_en = #{propNameEn,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"price != null\"> " +
            "                price = #{price,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"bamboo != null\"> " +
            "                bamboo = #{bamboo,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"exp != null\"> " +
            "                exp = #{exp,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"description != null\"> " +
            "                description = #{description,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"createTime != null\"> " +
            "                create_time = #{createTime,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"createUser != null\"> " +
            "                create_user = #{createUser,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"updateTime != null\"> " +
            "                update_time = #{updateTime,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"updateUser != null\"> " +
            "                update_user = #{updateUser,jdbcType=VARCHAR}, " +
            "            </if> " +
            "        </set> " +
            "        WHERE " +
            "        id = #{id,jdbcType=INTEGER}" +
            "</script>")
    public int updateSelective(Prop prop);

    //根据根据条件查询
    @Select("<script>" +
            "SELECT " +
            "  * " +
            "FROM " +
            "ed_prop " +
            "WHERE " +
            "1=1 " +
            "ORDER BY create_time DESC " +
            "</script>")
    public List<Prop> selectList(Prop prop);

    @Select("<script>" +
            "SELECT " +
            "  * " +
            "FROM " +
            "ed_prop " +
            "WHERE " +
            "1=1 " +
            " <if test='keywords != null and keywords != \"\"'> " +
            "    AND ( " +
            "    prop_name LIKE CONCAT('%', #{keywords, jdbcType=VARCHAR} , '%') " +
            "    ) " +
            " </if>" +
            "ORDER BY create_time DESC " +
            "</script>")
    public List<Prop> selectPageList(PageSearchDto pageSearchDto);


}