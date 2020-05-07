package com.daily.english.app.mapper;

import com.daily.english.app.condition.DownloadVideoCondition;
import com.daily.english.app.domain.DownloadVideo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;
/**
 * @author zhc
 **/
@Mapper
@Component
public interface DownloadVideoMapper {

    @Insert("insert into ed_download(video_id, user_id, createTime) " +
            "values(#{videoId}, #{userId}, #{createTime})")
    @Options(useGeneratedKeys = true, keyProperty="id", keyColumn = "id")
    int insert(DownloadVideo downloadVideo);


    @Select("<script>" +
            "select id,video_id videoId, user_id userId,createTime " +
            "from ed_download " +
            "where 1=1 " +
            "  <if test=\"videoId != null and videoId != '' \"> " +
            "    AND  video_id = #{videoId} " +
            "  </if> " +
            "  <if test=\"userId != null and userId != '' \"> " +
            "    AND  user_id  = #{userId} " +
            "  </if> " +
            "order by createTime desc limit #{offset}, #{limit}" +
            "</script>")
    List<DownloadVideo> findDownloadVideoListByPage(DownloadVideoCondition downloadVideo);

    @Select({"<script>" +
            "select count(*) " +
            "from ed_download " +
            "where 1=1 " +
            "  <if test=\"videoId != null and videoId != '' \"> " +
            "    AND  video_id = #{videoId} " +
            "  </if> " +
            "  <if test=\"userId != null and userId != '' \"> " +
            "    AND  user_id  = #{userId} " +
            "  </if> " +
            "</script>"})
    Integer findDownloadVideoListByPageCount(DownloadVideoCondition downloadVideo);

    @Select({"<script>" +
            "delete from ed_download  " +
            "where user_id = #{userId} " +
            "and video_id in " +
            "<foreach item='id' collection='ids' open='(' separator=',' close=')'>#{id}</foreach> " +
            "</script>"})
    Integer deleteBatchDownloadVideo(@Param("userId") Integer userId, @Param("ids") List<String> ids);

}
