package com.daily.english.app.service;

import com.daily.english.app.domain.Goods;
import com.daily.english.app.dto.GameGoodsDto;
import com.daily.english.app.dto.GoodsDto;
import com.daily.english.app.mapper.GoodsMapper;
import com.daily.english.app.vo.GoodsVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    public List<Goods> selectGoodsList(Goods goods) {
        return goodsMapper.selectList(goods);
    }

    public Goods selectGoodsById(String id) {
        return goodsMapper.selectById(id);
    }

    public void saveGoods(Goods goods) {
        if (null != goods.getId() && goods.getId() > 0) {
            goods.setUpdateTime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
            goodsMapper.updateSelective(goods);
        } else {
            goods.setCreateTime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
            goodsMapper.insertSelective(goods);
        }
    }

    public PageInfo<GoodsVo> selectGoodsPage(GoodsDto goodsDto) {
        PageHelper.startPage(goodsDto.getPageNo(), goodsDto.getPageSize());
        List<GoodsVo> list = goodsMapper.selectPageList(goodsDto);
        PageInfo<GoodsVo> pageInfo = new PageInfo(list, 5);
        return pageInfo;
    }

    public PageInfo<GoodsVo> selectGameGoodsPage(GameGoodsDto goodsDto) {
        PageHelper.startPage(goodsDto.getPageNo(), goodsDto.getPageSize());
        List<GoodsVo> list = goodsMapper.selectGameGoodsPageList(goodsDto);
        PageInfo<GoodsVo> pageInfo = new PageInfo(list, 5);
        return pageInfo;
    }

    public void deleteGoodsById(String id) {
        goodsMapper.deleteById(id);
    }

    /**
     * 查询商品中已存在的排序数值
     * @return
     */
    public List<Integer> goodsExistSortNo() {
        List<Integer> integers = goodsMapper.existSortNo(null);
        return integers;
    }


}
