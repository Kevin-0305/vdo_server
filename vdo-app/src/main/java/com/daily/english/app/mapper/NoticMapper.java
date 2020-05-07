package com.daily.english.app.mapper;

import com.daily.english.app.domain.Notic;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * class_name: ParnerClassMapper
 * package: com.daily.english.app.mapper
 * describe: TODO
 * creat_user: liuzd@chinadailyhk.com
 * creat_date: 2019-09-05
 * creat_time: 09:39
 **/
@Mapper
@Component
public interface NoticMapper {
    @Select("<script>SELECT\n" +
            "\tid,\n" +
            "\tusTitle,\n" +
            "\tcnTitle,\n" +
            "\thkTitle,\n" +
            "\tusContent,\n" +
            "\tcnContent,\n" +
            "\thkContent,\n" +
            "\tisRead,\n" +
            "\tcreateTime,\n" +
            "\tcreateUser,\n" +
            "\turl,\n" +
            "\tupdateTime,\n" +
            "\tupdateUser, \n" +
            "userId\n" +
            "FROM\n" +
            "\ted_notic \n" +
            "WHERE\n" +
            "\t1 = 1 \n" +
            "\t<if test=\"isRead !=null and isRead!=''\">\n" +
            "\tAND isRead = #{isRead}\n" +
            "\t</if>\n" +
            "\tAND userId =#{userId}\n" +
            "\tLIMIT #{pageNo},#{limit}</script>")
    List<Notic> getMyNoticList(@Param("userId") String userId, @Param("pageNo") Integer pageNo, @Param("limit") Integer limit, @Param("isRead") String isRead);

    @Select("<script>select \n" +
            "id,\n" +
            "usTitle,\n" +
            "cnTitle,\n" +
            "hkTitle,\n" +
            "usContent,\n" +
            "cnContent,\n" +
            "hkContent,\n" +
            "isRead,\n" +
            "createTime,\n" +
            "createUser,\n" +
            "updateTime,\n" +
            "url,\n" +
            "updateUser,\n" +
            "userId\n" +
            " from ed_notic\n" +
            " where 1=1 and id = #{noticId}</script>")
    Notic getMyNotic(String noticId);

    @Select("<script>SELECT\n" +
            "\tcount(1)\n" +
            "FROM\n" +
            "\ted_notic \n" +
            "WHERE\n" +
            "\t1 = 1 \n" +
            "\tAND userId = #{userId}</script>")
    Integer getMyNoticUnReadCount(String userId);

    @Select("<script>SELECT count(1) FROM\n" +
            "\ted_notic \n" +
            "WHERE\n" +
            "\t1 = 1 \n" +
            "\t<if test=\"isRead !=null and isRead!=''\">\n" +
            "\tAND isRead = #{isRead}\n" +
            "\t</if>\n" +
            "\tAND userId =#{userId}</script>")
    Integer getMyNoticListCount(@Param("userId") String userId, @Param("isRead") String isRead);

    @Update("update ed_notic set  isRead = '1' where id=#{id}")
    void updateById(Notic notic);
}