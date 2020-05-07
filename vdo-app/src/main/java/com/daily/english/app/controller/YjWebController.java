package com.daily.english.app.controller;

import com.daily.english.app.domain.Feedback;
import com.daily.english.app.domain.User;
import com.daily.english.app.domain.YjEventInfo;
import com.daily.english.app.domain.YjUserBmInfo;
import com.daily.english.app.dto.UserDto;
import com.daily.english.app.dto.YjUserBmInfoTeacherWebDto;
import com.daily.english.app.dto.YjUserBmInfoWebDto;
import com.daily.english.app.service.FeedbackService;
import com.daily.english.app.service.UserService;
import com.daily.english.app.service.YjEventInfoService;
import com.daily.english.app.service.YjUserBmInfoService;
import com.daily.english.app.util.Body;
import com.daily.english.app.util.DateUtil;
import com.daily.english.app.util.ResponseUtil;
import com.daily.english.app.vo.YjUserVo;
import com.google.common.collect.Maps;
import com.google.common.hash.Hashing;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
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
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 演讲比赛web端接口
 *
 * @author
 * @create 2019-08-13 09:55
 **/
@Api(tags = "21世纪杯演讲web")
@Slf4j
@RestController
@RequestMapping(value = "/web/eventInfo")
public class YjWebController {

    @Autowired
    private UserService userService;
    @Autowired
    private YjUserBmInfoService userBmInfoService;
    @Autowired
    private YjEventInfoService eventInfoService;
    @Autowired
    private FeedbackService feedbackService;
    @Autowired
    private MapperFacade mapper;
    @Value("${file.staticAccessPath}")
    private String staticAccessPath;
    @Value("${file.uploadFolder}")
    private String uploadFolder;

    @ApiOperation(value = "21世纪杯用户登陆", notes = "通过用户信息进行登陆")
    @PostMapping("/login")
    public ResponseEntity<Body<YjUserVo>> login(
            @ApiIgnore HttpSession httpSession,
            final UserDto userDto) {
        if (userDto == null || StringUtils.isEmpty(userDto.getUsername())
                || StringUtils.isEmpty(userDto.getPassword())) {
            return ResponseUtil.badRequest("error");
        }
        User user = userService.findUserByName(userDto.getUsername().trim());
        if (user == null) {
            //查询报名信息根据邮箱已经报名code进行登录
            YjUserBmInfo yjUserBmInfo = userBmInfoService.selectWebBmLogin(userDto.getUsername(), userDto.getPassword());
            if (yjUserBmInfo == null) {
                return ResponseUtil.of(HttpStatus.OK, "账户不存在！");
            } else {
                YjUserVo userVo = new YjUserVo();
                userVo.setId(yjUserBmInfo.getId().toString());
                userVo.setUsername(yjUserBmInfo.getEmail());
                userVo.setNickname(yjUserBmInfo.getNameCh());
                userVo.setLoginType("1");//虚拟账号
                httpSession.setAttribute("yj_user", userVo);
                httpSession.setAttribute("yj_username", userVo.getUsername().trim());
                return ResponseUtil.ok(userVo);
            }

        } else {
            String hashPassword = Hashing.md5().hashString(userDto.getPassword().trim(), StandardCharsets.UTF_8).toString();
            if (hashPassword.equals(user.getPassword())) {
                YjUserVo userVo = new YjUserVo();
                if (StringUtils.isBlank(user.getNickname())) {
                    userVo.setNickname(user.getNickname());
                } else {
                    userVo.setNickname(user.getUsername());
                }
                userVo.setUsername(user.getUsername());
                userVo.setId(user.getId());
                userVo.setLoginType("0");//网站注册用户登录信息
                httpSession.setAttribute("yj_user", userVo);
                httpSession.setAttribute("yj_username", userVo.getUsername().trim());
                return ResponseUtil.ok(userVo);
            } else {
                return ResponseUtil.of(HttpStatus.OK, "密码错误");
            }
        }
    }


    @ApiOperation(value = "21世纪杯用户退出登陆", notes = "退出登陆")
    @PostMapping("/logout")
    public ResponseEntity<Body<Void>> logout(@ApiIgnore HttpSession httpSession) {
        if (httpSession.getAttribute("yj_user") != null) {
            httpSession.removeAttribute("yj_user");
            httpSession.removeAttribute("yj_username");
            return ResponseUtil.ok();
        }
        return ResponseUtil.ok();
    }

    @ApiOperation(value = "检查登录用户是否报名信息", notes = "有活动报名信息返回报名数据，通过返回数据ID调用getUserBmInfoById获取详情")
    @GetMapping("/checkExistsBmInfo/{eventId}")
    public ResponseEntity<Body<Map<String, Object>>> checkExistsBmInfo(
            @ApiParam("活动ID") @PathVariable String eventId
            , @ApiIgnore HttpSession httpSession) {
        Map<String, Object> result = new HashMap<>();
        YjUserVo user = (YjUserVo) httpSession.getAttribute("yj_user");
        if (user == null) {
            return ResponseUtil.of(HttpStatus.UNAUTHORIZED, "用户未登录！");
        }
        List<YjUserBmInfo> yjUserBmInfos = userBmInfoService.selectUserBmInfoLoginType(user, eventId);
        if (yjUserBmInfos.size() == 0) {
            result.put("bmType", -1);//未报名
        } else {
            result.put("bmType", 0);//个人报名
            for (YjUserBmInfo yjUserBmInfo : yjUserBmInfos) {
                if (yjUserBmInfo.getType().equals(1)) {
                    result.put("bmType", 1);//老师报名
                    break;
                }
            }
        }
        result.put("userBmInfoList", yjUserBmInfos);
        return ResponseUtil.ok(result);
    }

    @ApiOperation(value = "新检查登录用户是否报名信息", notes = "有活动报名信息返回报名数据，通过返回数据ID调用getUserBmInfoById获取详情")
    @GetMapping("/checkExistsBmInfoNew/{eventId}")
    public ResponseEntity<Body<Map<String, Object>>> checkExistsBmInfoNew(
            @ApiParam("活动ID") @PathVariable String eventId
            , @ApiIgnore HttpSession httpSession) {
        Map<String, Object> result = new HashMap<>();
        YjUserVo user = (YjUserVo) httpSession.getAttribute("yj_user");
        if (user == null) {
            return ResponseUtil.of(HttpStatus.UNAUTHORIZED, "用户未登录！");
        }
        List<YjUserBmInfo> yjUserBmInfos = userBmInfoService.checkUserBmInfo(user.getId(), eventId);
        if (yjUserBmInfos.size() == 0) {
            result.put("bmType", -1);//未报名
        } else {
            result.put("bmType", 0);//个人登录
            for (YjUserBmInfo yjUserBmInfo : yjUserBmInfos) {
                if (yjUserBmInfo.getType().equals(0)){
                    continue;
                }
                if (yjUserBmInfo.getTeacherUserId().equals(user.getId())) {
                    result.put("bmType", 1);//老师登录
                    break;
                }
            }
        }
        result.put("userBmInfoList", yjUserBmInfos);
        return ResponseUtil.ok(result);
    }

    @ApiOperation(value = "获取报名详情", notes = "老师根据报名信息ID，打开学生报名信息详情")
    @GetMapping("/getUserBmInfoById/{bmInfoId}")
    public ResponseEntity<Body<YjUserBmInfo>> getUserBmInfoById(
            @PathVariable @ApiParam("报名信息ID") String bmInfoId
            , @ApiIgnore HttpSession httpSession) {
        YjUserVo user = (YjUserVo) httpSession.getAttribute("yj_user");
        if (user == null) {
            return ResponseUtil.of(HttpStatus.UNAUTHORIZED, "用户未登录！");
        }
        YjUserBmInfo userBmInfo = userBmInfoService.selectUserBmInfoById(bmInfoId);
        return ResponseUtil.ok(userBmInfo);
    }

    @ApiOperation(value = "首页数据", notes = "首页数据")
    @GetMapping("/index/{eventId}")
    public ResponseEntity<Body<YjEventInfo>> eventInfoIndex(@ApiParam("活动ID") @PathVariable String eventId) {
        YjEventInfo yjEventInfo = eventInfoService.selectEventInfoById(eventId);
        return ResponseUtil.ok(yjEventInfo);
    }

    @ApiOperation(value = "个人报名", notes = "个人用户报名")
    @PostMapping("/entry/save")
    public ResponseEntity<Body<Void>> saveUserBmInfo(
            @ApiParam("用户报名体") @RequestBody YjUserBmInfoWebDto userBmInfoDto, @ApiIgnore HttpSession httpSession) {
        YjUserVo user = (YjUserVo) httpSession.getAttribute("yj_user");
        if (user == null) {
            return ResponseUtil.of(HttpStatus.UNAUTHORIZED, "用户未登录！");
        }
        List<YjUserBmInfo> yjUserBmInfos = userBmInfoService.checkUserBmInfo(user.getId(), userBmInfoDto.getEventId());
//        List<YjUserBmInfo> yjUserBmInfos = userBmInfoService.selectUserBmInfoLoginType(user, userBmInfoDto.getEventId());
        if (yjUserBmInfos.size() == 0) {
            userBmInfoDto.setUserId(user.getId());
            userBmInfoService.saveWebUserBmInfo(userBmInfoDto);
        } else {
            return ResponseUtil.of(HttpStatus.OK, "该用户已在活动中提交了报名信息！");
        }
        return ResponseUtil.ok();
    }

    @ApiOperation(value = "APP-个人报名", notes = "APP-个人报名")
    @PostMapping("/entry/appSave")
    public ResponseEntity<Body<Void>> saveAppUserBmInfo(
            @ApiParam("用户报名体") @RequestBody YjUserBmInfoWebDto userBmInfoDto) {
        if (StringUtils.isBlank(userBmInfoDto.getEventId())){
            return ResponseUtil.badRequest("缺少eventId参数");
        }
        List<YjUserBmInfo> yjUserBmInfos = userBmInfoService.checkUserBmInfo(userBmInfoDto.getUserId(), userBmInfoDto.getEventId());
        if (yjUserBmInfos.size() == 0) {
            userBmInfoDto.setUserId(userBmInfoDto.getUserId());
            userBmInfoService.saveWebUserBmInfo(userBmInfoDto);
        } else {
            return ResponseUtil.of(HttpStatus.OK, "该用户已在活动中提交了报名信息！");
        }
        return ResponseUtil.ok();
    }

    @ApiOperation(value = "反馈提交", notes = "反馈提交")
    @PostMapping("/entry/saveFeedback")
    public ResponseEntity<Body<Void>> saveFeedback(
            @ApiParam("用户报名体") @RequestBody Feedback feedback) {
        feedback.setQuestionType(5);
        feedback.setSourceType(4);//21世纪杯报名标识
        feedback.setStatus(0);//21世纪杯报名标识
        feedbackService.saveFeedback(feedback);
        return ResponseUtil.ok();
    }

    @ApiOperation(value = "资料上传", notes = "资料上传")
    @PostMapping("/entry/update")
    public ResponseEntity<Body<Void>> updateUserBmInfo(
            @ApiParam("用户报名体") @RequestBody YjUserBmInfoWebDto userBmInfoDto, @ApiIgnore HttpSession httpSession) {
        YjUserVo user = (YjUserVo) httpSession.getAttribute("yj_user");
        if (user == null) {
            return ResponseUtil.of(HttpStatus.UNAUTHORIZED, "用户未登录！");
        }

//        List<YjUserBmInfo> yjUserBmInfos = userBmInfoService.selectUserBmInfoLoginType(user, userBmInfoDto.getEventId());
        List<YjUserBmInfo> yjUserBmInfos = userBmInfoService.checkUserBmInfo(user.getId(), userBmInfoDto.getEventId());
        if (yjUserBmInfos.size() == 0) {
            return ResponseUtil.of(HttpStatus.OK, "查询不到用户报名信息！");
        } else {
            YjUserBmInfo userBmInfo = mapper.map(userBmInfoDto, YjUserBmInfo.class);
            userBmInfoService.saveUserBmInfo(userBmInfo);
        }
        return ResponseUtil.ok();
    }

    //
    @ApiOperation(value = "老师报名", notes = "老师用户报名")
    @PostMapping("/entry/teacherSave")
    public ResponseEntity<Body<Void>> teacherSaveUserBmInfo(
            @ApiParam("老师报名体") @RequestBody YjUserBmInfoTeacherWebDto userBmInfoTeacherWebDto, @ApiIgnore HttpSession httpSession) {
        YjUserVo user = (YjUserVo) httpSession.getAttribute("yj_user");
        if (user == null) {
            return ResponseUtil.of(HttpStatus.UNAUTHORIZED, "用户未登录！");
        }
        userBmInfoTeacherWebDto.setUserId(user.getId());
        userBmInfoService.saveTeacherUserBmInfo(userBmInfoTeacherWebDto);
        return ResponseUtil.ok();
    }

    @ApiOperation(value = "新老师报名", notes = "老师用户报名")
    @PostMapping("/entry/teacherNewSave")
    public ResponseEntity<Body<Void>> teacherSaveUserBmInfoNew(
            @ApiParam("老师报名体") @RequestBody YjUserBmInfoTeacherWebDto userBmInfoTeacherWebDto, @ApiIgnore HttpSession httpSession) {
        YjUserVo user = (YjUserVo) httpSession.getAttribute("yj_user");
        if (user == null) {
            return ResponseUtil.of(HttpStatus.UNAUTHORIZED, "用户未登录！");
        }
        userBmInfoTeacherWebDto.setUserId(user.getId());
        userBmInfoService.saveTeacherUserBmInfoNew(userBmInfoTeacherWebDto);
        return ResponseUtil.ok();
    }

    @ApiOperation(value = "报名信息虚拟用户转化成用户", notes = "报名信息虚拟用户转化成用户")
    @PostMapping("/entry/bmInfoConvertUser")
    public ResponseEntity<Body<Void>> bmInfoConvertUser() {
        userBmInfoService.bmInfoConvertUser();
        return ResponseUtil.ok();
    }

    @ApiOperation(value = "用户反馈上传图片、以及文件", notes = "上传图片")
    @PostMapping("/upload/img")
    public ResponseEntity<Body<Map<String, String>>> uploadImg(@RequestParam("file") MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            String extension = FilenameUtils.getExtension(fileName);
            fileName = DateUtil.getRandom() + "." + extension;
            String pathdir = uploadFolder + "/feedback/";
            File dest = new File(pathdir, fileName);
            if (!dest.getParentFile().exists()) { //判断文件父目录是否存在
                dest.getParentFile().mkdir();
            }
            FileUtils.copyInputStreamToFile(file.getInputStream(), dest);

            Map<String, String> map = Maps.newHashMap();
            map.put("fileUrl", "/static/image" + "/feedback/" + fileName);
            return ResponseUtil.ok(map);
        } else {
            return ResponseUtil.of(HttpStatus.OK, "图片为空");
        }
    }


}
