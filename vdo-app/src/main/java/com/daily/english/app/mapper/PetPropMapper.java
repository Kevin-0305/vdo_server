package com.daily.english.app.mapper;

import java.util.List;

import com.daily.english.app.domain.PetProp;
import com.daily.english.app.dto.PageSearchDto;
import com.daily.english.app.dto.PetPropDto;
import com.daily.english.app.vo.PetPropVo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

/**
 * class_name: IPetPropDAO
 * package: com.vdoenglish.core.dao
 * describe: TODO
 * creat_user: liuzd@chinadailyhk.com
 * creat_date: 2019-09-17
 * creat_time: 15:39
 **/
@Mapper
@Component
public interface PetPropMapper {

    //根据ID查询
    @Select("SELECT * " +
            "        FROM ed_pet_prop " +
            "        WHERE id = #{id,jdbcType=INTEGER}")
    public PetProp selectById(@Param("id") Integer id);

    @Select("SELECT * " +
            "        FROM ed_pet_prop " +
            "        WHERE id = #{id,jdbcType=INTEGER} " +
            "   AND pet_id = #{id,jdbcType=INTEGER}")
    public PetProp selectByIdAndPetId(@Param("id") Integer id, @Param("petId") Integer petId);

    //根据ID删除
    @Delete("DELETE " +
            "        FROM ed_pet_prop " +
            "        WHERE id = #{id,jdbcType=INTEGER}")
    public int deleteById(Integer id);

    //根据业务保存
    @Insert("<script>" +
            "INSERT INTO ed_pet_prop " +
            "        <trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\"> " +
            "            <if test=\"petId != null\"> " +
            "                pet_id, " +
            "            </if> " +
            "            <if test=\"propId != null\"> " +
            "                prop_id, " +
            "            </if> " +
            "            <if test=\"amount != null\"> " +
            "                amount, " +
            "            </if> " +
            "        </trim> " +
            "        <trim prefix=\"VALUES (\" suffix=\")\" suffixOverrides=\",\"> " +
            "            <if test=\"petId != null\"> " +
            "                #{petId,jdbcType=INTEGER}, " +
            "            </if> " +
            "            <if test=\"propId != null\"> " +
            "                #{propId,jdbcType=INTEGER}, " +
            "            </if> " +
            "            <if test=\"amount != null\"> " +
            "                #{amount,jdbcType=INTEGER}, " +
            "            </if> " +
            "        </trim>" +
            "</script>")
    public int insertSelective(PetProp petProp);

    //根据ID、业务更新
    @Update("<script>" +
            "UPDATE ed_pet_prop " +
            "        <set> " +
            "            <if test=\"petId != null\"> " +
            "                pet_id = #{petId,jdbcType=INTEGER}, " +
            "            </if> " +
            "            <if test=\"propId != null\"> " +
            "                prop_id = #{propId,jdbcType=INTEGER}, " +
            "            </if> " +
            "            <if test=\"amount != null\"> " +
            "                amount = #{amount,jdbcType=INTEGER}, " +
            "            </if> " +
            "        </set> " +
            "        WHERE " +
            "        id = #{id,jdbcType=INTEGER}" +
            "</script>")
    public int updateSelective(PetProp petProp);

    //根据根据条件查询
    @Select("<script>" +
            "SELECT " +
            "  * " +
            "FROM " +
            "ed_pet_prop " +
            "WHERE " +
            "1=1 " +
            "<if test=\"petId != null\"> " +
            "   AND pet_id = #{petId,jdbcType=INTEGER} " +
            "</if> " +
            "</script>")
    public List<PetProp> selectList(PetProp petProp);

    //根据根据条件查询
    @Select("<script>" +
            "SELECT epp.*,  " +
            "       ep.prop_name AS 'prop_name', ep.prop_name_hk AS 'prop_name_hk'," +
            "       ep.prop_name_en AS 'prop_name_en', ep.exp AS 'prop_exp', ep.image_url AS 'prop_image', " +
            "       ep.description AS 'prop_description' " +
            "FROM ed_pet_prop epp " +
            "LEFT JOIN ed_prop ep ON ep.id = epp.prop_id " +
            "WHERE " +
            "1=1 " +
            "<if test=\"petId != null\"> " +
            "   AND epp.pet_id = #{petId,jdbcType=INTEGER} " +
            "</if> " +
            "</script>")
    public List<PetPropVo> selectPageList(PetPropDto petPropDto);

    @Select("<script>" +
            "SELECT " +
            "  * " +
            "FROM " +
            "ed_pet_prop " +
            "WHERE " +
            "1=1 " +
            "<if test=\"petId != null\"> " +
            "   AND pet_id = #{petId,jdbcType=INTEGER} " +
            "</if> " +
            "<if test=\"propId != null\"> " +
            "   AND prop_id = #{propId,jdbcType=INTEGER} " +
            "</if> " +
            "</script>")
    public PetProp selectListByPetIdAndPetPropId(@Param("petId") Integer petId, @Param("propId") Integer propId);
}