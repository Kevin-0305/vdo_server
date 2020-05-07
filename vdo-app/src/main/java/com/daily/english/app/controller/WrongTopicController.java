package com.daily.english.app.controller;

import com.alibaba.fastjson.JSON;
import com.daily.english.app.condition.WrongTopicCondition;
import com.daily.english.app.domain.ExamInfo;
import com.daily.english.app.domain.Node;
import com.daily.english.app.domain.Part;
import com.daily.english.app.domain.WrongTopic;
import com.daily.english.app.service.ExamInfoService;
import com.daily.english.app.service.WrongTopicService;
import com.daily.english.app.util.Body;
import com.daily.english.app.util.ResponseUtil;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Api(tags = "错题本")
@Slf4j
@RestController
@RequestMapping(value = "/wrongTopic")
public class WrongTopicController {

    @Autowired
    private ExamInfoService examInfoService;

    @Autowired
    private WrongTopicService wrongTopicService;

    @ApiOperation(value = "添加错题本", notes = "添加错题本")
    @PostMapping("/add")
    public ResponseEntity<Body<String>> addWrongTopic(
            @ApiParam("用户id") @RequestParam(required = true) String userId,
            @ApiParam("试卷id") @RequestParam(required = true) String examId,
            @ApiParam("题目id列表,用逗号分隔") @RequestParam(required = true) String nodeIds) {
        List<String> nodeIdList = Splitter.on(",").omitEmptyStrings().splitToList(nodeIds);
        ExamInfo examInfo = examInfoService.getExamInfo(examId);
        if (examInfo == null) {
            return ResponseUtil.badRequest("试卷为空");
        }
        List<Part> partList = examInfo.getPartList();
        for (Part part : partList) {
            List<Node> nodeList = part.getNodeList();
            for (Node node : nodeList) {
               // String nodeIdStr = node.getNodeId().toString();
                String questionId = node.getQuestionId() != null ? node.getQuestionId()+"": null;
                String type = part.getPartName();
                if (nodeIdList.contains(questionId)) {
                    String nodeStr = JSON.toJSONString(node);
                    WrongTopic wrongTopic = WrongTopic.builder()
                            .userId(userId)
                            .examId(examId)
                            .nodeId(questionId)
                            .node(nodeStr)
                            .answer(node.getAnswer())
                            .type(type)
                            .state(0)
                            .addDate(new Date())
                            .videoId(examInfo.getVideoId())
                            .build();
                    wrongTopicService.saveWrongTopic(wrongTopic);
                }
            }
        }
        return ResponseUtil.ok("ok");
    }

    @ApiOperation(value = "删除到回收站", notes = "删除到回收站")
    @PostMapping("/del")
    public ResponseEntity<Body<String>> delWrongTopic(
            @ApiParam("用户id") @RequestParam(required = true) String userId,
            @ApiParam("错题id列表,用逗号分隔") @RequestParam(required = true) String  wrongIds) {
        List<String> wrongIdStrList = Splitter.on(",").omitEmptyStrings().splitToList(wrongIds);
        List<Long> wrongIdList = Lists.newArrayList();
        for (String wrongIdStr : wrongIdStrList) {
            wrongIdList.add(Long.parseLong(wrongIdStr));
        }

        WrongTopicCondition condition = WrongTopicCondition.builder()
                .userId(userId).ids(wrongIdList)
                .build();
        List<WrongTopic> wrongTopicList = wrongTopicService.getWrongTopicList(condition);
        for (WrongTopic wrongTopic : wrongTopicList) {
            wrongTopic.setState(1);
            wrongTopicService.modifyWrongTopic(wrongTopic);
        }

        return ResponseUtil.ok("OK");
    }

    @ApiOperation(value = "回收站恢复", notes = "回收站恢复")
    @PostMapping("/resume")
    public ResponseEntity<Body<String>> resumeWrongTopic(
            @ApiParam("用户id") @RequestParam(required = true) String userId,
            @ApiParam("错题id列表,用逗号分隔") @RequestParam(required = true) String  wrongIds) {
        List<String> wrongIdStrList = Splitter.on(",").omitEmptyStrings().splitToList(wrongIds);
        List<Long> wrongIdList = Lists.newArrayList();
        for (String wrongIdStr : wrongIdStrList) {
            wrongIdList.add(Long.parseLong(wrongIdStr));
        }

        WrongTopicCondition condition = WrongTopicCondition.builder()
                .userId(userId).ids(wrongIdList)
                .build();
        List<WrongTopic> wrongTopicList = wrongTopicService.getWrongTopicList(condition);
        for (WrongTopic wrongTopic : wrongTopicList) {
            wrongTopic.setState(0);
            wrongTopicService.modifyWrongTopic(wrongTopic);
        }
        return ResponseUtil.ok("ok");
    }

    @ApiOperation(value = "回收站删除", notes = "回收站删除")
    @PostMapping("/recycle")
    public ResponseEntity<Body<String>> recycleWrongTopic(
            @ApiParam("用户id") @RequestParam(required = true) String userId,
            @ApiParam("错题id列表,用逗号分隔") @RequestParam(required = true) String  wrongIds) {
        List<String> wrongIdStrList = Splitter.on(",").omitEmptyStrings().splitToList(wrongIds);
        for (String wrongIdStr : wrongIdStrList) {
            wrongTopicService.removeWrongTopic(Long.parseLong(wrongIdStr));
        }
        return ResponseUtil.ok("ok");
    }

    @ApiOperation(value = "获取错题本列表", notes = "获取错题本列表(分页)")
    @GetMapping("/page/{pageNo}/{limit}")
    public ResponseEntity<Body<Map<String, Object>>> getVideoPage(
            @ApiParam("页号") @PathVariable Integer pageNo,
            @ApiParam("页行数") @PathVariable Integer limit,
            @ApiParam("题型默认全部,Comprehension/Vocabulary/Grammar") @RequestParam(required = false) String type,
            @ApiParam("用户id") @RequestParam(required = true) String userId,
            @ApiParam("错题本状态, 0:错题本;1:回收站") @RequestParam(required = true) Integer state) {
        if(pageNo == null || pageNo <= 0) {
            pageNo = 1;
        }
        int offset = (pageNo-1) * limit;
        if (state > 1 || state < 0) {
            return ResponseUtil.of(HttpStatus.OK, "错题本状态不对");
        }

        WrongTopicCondition wrongTopicCondition = WrongTopicCondition.builder()
                .type(type)
                .userId(userId).state(state).offset(offset).limit(limit)
                .build();
        List<WrongTopic> wrongTopicList = wrongTopicService.getWrongTopicListByPage(wrongTopicCondition);
        Integer wrongTopicCount = wrongTopicService.getWrongTopicCount(wrongTopicCondition);
        wrongTopicList.forEach(wt->{
            wt.setNodeFormat(JSON.parseObject(wt.getNode(), Node.class));
        });
        Map<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("wrongTopicList", wrongTopicList);
        resultMap.put("count", wrongTopicCount);
        return ResponseUtil.ok(resultMap);
    }
}
