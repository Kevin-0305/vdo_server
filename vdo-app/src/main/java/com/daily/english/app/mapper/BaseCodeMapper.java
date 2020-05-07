package com.daily.english.app.mapper;

import com.daily.english.app.domain.BaseCode;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * class_name: BaseCodeMapper
 * package: com.vdoenglish.core.dao
 * describe: TODO
 * creat_user: liuzd@chinadailyhk.com
 * creat_date: 2019-09-25
 * creat_time: 15:39
 **/
@Mapper
@Component
public interface BaseCodeMapper {

    //根据ID查询
    @Select("SELECT * " +
            "        FROM ed_base_code " +
            "        WHERE code = #{code,jdbcType=VARCHAR}")
    public BaseCode selectByCode(@Param("code") String code);

    //根据业务保存
    @Insert("<script>" +
            "INSERT INTO ed_base_code " +
            "        <trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\"> " +
            "            <if test=\"code != null\"> " +
            "                code, " +
            "            </if> " +
            "            <if test=\"status != null\"> " +
            "                status, " +
            "            </if> " +
            "            <if test=\"createTime != null\"> " +
            "                create_time, " +
            "            </if> " +
            "        </trim> " +
            "        <trim prefix=\"VALUES (\" suffix=\")\" suffixOverrides=\",\"> " +
            "            <if test=\"code != null\"> " +
            "                #{code,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"status != null\"> " +
            "                #{status,jdbcType=INTEGER}, " +
            "            </if> " +
            "            <if test=\"createTime != null\"> " +
            "                #{createTime,jdbcType=VARCHAR}, " +
            "            </if> " +
            "        </trim>" +
            "</script>")
    public int insertSelective(BaseCode baseCode);

    //根据ID、业务更新
    @Update("<script>" +
            "UPDATE ed_base_code " +
            "        <set> " +
            "            <if test=\"code != null\"> " +
            "                code = #{code,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"status != null\"> " +
            "                status = #{status,jdbcType=INTEGER}, " +
            "            </if> " +
            "            <if test=\"createTime != null\"> " +
            "                create_time = #{createTime,jdbcType=VARCHAR}, " +
            "            </if> " +
            "        </set> " +
            "        WHERE " +
            "        code = #{code,jdbcType=VARCHAR}" +
            "</script>")
    public int updateSelective(BaseCode baseCode);

    //根据根据条件查询
    @Select("<script>" +
            "SELECT * FROM ed_base_code WHERE 1=1 " +
            "<if test=\"status != null\"> " +
            "   AND status = #{id,jdbcType=INTEGER} " +
            "</if> " +
            "</script>")
    public List<BaseCode> selectList(BaseCode baseCode);

    //根据根据条件查询
    @Select("<script>" +
            "SELECT * FROM ed_base_code WHERE 1=1 " +
            "   AND status = 0 ORDER BY create_time " +
            "   limit #{pageSize,jdbcType=INTEGER}" +
            "</script>")
    public List<BaseCode> selectUseCodeList(@Param("pageSize") Integer pageSize);
}
