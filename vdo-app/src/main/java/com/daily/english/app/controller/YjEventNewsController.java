package com.daily.english.app.controller;


import com.daily.english.app.domain.YjEventNews;
import com.daily.english.app.dto.YjEventNewsDto;
import com.daily.english.app.service.YjEventInfoService;
import com.daily.english.app.service.YjEventNewsService;
import com.daily.english.app.util.Body;
import com.daily.english.app.util.DateUtil;
import com.daily.english.app.util.ResponseUtil;
import com.daily.english.app.vo.YjEventInfoVo;
import com.daily.english.app.vo.YjEventNewsVo;
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
 * 演讲比赛活动新闻信息
 *
 * @author
 * @create 2019-08-13 09:55
 **/
@Api(tags = "21世纪活动新闻")
@Slf4j
@RestController
@RequestMapping(value = "/eventNews")
public class YjEventNewsController {

    @Autowired
    private YjEventNewsService eventNewsService;
    @Autowired
    private YjEventInfoService eventInfoService;

    @ApiOperation(value = "获取活动新闻列表", notes = "获取活动新闻列表(分页)")
    @GetMapping("/list")
    public ResponseEntity<Body<PageInfo<YjEventNewsVo>>> getEventNewsPage(YjEventNewsDto yjEventNewsDto) {
        return ResponseUtil.ok(eventNewsService.selectEventNewsPage(yjEventNewsDto));
    }

    @ApiOperation(value = "新增/更新活动新闻", notes = "新增/更新活动新闻, 不含id为新增, 含id为更新(接口返回保存的新闻ID)")
    @PostMapping("/save")
    public ResponseEntity<Body<String>> saveEventNews(
            @ApiParam("活动新闻体") @RequestBody YjEventNews eventNews) {
        return ResponseUtil.ok(eventNewsService.saveEventNews(eventNews));
    }

    @ApiOperation(value = "获取活动新闻详情", notes = "通过活动新闻id获得活动新闻详情")
    @GetMapping("/info/{id}")
    public ResponseEntity<Body<YjEventNews>> getEventNews(
            @ApiParam("活动新闻ID") @PathVariable String id) {
        YjEventNews yjEventNews = eventNewsService.selectEventNewsById(id);
        yjEventNews.setEventInfoList(eventInfoService.selectEventInfoVoList(null));
        return ResponseUtil.ok(yjEventNews);
    }

    @ApiOperation(value = "获取活动信息数据", notes = "获取活动信息数据（添加时）")
    @GetMapping("/getEventInfoList")
    public ResponseEntity<Body<List<YjEventInfoVo>>> getEventInfoList() {
        List<YjEventInfoVo> yjEventInfoVos = eventInfoService.selectEventInfoVoList(null);
        return ResponseUtil.ok(yjEventInfoVos);
    }

    @ApiOperation(value = "删除活动新闻", notes = "删除活动新闻（数据硬删）")
    @DeleteMapping("/deleteEventNews/{id}")
    public ResponseEntity<Body<Void>> deleteEventInfo(@ApiParam("新闻ID") @PathVariable String id) {
        eventNewsService.deleteEventNewsById(id);
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
            String pathdir = uploadFolder + "/eventNewsFile/";
            File dest = new File(pathdir, fileName);
            if(!dest.getParentFile().exists()){ //判断文件父目录是否存在
                dest.getParentFile().mkdir();
            }
            FileUtils.copyInputStreamToFile(file.getInputStream(), dest);

            Map<String, String> map = Maps.newHashMap();
            map.put("fileUrl", "/static/image" + "/eventNewsFile/" + fileName);
            return ResponseUtil.ok(map);
        } else {
            return ResponseUtil.of(HttpStatus.OK, "图片为空");
        }
    }


}
