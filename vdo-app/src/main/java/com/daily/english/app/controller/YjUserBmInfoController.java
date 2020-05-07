package com.daily.english.app.controller;


import com.daily.english.app.domain.YjSchool;
import com.daily.english.app.domain.YjUserBmInfo;
import com.daily.english.app.dto.YjUserBmInfoDto;
import com.daily.english.app.service.YjUserBmInfoService;
import com.daily.english.app.util.Body;
import com.daily.english.app.util.ResponseUtil;
import com.daily.english.app.vo.YjUserBmInfoVo;
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
 * 演讲比赛用户报名信息
 *
 * @author
 * @create 2019-08-13 09:55
 **/
@Api(tags = "21世纪用户报名信息")
@Slf4j
@RestController
@RequestMapping(value = "/yjUserBmInfo")
public class YjUserBmInfoController {


    @Autowired
    private YjUserBmInfoService userBmInfoService;

    @ApiOperation(value = "获取活动用户报名列表", notes = "获取活动用户报名列表(分页)")
    @GetMapping("/list")
    public ResponseEntity<Body<PageInfo<YjUserBmInfoVo>>> getUserBmInfoPage(YjUserBmInfoDto yjUserBmInfoDto) {
        PageInfo<YjUserBmInfoVo> yjUserBmInfoVoPageInfo = userBmInfoService.selectUserBmInfoPage(yjUserBmInfoDto);
        return ResponseUtil.ok(yjUserBmInfoVoPageInfo);
    }

    @ApiOperation(value = "按条件查询报名信息", notes = "按条件查询报名信息")
    @GetMapping("/queryUserBmInfo")
    public ResponseEntity<Body<List<YjUserBmInfoVo>>> queryUserBmInfo(YjUserBmInfoDto yjUserBmInfoDto) {
        List<YjUserBmInfoVo> yjUserBmInfoVos = userBmInfoService.queryAllBmInfo(yjUserBmInfoDto);
        return ResponseUtil.ok(yjUserBmInfoVos);
    }

    @ApiOperation(value = "新增/更新21世纪杯活动用户报名", notes = "新增/更新21世纪杯活动用户报名, 不含id为新增, 含id为更新")
    @PostMapping("/save")
    public ResponseEntity<Body<Void>> saveUserBmInfo(
            @ApiParam("活动用户报名体") @RequestBody YjUserBmInfo userBmInfo) {
        userBmInfoService.saveUserBmInfo(userBmInfo);
        return ResponseUtil.ok();
    }

    @ApiOperation(value = "获取活动用户报名详情", notes = "通过活动用户报名id获得活动配置详情")
    @GetMapping("/info/{id}")
    public ResponseEntity<Body<YjUserBmInfo>> getUserBmInfo(
            @ApiParam("活动用户报名ID") @PathVariable String id) {
        YjUserBmInfo yjUserBmInfo = userBmInfoService.selectUserBmInfoById(id);
        return ResponseUtil.ok(yjUserBmInfo);
    }

    @ApiOperation(value = "获取所有学校列表", notes = "获取所有学校列表")
    @GetMapping("/schoolList")
    public ResponseEntity<Body<List<YjSchool>>> getAllSchoolList() {
        List<YjSchool> schools = userBmInfoService.selectAllSchool();
        return ResponseUtil.ok(schools);
    }


}
