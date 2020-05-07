package com.daily.english.app.controller;


import com.daily.english.app.domain.*;
import com.daily.english.app.dto.PageSearchDto;
import com.daily.english.app.service.*;
import com.daily.english.app.util.Body;
import com.daily.english.app.util.DateUtil;
import com.daily.english.app.util.ResponseUtil;
import com.daily.english.app.vo.MapVo;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 宝箱
 *
 * @author
 * @create 2019-08-13 09:55
 **/
@Api(tags = "宝箱")
@Slf4j
@RestController
@RequestMapping(value = "/box")
public class BoxController {

    @Value("${file.staticAccessPath}")
    private String staticAccessPath;

    @Value("${file.uploadFolder}")
    private String uploadFolder;

    @Autowired
    private BoxService boxService;
    @Autowired
    private MapService mapService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapService userMapService;
    @Autowired
    private UserRecordService userRecordService;
    @Autowired
    private UserValueService userValueService;

    @ApiOperation(value = "获取宝箱列表", notes = "获取宝箱列表(分页)")
    @GetMapping("/list")
    public ResponseEntity<Body<PageInfo<Box>>> getBoxPage(PageSearchDto pageSearchDto) {
        return ResponseUtil.ok(boxService.selectBoxPage(pageSearchDto));
    }

    @ApiOperation(value = "获取所有宝箱", notes = "获取所有宝箱")
    @GetMapping("/allList")
    public ResponseEntity<Body<List<Box>>> AllList(PageSearchDto pageSearchDto) {
        return ResponseUtil.ok(boxService.selectBoxList(null));
    }

    @ApiOperation(value = "新增/更新宝箱", notes = "新增/更新宝箱, 不含id为新增, 含id为更新")
    @PostMapping("/save")
    public ResponseEntity<Body<Void>> saveBox(
            @ApiParam("宝箱体") @RequestBody Box box) {
        boxService.saveBox(box);
        return ResponseUtil.ok();
    }

    @ApiOperation(value = "获取宝箱详情", notes = "通过宝箱id获得宝箱详情")
    @GetMapping("/info/{id}")
    public ResponseEntity<Body<Box>> getBox(
            @ApiParam("宝箱ID") @PathVariable String id) {
        Box box = boxService.selectBoxById(id);
        return ResponseUtil.ok(box);
    }

    @ApiOperation(value = "删除宝箱", notes = "删除宝箱（数据硬删）")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Body<Void>> deleteBox(@ApiParam("宝箱ID") @PathVariable String id) {
        boxService.deleteBoxById(id);
        return ResponseUtil.ok();
    }

    @ApiOperation(value = "领取宝箱奖励", notes = "通过宝箱id 节点及 userId获得宝箱奖励")
    @GetMapping("/incentiveByBox/{mapId}/{boxId}/{userId}")
    public ResponseEntity<Body<Void>> incentiveByBoxId(
            @ApiParam("宝箱ID") @PathVariable String boxId,@ApiParam("地图ID") @PathVariable String mapId
            ,@ApiParam("用户ID") @PathVariable String userId) {
        try {
            Box box = boxService.selectBoxById(boxId);
            if(box == null){
               return ResponseUtil.of(HttpStatus.BAD_REQUEST,"宝箱不存在","box is does not exist","寶箱不存在");
            }
        /*    MapVo map = mapService.getMapByMapID(Long.parseLong(mapId) - 1);
            if(map == null || Strings.isEmpty(map.getLevel())){
                return ResponseUtil.of(HttpStatus.BAD_REQUEST,"节点不存在","node is does not exist","節點不存在");
            }*/
            User user = userService.findUserById(userId);
            if(user == null){
                return ResponseUtil.of(HttpStatus.BAD_REQUEST,"用户不存在","user is does not exist","用戶不存在");
            }
        //    UserMap userMap = userMapService.findUserMapById(userId);
         //   String nodes = "";
/*            switch (map.getLevel()){
                case "S1":
                    nodes = userMap.getS1MapScore();
                    break;
                case "S2":
                    nodes = userMap.getS2MapScore();
                    break;
                case "S3":
                    nodes = userMap.getS3MapScore();
                    break;
                case "S4":
                    nodes = userMap.getS4MapScore();
                    break;
                case "S5":
                    nodes = userMap.getS5MapScore();
                    break;
                case "S6":
                    nodes = userMap.getS6MapScore();
                    break;
            }*/
            //历史节点分数
            //com.daily.english.app.domain.Map initMap = com.daily.english.app.domain.Map.builder().level(map.getLevel()).examId(map.getExamId()).build();

            /*int count = mapService.findMapExamIdCount(initMap);
            String [] scores = nodes.split(",");
            if(Strings.isEmpty(nodes)){
                return ResponseUtil.of(HttpStatus.BAD_REQUEST,"节点不存在","node is does not exist","節點不存在");
            }
            //分数小于70分不及格
            if(scores.length < count  || Double.parseDouble(scores[count]) < 70 ){
                return ResponseUtil.of(HttpStatus.BAD_REQUEST,"未解锁","Unlocked","未解鎖");
            }*/
            UserRecord userRecord = UserRecord.builder().type("bamboo").sourceId(mapId+"_"+boxId).userId(Long.parseLong(userId)).build();
            UserRecord record = userRecordService.findUserRecord(userRecord);
            if(record != null){
                return ResponseUtil.of(HttpStatus.BAD_REQUEST,"已领取过了","It has been collected","已領取過了");
            }
            userRecord.setChangeValue(Long.parseLong(box.getBamboo()));
            UserValue userValue = userValueService.findUserValueById(userId);
            userValue.setBamboo(userValue.getBamboo()+userRecord.getChangeValue());
            userValue.setGetBambooToday(userValue.getGetBambooToday() +userRecord.getChangeValue());
            userValue.setGetBambooAll(userValue.getGetBambooAll() + userRecord.getChangeValue());
            userValueService.updateUserValue(userValue);
            userRecord.setBeginValue(userValue.getBamboo());
            userRecord.setTargetValue(userValue.getBamboo());
            userRecord.setType("bamboo");
            userRecordService.insertUserRecord(userRecord);
        } catch (NumberFormatException e) {
            return ResponseUtil.of(HttpStatus.BAD_REQUEST,"节点配置错误","error","節點問题");
        }
        return ResponseUtil.ok();
    }
    @ApiOperation(value = "上传图片、以及文件", notes = "上传图片")
    @PostMapping("/upload/img")
    public ResponseEntity<Body<Map<String, String>>> uploadImg(@RequestParam("file") MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            String extension = FilenameUtils.getExtension(fileName);
            fileName = DateUtil.getRandom() + "." + extension;
            String pathdir = uploadFolder + "/box/";
            File dest = new File(pathdir, fileName);
            if (!dest.getParentFile().exists()) { //判断文件父目录是否存在
                dest.getParentFile().mkdir();
            }
            FileUtils.copyInputStreamToFile(file.getInputStream(), dest);

            Map<String, String> map = Maps.newHashMap();
            map.put("fileUrl", "/static/image" + "/box/" + fileName);
            return ResponseUtil.ok(map);
        } else {
            return ResponseUtil.of(HttpStatus.OK, "图片为空");
        }
    }




}
