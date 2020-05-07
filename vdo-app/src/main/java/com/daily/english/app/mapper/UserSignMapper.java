package com.daily.english.app.mapper;

import com.daily.english.app.domain.UserSign;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserSignMapper {

    //根据ID查询最后一次签到记录
    @Select("SELECT user_id userId,count,create_time createTime,last_modify_time lastModifyTime,sign_count signCount   " +
            "        FROM ed_user_sign " +
            "        WHERE user_id = #{userId} " )
    UserSign findUserSignByUserId(@Param("userId") Long userId);


    //根据业务保存
    @Insert("<script>" +
            "INSERT INTO ed_user_sign(user_id,count,create_time,last_modify_time,sign_count) values " +
            " (#{userId},#{count},#{createTime},#{lastModifyTime},#{signCount}) " +
            "</script>")
    int insertSelective(UserSign userSign);
    //根据ID、业务更新
    @Update("<script>" +
            "UPDATE ed_user_sign " +
            "        <set> " +
            "            <if test=\"signCount != null\"> " +
            "                sign_count = #{signCount}, " +
            "            </if> " +
            "            <if test=\"lastModifyTime != null and lastModifyTime != ''\"> " +
            "                last_modify_time = #{lastModifyTime}, " +
            "            </if> " +
            "            <if test=\"count != null\"> " +
            "                count = #{count}, " +
            "            </if> " +
            "        </set> " +
            "        WHERE  " +
            "        user_id = #{userId}" +
            "</script>")
    int updateUserSign(UserSign userSign);
}