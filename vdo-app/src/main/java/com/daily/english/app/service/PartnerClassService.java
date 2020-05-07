package com.daily.english.app.service;

import com.daily.english.app.domain.*;
import com.daily.english.app.dto.PartnerClassDto;
import com.daily.english.app.dto.StudyExamInfoDto;
import com.daily.english.app.dto.TeacherClassDto;
import com.daily.english.app.mapper.*;
import com.daily.english.app.util.DateUtil;
import com.daily.english.app.vo.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.iterators.ArrayListIterator;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Map;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Service
public class PartnerClassService {

    @Autowired
    private PartnerClassMapper partnerClassMapper;
    @Autowired
    private PartnerCodeMapper partnerCodeMapper;
    @Autowired
    private BaseCodeMapper baseCodeMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserMapMapper userMapMapper;
    @Autowired
    private FirstExamMapper firstExamMapper;


    public List<PartnerClass> selectPartnerClassList(PartnerClass partnerClass) {
        return partnerClassMapper.selectList(partnerClass);
    }

    public PageInfo<TeacherClassVo> findTeacherClassPage(TeacherClassDto teacherClassDto) {
        if (teacherClassDto.getSchoolId() == null) {//老师角色查询
            PartnerCode partnerCode = null;
            if (teacherClassDto.getTeacherUserId() != null) {
                partnerCode = PartnerCode.builder().userId(teacherClassDto.getTeacherUserId()).build();
                List<PartnerCode> partnerCodes = partnerCodeMapper.selectList(partnerCode);
                if (partnerCodes.size() > 0) {
                    PageHelper.startPage(teacherClassDto.getPageNo(), teacherClassDto.getPageSize());
                    teacherClassDto.setTeacherUserId(partnerCodes.get(0).getId().toString());//将用户id转成老师code进行查询
                    List<TeacherClassVo> list = setStudentInfo(teacherClassDto);
                    PageInfo<TeacherClassVo> pageInfo = new PageInfo(list, 5);
                    return pageInfo;
                } else {
                    List<TeacherClassVo> list = new ArrayList<>();
                    PageInfo<TeacherClassVo> pageInfo = new PageInfo(list, 5);
                    return pageInfo;
                }
            } else {
                List<TeacherClassVo> list = new ArrayList<>();
                PageInfo<TeacherClassVo> pageInfo = new PageInfo(list, 5);
                return pageInfo;
            }
        } else {//学校角色查询
            List<TeacherClassVo> list = setStudentInfo(teacherClassDto);
            PageInfo<TeacherClassVo> pageInfo = new PageInfo(list, 5);
            return pageInfo;
        }
    }

    /**
     * 查询班级信息返回学生集合和人数
     *
     * @param teacherClassDto
     * @return
     */
    private List<TeacherClassVo> setStudentInfo(TeacherClassDto teacherClassDto) {
        List<TeacherClassVo> list = partnerClassMapper.selectTeacherClassPageList(teacherClassDto);
        for (TeacherClassVo classVo : list) {
            //班级下所有学生
            List<StudentMapVo> studentMapVos = partnerCodeMapper.selectStudentMapByClassId(classVo.getClassId().toString());
            if (studentMapVos.size() > 0) {
                classVo.setStudentNumber(studentMapVos.size());
                String[] levels = classVo.getLevel().split(",");//获取第班级开通关卡
                if (levels.length > 0) {
                    for (int i = 0; i < levels.length; i++) {//循环每个级别，将每个级别闯关的最大
                        List<LevelCheckpointVo> levelNodeNumRanking = firstExamMapper.getLevelNodeNumRanking(classVo.getClassId().toString(), levels[i]);
                        Integer levelCheckpointValue = getLevelCheckpointValue(studentMapVos.size(), levelNodeNumRanking);
                        if (levels[i].equals("S1")) {
                            classVo.setCheckpointProgressS1(levelCheckpointValue);
                        } else if (levels[i].equals("S2")) {
                            classVo.setCheckpointProgressS2(levelCheckpointValue);
                        } else if (levels[i].equals("S3")) {
                            classVo.setCheckpointProgressS3(levelCheckpointValue);
                        } else if (levels[i].equals("S4")) {
                            classVo.setCheckpointProgressS4(levelCheckpointValue);
                        } else if (levels[i].equals("S5")) {
                            classVo.setCheckpointProgressS5(levelCheckpointValue);
                        } else if (levels[i].equals("S6")) {
                            classVo.setCheckpointProgressS6(levelCheckpointValue);
                        }
                    }
                }
            }
        }
        return list;
    }

    /**
     * 获取班级学生 MAP level 最多人数通关人数最多关卡
     *
     * @param studentSize 班级人数
     * @param list        level 统计列表
     * @return 返回关卡
     */
    private static Integer getLevelCheckpointValue(Integer studentSize, List<LevelCheckpointVo> list) {
        for (LevelCheckpointVo checkpointVo : list) {
            if (checkpointVo.getSumNum().doubleValue() / studentSize * 100 >= 20) {
                return checkpointVo.getNodeNum();
            }
        }
        return 1;
    }


    /**
     * 根据学校ID获取班级（分页）
     *
     * @param partnerClassDto
     * @return
     */
    public PageInfo<PartnerClass> findClassPageBySchoolId(PartnerClassDto partnerClassDto) {
        PageHelper.startPage(partnerClassDto.getPageNo(), partnerClassDto.getPageSize());
        List<PartnerClass> list = partnerClassMapper.selectPageList(partnerClassDto);
        for (PartnerClass partnerClass : list) {
            List<PartnerCode> studentList = partnerCodeMapper.selectStudentByClassId(partnerClass.getId().toString(), 1);
            partnerClass.setStudentCodeList(studentList);
        }
        PageInfo<PartnerClass> pageInfo = new PageInfo(list, 5);
        return pageInfo;
    }

    /**
     * 根据学校ID获取班级list
     *
     * @param schoolId
     * @return
     */
    public List<PartnerClass> findClassListBySchoolId(String schoolId) {
        PartnerClassDto partnerClassDto = new PartnerClassDto();
        partnerClassDto.setSchoolId(Integer.parseInt(schoolId));
        List<PartnerClass> list = partnerClassMapper.selectPageList(partnerClassDto);
        for (PartnerClass partnerClass : list) {
            List<PartnerCode> partnerCodes = partnerCodeMapper.selectStudentByClassId(partnerClass.getId().toString(), 1);
            partnerClass.setStudentCodeList(partnerCodes);
        }
        return list;
    }


    /**
     * 根据老师APP用户ID查询，用户下的所有班级
     *
     * @param teacherUserId 老师用户ID
     * @return
     */
    public List<PartnerClass> findClassListByUserId(String teacherUserId) {
        PartnerClassDto partnerClassDto = new PartnerClassDto();
        PartnerCode partnerCode = PartnerCode.builder().userId(partnerClassDto.getTeacherUserId()).build();
        List<PartnerCode> partnerCodes = partnerCodeMapper.selectList(partnerCode);
        if (partnerCodes.size() > 0) {
            partnerClassDto.setTeacherCodeId(partnerCodes.get(0).getId().toString());
            List<PartnerClass> list = partnerClassMapper.selectPageList(partnerClassDto);
            return list;
        } else {
            return null;
        }
    }

    public List<PartnerCode> selectTeacherList(String schoolId) {
        return partnerCodeMapper.selectTeacherBySchoolId(schoolId, 1);
    }

    public PartnerClass selectPartnerClassById(String id) {
        PartnerClass partnerClass = partnerClassMapper.selectClassInfoById(id);
        if (null != partnerClass) {
            partnerClass.setStudentCodeList(partnerCodeMapper.selectStudentByClassId(partnerClass.getId().toString(), null));
        }
        return partnerClass;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void savePartnerClass(PartnerClass partnerClass) {
        if (null != partnerClass.getId() && partnerClass.getId() > 0) {
            partnerClass.setUpdateTime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
            //结束日期如果在有效时间内修改用户对应解锁地图
            if (StringUtils.isNotBlank(partnerClass.getEndTime()) && DateUtil.timeIsBefore(partnerClass.getEndTime())) {
                //过期的保存只修改不做解锁操作
                for (PartnerCode partnerCode : partnerClass.getStudentCodeList()) {
                    partnerCodeMapper.updateSelective(partnerCode);
                }
            } else {
                for (PartnerCode partnerCode : partnerClass.getStudentCodeList()) {
                    /** 更新用户开通地图级别 begin */
                    if (StringUtils.isNotBlank(partnerCode.getUserId())) {
                        User user = userMapper.findUserById(partnerCode.getUserId());
                        user.setOrderMap(partnerClass.getLevel());
                        userMapper.updateUser(user);
                        UserMap userMap = UserMap.builder().id(Long.parseLong(partnerCode.getUserId())).unblock(partnerClass.getLevel()).build();
                        userMapMapper.updateUserMapUnblock(userMap);
                    }
                    /** 更新用户开通地图级别 end */
                    partnerCodeMapper.updateSelective(partnerCode);
                }
            }
            if (partnerClass.getStudentCodeList() != null) {
                partnerClass.setNumCount(partnerClass.getStudentCodeList().size());
            }
            partnerClassMapper.updateSelective(partnerClass);
        } else {
            partnerClass.setCreateTime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
            partnerClass.setNumCount(partnerClass.getStudentCodeList().size());
            partnerClassMapper.insertSelective(partnerClass);
            int begin = 0;
            List<BaseCode> baseCodes = baseCodeMapper.selectUseCodeList(partnerClass.getNumCount());
            for (PartnerCode partnerCode : partnerClass.getStudentCodeList()) {
                BaseCode baseCode = baseCodes.get(begin);
                baseCode.setStatus(1);//设置code码已使用状态
                baseCodeMapper.updateSelective(baseCode);
                partnerCode.setIdentifyCode(baseCode.getCode());
                partnerCode.setSchoolId(partnerClass.getSchoolId());
                partnerCode.setClassId(partnerClass.getId());
                partnerCode.setType(0);
                partnerCodeMapper.insertSelective(partnerCode);
                begin++;
            }
        }
    }

    public PageInfo<PartnerClass> selectPartnerClassPage(PartnerClassDto partnerClassDto) {
        PageHelper.startPage(partnerClassDto.getPageNo(), partnerClassDto.getPageSize());
        List<PartnerClass> list = partnerClassMapper.selectPageList(partnerClassDto);
        PageInfo<PartnerClass> pageInfo = new PageInfo(list, 5);
        return pageInfo;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deletePartnerClassById(String classId) {
        PartnerClass partnerClass = partnerClassMapper.selectById(classId);
        if (partnerClass != null) {
            partnerClassMapper.deleteById(classId);
            List<PartnerCode> partnerCodes = partnerCodeMapper.selectDeleteList(classId, null);
            for (PartnerCode partnerCode : partnerCodes) {
                /** 更新用户开通地图级别 begin */
                if (StringUtils.isNotBlank(partnerCode.getUserId())) {
                    User user = userMapper.findUserById(partnerCode.getUserId());
                    user.setOrderMap(null);
                    userMapper.updateUser(user);
                    UserMap userMap = UserMap.builder().id(Long.parseLong(partnerCode.getUserId())).unblock(null).build();
                    userMapMapper.updateUserMapUnblock(userMap);
                }
                /** 更新用户开通地图级别 end */
                partnerCodeMapper.deleteById(partnerCode.getId().toString());//彻底删除code
            }
        }

    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deletePartnerCodeById(String id) {
        PartnerCode partnerCode = partnerCodeMapper.selectById(id);
        PartnerClass partnerClass = partnerClassMapper.selectById(partnerCode.getClassId().toString());
        partnerClass.setNumCount(partnerClass.getNumCount() - 1);
        /** 更新用户开通地图级别 begin */
        if (StringUtils.isNotBlank(partnerCode.getUserId())) {
            User user = userMapper.findUserById(partnerCode.getUserId());
            user.setOrderMap(null);//设置开通为空
            userMapper.updateUser(user);
            UserMap userMap = UserMap.builder().id(Long.parseLong(partnerCode.getUserId())).unblock(null).build();
            userMapMapper.updateUserMapUnblock(userMap);
        }
        /** 更新用户开通地图级别 end */
        partnerClassMapper.updateSelective(partnerClass);
        partnerCodeMapper.deleteById(id);//彻底删除code

        //关连表学生平均分删除
        firstExamMapper.deleteById(partnerCode.getUserId(), "" + partnerCode.getClassId());
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addPartnerCode(Integer classId) {
        BaseCode baseCode = baseCodeMapper.selectUseCodeList(1).get(0);
        baseCode.setStatus(1);//设置code码已使用状态
        baseCodeMapper.updateSelective(baseCode);

        PartnerClass partnerClass = partnerClassMapper.selectById(classId.toString());
        partnerClass.setNumCount(partnerClass.getNumCount() + 1);
        partnerClassMapper.updateSelective(partnerClass);

        PartnerCode partnerCode = new PartnerCode();
        partnerCode.setClassId(classId);
        partnerCode.setSchoolId(partnerClass.getSchoolId());
        partnerCode.setType(0);//学生
        partnerCode.setIdentifyCode(baseCode.getCode());
        partnerCodeMapper.insertSelective(partnerCode);
    }

    private static String LevelOfMerger(String str1, String str2) {
        List<String> tempList = new ArrayList<String>();
        tempList.addAll(Arrays.asList(str2.split(",")));
        tempList.addAll(Arrays.asList(str1.split(",")));
        List<String> list = new ArrayList<>(new HashSet<>(tempList));
        String orderStr = "";
        for (int i = 0; i < list.size(); i++) {
            if (i == 0) {
                orderStr = list.get(i);
            } else {
                orderStr += "," + list.get(i);
            }
        }
        return orderStr;
    }

    /**
     * 获取满足条件的学生
     *
     * @param studyExamInfoDto
     * @return
     */
    public PageInfo<StudyExamInfo> queryStudyExamInfo(StudyExamInfoDto studyExamInfoDto) {
        if (Strings.isNotEmpty(studyExamInfoDto.getLevel())) {
            studyExamInfoDto.setLevel("S" + studyExamInfoDto.getLevel());
        }
        List<String> userIdList = null;
        if (Strings.isNotBlank(studyExamInfoDto.getUid())) {
            /**
             *  教师
             */
            userIdList = partnerCodeMapper.queryUserIdById(studyExamInfoDto);
        } else {
            userIdList = partnerCodeMapper.queryUserIdBySchoolId(studyExamInfoDto);
        }
        PageInfo<StudyExamInfo> pageInfo = null;
        if (userIdList != null && userIdList.size() != 0) {
            PageHelper.startPage(studyExamInfoDto.getPageNo(), studyExamInfoDto.getPageSize());
            studyExamInfoDto.setIds(userIdList);
            List<StudyExamInfo> studyExamInfos = firstExamMapper.findExamInfoListPage(studyExamInfoDto);
            //      List<StudyExamInfo> studyExamInfos = firstExamMapper.findExamInfoList(studyExamInfoDto);
            studyExamInfos.forEach(s -> {
                s.setClassName(partnerClassMapper.queryClassNameById(s.getClassId()));
            });
            pageInfo = new PageInfo(studyExamInfos, 10);
        } else {
            pageInfo = new PageInfo(Lists.newArrayList(), 10);
        }
        return pageInfo;
    }

    public List<PartnerClass> queryClassById(String userId, String schoolId) {
        PartnerClass partnerClass = PartnerClass.builder().build();
        if (Strings.isNotEmpty(userId)) {
            partnerClass.setTeacherCodeId(Integer.parseInt(userId));
        } else {
            partnerClass.setSchoolId(Integer.parseInt(schoolId));
        }
        return partnerClassMapper.queryClassListById(partnerClass);
    }

    /**
     * 单个学生列表
     *
     * @return
     */
    public List<StudyExamInfo> queryStudyExamInfoById(StudyExamInfoDto studyExamInfoDto) {
        return queryStudyList(studyExamInfoDto);
    }

    private List<StudyExamInfo> queryStudyList(StudyExamInfoDto studyExamInfoDto) {
        if (Strings.isNotEmpty(studyExamInfoDto.getLevel())) {
            studyExamInfoDto.setLevel("S" + studyExamInfoDto.getLevel());
        }
        List<String> userIdList = Lists.newArrayList(studyExamInfoDto.getUserId());
        studyExamInfoDto.setIds(userIdList);
        List<StudyExamInfo> studyExamInfos = firstExamMapper.findExamInfoList(studyExamInfoDto);
        return studyExamInfos;
    }

    /**
     * 单个学生图表
     *
     * @return
     */
    public List<ScoreChartVo> queryStudyExamInfoChart(StudyExamInfoDto studyExamInfoDto, String prat) {
        if (Strings.isEmpty(studyExamInfoDto.getCheckpointsStart())) {
            studyExamInfoDto.setCheckpointsStart("1");
        }
        if (Strings.isEmpty(studyExamInfoDto.getCheckpointsEnd())) {
            studyExamInfoDto.setCheckpointsEnd("40");
        }

        List<StudyExamInfo> examInfoList = queryStudyList(studyExamInfoDto);
        List<StudyExamInfo> studyExamInfoList = Lists.newArrayList();
        //取后10条
        for (int i = examInfoList.size() - 1; i >= 0; i--) {
            if (studyExamInfoList.size() == 10) {
                break;
            }
            studyExamInfoList.add(examInfoList.get(i));
        }
        List<ScoreChartVo> scoreChartVos = Lists.newArrayList();
        Map<String, Integer> columnarMap = new Hashtable<String, Integer>();
        studyExamInfoList.forEach(s -> {
            ScoreChartVo scoreChartVo = null;
            switch (prat) {
                case "1":
                    scoreChartVo = ScoreChartVo.builder().score(s.getComprehension()).ranking("" + s.getRanking())
                            .checkpoints(s.getLevel() + "-" + s.getNodeNum())
                            .build();
                    break;
                case "2":
                    scoreChartVo = ScoreChartVo.builder().score(s.getVocabulary()).ranking("" + s.getRanking())
                            .checkpoints(s.getLevel() + "-" + s.getNodeNum())
                            .build();
                    break;
                case "3":
                    scoreChartVo = ScoreChartVo.builder().score(s.getGrammar()).ranking("" + s.getRanking())
                            .checkpoints(s.getLevel() + "-" + s.getNodeNum())
                            .build();
                    break;
                default:
                    scoreChartVo = ScoreChartVo.builder().score(returnStudyExamInfoScore(studyExamInfoDto, s)).ranking("" + s.getRanking())
                            .checkpoints(s.getLevel() + "-" + s.getNodeNum())
                            .build();
            }
            if (columnarMap.get(s.getLevel() + "-" + s.getNodeNum()) == null) {
                columnarMap.put(s.getLevel() + "-" + s.getNodeNum(), 1);
                scoreChartVos.add(scoreChartVo);
            }
        });
        return scoreChartVos;
    }

    private String returnStudyExamInfoScore(StudyExamInfoDto studyExamInfoDto, StudyExamInfo studyExamInfo) {
        if ("1".equals(studyExamInfoDto.getScorceType())) {
            return studyExamInfo.getFirstScore();
        } else {
            return studyExamInfo.getHighestScore();
        }
    }

    /**
     * 获取满足条件的学生 图表信息
     *
     * @param studyExamInfoDto
     * @return
     */
    public StudyAverageVo queryStudyAverageExamInfo(StudyExamInfoDto studyExamInfoDto) {
        if (Strings.isNotEmpty(studyExamInfoDto.getLevel())) {
            studyExamInfoDto.setLevel("S" + studyExamInfoDto.getLevel());
        }
        List<String> userIdList = null;
        if (Strings.isNotBlank(studyExamInfoDto.getUid())) {
            userIdList = partnerCodeMapper.queryUserIdById(studyExamInfoDto);
        } else {
            userIdList = partnerCodeMapper.queryUserIdBySchoolId(studyExamInfoDto);
        }
        StudyAverageVo studyAverageVo = StudyAverageVo.builder().build();
        if (userIdList != null && userIdList.size() != 0) {
            studyExamInfoDto.setIds(userIdList);
            // List<StudyExamInfo> studyExamInfos = firstExamMapper.findExamInfoListPage(studyExamInfoDto);
            //所有的
            List<StudyExamInfo> studyExamInfoList = firstExamMapper.findExamInfoList(studyExamInfoDto);
            int cnum = 0;
            if (studyExamInfoDto.getCheckpointsEnd().equals(studyExamInfoDto.getCheckpointsStart())) {
                cnum = 1;
            } else {
                cnum = 1 + Integer.parseInt(studyExamInfoDto.getCheckpointsEnd()) - Integer.parseInt(studyExamInfoDto.getCheckpointsStart());
            }
//            int cnum = Integer.parseInt(studyExamInfoDto.getCheckpointsEnd()) - Integer.parseInt(studyExamInfoDto.getCheckpointsStart());
            average(0, studyAverageVo, userIdList.size(), cnum, studyExamInfoList, studyExamInfoDto.getScorceType());
            nodeMap(studyAverageVo, userIdList.size(), studyExamInfoList);
        }

        return studyAverageVo;
    }

    /**
     * 按条件查询老师学校学生闯关最大值
     *
     * @param level    年级
     * @param classId  班级ID
     * @param userId   老师ID
     * @param schoolId 学校ID
     * @return
     */
    public Integer getTeacherCheckpointMax(String grade, String level, String classId, String userId, String schoolId) {
        if (Strings.isNotEmpty(level)) {
            level = "S" + level;
        }
        List<Long> classIds;
        if (StringUtils.isBlank(userId) && StringUtils.isBlank(schoolId)) {
            return 1;
        } else {
            if (StringUtils.isNotBlank(userId)) {
                PartnerCode partnerCode = PartnerCode.builder().userId(userId).build();
                List<PartnerCode> partnerCodes = partnerCodeMapper.selectList(partnerCode);
                if (partnerCodes.size() > 0) {
                    classIds = partnerClassMapper.selectTeacherClassIdsList(null, partnerCodes.get(0).getId().toString());
                } else {
                    classIds = null;
                }
            } else {
                classIds = partnerClassMapper.selectTeacherClassIdsList(schoolId, null);
            }
        }
        if (classIds == null) {
            return 1;
        }
        Integer checkpointMax = firstExamMapper.getTeacherCheckpointMax(classIds, classId, grade, level);
        if (checkpointMax == null) {
            return 1;
        } else {
            return checkpointMax;
        }
    }

    /**
     *获取学生的最大关卡
     */

    public  Integer getStudentCheckpointMax(String level,Integer userId){
        Integer checkpointMax = firstExamMapper.getStudentCheckpointMax(userId,level);
        if (checkpointMax == null){
            return  1;
        }else{
            return checkpointMax;
        }
    }
    /**
     * 学生个人获取的 图表信息
     */
    public StudyAverageVo querySignStudyAverageChart(StudyExamInfoDto studyExamInfoDto) {
        if (Strings.isNotEmpty(studyExamInfoDto.getLevel())) {
            studyExamInfoDto.setLevel("S" + studyExamInfoDto.getLevel());
        }
        List<String> userIdList = Lists.newArrayList(studyExamInfoDto.getUserId());
        StudyAverageVo studyAverageVo = StudyAverageVo.builder().build();
        studyExamInfoDto.setIds(userIdList);
        List<StudyExamInfo> studyExamInfos = firstExamMapper.findExamInfoList(studyExamInfoDto);
        int cnum = 0;
        if (studyExamInfoDto.getCheckpointsEnd().equals(studyExamInfoDto.getCheckpointsStart())) {
            cnum = 1;
        } else {
            cnum = Integer.parseInt(studyExamInfoDto.getCheckpointsEnd()) - Integer.parseInt(studyExamInfoDto.getCheckpointsStart());
        }
        //size 就不是学生人数了，1 取试卷总数  而是试卷总数
        average(1, studyAverageVo, userIdList.size(), cnum, studyExamInfos, studyExamInfoDto.getScorceType());
        studyAverageVo.setColumnarMap(null);
        return studyAverageVo;
    }

    public void nodeMap(StudyAverageVo studyAverageVo, int size, List<StudyExamInfo> studyExamInfos) {
        Map<String, String> columnarMap = new Hashtable<String, String>();
        //实际关卡数
        AtomicInteger checkPoints = new AtomicInteger();
        AtomicInteger targetScore = new AtomicInteger();
        AtomicInteger noTargetScore = new AtomicInteger();
        AtomicInteger sumScore = new AtomicInteger();
        for (StudyExamInfo s : studyExamInfos) {
            for (StudyExamInfo studyExamInfo : studyExamInfos) {
                if ((studyExamInfo.getLevel() + studyExamInfo.getNodeNum()).equals(s.getLevel() + s.getNodeNum())) {
                    sumScore.getAndAdd(Integer.parseInt(studyExamInfo.getFirstScore()));
                    if (Long.parseLong(studyExamInfo.getFirstScore()) >= 70) {
                        //及格人数
                        targetScore.getAndIncrement();
                    } else {
                        //未及格人数
                        noTargetScore.getAndIncrement();
                    }
                }
            }
            if (columnarMap.get(s.getLevel() + "-" + s.getNodeNum()) == null) {
                checkPoints.getAndIncrement();
                //平均分
                int average = sumScore.get() / (targetScore.get() + noTargetScore.get());
                columnarMap.put(s.getLevel() + "-" + s.getNodeNum(),
                        (size - targetScore.get() - noTargetScore.get()) + "," + noTargetScore.get() + "," + targetScore.get() + "," + average);
            }
            targetScore.getAndSet(0);
            noTargetScore.getAndSet(0);
            sumScore.getAndSet(0);
        }
        studyAverageVo.setColumnarMap(columnarMap);
    }

    /**
     * 计算平均分
     */
    public void average(int flag, StudyAverageVo studyAverageVo, int size, int cnum, List<StudyExamInfo> studyExamInfos, String scorceType) {
        AtomicLong score = new AtomicLong();
        AtomicLong comprehension = new AtomicLong();
        AtomicLong vocabulary = new AtomicLong();
        AtomicLong grammar = new AtomicLong();
        AtomicInteger t1 = new AtomicInteger();
        AtomicInteger t2 = new AtomicInteger();
        AtomicInteger t3 = new AtomicInteger();
        AtomicInteger t4 = new AtomicInteger();
        //实际关卡数
        AtomicInteger checkPoints = new AtomicInteger();

        //userId-用户成绩list
        ArrayListMultimap<String, Integer> userScoreEntryMap = ArrayListMultimap.create();
        ArrayListMultimap<String, Integer> userComprehensionEntryMap = ArrayListMultimap.create();
        ArrayListMultimap<String, Integer> userVocabularyEntryMap = ArrayListMultimap.create();
        ArrayListMultimap<String, Integer> userGrammarEntryMap = ArrayListMultimap.create();
        for (StudyExamInfo s : studyExamInfos) {
            userScoreEntryMap.put(s.getUserId(), Integer.parseInt(s.getFirstScore()));
            userComprehensionEntryMap.put(s.getUserId(), Integer.parseInt(s.getComprehension()));
            userVocabularyEntryMap.put(s.getUserId(), Integer.parseInt(s.getVocabulary()));
            userGrammarEntryMap.put(s.getUserId(), Integer.parseInt(s.getGrammar()));
        }

        Set<String> userScoreSet = userScoreEntryMap.keySet();
        userScoreSet.forEach(us -> {
            List<Integer> userScoreList = userScoreEntryMap.get(us);
            Integer sumUserScore = 0;
            for (Integer s : userScoreList) {
                sumUserScore += s;
            }
            Integer averageVo = sumUserScore / userScoreList.size();
            score.getAndAdd(averageVo.longValue());
            if (flag == 1) {
                //个人
                for (Integer s : userScoreList) {
                    if (s <= 49) {
                        t1.getAndIncrement();
                    } else if (s <= 69) {
                        t2.getAndIncrement();
                    } else if (s <= 89) {
                        t3.getAndIncrement();
                    } else {
                        t4.getAndIncrement();
                    }
                }
            } else {
                if (averageVo <= 49) {
                    t1.getAndIncrement();
                } else if (averageVo <= 69) {
                    t2.getAndIncrement();
                } else if (averageVo <= 89) {
                    t3.getAndIncrement();
                } else {
                    t4.getAndIncrement();
                }
            }
        });
        //平均分
        for (Map.Entry<String, Collection<Integer>> entry : userComprehensionEntryMap.asMap().entrySet()) {
            Collection<Integer> collection = entry.getValue();
            Integer sumComprehension = 0;
            for (Integer scorecom : collection) {
                sumComprehension += scorecom;
            }
            comprehension.getAndAdd(sumComprehension / collection.size());
        }
        for (Map.Entry<String, Collection<Integer>> entry : userVocabularyEntryMap.asMap().entrySet()) {
            Collection<Integer> collection = entry.getValue();
            Integer sumVocabulary = 0;
            for (Integer scorecom : collection) {
                sumVocabulary += scorecom;
            }
            vocabulary.getAndAdd(sumVocabulary / collection.size());
        }
        for (Map.Entry<String, Collection<Integer>> entry : userGrammarEntryMap.asMap().entrySet()) {
            Collection<Integer> collection = entry.getValue();
            Integer sumGrammar = 0;
            for (Integer scorecom : collection) {
                sumGrammar += scorecom;
            }
            grammar.getAndAdd(sumGrammar / collection.size());
            //用实际关卡数统计
            //if (collection.size() < cnum || collection.size() > cnum) {
                cnum = collection.size();
            //}
        }
//
        if (flag == 1) {
            size = 1;
        } else {
            size = userScoreSet.size();
        }
        if(size == 0){
            size = 1;
        }
        //用实际人数算 已经是平均分了不用除以人数了
        studyAverageVo.setComprehension(new BigDecimal(comprehension.get() * 1.00 / size).setScale(0, BigDecimal.ROUND_HALF_UP).toString());
        studyAverageVo.setGrammar(new BigDecimal(grammar.get() * 1.00 / size).setScale(0, BigDecimal.ROUND_HALF_UP).toString());
        studyAverageVo.setAverage(new BigDecimal(score.get() * 1.00 / size).setScale(0, BigDecimal.ROUND_HALF_UP).toString());
        studyAverageVo.setVocabulary(new BigDecimal(vocabulary.get() * 1.00 / size).setScale(0, BigDecimal.ROUND_HALF_UP).toString());
        //用关卡数个人
        if (flag == 1) {
            size = cnum;
        }
        BigDecimal past1 = new BigDecimal((t1.get() * 1.00 / size) * 100);
        BigDecimal past2 = new BigDecimal((t2.get() * 1.00 / size) * 100);
        BigDecimal past3 = new BigDecimal((t3.get() * 1.00 / size) * 100);
        BigDecimal past4 = new BigDecimal((t4.get() * 1.00 / size) * 100);
        //四舍五入保留2位
        studyAverageVo.setPastry1(past1.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        studyAverageVo.setPastry2(past2.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        studyAverageVo.setPastry3(past3.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        studyAverageVo.setPastry4(past4.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
    }


    public TeacherClassLinkageVo queryClassByUserId(String userId, String school, String level, String grade) {
        List<TeacherClassVo> list = partnerClassMapper.queryClassByUserId(school, userId, level, grade);
        Set<String> levelSet = Sets.newTreeSet();
        for (TeacherClassVo t : list) {

            if (Strings.isNotEmpty(t.getLevel())) {
                String[] levels = t.getLevel().split(",");
                for (String l : levels) {
                    levelSet.add(l);
                }
            }
        }
        TeacherClassLinkageVo teacherClassLinkageVo = TeacherClassLinkageVo.builder().leval(levelSet).build();
        if (Strings.isNotEmpty(level) && Strings.isNotEmpty(grade)) {
            teacherClassLinkageVo.setTeacherClassVos(list);
        }
        return teacherClassLinkageVo;
    }

    /**
     * 统计学生详情
     */
    public Map<String, String> queryStudentInfoById(String studentId) {
        List<StudyExamInfo> studyExamInfoList = firstExamMapper.queryStudentInfoById(studentId);
        Map<String, String> studentInfo = Maps.newHashMap();
        Double sumScore = 0d;
        int level = 1;
        int node = 1;
        //百分百
        int passrate = 0;
        for (StudyExamInfo student : studyExamInfoList) {
            sumScore += Double.parseDouble(student.getFirstScore());
            if (Double.parseDouble(student.getFirstScore()) >= 70) {
                passrate += 1;
            }
            if (level < Integer.parseInt(student.getLevel().replaceAll("S", ""))) {
                level = Integer.parseInt(student.getLevel().replaceAll("S", ""));
            }
            if (node < student.getNodeNum()) {
                node = student.getNodeNum();
            }
        }
        if (studyExamInfoList.size() != 0) {
            studentInfo.put("averageScore", (sumScore * 1.00 / studyExamInfoList.size()) + "");
        } else {
            studentInfo.put("averageScore", (sumScore * 1.00 / studyExamInfoList.size()) + "");
        }
        if (studyExamInfoList.size() != 0) {
            studentInfo.put("passrate", (passrate * 1.00 / studyExamInfoList.size() * 100.00) + "%");
        } else {
            studentInfo.put("passrate", "0%");
        }
        studentInfo.put("recentCheckpoint", "S" + level + "_" + node);
        PartnerCode partnerCode = partnerCodeMapper.selectUserOpenCode(studentId);
        studentInfo.put("class", partnerClassMapper.queryClassNameById("" + partnerCode.getClassId()));

        return studentInfo;
    }
}
