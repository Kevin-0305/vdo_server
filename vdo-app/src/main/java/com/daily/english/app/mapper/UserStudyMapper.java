package com.daily.english.app.mapper;

import com.daily.english.app.domain.UserStudy;
import com.daily.english.app.domain.UserValue;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface UserStudyMapper {

    @Select("select id, studyTimeToday , studyTimeWeek, studyTimeAll, studyDayAll,weekHour, " +
            "studyDayContinue, studyTimeWeekMax, studyTimeWeekMaxDate, finishVdoWeek, newWordsWeek, finishChallengeWeek  , averageScoreWeek  " +
            "studyTimeMax, studyTimeMaxDate, finishVdoAll, newWordsAll, finishChallengeAll, questionAmoutToday  , questionAmoutAll  " +
            "from ed_user_study where id = #{id}")
    UserStudy findUserStudyByUserId(@Param("id") String id);
    @Select("select id, studyTimeToday , studyTimeWeek, studyTimeAll, studyDayAll,weekHour, " +
            "studyDayContinue, studyTimeWeekMax, studyTimeWeekMaxDate, finishVdoWeek, newWordsWeek, finishChallengeWeek  , averageScoreWeek  " +
            "studyTimeMax, studyTimeMaxDate, finishVdoAll, newWordsAll, finishChallengeAll, questionAmoutToday  , questionAmoutAll  " +
            "from ed_user_study")
    List<UserStudy> findUserStudyList();
//逻辑后面再加
    @Insert("insert into ed_user_study(id) " +
            "values(#{id})")
    void insertUserStudy(UserStudy userMap);


    @Update("<script>" +
            "update ed_user_study <set>" +
            "  <if test=\"studyTimeToday != null \"> " +
            "    studyTimeToday=#{studyTimeToday}," +
            "  </if> " +
            "  <if test=\"studyTimeWeek != null  \"> " +
            "    studyTimeWeek=#{studyTimeWeek}," +
            "  </if> " +
            "  <if test=\"studyTimeAll != null\"> " +
            "    studyTimeAll=#{studyTimeAll}," +
            "  </if> " +
            "  <if test=\"studyDayAll != null\"> " +
            "    studyDayAll=#{studyDayAll}," +
            "  </if> " +
            "  <if test=\"weekHour != null and weekHour != '' \"> " +
            "    weekHour=#{weekHour}," +
            "  </if> " +
            "  <if test=\"studyTimeWeekMax != null\"> " +
            "    studyTimeWeekMax=#{studyTimeWeekMax}," +
            "  </if> " +
            "  <if test=\"studyTimeWeekMaxDate != null and studyTimeWeekMaxDate != '' \"> " +
            "    studyTimeWeekMaxDate=#{studyTimeWeekMaxDate}," +
            "  </if> " +
            "  <if test=\"studyTimeMaxDate != null and studyTimeMaxDate != '' \"> " +
            "    studyTimeMaxDate=#{studyTimeMaxDate}," +
            "  </if> " +
            "  <if test=\"finishVdoWeek != null\"> " +
            "    finishVdoWeek=#{finishVdoWeek}," +
            "  </if> " +
            "  <if test=\"newWordsWeek != null \"> " +
            "    newWordsWeek=#{newWordsWeek}," +
            "  </if> " +
            "  <if test=\"finishChallengeWeek != null\"> " +
            "    finishChallengeWeek=#{finishChallengeWeek}," +
            "  </if> " +
            "  <if test=\"averageScoreWeek != null\"> " +
            "    averageScoreWeek=#{averageScoreWeek}," +
            "  </if> " +
            "  <if test=\"studyTimeMax != null\"> " +
            "    studyTimeMax=#{studyTimeMax}," +
            "  </if> " +
            "  <if test=\"finishVdoAll != null\"> " +
            "    finishVdoAll=#{finishVdoAll}," +
            "  </if> " +
            "  <if test=\"newWordsAll != null\"> " +
            "    newWordsAll=#{newWordsAll}," +
            "  </if> " +
            "  <if test=\"finishChallengeAll != null\"> " +
            "    finishChallengeAll=#{finishChallengeAll}," +
            "  </if> " +
            "  <if test=\"questionAmoutToday != null\"> " +
            "    questionAmoutToday=#{questionAmoutToday}," +
            "  </if> " +
            "  <if test=\"questionAmoutAll != null\"> " +
            "    questionAmoutAll=#{questionAmoutAll}," +
            "  </if> " +
            "  <if test=\"studyDayContinue != null\"> " +
            "    studyDayContinue = #{studyDayContinue}," +
            "  </if></set> " +
            "where id=#{id}"+
            "</script>")
    int updateUserStudy(UserStudy user);
}
