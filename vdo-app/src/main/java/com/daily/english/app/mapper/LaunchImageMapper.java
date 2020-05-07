package com.daily.english.app.mapper;


import com.daily.english.app.domain.LaunchImage;
import com.daily.english.app.dto.PageSearchDto;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;


@Mapper
@Component
public interface LaunchImageMapper {

    @Insert("<script>" +
            "INSERT INTO ed_launch_image " +
            "        <trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\"> " +
            "            <if test=\"title != null\"> " +
            "                title, " +
            "            </if> " +
            "            <if test=\"link != null\"> " +
            "                link, " +
            "            </if> " +
            "            <if test=\"iphone != null\"> " +
            "                iphone, " +
            "            </if> " +
            "            <if test=\"ipad != null\"> " +
            "                ipad, " +
            "            </if> " +
            "            <if test=\"android != null\"> " +
            "                android, " +
            "            </if> " +
            "            <if test=\"androidPad != null\"> " +
            "                android_pad, " +
            "            </if> " +
            "            <if test=\"startTime != null\"> " +
            "                start_time, " +
            "            </if> " +
            "            <if test=\"endTime != null\"> " +
            "                end_time, " +
            "            </if> " +
            "            <if test=\"createUser != null\"> " +
            "                create_user, " +
            "            </if> " +
            "            <if test=\"createTime != null\"> " +
            "                create_time, " +
            "            </if> " +
            "            <if test=\"updateUser != null\"> " +
            "                update_user, " +
            "            </if> " +
            "            <if test=\"updateTime != null\"> " +
            "                update_time, " +
            "            </if> " +
            "        </trim> " +
            "        <trim prefix=\"VALUES (\" suffix=\")\" suffixOverrides=\",\"> " +
            "            <if test=\"title != null\"> " +
            "                #{title,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"link != null\"> " +
            "                #{link,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"iphone != null\"> " +
            "                #{iphone,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"ipad != null\"> " +
            "                #{ipad,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"android != null\"> " +
            "                #{android,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"androidPad != null\"> " +
            "                #{androidPad,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"startTime != null\"> " +
            "                #{startTime,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"endTime != null\"> " +
            "                #{endTime,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"createUser != null\"> " +
            "                #{createUser,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"createTime != null\"> " +
            "                #{createTime,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"updateUser != null\"> " +
            "                #{updateUser,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"updateTime != null\"> " +
            "                #{updateTime,jdbcType=VARCHAR}, " +
            "            </if> " +
            "        </trim>" +
            "</script>")
    void insert(LaunchImage launchImage);

    @Update("<script>" +
            "UPDATE ed_launch_image " +
            "        <set> " +
            "            <if test=\"title != null\"> " +
            "                title = #{title,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"link != null\"> " +
            "                link = #{link,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"iphone != null\"> " +
            "                iphone = #{iphone,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"ipad != null\"> " +
            "                ipad = #{ipad,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"android != null\"> " +
            "                android = #{android,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"androidPad != null\"> " +
            "                android_pad = #{androidPad,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"startTime != null\"> " +
            "                start_time = #{startTime,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"endTime != null\"> " +
            "                end_time = #{endTime,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"createUser != null\"> " +
            "                create_user = #{createUser,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"createTime != null\"> " +
            "                create_time = #{createTime,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"updateUser != null\"> " +
            "                update_user = #{updateUser,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"updateTime != null\"> " +
            "                update_time = #{updateTime,jdbcType=VARCHAR}, " +
            "            </if> " +
            "        </set> " +
            "        WHERE " +
            "        id = #{id,jdbcType=INTEGER}" +
            "</script>")
    int update(LaunchImage launchImage);

    @Delete("delete from ed_launch_image where id = #{id}")
    void deleteById(@Param("id") String id);

    @Select("SELECT " +
            " * " +
            " FROM " +
            " ed_launch_image " +
            " WHERE " +
            " 1=1" +
            " AND id  =#{id}")
    LaunchImage selectById(@Param("id") String id);

    /**
     * 查询是否在有效时间内
     *
     * @return
     */
    @Select("SELECT * " +
            "FROM ed_launch_image " +
            "WHERE now() >= start_time AND now() <= end_time;")
    List<LaunchImage> selectLaunchImageByNowTime();

    @Select("<script>" +
            "SELECT " +
            " * " +
            " FROM " +
            " ed_launch_image " +
            " WHERE " +
            " 1=1" +
            "</script>")
    List<LaunchImage> selectList(LaunchImage launchImage);

    @Select("<script>" +
            "SELECT eli.*\n" +
            "FROM ed_launch_image eli\n" +
            "WHERE eli.end_time IS NOT NULL\n" +
            "      AND eli.end_time != ''\n" +
            "      AND eli.start_time IS NOT NULL AND eli.start_time != ''\n" +
            "      AND current_timestamp() <![CDATA[ >= ]]> eli.start_time\n" +
            "      AND current_timestamp() <![CDATA[ <= ]]> eli.end_time\n" +
            "ORDER BY eli.create_time" +
            "</script>")
    List<LaunchImage> appLaunchImageData();


    @Select("<script>" +
            "SELECT * " +
            "FROM ed_launch_image eli " +
            "WHERE 1 = 1 " +
            " <if test='keywords != null and keywords != \"\"'> " +
            "      AND ( " +
            "          title LIKE CONCAT('%', #{keywords, jdbcType=VARCHAR} , '%') " +
            "      ) " +
            " </if>" +
            " <if test=\"searchDate != null\"> " +
            "    AND eli.start_time &lt;= #{searchDate} " +
            "    AND eli.end_time &gt;= #{searchDate} " +
            " </if> " +
            " ORDER BY eli.create_time DESC" +
            "</script>")
    List<LaunchImage> selectListPage(PageSearchDto pageSearchDto);


}
