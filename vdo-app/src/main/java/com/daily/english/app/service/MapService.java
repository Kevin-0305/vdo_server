package com.daily.english.app.service;

import com.alibaba.fastjson.JSON;
import com.daily.english.app.domain.Map;
import com.daily.english.app.mapper.ExamInfoMapper;
import com.daily.english.app.mapper.MapMapper;
import com.daily.english.app.mapper.UserExamInfoMapper;
import com.daily.english.app.vo.MapVo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class MapService {

    @Autowired
    private MapMapper mapMapper;

    @Autowired
    private MapperFacade mapper;

    public void saveMap(Map map) {
        if (CollectionUtils.isNotEmpty(map.getInLevel())) {
            map.setInLevelJson(JSON.toJSONString(map.getInLevel()));
        }
        if (CollectionUtils.isNotEmpty(map.getNextLevel())) {
            map.setNextLevelJson(JSON.toJSONString(map.getNextLevel()));
        }
        mapMapper.insert(map);
    }

    public void updateMap(Map map) {
        if (CollectionUtils.isNotEmpty(map.getInLevel())) {
            map.setInLevelJson(JSON.toJSONString(map.getInLevel()));
        }
        if (CollectionUtils.isNotEmpty(map.getNextLevel())) {
            map.setNextLevelJson(JSON.toJSONString(map.getNextLevel()));
        }
        mapMapper.updateSelective(map);
    }

    public MapVo getMapByMapID(Long mapID) {
        Map map = mapMapper.findMapById(mapID);
        MapVo mapVo = mapper.map(map, MapVo.class);
        if( mapVo != null && StringUtils.isNotBlank(mapVo.getInLevelJson())) {
            mapVo.setInLevel(JSON.parseArray(mapVo.getInLevelJson(), Integer.class));
        }
        if( mapVo != null && StringUtils.isNotBlank(mapVo.getNextLevelJson())) {
            mapVo.setNextLevel(JSON.parseArray(mapVo.getNextLevelJson(), Integer.class));
        }
        return mapVo;
    }

    public List<MapVo> getMapList(Map map) {
        List<Map> mapList = mapMapper.findMapList(map);

        List<MapVo> mapVoList = mapper.mapAsList(mapList, MapVo.class);
        if (CollectionUtils.isNotEmpty(mapVoList)) {
            List<String> examIds = Lists.newArrayList();
            java.util.Map<String, MapVo> mapVoMap = Maps.newHashMap();
            mapVoList.forEach(mapVo -> {
                if(StringUtils.isNotBlank(mapVo.getInLevelJson())) {
                    mapVo.setInLevel(JSON.parseArray(mapVo.getInLevelJson(), Integer.class));
                }
                if(StringUtils.isNotBlank(mapVo.getNextLevelJson())) {
                    mapVo.setNextLevel(JSON.parseArray(mapVo.getNextLevelJson(), Integer.class));
                }
                examIds.add(mapVo.getExamId());
                mapVoMap.put(mapVo.getExamId(), mapVo);
            });
            // FIXME: 不需要返回试卷
//            if (CollectionUtils.isNotEmpty(examIds)) {
//                List<ExamInfo> examInfoList = examInfoMapper.findExamInfoListByExamIds(examIds);
//                if (CollectionUtils.isNotEmpty(examInfoList)) {
//                    examInfoList.forEach(examInfo -> {
//                        MapVo mapVo = mapVoMap.get(examInfo.getId());
//                        mapVo.setExamInfo(examInfo);
//                    });
//                }
//            }
        }
        return mapVoList;
    }

//    public List<MapVo> getMapList(String userId) {
//        List<Map> mapList = mapMapper.findMapList();
//
//        List<MapVo> pathVoList = mapper.mapAsList(mapList, MapVo.class);
//        if (CollectionUtils.isNotEmpty(pathVoList)) {
//            List<String> examIds = Lists.newArrayList();
//            java.util.Map<String, MapVo> mapVoMap = Maps.newHashMap();
//            pathVoList.forEach(mapVo -> {
//                examIds.add(mapVo.getExamId());
//                mapVoMap.put(mapVo.getExamId(), mapVo);
//            });
//            if (CollectionUtils.isNotEmpty(examIds)) {
//                List<ExamInfo> examInfoList = examInfoMapper.findExamInfoListByExamIds(examIds);
//                if (CollectionUtils.isNotEmpty(examInfoList)) {
//                    examInfoList.forEach(examInfo -> {
//                        MapVo mapVo = mapVoMap.get(examInfo.getId());
//                        mapVo.setExamInfo(examInfo);
//                    });
//                }
//
//                List<UserExamInfo> userExamInfoList = userExamInfoMapper.findUserExamInfoListByExamIds(userId, examIds);
//                if (CollectionUtils.isNotEmpty(userExamInfoList)) {
//                    userExamInfoList.forEach(userExamInfo -> {
//                        MapVo pathVo = mapVoMap.get(userExamInfo.getExamInfoId());
//                        pathVo.setUserId(userExamInfo.getUserId());
//                        pathVo.setScore(userExamInfo.getScore());
//                    });
//                }
//            }
//        }
//        return pathVoList;
//    }
    public int findMapExamIdCount(Map map){
        return mapMapper.findMapExamIdCount(map);
    }
}
