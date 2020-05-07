package com.daily.english.app.controller;


import com.daily.english.app.domain.Email;
import com.daily.english.app.dto.EmailDto;
import com.daily.english.app.service.EmailService;
import com.daily.english.app.service.MailService;
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
 * 演讲比赛邮箱信息
 *
 * @author
 * @create 2019-08-13 09:55
 **/
@Api(tags = "邮箱发送")
@Slf4j
@RestController
@RequestMapping(value = "/email")
public class EmailController {

    @Autowired
    private EmailService emailService;


    @ApiOperation(value = "获取邮箱列表", notes = "获取邮箱列表(分页)")
    @GetMapping("/list")
    public ResponseEntity<Body<PageInfo<Email>>> getEmailPage(EmailDto emailDto) {
        return ResponseUtil.ok(emailService.selectEmailPage(emailDto));
    }

    @ApiOperation(value = "更新邮箱信息", notes = "更新邮箱")
    @PostMapping("/update")
    public ResponseEntity<Body<Void>> saveEmail(
            @ApiParam("邮箱体") @RequestBody Email email) {
        Email email1 = emailService.selectEmailById(email.getId().toString());
        if (email1.getStatus().equals(1)) {
            return ResponseUtil.of(HttpStatus.OK, "该邮件已发送！");
        }
        emailService.saveEmail(email);
        return ResponseUtil.ok();
    }


    @ApiOperation(value = "获取邮箱详情", notes = "通过邮箱id获得邮箱详情")
    @GetMapping("/info/{id}")
    public ResponseEntity<Body<Email>> getEmail(
            @ApiParam("邮箱ID") @PathVariable String id) {
        Email email = emailService.selectEmailById(id);
        return ResponseUtil.ok(email);
    }

    @ApiOperation(value = "软删除", notes = "软删除")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Body<Void>> delete(
            @ApiParam("邮箱ID") @PathVariable String id) {
        emailService.deleteEmailById(id);
        return ResponseUtil.ok();
    }

    @ApiOperation(value = "直接发送邮件", notes = "直接发送邮件并保存邮件信息")
    @PostMapping("/sendEmail")
    public ResponseEntity<Body<Void>> sendEmail(@ApiParam("邮箱体") @RequestBody Email email) {
        emailService.sendEmailSave(email);
        return ResponseUtil.ok();
    }

    @ApiOperation(value = "定时邮件", notes = "定时发送邮件并保存邮件信息")
    @PostMapping("/sendTimeEmail")
    public ResponseEntity<Body<Void>> sendTimeEmail(@ApiParam("邮箱体") @RequestBody Email email) {
        emailService.sendTimeEmailSave(email);
        return ResponseUtil.ok();
    }

    @Value("${file.staticAccessPath}")
    private String staticAccessPath;

    @Value("${file.uploadFolder}")
    private String uploadFolder;

    @ApiOperation(value = "附件上传", notes = "附件上传")
    @PostMapping("/upload/file")
    public ResponseEntity<Body<Map<String, String>>> uploadImg(@RequestParam("file") MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            String extension = FilenameUtils.getExtension(fileName);
            fileName = DateUtil.getRandom() + "." + extension;
            String pathdir = uploadFolder + "/mailFile/";
            File dest = new File(pathdir, fileName);
            if(!dest.getParentFile().exists()){ //判断文件父目录是否存在
                dest.getParentFile().mkdir();
            }
            FileUtils.copyInputStreamToFile(file.getInputStream(), dest);

            Map<String, String> map = Maps.newHashMap();
            map.put("fileUrl", "/static/image" + "/mailFile/" + fileName);
            return ResponseUtil.ok(map);
        } else {
            return ResponseUtil.of(HttpStatus.OK, "图片为空");
        }
    }

}
