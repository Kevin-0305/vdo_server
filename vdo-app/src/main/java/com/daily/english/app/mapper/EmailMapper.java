package com.daily.english.app.mapper;


import com.daily.english.app.domain.Email;
import com.daily.english.app.dto.EmailDto;
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
public interface EmailMapper {

    @Insert("<script>" +
            "INSERT INTO ed_email " +
            "        <trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\"> " +
            "            <if test=\"id != null\"> " +
            "                id, " +
            "            </if> " +
            "            <if test=\"subject != null\"> " +
            "                subject, " +
            "            </if> " +
            "            <if test=\"content != null\"> " +
            "                content, " +
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
            "            <if test=\"addressees != null\"> " +
            "                addressees, " +
            "            </if> " +
            "            <if test=\"status != null\"> " +
            "                status, " +
            "            </if> " +
            "            <if test=\"sendTime != null\"> " +
            "                send_time, " +
            "            </if> " +
            "            <if test=\"attachmentJson != null\"> " +
            "                attachment_json, " +
            "            </if> " +
            "        </trim> " +
            "        <trim prefix=\"VALUES (\" suffix=\")\" suffixOverrides=\",\"> " +
            "            <if test=\"id != null\"> " +
            "                #{id,jdbcType=INTEGER}, " +
            "            </if> " +
            "            <if test=\"subject != null\"> " +
            "                #{subject,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"content != null\"> " +
            "                #{content,jdbcType=VARCHAR}, " +
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
            "            <if test=\"addressees != null\"> " +
            "                #{addressees,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"status != null\"> " +
            "                #{status,jdbcType=INTEGER}, " +
            "            </if> " +
            "            <if test=\"sendTime != null\"> " +
            "                #{sendTime,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"attachmentJson != null\"> " +
            "                #{attachmentJson,jdbcType=VARCHAR}, " +
            "            </if> " +
            "        </trim>" +
            "</script>")
    void insertEmail(Email email);

    @Update("<script>" +
            " UPDATE ed_email " +
            "        <set> " +
            "            <if test=\"id != null\"> " +
            "                id = #{id,jdbcType=INTEGER}, " +
            "            </if> " +
            "            <if test=\"subject != null\"> " +
            "                subject = #{subject,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"content != null\"> " +
            "                content = #{content,jdbcType=VARCHAR}, " +
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
            "            <if test=\"addressees != null\"> " +
            "                addressees = #{addressees,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"status != null\"> " +
            "                status = #{status,jdbcType=INTEGER}, " +
            "            </if> " +
            "            <if test=\"sendTime != null\"> " +
            "                send_time = #{sendTime,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"attachmentJson != null\"> " +
            "                attachment_json = #{attachmentJson,jdbcType=VARCHAR}, " +
            "            </if> " +
            "        </set> " +
            "        WHERE " +
            "        id = #{id,jdbcType=INTEGER}" +
            "</script>")
    int updateEmail(Email email);

    @Select("<script>" +
            " SELECT " +
            "   * " +
            " FROM " +
            " ed_email " +
            " WHERE 1=1" +
            " <if test=\"status != null\"> " +
            "   AND status = #{status,jdbcType=INTEGER} " +
            " </if> " +
            "</script>")
    List<Email> selectList(Email email);

    @Select("<script>" +
            " SELECT " +
            "   * " +
            " FROM " +
            " ed_email " +
            " WHERE 1=1 " +
            "  AND status != -1 " +
            " <if test=\"status != null and status &gt; 0 \"> " +
            "  AND status = #{status,jdbcType=INTEGER} " +
            " </if> " +
            "</script>")
    List<Email> selectListPage(EmailDto emailDto);

    @Select(" SELECT " +
            "    * " +
            " FROM " +
            " ed_email " +
            " WHERE " +
            " id = #{id,jdbcType=INTEGER}")
    Email selectById(@Param("id") String id);


    @Select("<script>" +
            "SELECT * " +
            "FROM ed_email ee " +
            "WHERE ee.status = 2 AND (TIMESTAMP(ee.send_time) <![CDATA[ <= ]]> CURRENT_TIMESTAMP()) " +
            "</script>")
    List<Email> queryMailToSend();

}
