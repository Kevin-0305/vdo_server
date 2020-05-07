package com.daily.english.app.mapper;

import com.daily.english.app.domain.PartnerClass;
import com.daily.english.app.dto.PageSearchDto;
import com.daily.english.app.dto.PartnerClassDto;
import com.daily.english.app.dto.TeacherClassDto;
import com.daily.english.app.vo.TeacherClassVo;
import org.apache.ibatis.annotations.*;
import org.apache.logging.log4j.core.tools.picocli.CommandLine;
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
public interface PartnerClassMapper {

    //根据ID查询
    @Select("SELECT * " +
            "        FROM ed_partner_class " +
            "        WHERE id = #{id,jdbcType=INTEGER}")
    public PartnerClass selectById(@Param("id") String id);

    //根据ID查询
    @Select("SELECT epp.*, teu.nickname AS 'teacher_name',\n" +
            "    eps.name AS 'school_name'\n" +
            "FROM ed_partner_class epp\n" +
            "         LEFT JOIN ed_partner_code epc ON epc.id = epp.teacher_code_id\n" +
            "         LEFT JOIN ed_partner_school eps ON eps.id = epp.school_id\n" +
            "         LEFT JOIN ed_user teu ON teu.id = epc.user_id " +
            " WHERE epp.id = #{id,jdbcType=INTEGER}")
    public PartnerClass selectClassInfoById(@Param("id") String id);


    //根据ID删除
    @Delete("DELETE " +
            "        FROM ed_partner_class " +
            "        WHERE id = #{id,jdbcType=INTEGER}")
    public int deleteById(@Param("id") String id);

    //根据业务保存
    @Insert("<script>" +
            "INSERT INTO ed_partner_class " +
            "        <trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\"> " +
            "            <if test=\"name != null\"> " +
            "                name, " +
            "            </if> " +
            "            <if test=\"teacherCodeId != null and teacherCodeId != '' \"> " +
            "                teacher_code_id, " +
            "            </if> " +
            "            <if test=\"level != null and level != '' \"> " +
            "                level, " +
            "            </if> " +
            "            <if test=\"grade != null and grade != '' \"> " +
            "                grade, " +
            "            </if> " +
            "            <if test=\"schoolId != null and schoolId != ''\"> " +
            "                school_id, " +
            "            </if> " +
            "            <if test=\"numCount != null and numCount != ''\"> " +
            "                num_count, " +
            "            </if> " +
            "            <if test=\"startTime != null and startTime != ''\">\n" +
            "                start_time,\n" +
            "            </if>\n" +
            "            <if test=\"endTime != null and endTime != ''\">\n" +
            "                end_time,\n" +
            "            </if>\n" +
            "            <if test=\"createTime != null and createTime != ''\"> " +
            "                create_time, " +
            "            </if> " +
            "            <if test=\"createUser != null and createUser != ''\"> " +
            "                create_user, " +
            "            </if> " +
            "            <if test=\"updateTime != null and updateTime != ''\"> " +
            "                update_time, " +
            "            </if> " +
            "            <if test=\"updateUser != null and updateUser != ''\"> " +
            "                update_user, " +
            "            </if> " +
            "        </trim> " +
            "        <trim prefix=\"VALUES (\" suffix=\")\" suffixOverrides=\",\"> " +
            "            <if test=\"name != null\"> " +
            "                #{name,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"teacherCodeId != null and teacherCodeId != ''\"> " +
            "                #{teacherCodeId,jdbcType=INTEGER}, " +
            "            </if> " +
            "            <if test=\"level != null and level != ''\"> " +
            "                #{level,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"grade != null and grade != ''\"> " +
            "                #{grade,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"schoolId != null and schoolId != ''\"> " +
            "                #{schoolId,jdbcType=INTEGER}, " +
            "            </if> " +
            "            <if test=\"numCount != null and numCount != ''\"> " +
            "                #{numCount,jdbcType=INTEGER}, " +
            "            </if> " +
            "            <if test=\"startTime != null and startTime != ''\">\n" +
            "                #{startTime,jdbcType=VARCHAR},\n" +
            "            </if>\n" +
            "            <if test=\"endTime != null and endTime != ''\">\n" +
            "                #{endTime,jdbcType=VARCHAR},\n" +
            "            </if>\n" +
            "            <if test=\"createTime != null and createTime != ''\"> " +
            "                #{createTime,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"createUser != null and createUser != ''\"> " +
            "                #{createUser,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"updateTime != null and updateTime != ''\"> " +
            "                #{updateTime,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"updateUser != null and updateUser != ''\"> " +
            "                #{updateUser,jdbcType=VARCHAR}, " +
            "            </if> " +
            "        </trim>" +
            "</script>")
    public int insertSelective(PartnerClass partnerClass);

    //根据ID、业务更新
    @Update("<script>" +
            " UPDATE ed_partner_class " +
            "        <set> " +
            "            <if test=\"name != null and name != ''\"> " +
            "                name = #{name,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"teacherCodeId != null and teacherCodeId != ''\"> " +
            "                teacher_code_id = #{teacherCodeId,jdbcType=INTEGER}, " +
            "            </if> " +
            "            <if test=\"level != null and level != ''\"> " +
            "                level = #{level,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"grade != null and grade != ''\"> " +
            "                grade = #{grade,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"schoolId != null and schoolId != ''\"> " +
            "                school_id = #{schoolId,jdbcType=INTEGER}, " +
            "            </if> " +
            "            <if test=\"numCount != null\"> " +
            "                num_count = #{numCount,jdbcType=INTEGER}, " +
            "            </if> " +
            "            <if test=\"startTime != null and startTime != ''\">\n" +
            "                start_time = #{startTime,jdbcType=VARCHAR},\n" +
            "            </if>\n" +
            "            <if test=\"endTime != null and endTime != ''\">\n" +
            "                end_time = #{endTime,jdbcType=VARCHAR},\n" +
            "            </if>\n" +
            "            <if test=\"createTime != null and createTime != ''\"> " +
            "                create_time = #{createTime,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"createUser != null and createUser != ''\"> " +
            "                create_user = #{createUser,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"updateTime != null and updateTime != ''\"> " +
            "                update_time = #{updateTime,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"updateUser != null and updateUser != ''\"> " +
            "                update_user = #{updateUser,jdbcType=VARCHAR}, " +
            "            </if> " +
            "        </set> " +
            "        WHERE " +
            "        id = #{id,jdbcType=INTEGER}" +
            "</script>")
    public int updateSelective(PartnerClass partnerClass);

    //根据根据条件查询
    @Select("<script>" +
            "SELECT " +
            "  * " +
            "FROM " +
            "ed_partner_class " +
            "WHERE " +
            "1=1 " +
            "<if test=\"teacherCodeId != null\"> " +
            "  AND teacher_code_id = #{teacherCodeId,jdbcType=INTEGER} " +
            "</if> " +
            "<if test=\"schoolId != null and schoolId != ''\"> " +
            "  AND school_id = #{schoolId,jdbcType=INTEGER} " +
            "</if> " +
            "ORDER BY create_time DESC " +
            "</script>")
    public List<PartnerClass> selectList(PartnerClass partnerClass);

    @Select("<script>" +
            "SELECT " +
            "  epc.*,eps.name as 'school_name' " +
            "FROM " +
            "ed_partner_class epc LEFT JOIN ed_partner_school eps ON eps.id = epc.school_id " +
            "WHERE " +
            "1=1 " +
            " <if test=\"grade != null\"> " +
            "     AND epc.grade = #{grade,jdbcType=INTEGER} " +
            " </if> " +
            " <if test=\"schoolId != null\"> " +
            "     AND epc.school_id = #{schoolId,jdbcType=INTEGER} " +
            " </if> " +
            "<if test=\"teacherCodeId != null\"> " +
            "  AND epc.teacher_code_id = #{teacherCodeId,jdbcType=INTEGER} " +
            "</if> " +
            " <if test='keywords != null and keywords != \"\"'> " +
            "    AND ( " +
            "    epc.partnerClass_name LIKE CONCAT('%', #{keywords, jdbcType=VARCHAR} , '%') " +
            "    ) " +
            " </if>" +
            "ORDER BY create_time DESC " +
            "</script>")
    public List<PartnerClass> selectPageList(PartnerClassDto partnerClassDto);


    @Select("<script>" +
            "SELECT " +
            "  epc.id AS 'classId',epc.name AS 'className',epc.level " +
            "FROM " +
            "ed_partner_class epc " +
            "WHERE " +
            "1=1 " +
            " <if test=\"schoolId != null\"> " +
            "     AND epc.school_id = #{schoolId,jdbcType=INTEGER} " +
            " </if> " +
            "<if test=\"teacherUserId != null\"> " +
            "  AND epc.teacher_code_id = #{teacherUserId,jdbcType=INTEGER} " +
            "</if> " +
            "ORDER BY create_time DESC " +
            "</script>")
    public List<TeacherClassVo> selectTeacherClassPageList(TeacherClassDto teacherClassDto);

    @Select("<script>" +
            "SELECT " +
            "  epc.id AS 'classId'" +
            "FROM " +
            "ed_partner_class epc " +
            "WHERE " +
            "1=1 " +
            " <if test=\"schoolId != null\"> " +
            "     AND epc.school_id = #{schoolId,jdbcType=INTEGER} " +
            " </if> " +
            "<if test=\"teacherUserId != null\"> " +
            "  AND epc.teacher_code_id = #{teacherUserId,jdbcType=INTEGER} " +
            "</if> " +
            "</script>")
    public List<Long> selectTeacherClassIdsList(@Param("schoolId") String schoolId,@Param("teacherUserId") String teacherUserId);

    @Select("<script> SELECT id,name from ed_partner_class  where 1 = 1  " +
            " <if test=\"schoolId != null\"> " +
            "     AND school_id = #{schoolId} " +
            " </if> " +
            " " +
            " <if test=\"teacherCodeId != null \"> " +
            "and teacher_code_id in (SELECT id from ed_partner_code where  type = 1 and user_id = #{teacherCodeId} )  " +
            " </if> " +
            "</script>")
    List<PartnerClass> queryClassListById(PartnerClass partnerClass);
    @Select("SELECT name from ed_partner_class  where id = #{id}")
    String queryClassNameById(String id);


    @Select("<script>" +
            "SELECT " +
            "  epc.id AS 'classId',epc.name AS 'className',epc.level " +
            "FROM " +
            "ed_partner_class epc " +
            "WHERE " +
            "1=1 " +
            " <if test=\"schoolId != null\"> " +
            " AND epc.school_id = #{schoolId} " +
            " </if> " +
            "<if test=\"userId != null\"> " +
            " and epc.teacher_code_id in (select id from ed_partner_code c where c.user_id = #{userId}) " +
            "</if> " +
            "<if test=\"level != null and level != ''\"> " +
            "  AND epc.level like CONCAT('%', #{level} , '%') " +
            "</if> " +
            "  and epc.grade = #{grade}  " +
            "ORDER BY epc.create_time DESC " +
            "</script>")
    public List<TeacherClassVo> queryClassByUserId(@Param("schoolId") String schoolId,@Param("userId") String userId,@Param("level") String level,@Param("grade")  String grade);

}