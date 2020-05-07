package com.daily.english.app.mapper;

import com.daily.english.app.domain.PartnerCode;
import com.daily.english.app.dto.PageSearchDto;
import com.daily.english.app.dto.StudyExamInfoDto;
import com.daily.english.app.vo.StudentMapVo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * class_name: ParnerClassMapper
 * package: com.daily.english.app.mapper
 * describe: TODO
 * creat_user: liuzd@chinadailyhk.com
 * creat_date: 2019-09-05
 * creat_time: 09:39
 **/
@Mapper
@Component
public interface PartnerCodeMapper {

    //根据ID查询
    @Select("SELECT * " +
            "        FROM ed_partner_code " +
            "        WHERE id = #{id,jdbcType=INTEGER}")
    public PartnerCode selectById(@Param("id") String id);

    @Select("<script>" +
            "SELECT epc.*,eu.useraccount AS 'user_name' " +
            "FROM ed_partner_code epc LEFT JOIN ed_user eu ON epc.user_id = eu.id " +
            "WHERE 1 = 1 AND epc.type = 1 AND status != -1 " +
            " <if test='status != null'> " +
            "   AND epc.status = #{status,jdbcType=INTEGER} " +
            " </if>" +
            " AND epc.school_id = #{SchoolId,jdbcType=INTEGER}" +
            "</script>")
    public List<PartnerCode> selectTeacherBySchoolId(@Param("SchoolId") String id, @Param("status") Integer status);

    @Select("<script>" +
            "SELECT epc.*,eu.useraccount AS 'user_name' " +
            "FROM ed_partner_code epc LEFT JOIN ed_user eu ON epc.user_id = eu.id " +
            "WHERE 1 = 1 AND epc.type = 0 AND status != -1 " +
            " <if test='status != null'> " +
            "   AND status = #{status,jdbcType=INTEGER} " +
            " </if>" +
            "AND class_id = #{ClassId,jdbcType=INTEGER}" +
            "</script>")
    public List<PartnerCode> selectStudentByClassId(@Param("ClassId") String id, @Param("status") Integer status);

    @Select("<script>" +
            "SELECT epc.user_id,epc.studentName,eum.s1mapScore, eum.s2mapScore, eum.s3mapScore, eum.s4mapScore, " +
            "    eum.s5mapScore, eum.s6mapScore " +
            "FROM ed_partner_code epc " +
            "    LEFT JOIN ed_user_map eum ON eum.id = epc.user_id " +
            "WHERE 1 = 1 AND epc.type = 0 AND status != -1 AND user_id IS NOT NULL AND status = 1 " +
            "AND epc.class_id = #{ClassId,jdbcType=INTEGER}" +
            "</script>")
    public List<StudentMapVo> selectStudentMapByClassId(@Param("ClassId") String id);


    //根据ID删除
    @Delete("DELETE " +
            "        FROM ed_partner_code " +
            "        WHERE id = #{id,jdbcType=INTEGER}")
    public int deleteById(@Param("id") String id);

    //根据业务保存
    @Insert("<script>" +
            "INSERT INTO ed_partner_code " +
            "        <trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\"> " +
            "            <if test=\"identifyCode != null and identifyCode != ''\"> " +
            "                identify_code, " +
            "            </if> " +
            "            <if test=\"status != null and status != ''\"> " +
            "                status, " +
            "            </if> " +
            "            <if test=\"userId != null and userId != ''\"> " +
            "                user_id, " +
            "            </if> " +
            "            <if test=\"schoolId != null and schoolId != ''\"> " +
            "                school_id, " +
            "            </if> " +
            "            <if test=\"classId != null and classId != ''\"> " +
            "                class_id, " +
            "            </if> " +
            "            <if test=\"type != null and type != ''\"> " +
            "                type, " +
            "            </if> " +
            "            <if test=\"activateTime != null and activateTime != ''\"> " +
            "                activate_time, " +
            "            </if> " +
            "            <if test=\"gender != null and gender != ''\"> " +
            "                gender, " +
            "            </if> " +
            "            <if test=\"studentName != null and studentName != ''\"> " +
            "                studentName, " +
            "            </if> " +
            "        </trim> " +
            "        <trim prefix=\"VALUES (\" suffix=\")\" suffixOverrides=\",\"> " +
            "            <if test=\"identifyCode != null and identifyCode != ''\"> " +
            "                #{identifyCode,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"status != null and status != ''\"> " +
            "                #{status,jdbcType=INTEGER}, " +
            "            </if> " +
            "            <if test=\"userId != null and userId != ''\"> " +
            "                #{userId,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"schoolId != null and schoolId != ''\"> " +
            "                #{schoolId,jdbcType=INTEGER}, " +
            "            </if> " +
            "            <if test=\"classId != null and classId != ''\"> " +
            "                #{classId,jdbcType=INTEGER}, " +
            "            </if> " +
            "            <if test=\"type != null and type != ''\"> " +
            "                #{type,jdbcType=INTEGER}, " +
            "            </if> " +
            "            <if test=\"activateTime != null and activateTime != ''\"> " +
            "                #{activateTime,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"gender != null and gender != ''\"> " +
            "                #{gender,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"studentName != null and studentName != ''\"> " +
            "                #{studentName,jdbcType=VARCHAR}, " +
            "            </if> " +
            "        </trim>" +
            "</script>")
    public int insertSelective(PartnerCode partnerClass);

    //根据ID、业务更新
    @Update("<script>" +
            "UPDATE ed_partner_code " +
            "        <set> " +
            "            <if test=\"identifyCode != null and identifyCode != ''\"> " +
            "                identify_code = #{identifyCode,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"status != null and status != ''\"> " +
            "                status = #{status,jdbcType=INTEGER}, " +
            "            </if> " +
            "            <if test=\"userId != null and userId != ''\"> " +
            "                user_id = #{userId,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"schoolId != null and schoolId != ''\"> " +
            "                school_id = #{schoolId,jdbcType=INTEGER}, " +
            "            </if> " +
            "            <if test=\"classId != null and classId != ''\"> " +
            "                class_id = #{classId,jdbcType=INTEGER}, " +
            "            </if> " +
            "            <if test=\"type != null and type != ''\"> " +
            "                type = #{type,jdbcType=INTEGER}, " +
            "            </if> " +
            "            <if test=\"activateTime != null and activateTime != ''\"> " +
            "                activate_time = #{activateTime,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"gender != null and gender != ''\"> " +
            "                gender = #{gender,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"studentName != null and studentName != ''\"> " +
            "                studentName = #{studentName,jdbcType=VARCHAR}, " +
            "            </if> " +
            "        </set> " +
            "        WHERE " +
            "        id = #{id,jdbcType=INTEGER}" +
            "</script>")
    public int updateSelective(PartnerCode partnerClass);

    @Select("SELECT * " +
            " FROM ed_partner_code a " +
            " WHERE 1=1 " +
            " and identify_code = #{identifyCode,jdbcType=VARCHAR}")
    PartnerCode selectByIdentifyCode(@Param("identifyCode") String identifyCode);

    //根据根据条件查询
    @Select("<script>" +
            "SELECT " +
            "  * " +
            "FROM " +
            "ed_partner_code " +
            "WHERE " +
            "1=1 " +
            "<if test=\"userId != null\"> " +
            "  AND user_id = #{userId,jdbcType=VARCHAR} " +
            "</if> " +
            "</script>")
    public List<PartnerCode> selectList(PartnerCode partnerClass);


    @Select("<script>" +
            "SELECT " +
            "  * " +
            "FROM " +
            "ed_partner_code " +
            "WHERE " +
            "1=1 AND status != -1 " +
            "<if test=\"classId != null and classId != ''\"> " +
            "  AND type = 0 " +
            "  AND class_id = #{classId,jdbcType=INTEGER} " +
            "</if> " +
            "<if test=\"schoolId != null and schoolId != ''\"> " +
            "  AND type = 1 " +
            "  AND school_id = #{schoolId,jdbcType=INTEGER} " +
            "</if> " +
            "</script>")
    public List<PartnerCode> selectDeleteList(@Param("classId") String classId, @Param("schoolId") String schoolId);

    @Select("<script>" +
            "SELECT " +
            "  * " +
            "FROM " +
            "ed_partner_code " +
            "WHERE " +
            "1=1 AND status = 1 " +
            "<if test=\"userId != null\"> " +
            "  AND user_id = #{userId,jdbcType=VARCHAR} " +
            "</if> " +
            "</script>")
    public PartnerCode selectUserOpenCode(@Param("userId") String userId);

    @Select("<script>" +
            "SELECT " +
            "  * " +
            "FROM " +
            "ed_partner_code " +
            "WHERE " +
            "1=1 " +
//            " <if test='keywords != null and keywords != \"\"'> " +
//            "    AND ( " +
//            "    partnerClass_name LIKE CONCAT('%', #{keywords, jdbcType=VARCHAR} , '%') " +
//            "    ) " +
//            " </if>" +
            "</script>")
    public List<PartnerCode> selectPageList(PageSearchDto pageSearchDto);

    @Select("<script> select b.user_id from (  " +
            "select s.id,`name` from ed_partner_code c LEFT JOIN ed_partner_class s on c.id=s.teacher_code_id where c.user_id = #{uid} " +
            "  <if test=\"grade != null and grade != ''\"> " +
            "  and s.grade = #{grade} " +
            "  </if> " +
            "  <if test=\"classs != null and classs != ''\"> " +
            "  and s.id = #{classs} " +
            "  </if> " +
            ")  a" +
            "   LEFT JOIN ed_partner_code  b on  a.id = b.class_id where b.status != -1 and b.user_id is not null and b.user_id != '' and b.type =  0   </script> ")
    List<String> queryUserIdById(StudyExamInfoDto studyExamInfoDto);
    @Select("<script>"+
            "select user_id from  ed_partner_code  where school_id = #{schoolId} " +
            "  <if test=\"classs != null and classs != ''\"> " +
            "  and class_id = #{classs} " +
            "  </if> " +
                    "and status != -1 and user_id is not null and user_id != '' and type =  0 " +
            "</script>" )
    List<String> queryUserIdBySchoolId(StudyExamInfoDto studyExamInfoDto);

    @Select(
            "SELECT class_id from ed_partner_code c where c.user_id = #{usetId}  group by class_id " )
    List<String> queryClassIdByUserId(String usetId);
 }