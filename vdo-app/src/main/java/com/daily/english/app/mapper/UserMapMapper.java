package com.daily.english.app.mapper;

import com.daily.english.app.domain.UserMap;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserMapMapper {

    @Select("select id, s1MapScore , s2MapScore, s3MapScore, s4MapScore, " +
            "s5MapScore, s6MapScore, isfirst, unblock  " +
            " from ed_user_map where id = #{id}")
    UserMap findUserMapByUserId(@Param("id") String id);

    @Insert("insert into ed_user_map(id, s1MapScore , s2MapScore, s3MapScore, s4MapScore,s5MapScore, s6MapScore) " +
            " values(#{id}, #{s1MapScore}, #{s2MapScore}, #{s3MapScore}, #{s4MapScore}, #{s5MapScore}, #{s6MapScore})")
    void insertUserMap(UserMap userMap);

    @Update("<script>  update ed_user_map <set>" +
            "  <if test=\"isfirst != null \"> " +
            "    isfirst=#{isfirst}," +
            "  </if> " +
            "  <if test=\"unblock != null and unblock != '' \"> " +
            "    unblock=#{unblock}," +
            "  </if> " +
            "  <if test=\"s1MapScore != null and s1MapScore != '' \"> " +
            "    s1MapScore=#{s1MapScore}," +
            "  </if> " +
            "  <if test=\"s2MapScore != null and s2MapScore != '' \"> " +
            "    s2MapScore=#{s2MapScore}," +
            "  </if> " +
            "  <if test=\"s3MapScore != null and s3MapScore != '' \"> " +
            "    s3MapScore=#{s3MapScore}," +
            "  </if> " +
            "  <if test=\"s4MapScore != null and s4MapScore != '' \"> " +
            "    s4MapScore=#{s4MapScore}," +
            "  </if> " +
            "  <if test=\"s5MapScore != null and s5MapScore != '' \"> " +
            "    s5MapScore=#{s5MapScore}," +
            "  </if> " +
            "  <if test=\"s6MapScore != null and s6MapScore != '' \"> " +
            "    s6MapScore=#{s6MapScore}," +
            "  </if> " +
            "</set> where id=#{id}  </script>")
    int updateUserMap(UserMap user);

    @Update("<script>" +
            " UPDATE ed_user_map " +
            "   SET unblock=#{unblock} " +
            " WHERE id=#{id}  " +
            "</script>")
    int updateUserMapUnblock(UserMap user);

}
