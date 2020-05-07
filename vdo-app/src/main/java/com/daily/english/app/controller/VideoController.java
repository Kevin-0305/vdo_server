package com.daily.english.app.controller;

import com.daily.english.app.condition.VideoCondition;
import com.daily.english.app.domain.Collect;
import com.daily.english.app.domain.User;
import com.daily.english.app.domain.UserStudy;
import com.daily.english.app.domain.Video;
import com.daily.english.app.service.UserService;
import com.daily.english.app.service.UserStudyService;
import com.daily.english.app.service.VideoService;
import com.daily.english.app.util.Body;
import com.daily.english.app.util.DateUtil;
import com.daily.english.app.util.ResponseUtil;
import com.daily.english.app.util.VideoUtil;
import com.daily.english.app.vo.CollectVo;
import com.daily.english.app.vo.VideoVo;
import com.google.common.collect.Maps;
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

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Api(tags = "视频")
@Slf4j
@RestController
@RequestMapping(value = "/videos")
public class VideoController {
    @Autowired
    private VideoService videoService;
    @Autowired
    private UserService userService;
    @Autowired
    private MapperFacade mapper;
    @Autowired
    private UserStudyService userStudyeService;

    @ApiOperation(value = "新增/更新视频", notes = "新增/更新视频")
    @PostMapping("/save")
    public ResponseEntity<Body<Void>> saveVideo(
            @ApiParam("视频体") @RequestBody Video video) {
        if (video.getId() == null || video.getId() <= 0) {
            videoService.saveVideo(video);
        } else {
            videoService.modifyVideo(video);
        }
        return ResponseUtil.ok();
    }

    @ApiOperation(value = "删除视频", notes = "通过试卷id删除试卷")
    @DeleteMapping("/del/{videoId}")
    public ResponseEntity<Body<Void>> delVideo(
            @ApiParam("视频ID") @PathVariable Long videoId) {
        videoService.removeVideo(videoId);
        return ResponseUtil.ok();
    }


    @ApiOperation(value = "发布视频", notes = "视频id发布视频")
    @GetMapping("/releaseVideo/{videoId}")
    public ResponseEntity<Body<Void>> isReleaseVideo(
            @ApiParam("视频ID") @PathVariable Long videoId) {
        Video video = Video.builder().id(videoId).isRelease(2).build();
        videoService.updateVideoIsRelease(video);
        return ResponseUtil.ok();
    }

    @ApiOperation(value = "下架视频", notes = "视频id下架视频")
    @GetMapping("/downVideo/{videoId}")
    public ResponseEntity<Body<Void>> downVideo(
            @ApiParam("视频ID") @PathVariable Long videoId) {
        Video video = Video.builder().id(videoId).isRelease(0).build();
        videoService.updateVideoIsRelease(video);
        return ResponseUtil.ok();
    }

    @ApiOperation(value = "热门视频设置", notes = "视频id下架视频")
    @GetMapping("/hotVideo/{videoId}")
    public ResponseEntity<Body<Void>> hotVideo(
            @ApiParam("视频ID") @PathVariable Long videoId) {
        Video videoById = videoService.getVideoById(videoId);
        if (videoById.getHot().equals("1")) {
            videoById.setHot("0");
        } else {
            videoById.setHot("1");
        }

        videoService.updateVideoHot(videoById);
        return ResponseUtil.ok();
    }

    @ApiOperation(value = "精选视频设置", notes = "视频id下架视频")
    @GetMapping("/editorPickVideo/{videoId}")
    public ResponseEntity<Body<Void>> editorPickVideo(
            @ApiParam("视频ID") @PathVariable Long videoId) {
        Video videoById = videoService.getVideoById(videoId);
        if (videoById.getEditorPick().equals("1")) {
            videoById.setEditorPick("0");
        } else {
            videoById.setEditorPick("1");
        }
        videoService.updateVideoEditorPick(videoById);
        return ResponseUtil.ok();
    }


    @ApiOperation(value = "根据视频标题查询视频列表", notes = "根据视频标题查询视频列表")
    @GetMapping("/searchTitle/{title}")
    public ResponseEntity<Body<List<VideoVo>>> getVideo(
            @ApiParam("视频标题") @PathVariable String title) {
        List<Video> videoList = videoService.getVideoListByHintTitle(title);
        List<VideoVo> videoVoList = mapper.mapAsList(videoList, VideoVo.class);
        return ResponseUtil.ok(videoVoList);
    }

    @ApiOperation(value = "获取视频总数", notes = "获取视频总数")
    @GetMapping("/page/count")
    public ResponseEntity<Body<Integer>> getVideoCount(
            @ApiParam("视频类型") @RequestParam(required = false) String type,
            @ApiParam("视频难度") @RequestParam(required = false) String level,
            @ApiParam("视频hint title") @RequestParam(required = false) String key) {
        if (StringUtils.isNotBlank(type) && type.equals("0")) {
            type = "";
        }
        if (StringUtils.isNotBlank(level) && level.equals("0")) {
            level = "";
        }
        VideoCondition videoCondition = VideoCondition.builder()
                .title(key).type(type).level(level)
                .build();
        Integer videoCount = videoService.getVideoCount(videoCondition);
        return ResponseUtil.ok(videoCount);
    }

    @ApiOperation(value = "获取视频列表", notes = "获取视频列表(分页)")
    @GetMapping("/page/{pageNo}/{limit}")
    public ResponseEntity<Body<Map<String, Object>>> getVideoPage(
            @ApiParam("页号") @PathVariable Integer pageNo,
            @ApiParam("页行数") @PathVariable Integer limit,
            @ApiParam("视频类型") @RequestParam(required = false) String type,
            @ApiParam("视频难度") @RequestParam(required = false) String level,
            @ApiParam("视频hint title") @RequestParam(required = false) String key) {
        if (pageNo == null || pageNo <= 0) {
            pageNo = 1;
        }
        int offset = (pageNo - 1) * limit;
        if (StringUtils.isNotBlank(type) && type.equals("0")) {
            type = "";
        }
        if (StringUtils.isNotBlank(level) && level.equals("0")) {
            level = "";
        }
        VideoCondition videoCondition = VideoCondition.builder()
                .title(key).type(type).level(level).offset(offset).limit(limit)
                .build();
        List<Video> videoList = videoService.getVideoListByPage(videoCondition);
        List<VideoVo> videoVoList = mapper.mapAsList(videoList, VideoVo.class);
        Integer videoCount = videoService.getVideoCount(videoCondition);

        Map<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("videoList", videoVoList);
        resultMap.put("count", videoCount);
        return ResponseUtil.ok(resultMap);
    }

    @ApiOperation(value = "获取视频列表", notes = "获取视频列表(分页)")
    @GetMapping("/appPage/{pageNo}/{limit}")
    public ResponseEntity<Body<Map<String, Object>>> getVideoAppPage(
            @ApiParam("页号") @PathVariable Integer pageNo,
            @ApiParam("页行数") @PathVariable Integer limit,
            @ApiParam("视频类型") @RequestParam(required = false) String type,
            @ApiParam("视频难度") @RequestParam(required = false) String level,
            @ApiParam("热门推荐（默认不传）要查询时传：1") @RequestParam(required = false) String hot,
            @ApiParam("精选视频（默认不传）要查询时传：1") @RequestParam(required = false) String editorPick,
            @ApiParam("视频hint title") @RequestParam(required = false) String key) {
        if (pageNo == null || pageNo <= 0) {
            pageNo = 1;
        }
        int offset = (pageNo - 1) * limit;
        if (StringUtils.isNotBlank(type) && type.equals("0")) {
            type = "";
        }
        if (StringUtils.isNotBlank(level) && level.equals("0")) {
            level = "";
        }
        VideoCondition videoCondition = VideoCondition.builder().isRelease(2)
                .title(key).hot(hot).editorPick(editorPick)
                .type(type).level(level).offset(offset).limit(limit)
                .build();
        List<Video> videoList = videoService.getVideoListByPage(videoCondition);
        List<VideoVo> videoVoList = mapper.mapAsList(videoList, VideoVo.class);
        Integer videoCount = videoService.getVideoCount(videoCondition);

        Map<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("videoList", videoVoList);
        resultMap.put("count", videoCount);
        return ResponseUtil.ok(resultMap);
    }

    @ApiOperation(value = "获取视频详情", notes = "通过视频id获得视频详情")
    @GetMapping("/info/{videoId}")
    public ResponseEntity<Body<VideoVo>> getVideo(
            @ApiParam("视频ID") @PathVariable Long videoId,
            @ApiParam("用户ID") @RequestParam(required = false) String userId) {
        Video video = videoService.getVideoById(videoId);
        if (video == null) {
            return ResponseUtil.of(HttpStatus.OK, "视频为空");
        }
        //更新视频浏览量 +9/次
        video.setBrowse(video.getBrowse() + 9);
        videoService.updateVideoBrowse(video);
        VideoVo videoVo = mapper.map(video, VideoVo.class);
        User user = userService.findUserById(userId);
        if (user != null) {
            Collect collect = Collect.builder().uid(Integer.parseInt(user.getId())).vid(video.getId().intValue()).type("collect").build();
            videoVo.setIsCollect(videoService.getVideoCollectCount(collect) > 0 ? 1 : 0);
            collect.setType("praise");
            videoVo.setIsPraise(videoService.getVideoCollectCount(collect) > 0 ? 1 : 0);

        }
        return ResponseUtil.ok(videoVo);
    }

    @ApiOperation(value = "统计用户查看视频", notes = "提交接口")
    @PostMapping("/videoCount/{videoId}")
    public ResponseEntity<Body<String>> videoCount(
            @ApiParam("视频ID") @PathVariable Long videoId,
            @ApiParam("用户ID") @RequestBody String userId) {
        Video video = videoService.getVideoById(videoId);
        if (video == null) {
            return ResponseUtil.of(HttpStatus.OK, "视频为空");
        }
        User user = userService.findUserById(userId);
        if (user == null) {
            return ResponseUtil.of(HttpStatus.OK, "用户不存在！");
        }
        UserStudy userStudy = userStudyeService.findUserMapById(userId);
        userStudy.setFinishVdoAll(userStudy.getFinishVdoAll() + 1);
        userStudy.setFinishVdoWeek(userStudy.getFinishVdoWeek() + 1);
        userStudyeService.modifyUserStudy(userStudy);
        return ResponseUtil.ok("");
    }

    @Value("${file.staticAccessPath}")
    private String staticAccessPath;

    @Value("${file.uploadFolder}")
    private String uploadFolder;

    @ApiOperation(value = "上传图片", notes = "上传图片")
    @PostMapping("/upload/img")
    public ResponseEntity<Body<Map<String, String>>> uploadImg(@RequestParam("file") MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            String extension = FilenameUtils.getExtension(fileName);
            fileName = DateUtil.getRandom() + "." + extension;
            String relativePath = "/videoImg/";
            String pathdir = uploadFolder + relativePath;
            File dest = new File(pathdir, fileName);
            if (!dest.getParentFile().exists()) { //判断文件父目录是否存在
                dest.getParentFile().mkdir();
            }
            FileUtils.copyInputStreamToFile(file.getInputStream(), dest);

            Map<String, String> map = Maps.newHashMap();
            map.put("fileUrl", "/static/image" + relativePath + fileName);
            return ResponseUtil.ok(map);
        } else {
            return ResponseUtil.of(HttpStatus.OK, "图片为空");
        }
    }

    @ApiOperation(value = "上传视频", notes = "上传视频")
    @PostMapping("/upload/videoFile")
    public ResponseEntity<Body<Map<String, String>>> uploadVideoFile(@RequestParam("file") MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            String extension = FilenameUtils.getExtension(fileName);
            fileName = DateUtil.getRandom() + "." + extension;
            String relativePath = "/video/";
            String pathdir = uploadFolder + relativePath;
            File dest = new File(pathdir, fileName);
            if (!dest.getParentFile().exists()) { //判断文件父目录是否存在
                dest.getParentFile().mkdir();
            }
            FileUtils.copyInputStreamToFile(file.getInputStream(), dest);

            Map<String, String> map = Maps.newHashMap();
            map.put("fileUrl", "/static/image" + relativePath + fileName);
            map.put("videoTime", VideoUtil.ReadVideoTime(uploadFolder + relativePath + fileName));

            return ResponseUtil.ok(map);
        } else {
            return ResponseUtil.of(HttpStatus.OK, "图片为空");
        }
    }

    @ApiOperation(value = "获取视频时长", notes = "获取视频时长")
    @PostMapping("/readVideoTime")
    public ResponseEntity<Body<Map<String, String>>> readVideoTime(@RequestParam("filePath") String filePath) throws IOException {
        Map<String, String> map = Maps.newHashMap();
        map.put("videoTime", VideoUtil.ReadVideoTime(uploadFolder + filePath.replaceAll("/static/image", "")));
        return ResponseUtil.ok(map);
    }


    @ApiOperation(value = "上传缩略图图片", notes = "上传缩略图图片")
    @PostMapping("/upload/thumb")
    public ResponseEntity<Body<Map<String, String>>> uploadThumb(@RequestParam("file") MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            String extension = FilenameUtils.getExtension(fileName);
            fileName = DateUtil.getRandom() + "." + extension;
            String relativePath = "/videoThumb/";
            String pathdir = uploadFolder + relativePath;
            File dest = new File(pathdir, fileName);
            if (!dest.getParentFile().exists()) { //判断文件父目录是否存在
                dest.getParentFile().mkdir();
            }
            FileUtils.copyInputStreamToFile(file.getInputStream(), dest);

            Map<String, String> map = Maps.newHashMap();
            map.put("fileUrl", "/static/image" + relativePath + fileName);
            return ResponseUtil.ok(map);
        } else {
            return ResponseUtil.of(HttpStatus.OK, "图片为空");
        }
    }

    @ApiOperation(value = "移动端  收藏视频或点赞视频", notes = "收藏视频type传 collect ,点赞传 praise")
    @PostMapping("/videoCollect")
    public ResponseEntity<Body<Void>> videoCollect(@ApiParam("收藏体") @RequestBody CollectVo collect) {
        if (collect == null) {
            return ResponseUtil.of(HttpStatus.BAD_REQUEST, "参数错误");
        }
        Collect c = mapper.map(collect, Collect.class);
        int code = videoService.saveVideoCollect(c);
        if (code == 1) {
            return ResponseUtil.ok();
        } else {
            return ResponseUtil.of(HttpStatus.BAD_REQUEST, "collect".equals(collect.getType()) ? "已收藏过了" : "已点赞过了");
        }
    }

    @ApiOperation(value = "移动端 删除收藏视频或取消点赞视频", notes = "点删 用户id,视频id,类型type")
    @DeleteMapping("/delVideoCollect")
    public ResponseEntity<Body<Void>> delVideoCollect(@ApiParam("收藏体") @RequestBody CollectVo collect) {
        if (collect == null) {
            return ResponseUtil.of(HttpStatus.BAD_REQUEST, "参数错误");
        }
        Collect c = mapper.map(collect, Collect.class);
        int code = videoService.removeVideoCollect(c);
        if (code == 1) {
            return ResponseUtil.ok();
        } else {
            return ResponseUtil.of(HttpStatus.BAD_REQUEST, "collect".equals(collect.getType()) ? "暂无收藏" : "暂无点赞");
        }
    }

}
