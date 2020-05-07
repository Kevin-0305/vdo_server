package com.daily.english.app.mapper;

import com.daily.english.app.condition.ExamCommentCondition;
import com.daily.english.app.domain.ExamComment;
import com.daily.english.app.domain.ExamCommentPraise;
import com.daily.english.app.vo.ExamCommentVo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ExamCommentMapper {

    @Insert("insert into ed_exam_comment(examId, userId,content,createTime) " +
            "values(#{examId}, #{userId}, #{content}, #{createTime})")
    @Options(useGeneratedKeys = true, keyProperty="id", keyColumn = "id")
    int insert(ExamCommentVo examCommentVo);
    @Insert("insert into ed_exam_comment_praise( userId,commentId) " +
            "values(#{userId}, #{commentId})")
    @Options(useGeneratedKeys = true, keyProperty="id", keyColumn = "id")
    int insertPraise(ExamCommentPraise examCommentPraise);

    @Update("update ed_exam_comment set praise = #{praise}" +
            "  where id = #{id}")
    int updateComment(ExamComment examComment);
    @Select("select id, examId, userId, createTime,praise,content  from ed_exam_comment  where id=#{id}")
    ExamComment queryExamCommentById(@Param("id") Long id);
    @Select("select count(*)  from ed_exam_comment_praise where userId=#{userId}  and commentId = #{commentId}")
    int queryPraiseCountByUserId(ExamCommentPraise examCommentPraise);

    @Select({"<script>" +
            "select count(*)" +
            "from ed_exam_comment  where  1 =1 " +
            "  <if test=\"examId != null and examId != '' \"> " +
            "    AND  examId  = #{examId} " +
            "  </if> " +
            "</script>"})
    Integer findCommentListByExamCount(ExamCommentCondition collect);

    @Select("<script>" +
            "select id, examId, userId, createTime,praise,content   " +
            "from ed_exam_comment   where 1=1 " +
            "  <if test=\"examId != null and examId != '' \"> " +
            "    AND  examId  = #{examId} " +
            "  </if> " +
            "order by createTime desc limit #{offset}, #{limit}" +
            "</script>")
    List<ExamComment> findCommentListByExam(ExamCommentCondition collect);

}
