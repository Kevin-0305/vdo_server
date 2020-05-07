package com.daily.english.app.controller;

import com.daily.english.app.domain.Notic;
import com.daily.english.app.service.NoticService;
import com.daily.english.app.util.Body;
import com.daily.english.app.util.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "消息")
@Slf4j
@RestController
@RequestMapping(value = "/notic")
public class NoticController {

    @Autowired
    private NoticService noticService;


    @ApiOperation(value = "我的消息列表", notes = "app的消息类表")
    @PostMapping("/getMyNoticList/{userId}/{pageNo}/{limit}")
    public ResponseEntity<Body<Map>> getMyNoticList(@ApiParam("用户id") @PathVariable String userId,
                                                        @ApiParam("页号") @PathVariable Integer pageNo,
                                                        @ApiParam("页行数") @PathVariable Integer limit,
                                                        @ApiParam("是否已读（1是，2否）") String isRead
    ) {
        if(pageNo == null || pageNo <= 0) {
            pageNo = 1;
        }
        int offset = (pageNo-1) * limit;

        Integer noticCount = noticService.getMyNoticListCount(userId,isRead);
        List<Notic> noticList = noticService.getMyNoticList(userId, offset, limit, isRead);
        Map map = new HashMap<>();
        map.put("data",noticList);
        map.put("count",noticCount);
        return ResponseUtil.ok(map);
    }

    @ApiOperation(value = "消息详情", notes = "app消息详情")
    @PostMapping("/geMyNoticInfo/{noticId}")
    public ResponseEntity<Body<Notic>> getMyNoticInfo(@ApiParam("消息id") @PathVariable String noticId
    ) {
        Notic notic = noticService.getMyNoticInfo(noticId);
        notic.setIsRead("1");
        noticService.updateById(notic);
        return ResponseUtil.ok(notic);
    }

    @ApiOperation(value = "未读消息条数", notes = "app未读消息条数")
    @PostMapping("/geMyNoticUnReadCount/{userId}")
    public ResponseEntity<Body<Integer>> getMyNoticUnReadCount(@ApiParam("用户id") @PathVariable String userId
                                                              ) {
        Integer noticCount = noticService.getMyNoticUnReadCount(userId);
        return ResponseUtil.ok(noticCount);
    }
}
