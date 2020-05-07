package com.daily.english.app.mapper;

import com.daily.english.app.condition.CollectCondition;
import com.daily.english.app.condition.VideoCondition;
import com.daily.english.app.domain.Collect;
import com.daily.english.app.domain.Video;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface VideoMapper {

    @Insert("insert into ed_video(title, browse, praise, collect, type, " +
            "videoUrl, downloadUrl, cloud_url, imgUrl, video_thumb, video_thumb_bigger, editor, hot, editorPick, " +
            "isRelease, isOpen, isQuiz, addDate, time_length, status, remark, level, pub_time, lastEditor, modify_time) " +
            "values(#{title}, #{browse}, #{praise}, #{collect}, #{type}, " +
            "#{videoUrl}, #{downloadUrl}, #{cloudUrl}, #{imgUrl}, #{videoThumb}, #{videoThumbBigger}, #{editor}, #{hot}, #{editorPick}, " +
            "#{isRelease}, #{isOpen}, #{isQuiz}, #{addDate}, #{timeLength}, #{status}, #{remark}, #{level}, #{pubTime}, " +
            "#{lastEditor}, #{modifyTime})")
    @Options(useGeneratedKeys = true, keyProperty="id", keyColumn = "id")
    void insertVideo(Video node);

    @Update("update ed_video set title=#{title}, browse=#{browse}, praise=#{praise}, collect=#{collect}, " +
            "type=#{type}, videoUrl=#{videoUrl}, downloadUrl=#{downloadUrl}, " +
            "cloud_url=#{cloudUrl}, imgUrl=#{imgUrl}, video_thumb=#{videoThumb}, " +
            "video_thumb_bigger=#{videoThumbBigger}, editor=#{editor}, hot=#{hot}, " +
            "editorPick=#{editorPick}, isRelease=#{isRelease}, isOpen=#{isOpen}, isQuiz=#{isQuiz}, " +
            "addDate=#{addDate}, time_length=#{timeLength}, status=#{status}, remark=#{remark}, " +
            "level=#{level}, pub_time=#{pubTime}, lastEditor=#{lastEditor}, modify_time=#{modifyTime} " +
            "where id=#{id}")
    int updateVideo(Video video);

    @Update("update ed_video set  browse=#{browse}   where id=#{id}")
    int updateVideoBrowse(Video video);

    @Update("update ed_video set  isRelease=#{isRelease}   where id=#{id}")
    int updateVideoIsRelease(Video video);

    @Update("update ed_video set  editorPick=#{editorPick}   where id=#{id}")
    int updateVideoEditorPick(Video video);

    @Update("update ed_video set  hot=#{hot}   where id=#{id}")
    int updateVideoHot(Video video);

    @Delete("delete from ed_video where id = #{id}")
    void deleteVideoById(@Param("id") Long id);

    @Select("select id, title, browse, praise, collect, type, " +
            "videoUrl, downloadUrl, cloud_url, imgUrl, video_thumb, video_thumb_bigger, editor, hot, editorPick, " +
            "isRelease, isOpen, isQuiz, addDate, time_length, status, remark, level, pub_time, lastEditor, modify_time " +
            "from ed_video where isRelease = 2 AND title like concat('%',#{title}, '%')")
    List<Video> findVideoByHintTitle(@Param("title") String title);

    @Select("select id, title, browse, praise, collect, type, " +
            "videoUrl, downloadUrl, cloud_url, imgUrl, video_thumb, video_thumb_bigger, editor, hot, editorPick, " +
            "isRelease, isOpen, isQuiz, addDate, time_length, status, remark, level, pub_time, lastEditor, modify_time " +
            "from ed_video where id = #{id}")
    Video findVideoById(@Param("id") Long id);

    @Select("<script>" +
            "select count(1) from ed_video " +
            "where 1=1 " +
            "  <if test=\"type != null and type != '' \"> " +
            "    AND  type = #{type,jdbcType=VARCHAR} " +
            "  </if> " +
            "  <if test=\"isRelease != null \"> " +
            "    AND  isRelease = #{isRelease} " +
            "  </if> " +
            "  <if test=\"title != null and title != '' \"> " +
            "    AND  title LIKE CONCAT('%', #{title, jdbcType=VARCHAR} , '%')" +
            "  </if> " +
            "  <if test=\"level != null and level != '' \"> " +
            "    AND  level = #{level,jdbcType=VARCHAR} " +
            "  </if> " +
            "  <if test=\"hot != null and hot != '' and hot == 1 \"> " +
            "    AND  hot = 1 " +
            "  </if> " +
            "  <if test=\"editorPick != null and editorPick != '' and editorPick == 1 \"> " +
            "    AND  editorPick = 1 " +
            "  </if> " +
            "</script>")
    Integer findVideoCount(VideoCondition videoCondition);

    @Select("<script>" +
            "select id, title, browse, praise, collect, type, " +
            "videoUrl, downloadUrl, cloud_url, imgUrl, video_thumb, video_thumb_bigger, editor, hot, editorPick, " +
            "isRelease, isOpen, isQuiz, addDate, time_length, status, remark, level, pub_time, lastEditor, modify_time " +
            "from ed_video " +
            "where 1=1 " +
            "  <if test=\"type != null and type != '' \"> " +
            "    AND  type = #{type,jdbcType=VARCHAR} " +
            "  </if> " +
            "  <if test=\"isRelease != null \"> " +
            "    AND  isRelease = #{isRelease} " +
            "  </if> " +
            "  <if test=\"title != null and title != '' \"> " +
            "    AND  title LIKE CONCAT('%', #{title, jdbcType=VARCHAR} , '%')" +
            "  </if> " +
            "  <if test=\"level != null and level != '' \"> " +
            "    AND  level = #{level,jdbcType=VARCHAR} " +
            "  </if> " +
            "  <if test=\"hot != null and hot != '' and hot == 1 \"> " +
            "    AND  hot = 1 " +
            "  </if> " +
            "  <if test=\"editorPick != null and editorPick != '' and editorPick == 1 \"> " +
            "    AND  editorPick = 1 " +
            "  </if> " +
            "order by addDate desc limit #{offset}, #{limit}" +
            "</script>")
    List<Video> findVideoListByPage(VideoCondition videoCondition);

    @Select({"<script>" +
            "select id, title, browse, praise, collect, type, " +
            "videoUrl, downloadUrl, cloud_url, imgUrl, video_thumb, video_thumb_bigger, editor, hot, editorPick, " +
            "isRelease, isOpen, isQuiz, addDate, time_length, status, remark, level, pub_time, lastEditor, modify_time " +
            "from ed_video where id in " +
            "<foreach item='id' collection='ids' open='(' separator=',' close=')'>#{id}</foreach> " +
            "order by addDate desc " +
            "</script>"})
    List<Video> findVideoListByIds(@Param("ids") List<Long> ids);

    @Insert("insert into ed_praise (uid, vid, create_time)values(#{collect.uid}, #{collect.vid}, #{collect.createTime})")
    void insertVideoCollect(@Param("collect") Collect collect);

    @Delete("delete from  ${table}   where uid = #{collect.uid} and vid = #{collect.vid} ")
    void deleteVideoCollect(@Param("collect")Collect collect,@Param("table")String table);
    @Select("<script>" +
            "select count(*) from  ${table}   where " +
            " uid = #{collect.uid} and vid =#{collect.vid}" +
            "</script>")
    Integer findVideoCollectCount(@Param("collect") Collect collect,@Param("table")String table);


}
