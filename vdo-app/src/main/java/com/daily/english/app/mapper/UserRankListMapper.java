package com.daily.english.app.mapper;

import com.daily.english.app.condition.UserRankListCondition;
import com.daily.english.app.domain.UserRankList;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface UserRankListMapper {

    @Insert("insert into ed_user_exp_rank_list(exp, userId, type,ranks,rankChange) " +
            "values(#{exp}, #{userId}, #{type},#{ranks},#{rankChange})")
    int insertExpRank(UserRankList uerl);

    @Insert("insert into ed_user_bamboo_rank_list(bamboo, userId, type,ranks,rankChange) " +
            "values(#{bamboo}, #{userId}, #{type},#{ranks},#{rankChange})")
    int insertBambooRank(UserRankList  ubrl);

    @Select("select ranks, bamboo, userId,rankChange  " +
            " from ed_user_bamboo_rank_list "+
            "   where type =  #{type} "+
            "  order by ranks  asc  limit #{offset}, #{limit} ")
    List<UserRankList> findBambooRankList(UserRankListCondition type);

    @Select("select ranks, exp, userId,rankChange  " +
            " from ed_user_exp_rank_list "+
            "   where type=#{type} "+
            "  order by ranks  asc  limit #{offset}, #{limit} ")
    List<UserRankList> findExpRankList(UserRankListCondition type);

    @Select("<script>" +
            "select count(1)  from ed_user_bamboo_rank_list " +
            "   where type =#{type} "+
            "</script>")
    Integer findBambooRankListCount(UserRankListCondition type);

    @Select("<script>" +
            "select count(1)  from ed_user_exp_rank_list " +
            "   where type= #{type} "+
            "</script>")
    Integer findExpRankListCount(UserRankListCondition type);
    @Delete("DELETE " +
            "        FROM ${table}    " +
            "        WHERE type = #{type}")
    Integer deleteByTable(@Param("type") String type,@Param("table") String table);
}
