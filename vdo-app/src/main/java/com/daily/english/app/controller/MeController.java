package com.daily.english.app.controller;

import com.daily.english.app.condition.CollectCondition;
import com.daily.english.app.condition.DownloadVideoCondition;
import com.daily.english.app.condition.UserRankListCondition;
import com.daily.english.app.domain.*;
import com.daily.english.app.service.*;
import com.daily.english.app.util.Body;
import com.daily.english.app.util.DateUtil;
import com.daily.english.app.util.ResponseUtil;
import com.daily.english.app.vo.UserInformation;
import com.daily.english.app.vo.UserRankListVo;
import com.daily.english.app.vo.VideoVo;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.logging.log4j.util.Strings;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.Map;

/**
 * 个人中心
 *
 * @author zhc
 * @create 2019-09-18 10:15:11
 **/
@Api(tags = "个人中心")
@Slf4j
@RestController
@RequestMapping(value = "/me")
public class MeController {
    @Autowired
    private DownloadVideoService downloadVideoService;
    @Autowired
    private VideoService videoService;
    @Autowired
    private UserRankListService userRankListService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserValueService userValueService;
    @Autowired
    private UserStudyService userStudyeService;
    @Autowired
    private UserMapService userMapService;
    @Autowired
    private UserSignService userSignService;
    @Autowired
    private MapperFacade mapper;

    @ApiOperation(value = "提交完善资料信息", notes = "id 为用户ID")
    @PostMapping("/saveInformation")
    public ResponseEntity<Body<Void>> saveInformation(@ApiParam("用户信息") @RequestBody UserInformation ui) {
        User user = userService.findUserById(ui.getId());
        if (user == null) {
            return ResponseUtil.of(HttpStatus.OK, "Re-login please.");
        }
        userService.modifyUserInformation(User.builder().id(ui.getId()).sex(ui.getSex())
                .birthday(ui.getBirthday()).purpose(ui.getPurpose()).nickname(ui.getNickname())
                .introduction(ui.getIntroduction()).build());
        return ResponseUtil.of(HttpStatus.OK,"success");
    }
    @ApiOperation(value = "语言设置", notes = "language(1/2/3)->简体中文=1，繁体中文=2，英文=3")
    @PostMapping("/language/{userId}/{language}")
    public ResponseEntity<Body<Void>> saveInformation(@ApiParam("用户ID") @PathVariable String userId,
                                                      @ApiParam("默认2") @PathVariable Integer language) {
        User user = userService.findUserById(userId);
        if (user == null) {
            return ResponseUtil.of(HttpStatus.OK, "Re-login please.");
        }
        if(language <= 0 || language > 3){
            language = 2;
        }
        userService.modifyUserInformation(User.builder().id(user.getId()).language(language).build());
        return ResponseUtil.of(HttpStatus.OK,"success");
    }
    @ApiOperation(value = "退出帐号", notes = "传userId")
    @GetMapping("/exit/{userId}")
    public ResponseEntity<Body<Void>> exit(@ApiParam("用户ID") @PathVariable String userId,
                                           @ApiIgnore HttpSession httpSession){
        User user = userService.findUserById(userId);
        if (user == null) {
            return ResponseUtil.of(HttpStatus.OK, "Re-login please.");
        }
        httpSession.setAttribute("user", null);
        return ResponseUtil.of(HttpStatus.OK,"success");
    }
    @ApiOperation(value = "获取收藏视频列表", notes = "移动端 获取收藏视频列表(分页)")
    @GetMapping("/page/{userId}/{pageNo}/{limit}")
    public ResponseEntity<Body<Map<String, Object>>> getVideoCollectPage(
            @ApiParam("用户ID") @PathVariable String userId,
            @ApiParam("页号") @PathVariable Integer pageNo,
            @ApiParam("类型默认1; 1=video/2=challenge") @RequestParam(required = false) Integer flag,
            @ApiParam("页行数") @PathVariable Integer limit) {
        User user = userService.findUserById(userId);
        if (user == null) {
            return ResponseUtil.of(HttpStatus.OK, "Re-login please.");
        }
        if(pageNo == null || pageNo <= 0) {
            pageNo = 1;
        }
        int offset = (pageNo-1) * limit;
        String type = "video";
        if(flag == 2){
            type = "challenge";
        }
        CollectCondition collectCondition = CollectCondition.builder().uid(user.getId()).type(type)
                .offset(offset).limit(limit).build();
        List<Video> videoList = videoService.getVideoCollectListByPage(collectCondition);
        List<VideoVo> videoVoList = mapper.mapAsList(videoList, VideoVo.class);
        Integer collectCount = videoService.findVideoCollectListByPageCount(collectCondition);

        Map<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("videoList", videoVoList);
        resultMap.put("count", collectCount);
        return ResponseUtil.ok(resultMap);
    }

    @ApiOperation(value = "批量删除收藏视频", notes = "硬删 批量删除 用户id,视频id多个用逗号隔开(如: 10012,1255,1,2,5)")
    @DeleteMapping("/batchDelVideoCollects/{userId}/{ids}")
    public ResponseEntity<Body<Void>> batchDelVideoCollects(@ApiParam("用户id") @PathVariable String userId,
                                                            @ApiParam("视频id,多个用逗号隔开")  @PathVariable String ids) {
        try {
            User user = userService.findUserById(userId);
            if (user == null) {
                return ResponseUtil.of(HttpStatus.PRECONDITION_FAILED, "Re-login please.");
            }
            String[] collects = ids.split(",");
            if(collects.length == 0){
                return ResponseUtil.of(HttpStatus.BAD_REQUEST, "collect id length is zero.");
            }
            videoService.removeBatchVideoCollect(Collect.builder().uid(Integer.parseInt(user.getId())).type("collect")
                    .ids(Arrays.asList(collects)).build());
            return ResponseUtil.of(HttpStatus.OK,"success");
        } catch (Exception e) {
            return ResponseUtil.badRequest(e.toString());
        }
    }
    @ApiOperation(value = "保存下载视频记录", notes = "传userId")
    @PostMapping("/saveDownloadVideo/{userId}/{videoId}")
    public ResponseEntity<Body<Void>> exit(@ApiParam("用户ID") @PathVariable String userId,
                                           @ApiParam("视频ID") @PathVariable String videoId){
        User user = userService.findUserById(userId);
        if (user == null) {
            return ResponseUtil.of(HttpStatus.OK, "Re-login please.");
        }
        downloadVideoService.saveVideoDownload(DownloadVideo.builder().userId(userId).videoId(videoId).build());
        return ResponseUtil.of(HttpStatus.OK,"success");
    }

    @ApiOperation(value = "获取下载视频列表", notes = "移动端 获取下载视频列表(分页)")
    @GetMapping("/downloadVideo/{userId}/{pageNo}/{limit}")
    public ResponseEntity<Body<Map<String, Object>>> getVideoDownloadPage(
            @ApiParam("用户ID") @PathVariable String userId,
            @ApiParam("页号") @PathVariable Integer pageNo,
            @ApiParam("页行数") @PathVariable Integer limit) {
        User user = userService.findUserById(userId);
        if (user == null) {
            return ResponseUtil.of(HttpStatus.OK, "Re-login please.");
        }
        if(pageNo == null || pageNo <= 0) {
            pageNo = 1;
        }
        int offset = (pageNo-1) * limit;
        DownloadVideoCondition dvc = DownloadVideoCondition.builder().userId(userId)
                .offset(offset).limit(limit).build();
        List<Video> videoList = downloadVideoService.getVideoCollectListByPage(dvc);
        List<VideoVo> videoVoList = mapper.mapAsList(videoList, VideoVo.class);
        Integer collectCount = downloadVideoService.getVideoCollectListByPageCount(dvc);

        Map<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("videoList", videoVoList);
        resultMap.put("count", collectCount);
        return ResponseUtil.ok(resultMap);
    }
    @ApiOperation(value = "批量删除下载视频", notes = "硬删 批量删除 用户id,视频id多个用逗号隔开(如: 10012,1255,1,2,5)")
    @DeleteMapping("/batchDelDownloadVideo/{userId}/{ids}")
    public ResponseEntity<Body<Void>> batchDelDownloadVideo(@ApiParam("用户id") @PathVariable String userId,
                                                            @ApiParam("视频id,多个用逗号隔开")  @PathVariable String ids) {
        try {
            User user = userService.findUserById(userId);
            if (user == null) {
                return ResponseUtil.of(HttpStatus.PRECONDITION_FAILED, "Re-login please.");
            }
            String[] collects = ids.split(",");
            if(collects.length == 0){
                return ResponseUtil.of(HttpStatus.BAD_REQUEST, "collect id length is zero.");
            }
            downloadVideoService.deleteDownloadVideoByIds(Integer.parseInt(user.getId()),Arrays.asList(collects));
            return ResponseUtil.of(HttpStatus.OK,"success");
        } catch (Exception e) {
            return ResponseUtil.badRequest(e.toString());
        }
    }
    @ApiOperation(value = "个人竹子数值统计", notes = "")
    @GetMapping("/myBambooValue/{userId}")
    public ResponseEntity<Body<UserValue>>  myBambooValue (@ApiParam("用户ID") @PathVariable String userId){
        User user = userService.findUserById(userId);
        if (user == null) {
            return ResponseUtil.of(HttpStatus.PRECONDITION_FAILED, "Re-login please.");
        }
        UserValue uv = userValueService.findUserValueById(userId);
        if(uv == null){
            uv = UserValue.builder().id(Long.parseLong(userId)).build();
            userValueService.insertUserValue(uv);
            uv = userValueService.findUserValueById(userId);
        }
        return ResponseUtil.ok(uv);
    }
    @ApiOperation(value = "用户数值学习统计", notes = "")
    @GetMapping("/myStudyValue/{userId}")
    public ResponseEntity<Body<UserStudy>>  myStudyValue (@ApiParam("用户ID") @PathVariable String userId){
        User user = userService.findUserById(userId);
        if (user == null) {
            return ResponseUtil.of(HttpStatus.PRECONDITION_FAILED, "Re-login please.");
        }
        return ResponseUtil.ok(userStudyeService.findUserMapById(userId));
    }
    @ApiOperation(value = "用户地图数值统计", notes = "")
    @GetMapping("/myMapValue/{userId}")
    public ResponseEntity<Body<UserMap>>  myMapValue (@ApiParam("用户ID") @PathVariable String userId){
        User user = userService.findUserById(userId);
        if (user == null) {
            return ResponseUtil.of(HttpStatus.PRECONDITION_FAILED, "Re-login please.");
        }
        return ResponseUtil.ok(userMapService.findUserMapById(userId));
    }
    @ApiOperation(value = "用户基本数据", notes = "")
    @GetMapping("/userBasicValue/{userId}")
    public ResponseEntity<Body<User>>  userBasicValue (@ApiParam("用户ID") @PathVariable String userId){
        User user = userService.findUserById(userId);
        if (user == null) {
            return ResponseUtil.of(HttpStatus.PRECONDITION_FAILED, "Re-login please.");
        }
        user.setPassword(null);
        return ResponseUtil.ok(user);
    }
    @ApiOperation(value = "排行榜", notes = "exp 经验  bamboo 竹子")
    @GetMapping("/userRankage/{flag}/{pageNo}/{limit}")
    public ResponseEntity<Body<Map<String, Object>>> getUserRankage(
            @ApiParam("1 竹子日榜,2 周榜;3 经验日榜,4经验周榜") @PathVariable Integer flag,
            @ApiParam("页号") @PathVariable Integer pageNo,
            @ApiParam("用户ID") @RequestParam String userId,
            @ApiParam("页行数") @PathVariable Integer limit) {
        if(pageNo == null || pageNo <= 0) {
            pageNo = 1;
        }
        int offset = (pageNo-1) * limit;
        UserRankListCondition urc = UserRankListCondition.builder().offset(offset).limit(limit).build();
        List<UserRankList>   userRankList = null;
        Integer collectCount = 0;
        switch (flag){
            case 1:
                urc.setType("day");
                userRankList =  userRankListService.getBambooRankByPage(urc);
                collectCount = userRankListService.getBambooRankListCount(urc);
                break;
            case 2:
                urc.setType("week");
                userRankList =  userRankListService.getBambooRankByPage(urc);
                collectCount = userRankListService.getBambooRankListCount(urc);
                break;
            case 3:
                urc.setType("day");
                userRankList =  userRankListService.getExpRankByPage(urc);
                collectCount = userRankListService.getExpRankListCount(urc);
                break;
            case 4:
                urc.setType("week");
                userRankList =  userRankListService.getExpRankByPage(urc);
                collectCount = userRankListService.getExpRankListCount(urc);
                break;
        }
        Map<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("rankList", mapper.mapAsList(userRankList, UserRankListVo.class));
        resultMap.put("count", collectCount);
        return ResponseUtil.ok(resultMap);
    }
    @ApiOperation(value = "提交当天用户学习时长(单位:分钟)", notes = "每一分钟2经验")
    @PostMapping("/submitUserStudyTime/{userId}")
    public ResponseEntity<Body<Void>>  submitUserStudyTime (@ApiParam("用户ID") @PathVariable String userId,
                                                            @ApiParam("学习时长(单位:分钟)") @RequestParam Integer time){
        User user = userService.findUserById(userId);
        if (user == null) {
            return ResponseUtil.of(HttpStatus.PRECONDITION_FAILED, "Re-login please.");
        }
        UserStudy userStudy = userStudyeService.findUserMapById(userId);
        weekHourByUser(userStudy,time);
        return ResponseUtil.ok();
    }
    private void weekHourByUser(UserStudy userStudy,Integer time){
        String weekhour = userStudy.getWeekHour();
        List<String> list =  Arrays.asList(weekhour.split(","));
        List<String> weekHourlist = new LinkedList<String>();
        for(String l:list){
            weekHourlist.add(l);
        };
        //当前星期几
        int day = DateUtil.dayForWeek(DateTime.now().toString("yyyy-MM-dd"));
        switch (day - weekHourlist.size()){
            case 0:
                userStudy.setStudyTimeToday(userStudy.getStudyTimeToday()+time.longValue());
                if(Strings.isNotEmpty(weekHourlist.get(day-1))){
                    weekHourlist.set(day-1, ""+ (Integer.parseInt(weekHourlist.get(day-1)) + time)) ;
                }else{
                    weekHourlist.set(day-1, ""+  time);
                }

                break;
            case 1:
                if("".equals(weekHourlist.get(weekHourlist.size()-1))){
                    userStudy.setStudyDayContinue(0L);
                }else{
                    userStudy.setStudyDayContinue(userStudy.getStudyDayContinue()+1);
                }
                weekHourlist.add(""+ time) ;
                userStudy.setStudyTimeToday(time.longValue());
                break;
            case 2:
                weekHourlist.add("0");
                weekHourlist.add(""+ time) ;
                userStudy.setStudyTimeToday(time.longValue());
                userStudy.setStudyDayContinue(0L);
                break;
            case 3:
                weekHourlist.add("0");
                weekHourlist.add("0");
                weekHourlist.add(""+ time) ;
                userStudy.setStudyDayContinue(0L);
                userStudy.setStudyTimeToday(time.longValue());
                break;
            case 4:
                weekHourlist.add("0");
                weekHourlist.add("0");
                weekHourlist.add("0");
                weekHourlist.add(""+ time) ;
                userStudy.setStudyDayContinue(0L);
                break;
            case 5:
                weekHourlist.add("0");
                weekHourlist.add("0");
                weekHourlist.add("0");
                weekHourlist.add("0");
                weekHourlist.add( ""+ time) ;
                userStudy.setStudyTimeToday(time.longValue());
                userStudy.setStudyDayContinue(0L);
                break;
            case 6:
                weekHourlist.add("0");
                weekHourlist.add("0");
                weekHourlist.add("0");
                weekHourlist.add("0");
                weekHourlist.add("0");
                userStudy.setStudyTimeToday(time.longValue());
                weekHourlist.add( ""+ time) ;
                userStudy.setStudyDayContinue(0L);
                break;
            default:
                weekHourlist.add("0");
                weekHourlist.add("0");
                weekHourlist.add("0");
                weekHourlist.add("0");
                weekHourlist.add("0");
                weekHourlist.add("0");
                weekHourlist.add( ""+ time) ;
                userStudy.setStudyTimeToday(time.longValue());
                userStudy.setStudyDayContinue(0L);
        }
        for(int i=0;i<weekHourlist.size();i++){
            if(i==0){
                weekhour = weekHourlist.get(0);
                if("".equals(weekhour)){
                    weekhour = "0";
                }
            }else{
                weekhour += ","+weekHourlist.get(i);
            }
        }
        userStudy.setStudyTimeAll(userStudy.getStudyTimeAll()+time.longValue());
        userStudy.setWeekHour(weekhour);
        userStudy.setStudyTimeWeek(userStudy.getStudyTimeWeek() + time.longValue());
        userStudyeService.modifyUserStudy(userStudy);
        UserValue userValue = userValueService.findUserValueById(userStudy.getId().toString());
        userValue.setGetExpToday(2 * time + (userValue.getGetExpToday() == null ? 0 : userValue.getGetExpToday()));
        userValue.setExp(2 * time + (userValue.getExp() == null ? 0 : userValue.getExp()));
        userValue.setSumWeekExp(2 * time + (userValue.getSumWeekExp() == null? 0 : userValue.getSumWeekExp()));
        userValueService.updateUserValue(userValue);
    }

    @ApiOperation(value = "签到", notes = "返回签到几天")
    @GetMapping("/userSign/{userId}")
    public ResponseEntity<Body<Integer>> userSign(@ApiParam("用户ID") @PathVariable String userId){
        User user = userService.findUserById(userId);
        if (user == null) {
            return ResponseUtil.of(HttpStatus.PRECONDITION_FAILED, "请重新登录!","Re-login please","請重新登入");
        }
        Integer signCount = userSignService.findUserSignByUserId(Long.parseLong(userId));
        return ResponseUtil.ok(signCount);
    }
    @ApiOperation(value = "今天是否签到", notes = "isSign 1 已签到  0未签到 signCount连续签到天数 signBamboo 下次签到获得竹子")
    @GetMapping("/userIsSign/{userId}")
    public ResponseEntity<Body<Map<String,String>>> userIsSign(@ApiParam("用户ID") @PathVariable String userId){
        User user = userService.findUserById(userId);
        if (user == null) {
            return ResponseUtil.of(HttpStatus.PRECONDITION_FAILED, "请重新登录!","Re-login please","請重新登入");
        }
        Map<String,String> map = Maps.newHashMap();
        UserSign userSign = userSignService.querySignByUserId(Long.parseLong(userId));
        String signCount = "0";
        if(userSign != null && userSign.getLastModifyTime().equals(DateTime.now().toString("yyyy-MM-dd"))){
            signCount = "1";
        }
        if(userSign != null){
            map.put("signCount",""+userSign.getSignCount());
            if(userSign.getSignCount() == 6){
                map.put("signBamboo","40");
            }else{
                map.put("signBamboo","20");
            }
        }else{
            map.put("signCount","0");
            map.put("signBamboo","20");
        }
        map.put("isSign",signCount);


        return ResponseUtil.ok(map);
    }
}
