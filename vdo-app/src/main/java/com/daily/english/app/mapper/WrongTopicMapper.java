package com.daily.english.app.mapper;

import com.daily.english.app.condition.WrongTopicCondition;
import com.daily.english.app.domain.Collect;
import com.daily.english.app.domain.WrongTopic;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface WrongTopicMapper {

    @Insert("insert into ed_wrong_topic(userId, examId, nodeId, node, answer, state, addDate,videoId,type ) " +
            "values(#{userId}, #{examId}, #{nodeId}, #{node}, #{answer}, #{state}, #{addDate},#{videoId},#{type})")
    @Options(useGeneratedKeys = true, keyProperty="id", keyColumn = "id")
    void insertWrongTopic(WrongTopic wrongTopic);

    @Update("update ed_wrong_topic set userId=#{userId}, examId=#{examId}, nodeId=#{nodeId}, answer=#{answer}, " +
            "state=#{state} " +
            "where id=#{id}")
    int updateWrongTopic(WrongTopic wrongTopic);

    @Delete("delete from ed_wrong_topic where id = #{id}")
    void deleteWrongTopicById(@Param("id") Long id);

    @Select("select id, userId, examId, nodeId, node, answer, state, addDate ,videoId,type " +
            "from ed_wrong_topic where id = #{id}")
    WrongTopic findWrongTopicById(@Param("id") Long id);

    @Select("<script>" +
            "select count(1) from ed_wrong_topic " +
            "where 1=1 " +
            "  <if test=\"userId != null and userId != '' \"> " +
            "    AND  userId = #{userId,jdbcType=VARCHAR} " +
            "  </if> " +
            "  <if test=\"type != null and type != '' \"> " +
            "    AND  type like '%${type}%' " +
            "  </if> " +
            "  <if test=\"state != null\"> " +
            "    AND  state = #{state,jdbcType=INTEGER} " +
            "  </if> " +
            "</script>")
    Integer findWrongTopicCount(WrongTopicCondition wrongTopicCondition);

    @Select("<script>" +
            "select id, userId, examId, nodeId, node, answer, state, addDate,videoId,type  " +
            "from ed_wrong_topic " +
            "where 1=1 " +
            "  <if test=\"userId != null and userId != '' \"> " +
            "    AND  userId = #{userId,jdbcType=VARCHAR} " +
            "  </if> " +
            "  <if test=\"type != null and type != '' \"> " +
            "    AND  type like '%${type}%' "+
            "  </if> " +
            "  <if test=\"state != null\"> " +
            "    AND  state = #{state,jdbcType=INTEGER} " +
            "  </if> " +
            "order by addDate desc limit #{offset}, #{limit}" +
            "</script>")
    List<WrongTopic> findWrongTopicListByPage(WrongTopicCondition wrongTopicCondition);

    @Select({"<script>" +
            "select id, userId, examId, nodeId, node, answer, state, addDate,videoId,type  " +
            "from ed_wrong_topic " +
            "where 1=1 " +
            "  <if test=\"userId != null and userId != '' \"> " +
            "    AND  userId = #{userId,jdbcType=VARCHAR} " +
            "  </if> " +
            "  <if test=\"type != null and type != '' \"> " +
            "    AND  type like '%${type}%' " +
            "  </if> " +
            " AND id in " +
            "<foreach item='id' collection='ids' open='(' separator=',' close=')'>#{id}</foreach> " +
            "order by addDate desc " +
            "</script>"})
    List<WrongTopic> findWrongTopicList(WrongTopicCondition wrongTopicCondition);

}
