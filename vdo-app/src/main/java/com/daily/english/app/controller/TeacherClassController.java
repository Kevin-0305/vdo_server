package com.daily.english.app.controller;

import com.daily.english.app.domain.PartnerClass;
import com.daily.english.app.domain.StudyExamInfo;
import com.daily.english.app.dto.StudyExamInfoDto;
import com.daily.english.app.dto.TeacherClassDto;
import com.daily.english.app.service.AnswerService;
import com.daily.english.app.service.ExamInfoService;
import com.daily.english.app.service.MapService;
import com.daily.english.app.service.PartnerClassService;
import com.daily.english.app.util.Body;
import com.daily.english.app.util.ResponseUtil;
import com.daily.english.app.vo.*;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.ArrayListMultimap;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 老师班级管理
 *
 * @author
 * @create 2019-10-24 10:56
 **/
@Api(tags = "老师班级管理")
@Slf4j
@RestController
@RequestMapping(value = "/teacherClass")
public class TeacherClassController {
    @Autowired
    private PartnerClassService partnerClassService;
    @Autowired
    private MapService mapService;
    @Autowired
    private ExamInfoService examInfoService;
    @Autowired
    private AnswerService answerService;

    @ApiOperation(value = "查询老师、学校负责人管理所有班级", notes = "查询老师、学校负责人管理所有班级(分页)")
    @GetMapping("/findTeacherClassPage")
    public ResponseEntity<Body<PageInfo<TeacherClassVo>>> findTeacherClassPage(TeacherClassDto teacherClassDto) {
        return ResponseUtil.ok(partnerClassService.findTeacherClassPage(teacherClassDto));
    }

    @ApiOperation(value = "查询老师 学校负责人管理所有班级学生考试信息", notes = "(分页)")
    @GetMapping("/findStudyByTeacherIdPage")
    public ResponseEntity<Body<StudyInfoVo>> findStudyByTeacherIdPage(StudyExamInfoDto studyExamInfoDto) {
        PageInfo<StudyExamInfo> studyExamInfos = partnerClassService.queryStudyExamInfo(studyExamInfoDto);
        //下面班级
        List<PartnerClass> partnerClasses = partnerClassService.queryClassById(studyExamInfoDto.getUid(), studyExamInfoDto.getSchoolId());
        StudyInfoVo studyInfoVo = StudyInfoVo.builder().pageInfo(studyExamInfos)
                .classList(partnerClasses).build();
        return ResponseUtil.ok(studyInfoVo);
    }

    @ApiOperation(value = "班级考试信息图表数据")
    @GetMapping("/findStudyAverageByCheckpoints")
    public ResponseEntity<Body<StudyInfoVo>> findStudyAverageByCheckpoints(@ApiParam(value = "1-6", example = "1") @RequestParam(required = false) String level,
                                                                           @ApiParam(value = "classId") @RequestParam(required = false) String classId, @ApiParam(value = "老师ID") @RequestParam(required = false) String userId, @ApiParam(value = "学校负责人") @RequestParam(required = false) String schoolId,
                                                                           @ApiParam(value = "关卡开始", example = "1") @RequestParam String checkpointsStart, @ApiParam(value = "1为首考分数，0为最高分分数", example = "1") @RequestParam String scorceType,
                                                                           @ApiParam(value = "关卡结束", example = "4") @RequestParam String checkpointsEnd) {

        StudyExamInfoDto studyExamInfoDto = new StudyExamInfoDto();
        studyExamInfoDto.setScorceType(scorceType);
        studyExamInfoDto.setCheckpointsStart(checkpointsStart);
        studyExamInfoDto.setCheckpointsEnd(checkpointsEnd);
        if (Strings.isNotEmpty(level)) {
            studyExamInfoDto.setLevel(level);
        }
        if (Strings.isNotEmpty(userId)) {
            studyExamInfoDto.setUid(userId);
        }
        if (Strings.isNotEmpty(schoolId)) {
            studyExamInfoDto.setSchoolId(schoolId);
        }
        if (Strings.isNotEmpty(classId)) {
            studyExamInfoDto.setClasss(classId);
        } else {
            studyExamInfoDto.setClasss(null);
        }
        StudyAverageVo studyAverageVo = partnerClassService.queryStudyAverageExamInfo(studyExamInfoDto);
        //下面班级
        List<PartnerClass> partnerClasses = partnerClassService.queryClassById(studyExamInfoDto.getUid(), studyExamInfoDto.getSchoolId());

        StudyInfoVo studyInfoVo = StudyInfoVo.builder().studyAverageVo(studyAverageVo).classList(partnerClasses).build();
        return ResponseUtil.ok(studyInfoVo);
    }

    @ApiOperation(value = "获取学校老师班级下学生闯关最大值")
    @GetMapping("/getTeacherCheckpointMax")
    public ResponseEntity<Body<Integer>> getTeacherCheckpointMax(@ApiParam(value = "年级") @RequestParam(required = false) String grade,
                                                                 @ApiParam(value = "classId") @RequestParam(required = false) String classId,
                                                                 @ApiParam(value = "level") @RequestParam(required = false) String level,
                                                                 @ApiParam(value = "老师ID") @RequestParam(required = false) String userId,
                                                                 @ApiParam(value = "学校负责人") @RequestParam(required = false) String schoolId) {
        return ResponseUtil.ok(partnerClassService.getTeacherCheckpointMax(grade,level,classId,userId,schoolId));
    }

    @ApiOperation(value = "获取学生在当前难度下闯关最大值")
    @GetMapping("/getStudentCheckpointMax")
    public  ResponseEntity<Body<Integer>> getStudentCheckpointMax(@ApiParam(value = "level") @RequestParam(required = false) String level,
                                                                  @ApiParam(value = "学生ID") @RequestParam(required = false) Integer userId){
        return  ResponseUtil.ok(partnerClassService.getStudentCheckpointMax(level,userId));
    }

    @ApiOperation(value = "查询学生考试信息")
    @GetMapping("/querySturyInfoByLevel/{userId}/{scorceType}")
    public ResponseEntity<Body<List<StudyExamInfo>>> querySturyInfoByLevel(@ApiParam(value = "level(1-6) 不传默认全部", example = "1") @RequestParam(required = false) String level,
                                                                           @ApiParam(value = "关卡开始", example = "1") @RequestParam String checkpointsStart,
                                                                           @ApiParam(value = "关卡结束", example = "4") @RequestParam String checkpointsEnd,
                                                                           @ApiParam(value = "分数范围") @RequestParam(required = false) String scoreRangeStart,
                                                                           @ApiParam(value = "分数范围") @RequestParam(required = false) String scoreRangeEnd,
                                                                           @ApiParam(value = "userId", required = true) @PathVariable String userId,
                                                                           @ApiParam(value = "scorceType(1是按首考分数排/0是按历史最高分排) ", example = "1", required = true) @PathVariable String scorceType) {
        StudyExamInfoDto studyExamInfoDto = new StudyExamInfoDto();
        studyExamInfoDto.setUserId(userId);
        studyExamInfoDto.setScorceType(scorceType);
        studyExamInfoDto.setCheckpointsStart(checkpointsStart);
        studyExamInfoDto.setCheckpointsEnd(checkpointsEnd);
        studyExamInfoDto.setScoreRangeStart(scoreRangeStart);
        studyExamInfoDto.setScoreRangeEnd(scoreRangeEnd);
        studyExamInfoDto.setLevel(level);
        List<StudyExamInfo> studyExamInfos = partnerClassService.queryStudyExamInfoById(studyExamInfoDto);
        for (StudyExamInfo s : studyExamInfos) {
            s.setCheckpoint(s.getLevel() + "-" + s.getNodeNum());
        }
        return ResponseUtil.ok(studyExamInfos);
    }

    @ApiOperation(value = "查询学生历史考试信息图表")
    @GetMapping("/querySturyInfoHistoricalChart/{userId}/{scorceType}")
    public ResponseEntity<Body<List<ScoreChartVo>>> querySturyInfoHistoricalChart(@ApiParam(value = "level(1-6) 不传默认全部", example = "1") @RequestParam(required = false) String level,
                                                                                  @ApiParam(value = "userId", required = true) @PathVariable String userId,
                                                                                  @ApiParam(value = "关卡开始", example = "1") @RequestParam String checkpointsStart,
                                                                                  @ApiParam(value = "关卡结束", example = "4") @RequestParam String checkpointsEnd,
                                                                                  @ApiParam(value = "1 =part1 comprehension；（0=总分 1-3）3 = grammar", example = "0", required = true) @RequestParam String part,
                                                                                  @ApiParam(value = "scorceType(1是按首考分数/0是按历史最高分) ", example = "1", required = true) @PathVariable String scorceType) {

        StudyExamInfoDto studyExamInfoDto = new StudyExamInfoDto();
        studyExamInfoDto.setUserId(userId);
        studyExamInfoDto.setScorceType(scorceType);
        studyExamInfoDto.setCheckpointsStart(checkpointsStart);
        studyExamInfoDto.setCheckpointsEnd(checkpointsEnd);
        studyExamInfoDto.setLevel(level);
        List<ScoreChartVo> scoreChartVos = partnerClassService.queryStudyExamInfoChart(studyExamInfoDto, part);
        return ResponseUtil.ok(scoreChartVos);
    }

    @ApiOperation(value = "查询学生考试平均成绩信息图表")
    @GetMapping("/querySturyInfoAverageChart/{userId}/{scorceType}")
    public ResponseEntity<Body<StudyAverageVo>> querySturyInfoAverageChart(@ApiParam(value = "level(1-6) 不传默认全部", example = "1") @RequestParam(required = false) String level,
                                                                           @ApiParam(value = "userId", required = true) @PathVariable String userId,
                                                                           @ApiParam(value = "关卡开始", example = "1") @RequestParam String checkpointsStart,
                                                                           @ApiParam(value = "关卡结束", example = "4") @RequestParam String checkpointsEnd,
                                                                           @ApiParam(value = "scorceType(1是按首考分数/0是按历史最高分) ", example = "1", required = true) @PathVariable String scorceType) {

        StudyExamInfoDto studyExamInfoDto = new StudyExamInfoDto();
        studyExamInfoDto.setUserId(userId);
        studyExamInfoDto.setScorceType(scorceType);
        studyExamInfoDto.setLevel(level);
        studyExamInfoDto.setCheckpointsStart(checkpointsStart);
        studyExamInfoDto.setCheckpointsEnd(checkpointsEnd);
        StudyAverageVo studyAverageVo = partnerClassService.querySignStudyAverageChart(studyExamInfoDto);
        return ResponseUtil.ok(studyAverageVo);
    }
    @ApiOperation(value = "获取级别等联动条件",notes = "条件【Grade】【Level】【Class】联动")
    @GetMapping("/queryLevelByGrade/{grade}")
    public ResponseEntity<Body<TeacherClassLinkageVo>> queryLevelByGrade(
            @ApiParam(value = "level(1-6) 不传默认全部") @RequestParam(required = false) String level,
            @ApiParam(value = "老师ID") @RequestParam(required = false) String userId,
            @ApiParam(value = "负责人ID") @RequestParam(required = false) String schoolId,
            @ApiParam(value = "grade", required = true) @PathVariable String grade) {
       TeacherClassLinkageVo teacherClassLinkageVo  = partnerClassService.queryClassByUserId(userId,schoolId,level,grade);

        return ResponseUtil.ok(teacherClassLinkageVo);
    }
    @ApiOperation(value = "获取学生详细信息",notes = "")
    @GetMapping("/queryStudentInfoById/{studentId}")
    public ResponseEntity<Body<Map<String,String>>> queryStudentInfoById(
            @ApiParam(value = "studentId", required = true) @PathVariable String studentId) {
        Map<String,String> student = partnerClassService.queryStudentInfoById(studentId);
        return ResponseUtil.ok(student);
    }
}
