package com.daily.english.app.controller;


import com.daily.english.app.domain.PartnerClass;
import com.daily.english.app.domain.PartnerCode;
import com.daily.english.app.domain.PartnerSchool;
import com.daily.english.app.dto.PageSearchDto;
import com.daily.english.app.dto.PartnerClassDto;
import com.daily.english.app.service.PartnerClassService;
import com.daily.english.app.service.PartnerSchoolService;
import com.daily.english.app.util.Body;
import com.daily.english.app.util.ResponseUtil;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 合作方
 *
 * @author
 * @create 2019-08-13 09:55
 **/
@Api(tags = "合作方管理-班级")
@Slf4j
@RestController
@RequestMapping(value = "/partnerClass")
public class PartnerClassController {


    @Autowired
    private PartnerClassService partnerClassService;
    @Autowired
    private PartnerSchoolService partnerSchoolService;

    @ApiOperation(value = "获取合作方班级列表", notes = "获取合作方班级列表(分页)")
    @GetMapping("/list")
    public ResponseEntity<Body<PageInfo<PartnerClass>>> getPartnerClassPage(PartnerClassDto partnerClassDto) {
        return ResponseUtil.ok(partnerClassService.selectPartnerClassPage(partnerClassDto));
    }

    @ApiOperation(value = "获取合作方老师下拉数据", notes = "获取合作方老师下拉数据")
    @GetMapping("/teacherList")
    public ResponseEntity<Body<List<PartnerCode>>> getPartnerClassPage(String schoolId) {
        return ResponseUtil.ok(partnerClassService.selectTeacherList(schoolId));
    }

    @ApiOperation(value = "获取合作方学校下拉数据", notes = "获取合作方学校下拉数据")
    @GetMapping("/schoolList")
    public ResponseEntity<Body<List<PartnerSchool>>> getPartnerClassPage() {
        return ResponseUtil.ok(partnerSchoolService.selectPartnerSchoolList(null));
    }

    @ApiOperation(value = "新增/更新合作方班级", notes = "新增/更新合作方班级, 不含id为新增, 含id为更新")
    @PostMapping("/save")
    public ResponseEntity<Body<Void>> savePartnerClass(
            @ApiParam("合作方班级体") @RequestBody PartnerClass partnerClass) {
        partnerClassService.savePartnerClass(partnerClass);
        return ResponseUtil.ok();
    }

    @ApiOperation(value = "获取合作方班级详情", notes = "通过合作方班级id获得合作方班级详情")
    @GetMapping("/info/{id}")
    public ResponseEntity<Body<PartnerClass>> getPartnerClass(
            @ApiParam("合作方班级ID") @PathVariable String id) {
        PartnerClass partnerClass = partnerClassService.selectPartnerClassById(id);
        return ResponseUtil.ok(partnerClass);
    }

    @ApiOperation(value = "删除合作方班级", notes = "删除合作方班级（数据硬删）")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Body<Void>> deletePartnerClass(@ApiParam("合作方班级ID") @PathVariable String id) {
        partnerClassService.deletePartnerClassById(id);
        return ResponseUtil.ok();
    }

    @ApiOperation(value = "删除合作方学生code", notes = "删除合作方学生code（数据硬删）")
    @DeleteMapping("/deletePartnerCode/{id}")
    public ResponseEntity<Body<Void>> deletePartnerCode(@ApiParam("学生code ID") @PathVariable String id) {
        partnerClassService.deletePartnerCodeById(id);
        return ResponseUtil.ok();
    }

    @ApiOperation(value = "添加合作方学生code", notes = "添加合作方老师code")
    @PostMapping("/addPartnerCode")
    public ResponseEntity<Body<Void>> addPartnerCode(
            @ApiParam("班级ID") @RequestBody Integer classId) {
        partnerClassService.addPartnerCode(classId);
        return ResponseUtil.ok();
    }






}
