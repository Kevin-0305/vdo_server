package com.daily.english.app.mapper;

import com.daily.english.app.domain.UserExamInfo;
import org.apache.ibatis.annotations.*;
import org.apache.logging.log4j.core.tools.picocli.CommandLine;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface UserExamInfoMapper {

    @Insert("insert into ed_exam_user_info(useTime,id, sbmit_json, examInfo_id, user_id, answer_id, retry_time, create_time, update_time, score,type,isReset  ) " +
            "values(#{useTime},#{id}, #{sbmitJson}, #{examInfoId}, #{userId}, #{answerId}, #{retryTime}, #{createTime}, #{updateTime}, #{score},#{type},#{isReset})")
    void insertUserExamInfo(UserExamInfo userExamInfo);

    @Update("update ed_exam_user_info set useTime = #{useTime},sbmit_json=#{sbmitJson}, examInfo_id=#{examInfoId}, user_id=#{userId}, " +
            "answer_id=#{answerId}, retry_time=#{retryTime}, update_time=#{updateTime}, score=#{score}, type=#{type}, isReset=#{isReset} ,flag = #{flag}   " +
            "where id=#{id}")
    void modifyUserExamInfo(UserExamInfo userExamInfo);

    @Delete("delete from ed_exam_user_info where user_id = #{userId} and  examInfo_id =#{examInfoId}")
    void delUserExamInfo(@Param("userId") String userId,@Param("examInfoId") String examInfoId);
    @Select("select useTime,id, sbmit_json, examInfo_id, user_id, answer_id, retry_time, create_time, update_time, score, type,isReset ,flag        " +
            "from ed_exam_user_info where user_id = #{userId} and examInfo_id = #{examId}")
    UserExamInfo findUserExamInfoByExamId(@Param("userId") String userId, @Param("examId") String examId);

    @Select("select useTime,id, sbmit_json, examInfo_id, user_id, answer_id, retry_time, create_time, update_time, score,type,isReset ,flag    " +
            "from ed_exam_user_info where examInfo_id = #{examId}  order by update_time desc")
    List<UserExamInfo> findUserExamInfoListByExamId(@Param("examId") String examId);

    @Select("select useTime,id, sbmit_json, examInfo_id, user_id, answer_id, retry_time, create_time, update_time, score,type,isReset,flag    " +
            "from ed_exam_user_info where user_id = #{userId} ")
    List<UserExamInfo> findUserExamInfoListByUserId(@Param("userId") String userId);

    @Select({"<script>" +
            "select useTime,id, sbmit_json, examInfo_id, user_id, answer_id, retry_time, create_time, update_time, score,type,isReset ,flag     " +
            "from ed_exam_user_info where user_id = #{userId} and examInfo_id in " +
            "<foreach item='examId' collection='examIds' open='(' separator=',' close=')'>#{examId}</foreach>" +
            "</script>"})
    List<UserExamInfo> findUserExamInfoListByExamIds(@Param("userId") String userId, @Param("examIds") List<String> ids);
}
