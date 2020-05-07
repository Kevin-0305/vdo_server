package com.daily.english.app.controller;

import com.daily.english.app.domain.User;
import com.daily.english.app.domain.UserRecord;
import com.daily.english.app.domain.UserValue;
import com.daily.english.app.service.UserRecordService;
import com.daily.english.app.service.UserService;
import com.daily.english.app.service.UserValueService;
import com.daily.english.app.util.Body;
import com.daily.english.app.util.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;

/**
 * @author zhc
 */
@Api(tags = "奖励相关")
@Slf4j
@RestController
@RequestMapping(value = "/userRecord")
public class UserRecordController {
    @Autowired
    private UserRecordService userRecordService;
    @Autowired
    private UserValueService userValueService;
    @Autowired
    private UserService userService;

    @ApiOperation(value = "由奖励ID领取相关东西", notes = "")
    @GetMapping("/getRecordById/{id}")
    public ResponseEntity<Body<String>> getRecordById(@ApiIgnore HttpSession httpSession,
                                                   @ApiParam("奖励id") @PathVariable String id) {
        Object nowRecordById = httpSession.getAttribute("nowRecordById");
        if(nowRecordById != null){
            try {
                Thread.sleep(1500);
                httpSession.setAttribute("nowRecordById",null);
                return ResponseUtil.of(HttpStatus.BAD_REQUEST, "请求过多","Too many requests","請求過多");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        httpSession.setAttribute("nowRecordById",id);
        UserRecord userRecord = userRecordService.findUserRecordById(id);
        if (userRecord == null) {
            httpSession.setAttribute("nowRecordById",null);
            return ResponseUtil.of(HttpStatus.BAD_REQUEST, "奖励无效","Reward ineffective","獎勵無效");
        }
        if (userRecord.getIsTake() == 1) {
            httpSession.setAttribute("nowRecordById",null);
            return ResponseUtil.of(HttpStatus.BAD_REQUEST, "奖励已领取过了","Reward ineffective","獎勵無效");
        }
        userRecord.setIsTake(1L);
        userRecordService.updateUserRecord(userRecord);
        UserValue userValue = userValueService.findUserValueById(userRecord.getUserId().toString());
        if("exp".equals(userRecord.getType())){
              userValue.setExp(userValue.getExp()+userRecord.getChangeValue());
              userValue.setGetExpToday(userValue.getGetExpToday()+ userRecord.getChangeValue());
        }
        if("bamboo".equals(userRecord.getType())){
            userValue.setBamboo(userValue.getBamboo()+userRecord.getChangeValue());
            userValue.setGetBambooToday(userValue.getGetBambooToday() +userRecord.getChangeValue());
            userValue.setGetBambooAll(userValue.getGetBambooAll() + userRecord.getChangeValue());
        }
        userValueService.updateUserValue(userValue);
        httpSession.setAttribute("nowRecordById",null);
        return ResponseUtil.ok("");
    }
    //扣除相关奖励
    @ApiOperation(value = "扣除相关奖励", notes = "")
    @PostMapping("/takeOutRecord/{userId}/{type}/{flag}")
    public ResponseEntity<Body<String>> takeOutRecord(@ApiIgnore HttpSession httpSession,@ApiParam("userId") @PathVariable String userId,
                                                    @ApiParam("扣除类型:1 求助") @PathVariable int flag,
                                                    @ApiParam("求助题 用试卷和小题数拼接起来，例（ 22f3f1re3_2）") @RequestParam(required =  false) String remark,
                                                    @ApiParam("1 exp 或2 bamboo") @PathVariable int type) {
        User user = userService.findUserById(userId);
        if (user == null) {
            return ResponseUtil.of(HttpStatus.PRECONDITION_FAILED, "Re-login please.");
        }
        Object nowRecordById = httpSession.getAttribute("nowRecordById");
        if(nowRecordById != null){
            try {
                Thread.sleep(1500);
                httpSession.setAttribute("nowRecordById",null);
                return ResponseUtil.of(HttpStatus.BAD_REQUEST, "请求过多","Too many requests","請求過多");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        httpSession.setAttribute("nowRecordById",flag);
        Long exp = 0L;
        Long bamboo = 0L;
        switch (flag){
            case 1:
                if(1 == type){
                    exp = 10L;
                }else{
                    bamboo = 5L;
                }
                break;
        }
        UserValue userValue = userValueService.findUserValueById(userId);
        if(2 == type ){

            UserRecord userRecord = UserRecord.builder().userId(userValue.getId()).type("bamboo").changeValue(bamboo * -1)
                    .beginValue(userValue.getBamboo())
                    .targetValue(userValue.getBamboo()- bamboo)
                    .sourceId(remark).isTake(1l)
                    .content("求助")
                    .build();
/*            if(userRecordService.findUserRecord(userRecord) != null){
                httpSession.setAttribute("nowRecordById",null);
                return ResponseUtil.of(HttpStatus.OK,"已求助过了","Help has been sought);","已求助過了");
            }*/
            if(userValue.getBamboo()< bamboo){
                httpSession.setAttribute("nowRecordById",null);
                return ResponseUtil.of(HttpStatus.BAD_REQUEST, "竹子不足！","Bamboo is insufficient","竹子不足！");
            }
            userValue.setBamboo(userValue.getBamboo()-bamboo);
            userValue.setUseBambooToday(userValue.getUseBambooToday()+ bamboo);
            userValue.setUseBambooAll(userValue.getUseBambooAll()+ bamboo);
            userRecordService.insertUserRecord(userRecord);
        }
        userValueService.updateUserValue(userValue);

        httpSession.setAttribute("nowRecordById",null);
        return ResponseUtil.ok("");
    }

}
