package com.daily.english.app.mapper;

import com.daily.english.app.domain.Part;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface PartMapper {

    @Insert("insert into ed_part(exam_info_id, part_name, orders, status, create_time, update_time, " +
            "create_account, update_account) " +
            "values(#{examInfoId}, #{partName}, #{orders}, " +
            "#{status}, #{createTime}, #{updateTime}, #{createAccount}, #{updateAccount})")
    @Options(useGeneratedKeys = true, keyProperty="partId", keyColumn = "part_id")
    void insertPart(Part part);

    @Update("update ed_part set part_name=#{partName}, orders=#{orders}, status=#{status}, " +
            "update_time=#{updateTime}, update_account=#{updateAccount} " +
            "where part_id=#{partId}")
    int updatePart(Part part);

    @Delete("delete from ed_part where exam_info_id = #{id}")
    void deletePartByExamId(@Param("id") String id);

    @Select("select part_id, exam_info_id, part_name, orders, status, create_time, update_time, " +
            "create_account, update_account " +
            "from ed_part where exam_info_id = #{id}")
    List<Part> findPartListByExamId(@Param("id") String id);

    @Select("select part_id, exam_info_id, part_name, orders, status, create_time, update_time, " +
            "create_account, update_account " +
            "from ed_part")
    List<Part> findPartList();

//    @Select({"<script>" +
//            "select part_id, exam_info_id, part_name, orders, status, create_time, update_time, " +
//            "create_account, update_account " +
//            "from ed_part where exam_info_id in " +
//            "<foreach item='id' collection='ids' open='(' separator=',' close=')'>#{id}</foreach> " +
//            "order by orders " +
//            "</script>"})
//    List<Part> findPartListByExamIds(@Param("ids") List<String> ids);

}
