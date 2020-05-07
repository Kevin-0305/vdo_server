package com.daily.english.app.controller;

import com.daily.english.app.condition.CollectCondition;
import com.daily.english.app.condition.DownloadVideoCondition;
import com.daily.english.app.condition.UserRankListCondition;
import com.daily.english.app.domain.*;
import com.daily.english.app.service.*;
import com.daily.english.app.util.Body;
import com.daily.english.app.util.DateUtil;
import com.daily.english.app.util.ResponseUtil;
import com.daily.english.app.vo.HelpVo;
import com.daily.english.app.vo.UserInformation;
import com.daily.english.app.vo.UserRankListVo;
import com.daily.english.app.vo.VideoVo;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author zhc
 * @create 2019-09-18 10:15:11
 **/
@Api(tags = "帮助")
@Slf4j
@RestController
@RequestMapping(value = "/help")
public class HelpController {

    @Autowired
    private HelpService helpService;
    @Autowired
    private MapperFacade mapper;

    @ApiOperation(value = "保存帮助信息", notes = "")
    @PostMapping("/saveHelp")
    public ResponseEntity<Body<Void>> saveHelp(@ApiParam("帮助信息") @RequestBody HelpVo helpVo) {
        Help help = mapper.map(helpVo, Help.class);
        helpService.saveHelp(help);
        return ResponseUtil.of(HttpStatus.OK,"success");
    }

    @ApiOperation(value = "删除帮助", notes = "")
    @PostMapping("/delHelpById/{id}")
    public ResponseEntity<Body<Void>> delHelpById(@ApiParam("删除ID") @PathVariable String id){
        //helpService.getHelpById(Long.parseLong(id));
        helpService.deleteHelpById(Long.parseLong(id));
        return ResponseUtil.of(HttpStatus.OK,"success");
    }

    @ApiOperation(value = "获取帮助列表", notes = "")
    @GetMapping("/queryHelpList")
    public ResponseEntity<Body<List<HelpVo>>> queryHelpList() {
        List<Help> helps = helpService.getHelpList();
        List<HelpVo> vos = mapper.mapAsList(helps,HelpVo.class);
        return ResponseUtil.ok(vos);
    }

}
