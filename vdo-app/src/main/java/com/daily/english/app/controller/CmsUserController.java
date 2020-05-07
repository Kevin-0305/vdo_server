package com.daily.english.app.controller;


import com.daily.english.app.domain.CmsUser;
import com.daily.english.app.domain.PartnerSchool;
import com.daily.english.app.domain.User;
import com.daily.english.app.dto.CmsUserDto;
import com.daily.english.app.dto.UserDto;
import com.daily.english.app.service.CmsUserService;
import com.daily.english.app.service.PartnerSchoolService;
import com.daily.english.app.service.UserService;
import com.daily.english.app.util.Body;
import com.daily.english.app.util.ResponseUtil;
import com.daily.english.app.vo.CmsUserInformation;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


/**
 * 后台用户管理
 *
 * @author
 * @create 2019-08-13 09:55
 **/
@Api(tags = "后台用户管理")
@Slf4j
@RestController
@RequestMapping(value = "/cmsUser")
public class CmsUserController {

    @Autowired
    private CmsUserService cmsUserService;
    @Autowired
    private UserService userService;
    @Autowired
    private PartnerSchoolService partnerSchoolService;


    @ApiOperation(value = "获取后台用户列表", notes = "获取后台用户列表(分页)")
    @GetMapping("/list")
    public ResponseEntity<Body<PageInfo<CmsUser>>> getCmsUserPage(CmsUserDto cmsUserDto) {
        return ResponseUtil.ok(cmsUserService.selectCmsUserPage(cmsUserDto));
    }

    @ApiOperation(value = "新增/更新后台用户", notes = "新增/更新后台用户, 不含id为新增, 含id为更新")
    @PostMapping("/save")
    public ResponseEntity<Body<Void>> saveCmsUser(
            @ApiParam("后台用户体") @RequestBody CmsUser cmsUser) {
        CmsUser cmsUserByUserName = cmsUserService.findCmsUserByUserName(cmsUser.getAccount());
        if (cmsUserByUserName != null && (null != cmsUser.getId() && cmsUser.getId() < 1)) {
            return ResponseUtil.of(HttpStatus.OK, "账户已被占用！");
        }
        if (cmsUser.getRoleType().equals(3)) {
            if (StringUtils.isBlank(cmsUser.getTeacherAppAccount())) {
                return ResponseUtil.of(HttpStatus.OK, "请填写老师APP账号！");
            }
            User userByName = userService.findUserByName(cmsUser.getTeacherAppAccount());
            if (userByName == null) {
                return ResponseUtil.of(HttpStatus.OK, "根据账号未查找到对应老师用户！");
            }
            cmsUser.setTeacherUserId(userByName.getId());
        }
        cmsUserService.saveCmsUser(cmsUser);
        return ResponseUtil.ok();
    }


    @ApiOperation(value = "新增/更新后台评委用户", notes = "新增/更新后台评委角色用户, 不含id为新增, 含id为更新")
    @PostMapping("/saveJuryUser")
    public ResponseEntity<Body<Void>> saveCmsJuryUser(
            @ApiParam("后台用户体") @RequestBody CmsUser cmsUser) {
        cmsUser.setRoleType(4);
        cmsUserService.saveCmsUser(cmsUser);
        return ResponseUtil.ok();
    }

    @ApiOperation(value = "用户开通", notes = "用户开通")
    @GetMapping("/openUser/{id}")
    public ResponseEntity<Body<Void>> openJuryUser(
            @ApiParam("后台用户体") @PathVariable String id) {
        CmsUser cmsUser = new CmsUser();
        cmsUser.setId(Integer.parseInt(id));
        cmsUser.setStatus(1);
        cmsUserService.saveCmsUser(cmsUser);
        return ResponseUtil.ok();
    }

    @ApiOperation(value = "用户关闭开通", notes = "用户关闭开通")
    @GetMapping("/closeUser/{id}")
    public ResponseEntity<Body<Void>> closeJuryUser(
            @ApiParam("后台用户体") @PathVariable String id) {
        CmsUser cmsUser = new CmsUser();
        cmsUser.setId(Integer.parseInt(id));
        cmsUser.setStatus(0);
        cmsUserService.saveCmsUser(cmsUser);
        return ResponseUtil.ok();
    }

    @ApiOperation(value = "获取后台用户详情", notes = "通过后台用户id获得活动后台用户详情")
    @GetMapping("/info/{id}")
    public ResponseEntity<Body<CmsUser>> getCmsUser(
            @ApiParam("后台用户ID") @PathVariable String id) {
        CmsUser cmsUser = cmsUserService.selectCmsUserById(id);
        if (cmsUser.getRoleType().equals(3)) {//老师账号返回对应ID以及老师app账号
            User userByName = userService.findUserById(cmsUser.getTeacherUserId());
            cmsUser.setTeacherUserId(userByName.getId());
            cmsUser.setTeacherAppAccount(userByName.getUsername());
        }
        if (cmsUser.getRoleType().equals(5) && StringUtils.isNotBlank(cmsUser.getSchoolId())) {//学校负责人账号返回对应ID以及老师app账号
            PartnerSchool partnerSchool = partnerSchoolService.selectPartnerSchoolById(cmsUser.getSchoolId());
            cmsUser.setSchoolName(partnerSchool.getName());
        }
        return ResponseUtil.ok(cmsUser);
    }

    @ApiOperation(value = "删除后台用户", notes = "删除后台用户（数据软删）")
    @DeleteMapping("/deleteCmsUser/{id}")
    public ResponseEntity<Body<Void>> deleteEventInfo(@ApiParam("用户ID") @PathVariable String id) {
        cmsUserService.deleteCmsUser(id);
        return ResponseUtil.ok();
    }

    @ApiOperation(value = "后台用户登陆", notes = "通过用户信息进行登陆")
    @PostMapping("/login")
    public ResponseEntity<Body<CmsUser>> login(final UserDto userDto) {
        if (StringUtils.isBlank(userDto.getUsername()) || StringUtils.isBlank(userDto.getPassword())) {
            return ResponseUtil.notFound();
        }
        CmsUser cmsUser = cmsUserService.findCmsUserByUserName(userDto.getUsername());
        if (cmsUser == null) {
            return ResponseUtil.notFound("未找到用户信息");
        } else {
            if (!userDto.getPassword().equals(cmsUser.getPassword())) {
                return ResponseUtil.badRequest("密码错误！");
            }
            if (cmsUser.getStatus().equals(1)) {
                return ResponseUtil.ok(cmsUser);
            } else {
                return ResponseUtil.forbidden("该用户暂未开通！请联系管理人员进行开通！");
            }
        }
    }

    /**
     * 修改用户密码
     *
     * @return
     * @throws IOException
     */
    @ApiOperation(value = "修改用户密码", notes = "修改用户密码")
    @PostMapping("/modifyPassword")
    public ResponseEntity<Body<String>> modifyPassword(
            String id,
            String oldPassword,
            String newPassword
    ) {
        CmsUser newUser = cmsUserService.selectCmsUserById(id);
        if (newUser == null) {
            return ResponseUtil.of(HttpStatus.OK, "The account is not exist.");
        }
        if (StringUtils.isNotBlank(newPassword)) {
            if (newUser.getPassword().equals(oldPassword)) {
                newUser.setPassword(newPassword);
            } else {
                return ResponseUtil.of(HttpStatus.OK, "check Pwd fail");
            }
        }
        cmsUserService.saveCmsUser(newUser);
        return ResponseUtil.ok();
    }

    @ApiOperation(value = "完善资料信息", notes = "id 为后台用户ID")
    @PostMapping("/saveInformation")
    public ResponseEntity<Body<Void>> saveInformation(@ApiParam("后台用户信息") @RequestBody CmsUserInformation ui) {
        CmsUser user = cmsUserService.selectCmsUserById(ui.getId());
        if (user == null) {
            return ResponseUtil.of(HttpStatus.OK, "Re-login please.");
        }
        cmsUserService.modifyCmsUserInformation(CmsUser.builder().id(Integer.parseInt(ui.getId())).gender(ui.getGender())
                .birthday(ui.getBirthday()).name(ui.getName()).contact(ui.getContact()).build());
        return ResponseUtil.ok();
    }


}
