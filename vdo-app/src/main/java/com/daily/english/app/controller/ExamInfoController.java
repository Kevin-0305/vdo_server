package com.daily.english.app.controller;

import com.daily.english.app.condition.ExamCommentCondition;
import com.daily.english.app.condition.ExamInfoCondition;
import com.daily.english.app.domain.*;
import com.daily.english.app.service.ExamCommentService;
import com.daily.english.app.service.ExamInfoService;
import com.daily.english.app.service.UserExamInfoService;
import com.daily.english.app.service.UserService;
import com.daily.english.app.util.Body;
import com.daily.english.app.util.DateUtil;
import com.daily.english.app.util.ResponseUtil;
import com.daily.english.app.vo.ExamCommentVo;
import com.daily.english.app.vo.ExamInfoVo;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Api(tags = "试卷")
@Slf4j
@RestController
@RequestMapping(value = "/exam")
public class ExamInfoController {
    @Autowired
    private ExamInfoService examInfoService;
    @Autowired
    private UserExamInfoService userExamInfoService;
    @Autowired
    private UserService userService;
    @Autowired
    private ExamCommentService examCommentService;
    @Autowired
    private MapperFacade mapper;

    @ApiOperation(value = "新增/更新试卷", notes = "新增/更新试卷, 不含id为新增, 含id为更新")
    @PostMapping("/save")
    public ResponseEntity<Body<Void>> saveExamInfo(
            @ApiParam("试卷体") @RequestBody ExamInfo examInfo) {
        String dateTimeStr = DateTime.now().toString("yyyy-MM-dd HH:mm:ss");
        examInfo.setCreateTime(dateTimeStr);
        examInfo.setUpdateTime(dateTimeStr);
        examInfo.setState(0);
        examInfoService.saveExamInfo(examInfo);
        return ResponseUtil.ok();
    }

    @ApiOperation(value = "删除试卷", notes = "通过试卷id删除试卷")
    @DeleteMapping("/del/{examId}")
    public ResponseEntity<Body<Void>> delExam(
            @ApiParam("试卷ID") @PathVariable String examId) {
        examInfoService.deleteExamInfoById(examId);
        return ResponseUtil.ok();
    }

    @ApiOperation(value = "获取试卷列表", notes = "获取试卷列表(未分页)")
    @GetMapping("/list")
    public ResponseEntity<Body<List<ExamInfoVo>>> getExamList() {
        List<ExamInfo> examInfoList = examInfoService.getExamInfoList();
        List<ExamInfoVo> examInfoVoList = mapper.mapAsList(examInfoList, ExamInfoVo.class);
        return ResponseUtil.ok(examInfoVoList);
    }

    @ApiOperation(value = "获取试卷列表", notes = "获取试卷列表(分页)")
    @GetMapping("/page/{pageNo}/{limit}")
    public ResponseEntity<Body<Map<String, Object>>> getExamPage(
            @ApiParam("页号") @PathVariable Integer pageNo,
            @ApiParam("页行数") @PathVariable Integer limit,
            @ApiParam("试卷类型") @RequestParam(required = false) String type,
            @ApiParam("试卷难度") @RequestParam(required = false) String level,
            @ApiParam("试卷hint title") @RequestParam(required = false) String key) {
        if(pageNo == null || pageNo <= 0) {
            pageNo = 1;
        }
        int offset = (pageNo-1) * limit;
        if (StringUtils.isNotBlank(type) && type.equals("0")) {
            type = "";
        }
        if (StringUtils.isNotBlank(level) && level.equals("0")) {
            level = "";
        }
        ExamInfoCondition examInfoCondition = ExamInfoCondition.builder()
                .title(key).examType(type).level(level).offset(offset).limit(limit)
                .build();
        List<ExamInfo> examInfoList = examInfoService.getExamInfoByPage(examInfoCondition);
        List<ExamInfoVo> examInfoVoList = mapper.mapAsList(examInfoList, ExamInfoVo.class);
        Integer examInfoCount = examInfoService.getExamInfoCount(examInfoCondition);

        Map<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("examInfoList", examInfoVoList);
        resultMap.put("count", examInfoCount);
        return ResponseUtil.ok(resultMap);
    }

    @ApiOperation(value = "获取试卷详情", notes = "通过试卷id获得试卷详情")
    @GetMapping("/info/{examId}")
    public ResponseEntity<Body<ExamInfo>> getExam(
            @ApiParam("试卷ID") @PathVariable String examId) {
        ExamInfo examInfo = examInfoService.getExamInfo(examId);
        return ResponseUtil.ok(examInfo);
    }
    @ApiOperation(value = "获取试卷评论列表", notes = "获取评论列表(分页) praiseInfo  当前userid是否点赞")
    @GetMapping("/comment/{pageNo}/{limit}")
    public ResponseEntity<Body<Map<String, Object>>> getCommentPage(
            @ApiParam("页号") @PathVariable Integer pageNo,
            @ApiParam("页行数") @PathVariable Integer limit,
            @ApiParam("用户ID") @RequestParam String userId,
            @ApiParam("试卷ID") @RequestParam String examId) {
        if(pageNo == null || pageNo <= 0) {
            pageNo = 1;
        }
        int offset = (pageNo-1) * limit;
        ExamCommentCondition examCommentCondition = ExamCommentCondition.builder()
                .examId(examId)
                .offset(offset).limit(limit).build();
        List<ExamComment> examComments = examCommentService.findCommentListByExam(examCommentCondition,Long.parseLong(userId));
        List<ExamCommentVo> examCommentVos = mapper.mapAsList(examComments, ExamCommentVo.class);
        Integer count = examCommentService.findCommentListByExamCount(examCommentCondition);
        Map<String, Object> resultMap = Maps.newHashMap();
        if(pageNo == 1){
            List<String> userImgs = new ArrayList<String>();
            List<UserExamInfo> userExamInfoList = userExamInfoService.getUserExamInfoListByExamId(examId);
            userExamInfoList.forEach(ue ->{
                User user = userService.findUserById(ue.getUserId());
                if(user!= null && user.getUserImg() == null){
                    user.setUserImg("");
                }
                if(user != null ){
                    userImgs.add(user.getUserImg());
                    if(userImgs.size() > 6){
                        return;
                    }
                }
            });
            resultMap.put("userImgs",userImgs);
        }
        resultMap.put("commentList", examCommentVos);
        resultMap.put("count", count);
        return ResponseUtil.ok(resultMap);
    }
    @ApiOperation(value = "保存评论", notes = "")
    @PostMapping("/saveComment")
    public ResponseEntity<Body<Void>> saveComment(
            @ApiParam("带*必传") @RequestBody ExamCommentVo  examCommentVo) {
        examCommentService.insertExamComment(examCommentVo);
        return ResponseUtil.ok();
    }
    @ApiOperation(value = "点赞", notes = "")
    @PostMapping("/saveCommentPraise/{commentId}/{userId}")
    public ResponseEntity<Body<Void>> saveCommentPraise(
            @ApiParam("评论ID") @PathVariable String commentId,
            @ApiParam("用户ID") @PathVariable String userId) {
        ExamCommentPraise examCommentPraise = ExamCommentPraise.builder().userId(Long.parseLong(userId)).commentId(Long.parseLong(commentId)).build();
        int count = examCommentService.insertPraise(examCommentPraise);
        if(count == 0){
            return ResponseUtil.of(HttpStatus.PRECONDITION_FAILED, "已赞过了");
        }
        return ResponseUtil.of(HttpStatus.OK,"success");
    }
    @Value("${file.staticAccessPath}")
    private String staticAccessPath;

    @Value("${file.uploadFolder}")
    private String uploadFolder;

    @ApiOperation(value = "上传图片", notes = "上传图片")
    @PostMapping("/upload/img")
    public ResponseEntity<Body<Map<String, String>>> uploadImg(@RequestParam("file") MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            String extension = FilenameUtils.getExtension(fileName);
            fileName = DateUtil.getRandom() + "." + extension;
            String relativePath = "/examImg/";
            String pathDir = uploadFolder + relativePath;
            File dest = new File(pathDir, fileName);
            if(!dest.getParentFile().exists()){ //判断文件父目录是否存在
                dest.getParentFile().mkdir();
            }
            FileUtils.copyInputStreamToFile(file.getInputStream(), dest);

            Map<String, String> map = Maps.newHashMap();
            map.put("fileUrl", "/static/image" + relativePath + fileName);
            return ResponseUtil.ok(map);
        } else {
            return ResponseUtil.of(HttpStatus.OK, "图片为空");
        }
    }

}
