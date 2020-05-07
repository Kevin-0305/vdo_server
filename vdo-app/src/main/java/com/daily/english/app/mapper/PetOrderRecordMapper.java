package com.daily.english.app.mapper;

import java.util.List;

import com.daily.english.app.domain.PetOrderRecord;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

/**
 * class_name: IPetOrderRecordDAO
 * package: com.vdoenglish.core.dao
 * describe: TODO
 * creat_user: liuzd@chinadailyhk.com
 * creat_date: 2019-09-17
 * creat_time: 15:39
 **/
@Mapper
@Component
public interface PetOrderRecordMapper {

    //根据ID查询
    @Select("SELECT * " +
            "        FROM ed_pet_order_record " +
            "        WHERE id = #{id,jdbcType=INTEGER}")
    public PetOrderRecord selectById(Integer id);

    //根据ID删除
    @Delete("DELETE " +
            "        FROM ed_pet_order_record " +
            "        WHERE id = #{id,jdbcType=INTEGER}")
    public int deleteById(Integer id);

    //根据业务保存
    @Insert("<script>" +
            "INSERT INTO ed_pet_order_record " +
            "        <trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\"> " +
            "            <if test=\"goodsId != null\"> " +
            "                goods_id, " +
            "            </if> " +
            "            <if test=\"petId != null\"> " +
            "                pet_id, " +
            "            </if> " +
            "            <if test=\"amount != null\"> " +
            "                amount, " +
            "            </if> " +
            "            <if test=\"totalValue != null\"> " +
            "                total_value, " +
            "            </if> " +
            "            <if test=\"createTime != null\"> " +
            "                create_time, " +
            "            </if> " +
            "        </trim> " +
            "        <trim prefix=\"VALUES (\" suffix=\")\" suffixOverrides=\",\"> " +
            "            <if test=\"goodsId != null\"> " +
            "                #{goodsId,jdbcType=INTEGER}, " +
            "            </if> " +
            "            <if test=\"petId != null\"> " +
            "                #{petId,jdbcType=INTEGER}, " +
            "            </if> " +
            "            <if test=\"amount != null\"> " +
            "                #{amount,jdbcType=INTEGER}, " +
            "            </if> " +
            "            <if test=\"totalValue != null\"> " +
            "                #{totalValue,jdbcType=DOUBLE}, " +
            "            </if> " +
            "            <if test=\"createTime != null\"> " +
            "                #{createTime,jdbcType=VARCHAR}, " +
            "            </if> " +
            "        </trim>" +
            "</script>")
    public int insertSelective(PetOrderRecord petOrderRecord);

    //根据ID、业务更新
    @Update("<script>" +
            "UPDATE ed_pet_order_record " +
            "        <set> " +
            "            <if test=\"goodsId != null\"> " +
            "                goods_id = #{goodsId,jdbcType=INTEGER}, " +
            "            </if> " +
            "            <if test=\"petId != null\"> " +
            "                pet_id = #{petId,jdbcType=INTEGER}, " +
            "            </if> " +
            "            <if test=\"amount != null\"> " +
            "                amount = #{amount,jdbcType=INTEGER}, " +
            "            </if> " +
            "            <if test=\"totalValue != null\"> " +
            "                total_value = #{totalValue,jdbcType=DOUBLE}, " +
            "            </if> " +
            "            <if test=\"createTime != null\"> " +
            "                create_time = #{createTime,jdbcType=VARCHAR}, " +
            "            </if> " +
            "        </set> " +
            "        WHERE " +
            "        id = #{id,jdbcType=INTEGER}" +
            "</script>")
    public int updateSelective(PetOrderRecord petOrderRecord);

    //根据根据条件查询
    @Select("<script>" +
            "SELECT " +
            "  * " +
            "FROM " +
            "ed_pet_order_record " +
            "WHERE " +
            "1=1 " +
            "ORDER BY create_time DESC " +
            "</script>")
    public List<PetOrderRecord> selectList(PetOrderRecord petOrderRecord);
}