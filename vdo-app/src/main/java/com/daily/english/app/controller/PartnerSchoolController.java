package com.daily.english.app.controller;


import com.daily.english.app.domain.PartnerSchool;
import com.daily.english.app.dto.PageSearchDto;
import com.daily.english.app.service.BaseCodeService;
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
@Api(tags = "合作方管理-学校")
@Slf4j
@RestController
@RequestMapping(value = "/partnerSchool")
public class PartnerSchoolController {


    @Autowired
    private PartnerSchoolService partnerSchoolService;
    @Autowired
    private BaseCodeService baseCodeService;

    @ApiOperation(value = "获取合作方学校列表", notes = "获取合作方学校列表(分页)")
    @GetMapping("/list")
    public ResponseEntity<Body<PageInfo<PartnerSchool>>> getPartnerSchoolPage(PageSearchDto pageSearchDto) {
        return ResponseUtil.ok(partnerSchoolService.selectPartnerSchoolPage(pageSearchDto));
    }

    @ApiOperation(value = "获取所有合作方学校", notes = "获取所有合作方学校")
    @GetMapping("/allList")
    public ResponseEntity<Body<List<PartnerSchool>>> AllList() {
        return ResponseUtil.ok(partnerSchoolService.selectPartnerSchoolList(null));
    }

    @ApiOperation(value = "新增/更新合作方学校", notes = "新增/更新合作方学校, 不含id为新增, 含id为更新")
    @PostMapping("/save")
    public ResponseEntity<Body<Void>> savePartnerSchool(
            @ApiParam("合作方学校体") @RequestBody PartnerSchool partnerSchool) {
        partnerSchoolService.savePartnerSchool(partnerSchool);
        return ResponseUtil.ok();
    }

    @ApiOperation(value = "获取合作方学校详情", notes = "通过合作方学校id获得合作方学校详情")
    @GetMapping("/info/{id}")
    public ResponseEntity<Body<PartnerSchool>> getPartnerSchool(
            @ApiParam("合作方学校ID") @PathVariable String id) {
        PartnerSchool partnerSchool = partnerSchoolService.selectPartnerSchoolById(id);
        return ResponseUtil.ok(partnerSchool);
    }

    @ApiOperation(value = "删除合作方学校", notes = "删除合作方学校（数据硬删）")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Body<Void>> deletePartnerSchool(@ApiParam("合作方学校ID") @PathVariable String id) {
        partnerSchoolService.deletePartnerSchoolById(id);
        return ResponseUtil.ok();
    }

    @ApiOperation(value = "删除合作方老师code", notes = "删除合作方老师code（数据硬删）")
    @DeleteMapping("/deletePartnerCode/{id}")
    public ResponseEntity<Body<Void>> deletePartnerCode(@ApiParam("老师code ID") @PathVariable String id) {
        partnerSchoolService.deletePartnerCodeById(id);
        return ResponseUtil.ok();
    }

    @ApiOperation(value = "添加合作方老师code", notes = "添加合作方老师code")
    @PostMapping("/addPartnerCode")
    public ResponseEntity<Body<Void>> addPartnerCode(
            @ApiParam("学校ID") @RequestBody Integer schoolId) {
        partnerSchoolService.addPartnerCode(schoolId);
        return ResponseUtil.ok();
    }


    @ApiOperation(value = "生成码-初始化code码接口误用", notes = "初始化code码接口误用")
    @PostMapping("/generatorBaseCode")
    public ResponseEntity<Body<Void>> generatorBaseCode(
            @ApiParam("生成数量") @RequestBody Integer count) {
        baseCodeService.generatorBaseCode(count);
        return ResponseUtil.ok();
    }




}
