package com.daily.english.app.controller;


import com.daily.english.app.domain.Goods;
import com.daily.english.app.dto.GoodsDto;
import com.daily.english.app.service.GoodsService;
import com.daily.english.app.util.Body;
import com.daily.english.app.util.DateUtil;
import com.daily.english.app.util.ResponseUtil;
import com.daily.english.app.vo.GoodsVo;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 商品
 *
 * @author
 * @create 2019-08-13 09:55
 **/
@Api(tags = "商品")
@Slf4j
@RestController
@RequestMapping(value = "/goods")
public class GoodsController {

    @Value("${file.staticAccessPath}")
    private String staticAccessPath;

    @Value("${file.uploadFolder}")
    private String uploadFolder;

    @Autowired
    private GoodsService goodsService;

    @ApiOperation(value = "获取商品列表", notes = "获取商品列表(分页)")
    @GetMapping("/list")
    public ResponseEntity<Body<PageInfo<GoodsVo>>> getGoodsPage(GoodsDto goodsDto) {
        return ResponseUtil.ok(goodsService.selectGoodsPage(goodsDto));
    }

    @ApiOperation(value = "所有商品已存在数值列表", notes = "所有商品已存在数值列表")
    @GetMapping("/goodsExistSortNo")
    public ResponseEntity<Body<List<Integer>>> goodsExistSortNo() {
        return ResponseUtil.ok(goodsService.goodsExistSortNo());
    }

    @ApiOperation(value = "新增/更新商品", notes = "新增/更新商品, 不含id为新增, 含id为更新")
    @PostMapping("/save")
    public ResponseEntity<Body<Void>> saveGoods(
            @ApiParam("商品体") @RequestBody Goods goods) {
        goodsService.saveGoods(goods);
        return ResponseUtil.ok();
    }

    @ApiOperation(value = "获取商品详情", notes = "通过商品id获得商品详情")
    @GetMapping("/info/{id}")
    public ResponseEntity<Body<Goods>> getGoods(
            @ApiParam("商品ID") @PathVariable String id) {
        Goods goods = goodsService.selectGoodsById(id);
        return ResponseUtil.ok(goods);
    }

    @ApiOperation(value = "删除商品", notes = "删除商品（数据硬删）")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Body<Void>> deleteEventInfo(@ApiParam("商品ID") @PathVariable String id) {
        goodsService.deleteGoodsById(id);
        return ResponseUtil.ok();
    }


    @ApiOperation(value = "上传图片、以及文件", notes = "上传图片")
    @PostMapping("/upload/img")
    public ResponseEntity<Body<Map<String, String>>> uploadImg(@RequestParam("file") MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            String extension = FilenameUtils.getExtension(fileName);
            fileName = DateUtil.getRandom() + "." + extension;
            String pathdir = uploadFolder + "/goods/";
            File dest = new File(pathdir, fileName);
            if (!dest.getParentFile().exists()) { //判断文件父目录是否存在
                dest.getParentFile().mkdir();
            }
            FileUtils.copyInputStreamToFile(file.getInputStream(), dest);

            Map<String, String> map = Maps.newHashMap();
            map.put("fileUrl", "/static/image" + "/goods/" + fileName);
            return ResponseUtil.ok(map);
        } else {
            return ResponseUtil.of(HttpStatus.OK, "图片为空");
        }
    }




}
