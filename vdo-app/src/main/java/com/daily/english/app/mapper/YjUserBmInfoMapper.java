package com.daily.english.app.mapper;

import com.daily.english.app.domain.YjUserBmInfo;
import com.daily.english.app.dto.YjScoreRecordDto;
import com.daily.english.app.dto.YjUserBmInfoDto;
import com.daily.english.app.vo.YjUserBmInfoJuryVo;
import com.daily.english.app.vo.YjUserBmInfoVo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * class_name: YjUserBmInfoMapper
 * package: com.daily.english.app.mapper
 * describe: TODO
 * creat_user: liuzd@chinadailyhk.com
 * creat_date: 2019-08-14
 * creat_time: 15:59
 **/
@Mapper
@Component
public interface YjUserBmInfoMapper {

    @Insert("<script>" +
            "INSERT INTO yj_user_bm_info " +
            "<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\"> " +
            "<if test=\"teacherCall != null\"> " +
            "teacher_call, " +
            "</if> " +
            "<if test=\"teacherName != null\"> " +
            "teacher_name, " +
            "</if> " +
            "<if test=\"teacherEmail != null\"> " +
            "teacher_email, " +
            "</if> " +
            "<if test=\"teacherTel != null\"> " +
            "teacher_tel, " +
            "</if> " +
            "<if test=\"schoolArea != null\"> " +
            "school_area, " +
            "</if> " +
            "<if test=\"schoolId != null\"> " +
            "school_id, " +
            "</if> " +
            "<if test=\"nameCh != null\"> " +
            "name_ch, " +
            "</if> " +
            "<if test=\"nameEn != null\"> " +
            "name_en, " +
            "</if> " +
            "<if test=\"sex != null\"> " +
            "sex, " +
            "</if> " +
            "<if test=\"grade != null\"> " +
            "grade, " +
            "</if> " +
            "<if test=\"groupType != null\"> " +
            "group_type, " +
            "</if> " +
            "<if test=\"createTime != null\"> " +
            "create_time, " +
            "</if> " +
            "<if test=\"email != null\"> " +
            "email, " +
            "</if> " +
            "<if test=\"tel != null\"> " +
            "tel, " +
            "</if> " +
            "<if test=\"content != null\"> " +
            "content, " +
            "</if> " +
            "<if test=\"videoUrl != null\"> " +
            "video_url, " +
            "</if> " +
            "<if test=\"type != null\"> " +
            "type, " +
            "</if> " +
            "<if test=\"uploadStatus != null\"> " +
            "upload_status, " +
            "</if> " +
            "<if test=\"scoreStatus != null\"> " +
            "score_status, " +
            "</if> " +
            "<if test=\"score != null\"> " +
            "score, " +
            "</if> " +
            "<if test=\"scoreCount != null\"> " +
            "score_count, " +
            "</if> " +
            "<if test=\"eventId != null\"> " +
            "event_id, " +
            "</if> " +
            "<if test=\"userId != null\"> " +
            "user_id, " +
            "</if> " +
            "<if test=\"lockStatus != null\"> " +
            "lock_status, " +
            "</if> " +
            "<if test= 'schoolOtherName != null '> " +
            "school_other_name, " +
            "</if> " +
            "<if test= 'registerCode != null '> " +
            "register_code, " +
            "</if> " +
            "<if test= 'teacherUserId != null '> " +
            "teacher_user_id, " +
            "</if> " +
            "</trim> " +
            "<trim prefix=\"VALUES (\" suffix=\")\" suffixOverrides=\",\"> " +
            "<if test=\"teacherCall != null\"> " +
            "#{teacherCall,jdbcType=INTEGER}, " +
            "</if> " +
            "<if test=\"teacherName != null\"> " +
            "#{teacherName,jdbcType=VARCHAR}, " +
            "</if> " +
            "<if test=\"teacherEmail != null\"> " +
            "#{teacherEmail,jdbcType=VARCHAR}, " +
            "</if> " +
            "<if test=\"teacherTel != null\"> " +
            "#{teacherTel,jdbcType=VARCHAR}, " +
            "</if> " +
            "<if test=\"schoolArea != null\"> " +
            "#{schoolArea,jdbcType=VARCHAR}, " +
            "</if> " +
            "<if test=\"schoolId != null\"> " +
            "#{schoolId,jdbcType=INTEGER}, " +
            "</if> " +
            "<if test=\"nameCh != null\"> " +
            "#{nameCh,jdbcType=VARCHAR}, " +
            "</if> " +
            "<if test=\"nameEn != null\"> " +
            "#{nameEn,jdbcType=VARCHAR}, " +
            "</if> " +
            "<if test=\"sex != null\"> " +
            "#{sex,jdbcType=INTEGER}, " +
            "</if> " +
            "<if test=\"grade != null\"> " +
            "#{grade,jdbcType=INTEGER}, " +
            "</if> " +
            "<if test=\"groupType != null\"> " +
            "#{groupType,jdbcType=INTEGER}, " +
            "</if> " +
            "<if test=\"createTime != null\"> " +
            "#{createTime,jdbcType=VARCHAR}, " +
            "</if> " +
            "<if test=\"email != null\"> " +
            "#{email,jdbcType=VARCHAR}, " +
            "</if> " +
            "<if test=\"tel != null\"> " +
            "#{tel,jdbcType=VARCHAR}, " +
            "</if> " +
            "<if test=\"content != null\"> " +
            "#{content,jdbcType=VARCHAR}, " +
            "</if> " +
            "<if test=\"videoUrl != null\"> " +
            "#{videoUrl,jdbcType=VARCHAR}, " +
            "</if> " +
            "<if test=\"type != null\"> " +
            "#{type,jdbcType=INTEGER}, " +
            "</if> " +
            "<if test=\"uploadStatus != null\"> " +
            "#{uploadStatus,jdbcType=INTEGER}, " +
            "</if> " +
            "<if test=\"scoreStatus != null\"> " +
            "#{scoreStatus,jdbcType=INTEGER}, " +
            "</if> " +
            "<if test=\"score != null\"> " +
            "#{score,jdbcType=VARCHAR}, " +
            "</if> " +
            "<if test=\"scoreCount != null\"> " +
            "#{scoreCount,jdbcType=INTEGER}, " +
            "</if> " +
            "<if test=\"eventId != null\"> " +
            "#{eventId,jdbcType=VARCHAR}, " +
            "</if> " +
            "<if test=\"userId != null\"> " +
            "#{userId,jdbcType=VARCHAR}, " +
            "</if> " +
            "<if test=\"lockStatus != null\"> " +
            "#{lockStatus,jdbcType=INTEGER}, " +
            "</if> " +
            "<if test= 'schoolOtherName != null '> " +
            "  #{schoolOtherName,jdbcType=VARCHAR}, " +
            "</if> " +
            "<if test= 'registerCode != null '> " +
            " #{registerCode,jdbcType=VARCHAR}, " +
            "</if> " +
            "<if test= 'teacherUserId != null '> " +
            " #{teacherUserId,jdbcType=VARCHAR}, " +
            "</if> " +
            "</trim>" +
            "</script>")
    void insertUserBmInfo(YjUserBmInfo userBmInfo);

    @Update("<script>" +
            "UPDATE yj_user_bm_info " +
            "<set> " +
            "<if test=\"teacherCall != null\"> " +
            " teacher_call = #{teacherCall,jdbcType=INTEGER}, " +
            "</if> " +
            "<if test=\"teacherName != null\"> " +
            " teacher_name = #{teacherName,jdbcType=VARCHAR}, " +
            "</if> " +
            "<if test=\"teacherEmail != null\"> " +
            " teacher_email = #{teacherEmail,jdbcType=VARCHAR}, " +
            "</if> " +
            "<if test=\"teacherTel != null\"> " +
            " teacher_tel = #{teacherTel,jdbcType=VARCHAR}, " +
            "</if> " +
            "<if test=\"schoolArea != null\"> " +
            " school_area = #{schoolArea,jdbcType=VARCHAR}, " +
            "</if> " +
            "<if test=\"schoolId != null\"> " +
            " school_id = #{schoolId,jdbcType=INTEGER}, " +
            "</if> " +
            "<if test=\"nameCh != null\"> " +
            " name_ch = #{nameCh,jdbcType=VARCHAR}, " +
            "</if> " +
            "<if test=\"nameEn != null\"> " +
            " name_en = #{nameEn,jdbcType=VARCHAR}, " +
            "</if> " +
            "<if test=\"sex != null\"> " +
            " sex = #{sex,jdbcType=INTEGER}, " +
            "</if> " +
            "<if test=\"grade != null\"> " +
            " grade = #{grade,jdbcType=INTEGER}, " +
            "</if> " +
            "<if test=\"groupType != null\"> " +
            " group_type = #{groupType,jdbcType=INTEGER}, " +
            "</if> " +
            "<if test=\"createTime != null\"> " +
            " create_time = #{createTime,jdbcType=VARCHAR}, " +
            "</if> " +
            "<if test=\"email != null\"> " +
            " email = #{email,jdbcType=VARCHAR}, " +
            "</if> " +
            "<if test=\"tel != null\"> " +
            " tel = #{tel,jdbcType=VARCHAR}, " +
            "</if> " +
            "<if test=\"content != null\"> " +
            " content = #{content,jdbcType=VARCHAR}, " +
            "</if> " +
            "<if test=\"videoUrl != null\"> " +
            " video_url = #{videoUrl,jdbcType=VARCHAR}, " +
            "</if> " +
            "<if test=\"type != null\"> " +
            " type = #{type,jdbcType=INTEGER}, " +
            "</if> " +
            "<if test=\"uploadStatus != null\"> " +
            " upload_status = #{uploadStatus,jdbcType=INTEGER}, " +
            "</if> " +
            "<if test=\"scoreStatus != null\"> " +
            " score_status = #{scoreStatus,jdbcType=INTEGER}, " +
            "</if> " +
            "<if test=\"score != null\"> " +
            " score = #{score,jdbcType=VARCHAR}, " +
            "</if> " +
            "<if test=\"scoreCount != null\"> " +
            " score_count = #{scoreCount,jdbcType=INTEGER}, " +
            "</if> " +
            "<if test=\"eventId != null\"> " +
            " event_id = #{eventId,jdbcType=VARCHAR}, " +
            "</if> " +
            "<if test=\"userId != null\"> " +
            " user_id = #{userId,jdbcType=VARCHAR}, " +
            "</if> " +
            "<if test=\"lockStatus != null\"> " +
            " lock_status =#{lockStatus,jdbcType=INTEGER}, " +
            "</if> " +
            "<if test= 'schoolOtherName != null '> " +
            " school_other_name = #{schoolOtherName,jdbcType=VARCHAR}, " +
            "</if> " +
            "<if test= 'registerCode != null '> " +
            " register_code = #{registerCode,jdbcType=VARCHAR}, " +
            "</if> " +
            "<if test= 'teacherUserId != null '> " +
            " teacher_user_id = #{teacherUserId,jdbcType=VARCHAR}, " +
            "</if> " +
            "</set> " +
            "WHERE " +
            " id = #{id,jdbcType=INTEGER}" +
            "</script>")
    int updateUserBmInfo(YjUserBmInfo userBmInfo);

    @Select("SELECT " +
            "yubi.id, yubi.teacher_call, yubi.teacher_name, yubi.teacher_email, yubi.teacher_tel, yubi.school_area, " +
            "yubi.school_id, yubi.name_ch, yubi.name_en, yubi.sex, yubi.grade, " +
            "yubi.group_type, yubi.create_time, yubi.email, yubi.tel, yubi.content, yubi.video_url, yubi.type, " +
            "yubi.upload_status, yubi.score_status, yubi.score, yubi.score_count, yubi.school_other_name, " +
            "yubi.event_id, yubi.user_id, ys.name_ch AS 'school_name', ys.distinct AS 'school_distinct', " +
            "ys.name_en AS 'schoolNameEn', ys.distinct_en AS 'school_distinct_en', yubi.register_code " +
            "FROM yj_user_bm_info yubi " +
            "LEFT JOIN yj_school ys ON yubi.school_id = ys.id " +
            "WHERE " +
            " 1=1" +
            " AND yubi.id  =#{id}")
    YjUserBmInfo selectUserBmInfoById(@Param("id") String id);

    @Select("SELECT " +
            "yubi.id, yubi.teacher_call, yubi.teacher_name, yubi.teacher_email, yubi.teacher_tel, yubi.school_area, " +
            "yubi.school_id, yubi.name_ch, yubi.name_en, yubi.sex, yubi.grade, " +
            "yubi.group_type, yubi.create_time, yubi.email, yubi.tel, yubi.content, yubi.video_url, yubi.type, " +
            "yubi.upload_status, yubi.score_status, yubi.score, yubi.score_count, " +
            "yubi.event_id, yubi.user_id, ys.name_ch AS 'school_name', ys.distinct AS 'school_distinct' " +
            "FROM yj_user_bm_info yubi " +
            "LEFT JOIN yj_school ys ON yubi.school_id = ys.id " +
            "WHERE " +
            " 1=1" +
            " AND yubi.email  =#{email} " +
            " AND yubi.register_code  =#{registerCode} ")
    YjUserBmInfo selectUserBmInfoByEmailAndRegisterCode(@Param("email") String email, @Param("registerCode") String registerCode);

    @Select("<script>" +
            "SELECT " +
            "yubi.*, " +
            "ys.name_ch AS 'school_name', ys.name_en AS 'school_name_en', ys.distinct AS 'school_distinct' " +
            "FROM yj_user_bm_info yubi LEFT JOIN yj_school ys ON yubi.school_id = ys.id " +
            "WHERE 1 = 1 " +
            "<if test=\"eventId != null\">" +
            "   AND yubi.event_id = #{eventId,jdbcType=INTEGER} " +
            "</if>" +
            "<if test=\"userId != null\">" +
            "   AND yubi.user_id = #{userId,jdbcType=INTEGER} " +
            "</if>" +
            "<if test=\"id != null\">" +
            "   AND yubi.id = #{id,jdbcType=INTEGER} " +
            "</if>" +
            "<if test=\"lockStatus != null\">" +
            "   AND yubi.lock_status = #{lockStatus,jdbcType=INTEGER} " +
            "</if>" +
            "</script>")
    List<YjUserBmInfo> selectUserBmInfoList(YjUserBmInfo userBmInfo);

    @Select("<script>" +
            "SELECT " +
            "yubi.*, " +
            "ys.name_ch AS 'school_name', ys.name_en AS 'school_name_en', ys.distinct AS 'school_distinct' " +
            "FROM yj_user_bm_info yubi LEFT JOIN yj_school ys ON yubi.school_id = ys.id " +
            "WHERE 1 = 1 " +
            "<if test=\"eventId != null\">" +
            "   AND yubi.event_id = #{eventId,jdbcType=INTEGER} " +
            "</if>" +
            "<if test=\"userId != null\">" +
            "   AND (yubi.user_id = #{userId,jdbcType=INTEGER} OR yubi.teacher_user_id = #{userId,jdbcType=INTEGER}) " +
            "</if>" +
            "</script>")
    List<YjUserBmInfo> selectUserBmInfoListNew(YjUserBmInfo userBmInfo);

    @Select("<script>" +
            "SELECT yubi.id, yubi.name_ch, yubi.name_en, yubi.teacher_name, yubi.school_area, yubi.school_id, " +
            "ys.name_ch AS 'school_name', ys.distinct AS 'school_distinct',  " +
            "yubi.type,  yubi.group_type, yubi.upload_status, yubi.email, yubi.score_status, yubi.school_other_name, " +
            "yubi.score, yubi.score_count, yubi.event_id ,yubi.register_code " +
            "FROM yj_user_bm_info yubi LEFT JOIN yj_school ys ON yubi.school_id = ys.id " +
            "WHERE 1 = 1 " +
            //"<if test=\"eventId != null\">" +
            //"   AND yubi.event_id = #{eventId,jdbcType=INTEGER} " +
            //"</if>" +
            "<if test=\"type != null\">" +
            "   AND yubi.type = #{type,jdbcType=INTEGER} " +
            "</if>" +
            "<if test=\"groupTypes != null\">" +
            "   AND yubi.group_type IN" +
            "   <foreach item='groupType' collection='groupTypes' open='(' separator=',' close=')'>#{groupType}</foreach>" +
            "</if>" +
            "<if test=\"uploadStatus != null\">" +
            "   AND yubi.upload_status = #{uploadStatus,jdbcType=INTEGER} " +
            "</if>" +
            "<if test=\"scoreStatus != null\">" +
            "   AND yubi.score_status = #{scoreStatus,jdbcType=INTEGER} " +
            "</if>" +
            "<if test=\"score != null\">" +
            "   AND yubi.score &gt;= #{score} " +
            "</if>" +
            " <if test='keywords != null and keywords != \"\"'> " +
            "            AND ( " +
            "            yubi.name_ch LIKE CONCAT('%', #{keywords, jdbcType=VARCHAR} , '%') " +
            "            OR " +
            "            yubi.name_en LIKE CONCAT('%', #{keywords, jdbcType=VARCHAR} , '%') " +
            "            OR " +
            "            yubi.school_area LIKE CONCAT('%', #{keywords, jdbcType=VARCHAR} , '%') " +
            "            OR " +
            "            yubi.register_code LIKE CONCAT('%', #{keywords, jdbcType=VARCHAR} , '%') " +
            "            OR " +
            "            ys.name_ch LIKE CONCAT('%', #{keywords, jdbcType=VARCHAR} , '%') " +
            "            OR " +
            "            ys.name_en LIKE CONCAT('%', #{keywords, jdbcType=VARCHAR} , '%') " +
            "            ) " +
            " </if>" +
            "</script>")
    List<YjUserBmInfoVo> selectUserBmInfoPage(YjUserBmInfoDto userBmInfoDto);


    @Select("<script>" +
            "SELECT yubi.id, yubi.name_ch, yubi.name_en, yubi.teacher_name, yubi.school_area, yubi.school_id,yubi.school_other_name, " +
            "ys.name_ch AS 'school_name', ys.distinct AS 'school_distinct', ys.name_en AS 'schoolNameEn', " +
            "yubi.type,  yubi.group_type, yubi.upload_status, yubi.email, yubi.score_status, yubi.school_other_name, " +
            "yubi.score, yubi.score_count, yubi.event_id ,yubi.register_code," +
            "yubi.teacher_call, yubi.teacher_tel, yubi.teacher_email, yubi.grade,yubi.tel,yubi.sex " +
            "FROM yj_user_bm_info yubi LEFT JOIN yj_school ys ON yubi.school_id = ys.id " +
            "WHERE 1 = 1 " +
            "<if test=\"type != null\">" +
            "   AND yubi.type = #{type,jdbcType=INTEGER} " +
            "</if>" +
            "<if test=\"groupTypes != null\">" +
            "   AND yubi.group_type IN" +
            "   <foreach item='groupType' collection='groupTypes' open='(' separator=',' close=')'>#{groupType}</foreach>" +
            "</if>" +
            "<if test=\"uploadStatus != null\">" +
            "   AND yubi.upload_status = #{uploadStatus,jdbcType=INTEGER} " +
            "</if>" +
            "<if test=\"scoreStatus != null\">" +
            "   AND yubi.score_status = #{scoreStatus,jdbcType=INTEGER} " +
            "</if>" +
            "<if test=\"score != null\">" +
            "   AND yubi.score &gt;= #{score} " +
            "</if>" +
            " <if test='keywords != null and keywords != \"\"'> " +
            "            AND ( " +
            "            yubi.name_ch LIKE CONCAT('%', #{keywords, jdbcType=VARCHAR} , '%') " +
            "            OR " +
            "            yubi.name_en LIKE CONCAT('%', #{keywords, jdbcType=VARCHAR} , '%') " +
            "            OR " +
            "            yubi.school_area LIKE CONCAT('%', #{keywords, jdbcType=VARCHAR} , '%') " +
            "            OR " +
            "            ys.name_ch LIKE CONCAT('%', #{keywords, jdbcType=VARCHAR} , '%') " +
            "            OR " +
            "            ys.name_en LIKE CONCAT('%', #{keywords, jdbcType=VARCHAR} , '%') " +
            "            ) " +
            " </if>" +
            "</script>")
    List<YjUserBmInfoVo> selectUserBmInfoAll(YjUserBmInfoDto userBmInfoDto);

    @Select("<script>" +
            "SELECT yubi.id, yubi.group_type, yubi.register_code, yubi.content, yubi.video_url, yubi.event_id " +
            "FROM yj_user_bm_info yubi " +
            "WHERE 1 = 1 " +
            " AND yubi.lock_status = 0 " +
            " AND yubi.score_status = 0 " +
            " AND yubi.upload_status = 1 " +
            "<if test=\"groupTypes != null\">" +
            "   AND yubi.group_type IN" +
            "   <foreach item='groupType' collection='groupTypes' open='(' separator=',' close=')'>#{groupType}</foreach>" +
            "</if>" +
            "<if test=\"groupType != null\">" +
            "  AND yubi.group_type = #{groupType,jdbcType=INTEGER} " +
            "</if>" +
            "<if test=\"userId != null\">" +
            "  AND yubi.id NOT IN (SELECT ysr.bm_id FROM yj_score_record ysr WHERE ysr.user_id = #{userId,jdbcType=INTEGER})" +
            "</if>" +
            " ORDER BY yubi.score_count DESC " +
            "</script>")
    List<YjUserBmInfoJuryVo> selectUserBmInfoJuryListPage(YjScoreRecordDto scoreRecordDto);

    @Select("<script>" +
            "SELECT yubi.id, yubi.group_type, yubi.register_code, yubi.content, yubi.video_url, yubi.lock_status " +
            "FROM yj_user_bm_info yubi " +
            "WHERE 1 = 1 " +
            "AND yubi.id  =#{id}" +
            "</script>")
    YjUserBmInfoJuryVo selectUserBmInfoJuryDetailById(@Param("id") String id);

}
