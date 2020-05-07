package com.daily.english.app.mapper;

import com.daily.english.app.domain.PartnerSchool;
import com.daily.english.app.dto.PageSearchDto;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * class_name: ParnerSchoolMapper
 * package: com.daily.english.app.mapper
 * describe: TODO
 * creat_user: liuzd@chinadailyhk.com
 * creat_date: 2019-09-05
 * creat_time: 09:39
 **/
@Mapper
@Component
public interface PartnerSchoolMapper {

    //根据ID查询
    @Select("SELECT * " +
            "        FROM ed_partner_school " +
            "        WHERE id = #{id,jdbcType=INTEGER}")
    public PartnerSchool selectById(@Param("id") String id);

    //根据ID删除
    @Delete("DELETE " +
            "        FROM ed_partner_school " +
            "        WHERE id = #{id,jdbcType=INTEGER}")
    public int deleteById(@Param("id") String id);

    //根据业务保存
    @Insert("<script>" +
            "INSERT INTO ed_partner_school\n" +
            "        <trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">\n" +
            "            <if test=\"name != null\">\n" +
            "                name,\n" +
            "            </if>\n" +
            "            <if test=\"contact != null\">\n" +
            "                contact,\n" +
            "            </if>\n" +
            "            <if test=\"startTime != null\">\n" +
            "                start_time,\n" +
            "            </if>\n" +
            "            <if test=\"endTime != null\">\n" +
            "                end_time,\n" +
            "            </if>\n" +
            "            <if test=\"createTime != null\">\n" +
            "                create_time,\n" +
            "            </if>\n" +
            "            <if test=\"createUser != null\">\n" +
            "                create_user,\n" +
            "            </if>\n" +
            "            <if test=\"updateTime != null\">\n" +
            "                update_time,\n" +
            "            </if>\n" +
            "            <if test=\"updateUser != null\">\n" +
            "                update_user,\n" +
            "            </if>\n" +
            "            <if test=\"numCount != null\"> " +
            "                num_count, " +
            "            </if> " +
            "        </trim>\n" +
            "        <trim prefix=\"VALUES (\" suffix=\")\" suffixOverrides=\",\">\n" +
            "            <if test=\"name != null\">\n" +
            "                #{name,jdbcType=VARCHAR},\n" +
            "            </if>\n" +
            "            <if test=\"contact != null\">\n" +
            "                #{contact,jdbcType=VARCHAR},\n" +
            "            </if>\n" +
            "            <if test=\"startTime != null\">\n" +
            "                #{startTime,jdbcType=VARCHAR},\n" +
            "            </if>\n" +
            "            <if test=\"endTime != null\">\n" +
            "                #{endTime,jdbcType=VARCHAR},\n" +
            "            </if>\n" +
            "            <if test=\"createTime != null\">\n" +
            "                #{createTime,jdbcType=VARCHAR},\n" +
            "            </if>\n" +
            "            <if test=\"createUser != null\">\n" +
            "                #{createUser,jdbcType=VARCHAR},\n" +
            "            </if>\n" +
            "            <if test=\"updateTime != null\">\n" +
            "                #{updateTime,jdbcType=VARCHAR},\n" +
            "            </if>\n" +
            "            <if test=\"updateUser != null\">\n" +
            "                #{updateUser,jdbcType=VARCHAR},\n" +
            "            </if>\n" +
            "            <if test=\"numCount != null\"> " +
            "                #{numCount,jdbcType=INTEGER}, " +
            "            </if> " +
            "        </trim>" +
            "</script>")
    public int insertSelective(PartnerSchool partnerSchool);

    //根据ID、业务更新
    @Update("<script>" +
            "UPDATE ed_partner_school\n" +
            "        <set>\n" +
            "            <if test=\"name != null\">\n" +
            "                name = #{name,jdbcType=VARCHAR},\n" +
            "            </if>\n" +
            "            <if test=\"contact != null\">\n" +
            "                contact = #{contact,jdbcType=VARCHAR},\n" +
            "            </if>\n" +
            "            <if test=\"startTime != null\">\n" +
            "                start_time = #{startTime,jdbcType=VARCHAR},\n" +
            "            </if>\n" +
            "            <if test=\"endTime != null\">\n" +
            "                end_time = #{endTime,jdbcType=VARCHAR},\n" +
            "            </if>\n" +
            "            <if test=\"createTime != null\">\n" +
            "                create_time = #{createTime,jdbcType=VARCHAR},\n" +
            "            </if>\n" +
            "            <if test=\"createUser != null\">\n" +
            "                create_user = #{createUser,jdbcType=VARCHAR},\n" +
            "            </if>\n" +
            "            <if test=\"updateTime != null\">\n" +
            "                update_time = #{updateTime,jdbcType=VARCHAR},\n" +
            "            </if>\n" +
            "            <if test=\"updateUser != null\">\n" +
            "                update_user = #{updateUser,jdbcType=VARCHAR},\n" +
            "            </if>\n" +
            "            <if test=\"numCount != null\"> " +
            "                num_count = #{numCount,jdbcType=INTEGER}, " +
            "            </if> " +
            "        </set>\n" +
            "        WHERE\n" +
            "        id = #{id,jdbcType=INTEGER}" +
            "</script>")
    public int updateSelective(PartnerSchool partnerSchool);

    //根据根据条件查询
    @Select("<script>" +
            "SELECT " +
            "  * " +
            "FROM " +
            "ed_partner_school " +
            "WHERE " +
            "1=1 " +
            "ORDER BY create_time DESC " +
            "</script>")
    public List<PartnerSchool> selectList(PartnerSchool partnerSchool);

    @Select("<script>" +
            "SELECT " +
            "  * " +
            "FROM " +
            "ed_partner_school " +
            "WHERE " +
            "1=1 " +
//            " <if test='keywords != null and keywords != \"\"'> " +
//            "    AND ( " +
//            "    partnerSchool_name LIKE CONCAT('%', #{keywords, jdbcType=VARCHAR} , '%') " +
//            "    ) " +
//            " </if>" +
            "ORDER BY create_time DESC " +
            "</script>")
    public List<PartnerSchool> selectPageList(PageSearchDto pageSearchDto);


}