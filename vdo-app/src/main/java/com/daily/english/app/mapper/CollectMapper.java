package com.daily.english.app.mapper;

import com.daily.english.app.condition.CollectCondition;
import com.daily.english.app.domain.Collect;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface CollectMapper {

    @Insert("insert into ed_collect(uid, vid) " +
            "values(#{uid}, #{vid})")
    @Options(useGeneratedKeys = true, keyProperty="id", keyColumn = "id")
    int insert(Collect collect);

    @Insert("insert into ed_collect (uid, vid, create_time,type,examId,mapId)" +
            "values(#{collect.uid}, #{collect.vid}, #{collect.createTime},#{collect.type}" +
            ", #{collect.examId}, #{collect.mapId})")
    void insertVideoCollect(@Param("collect") Collect collect);

    @Select("select id, uid, vid,examId,mapId  " +
            "from ed_collect")
    List<Collect> findCollectList();



    @Select({"<script>" +
            "select count(*)" +
            "from ed_collect  where  1 =1 " +
            "  <if test=\"uid != null and uid != '' \"> " +
            "    AND  uid  = #{uid} " +
            "  </if> " +
            "  <if test=\"type != null and type != '' \"> " +
            "    AND  type  = #{type} " +
            "  </if> " +
            "</script>"})
    Integer findVideoCollectListByPageCount(CollectCondition collect);
    @Select({"<script>" +
            "delete from ed_collect  " +
            "   where uid = #{uid}  " +
            "and vid in " +
            "<foreach item='id' collection='ids' open='(' separator=',' close=')'>#{id}</foreach> " +
            "</script>"})
    Integer deleteBatchVideoCollect(@Param("uid") Integer uid,@Param("ids") List<String> ids);

    @Select("<script>" +
            "select id, uid, vid, create_time createTime,type,examId,mapId   " +
            "from ed_collect   where 1=1 " +
            "  <if test=\"uid != null and uid != '' \"> " +
            "    AND  uid  = #{uid} " +
            "  </if> " +
            "  <if test=\"type != null and type != '' \"> " +
            "    AND  type  = #{type} " +
            "  </if> " +
            "order by create_time desc limit #{offset}, #{limit}" +
            "</script>")
    List<Collect> findVideoCollectListByPage( CollectCondition collect);


    @Select("<script>" +
            "select count(*) from  ed_collect   where " +
            " uid = #{collect.uid} and vid =#{collect.vid}" +
            "</script>")
    Integer findVideoCollectCount(@Param("collect") Collect collect);
}
