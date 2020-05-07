package com.daily.english.app.controller;


import com.daily.english.app.domain.Box;
import com.daily.english.app.domain.Prop;
import com.daily.english.app.dto.PageSearchDto;
import com.daily.english.app.service.PropService;
import com.daily.english.app.util.Body;
import com.daily.english.app.util.DateUtil;
import com.daily.english.app.util.ResponseUtil;
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
 * 道具
 *
 * @author
 * @create 2019-08-13 09:55
 **/
@Api(tags = "道具")
@Slf4j
@RestController
@RequestMapping(value = "/prop")
public class PropController {

    @Value("${file.staticAccessPath}")
    private String staticAccessPath;

    @Value("${file.uploadFolder}")
    private String uploadFolder;

    @Autowired
    private PropService propService;

    @ApiOperation(value = "获取道具列表", notes = "获取道具列表(分页)")
    @GetMapping("/list")
    public ResponseEntity<Body<PageInfo<Prop>>> getPropPage(PageSearchDto pageSearchDto) {
        return ResponseUtil.ok(propService.selectPropPage(pageSearchDto));
    }

    @ApiOperation(value = "获取所有道具", notes = "获取所有道具")
    @GetMapping("/allList")
    public ResponseEntity<Body<List<Prop>>> AllList() {
        return ResponseUtil.ok(propService.selectPropList(null));
    }

    @ApiOperation(value = "新增/更新道具", notes = "新增/更新道具, 不含id为新增, 含id为更新")
    @PostMapping("/save")
    public ResponseEntity<Body<Void>> saveProp(
            @ApiParam("道具体") @RequestBody Prop prop) {
        propService.saveProp(prop);
        return ResponseUtil.ok();
    }

    @ApiOperation(value = "获取道具详情", notes = "通过道具id获得道具详情")
    @GetMapping("/info/{id}")
    public ResponseEntity<Body<Prop>> getProp(
            @ApiParam("道具ID") @PathVariable String id) {
        Prop prop = propService.selectPropById(id);
        return ResponseUtil.ok(prop);
    }

    @ApiOperation(value = "删除道具", notes = "删除道具（数据硬删）")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Body<Void>> deleteProp(@ApiParam("道具ID") @PathVariable String id) {
        propService.deletePropById(id);
        return ResponseUtil.ok();
    }


    @ApiOperation(value = "上传图片、以及文件", notes = "上传图片")
    @PostMapping("/upload/img")
    public ResponseEntity<Body<Map<String, String>>> uploadImg(@RequestParam("file") MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            String extension = FilenameUtils.getExtension(fileName);
            fileName = DateUtil.getRandom() + "." + extension;
            String pathdir = uploadFolder + "/prop/";
            File dest = new File(pathdir, fileName);
            if (!dest.getParentFile().exists()) { //判断文件父目录是否存在
                dest.getParentFile().mkdir();
            }
            FileUtils.copyInputStreamToFile(file.getInputStream(), dest);

            Map<String, String> map = Maps.newHashMap();
            map.put("fileUrl", "/static/image" + "/prop/" + fileName);
            return ResponseUtil.ok(map);
        } else {
            return ResponseUtil.of(HttpStatus.OK, "图片为空");
        }
    }




}
