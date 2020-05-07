package com.daily.english.app.controller;


import com.daily.english.app.domain.WordPk;
import com.daily.english.app.dto.PageSearchDto;
import com.daily.english.app.service.WordPkService;
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
 * 单词PK
 *
 * @author
 * @create 2019-08-13 09:55
 **/
@Api(tags = "单词PK")
@Slf4j
@RestController
@RequestMapping(value = "/wordPk")
public class WordPkController {

    @Autowired
    private WordPkService wordPkService;

    @ApiOperation(value = "获取单词PK列表", notes = "获取单词PK列表(分页)")
    @GetMapping("/list")
    public ResponseEntity<Body<PageInfo<WordPk>>> getWordPkPage(PageSearchDto pageSearchDto) {
        return ResponseUtil.ok(wordPkService.selectWordPkPage(pageSearchDto));
    }

    @ApiOperation(value = "新增/更新单词PK", notes = "新增/更新单词PK, 不含id为新增, 含id为更新")
    @PostMapping("/save")
    public ResponseEntity<Body<Void>> saveWordPk(
            @ApiParam("单词PK体") @RequestBody WordPk wordPk) {
        wordPkService.saveWordPk(wordPk);
        return ResponseUtil.ok();
    }

    @ApiOperation(value = "获取单词PK详情", notes = "通过单词PKid获得单词PK详情")
    @GetMapping("/info/{id}")
    public ResponseEntity<Body<WordPk>> getWordPk(
            @ApiParam("单词PK ID") @PathVariable String id) {
        WordPk wordPk = wordPkService.selectWordPkById(id);
        return ResponseUtil.ok(wordPk);
    }

    @ApiOperation(value = "删除单词PK", notes = "删除单词PK（数据硬删）")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Body<Void>> deleteWordPk(@ApiParam("单词PK ID") @PathVariable String id) {
        wordPkService.deleteWordPkById(id);
        return ResponseUtil.ok();
    }


}
