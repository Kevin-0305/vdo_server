package com.daily.english.app.mapper;

import com.daily.english.app.domain.YjEventNews;
import com.daily.english.app.dto.YjEventNewsDto;
import com.daily.english.app.vo.YjEventNewsVo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface YjEventNewsMapper {

    @Insert("<script>" +
            "INSERT INTO yj_event_news " +
            "  <trim prefix='(' suffix=')' suffixOverrides=','> " +
            "   <if test='titleCh != null'> " +
            "    title_ch, " +
            "   </if> " +
            "   <if test='titleHk != null'> " +
            "    title_hk, " +
            "   </if> " +
            "   <if test='titleEn != null'> " +
            "    title_en, " +
            "   </if> " +
            "   <if test='contentHk != null'> " +
            "    content_hk, " +
            "   </if> " +
            "   <if test='contentCh != null'> " +
            "    content_ch, " +
            "   </if> " +
            "   <if test='contentEn != null'> " +
            "    content_en, " +
            "   </if> " +
            "   <if test='eventId != null'> " +
            "    event_id, " +
            "   </if> " +
            "   <if test='createUser != null'> " +
            "    create_user, " +
            "   </if> " +
            "   <if test='createTime != null'> " +
            "    create_time, " +
            "   </if> " +
            "   <if test='updateUser != null'> " +
            "    update_user, " +
            "   </if> " +
            "   <if test='updateTime != null'> " +
            "    update_time, " +
            "   </if> " +
            "   <if test='imgUrl != null'> " +
            "    img_url, " +
            "   </if> " +
            "   <if test='newsAttaJson != null'> " +
            "    newsAttaJson, " +
            "   </if> " +
            "  </trim> " +
            "  <trim prefix='VALUES (' suffix=')' suffixOverrides=','> " +
            "  <if test='titleCh != null'> " +
            "   #{titleCh,jdbcType=VARCHAR}, " +
            "  </if> " +
            "  <if test='titleHk != null'> " +
            "   #{titleHk,jdbcType=VARCHAR}, " +
            "  </if> " +
            "  <if test='titleEn != null'> " +
            "   #{titleEn,jdbcType=VARCHAR}, " +
            "  </if> " +
            "  <if test='contentHk != null'> " +
            "   #{contentHk,jdbcType=VARCHAR}, " +
            "  </if> " +
            "  <if test='contentCh != null'> " +
            "   #{contentCh,jdbcType=VARCHAR}, " +
            "  </if> " +
            "  <if test='contentEn != null'> " +
            "   #{contentEn,jdbcType=VARCHAR}, " +
            "  </if> " +
            "  <if test='eventId != null'> " +
            "   #{eventId,jdbcType=VARCHAR}, " +
            "  </if> " +
            "  <if test='createUser != null'> " +
            "   #{createUser,jdbcType=VARCHAR}, " +
            "  </if> " +
            "  <if test='createTime != null'> " +
            "   #{createTime,jdbcType=VARCHAR}, " +
            "  </if> " +
            "  <if test='updateUser != null'> " +
            "   #{updateUser,jdbcType=VARCHAR}, " +
            "  </if> " +
            "  <if test='updateTime != null'> " +
            "   #{updateTime,jdbcType=VARCHAR}, " +
            "  </if> " +
            "  <if test='imgUrl != null'> " +
            "   #{imgUrl,jdbcType=VARCHAR}, " +
            "  </if> " +
            "   <if test='newsAttaJson != null'> " +
            "   #{newsAttaJson,jdbcType=VARCHAR}, " +
            "   </if> " +
            "  </trim>" +
            "</script>")
    void insertEventNews(YjEventNews eventNews);

    @Update("<script>" +
            "UPDATE yj_event_news " +
            "  <set> " +
            "  <if test='titleCh != null'> " +
            "   title_ch = #{titleCh,jdbcType=VARCHAR}, " +
            "  </if> " +
            "  <if test='titleHk != null'> " +
            "   title_hk = #{titleHk,jdbcType=VARCHAR}, " +
            "  </if> " +
            "  <if test='titleEn != null'> " +
            "   title_en = #{titleEn,jdbcType=VARCHAR}, " +
            "  </if> " +
            "  <if test='contentHk != null'> " +
            "   content_hk = #{contentHk,jdbcType=VARCHAR}, " +
            "  </if> " +
            "  <if test='contentCh != null'> " +
            "   content_ch = #{contentCh,jdbcType=VARCHAR}, " +
            "  </if> " +
            "  <if test='contentEn != null'> " +
            "   content_en = #{contentEn,jdbcType=VARCHAR}, " +
            "  </if> " +
            "  <if test='eventId != null'> " +
            "   event_id = #{eventId,jdbcType=VARCHAR}, " +
            "  </if> " +
            "  <if test='createUser != null'> " +
            "   create_user = #{createUser,jdbcType=VARCHAR}, " +
            "  </if> " +
            "  <if test='createTime != null'> " +
            "   create_time = #{createTime,jdbcType=VARCHAR}, " +
            "  </if> " +
            "  <if test='updateUser != null'> " +
            "   update_user = #{updateUser,jdbcType=VARCHAR}, " +
            "  </if> " +
            "  <if test='updateTime != null'> " +
            "   update_time = #{updateTime,jdbcType=VARCHAR}, " +
            "  </if> " +
            "  <if test='imgUrl != null'> " +
            "   img_url = #{imgUrl,jdbcType=VARCHAR}, " +
            "  </if> " +
            "   <if test='newsAttaJson != null'> " +
            "   newsAttaJson = #{newsAttaJson,jdbcType=VARCHAR}, " +
            "   </if> " +
            "  </set> " +
            "  WHERE " +
            "   id = #{id,jdbcType=INTEGER}" +
            "</script>")
    int updateEventNews(YjEventNews eventNews);

    @Delete("delete from yj_event_news where id = #{id}")
    void deleteEventNewsById(@Param("id") String id);

    @Select("SELECT " +
            " * " +
            " FROM " +
            " yj_event_news " +
            " WHERE " +
            " 1=1" +
            " AND id  =#{id}")
    YjEventNews findEventNewsById(@Param("id") String id);

    @Select("SELECT " +
            " * " +
            " FROM " +
            " yj_event_news " +
            " WHERE " +
            " 1=1")
    List<YjEventNews> findEventNewsList();

    @Select("<script>" +
            "SELECT yen.id, yen.event_id, yen.title_ch, yen.title_hk, yen.title_en, yei.name_ch as event_name, yen.create_time, " +
            "yen.create_user, yen.update_time, yen.update_user, yen.img_url " +
            "FROM yj_event_news yen " +
            "         LEFT JOIN yj_event_info yei ON yen.event_id = yei.id " +
            "WHERE 1=1 " +
            " <if test='keywords != null and keywords != \"\"'> " +
            "            AND ( " +
            "            title_ch LIKE CONCAT('%', #{keywords, jdbcType=VARCHAR} , '%') " +
            "            OR " +
            "            title_hk LIKE CONCAT('%', #{keywords, jdbcType=VARCHAR} , '%') " +
            "            OR " +
            "            title_en LIKE CONCAT('%', #{keywords, jdbcType=VARCHAR} , '%') " +
            "            ) " +
            " </if>" +
            " <if test='eventId != null'> " +
            "            AND yen.event_id = #{eventId,jdbcType=INTEGER} " +
            " </if>" +
            " <if test=\"startDate != null\"> " +
            "            AND yen.create_time &gt;= #{startDate} " +
            " </if> " +
            " <if test=\"endDate != null\"> " +
            "            AND yen.create_time &lt;= #{endDate} " +
            " </if> " +
            " ORDER BY yen.create_time DESC " +
            "</script>")
    List<YjEventNewsVo> findEventNewsPage(YjEventNewsDto yjEventNewsDto);


}
