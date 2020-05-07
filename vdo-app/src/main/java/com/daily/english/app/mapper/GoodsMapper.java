package com.daily.english.app.mapper;

import com.daily.english.app.domain.Goods;
import com.daily.english.app.dto.GameGoodsDto;
import com.daily.english.app.dto.GoodsDto;
import com.daily.english.app.vo.GoodsVo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * class_name: GoodsMapper
 * package: com.vdoenglish.core.dao
 * describe: TODO
 * creat_user: liuzd@chinadailyhk.com
 * creat_date: 2019-08-29
 * creat_time: 15:39
 **/
@Mapper
@Component
public interface GoodsMapper {

    //根据ID查询
    @Select("SELECT * " +
            "        FROM ed_goods " +
            "        WHERE id = #{id,jdbcType=INTEGER}")
    public Goods selectById(@Param("id") String id);

    //根据ID删除
    @Delete("DELETE " +
            "        FROM ed_goods " +
            "        WHERE id = #{id,jdbcType=INTEGER}")
    public int deleteById(@Param("id") String id);

    //根据业务保存
    @Insert("<script>" +
            "INSERT INTO ed_goods " +
            "        <trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\"> " +
            "            <if test=\"propId != null\"> " +
            "                prop_id, " +
            "            </if> " +
            "            <if test=\"sortNo != null\"> " +
            "                sort_no, " +
            "            </if> " +
            "            <if test=\"goodsBadge != null\"> " +
            "                goods_badge, " +
            "            </if> " +
            "            <if test=\"discount != null\"> " +
            "                discount, " +
            "            </if> " +
            "            <if test=\"price != null\"> " +
            "                price, " +
            "            </if> " +
            "            <if test=\"discountPrice != null\"> " +
            "                discount_price, " +
            "            </if> " +
            "            <if test=\"status != null\"> " +
            "                status, " +
            "            </if> " +
            "            <if test=\"createTime != null\"> " +
            "                create_time, " +
            "            </if> " +
            "            <if test=\"createUser != null\"> " +
            "                create_user, " +
            "            </if> " +
            "            <if test=\"updateUser != null\"> " +
            "                update_user, " +
            "            </if> " +
            "            <if test=\"updateTime != null\"> " +
            "                update_time, " +
            "            </if> " +
            "            <if test=\"newBadge != null\"> " +
            "                new_badge, " +
            "            </if> " +
            "            <if test=\"hotBadge != null\"> " +
            "                hot_badge, " +
            "            </if> " +
            "            <if test=\"activityBadge != null\"> " +
            "                activity_badge, " +
            "            </if> " +
            "            <if test=\"discountBadge != null\"> " +
            "                discount_badge, " +
            "            </if> " +
            "        </trim> " +
            "        <trim prefix=\"VALUES (\" suffix=\")\" suffixOverrides=\",\"> " +
            "            <if test=\"propId != null\"> " +
            "                #{propId,jdbcType=INTEGER}, " +
            "            </if> " +
            "            <if test=\"sortNo != null\"> " +
            "                #{sortNo,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"goodsBadge != null\"> " +
            "                #{goodsBadge,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"discount != null\"> " +
            "                #{discount,jdbcType=DOUBLE}, " +
            "            </if> " +
            "            <if test=\"price != null\"> " +
            "                #{price,jdbcType=DOUBLE}, " +
            "            </if> " +
            "            <if test=\"discountPrice != null\"> " +
            "                #{discountPrice,jdbcType=DOUBLE}, " +
            "            </if> " +
            "            <if test=\"status != null\"> " +
            "                #{status,jdbcType=INTEGER}, " +
            "            </if> " +
            "            <if test=\"createTime != null\"> " +
            "                #{createTime,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"createUser != null\"> " +
            "                #{createUser,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"updateUser != null\"> " +
            "                #{updateUser,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"updateTime != null\"> " +
            "                #{updateTime,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"newBadge != null\"> " +
            "                #{newBadge,jdbcType=INTEGER}, " +
            "            </if> " +
            "            <if test=\"hotBadge != null\"> " +
            "                #{hotBadge,jdbcType=INTEGER}, " +
            "            </if> " +
            "            <if test=\"activityBadge != null\"> " +
            "                #{activityBadge,jdbcType=INTEGER}, " +
            "            </if> " +
            "            <if test=\"discountBadge != null\"> " +
            "                #{discountBadge,jdbcType=INTEGER}, " +
            "            </if> " +
            "        </trim>" +
            "</script>")
    public int insertSelective(Goods goods);

    //根据ID、业务更新
    @Update("<script>" +
            "UPDATE ed_goods " +
            "        <set> " +
            "            <if test=\"propId != null\"> " +
            "                prop_id = #{propId,jdbcType=INTEGER}, " +
            "            </if> " +
            "            <if test=\"sortNo != null\"> " +
            "                sort_no = #{sortNo,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"goodsBadge != null\"> " +
            "                goods_badge = #{goodsBadge,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"discount != null\"> " +
            "                discount = #{discount,jdbcType=DOUBLE}, " +
            "            </if> " +
            "            <if test=\"price != null\"> " +
            "                price = #{price,jdbcType=DOUBLE}, " +
            "            </if> " +
            "            <if test=\"discountPrice != null\"> " +
            "                discount_price = #{discountPrice,jdbcType=DOUBLE}, " +
            "            </if> " +
            "            <if test=\"status != null\"> " +
            "                status = #{status,jdbcType=INTEGER}, " +
            "            </if> " +
            "            <if test=\"createTime != null\"> " +
            "                create_time = #{createTime,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"createUser != null\"> " +
            "                create_user = #{createUser,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"updateUser != null\"> " +
            "                update_user = #{updateUser,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"updateTime != null\"> " +
            "                update_time = #{updateTime,jdbcType=VARCHAR}, " +
            "            </if> " +
            "            <if test=\"newBadge != null\"> " +
            "                new_badge = #{newBadge,jdbcType=INTEGER}, " +
            "            </if> " +
            "            <if test=\"hotBadge != null\"> " +
            "                hot_badge = #{hotBadge,jdbcType=INTEGER}, " +
            "            </if> " +
            "            <if test=\"activityBadge != null\"> " +
            "                activity_badge = #{activityBadge,jdbcType=INTEGER}, " +
            "            </if> " +
            "            <if test=\"discountBadge != null\"> " +
            "                discount_badge = #{discountBadge,jdbcType=INTEGER}, " +
            "            </if> " +
            "        </set> " +
            "        WHERE " +
            "        id = #{id,jdbcType=INTEGER}" +
            "</script>")
    public int updateSelective(Goods goods);

    //根据根据条件查询
    @Select("<script>" +
            "SELECT " +
            "  * " +
            "FROM " +
            "ed_goods " +
            "WHERE " +
            "1=1 " +
            " <if test= 'status != null '> " +
            "  AND status = #{status,jdbcType=INTEGER} " +
            " </if> " +
            "</script>")
    public List<Goods> selectList(Goods goods);

    @Select("<script>" +
            "SELECT eg.id, eg.prop_id, eg.sort_no, eg.goods_badge, eg.discount, eg.price, eg.discount_price, eg.status, " +
            "    eg.new_badge, eg.hot_badge, eg.activity_badge, eg.discount_badge, " +
            "    eg.create_time, eg.create_user, " +
            "    eg.update_user, eg.update_time, ep.prop_name AS 'prop_name', ep.exp AS 'prop_exp', ep.prop_name_hk AS 'prop_name_hk'," +
            "    ep.prop_name_en AS 'prop_name_en',ep.image_url AS 'prop_image',ep.description AS 'prop_description' " +
            "FROM ed_goods eg " +
            "         LEFT JOIN ed_prop ep ON ep.id = eg.prop_id " +
            "WHERE " +
            "1=1 " +
            "<if test= 'status != null '> " +
            "  AND eg.status = #{status,jdbcType=INTEGER} " +
            "</if> " +
            "ORDER BY eg.create_time DESC " +
            "</script>")
    public List<GoodsVo> selectPageList(GoodsDto goodsDto);


    @Select("<script>" +
            "SELECT eg.id, eg.prop_id, eg.sort_no, eg.goods_badge, eg.new_badge, eg.hot_badge, eg.activity_badge, eg.discount_badge, " +
            "    eg.discount, eg.price, eg.discount_price, eg.status, " +
            "    eg.create_time, eg.create_user, " +
            "    eg.update_user, eg.update_time, ep.prop_name AS 'prop_name', ep.exp AS 'prop_exp', " +
            "    ep.prop_name_hk AS 'prop_name_hk', " +
            "    ep.prop_name_en AS 'prop_name_en', ep.image_url AS 'prop_image', ep.description AS 'prop_description' " +
            "FROM ed_goods eg " +
            "         LEFT JOIN ed_prop ep ON ep.id = eg.prop_id " +
            "WHERE 1 = 1 " +
            "   <if test=\"sourceType != null and sourceType == 1 \"> " +
            "      AND (eg.sort_no <![CDATA[ >= ]]> 1 AND eg.sort_no <![CDATA[ <= ]]> 50) " +
            "   </if> " +
            "   <if test=\"sourceType != null and sourceType == 2 \"> " +
            "      AND (eg.sort_no <![CDATA[ >= ]]> 51 AND eg.sort_no <![CDATA[ <= ]]> 100) " +
            "   </if> " +
            "   <if test=\"sourceType != null and sourceType == 3 \"> " +
            "      AND (eg.sort_no <![CDATA[ >= ]]> 101 AND eg.sort_no <![CDATA[ <= ]]> 150) " +
            "   </if> " +
            "   <if test=\"sourceType != null and sourceType == 4 \"> " +
            "      AND (eg.sort_no <![CDATA[ >= ]]> 151 AND eg.sort_no <![CDATA[ <= ]]> 200) " +
            "   </if> " +
            "   <if test=\"sourceType != null and sourceType == 5 \"> " +
            "      AND (eg.sort_no <![CDATA[ >= ]]> 201 AND eg.sort_no <![CDATA[ <= ]]> 250) " +
            "   </if> " +
            "   <if test=\"sourceType == null \"> " +
            "       AND (eg.sort_no <![CDATA[ >= ]]> 1 AND eg.sort_no <![CDATA[ <= ]]> 50) " +
            "   </if> " +
            "ORDER BY eg.new_badge DESC, eg.activity_badge DESC, eg.hot_badge DESC, eg.discount_badge DESC, eg.sort_no ASC " +
            "</script>")
    public List<GoodsVo> selectGameGoodsPageList(GameGoodsDto gameGoodsDto);


    @Select("<script>" +
            "SELECT sort_no FROM ed_goods WHERE sort_no IS NOT NULL AND sort_no != '' ORDER BY sort_no" +
            "</script>")
    public List<Integer> existSortNo(@Param("sourceType") Integer sourceType);


}