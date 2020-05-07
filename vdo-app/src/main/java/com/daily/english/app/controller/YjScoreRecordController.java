package com.daily.english.app.controller;



import com.daily.english.app.domain.YjScoreRecord;
import com.daily.english.app.domain.YjUserBmInfo;
import com.daily.english.app.dto.ScoreRecordSaveDto;
import com.daily.english.app.dto.YjScoreRecordDto;
import com.daily.english.app.service.YjScoreRecordService;
import com.daily.english.app.service.YjUserBmInfoService;
import com.daily.english.app.util.Body;
import com.daily.english.app.util.ResponseUtil;
import com.daily.english.app.vo.YjUserBmInfoJuryVo;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * class_name: YjScoreRecordController
 * package: com.daily.english.app.controller
 * describe: 21世纪杯评委评分
 * creat_user: liuzd@chinadailyhk.com
 * creat_date: 2019-08-20
 * creat_time: 14:08
 **/
@Api(tags = "21世纪杯评委评分")
@Slf4j
@RestController
@RequestMapping(value = "/scoreRecord")
public class YjScoreRecordController {


    @Autowired
    private YjUserBmInfoService userBmInfoService;

    @Autowired
    private YjScoreRecordService scoreRecordService;

    @ApiOperation(value = "评委可评分数据列表", notes = "评委可评分数据列表(分页)")
    @GetMapping("canScoreList")
    public ResponseEntity<Body<PageInfo<YjUserBmInfoJuryVo>>> canScoreList(YjScoreRecordDto scoreRecordDto) {
        PageInfo<YjUserBmInfoJuryVo> yjUserBmInfoVoPageInfo = userBmInfoService.selectUserBmInfoJuryListPage(scoreRecordDto);
        return ResponseUtil.ok(yjUserBmInfoVoPageInfo);
    }

    @ApiOperation(value = "报名信息打开详情", notes = "报名信息打开详情,注意打开详情时会对该数据加上锁状态\n" +
            "在不评分的直接关闭窗口状况下，关闭窗口需要调用unlockBmInfo接口,")
    @GetMapping("/getBmInfoDetail/{id}")
    public synchronized ResponseEntity<Body<YjUserBmInfoJuryVo>> getBmInfoDetail(@ApiParam("活动ID") @PathVariable String id) {
        YjUserBmInfoJuryVo yjUserBmInfoJuryVo = userBmInfoService.selectUserBmInfoJuryDetail(id);
        if (yjUserBmInfoJuryVo != null) {
            if (yjUserBmInfoJuryVo.getLockStatus()==1){
                return ResponseUtil.notFound("已有用户在评论该信息！");
            }else {
                userBmInfoService.lockUserBmInfo(id);
            }
        }
        return ResponseUtil.ok(yjUserBmInfoJuryVo);
    }

    @ApiOperation(value = "关闭报名信息详情", notes = "关闭报名信息详情，关闭时调用解锁报名信息")
    @GetMapping("/unlockBmInfo/{id}")
    public ResponseEntity<Body<Void>> unlockBmInfo(@ApiParam("活动ID") @PathVariable String id) {
        userBmInfoService.unlockBmInfoById(id);
        return ResponseUtil.ok();
    }

    @ApiOperation(value = "评委提交评分", notes = "评委提交评分")
    @PostMapping("submitScoreRecord")
    public ResponseEntity<Body<Void>> submitScoreRecord(ScoreRecordSaveDto saveDto) {
        YjScoreRecord scoreRecord = new YjScoreRecord();
        scoreRecord.setScore(saveDto.getScore());
        scoreRecord.setBmId(saveDto.getBmId());
        scoreRecord.setUserId(saveDto.getUserId());
        int i = scoreRecordService.submitScoreRecord(scoreRecord);
        if (i==-1){
            return ResponseUtil.notFound("没有找到报名信息！");
        }
        if (i==2){
            return ResponseUtil.notFound("该用户已经没有评分次数了！");
        }
        return ResponseUtil.ok();
    }

    @ApiOperation(value = "用户已评分记录", notes = "用户已评分记录(分页)")
    @GetMapping("userRecordList")
    public ResponseEntity<Body<PageInfo<YjScoreRecord>>> userRecordList(YjScoreRecordDto scoreRecord) {
        PageInfo<YjScoreRecord> yjScoreRecordPageInfo = scoreRecordService.selectScoreRecordPage(scoreRecord);
        return ResponseUtil.ok(yjScoreRecordPageInfo);
    }

    @ApiOperation(value = "用户已评分记录详情", notes = "用户已评分记录详情，根据已评分列表主键ID查询")
    @GetMapping("/getUserRecord/{id}")
    public ResponseEntity<Body<YjScoreRecord>> getUserRecord(@ApiParam("评分ID") @PathVariable String id) {
        YjScoreRecord yjScoreRecord = scoreRecordService.selectScoreRecordById(id);
        return ResponseUtil.ok(yjScoreRecord);
    }


}
