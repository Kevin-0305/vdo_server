package com.daily.english.app.mapper;

import com.daily.english.app.domain.WordPkRecord;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * class_name: 单词PK记录
 * package: com.vdoenglish.core.dao
 * describe: TODO
 * creat_user: liuzd@chinadailyhk.com
 * creat_date: 2019-08-29
 * creat_time: 15:39
 **/
@Mapper
@Component
public interface WordPkRecordMapper {

    //根据业务保存
    @Insert("<script>" +
            "INSERT INTO ed_word_pk_record " +
            "        <trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\"> " +
            "            <if test=\"userId != null\"> " +
            "                user_id, " +
            "            </if> " +
            "            <if test=\"isVictory != null\"> " +
            "                is_victory, " +
            "            </if> " +
            "            <if test=\"bambooReward != null\"> " +
            "                bamboo_reward, " +
            "            </if> " +
            "            <if test=\"createTime != null\"> " +
            "                create_time, " +
            "            </if> " +
            "            <if test=\"isReceive != null\"> " +
            "                is_receive, " +
            "            </if> " +
            "        </trim> " +
            "        <trim prefix=\"VALUES (\" suffix=\")\" suffixOverrides=\",\"> " +
            "            <if test=\"userId != null\"> " +
            "                #{userId,jdbcType=INTEGER}, " +
            "            </if> " +
            "            <if test=\"isVictory != null\"> " +
            "                #{isVictory,jdbcType=INTEGER}, " +
            "            </if> " +
            "            <if test=\"bambooReward != null\"> " +
            "                #{bambooReward,jdbcType=INTEGER}, " +
            "            </if> " +
            "            <if test=\"createTime != null\"> " +
            "                #{createTime,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"isReceive != null\"> " +
            "                #{isReceive,jdbcType=INTEGER}, " +
            "            </if> " +
            "        </trim>" +
            "</script>")
    public int insertSelective(WordPkRecord wordPkRecord);

    //根据ID、业务更新
    @Update("<script>" +
            "UPDATE ed_word_pk_record " +
            "        <set> " +
            "            <if test=\"isReceive != null\"> " +
            "                is_receive = #{isReceive,jdbcType=INTEGER}, " +
            "            </if> " +
            "        </set> " +
            "        WHERE " +
            "        id = #{id,jdbcType=INTEGER}" +
            "</script>")
    public int updateSelective(WordPkRecord wordPkRecord);


    //根据根据条件查询
    @Select("<script>" +
            "SELECT " +
            "  * " +
            "FROM " +
            "ed_word_pk_record " +
            "WHERE " +
            "1=1 " +
            "ORDER BY create_time DESC " +
            "</script>")
    public List<WordPkRecord> selectList(WordPkRecord wordPkRecord);

    //根据ID查询
    @Select("SELECT * " +
            "        FROM ed_word_pk_record " +
            "        WHERE id = #{id,jdbcType=INTEGER}")
    public WordPkRecord selectById(@Param("id") String id);

    /**
     *
     * @param userId 用户id
     * @param isVictory 1.胜 0.负数
     * @param isFlag 1.查询当天记录 查询总战绩
     * @return
     */
    @Select("<script>" +
            "SELECT count(id)  " +
            "FROM ed_word_pk_record  " +
            "WHERE  " +
            "      1=1  " +
            "      <if test=\"userId != null\"> " +
            "       AND user_id = #{userId,jdbcType=INTEGER} " +
            "      </if> " +
            "      <if test=\"isVictory != null\"> " +
            "       AND is_victory = #{isVictory,jdbcType=INTEGER}  " +
            "      </if> " +
            "      <if test=\"isFlag != null and isFlag == 1 \"> " +
            "       AND to_days(create_time) = to_days(now()) " +
            "      </if> " +
            "</script>")
    public Integer selectUserOutcomeCount(@Param("userId") Integer userId,@Param("isVictory") Integer isVictory,@Param("isFlag") Integer isFlag);


}