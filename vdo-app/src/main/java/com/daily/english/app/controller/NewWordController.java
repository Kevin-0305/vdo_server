package com.daily.english.app.controller;

import com.daily.english.app.condition.NewWordCondition;
import com.daily.english.app.domain.NewWord;
import com.daily.english.app.service.NewWordService;
import com.daily.english.app.util.Body;
import com.daily.english.app.util.ResponseUtil;
import com.google.common.base.Splitter;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Api(tags = "生词本")
@Slf4j
@RestController
@RequestMapping(value = "/newWord")
public class NewWordController {

    @Autowired
    private NewWordService newWordService;

    @ApiOperation(value = "添加生词本", notes = "添加生词本")
    @PostMapping("/add")
    public ResponseEntity<Body<Void>> addNewWord(
            @ApiParam("用户id") @RequestParam(required = true) String userId,
            @ApiParam("单词列表,用过逗号分隔") @RequestParam(required = true) String words) {
        List<String> wordList = Splitter.on(",").omitEmptyStrings().splitToList(words);
        for (String word : wordList) {
            NewWordCondition condition = NewWordCondition.builder()
                    .userId(userId).word(word)
                    .build();
            NewWord newWord = newWordService.getNewWord(condition);
            if (newWord == null) {
                newWord = NewWord.builder()
                        .userId(userId).word(word).state(0).addDate(new Date())
                        .build();
                newWordService.saveNewWord(newWord);
            }
        }
        return ResponseUtil.ok();
    }

    @ApiOperation(value = "删除到回收站", notes = "删除到回收站")
    @PostMapping("/del")
    public ResponseEntity<Body<Void>> delNewWord(
            @ApiParam("用户id") @RequestParam(required = true) String userId,
            @ApiParam("单词列表,用过逗号分隔") @RequestParam(required = true) String  words) {
        List<String> wordList = Splitter.on(",").omitEmptyStrings().splitToList(words);
        NewWordCondition condition = NewWordCondition.builder()
                .userId(userId).words(wordList)
                .build();
        List<NewWord> newWordList = newWordService.getNewWordList(condition);
        for (NewWord newWord : newWordList) {
            newWord.setState(1);
            newWordService.modifyNewWord(newWord);
        }
        return ResponseUtil.ok();
    }

    @ApiOperation(value = "是否存在该生词", notes = "是否存在该生词")
    @PostMapping("/isExist")
    public ResponseEntity<Body<Boolean>> isExistNewWord(
            @ApiParam("用户id") @RequestParam(required = true) String userId,
            @ApiParam("单词") @RequestParam(required = true) String  word) {
        NewWordCondition condition = NewWordCondition.builder()
                .userId(userId).word(word)
                .build();
        NewWord newWord = newWordService.getNewWord(condition);
        if (newWord != null) {
            return ResponseUtil.ok(true);
        } else {
            return ResponseUtil.ok(false);
        }
    }

    @ApiOperation(value = "回收站恢复", notes = "回收站恢复")
    @PostMapping("/resume")
    public ResponseEntity<Body<Void>> resumeNewWord(
            @ApiParam("用户id") @RequestParam(required = true) String userId,
            @ApiParam("单词列表,用过逗号分隔") @RequestParam(required = true) String  words) {
        List<String> wordList = Splitter.on(",").omitEmptyStrings().splitToList(words);
        NewWordCondition condition = NewWordCondition.builder()
                .userId(userId).words(wordList)
                .build();
        List<NewWord> newWordList = newWordService.getNewWordList(condition);
        for (NewWord newWord : newWordList) {
            newWord.setState(0);
            newWordService.modifyNewWord(newWord);
        }
        return ResponseUtil.ok();
    }

    @ApiOperation(value = "回收站删除", notes = "回收站删除")
    @PostMapping("/recycle")
    public ResponseEntity<Body<Void>> recycleNewWord(
            @ApiParam("用户id") @RequestParam(required = true) String userId,
            @ApiParam("单词列表,用过逗号分隔") @RequestParam(required = true) String  words) {
        List<String> wordList = Splitter.on(",").omitEmptyStrings().splitToList(words);
        NewWordCondition condition = NewWordCondition.builder()
                .userId(userId).words(wordList)
                .build();
        List<NewWord> newWordList = newWordService.getNewWordList(condition);
        for (NewWord newWord : newWordList) {
            newWordService.removeNewWord(newWord.getId());
        }
        return ResponseUtil.ok();
    }

    @ApiOperation(value = "获得最近七天生词本", notes = "获得最近七天生词本")
    @GetMapping("/getNewWordByDay")
    public ResponseEntity<Body<Map<String, Object>>> getNewWordByDay(
            @ApiParam("用户id") @RequestParam(required = true) String userId,
            @ApiParam("生词本状态, 0:生词本;1:回收站") @RequestParam(required = true) Integer state,
            @ApiParam(value = "查询最大日期", example = "2019-10-01") @RequestParam(required = false) String date) {
        Date maxDate = new Date();
        if (StringUtils.isNotBlank(date)) {
            maxDate = DateTime.parse(date).plusDays(1).toDate();
        }

        NewWordCondition newWordCondition = NewWordCondition.builder()
                .userId(userId).state(state).maxDate(maxDate)
                .build();
        Map<String, Object> dateMap = newWordService.getDate(newWordCondition);
        if (dateMap == null) {
            return ResponseUtil.notFound("未查询到数据！");
        }
        return ResponseUtil.ok(dateMap);
    }

    @ApiOperation(value = "获取生词本列表", notes = "获取生词本列表(分页)")
    @GetMapping("/page/{pageNo}/{limit}")
    public ResponseEntity<Body<Map<String, Object>>> getNewWordPage(
            @ApiParam("页号") @PathVariable Integer pageNo,
            @ApiParam("页行数") @PathVariable Integer limit,
            @ApiParam("用户id") @RequestParam(required = true) String userId,
            @ApiParam("生词本状态, 0:生词本;1:回收站") @RequestParam(required = true) Integer state) {
        if(pageNo == null || pageNo <= 0) {
            pageNo = 1;
        }
        int offset = (pageNo-1) * limit;
        if (state > 1 || state < 0) {
            return ResponseUtil.of(HttpStatus.OK, "生词本状态不对");
        }
        NewWordCondition newWordCondition = NewWordCondition.builder()
                .userId(userId).state(state).offset(offset).limit(limit)
                .build();
        List<NewWord> newWordList = newWordService.getNewWordListByPage(newWordCondition);
        Integer newWordCount = newWordService.getNewWordCount(newWordCondition);

        Map<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("newWordList", newWordList);
        resultMap.put("count", newWordCount);
        return ResponseUtil.ok(resultMap);
    }
}
