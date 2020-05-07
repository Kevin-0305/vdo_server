package com.daily.english.app.mapper;

import com.daily.english.app.domain.Map;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface MapMapper {

    @Insert("insert into ed_map(id, x, y, source, cname, type, userId, boxId, examId, examType, free, price, " +
            "score, needScore, inLevelJson, nextLevelJson, orders, status, level ) " +
            "values(#{id}, #{x}, #{y}, #{source}, #{cname}, #{type}, #{userId}, #{boxId}, #{examId}, #{examType}, " +
            "#{free}, #{price}, #{score}, #{needScore}, #{inLevelJson}, #{nextLevelJson}, #{orders}, #{status}, #{level})")
//    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(Map map);

    @Select("select id, x, y, source, cname, type, userId, boxId, examId, examType, free, price, " +
            " webx , weby, score, needScore, inLevelJson, nextLevelJson, orders, status, level " +
            "from ed_map where id = #{id} ")
    Map findMapById(Long id);

    @Select("select id, x, y, source, cname, type, userId, boxId, examId, examType, free, price, " +
            "  webx , weby,  score, needScore, inLevelJson, nextLevelJson, orders, status, level " +
            "from ed_map where examId = #{id} ")
    Map findMapByExamId(String id);
    @Select("<script>" +
            "SELECT id, x, y,  webx , weby, source, cname, type, userId, boxId, examId, examType, free, price, score, needScore, inLevelJson, " +
            "    nextLevelJson, orders, status, level " +
            "FROM ed_map " +
            "WHERE 1=1 " +
            "  <if test=\"level != null and level != '' \"> " +
            "    AND  level = #{level,jdbcType=VARCHAR} " +
            "  </if> " +
            "</script>")
    List<Map> findMapList(Map map);

    @Update("update ed_map set x=#{x}, y=#{y}, source=#{source}, cname=#{cname}, " +
            "type=#{type}, userId=#{userId}, boxId=#{boxId}, examId=#{examId}, examType=#{examType}, free=#{free}, " +
            "price=#{price}, score=#{score}, needScore=#{needScore}, inLevelJson=#{inLevelJson}, nextLevelJson=#{nextLevelJson}, " +
            "orders=#{orders}, status=#{status},level = #{level} " +
            "where id=#{id}")
    int update(Map map);


    @Update("<script>" +
            "UPDATE ed_map " +
            "<set> " +
            "  <if test=\"x != null\"> " +
            "      x = #{x,jdbcType=INTEGER}, " +
            "  </if> " +
            "  <if test=\"y != null\"> " +
            "      y = #{y,jdbcType=INTEGER}, " +
            "  </if> " +
            "  <if test=\"webx != null\"> " +
            "       webx = #{webx,jdbcType=INTEGER}, " +
            "  </if> " +
            "  <if test=\"weby != null\"> " +
            "      weby = #{weby,jdbcType=INTEGER}, " +
            "  </if> " +
            "  <if test=\"source != null\"> " +
            "      source = #{source,jdbcType=VARCHAR}, " +
            "  </if> " +
            "  <if test=\"cname != null\"> " +
            "      cname = #{cname,jdbcType=VARCHAR}, " +
            "  </if> " +
            "  <if test=\"type != null\"> " +
            "      type = #{type,jdbcType=VARCHAR}, " +
            "  </if> " +
            "  <if test=\"userId != null\"> " +
            "      userId = #{userId,jdbcType=VARCHAR}, " +
            "  </if> " +
            "  <if test=\"boxId != null\"> " +
            "      boxId = #{boxId,jdbcType=VARCHAR}, " +
            "  </if> " +
            "  <if test=\"examId != null\"> " +
            "      examId = #{examId,jdbcType=VARCHAR}, " +
            "  </if> " +
            "  <if test=\"examType != null\"> " +
            "      examType = #{examType,jdbcType=VARCHAR}, " +
            "  </if> " +
            "  <if test=\"free != null\"> " +
            "      free = #{free,jdbcType=INTEGER}, " +
            "  </if> " +
            "  <if test=\"price != null\"> " +
            "      price = #{price,jdbcType=DOUBLE}, " +
            "  </if> " +
            "  <if test=\"score != null\"> " +
            "      score = #{score,jdbcType=INTEGER}, " +
            "  </if> " +
            "  <if test=\"needScore != null\"> " +
            "      needScore = #{needScore,jdbcType=INTEGER}, " +
            "  </if> " +
            "  <if test=\"inLevelJson != null\"> " +
            "      inLevelJson = #{inLevelJson,jdbcType=VARCHAR}, " +
            "  </if> " +
            "  <if test=\"nextLevelJson != null\"> " +
            "      nextLevelJson = #{nextLevelJson,jdbcType=VARCHAR}, " +
            "  </if> " +
            "  <if test=\"orders != null\"> " +
            "      orders = #{orders,jdbcType=BIGINT}, " +
            "  </if> " +
            "  <if test=\"status != null\"> " +
            "      status = #{status,jdbcType=TINYINT}, " +
            "  </if> " +
            "  <if test=\"level != null\"> " +
            "      level = #{level,jdbcType=VARCHAR}, " +
            "  </if> " +
            "</set> " +
            "WHERE " +
            "   id = #{id,jdbcType=BIGINT}" +
            "</script>")
    int updateSelective(Map map);

    @Select("<script>" +
            "select count(*)+1  from ed_map where  " +
            "level =#{level} and type = 1" +
            " <![CDATA[  and id < (select id from ed_map where  examId = #{examId})   ]]>" +
            "</script>")
    int findMapExamIdCount(Map map);
}
