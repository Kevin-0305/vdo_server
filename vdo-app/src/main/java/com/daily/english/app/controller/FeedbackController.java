package com.daily.english.app.controller;


import com.daily.english.app.domain.Feedback;
import com.daily.english.app.dto.FeedbackDto;
import com.daily.english.app.service.FeedbackService;
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
import org.joda.time.DateTime;
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
 * 用户反馈
 *
 * @author
 * @create 2019-08-13 09:55
 **/
@Api(tags = "用户反馈")
@Slf4j
@RestController
@RequestMapping(value = "/feedback")
public class FeedbackController {

    @Value("${file.staticAccessPath}")
    private String staticAccessPath;

    @Value("${file.uploadFolder}")
    private String uploadFolder;

    @Autowired
    private FeedbackService feedbackService;

    @ApiOperation(value = "获取用户反馈列表", notes = "获取用户反馈列表(分页)")
    @GetMapping("/list")
    public ResponseEntity<Body<PageInfo<Feedback>>> getFeedbackPage(FeedbackDto feedbackDto) {
        return ResponseUtil.ok(feedbackService.selectFeedbackPage(feedbackDto));
    }

    @ApiOperation(value = "新增/更新用户反馈", notes = "新增/更新用户反馈, 不含id为新增, 含id为更新")
    @PostMapping("/save")
    public ResponseEntity<Body<Void>> saveFeedback(
            @ApiParam("用户反馈体") @RequestBody Feedback feedback) {
        feedbackService.saveFeedback(feedback);
        return ResponseUtil.ok();
    }

    @ApiOperation(value = "获取用户反馈详情", notes = "通过用户反馈id获得用户反馈详情")
    @GetMapping("/info/{id}")
    public ResponseEntity<Body<Feedback>> getFeedback(
            @ApiParam("用户反馈ID") @PathVariable String id) {
        Feedback feedback = feedbackService.selectFeedbackById(id);
        if (feedback != null){
            if (!feedback.getStatus().equals(1)){
                Feedback update = Feedback.builder()
                        .id(Integer.parseInt(id))
                        .status(2)
                        .build();
                feedbackService.saveFeedback(update);//点开详情更改成处理中状态
            }
        }
        return ResponseUtil.ok(feedback);
    }

    @ApiOperation(value = "完成处理", notes = "完成处理反馈保存")
    @PostMapping("/completeSave")
    public ResponseEntity<Body<Void>> completeSave(
            @ApiParam("用户反馈ID") @RequestParam(required = true) String id) {
        Feedback feedback = feedbackService.selectFeedbackById(id);
        if (!feedback.getStatus().equals(1)){
            Feedback update = Feedback.builder().id(Integer.parseInt(id))
                    .status(1)
                    .processingTime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"))
                    .build();
            feedbackService.saveFeedback(update);//点击完成按钮改成已处理状态
        }
        return ResponseUtil.ok();
    }


    @ApiOperation(value = "上传图片、以及文件", notes = "上传图片")
    @PostMapping("/upload/img")
    public ResponseEntity<Body<Map<String, String>>> uploadImg(@RequestParam("file") MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            String extension = FilenameUtils.getExtension(fileName);
            fileName = DateUtil.getRandom() + "." + extension;
            String pathdir = uploadFolder + "/feedback/";
            File dest = new File(pathdir, fileName);
            if (!dest.getParentFile().exists()) { //判断文件父目录是否存在
                dest.getParentFile().mkdir();
            }
            FileUtils.copyInputStreamToFile(file.getInputStream(), dest);

            Map<String, String> map = Maps.newHashMap();
            map.put("fileUrl", "/static/image" + "/feedback/" + fileName);
            return ResponseUtil.ok(map);
        } else {
            return ResponseUtil.of(HttpStatus.OK, "图片为空");
        }
    }




}
