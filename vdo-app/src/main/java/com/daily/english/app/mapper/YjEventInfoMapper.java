package com.daily.english.app.mapper;

import com.daily.english.app.domain.YjEventInfo;
import com.daily.english.app.dto.YjEventInfoDto;
import com.daily.english.app.vo.YjEventInfoVo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface YjEventInfoMapper {

    @Insert("<script>" +
            "INSERT INTO yj_event_info " +
            "  <trim prefix= '( ' suffix= ') ' suffixOverrides= ', '> " +
            "   <if test= 'nameCh != null '> " +
            "    name_ch, " +
            "   </if> " +
            "   <if test= 'nameHk != null '> " +
            "    name_hk, " +
            "   </if> " +
            "   <if test= 'nameEn != null '> " +
            "    name_en, " +
            "   </if> " +
            "   <if test= 'bannerUrl != null '> " +
            "    banner_url, " +
            "   </if> " +
            "   <if test= 'bannerSquareUrl != null '> " +
            "    banner_square_url, " +
            "   </if> " +
            "   <if test= 'pdfJson != null '> " +
            "    pdf_json, " +
            "   </if> " +
            "   <if test= 'createUser != null '> " +
            "    create_user, " +
            "   </if> " +
            "   <if test= 'createTime != null '> " +
            "    create_time, " +
            "   </if> " +
            "   <if test= 'updateUser != null '> " +
            "    update_user, " +
            "   </if> " +
            "   <if test= 'updateTime != null '> " +
            "    update_time, " +
            "   </if> " +
            "   <if test= 'descriptionPicUrl != null '> " +
            "    description_pic_url, " +
            "   </if> " +
            "   <if test= 'descriptionVideoUrl != null '> " +
            "    description_video_url, " +
            "   </if> " +
            "   <if test= 'descriptionCh != null '> " +
            "    description_ch, " +
            "   </if> " +
            "   <if test= 'descriptionHk != null '> " +
            "    description_hk, " +
            "   </if> " +
            "   <if test= 'descriptionEn != null '> " +
            "    description_en, " +
            "   </if> " +
            "   <if test= 'picJson != null '> " +
            "    pic_json, " +
            "   </if> " +
            "   <if test= 'videoJson != null '> " +
            "    video_json, " +
            "   </if> " +
            "   <if test= 'sponsorsJson != null '> " +
            "    sponsors_json, " +
            "   </if> " +
            "   <if test= 'guestsJson != null '> " +
            "    guests_json, " +
            "   </if> " +
            "   <if test= 'playersJson != null '> " +
            "    players_json, " +
            "   </if> " +
            "   <if test= 'juryOrganJson != null '> " +
            "    jury_organ_json, " +
            "   </if> " +
            "   <if test= 'status != null '> " +
            "    status, " +
            "   </if> " +
            "    <if test= 'startTime != null '> " +
            "      start_time, " +
            "    </if> " +
            "    <if test= 'endTime != null '> " +
            "      end_time, " +
            "    </if> " +
            "  </trim> " +
            "  <trim prefix= 'VALUES ( ' suffix= ') ' suffixOverrides= ', '> " +
            "  <if test= 'nameCh != null '> " +
            "   #{nameCh,jdbcType=VARCHAR}, " +
            "  </if> " +
            "  <if test= 'nameHk != null '> " +
            "   #{nameHk,jdbcType=VARCHAR}, " +
            "  </if> " +
            "  <if test= 'nameEn != null '> " +
            "   #{nameEn,jdbcType=VARCHAR}, " +
            "  </if> " +
            "  <if test= 'bannerUrl != null '> " +
            "   #{bannerUrl,jdbcType=VARCHAR}, " +
            "  </if> " +
            "  <if test= 'bannerSquareUrl != null '> " +
            "   #{bannerSquareUrl,jdbcType=VARCHAR}, " +
            "  </if> " +
            "  <if test= 'pdfJson != null '> " +
            "   #{pdfJson,jdbcType=VARCHAR}, " +
            "  </if> " +
            "  <if test= 'createUser != null '> " +
            "   #{createUser,jdbcType=VARCHAR}, " +
            "  </if> " +
            "  <if test= 'createTime != null '> " +
            "   #{createTime,jdbcType=VARCHAR}, " +
            "  </if> " +
            "  <if test= 'updateUser != null '> " +
            "   #{updateUser,jdbcType=VARCHAR}, " +
            "  </if> " +
            "  <if test= 'updateTime != null '> " +
            "   #{updateTime,jdbcType=VARCHAR}, " +
            "  </if> " +
            "   <if test= 'descriptionPicUrl != null '> " +
            "   #{descriptionPicUrl,jdbcType=VARCHAR}, " +
            "   </if> " +
            "   <if test= 'descriptionVideoUrl != null '> " +
            "   #{descriptionVideoUrl,jdbcType=VARCHAR}, " +
            "   </if> " +
            "  <if test= 'descriptionCh != null '> " +
            "   #{descriptionCh,jdbcType=VARCHAR}, " +
            "  </if> " +
            "  <if test= 'descriptionHk != null '> " +
            "   #{descriptionHk,jdbcType=VARCHAR}, " +
            "  </if> " +
            "  <if test= 'descriptionEn != null '> " +
            "   #{descriptionEn,jdbcType=VARCHAR}, " +
            "  </if> " +
            "  <if test= 'picJson != null '> " +
            "   #{picJson,jdbcType=VARCHAR}, " +
            "  </if> " +
            "  <if test= 'videoJson != null '> " +
            "   #{videoJson,jdbcType=VARCHAR}, " +
            "  </if> " +
            "  <if test= 'sponsorsJson != null '> " +
            "   #{sponsorsJson,jdbcType=VARCHAR}, " +
            "  </if> " +
            "  <if test= 'guestsJson != null '> " +
            "   #{guestsJson,jdbcType=VARCHAR}, " +
            "  </if> " +
            "  <if test= 'playersJson != null '> " +
            "   #{playersJson,jdbcType=VARCHAR}, " +
            "  </if> " +
            "  <if test= 'juryOrganJson != null '> " +
            "   #{juryOrganJson,jdbcType=VARCHAR}, " +
            "  </if> " +
            "  <if test= 'status != null '> " +
            "   #{status,jdbcType=INTEGER}, " +
            "  </if> " +
            "  <if test= 'startTime != null '> " +
            "    #{startTime,jdbcType=VARCHAR}, " +
            "  </if> " +
            "  <if test= 'endTime != null '> " +
            "    #{endTime,jdbcType=VARCHAR}, " +
            "  </if> " +
            "  </trim>" +
            "</script>")
    void insertEventInfo(YjEventInfo eventInfo);

    @Update("<script>" +
            "UPDATE yj_event_info " +
            "    <set> " +
            "    <if test= 'nameCh != null '> " +
            "      name_ch = #{nameCh,jdbcType=VARCHAR}, " +
            "    </if> " +
            "    <if test= 'nameHk != null '> " +
            "      name_hk = #{nameHk,jdbcType=VARCHAR}, " +
            "    </if> " +
            "    <if test= 'nameEn != null '> " +
            "      name_en = #{nameEn,jdbcType=VARCHAR}, " +
            "    </if> " +
            "    <if test= 'bannerUrl != null '> " +
            "      banner_url = #{bannerUrl,jdbcType=VARCHAR}, " +
            "    </if> " +
            "    <if test= 'bannerSquareUrl != null '> " +
            "      banner_square_url = #{bannerSquareUrl,jdbcType=VARCHAR}, " +
            "    </if> " +
            "    <if test= 'pdfJson != null '> " +
            "      pdf_json = #{pdfJson,jdbcType=VARCHAR}, " +
            "    </if> " +
            "    <if test= 'createUser != null '> " +
            "      create_user = #{createUser,jdbcType=VARCHAR}, " +
            "    </if> " +
            "    <if test= 'createTime != null '> " +
            "      create_time = #{createTime,jdbcType=VARCHAR}, " +
            "    </if> " +
            "    <if test= 'updateUser != null '> " +
            "      update_user = #{updateUser,jdbcType=VARCHAR}, " +
            "    </if> " +
            "    <if test= 'updateTime != null '> " +
            "      update_time = #{updateTime,jdbcType=VARCHAR}, " +
            "    </if> " +
            "   <if test= 'descriptionPicUrl != null '> " +
            "      description_pic_url = #{descriptionPicUrl,jdbcType=VARCHAR}, " +
            "   </if> " +
            "   <if test= 'descriptionVideoUrl != null '> " +
            "      description_video_url = #{descriptionVideoUrl,jdbcType=VARCHAR}, " +
            "   </if> " +
            "    <if test= 'descriptionCh != null '> " +
            "      description_ch = #{descriptionCh,jdbcType=VARCHAR}, " +
            "    </if> " +
            "    <if test= 'descriptionHk != null '> " +
            "      description_hk = #{descriptionHk,jdbcType=VARCHAR}, " +
            "    </if> " +
            "    <if test= 'descriptionEn != null '> " +
            "      description_en = #{descriptionEn,jdbcType=VARCHAR}, " +
            "    </if> " +
            "    <if test= 'picJson != null '> " +
            "      pic_json = #{picJson,jdbcType=VARCHAR}, " +
            "    </if> " +
            "    <if test= 'videoJson != null '> " +
            "      video_json = #{videoJson,jdbcType=VARCHAR}, " +
            "    </if> " +
            "    <if test= 'sponsorsJson != null '> " +
            "      sponsors_json = #{sponsorsJson,jdbcType=VARCHAR}, " +
            "    </if> " +
            "    <if test= 'guestsJson != null '> " +
            "      guests_json = #{guestsJson,jdbcType=VARCHAR}, " +
            "    </if> " +
            "    <if test= 'playersJson != null '> " +
            "      players_json = #{playersJson,jdbcType=VARCHAR}, " +
            "    </if> " +
            "    <if test= 'juryOrganJson != null '> " +
            "      jury_organ_json = #{juryOrganJson,jdbcType=VARCHAR}, " +
            "    </if> " +
            "    <if test= 'status != null '> " +
            "      status = #{status,jdbcType=INTEGER}, " +
            "    </if> " +
            "    <if test= 'startTime != null '> " +
            "      start_time = #{startTime,jdbcType=VARCHAR}, " +
            "    </if> " +
            "    <if test= 'endTime != null '> " +
            "      end_time = #{endTime,jdbcType=VARCHAR}, " +
            "    </if> " +
            "    </set> " +
            "    WHERE " +
            "      id = #{id,jdbcType=INTEGER}" +
            "</script>")
    int updateEventInfo(YjEventInfo eventInfo);

    @Delete("delete from yj_event_info where id = #{id}")
    void deleteEventInfoById(@Param("id") String id);

    @Select("SELECT " +
            "* " +
            "FROM " +
            "yj_event_info " +
            "WHERE " +
            "id = #{id} ")
    YjEventInfo findEventInfoById(@Param("id") String id);

    @Select("<script>" +
            "SELECT " +
            "* " +
            "FROM " +
            "yj_event_info " +
            "WHERE " +
            "1=1 " +
            "<if test= 'status != null '> " +
            "  AND status = #{status,jdbcType=INTEGER} " +
            "</if> " +
            "</script>")
    List<YjEventInfo> findEventInfoList(YjEventInfo eventInfo);

    @Update("UPDATE yj_event_info " +
            "SET status = #{status} " +
            "WHERE id  =#{id} ")
    void updateEventInfoStatus(@Param("id") String id, @Param("status") Integer status);

    @Select("<script>" +
            "SELECT " +
            "yei.id, yei.name_ch, yei.create_time, yei.create_user, yei.update_time, yei.update_user, yei.status " +
            "FROM " +
            "yj_event_info yei " +
            "WHERE " +
            "1=1 " +
            " AND yei.status != -1 " +
            " <if test= 'status != null '> " +
            "  AND yei.status = #{status,jdbcType=INTEGER} " +
            " </if> " +
            " <if test='keywords != null and keywords != \"\"'> " +
            "            AND ( " +
            "            yei.name_ch LIKE CONCAT('%', #{keywords, jdbcType=VARCHAR} , '%') " +
            "            OR " +
            "            yei.name_hk LIKE CONCAT('%', #{keywords, jdbcType=VARCHAR} , '%') " +
            "            OR " +
            "            yei.name_en LIKE CONCAT('%', #{keywords, jdbcType=VARCHAR} , '%') " +
            "            )" +
            " </if>" +
            " <if test=\"startDate != null\"> " +
            "            AND yei.create_time &gt;= #{startDate} " +
            " </if> " +
            " <if test=\"endDate != null\"> " +
            "            AND yei.create_time &lt;= #{endDate} " +
            " </if>" +
            " ORDER BY yei.create_time DESC " +
            "</script>")
    List<YjEventInfoVo> selectEventInfoPage(YjEventInfoDto yjEventInfoDto);


    @Select("<script>" +
            "SELECT " +
            "yei.id, yei.name_ch, yei.create_time, yei.create_user, yei.update_time, yei.update_user, yei.status " +
            "FROM " +
            "yj_event_info yei " +
            "WHERE " +
            "1=1 " +
            " <if test= 'status != null '> " +
            "  AND yei.status = #{status,jdbcType=INTEGER} " +
            " </if> " +
            " ORDER BY yei.create_time DESC " +
            "</script>")
    List<YjEventInfoVo> selectEventInfoList(YjEventInfoDto yjEventInfoDto);
}
