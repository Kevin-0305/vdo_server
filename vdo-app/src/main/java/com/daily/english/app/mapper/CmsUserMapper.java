package com.daily.english.app.mapper;

import com.daily.english.app.domain.CmsUser;
import com.daily.english.app.dto.CmsUserDto;
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
public interface CmsUserMapper {

    @Select("<script>" +
            "SELECT " +
            "*" +
            "FROM " +
            "ed_cms_user " +
            "WHERE " +
            " 1=1 " +
            "</script>")
    List<CmsUser> selectList(CmsUser juryUser);

    @Select("<script>" +
            "SELECT " +
            " * " +
            "FROM " +
            "ed_cms_user " +
            "WHERE " +
            "id = #{id,jdbcType=INTEGER}" +
            "</script>")
    CmsUser selectById(@Param("id") String id);

    @Select("<script>" +
            "SELECT " +
            " * " +
            "FROM " +
            "ed_cms_user " +
            "WHERE 1=1 " +
            "AND status != -1 " +
            "AND account = #{userName,jdbcType=VARCHAR}" +
            "</script>")
    CmsUser selectByUserName(@Param("userName") String userName);

    @Insert("<script>" +
            "INSERT INTO ed_cms_user " +
            "<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\"> " +
            "<if test=\"name != null\"> " +
            "name, " +
            "</if> " +
            "<if test=\"execCount != null\"> " +
            "exec_count, " +
            "</if> " +
            "<if test=\"account != null\"> " +
            "account, " +
            "</if> " +
            "<if test=\"password != null\"> " +
            "password, " +
            "</if> " +
            "<if test=\"status != null\"> " +
            "status, " +
            "</if> " +
            "<if test=\"groupTypes != null\"> " +
            "group_types, " +
            "</if> " +
            "<if test=\"createUser != null\"> " +
            "create_user, " +
            "</if> " +
            "<if test=\"createTime != null\"> " +
            "create_time, " +
            "</if> " +
            "<if test=\"updateUser != null\"> " +
            "update_user, " +
            "</if> " +
            "<if test=\"updateTime != null\"> " +
            "update_time, " +
            "</if> " +
            "<if test=\"roleType != null\"> " +
            "role_type, " +
            "</if> " +
            "<if test=\"gender != null\"> " +
            "gender, " +
            "</if> " +
            "<if test=\"birthday != null\"> " +
            "birthday, " +
            "</if> " +
            "<if test=\"contact != null\"> " +
            "contact, " +
            "</if> " +
            "<if test=\"teacherUserId != null\"> " +
            "teacherUserId, " +
            "</if> " +
            "<if test=\"schoolId != null\"> " +
            "schoolId, " +
            "</if> " +
            "</trim> " +
            "<trim prefix=\"VALUES (\" suffix=\")\" suffixOverrides=\",\"> " +
            "<if test=\"name != null\"> " +
            "#{name,jdbcType=VARCHAR}, " +
            "</if> " +
            "<if test=\"execCount != null\"> " +
            "#{execCount,jdbcType=INTEGER}, " +
            "</if> " +
            "<if test=\"account != null\"> " +
            "#{account,jdbcType=VARCHAR}, " +
            "</if> " +
            "<if test=\"password != null\"> " +
            "#{password,jdbcType=VARCHAR}, " +
            "</if> " +
            "<if test=\"status != null\"> " +
            "#{status,jdbcType=INTEGER}, " +
            "</if> " +
            "<if test=\"groupTypes != null\"> " +
            "#{groupTypes,jdbcType=INTEGER}, " +
            "</if> " +
            "<if test=\"createUser != null\"> " +
            "#{createUser,jdbcType=VARCHAR}, " +
            "</if> " +
            "<if test=\"createTime != null\"> " +
            "#{createTime,jdbcType=VARCHAR}, " +
            "</if> " +
            "<if test=\"updateUser != null\"> " +
            "#{updateUser,jdbcType=VARCHAR}, " +
            "</if> " +
            "<if test=\"updateTime != null\"> " +
            "#{updateTime,jdbcType=VARCHAR}, " +
            "</if> " +
            "<if test=\"roleType != null\"> " +
            "#{roleType,jdbcType=INTEGER}, " +
            "</if> " +
            "<if test=\"gender != null\"> " +
            "#{gender,jdbcType=VARCHAR}, " +
            "</if> " +
            "<if test=\"birthday != null\"> " +
            "#{birthday,jdbcType=VARCHAR}, " +
            "</if> " +
            "<if test=\"contact != null\"> " +
            "#{contact,jdbcType=VARCHAR}, " +
            "</if> " +
            "<if test=\"teacherUserId != null\"> " +
            "#{teacherUserId,jdbcType=VARCHAR}, " +
            "</if> " +
            "<if test=\"schoolId != null\"> " +
            "#{schoolId,jdbcType=VARCHAR}, " +
            "</if> " +
            "</trim>" +
            "</script>")
    void insertCmsUser(CmsUser juryUser);

    @Update("<script>" +
            "UPDATE ed_cms_user " +
            "<set> " +
            "<if test=\"name != null\"> " +
            "   name = #{name,jdbcType=VARCHAR}, " +
            "</if> " +
            "<if test=\"execCount != null\"> " +
            "   exec_count = #{execCount,jdbcType=INTEGER}, " +
            "</if> " +
            "<if test=\"account != null\"> " +
            "   account = #{account,jdbcType=VARCHAR}, " +
            "</if> " +
            "<if test=\"password != null\"> " +
            "   password = #{password,jdbcType=VARCHAR}, " +
            "</if> " +
            "<if test=\"status != null\"> " +
            "   status = #{status,jdbcType=INTEGER}, " +
            "</if> " +
            "<if test=\"groupTypes != null\"> " +
            "   group_types = #{groupTypes,jdbcType=VARCHAR}, " +
            "</if> " +
            "<if test=\"createUser != null\"> " +
            "   create_user = #{createUser,jdbcType=VARCHAR}, " +
            "</if> " +
            "<if test=\"createTime != null\"> " +
            "   create_time = #{createTime,jdbcType=VARCHAR}, " +
            "</if> " +
            "<if test=\"updateUser != null\"> " +
            "   update_user = #{updateUser,jdbcType=VARCHAR}, " +
            "</if> " +
            "<if test=\"updateTime != null\"> " +
            "   update_time = #{updateTime,jdbcType=VARCHAR}, " +
            "</if> " +
            "<if test=\"roleType != null\"> " +
            "   role_type = #{roleType,jdbcType=INTEGER}, " +
            "</if> " +
            "<if test=\"gender != null\"> " +
            "   gender = #{gender,jdbcType=VARCHAR}, " +
            "</if> " +
            "<if test=\"birthday != null\"> " +
            "   birthday = #{birthday,jdbcType=VARCHAR}, " +
            "</if> " +
            "<if test=\"contact != null\"> " +
            "   contact = #{contact,jdbcType=VARCHAR}, " +
            "</if> " +
            "<if test=\"teacherUserId != null\"> " +
            "   teacherUserId = #{teacherUserId,jdbcType=VARCHAR}, " +
            "</if> " +
            "<if test=\"schoolId != null\"> " +
            "   schoolId = #{schoolId,jdbcType=VARCHAR}, " +
            "</if> " +
            "</set> " +
            "WHERE " +
            "id = #{id,jdbcType=INTEGER}" +
            "</script>")
    void updateCmsUser(CmsUser juryUser);

    @Select("<script>" +
            "SELECT " +
            "*" +
            "FROM " +
            "ed_cms_user " +
            "WHERE " +
            " 1=1 " +
            " AND status != -1 " +
            "<if test=\"roleTypes != null\">" +
            "   AND role_type IN" +
            "   <foreach item='roleType' collection='roleTypes' open='(' separator=',' close=')'>#{roleType}</foreach>" +
            "</if>" +
            "<if test=\"roleType != null\"> " +
            "           AND role_type = #{roleType,jdbcType=INTEGER} " +
            "</if> " +
            " <if test='keywords != null and keywords != \"\"'> " +
            "            AND ( " +
            "            name LIKE CONCAT('%', #{keywords, jdbcType=VARCHAR} , '%') " +
            "            OR " +
            "            account LIKE CONCAT('%', #{keywords, jdbcType=VARCHAR} , '%') " +
            "            ) " +
            " </if>" +
            " <if test='status != null'> " +
            "            AND status = #{status,jdbcType=INTEGER} " +
            " </if>" +
            "</script>")
    List<CmsUser> selectCmsUserPage(CmsUserDto juryUserDto);
}
