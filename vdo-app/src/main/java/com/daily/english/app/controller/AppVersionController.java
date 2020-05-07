package com.daily.english.app.controller;

import com.daily.english.app.domain.AppVersion;
import com.daily.english.app.service.AppVersionrService;
import com.daily.english.app.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "版本控制")
@Slf4j
@RestController
@RequestMapping(value = "/version")
public class AppVersionController {

    @Autowired
    private AppVersionrService appVersionrService;

    @ApiOperation(value = "检查更新", notes = "")
    @GetMapping("/queryVersion/{type}")
    public ResponseEntity<Body<AppVersion>>version (@ApiParam("1=IOS,2=ANDROID") @PathVariable Integer type){
        AppVersion appVersion = appVersionrService.getAppVersionByType(type);
        if(appVersion == null){
            return ResponseUtil.of(HttpStatus.PRECONDITION_FAILED, "暂无版本");
        }
        return ResponseUtil.ok(appVersion);
    }
    @ApiOperation(value = "增加版本", notes = "")
    @PostMapping("/saveVersion")
    public ResponseEntity<Body<Void>> addVersion (@ApiParam("版本") @RequestBody AppVersion appVersion){
        appVersionrService.saveAppVersion(appVersion);
        return ResponseUtil.ok();
    }
    @ApiOperation(value = "版本列表", notes = "")
    @GetMapping("/versionList")
    public ResponseEntity<Body<List<AppVersion>>> versionList (){
        List<AppVersion> appVersionList = appVersionrService.getAppVersionList();
        return ResponseUtil.ok(appVersionList);
    }

}
