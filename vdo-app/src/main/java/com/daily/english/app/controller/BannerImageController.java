package com.daily.english.app.controller;


import com.daily.english.app.domain.BannerImage;
import com.daily.english.app.dto.PageSearchDto;
import com.daily.english.app.service.BannerImageService;
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
import java.util.Map;

/**
 * class_name: BannerImageController
 * package: com.daily.english.app.controller
 * describe: banner 管理
 * creat_user: liuzd@chinadailyhk.com
 * creat_date: 2019-08-28
 * creat_time: 10:22
 **/
@Api(tags = "广告管理-Banner")
@Slf4j
@RestController
@RequestMapping(value = "/bannerImage")
public class BannerImageController {

    @Value("${file.staticAccessPath}")
    private String staticAccessPath;

    @Value("${file.uploadFolder}")
    private String uploadFolder;

    @Autowired
    private BannerImageService bannerImageService;

    @ApiOperation(value = "获取banner列表", notes = "获取banner列表(分页)")
    @GetMapping("/list")
    public ResponseEntity<Body<PageInfo<BannerImage>>> getEventNewsPage(PageSearchDto pageSearchDto) {
        return ResponseUtil.ok(bannerImageService.selectBannerImagePage(pageSearchDto));
    }

    @ApiOperation(value = "新增/更新banner", notes = "新增/更新banner, 不含id为新增, 含id为更新")
    @PostMapping("/save")
    public ResponseEntity<Body<Void>> saveEventNews(
            @ApiParam("banner体") @RequestBody BannerImage bannerImage) {
        bannerImageService.saveBannerImage(bannerImage);
        return ResponseUtil.ok();
    }

    @ApiOperation(value = "获取banner详情", notes = "通过banner id获得banner详情")
    @GetMapping("/info/{id}")
    public ResponseEntity<Body<BannerImage>> getEventNews(
            @ApiParam("bannerID") @PathVariable String id) {
        BannerImage bannerImage = bannerImageService.selectBannerImageById(id);
        return ResponseUtil.ok(bannerImage);
    }

    @ApiOperation(value = "删除banner", notes = "删除banner（数据硬删）")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Body<Void>> deleteBannerImage(@ApiParam("banner ID") @PathVariable String id) {
        bannerImageService.deleteBannerImageById(id);
        return ResponseUtil.ok();
    }


    @ApiOperation(value = "上传图片、以及文件", notes = "上传图片")
    @PostMapping("/upload/img")
    public ResponseEntity<Body<Map<String, String>>> uploadImg(@RequestParam("file") MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            String extension = FilenameUtils.getExtension(fileName);
            fileName = DateUtil.getRandom() + "." + extension;
            String pathdir = uploadFolder + "/bannerImage/";
            File dest = new File(pathdir, fileName);
            if (!dest.getParentFile().exists()) { //判断文件父目录是否存在
                dest.getParentFile().mkdir();
            }
            FileUtils.copyInputStreamToFile(file.getInputStream(), dest);

            Map<String, String> map = Maps.newHashMap();
            map.put("fileUrl", "/static/image" + "/bannerImage/" + fileName);
            return ResponseUtil.ok(map);
        } else {
            return ResponseUtil.of(HttpStatus.OK, "图片为空");
        }
    }




}
