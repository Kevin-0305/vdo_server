package com.daily.english.app.mapper;

import com.daily.english.app.domain.WordPk;
import com.daily.english.app.dto.PageSearchDto;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * class_name: WordPkMapper
 * package: com.daily.english.app.mapper
 * describe: 单词PK
 * creat_user: liuzd@chinadailyhk.com
 * creat_date: 2019-09-02
 * creat_time: 11:20
 **/
@Mapper
@Component
public interface WordPkMapper {

    //根据ID查询
    @Select("SELECT * " +
            "        FROM ed_word_pk " +
            "        WHERE id = #{id,jdbcType=INTEGER}")
    public WordPk selectById(@Param("id") String id);

    //根据ID删除
    @Delete("DELETE " +
            "        FROM ed_word_pk " +
            "        WHERE id = #{id,jdbcType=INTEGER}")
    public int deleteById(@Param("id") String id);

    //根据业务保存
    @Insert("<script>" +
            "INSERT INTO ed_word_pk " +
            "        <trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\"> " +
            "            <if test=\"title != null\"> " +
            "                title, " +
            "            </if> " +
            "            <if test=\"explainCh != null\"> " +
            "                explain_ch, " +
            "            </if> " +
            "            <if test=\"explainHk != null\"> " +
            "                explain_hk, " +
            "            </if> " +
            "            <if test=\"createTime != null\"> " +
            "                create_time, " +
            "            </if> " +
            "            <if test=\"updateTime != null\"> " +
            "                update_time, " +
            "            </if> " +
            "            <if test=\"createUser != null\"> " +
            "                create_user, " +
            "            </if> " +
            "            <if test=\"updateUser != null\"> " +
            "                update_user, " +
            "            </if> " +
            "            <if test=\"level != null\"> " +
            "                level, " +
            "            </if> " +
            "        </trim> " +
            "        <trim prefix=\"VALUES (\" suffix=\")\" suffixOverrides=\",\"> " +
            "            <if test=\"title != null\"> " +
            "                #{title,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"explainCh != null\"> " +
            "                #{explainCh,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"explainHk != null\"> " +
            "                #{explainHk,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"createTime != null\"> " +
            "                #{createTime,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"updateTime != null\"> " +
            "                #{updateTime,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"createUser != null\"> " +
            "                #{createUser,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"updateUser != null\"> " +
            "                #{updateUser,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"level != null\"> " +
            "                #{level,jdbcType=VARCHAR}, " +
            "            </if> " +
            "        </trim>" +
            "</script>")
    public int insertSelective(WordPk prop);

    //根据ID、业务更新
    @Update("<script>" +
            "UPDATE ed_word_pk " +
            "        <set> " +
            "            <if test=\"title != null\"> " +
            "                title = #{title,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"explainCh != null\"> " +
            "                explain_ch = #{explainCh,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"explainHk != null\"> " +
            "                explain_hk = #{explainHk,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"createTime != null\"> " +
            "                create_time = #{createTime,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"updateTime != null\"> " +
            "                update_time = #{updateTime,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"createUser != null\"> " +
            "                create_user = #{createUser,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"updateUser != null\"> " +
            "                update_user = #{updateUser,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"level != null\"> " +
            "                level = #{level,jdbcType=VARCHAR}, " +
            "            </if> " +
            "        </set> " +
            "        WHERE " +
            "        id = #{id,jdbcType=INTEGER}" +
            "</script>")
    public int updateSelective(WordPk prop);

    //根据根据条件查询
    @Select("<script>" +
            "SELECT " +
            "  * " +
            "FROM " +
            "ed_word_pk " +
            "WHERE " +
            "1=1 " +
            "ORDER BY create_time DESC " +
            "</script>")
    public List<WordPk> selectList(WordPk prop);


    @Select("<script>" +
            "SELECT * " +
            "FROM ed_word_pk " +
            "WHERE 1 = 1 " +
            "AND level = #{level,jdbcType=VARCHAR} " +
            "ORDER BY RAND() " +
            "LIMIT #{size,jdbcType=INTEGER}" +
            "</script>")
    public List<WordPk> selectRandomList(@Param("size") Integer size, @Param("level") String level);

    @Select("<script>" +
            "SELECT " +
            "  * " +
            "FROM " +
            "ed_word_pk " +
            "WHERE " +
            "1=1 " +
            " <if test='keywords != null and keywords != \"\"'> " +
            "            AND ( " +
            "            title LIKE CONCAT('%', #{keywords, jdbcType=VARCHAR} , '%') " +
            "            OR " +
            "            explain_ch LIKE CONCAT('%', #{keywords, jdbcType=VARCHAR} , '%') " +
            "            OR " +
            "            explain_hk LIKE CONCAT('%', #{keywords, jdbcType=VARCHAR} , '%') " +
            "            ) " +
            " </if>" +
            "ORDER BY create_time DESC " +
            "</script>")
    public List<WordPk> selectPageList(PageSearchDto pageSearchDto);


}