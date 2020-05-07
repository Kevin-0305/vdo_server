package com.daily.english.app.mapper;

import com.daily.english.app.domain.YjScoreRecord;
import com.daily.english.app.dto.YjScoreRecordDto;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * class_name: YjScoreRecordMapper
 * package: com.daily.english.app.mapper
 * describe: 用户评分记录持久层
 * creat_user: liuzd@chinadailyhk.com
 * creat_date: 2019-08-20
 * creat_time: 11:26
 **/
@Mapper
@Component
public interface YjScoreRecordMapper {

    @Insert("<script>" +
            "INSERT INTO yj_score_record " +
            "<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\"> " +
            "<if test=\"userId != null\"> " +
            "user_id, " +
            "  </if> " +
            "<if test=\"bmId != null\"> " +
            "bm_id, " +
            "  </if> " +
            "<if test=\"score != null\"> " +
            "score, " +
            "  </if> " +
            "<if test=\"createTime != null\"> " +
            "create_time, " +
            "  </if> " +
            "</trim> " +
            "<trim prefix=\"VALUES (\" suffix=\")\" suffixOverrides=\",\"> " +
            "<if test=\"userId != null\"> " +
            "  #{userId,jdbcType=VARCHAR}, " +
            "</if> " +
            "<if test=\"bmId != null\"> " +
            "  #{bmId,jdbcType=VARCHAR}, " +
            "</if> " +
            "<if test=\"score != null\"> " +
            "  #{score,jdbcType=VARCHAR}, " +
            "</if> " +
            "<if test=\"createTime != null\"> " +
            "  #{createTime,jdbcType=VARCHAR}, " +
            "</if> " +
            "</trim>" +
            "</script>")
    void insert(YjScoreRecord scoreRecord);

    @Update("<script>" +
            "UPDATE yj_score_record " +
            "<set> " +
            "<if test=\"userId != null\"> " +
            "  user_id = #{userId,jdbcType=VARCHAR}, " +
            "</if> " +
            "<if test=\"bmId != null\"> " +
            "  bm_id = #{bmId,jdbcType=VARCHAR}, " +
            "</if> " +
            "<if test=\"score != null\"> " +
            "  score = #{score,jdbcType=VARCHAR}, " +
            "</if> " +
            "<if test=\"createTime != null\"> " +
            "  create_time = #{createTime,jdbcType=VARCHAR}, " +
            "</if> " +
            "</set> " +
            "WHERE " +
            "  id = #{id,jdbcType=INTEGER}" +
            "</script>")
    void update(YjScoreRecord scoreRecord);


    @Select("<script>" +
            "SELECT ysr.id, ysr.user_id, ysr.bm_id,ecu.name AS 'userName', ysr.score, ysr.create_time, yubi.group_type  " +
            "            FROM yj_score_record ysr  " +
            "            LEFT JOIN yj_user_bm_info yubi ON yubi.id = ysr.bm_id  " +
            "            LEFT JOIN ed_cms_user ecu ON ecu.id = ysr.user_id " +
            "WHERE 1=1 " +
            "<if test=\"userId != null\">" +
            "   AND ysr.user_id = #{userId,jdbcType=VARCHAR} " +
            "</if>" +
            "<if test=\"bmId != null\">" +
            "   AND ysr.bm_id = #{bmId,jdbcType=INTEGER} " +
            "</if>" +
            "<if test=\"groupType != null\">" +
            "   AND yubi.group_type = #{groupType,jdbcType=INTEGER} " +
            "</if>" +
            "</script>")
    List<YjScoreRecord> selectList(YjScoreRecordDto scoreRecordDto);


    @Select("SELECT ysr.id, ysr.user_id, ysr.bm_id, ysr.score, ysr.create_time, " +
            " yubi.group_type, yubi.register_code, yubi.video_url, yubi.content " +
            "FROM yj_score_record ysr " +
            "         LEFT JOIN yj_user_bm_info yubi ON yubi.id = ysr.bm_id " +
            "WHERE 1=1 " +
            "AND ysr.id = #{id,jdbcType=INTEGER} ")
    YjScoreRecord selectById(@Param("id") String id);

}
