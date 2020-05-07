package com.daily.english.app.mapper;

import com.daily.english.app.domain.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserMapper {

    @Select("select id, useraccount as username, password, name, role, nickname, userImg, orderMap, " +
            "sex, introduction, birthday, purpose,language," +
            "facebookUID, create_time as createTime, location, user_type as user_type, vip_enddate as vip_enddate " +
            "from ed_user where id = #{id}")
    User findUserById(@Param("id") String id);

    @Select("select id, useraccount as username, password, name, role, nickname, userImg, orderMap, " +
            "facebookUID, create_time as createTime, location, user_type as user_type, vip_enddate as vip_enddate " +
            "from ed_user where useraccount = #{username}")
    User findUserByName(@Param("username") String username);

    @Update("update ed_user set useraccount=#{username}, password=#{password}, name=#{name}, role=#{role}, " +
            "nickname=#{nickname}, userImg=#{userImg}, facebookUID=#{facebookUID}, orderMap=#{orderMap}, " +
            "location=#{location}, user_type=#{userType}, vip_enddate=#{vipEndDate} " +
            "where id=#{id}")
    int updateUser(User user);

    @Insert("insert into ed_user(useraccount, password, nickname, create_time) " +
            "values(#{username}, #{password}, #{nickname}, #{createTime})")
    @Options(useGeneratedKeys = true, keyColumn = "id")
    long insertUser(User user);
    @Update("<script>" +
            "update ed_user <set>" +
            "  <if test=\"birthday != null and birthday != '' \"> " +
            "    birthday=#{birthday}," +
            "  </if> " +
            "  <if test=\"sex != null and sex != '' \"> " +
            "    sex=#{sex}," +
            "  </if> " +
            "  <if test=\"purpose != null\"> " +
            "    purpose=#{purpose}," +
            "  </if> " +
            "  <if test=\"language != null\"> " +
            "    language=#{language}," +
            "  </if> " +
            "  <if test=\"userImg != null and userImg != ''\"> " +
            "    userImg=#{userImg}," +
            "  </if> " +
            "  <if test=\"nickname != null and nickname != '' \"> " +
            "    nickname=#{nickname}," +
            "  </if> " +
            "  <if test=\"introduction != null and introduction != '' \"> " +
            "    introduction = #{introduction}," +
            "  </if></set> " +
            "where id=#{id}"+
            "</script>")
    int updateUserInformation(User user);


}
