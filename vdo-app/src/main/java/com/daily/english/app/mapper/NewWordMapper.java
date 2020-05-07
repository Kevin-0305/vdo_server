package com.daily.english.app.mapper;

import com.daily.english.app.condition.NewWordCondition;
import com.daily.english.app.domain.NewWord;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
@Component
public interface NewWordMapper {

    @Insert("insert into ed_new_word(userId, word, state, addDate) " +
            "values(#{userId}, #{word}, #{state}, #{addDate})")
    @Options(useGeneratedKeys = true, keyProperty="id", keyColumn = "id")
    void insertNewWord(NewWord newWord);

    @Update("update ed_new_word set userId=#{userId}, word=#{word}, state=#{state}, addDate=#{addDate} " +
            "where id=#{id}")
    int updateNewWord(NewWord newWord);

    @Delete("delete from ed_new_word where id = #{id}")
    void deleteNewWordById(@Param("id") Long id);

    @Select("select id, userId, word, state, addDate " +
            "from ed_new_word where id = #{id}")
    NewWord findNewWordById(@Param("id") Long id);

    @Select("<script>" +
            "select count(1) from ed_new_word " +
            "where 1=1 " +
            "  <if test=\"userId != null and userId != '' \"> " +
            "    AND userId = #{userId,jdbcType=VARCHAR} " +
            "  </if> " +
            "  <if test=\"state != null\"> " +
            "    AND state = #{state,jdbcType=INTEGER} " +
            "  </if> " +
            "</script>")
    Integer findNewWordCount(NewWordCondition newWordCondition);

    @Select("select min(addDate) from (select addDate from ed_new_word a " +
            "where userId = #{userId,jdbcType=VARCHAR} " +
            "and state = #{state,jdbcType=INTEGER} " +
            "group by addDate order by a.addDate desc limit 7) a ")
    Date getMinDate(NewWordCondition newWordCondition);

    @Select("<script>" +
            "select id, userId, word, state, addDate from ed_new_word a " +
            "where userId = #{userId,jdbcType=VARCHAR} " +
            "    AND state = #{state,jdbcType=INTEGER} " +
            "    AND a.addDate &lt; #{maxDate} " +
            "    AND a.addDate &gt;= #{minDate} " +
            "    order by a.addDate desc " +
            "</script>")
    List<NewWord> getByDate(NewWordCondition newWordCondition);

    @Select("<script> " +
            "select id, userId, word, state, addDate " +
            "from ed_new_word " +
            "where 1=1 " +
            "  <if test=\"userId != null and userId != '' \"> " +
            "    AND  userId = #{userId,jdbcType=VARCHAR} " +
            "  </if> " +
            "  <if test=\"state != null\"> " +
            "    AND  state = #{state,jdbcType=INTEGER} " +
            "  </if> " +
            "order by addDate desc limit #{offset}, #{limit} " +
            "</script>")
    List<NewWord> findNewWordListByPage(NewWordCondition newWordCondition);

    @Select({"<script>" +
            "select id, userId, word, state, addDate " +
            "from ed_new_word " +
            "where 1=1 " +
            "  <if test=\"userId != null and userId != '' \"> " +
            "    AND  userId = #{userId,jdbcType=VARCHAR} " +
            "  </if> " +
            " and word in " +
            "<foreach item='word' collection='words' open='(' separator=',' close=')'>#{word}</foreach> " +
            "order by addDate desc " +
            "</script>"})
    List<NewWord> findNewWordList(NewWordCondition newWordCondition);


    @Select({"<script>" +
            "select id, userId, word, state, addDate " +
            "from ed_new_word " +
            "where 1=1 " +
            "  <if test=\"userId != null and userId != '' \"> " +
            "    AND  userId = #{userId,jdbcType=VARCHAR} " +
            "  </if> " +
            " and word = #{word} " +
            "</script>"})
    NewWord findNewWord(NewWordCondition newWordCondition);

}
