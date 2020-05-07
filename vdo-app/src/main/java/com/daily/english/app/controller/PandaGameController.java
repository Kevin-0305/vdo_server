package com.daily.english.app.controller;

import com.daily.english.app.domain.*;
import com.daily.english.app.dto.GameGoodsDto;
import com.daily.english.app.dto.GoodsDto;
import com.daily.english.app.dto.PetPropDto;
import com.daily.english.app.service.GoodsService;
import com.daily.english.app.service.PetService;
import com.daily.english.app.service.WordPkRecordService;
import com.daily.english.app.service.WordPkService;
import com.daily.english.app.util.Body;
import com.daily.english.app.util.ResponseUtil;
import com.daily.english.app.vo.GoodsVo;
import com.daily.english.app.vo.PetPropVo;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;


/**
 * game app
 *
 * @author liuzd
 * @create 2019-09-16 16:11
 **/
@Api(tags = "GAME APP")
@Slf4j
@RestController
@RequestMapping(value = "/game")
public class PandaGameController {

    @Value("${file.staticAccessPath}")
    private String staticAccessPath;
    @Value("${file.uploadFolder}")
    private String uploadFolder;

    @Autowired
    private WordPkRecordService wordPkRecordService;
    @Autowired
    private WordPkService wordPkService;
    @Autowired
    private PetService petService;
    @Autowired
    private GoodsService goodsService;


    @ApiOperation(value = "获取用户宠物信息", notes = "获取用户宠物信息")
    @GetMapping("/getPetData")
    public ResponseEntity<Body<Pet>> getPetData(
            @ApiParam("用户id") @RequestParam(required = true) String userId) {
        Pet userPet = petService.getUserPet(Integer.parseInt(userId));
        if (userPet == null){
            ResponseUtil.badRequest("用户ID不存在！");
        }
        return ResponseUtil.ok(userPet);
    }

    @ApiOperation(value = "添加单词PK记录", notes = "添加单词PK记录（返回PK记录ID用于领取竹币）")
    @PostMapping("/addWordPkRecord")
    public ResponseEntity<Body<String>> addWordPkRecord(
            @ApiParam("PK记录体") @RequestBody WordPkRecord wordPkRecord) {
        return ResponseUtil.ok(wordPkRecordService.addWordPkRecord(wordPkRecord));
    }

    @ApiOperation(value = "挑战成功领取竹币", notes = "挑战成功领取竹币")
    @PostMapping("/receiveBamboo")
    public ResponseEntity<Body<Void>> receiveBamboo(
            @ApiParam("挑战记录ID") @RequestParam(required = true) String pkRecordId) {
        wordPkRecordService.receiveBamboo(pkRecordId);
        return ResponseUtil.ok();
    }

    @ApiOperation(value = "获取单词PK对战数据", notes = "获取单词PK对战数据")
    @GetMapping("/getWordPkData")
    public ResponseEntity<Body<Map<String, Object>>> getWordPkData(
            @ApiParam("用户id") @RequestParam(required = true) String userId,
            @ApiParam("level级别") @RequestParam(required = true) String level) {
        Map<String, Object> resultMap = Maps.newHashMap();
        if (petService.checkUserWordCurrency(Integer.parseInt(userId)) == 0) {
            resultMap.put("isFlag", 0);
            resultMap.put("wordPkList", null);
            resultMap.put("otherOptionList", null);
            resultMap.put("robotInfo", null);
        } else {
            resultMap.put("isFlag", 1);
            resultMap.put("wordPkList", wordPkService.selectWordPkRandom(8, level));
            resultMap.put("otherOptionList", wordPkService.selectWordPkRandom(24, level));
            resultMap.put("robotInfo", wordPkService.selectRandomRobot());
        }
        return ResponseUtil.ok(resultMap);
    }

    @ApiOperation(value = "获取单词PK用户战绩", notes = "根据用户ID获取用户战绩")
    @GetMapping("/getUserWordPkRecord")
    public ResponseEntity<Body<Map<String, Object>>> getUserWordPkRecord(
            @ApiParam("用户id") @RequestParam(required = true) String userId) {
        Map<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("todayRecord", wordPkRecordService.userTodayRecordCount(Integer.parseInt(userId)));
        resultMap.put("record", wordPkRecordService.userRecordCount(Integer.parseInt(userId)));
        return ResponseUtil.ok(resultMap);
    }

    @ApiOperation(value = "获取商品列表", notes = "获取商品列表(分页)")
    @GetMapping("/queryGoodsList")
    public ResponseEntity<Body<PageInfo<GoodsVo>>> getGoodsPage(GoodsDto goodsDto) {
        goodsDto.setStatus(1);
        return ResponseUtil.ok(goodsService.selectGoodsPage(goodsDto));
    }

    @ApiOperation(value = "获取商品列表", notes = "获取商品列表(分页)")
    @GetMapping("/queryGameGoodsPage")
    public ResponseEntity<Body<PageInfo<GoodsVo>>> selectGameGoodsPage(GameGoodsDto gameGoodsDto) {
        return ResponseUtil.ok(goodsService.selectGameGoodsPage(gameGoodsDto));
    }

    @ApiOperation(value = "获取宠物的道具", notes = "获取宠物的道具(分页)")
    @GetMapping("/queryPetPropList")
    public ResponseEntity<Body<PageInfo<PetPropVo>>> getGoodsPage(PetPropDto petPropDto) {
        return ResponseUtil.ok(petService.selectPetPropPage(petPropDto));
    }

    @ApiOperation(value = "购买商品", notes = "购买商品：成功返回1，竹子不够返回2，无此商品返回3")
    @PostMapping("/buyGoods")
    public ResponseEntity<Body<Integer>> buyGoods(@ApiParam("购买商品参数体") @RequestBody PetOrderRecord orderRecord) {
        return ResponseUtil.ok(petService.buyGoods(orderRecord));
    }

    @ApiOperation(value = "宠物喂食", notes = "宠物喂食：成功返回1，道具不够返回2，无此道具返回3")
    @PostMapping("/feedPet")
    public ResponseEntity<Body<Integer>> feedPet(
            @ApiParam("宠物ID") @RequestParam(required = true) Integer petId,
            @ApiParam("背包道具ID") @RequestParam(required = true) Integer propId,
            @ApiParam("喂食次数") @RequestParam(required = true) Integer amount) {
        return ResponseUtil.ok(petService.feedPet(petId, propId, amount));
    }


    @ApiOperation(value = "导入zip机器人信息", notes = "导入zip机器人信息")
    @PostMapping("/robotZipUpload")
    public ResponseEntity<Body<Map<String, String>>> uploadImg(@RequestParam("file") MultipartFile file,
          @ApiParam("nickName") @RequestParam(required = true) String nickName) throws IOException {
        if (file != null && !file.isEmpty()) {
            Map<String, String> map = Maps.newHashMap();
            petService.robotZipUpload(file, uploadFolder, nickName);
            return ResponseUtil.ok(map);
        } else {
            return ResponseUtil.of(HttpStatus.OK, "文件为空");
        }
    }


}
