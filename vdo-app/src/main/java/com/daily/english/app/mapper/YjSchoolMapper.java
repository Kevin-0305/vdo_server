package com.daily.english.app.mapper;

import com.daily.english.app.domain.YjSchool;
import com.daily.english.app.dto.PageSearchDto;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * class_name: YjUserBmInfoMapper
 * package: com.daily.english.app.mapper
 * describe: 评委mapper
 * creat_user: liuzd@chinadailyhk.com
 * creat_date: 2019-08-14
 * creat_time: 15:59
 **/
@Mapper
@Component
public interface YjSchoolMapper {

    @Select("<script>" +
            "SELECT " +
            "* " +
            "FROM " +
            "yj_school " +
            "WHERE " +
            " 1=1 " +
            "</script>")
    List<YjSchool> selectList(YjSchool yjSchool);


    @Select("<script>" +
            "SELECT " +
            "* " +
            "FROM " +
            "yj_school " +
            "WHERE " +
            " 1=1 " +
            " <if test='keywords != null and keywords != \"\"'> " +
            "    AND ( " +
            "    distinct_en LIKE CONCAT('%', #{keywords, jdbcType=VARCHAR} , '%') " +
            "    OR " +
            "    name_ch LIKE CONCAT('%', #{keywords, jdbcType=VARCHAR} , '%') " +
            "    OR " +
            "    name_en LIKE CONCAT('%', #{keywords, jdbcType=VARCHAR} , '%') " +
            "    OR " +
            "    `distinct` LIKE CONCAT('%', #{keywords, jdbcType=VARCHAR} , '%') " +
            "    OR " +
            "    distinct_hk LIKE CONCAT('%', #{keywords, jdbcType=VARCHAR} , '%') " +
            "    ) " +
            " </if>" +
            " ORDER BY id DESC" +
            "</script>")
    List<YjSchool> selectListPage(PageSearchDto pageSearchDto);

    @Insert("<script>" +
            " INSERT INTO yj_school " +
            "<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">" +
            "    <if test=\"distinctEn != null\">" +
            "        distinct_en," +
            "    </if>" +
            "    <if test=\"nameCh != null\">" +
            "        name_ch," +
            "    </if>" +
            "    <if test=\"nameEn != null\">" +
            "        name_en," +
            "    </if>" +
            "    <if test=\"distinct != null\">" +
            "        `distinct`," +
            "    </if>" +
            "    <if test=\"schoolType != null\">" +
            "        school_type," +
            "    </if>" +
            "    <if test=\"distinctHk != null\">" +
            "        distinct_hk," +
            "    </if>" +
            "</trim>" +
            "<trim prefix=\"VALUES (\" suffix=\")\" suffixOverrides=\",\">" +
            "    <if test=\"distinctEn != null\">" +
            "        #{distinctEn,jdbcType=VARCHAR}," +
            "    </if>" +
            "    <if test=\"nameCh != null\">" +
            "        #{nameCh,jdbcType=VARCHAR}," +
            "    </if>" +
            "    <if test=\"nameEn != null\">" +
            "        #{nameEn,jdbcType=VARCHAR}," +
            "    </if>" +
            "    <if test=\"distinct != null\">" +
            "        #{distinct,jdbcType=VARCHAR}," +
            "    </if>" +
            "    <if test=\"schoolType != null\">" +
            "        #{schoolType,jdbcType=INTEGER}," +
            "    </if>" +
            "    <if test=\"distinctHk != null\">" +
            "        #{distinctHk,jdbcType=VARCHAR}," +
            "    </if>" +
            "</trim>" +
            "</script>")
    void insert(YjSchool scoreRecord);

    @Update("<script>" +
            "UPDATE yj_school " +
            "<set>" +
            "    <if test=\"distinctEn != null\">" +
            "        distinct_en = #{distinctEn,jdbcType=VARCHAR}," +
            "    </if>" +
            "    <if test=\"nameCh != null\">" +
            "        name_ch = #{nameCh,jdbcType=VARCHAR}," +
            "    </if>" +
            "    <if test=\"nameEn != null\">" +
            "        name_en = #{nameEn,jdbcType=VARCHAR}," +
            "    </if>" +
            "    <if test=\"distinct != null\">" +
            "        `distinct` = #{distinct,jdbcType=VARCHAR}," +
            "    </if>" +
            "    <if test=\"schoolType != null\">" +
            "        school_type = #{schoolType,jdbcType=INTEGER}," +
            "    </if>" +
            "    <if test=\"distinctHk != null\">" +
            "        distinct_hk = #{distinctHk,jdbcType=VARCHAR}," +
            "    </if>" +
            "</set>" +
            "WHERE " +
            "id = #{id,jdbcType=INTEGER}" +
            "</script>")
    void update(YjSchool scoreRecord);


    @Select(" SELECT" +
            "    * " +
            " FROM " +
            "     yj_school " +
            " WHERE " +
            " id = #{id,jdbcType=INTEGER}")
    YjSchool selectById(@Param("id") String id);

    @Delete("delete from yj_school where id = #{id}")
    void deleteById(@Param("id") String id);

}
