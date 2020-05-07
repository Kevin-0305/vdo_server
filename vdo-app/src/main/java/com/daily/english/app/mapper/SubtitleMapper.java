package com.daily.english.app.mapper;

import com.daily.english.app.domain.Subtitle;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface SubtitleMapper {

    @Insert("insert into ed_subtitle(video_id, title, beginTime, endTime, english, " +
            "simp_chinese, trad_chinese, status, timeSs) " +
            "values(#{videoId}, #{title}, #{beginTime}, #{endTime}, #{english}, " +
            "#{simpChinese}, #{tradChinese}, #{status}, #{timeSs})")
    @Options(useGeneratedKeys = true, keyProperty="id", keyColumn = "id")
    void insertSubtitle(Subtitle subtitle);

    @Delete("delete from ed_subtitle where video_id = #{id}")
    void deleteSubtitleByVideoId(@Param("id") Long id);

    @Select("select id, video_id, title, beginTime, endTime, english, " +
            "simp_chinese, trad_chinese, status, timeSs " +
            "from ed_subtitle where video_id = #{id}")
    List<Subtitle> findSubtitleListByVideoId(@Param("id") Long id);
}
