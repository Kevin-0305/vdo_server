package com.daily.english.app.controller;

import com.daily.english.app.domain.Map;
import com.daily.english.app.domain.User;
import com.daily.english.app.domain.UserExamInfo;
import com.daily.english.app.service.MapService;
import com.daily.english.app.service.UserExamInfoService;
import com.daily.english.app.service.UserService;
import com.daily.english.app.util.Body;
import com.daily.english.app.util.ResponseUtil;
import com.daily.english.app.vo.MapVo;
import com.daily.english.app.vo.UserMapVo;
import com.daily.english.app.vo.UserVo;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

@Api(tags = "用户地图")
@Slf4j
@RestController
@RequestMapping(value = "/user/map")
public class UserMapController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserExamInfoService userExamInfoService;

    @Autowired
    private MapService mapService;

    @Autowired
    private MapperFacade mapper;

    @ApiOperation(value = "用户地图列表", notes = "用户地图列表")
    @GetMapping("/list/{userId}")
    public ResponseEntity<Body<HashMap<String, Object>>> getMapList(
            @ApiIgnore HttpSession httpSession,
            @ApiParam("地图级别难度") @RequestParam String level,
            @ApiParam("用户 Id") @PathVariable String userId) {
//        User user = (User) httpSession.getAttribute("user");
//        if (user == null) {
//            return ResponseUtil.of(HttpStatus.OK, "Re-login please.");
//        } else {
//            List<MapVo> pathList = mapService.getMapList(user.getId());
//            return ResponseUtil.ok(pathList);
//        }
        Map map = Map.builder().level(level).build();
        List<MapVo> mapVoList = mapService.getMapList(map);
        for (MapVo mapVo : mapVoList) {
            mapVo.setMapId(mapVo.getId());
            UserExamInfo userExamInfo = userExamInfoService.getUserExamInfo(userId, mapVo.getExamId());
            if (userExamInfo == null) {
                mapVo.setScore(0);
            } else {
                mapVo.setScore(Integer.parseInt(userExamInfo.getScore()));
            }
        }
        User user = userService.findUserById(userId);
        UserVo userVo = mapper.map(user, UserVo.class);
        List<UserMapVo> userMapVoList = mapper.mapAsList(mapVoList, UserMapVo.class);
        HashMap<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("maps", userMapVoList);
        resultMap.put("user", userVo);
        return ResponseUtil.ok(resultMap);
    }
}
