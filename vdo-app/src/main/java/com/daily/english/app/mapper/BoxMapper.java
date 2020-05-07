package com.daily.english.app.mapper;

import com.daily.english.app.domain.Box;
import com.daily.english.app.dto.PageSearchDto;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * class_name: IBoxDAO
 * package: com.vdoenglish.core.dao
 * describe: TODO
 * creat_user: liuzd@chinadailyhk.com
 * creat_date: 2019-08-29
 * creat_time: 15:39
 **/
@Mapper
@Component
public interface BoxMapper {

    //根据ID查询
    @Select("SELECT * " +
            "        FROM ed_box " +
            "        WHERE id = #{id,jdbcType=INTEGER}")
    public Box selectById(@Param("id") String id);

	@Delete("DELETE " +
			"        FROM ed_box " +
			"        WHERE id = #{id,jdbcType=INTEGER}")
	public int deleteById(@Param("id") String id);

    //根据业务保存
    @Insert("<script>" +
            "INSERT INTO ed_box " +
            "        <trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\"> " +
            "            <if test=\"boxType != null\"> " +
            "                box_type, " +
            "            </if> " +
            "            <if test=\"bamboo != null\"> " +
            "                bamboo, " +
            "            </if> " +
            "            <if test=\"iconImg != null\"> " +
            "                icon_img, " +
            "            </if> " +
            "            <if test=\"boxImg != null\"> " +
            "                box_img, " +
            "            </if> " +
            "            <if test=\"dropJson != null\"> " +
            "                drop_json, " +
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
            "            <if test=\"boxType != null\"> " +
            "                #{boxType,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"bamboo != null\"> " +
            "                #{bamboo,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"iconImg != null\"> " +
            "                #{iconImg,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"boxImg != null\"> " +
            "                #{boxImg,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"dropJson != null\"> " +
            "                #{dropJson,jdbcType=VARCHAR}, " +
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
    public int insertSelective(Box box);

    //根据ID、业务更新
    @Update("<script>" +
            "UPDATE ed_box " +
            "        <set> " +
            "            <if test=\"boxType != null\"> " +
            "                box_type = #{boxType,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"bamboo != null\"> " +
            "                bamboo = #{bamboo,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"iconImg != null\"> " +
            "                icon_img = #{iconImg,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"boxImg != null\"> " +
            "                box_img = #{boxImg,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"dropJson != null\"> " +
            "                drop_json = #{dropJson,jdbcType=VARCHAR}, " +
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
    public int updateSelective(Box box);

    //根据根据条件查询
    @Select("<script>" +
            "SELECT " +
            "  * " +
            "FROM " +
            "ed_box " +
            "WHERE " +
            "1=1 " +
            "</script>")
    public List<Box> selectList(Box box);

    @Select("<script>" +
            "SELECT " +
            "  * " +
            "FROM " +
            "ed_box " +
            "WHERE " +
            "1=1 " +
            "<if test='keywords != null and keywords != \"\"'> " +
            "    AND ( " +
            "    	box_type LIKE CONCAT('%', #{keywords, jdbcType=VARCHAR} , '%') " +
            "    ) " +
            "</if>" +
            "ORDER BY create_time DESC " +
            "</script>")
    public List<Box> selectPageList(PageSearchDto pageSearchDto);
}