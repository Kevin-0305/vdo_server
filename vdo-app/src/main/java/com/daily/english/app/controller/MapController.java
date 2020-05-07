package com.daily.english.app.controller;

import com.daily.english.app.domain.Map;
import com.daily.english.app.service.MapService;
import com.daily.english.app.util.Body;
import com.daily.english.app.util.DateUtil;
import com.daily.english.app.util.ResponseUtil;
import com.daily.english.app.vo.MapVo;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Api(tags = "地图")
@Slf4j
@RestController
@RequestMapping(value = "/map")
public class MapController {
    @Autowired
    private MapService mapService;

    @ApiOperation(value = "新增地图", notes = "新增地图")
    @PostMapping("/save")
    public ResponseEntity<Body<Void>> saveMap(
            @ApiParam("地图体") @RequestBody Map map) {
        mapService.saveMap(map);
        return ResponseUtil.ok();
    }

    @ApiOperation(value = "批量新增地图", notes = "新增地图")
    @PostMapping("/batchSave")
    public ResponseEntity<Body<Void>> batchSaveMap(
            @ApiParam("地图体") @RequestBody HashMap<String, Map> maps) {
        maps.values().forEach(map -> {
                mapService.saveMap(map);
            }
        );
        return ResponseUtil.ok();
    }

    @ApiOperation(value = "更新地图", notes = "新增地图")
    @PostMapping("/update")
    public ResponseEntity<Body<Void>> updateMap(
            @ApiParam("地图体") @RequestBody Map map) {
        mapService.updateMap(map);
        return ResponseUtil.ok();
    }

    @ApiOperation(value = "获取地图通过id", notes = "获取地图通过id")
    @GetMapping("/info/{mapId}")
    public ResponseEntity<Body<MapVo>> getMapInfo(
            @ApiIgnore HttpSession httpSession,
            @ApiParam("地图ID") @PathVariable Long mapId) {
        MapVo mapVo = mapService.getMapByMapID(mapId);
        mapVo.setMapId(mapVo.getId());
        return ResponseUtil.ok(mapVo);
    }

    @ApiOperation(value = "地图列表", notes = "获得地图列表并返回地图对应的考试信息和学员考试成绩")
    @GetMapping("/list")
    public ResponseEntity<Body<List<MapVo>>> getMapList(@ApiParam("地图级别难度") @RequestParam String level,
            @ApiIgnore HttpSession httpSession) {
        Map map = new Map();
        map.setLevel(level);
        List<MapVo> mapVoList = mapService.getMapList(map);
//        User user = (User) httpSession.getAttribute("user");
//        if (user == null) {
//            return ResponseUtil.of(HttpStatus.OK, "Re-login please.");
//        } else {
//            List<MapVo> pathList = mapService.getMapList(user.getId());
//            return ResponseUtil.ok(pathList);
//        }
        return ResponseUtil.ok(mapVoList);
    }

    @Value("${file.staticAccessPath}")
    private String staticAccessPath;

    @Value("${file.uploadFolder}")
    private String uploadFolder;

    @ApiOperation(value = "上传图片", notes = "上传图片")
    @PostMapping("/upload/img")
    public ResponseEntity<Body<java.util.Map<String, String>>> uploadImg(@RequestParam("file") MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            String extension = FilenameUtils.getExtension(fileName);
            fileName = DateUtil.getRandom() + "." + extension;
            String relativePath = "/mapFile/";
            String pathDir = uploadFolder + relativePath;
            File dest = new File(pathDir, fileName);
            if(!dest.getParentFile().exists()){ //判断文件父目录是否存在
                dest.getParentFile().mkdir();
            }
            FileUtils.copyInputStreamToFile(file.getInputStream(), dest);

            java.util.Map<String, String> map = Maps.newHashMap();
            map.put("fileUrl", "/static/image" + relativePath + fileName);
            return ResponseUtil.ok(map);
        } else {
            return ResponseUtil.of(HttpStatus.OK, "图片为空");
        }
    }

}
