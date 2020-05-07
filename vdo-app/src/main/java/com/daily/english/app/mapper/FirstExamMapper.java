package com.daily.english.app.mapper;

import com.daily.english.app.domain.StudyExamInfo;
import com.daily.english.app.dto.StudyExamInfoDto;
import com.daily.english.app.vo.LevelCheckpointVo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import java.util.List;

@Mapper
@Component
public interface FirstExamMapper {

    @Insert("insert into ed_firstExam_user(userId,nodeNum,firstScore,comprehension,vocabulary," +
            "grammar,highestScore,level,examId,classId,ranking," +
            "highestComprehension,highestVocabulary,highestGrammar) " +
            "values(#{userId}, #{nodeNum}, #{firstScore}, #{comprehension}, #{vocabulary}, #{grammar}, " +
            "#{highestScore}, #{level}, #{examId} ,#{classId},#{ranking}," +
            "#{highestComprehension},#{highestVocabulary},#{highestGrammar})")
    void insertFirstExam(StudyExamInfo studyExamInfo);

    @Update("UPDATE ed_firstExam_user set ranking = ?")


    @Select("select  *  " +
            "from ed_firstExam_user where userId = #{userId} and examId = #{examId}")
    StudyExamInfo findExamByExamId(StudyExamInfo studyExamInfo);

    @Update("UPDATE ed_firstExam_user set highestScore = #{highestScore} ," +
            "highestGrammar = #{highestGrammar}," +
            "highestVocabulary = #{highestVocabulary}," +
            "highestComprehension = #{highestComprehension}" +
            "    where id = #{id}")
    void updateHightScore(StudyExamInfo studyExamInfo);

    @Update("UPDATE ed_firstExam_user set classId = #{classId} " +
            "    where userId = #{userId}")
    void updateClassIdByUserId(@Param("classId")String classId,@Param("userId") String userId);

    @Select("<script> select c.user_id userId,f.nodeNum" +
            "<choose> " +
            "            <when test=\" scorceType != null and scorceType.toString() == '0'.toString() \">  " +
            "               ,f.highestScore firstScore,f.highestComprehension comprehension,f.highestVocabulary vocabulary,f.highestGrammar  grammar" +
            "            </when >  " +
            "            <otherwise>  " +
            "               ,f.firstScore,f.comprehension,f.vocabulary, f.grammar " +
            "            </otherwise>  " +
            "</choose> "+
            "      ,c.class_id classId ,f.highestScore,f.level,examId,c.gender,c.studentName name ," +
            "(SELECT count(*)+1 FROM ed_firstExam_user s WHERE c.class_id = f.classId and f.level = s.level and f.nodeNum = s.nodeNum " +
            "AND s.firstScore > f.firstScore) AS ranking  " +
            "        from ed_firstExam_user  f RIGHT JOIN (select * from ed_partner_code where status = 1 " +
            "  <if test=\"classs != null and classs != ''\"> " +
            "   and class_id = #{classs} " +
            "  </if> " +
            ") c  on f.userId = c.user_id  where  c.user_id in " +
            "<foreach item='id' collection='ids' open='(' separator=',' close=')'>#{id}</foreach> " +
            "  <if test=\"level != null and level != ''\"> " +
            "   and f.level = #{level} " +
            "  </if> " +
            "  <if test=\"keywords != null and keywords != ''\"> " +
            "   and  c.studentName like '%${keywords}%' " +
            "  </if> " +
            "  <if test=\"gender != null and gender != ''\"> " +
            "   AND c.gender = #{gender}" +
            "  </if> " +
            "  <if test=\"checkpointsStart != null and checkpointsStart != ''\"> " +
            "   AND f.nodeNum <![CDATA[ >= ]]> #{checkpointsStart}" +
            "  </if> " +
            "  <if test=\"checkpointsEnd != null and checkpointsEnd != ''\"> " +
            "   AND f.nodeNum <![CDATA[ <= ]]> #{checkpointsEnd} " +
            "  </if> " +
            "  <if test=\"scoreRangeStart != null and scoreRangeStart != '' and scorceType != null and scorceType.toString() == '1'.toString()\"> " +
            "   AND f.firstScore <![CDATA[ >= ]]> #{scoreRangeStart}  " +
            "  </if> " +
            "  <if test=\"scoreRangeEnd != null and scoreRangeEnd != '' and scorceType != null and scorceType.toString() == '1'.toString()\"> " +
            "   AND f.firstScore <![CDATA[ <= ]]> #{scoreRangeEnd} " +
            "  </if> " +
            "  <if test=\"scoreRangeStart != null and scoreRangeStart != '' and scorceType != null and scorceType.toString() == '0'.toString()\"> " +
            "   AND f.highestScore <![CDATA[ >= ]]> #{scoreRangeStart}  " +
            "  </if> " +
            "  <if test=\"scoreRangeEnd != null and scoreRangeEnd != '' and scorceType != null and scorceType.toString() == '0'.toString()\"> " +
            "   AND f.highestScore <![CDATA[ <= ]]> #{scoreRangeEnd} " +
            "  </if> " +
            "<choose> " +
            "            <when test=\"sort != null and sort.toString() == '1'.toString() and scorceType != null and scorceType.toString() == '1'.toString() \">  " +
            "               order by f.firstScore asc  " +
            "            </when >  " +
            "            <when test=\"sort != null and sort.toString() == '0'.toString() and scorceType != null and scorceType.toString() == '1'.toString() \">  " +
            "               order by f.firstScore desc  " +
            "            </when >  " +
            "            <when test=\"sort != null and sort.toString() == '1'.toString() and scorceType != null and scorceType.toString() == '0'.toString() \">  " +
            "               order by f.highestScore asc  " +
            "            </when >  " +
			"            <when test=\"sort != null and sort.toString() == '0'.toString() and scorceType != null and scorceType.toString() == '0'.toString() \">  " +
			"               order by f.highestScore desc  " +
			"            </when >  " +
            "            <otherwise>  " +
            "               order by f.nodeNum  asc " +
            "            </otherwise>  " +
            "</choose> "+
            " </script>")
    List<StudyExamInfo> findExamInfoList(StudyExamInfoDto studyExamInfoDto);

    /*
     * 和上面区别加了 group by userId
     * */
    @Select("<script> select c.user_id userId,MAX(f.nodeNum) nodeNum" +
            "<choose> " +
            "            <when test=\" scorceType != null and scorceType.toString() == '0'.toString() \">  " +
            "               , round(AVG(f.highestScore),0) firstScore, round(AVG(f.highestComprehension),0) comprehension, round(AVG(f.highestVocabulary),0) vocabulary, round(AVG(f.highestGrammar),0) grammar" +
  "            </when >  " +
                    "            <otherwise>  " +
                    "               , round(AVG(f.firstScore),0) firstScore,round(AVG(f.comprehension),0) comprehension,round(AVG(f.vocabulary),0) vocabulary, round(AVG(f.grammar),0) grammar" +
            "            </otherwise>  " +
            "</choose> "+
            "   ,c.class_id classId ,f.highestScore,f.level,examId,c.gender,c.studentName name ," +
            "(SELECT count(*)+1 FROM ed_firstExam_user s WHERE s.classId = f.classId and f.level = s.level and f.nodeNum = s.nodeNum " +
            "AND s.highestScore > f.highestScore) AS ranking  " +
                    "     from ed_firstExam_user  f RIGHT JOIN " +
            " (select * from ed_partner_code where status = 1 " +
            "  <if test=\"classs != null and classs != ''\"> " +
            "   and class_id = #{classs} " +
            "  </if> " +
            ") c  on c.user_id=f.userId    " +
            "  <if test=\"checkpointsStart != null and checkpointsStart != ''\"> " +
            "   AND (f.nodeNum <![CDATA[ >= ]]> #{checkpointsStart} or f.nodeNum  is null)" +
            "  </if> " +
            "  <if test=\"checkpointsEnd != null and checkpointsEnd != ''\"> " +
            "   AND (f.nodeNum <![CDATA[ <= ]]> #{checkpointsEnd} or f.nodeNum  is null)"+
            "  </if> " +
            "  <if test=\"level != null and level != ''\"> " +
            "   and (f.level = #{level} or f.`level` is null)" +
            "  </if> " +
            "where  c.user_id in "+
                    "<foreach item='id' collection='ids' open='(' separator=',' close=')'>#{id}</foreach> " +
            "  <if test=\"keywords != null and keywords != ''\"> " +
            "   and  c.studentName like '%${keywords}%' " +
            "  </if> " +
            "  <if test=\"gender != null and gender != ''\"> " +
            "   AND c.gender = #{gender}" +
            "  </if> " +
            "  <if test=\"scoreRangeStart != null and scoreRangeStart != '' and scorceType != null and scorceType.toString() == '1'.toString()\"> " +
            "   AND f.firstScore <![CDATA[ >= ]]> #{scoreRangeStart}  " +
            "  </if> " +
            "  <if test=\"scoreRangeEnd != null and scoreRangeEnd != '' and scorceType != null and scorceType.toString() == '1'.toString()\"> " +
            "   AND f.firstScore <![CDATA[ <= ]]> #{scoreRangeEnd} " +
            "  </if> " +
            "  <if test=\"scoreRangeStart != null and scoreRangeStart != '' and scorceType != null and scorceType.toString() == '0'.toString()\"> " +
            "   AND f.highestScore <![CDATA[ >= ]]> #{scoreRangeStart}  " +
            "  </if> " +
            "  <if test=\"scoreRangeEnd != null and scoreRangeEnd != '' and scorceType != null and scorceType.toString() == '0'.toString()\"> " +
            "   AND f.highestScore <![CDATA[ <= ]]> #{scoreRangeEnd} " +
            "  </if> " +
            "  GROUP BY c.user_id    " +
            "<choose> " +
            "            <when test=\"sort != null and sort.toString() == '1'.toString() and scorceType != null and scorceType.toString() == '1'.toString() \">  " +
            "               order by f.firstScore asc  " +
            "            </when >  " +
            "            <when test=\"sort != null and sort.toString() == '0'.toString() and scorceType != null and scorceType.toString() == '1'.toString() \">  " +
            "               order by f.firstScore desc  " +
            "            </when >  " +
            "            <when test=\"sort != null and sort.toString() == '1'.toString() and scorceType != null and scorceType.toString() == '0'.toString() \">  " +
            "               order by f.highestScore asc  " +
            "            </when >  " +
            "            <when test=\"sort != null and sort.toString() == '0'.toString() and scorceType != null and scorceType.toString() == '0'.toString() \">  " +
            "               order by f.highestScore desc  " +
            "            </when >  " +
            "            <otherwise>  " +
            "               order by f.nodeNum  asc " +
            "            </otherwise>  " +
            "</choose> "+
            " </script>")
    List<StudyExamInfo> findExamInfoListPage(StudyExamInfoDto studyExamInfoDto);
    @Select("<script>" +
            "select count(*)+1  from ed_firstExam_user where  " +
            "classId  = #{classId} and examId = #{examId} " +
            " <![CDATA[  and firstScore > #{score}   ]]>" +
            "</script>")
    int findUExamIdCount(@Param("classId")String classId,@Param("score") String score,@Param("examId") String examId);

    @Select("<script>" +
            "SELECT MAX(efu.nodeNum)\n" +
            "FROM ed_firstExam_user efu\n" +
            "         LEFT JOIN ed_partner_class epc ON efu.classId = epc.id\n" +
            "WHERE 1=1 " +
            "  <if test=\"classIds != null\"> " +
            "       AND efu.classId IN <foreach item='id' collection='classIds' open='(' separator=',' close=')'>#{id}</foreach> " +
            "  </if> " +
            "  <if test=\"grade != null\"> " +
            "       AND epc.grade = #{grade} " +
            "  </if> " +
            "  <if test=\"classId != null\"> " +
            "       AND efu.classId = #{classId} "+
            "  </if> " +
            "  <if test=\"level != null\"> " +
            "       AND efu.level = #{level} "+
            "  </if> " +
            "</script>")
    Integer getTeacherCheckpointMax(@Param("classIds") List<Long> classIds,@Param("classId")String classId,@Param("grade") String grade,@Param("level") String level);

    @Select("<script>" +
            "SELECT MAX(nodeNum) \n" +
            "FROM ed_firstExam_user\n" +
            "WHERE 1=1"+
            " <if test=\"userId !=null\">" +
            "   AND userId = #{userId}" +
            "</if>"+
            "  <if test=\"level != null\"> " +
            "       AND level = #{level} "+
            "  </if> " +
            "</script>")
    Integer getStudentCheckpointMax(@Param("userId")Integer userId,@Param("level") String level);


    @Select("<script>" +
           "SELECT DISTINCT efu.nodeNum,\n" +
            "    (SELECT sum(s.nodeNum = efu.nodeNum)\n" +
            "     FROM ed_firstExam_user s\n" +
            "     WHERE s.classId = efu.classId AND s.level = efu.level AND efu.firstScore > 0) AS sumNum\n" +
            "FROM ed_firstExam_user efu\n" +
            "WHERE efu.firstScore > 0\n" +
            "  <if test=\"level != null\"> " +
            "       AND efu.level = #{level} " +
            "  </if> " +
            "  <if test=\"classId != null\"> " +
            "       AND efu.classId = #{classId} "+
            "  </if> " +
            "ORDER BY efu.nodeNum DESC;"+
            "</script>")
    List<LevelCheckpointVo> getLevelNodeNumRanking(@Param("classId")String classId, @Param("level") String level);

    //根据ID删除
    @Delete("DELETE " +
            "        FROM ed_firstExam_user   " +
            "        WHERE userId = #{userId}  and  classId = #{classId}")
    public int deleteById(@Param("userId") String userId,@Param("classId") String classId);
    @Select("<script>" +
            "SELECT userId,nodeNum,firstScore,comprehension,vocabulary,  " +
            "grammar,highestScore,level,examId,classId,ranking, " +
            "highestComprehension,highestVocabulary,highestGrammar  " +
            "FROM ed_firstExam_user   " +
            "WHERE  userId = #{studentId} " +
            "</script>")
    List<StudyExamInfo> queryStudentInfoById(@Param("studentId") String studentId);

}
