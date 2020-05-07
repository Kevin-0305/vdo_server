package com.daily.english.app.controller;

import com.daily.english.app.domain.User;
import com.daily.english.app.dto.UserDto;
import com.daily.english.app.enums.CodeStateEnum;
import com.daily.english.app.service.MailService;
import com.daily.english.app.service.UserService;
import com.daily.english.app.util.*;
import com.daily.english.app.vo.UserVo;
import com.github.pagehelper.util.StringUtil;
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
import org.apache.logging.log4j.util.Strings;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Api(tags = "用户")
@Slf4j
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MapperFacade mapper;

    @Autowired
    private MailService mailService;

    @ApiOperation(value = "检查邮箱是否账号存在", notes = "检查电子邮件是否存在（status: <200 不存在> <400 已存在>）")
    @PostMapping("/checkEmailExists")
    public ResponseEntity<Body<Void>> checkEmailExists(final String email) {
        User user = userService.findUserByName(email.trim());
        if (user != null) {
            return ResponseUtil.of(HttpStatus.BAD_REQUEST
                    , "此邮箱已注册，如果需要建立新账号，请使用其他电邮。"
                    , "We have a record of this email already. If you wish to create a new account, please use another email."
                    , "此郵箱已註冊，如果需要建立新帳號，請使用其他電郵。");
        }
        return ResponseUtil.ok();
    }

    /**
     * 发送验证码
     *
     * @throws Exception
     */
    @ApiOperation(value = "获得验证码", notes = "提交注册账号(邮箱名),发送验证码")
    @PostMapping("/sendCode")
    public ResponseEntity<Body<String>> sendCode(
            @ApiIgnore HttpSession httpSession,
            String username) {
        User user = userService.findUserByName(username.trim());
        if (user != null) {//邮箱已注册
            return ResponseUtil.of(HttpStatus.BAD_REQUEST
                    , "此邮箱已注册，如果需要建立新账号，请使用其他电邮。"
                    , "We have a record of this email already. If you wish to create a new account, please use another email."
                    , "此郵箱已註冊，如果需要建立新帳號，請使用其他電郵。");
        }
        try {
            String code = RandomStringUtil.getRandomString(6);//验证码
            mailService.sendRegisterMail(username, "Verification Code from VDO English 來自VDO English的註冊驗證碼", code);
            httpSession.setAttribute("registerCode", username + "|" + code);
            return ResponseUtil.of(HttpStatus.OK
                    , "验证码已发送"
                    , "Verification code has been sent."
                    , "驗證碼已發送");
        } catch (Exception e) {
            return ResponseUtil.of(HttpStatus.BAD_REQUEST
                    , "验证码发送失败"
                    , "Verification code sent failed"
                    , "驗證碼發送失败");
        }
//        if (MailUtil.sendCode(username, code)) {
//            httpSession.setAttribute("registerCode", code);
//            return ResponseUtil.of(HttpStatus.OK
//                    , "验证码已发送"
//                    ,"Verification code has been sent."
//                    ,"驗證碼已發送");
//        } else {
//            return ResponseUtil.of(HttpStatus.BAD_REQUEST
//                    , "验证码发送失败"
//                    ,"Verification code sent failed"
//                    ,"驗證碼發送失败");
//        }
    }

    /**
     * 密码格式判断6-18位之间合法
     *
     * @param password
     * @return true 合法 false 不合法
     */
    private static boolean checkPasswordFormat(String password) {
        if (password.length() < 6 || password.length() > 18) {
            return false;
        } else {
            return true;
        }
    }

    @ApiOperation(value = "用户注册", notes = "通过注册码或者用户信息进行注册")
    @PostMapping("/register")
    public ResponseEntity<Body<UserVo>> register(
            @ApiIgnore HttpSession httpSession,
            final UserDto userDto,
            final String registerCode) {
        String str = (String) httpSession.getAttribute("registerCode");
        String[] nameAndCode = str.split("\\|");
        String name = nameAndCode[0];
        if (!userDto.getUsername().trim().equals(name)) {
            return ResponseUtil.of(HttpStatus.BAD_REQUEST
                    , "邮箱不是注册邮箱，请输入注册邮箱！"
                    , "Email is not registered email, please enter registered email!"
                    , "郵箱不是註冊郵箱，請輸入註冊郵箱！");
        }
        String registerCodeStr = nameAndCode[1];
        if (!checkPasswordFormat(userDto.getPassword().trim())) {
            return ResponseUtil.of(HttpStatus.BAD_REQUEST
                    , "密码格式错误，请重新输入。"
                    , "Wrong password format. Please try again."
                    , "密碼格式錯誤，請重新輸入。");
        }
        if (StringUtil.isEmpty(registerCodeStr)) {//验证码过期
            return ResponseUtil.of(HttpStatus.BAD_REQUEST
                    , "验证码已过期，请重新获取。"
                    , "The verification code has expired. Please get a new one."
                    , "驗證碼已過期，請重新獲取。");
        }
        if (!registerCodeStr.equals(registerCode)) {//验证码错误
            return ResponseUtil.of(HttpStatus.BAD_REQUEST
                    , "验证码错误，请重新输入。"
                    , "Incorrect verification code. Please input again."
                    , "驗證碼錯誤，請重新輸入。");
        }
        User user = userService.findUserByName(userDto.getUsername().trim());
        if (user != null) {//邮箱已注册
            return ResponseUtil.of(HttpStatus.BAD_REQUEST
                    , "此邮箱已注册，如果需要建立新账号，请使用其他电邮。"
                    , "We have a record of this email already. If you wish to create a new account, please use another email."
                    , "此郵箱已註冊，如果需要建立新帳號，請使用其他電郵。");
        }
        user = new User();
        user.setUsername(userDto.getUsername().trim());
        String hashPassword = Hashing.md5().hashString(userDto.getPassword().trim(), StandardCharsets.UTF_8).toString();
        user.setPassword(hashPassword);
        user.setNickname("");
        String dateTimeStr = DateTime.now().toString("yyyy-MM-dd HH:mm:ss");
        user.setCreateTime(dateTimeStr);

        userService.insertUser(user);
        httpSession.setAttribute("username", user.getUsername().trim());
        httpSession.setAttribute("user", user);

        UserVo userVo = mapper.map(user, UserVo.class);
        httpSession.removeAttribute("registerCode");//修改成功并将registerCode重置
        return ResponseUtil.ok(userVo);
    }


    /**
     * 发送验证码（找回密码）
     *
     * @throws Exception
     */
    @ApiOperation(value = "获得验证码(找回密码)", notes = "提交账号(邮箱名),发送验证码,返回用户ID")
    @PostMapping("/sendRetrieveCode")
    public ResponseEntity<Body<Void>> sendPasswordCode(
            @ApiIgnore HttpSession httpSession,
            @ApiParam("用户账号") @RequestParam(required = true) String username) {
        User user = userService.findUserByName(username.trim());
        if (user == null) {
            return ResponseUtil.of(HttpStatus.BAD_REQUEST
                    , "该账号不存在"
                    , "We have no record of this account."
                    , "該帳號不存在");
        }
        try {
            String code = RandomStringUtil.getRandomString(6);//验证码
            mailService.sendPasswordCodeMail(username, "Verification Code from VDO English 來自VDO English的驗證碼", code);
            httpSession.setAttribute("retrieveCode", code);
            return ResponseUtil.ofAndData(HttpStatus.OK
                    , "验证码已发送"
                    , "Verification code has been sent."
                    , "驗證碼已發送",
                    user.getId());
        } catch (Exception e) {
            return ResponseUtil.of(HttpStatus.BAD_REQUEST
                    , "验证码发送失败"
                    , "Verification code sent failed"
                    , "驗證碼發送失败");
        }
    }

    /**
     * 找回密码
     *
     * @return
     */
    @ApiOperation(value = "找回密码", notes = "找回密码")
    @PostMapping("/retrievePassword")
    public ResponseEntity<Body<Void>> retrievePassword(
            @ApiIgnore HttpSession httpSession,
            @ApiParam("用户id") @RequestParam(required = true) String userId,
            @ApiParam("验证码") @RequestParam(required = true) String retrieveCode,
            @ApiParam("新密码") @RequestParam(required = true) String newPassword) {
        if (!checkPasswordFormat(newPassword.trim())) {
            return ResponseUtil.of(HttpStatus.BAD_REQUEST
                    , "密码格式错误，请重新输入。"
                    , "Wrong password format. Please try again."
                    , "密碼格式錯誤，請重新輸入。");
        }
        //String registerCodeStr = (String) httpSession.getAttribute("retrieveCode");
        String str = (String) httpSession.getAttribute("registerCode");
        String[] nameAndCode = str.split("|");
        String name = nameAndCode[0];
        User newUser = userService.findUserById(userId);
        if (!newUser.getUsername().trim().equals(name)) {
            return ResponseUtil.of(HttpStatus.BAD_REQUEST
                    , "邮箱不是注册邮箱，请输入注册邮箱！"
                    , "Email is not registered email, please enter registered email!"
                    , "郵箱不是註冊郵箱，請輸入註冊郵箱！");
        }
        String registerCodeStr = nameAndCode[1];
        if (StringUtil.isEmpty(registerCodeStr)) {//验证码过期
            return ResponseUtil.of(HttpStatus.BAD_REQUEST
                    , "验证码已过期，请重新获取。"
                    , "The verification code has expired. Please get a new one."
                    , "驗證碼已過期，請重新獲取。");
        }
        if (!registerCodeStr.equals(retrieveCode)) {//验证码错误
            return ResponseUtil.of(HttpStatus.BAD_REQUEST
                    , "验证码错误，请重新输入。"
                    , "Incorrect verification code. Please input again."
                    , "驗證碼錯誤，請重新輸入。");
        }
        //User newUser = userService.findUserById(userId);
        if (newUser == null) {
            return ResponseUtil.of(HttpStatus.BAD_REQUEST
                    , "该账号不存在"
                    , "We have no record of this account."
                    , "該帳號不存在");
        }
        String hashPassword = Hashing.md5().hashString(newPassword.trim(), StandardCharsets.UTF_8).toString();
        newUser.setPassword(hashPassword);
        userService.modifyUser(newUser);
        httpSession.removeAttribute("retrieveCode");//修改成功并将retrieveCode重置
        return ResponseUtil.ok();
    }


    @ApiOperation(value = "用户登陆", notes = "通过用户信息进行登陆")
    @PostMapping("/login")
    public ResponseEntity<Body<UserVo>> login(
            @ApiIgnore HttpSession httpSession,
            final UserDto userDto) {
        if (userDto == null || StringUtils.isEmpty(userDto.getUsername())
                || StringUtils.isEmpty(userDto.getPassword())) {
            return ResponseUtil.of(HttpStatus.BAD_REQUEST
                    , "邮箱或密码错误，请重新输入。"
                    , "Incorrect email or password.Please try again."
                    , "電郵或密碼錯誤，請重新輸入。");
        }
        User user = userService.findUserByName(userDto.getUsername().trim());
        if (user == null) {
            return ResponseUtil.of(HttpStatus.NOT_FOUND
                    , "该账号不存在，请重新输入"
                    , "We have no record of this account.Please try again."
                    , "該帳號不存在，請重新輸入。");
        } else {
            String hashPassword = Hashing.md5().hashString(userDto.getPassword().trim(), StandardCharsets.UTF_8).toString();
            if (hashPassword.equals(user.getPassword())) {
                httpSession.setAttribute("user", user);
                httpSession.setAttribute("username", user.getUsername().trim());
                UserVo userVo = mapper.map(user, UserVo.class);
                return ResponseUtil.ok(userVo);
            } else {
                return ResponseUtil.of(HttpStatus.BAD_REQUEST
                        , "邮箱或密码错误，请重新输入。"
                        , "Incorrect email or password.Please try again."
                        , "電郵或密碼錯誤，請重新輸入。");
            }
        }
    }

    /**
     * 修改密码
     *
     * @return
     */
    @ApiOperation(value = "修改密码", notes = "修改密码")
    @PostMapping("/modifyPassword")
    public ResponseEntity<Body<Void>> modifyPassword(
            @ApiIgnore HttpSession httpSession,
            @ApiParam("用户ID") @RequestParam(value = "userId", required = false) String userId,
            String oldPassword,
            String newPassword) {
        if (!checkPasswordFormat(newPassword.trim())) {
            return ResponseUtil.of(HttpStatus.BAD_REQUEST
                    , "密码格式错误，请重新输入。"
                    , "Wrong password format. Please try again."
                    , "密碼格式錯誤，請重新輸入。");
        }
        User user = (User) httpSession.getAttribute("user");
        if (user == null) {
            user = userService.findUserById(userId);
        }
        if (user == null) {
            return ResponseUtil.of(HttpStatus.BAD_REQUEST
                    , "登录已过期，请重新登录！"
                    , "Login has expired, please login again!"
                    , "登錄已過期，請重新登錄！");
        }
        String hashOldPassword = Hashing.md5().hashString(oldPassword.trim(), StandardCharsets.UTF_8).toString();
        if (!user.getPassword().equals(hashOldPassword)) {
            return ResponseUtil.of(HttpStatus.BAD_REQUEST
                    , "旧密码错误，请重新输入。"
                    , "Incorrect old password.Please try again."
                    , "旧密碼錯誤，請重新輸入。");
        } else {
            User newUser = userService.findUserByName(user.getUsername());
            if (newUser == null) {
                return ResponseUtil.of(HttpStatus.NOT_FOUND
                        , "该账号不存在，请重新输入"
                        , "We have no record of this account.Please try again."
                        , "該帳號不存在，請重新輸入。");
            }
            String hashPassword = Hashing.md5().hashString(newPassword.trim(), StandardCharsets.UTF_8).toString();
            newUser.setPassword(hashPassword);
            userService.modifyUser(newUser);
            return ResponseUtil.ok();
        }
    }

    /**
     * 重置密码
     *
     * @return
     */
    @ApiOperation(value = "重置密码", notes = "重置密码")
    @PostMapping("/resetPassword")
    public ResponseEntity<Body<Void>> resetPassword(
            String id,
            String newPassword) {
        User newUser = userService.findUserById(id);
        if (newUser == null) {
            return ResponseUtil.of(HttpStatus.BAD_REQUEST
                    , "该账号不存在"
                    , "We have no record of this account."
                    , "該帳號不存在");
        }
        String hashPassword = Hashing.md5().hashString(newPassword.trim(), StandardCharsets.UTF_8).toString();
        newUser.setPassword(hashPassword);
        userService.modifyUser(newUser);
        return ResponseUtil.ok();

    }

    /**
     * 修改用户信息
     *
     * @return
     * @throws IOException
     */
    @ApiOperation(value = "修改用户信息", notes = "修改用户信息")
    @PostMapping("/modifyUser")
    public ResponseEntity<Body<Void>> modifyUser(
            String id,
            String nickName,
            String password,
            String newPassword
    ) {
        User newUser = userService.findUserById(id);
        if (newUser == null) {
            return ResponseUtil.of(HttpStatus.BAD_REQUEST
                    , "该账号不存在"
                    , "We have no record of this account."
                    , "該帳號不存在");
        }
        if (StringUtils.isNotBlank(nickName)) {
            newUser.setNickname(nickName);
        }
        if (StringUtils.isNotBlank(newPassword)) {
            password = Hashing.md5().hashString(password.trim(), StandardCharsets.UTF_8).toString();
            if (newUser.getPassword().equals(password)) {
                String hashPassword = Hashing.md5().hashString(newPassword.trim(), StandardCharsets.UTF_8).toString();
                newUser.setPassword(hashPassword);
            } else {
                return ResponseUtil.of(HttpStatus.BAD_REQUEST
                        , "旧密码错误，请重新输入。"
                        , "Incorrect old password.Please try again."
                        , "旧密碼錯誤，請重新輸入。");
            }

        }
        userService.modifyUser(newUser);
        return ResponseUtil.ok();
    }

    @ApiOperation(value = "选择用户等级", notes = "选择用户等级")
    @PostMapping("/choiceLevel")
    public ResponseEntity<Body<Void>> choiceLevel(
            @ApiParam("用户id") @RequestParam(required = true) String userId,
            @ApiParam("挑战等级") @RequestParam(required = true) String level) {

        return ResponseUtil.ok();
    }

    @ApiOperation(value = "提交验证码订单", notes = "提交验证码订单")
    @PostMapping("/code")
    public ResponseEntity<Body<String>> code(
            @ApiParam("用户id") @RequestParam(required = true) String userId,
            @ApiParam("挑战等级") @RequestParam(required = true) String level,
            @ApiParam("验证码") @RequestParam(required = true) String code) {
        CodeStateEnum codeStateEnum = userService.updateIdentifyCode(userId, level, code);
        if (codeStateEnum.getValue() == 1) {
            return ResponseUtil.ofAndData(HttpStatus.OK
                    , "成功"
                    , "Success"
                    , "成功"
                    , codeStateEnum.getName());
        }
        if (codeStateEnum.getValue() == 2) {
            return ResponseUtil.ofAndData(HttpStatus.BAD_REQUEST
                    , "输入的学生ID已使用"
                    , "The student id you entered has been used."
                    , "輸入的學生ID已使用"
                    , codeStateEnum.getName());
        }
        if (codeStateEnum.getValue() == 3) {
            return ResponseUtil.ofAndData(HttpStatus.BAD_REQUEST
                    , "输入的学生ID已过期"
                    , "The student id you entered has expired."
                    , "輸入的學生ID已過期"
                    , codeStateEnum.getName());
        }
        if (codeStateEnum.getValue() == 4) {
            return ResponseUtil.ofAndData(HttpStatus.BAD_REQUEST
                    , "输入的学生ID不存在"
                    , "The student id you entered does not exist."
                    , "輸入的學生ID不存在"
                    , codeStateEnum.getName());
        }
        return ResponseUtil.ok(codeStateEnum.getName());
    }


    /**
     * 录音
     *
     * @throws IOException
     */
    @ApiOperation(value = "录音", notes = "录音")
    @PostMapping("/record")
    public ResponseEntity<Body<String>> record(
            @ApiIgnore HttpSession httpSession) {

        User user = (User) httpSession.getAttribute("user");

        boolean record = RecordUtil.capture();
        if (record) {
            return ResponseUtil.ok();
        } else {
            return ResponseUtil.of(HttpStatus.OK, "fail");
        }
    }

    /**
     * 停止录音
     *
     * @throws IOException
     */
    @ApiOperation(value = "停止录音", notes = "停止录音")
    @PostMapping("/stop")
    public ResponseEntity<Body<String>> stop(
            @ApiIgnore HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("user");

        boolean stop = RecordUtil.stop();
        if (stop) {
            return ResponseUtil.ok();
        } else {
            return ResponseUtil.of(HttpStatus.OK, "fail");
        }
    }

    /**
     * 播放录音
     *
     * @throws IOException
     */
    @ApiOperation(value = "播放录音", notes = "播放录音")
    @PostMapping("/play")
    public ResponseEntity<Body<String>> play(
            @ApiIgnore HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("user");

        boolean play = RecordUtil.play();
        if (play) {
            return ResponseUtil.ok();
        } else {
            return ResponseUtil.of(HttpStatus.OK, "fail");
        }
    }

    /**
     * 保存录音
     *
     * @throws IOException
     */
    @ApiOperation(value = "保存录音", notes = "保存录音")
    @PostMapping("/save")
    public ResponseEntity<Body<String>> save(
            @ApiIgnore HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("user");

        boolean stop = RecordUtil.stop();
        boolean save = RecordUtil.save();
        if (save) {
            return ResponseUtil.ok();
        } else {
            return ResponseUtil.of(HttpStatus.OK, "fail");
        }
    }

    @Value("${file.staticAccessPath}")
    private String staticAccessPath;

    @Value("${file.uploadFolder}")
    private String uploadFolder;

    @ApiOperation(value = "上传用户图片", notes = "上传用户图片,修改用户头像")
    @PostMapping("/upload/img")
    public ResponseEntity<Body<Map<String, String>>> uploadImg(
            @ApiParam("用户id") @RequestParam(required = false) String userId,
            @ApiIgnore HttpSession httpSession,
            @RequestParam("file") MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            String extension = FilenameUtils.getExtension(fileName);
            fileName = DateUtil.getRandom() + "." + extension;
            String relativePath = "/userImg/";
            String pathDir = uploadFolder + relativePath;
            File dest = new File(pathDir, fileName);
            if (!dest.getParentFile().exists()) { //判断文件父目录是否存在
                dest.getParentFile().mkdir();
            }
            FileUtils.copyInputStreamToFile(file.getInputStream(), dest);

            Map<String, String> map = Maps.newHashMap();
            map.put("fileUrl", "/static/image" + relativePath + fileName);
            if (Strings.isNotEmpty(userId)) {
                User user = userService.findUserById(userId);
                if (user != null) {
                    user.setUserImg("/static/image" + relativePath + fileName);
                    userService.modifyUserInformation(user);
                }
            }
            return ResponseUtil.ok(map);
        } else {
            return ResponseUtil.of(HttpStatus.OK, "图片为空");
        }
    }
}
