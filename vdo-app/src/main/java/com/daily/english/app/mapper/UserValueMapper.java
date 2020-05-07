package com.daily.english.app.mapper;

import com.daily.english.app.domain.UserValue;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface UserValueMapper {

    @Select("select id, getBambooToday , useBambooToday, getBambooAll, useBambooAll," +
            "getExpToday, exp, expRank, expRankLastweek, bamboo, bambooRank  , bambooRankLastweek   " +
            "from ed_user_value where id = #{id}")
    UserValue findUserValueByUserId(@Param("id") String id);

    @Insert("insert into ed_user_value(id,getBambooToday,bamboo,getBambooAll) " +
            "values(#{id},#{getBambooToday},#{bamboo},#{getBambooAll})")
    void insertUserValue(UserValue userMap);

    @Select("select id, getBambooToday , useBambooToday, getBambooAll, useBambooAll,sumWeekExp,sumWeekBamboo," +
            "lastExpRank,lastExpWeekRank,lastBambooRank,lastBambooWeekRank,getExpYesterday,getBambooYesterday, "+
            "getExpToday, exp, expRank, expRankLastweek, bamboo, bambooRank  , bambooRank,bambooRankLastweek  " +
            " from ed_user_value  ")
    List<UserValue> findUserValueList();

    @Update("<script> UPDATE ed_user_value  <set> " +
            "<if test=\"exp != null\"> " +
            "       exp = #{exp} ," +
            " </if> " +
            "<if test=\"bamboo != null\"> " +
            "       bamboo = #{bamboo} ," +
            " </if> " +
            "<if test=\"expRank != null\"> " +
            "       expRank = #{expRank} ," +
            " </if> " +
            "<if test=\"bambooRank != null\"> " +
            "       bambooRank = #{bambooRank} ," +
            " </if> " +
            "<if test=\"expRankLastweek != null\"> " +
            "       expRankLastweek = #{expRankLastweek} ," +
            " </if> " +
            "<if test=\"bambooRankLastweek != null\"> " +
            "       bambooRankLastweek = #{bambooRankLastweek} ," +
            " </if> " +
            "<if test=\"sumWeekExp != null\"> " +
            "       sumWeekExp = #{sumWeekExp} ," +
            " </if> " +
            "<if test=\"sumWeekBamboo != null\"> " +
            "       sumWeekBamboo = #{sumWeekBamboo} ," +
            " </if> " +
            "<if test=\"lastExpRank != null\"> " +
            "       lastExpRank = #{lastExpRank} ," +
            " </if> " +
            "<if test=\"lastExpWeekRank != null\"> " +
            "       lastExpWeekRank = #{lastExpWeekRank} ," +
            " </if> " +
            "<if test=\"lastBambooRank != null\"> " +
            "       lastBambooRank = #{lastBambooRank} ," +
            " </if> " +
            "<if test=\"lastBambooWeekRank != null\"> " +
            "       lastBambooWeekRank = #{lastBambooWeekRank} ," +
            " </if> " +
            "<if test=\"getExpYesterday != null\"> " +
            "       getExpYesterday = #{getExpYesterday} ," +
            " </if> " +
            "<if test=\"getBambooYesterday != null\"> " +
            "       getBambooYesterday = #{getBambooYesterday} ," +
            " </if> " +
            "<if test=\"getBambooToday != null\"> " +
            "       getBambooToday = #{getBambooToday} ," +
            " </if> " +
            "<if test=\"getExpToday != null\"> " +
            "       getExpToday = #{getExpToday} ," +
            " </if> " +
            "</set>  WHERE id=#{id}  "+
             "  </script>")
    void updateUserValue(UserValue userMap);

}
