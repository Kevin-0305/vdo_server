package com.daily.english.app.mapper;

import com.daily.english.app.domain.UserRecord;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface UserRecordMapper {

    @Select("select  *  " +
            "from ed_user_record where id = #{id}")
    UserRecord findUserRecordById(@Param("id") String id);
    @Select("<script> select  *  " +
            "from ed_user_record where 1=1 " +
            "<if test=\"sourceId != null and sourceId != ''\"> " +
            "    and   sourceId = #{sourceId} " +
            " </if> " +
            "<if test=\"type != null and type != ''\"> " +
            "    and   type = #{type} " +
            " </if> " +
            "<if test=\"userId != null and userId != ''\"> " +
            "    and   userId = #{userId} " +
            " </if> " +
            "limit 1" +
            "  </script>")
    UserRecord findUserRecord(UserRecord ur);

    @Insert("<script> insert into ed_user_record" +
            "(id,userId,content,type,targetValue,beginValue,changeValue,createTime,sourceId" +
            "  <if test=\"isTake != null \"> " +
            "  ,isTake" +
            "  </if> " +
            ") "+
            "values(#{id},#{userId},#{content},#{type},#{targetValue},#{beginValue},#{changeValue},#{createTime},"+
            "#{sourceId}" +
            "  <if test=\"isTake != null \"> " +
            "  ,#{isTake}" +
            "  </if> " +
            ")" +
            "</script>")
    void insertUserRecord(UserRecord record);

    @Select("<script> select *  " +
            " from ed_user_record  where  1=1 " +
            "  <if test=\"sourceId != null and sourceId != '' \"> " +
            "   and  sourceId=#{sourceId}" +
            "  </if> " +
            "<if test=\"userId != null and userId != '' \"> " +
            "    and   userId = #{userId} " +
            " </if> " +
            "  </script>")
    List<UserRecord> findUserRecordList(UserRecord ur);

    @Update("<script> UPDATE ed_user_record  <set> " +
            "<if test=\"isTake != null\"> " +
            "       isTake = ${isTake} ," +
            " </if> " +
            "</set>  WHERE id=#{id}  "+
             "  </script>")
    void updateUserRecord(UserRecord record);

}
