package com.daily.english.app.controller;

import com.daily.english.app.domain.Column;
import com.daily.english.app.service.ColumnService;
import com.daily.english.app.util.Body;
import com.daily.english.app.util.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;
import java.util.List;

@Api(tags = "栏目")
@Slf4j
@RestController
@RequestMapping(value = "/column")
public class ColumnController {

    @Autowired
    private ColumnService columnService;

    @ApiOperation(value = "栏目列表", notes = "获得地图列表并返回地图对应的考试信息和学员考试成绩")
    @GetMapping("/list")
    public ResponseEntity<Body<List<Column>>> getMapList(
            @ApiIgnore HttpSession httpSession) {
        List<Column> columnList = columnService.getColumnList();
        return ResponseUtil.ok(columnList);
    }


    @ApiOperation(value = "新增栏目", notes = "新增栏目")
    @PostMapping("/save")
    public ResponseEntity<Body<Void>> saveColumn(
            @ApiParam("试卷体") @RequestBody Column column) {
        columnService.saveColumn(column);
        return ResponseUtil.ok();
    }

}
