package com.daily.english.app.controller;


import com.daily.english.app.domain.YjSchool;
import com.daily.english.app.dto.PageSearchDto;
import com.daily.english.app.service.YjSchoolService;
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
/**
 * 21世纪杯学校管理
 *
 * @author
 * @create 2019-08-13 09:55
 **/
@Api(tags = "21世纪杯学校管理")
@Slf4j
@RestController
@RequestMapping(value = "/yjSchool")
public class YjSchoolController {

    @Autowired
    private YjSchoolService yjSchoolService;
   

    @ApiOperation(value = "获取活动学校列表", notes = "获取活动学校列表(分页)")
    @GetMapping("/list")
    public ResponseEntity<Body<PageInfo<YjSchool>>> getSchoolPage(PageSearchDto pageSearchDto) {
        return ResponseUtil.ok(yjSchoolService.selectListPage(pageSearchDto));
    }

    @ApiOperation(value = "新增/更新活动学校", notes = "新增/更新活动学校, 不含id为新增, 含id为更新(接口返回保存的学校ID)")
    @PostMapping("/save")
    public ResponseEntity<Body<String>> saveSchool(
            @ApiParam("活动学校体") @RequestBody YjSchool yjSchool) {
        return ResponseUtil.ok(yjSchoolService.saveYjSchool(yjSchool));
    }

    @ApiOperation(value = "获取活动学校详情", notes = "通过活动学校id获得活动学校详情")
    @GetMapping("/info/{id}")
    public ResponseEntity<Body<YjSchool>> getSchool(
            @ApiParam("活动学校ID") @PathVariable String id) {
        YjSchool yjSchool = yjSchoolService.selectYjSchoolById(id);
        return ResponseUtil.ok(yjSchool);
    }


    @ApiOperation(value = "删除活动学校", notes = "删除活动学校（数据硬删）")
    @DeleteMapping("/deleteSchool/{id}")
    public ResponseEntity<Body<Void>> deleteEventInfo(@ApiParam("学校ID") @PathVariable String id) {
        yjSchoolService.deleteById(id);
        return ResponseUtil.ok();
    }

}
