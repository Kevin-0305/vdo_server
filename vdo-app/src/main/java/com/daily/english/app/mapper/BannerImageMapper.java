package com.daily.english.app.mapper;


import com.daily.english.app.domain.BannerImage;
import com.daily.english.app.dto.PageSearchDto;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;


@Mapper
@Component
public interface BannerImageMapper {

    @Insert("<script>" +
            "INSERT INTO ed_banner_image " +
            "        <trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\"> " +
            "            <if test=\"title != null\"> " +
            "                title, " +
            "            </if> " +
            "            <if test=\"link != null\"> " +
            "                link, " +
            "            </if> " +
            "            <if test=\"pc != null\"> " +
            "                pc, " +
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
            "            <if test=\"pc != null\"> " +
            "                #{pc,jdbcType=VARCHAR}, " +
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
    void insert(BannerImage launchImage);

    @Update("<script>" +
            "UPDATE ed_banner_image " +
            "        <set> " +
            "            <if test=\"title != null\"> " +
            "                title = #{title,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"link != null\"> " +
            "                link = #{link,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"pc != null\"> " +
            "                pc = #{pc,jdbcType=VARCHAR}, " +
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
    int update(BannerImage launchImage);

    @Delete("delete from ed_banner_image where id = #{id}")
    void deleteById(@Param("id") String id);

    @Select("SELECT " +
            " * " +
            " FROM " +
            " ed_banner_image " +
            " WHERE " +
            " 1=1" +
            " AND id  =#{id}")
    BannerImage selectById(@Param("id") String id);

    /**
     * 查询是否在有效时间内
     *
     * @return
     */
    @Select("SELECT * " +
            "FROM ed_banner_image " +
            "WHERE now() >= start_time AND now() <= end_time;")
    List<BannerImage> selectBannerImageByNowTime();

    @Select("<script>" +
            "SELECT " +
            " * " +
            " FROM " +
            " ed_banner_image " +
            " WHERE " +
            " 1=1" +
            "</script>")
    List<BannerImage> selectList(BannerImage launchImage);

    @Select("<script>" +
            " SELECT " +
            " * " +
            " FROM " +
            " ed_banner_image " +
            " WHERE " +
            " 1=1 " +
            " <if test='keywords != null and keywords != \"\"'> " +
            "      AND ( " +
            "          title LIKE CONCAT('%', #{keywords, jdbcType=VARCHAR} , '%') " +
            "      ) " +
            " </if>" +
            "ORDER BY create_time DESC" +
            "</script>")
    List<BannerImage> selectListPage(PageSearchDto pageSearchDto);


}
