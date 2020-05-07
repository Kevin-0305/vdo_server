package com.daily.english.app.controller;


import com.daily.english.app.domain.LaunchImage;
import com.daily.english.app.dto.PageSearchDto;
import com.daily.english.app.service.LaunchImageService;
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
 * 启动图
 *
 * @author
 * @create 2019-08-13 09:55
 **/
@Api(tags = "广告管理-启动图")
@Slf4j
@RestController
@RequestMapping(value = "/launchImage")
public class LaunchImageController {

    @Value("${file.staticAccessPath}")
    private String staticAccessPath;

    @Value("${file.uploadFolder}")
    private String uploadFolder;

    @Autowired
    private LaunchImageService launchImageService;

    @ApiOperation(value = "获取启动图列表", notes = "获取启动图列表(分页)")
    @GetMapping("/list")
    public ResponseEntity<Body<PageInfo<LaunchImage>>> getEventNewsPage(PageSearchDto pageSearchDto) {
        return ResponseUtil.ok(launchImageService.selectLaunchImagePage(pageSearchDto));
    }

    @ApiOperation(value = "APP获取启动图列表", notes = "APP获取启动图列表")
    @GetMapping("/appLaunchImageList")
    public ResponseEntity<Body<List<LaunchImage>>> appLaunchImageData() {
        return ResponseUtil.ok(launchImageService.appLaunchImageData());
    }

    @ApiOperation(value = "新增/更新启动图", notes = "新增/更新启动图, 不含id为新增, 含id为更新")
    @PostMapping("/save")
    public ResponseEntity<Body<Void>> saveEventNews(
            @ApiParam("启动图体") @RequestBody LaunchImage launchImage) {
        launchImageService.saveLaunchImage(launchImage);
        return ResponseUtil.ok();
    }

    @ApiOperation(value = "获取启动图详情", notes = "通过启动图id获得启动图详情")
    @GetMapping("/info/{id}")
    public ResponseEntity<Body<LaunchImage>> getEventNews(
            @ApiParam("启动图ID") @PathVariable String id) {
        LaunchImage launchImage = launchImageService.selectLaunchImageById(id);
        return ResponseUtil.ok(launchImage);
    }

    @ApiOperation(value = "删除启动图", notes = "删除启动图（数据硬删）")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Body<Void>> deleteLaunchImage(@ApiParam("启动图ID") @PathVariable String id) {
        launchImageService.deleteLaunchImageById(id);
        return ResponseUtil.ok();
    }


    @ApiOperation(value = "上传图片、以及文件", notes = "上传图片")
    @PostMapping("/upload/img")
    public ResponseEntity<Body<Map<String, String>>> uploadImg(@RequestParam("file") MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            String extension = FilenameUtils.getExtension(fileName);
            fileName = DateUtil.getRandom() + "." + extension;
            String pathdir = uploadFolder + "/launchImage/";
            File dest = new File(pathdir, fileName);
            if (!dest.getParentFile().exists()) { //判断文件父目录是否存在
                dest.getParentFile().mkdir();
            }
            FileUtils.copyInputStreamToFile(file.getInputStream(), dest);

            Map<String, String> map = Maps.newHashMap();
            map.put("fileUrl", "/static/image" + "/launchImage/" + fileName);
            return ResponseUtil.ok(map);
        } else {
            return ResponseUtil.of(HttpStatus.OK, "图片为空");
        }
    }




}
