package com.daily.english.app.controller;

import com.daily.english.app.domain.YjEventInfo;
import com.daily.english.app.dto.YjEventInfoDto;
import com.daily.english.app.service.YjEventInfoService;
import com.daily.english.app.util.Body;
import com.daily.english.app.util.DateUtil;
import com.daily.english.app.util.ResponseUtil;
import com.daily.english.app.vo.YjEventInfoVo;
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
 * 演讲比赛活动配置信息
 *
 * @author
 * @create 2019-08-13 09:55
 **/
@Api(tags = "21世纪活动配置")
@Slf4j
@RestController
@RequestMapping(value = "/eventInfo")
public class YjEventInfoController {

    @Autowired
    private YjEventInfoService eventInfoService;

    @ApiOperation(value = "新增/更新21世纪杯活动配置", notes = "新增/更新21世纪杯活动配置, 不含id为新增, 含id为更新")
    @PostMapping("/save")
    public ResponseEntity<Body<Void>> saveEventInfo(
            @ApiParam("活动配置体") @RequestBody YjEventInfo eventInfo) {
        eventInfoService.saveEventInfo(eventInfo);
        return ResponseUtil.ok();
    }

    @ApiOperation(value = "获取活动配置详情", notes = "通过活动配置id获得活动配置详情")
    @GetMapping("/info/{id}")
    public ResponseEntity<Body<YjEventInfo>> getEventInfo(
            @ApiParam("活动ID") @PathVariable String id) {
        YjEventInfo examInfo = eventInfoService.selectEventInfoById(id);
        return ResponseUtil.ok(examInfo);
    }

    @ApiOperation(value = "获取活动配置列表", notes = "获取活动配置列表(分页)")
    @GetMapping("/list")
    public ResponseEntity<Body<PageInfo<YjEventInfoVo>>> getEventInfoPage(YjEventInfoDto yjEventInfoDto) {
        return ResponseUtil.ok(eventInfoService.findEventInfoPage(yjEventInfoDto));
    }

    @ApiOperation(value = "上架21世纪杯活动配置", notes = "上架21世纪杯活动配置")
    @PutMapping("/upEventInfo/{id}")
    public ResponseEntity<Body<Void>> upEventInfo(@ApiParam("活动ID") @PathVariable String id) {
        eventInfoService.updateEventInfoStatus(id, 1);
        return ResponseUtil.ok();
    }

    @ApiOperation(value = "下架21世纪杯活动配置", notes = "下架21世纪杯活动配置")
    @PutMapping("/downEventInfo/{id}")
    public ResponseEntity<Body<Void>> downEventInfo(@ApiParam("活动ID") @PathVariable String id) {
        eventInfoService.updateEventInfoStatus(id, 2);
        return ResponseUtil.ok();
    }

    @ApiOperation(value = "删除21世纪杯活动配置", notes = "删除21世纪杯活动配置，数据软删")
    @PutMapping("/deleteEventInfo/{id}")
    public ResponseEntity<Body<Void>> deleteEventInfo(@ApiParam("活动ID") @PathVariable String id) {
        eventInfoService.updateEventInfoStatus(id, -1);
        return ResponseUtil.ok();
    }

    @Value("${file.staticAccessPath}")
    private String staticAccessPath;

    @Value("${file.uploadFolder}")
    private String uploadFolder;

    @ApiOperation(value = "上传图片、以及文件", notes = "上传图片")
    @PostMapping("/upload/img")
    public ResponseEntity<Body<Map<String, String>>> uploadImg(@RequestParam("file") MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            String extension = FilenameUtils.getExtension(fileName);
            fileName = DateUtil.getRandom() + "." + extension;
            String relativePath = "/videoI21st/";
            String pathdir = uploadFolder + relativePath;
            File dest = new File(pathdir, fileName);
            if (!dest.getParentFile().exists()) { //判断文件父目录是否存在
                dest.getParentFile().mkdir();
            }
            FileUtils.copyInputStreamToFile(file.getInputStream(), dest);

            Map<String, String> map = Maps.newHashMap();
            map.put("fileUrl", "/static/image" + relativePath + fileName);
            return ResponseUtil.ok(map);
        } else {
            return ResponseUtil.of(HttpStatus.OK, "文件为空");
        }
    }

    @ApiOperation(value = "上传视频", notes = "上传视频")
    @PostMapping("/upload/videoFile")
    public ResponseEntity<Body<Map<String, String>>> uploadVideoFile(@RequestParam("file") MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            String extension = FilenameUtils.getExtension(fileName);
            fileName = DateUtil.getRandom() + "." + extension;
            String relativePath = "/videoI21st/video/";
            String pathdir = uploadFolder + relativePath;
            File dest = new File(pathdir, fileName);
            if (!dest.getParentFile().exists()) { //判断文件父目录是否存在
                dest.getParentFile().mkdir();
            }
            FileUtils.copyInputStreamToFile(file.getInputStream(), dest);
            Map<String, String> map = Maps.newHashMap();
            map.put("fileUrl", "/static/image" + relativePath + fileName);
            return ResponseUtil.ok(map);
        } else {
            return ResponseUtil.of(HttpStatus.OK, "文件为空");
        }
    }

    @ApiOperation(value = "删除文件", notes = "删除文件")
    @PostMapping("/delete/file")
    public ResponseEntity<Body<Map<String, String>>> uploadVideoFile(@RequestParam("filePath") String filePath) throws IOException {
        FileUtils.deleteQuietly(new File(uploadFolder + filePath.replaceAll("/static/image", "")));
        Map<String, String> map = Maps.newHashMap();
        map.put("fileUrl", filePath);
        return ResponseUtil.ok(map);
    }


}
