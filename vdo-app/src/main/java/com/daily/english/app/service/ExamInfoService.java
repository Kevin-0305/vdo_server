package com.daily.english.app.service;

import com.alibaba.fastjson.JSON;
import com.daily.english.app.condition.ExamInfoCondition;
import com.daily.english.app.domain.*;
import com.daily.english.app.mapper.ExamInfoMapper;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
public class ExamInfoService {

    @Autowired
    private PartService partService;

    @Autowired
    private ExamInfoMapper examInfoMapper;

    public void saveExamInfo(ExamInfo examInfo) {
        if (CollectionUtils.isNotEmpty(examInfo.getPartList())) {
            String partListJson = JSON.toJSONString(examInfo.getPartList());
            examInfo.setPartListJson(partListJson);
        }
        examInfo = examInfoCountNum(examInfo);
        if (StringUtils.isNotBlank(examInfo.getId())) {
            examInfoMapper.updateExamInfo(examInfo);
        } else {
            examInfo.setId(UUID.randomUUID().toString());
            examInfoMapper.insertExamInfo(examInfo);
        }

    }
    private String comprehension = "Comprehension";
    private String vocabulary = "Vocabulary";
    private String grammar = "Grammar";
    private ExamInfo examInfoCountNum(ExamInfo examInfo) {
        List<Part> partList = examInfo.getPartList();
        ArrayListMultimap<String, List<Node>> nodeEntryMap = ArrayListMultimap.create();
        // 判断各考卷子类型
        for (Part part : partList) {
            // 判断当前是什么类型
            String partName = part.getPartName();
            List<Node> nodeList = part.getNodeList();
            String key = "";
            if (partName.contains(comprehension)) {
                key = comprehension;

            } else if (partName.contains(vocabulary)) {
                key = vocabulary;
            } else if (partName.contains(grammar)) {
                key = grammar;
            } else {
                key = "other";
            }
            nodeEntryMap.put(key,nodeList);
        }
        AtomicInteger grammarNum = new AtomicInteger();
        AtomicInteger comprehensionNum = new AtomicInteger();
        AtomicInteger vocabularyNum = new AtomicInteger();
        // 统计各类型题目的正确结果数和总题目结果数
        {
            List<List<Node>> nodeEntryList = nodeEntryMap.get(comprehension);
            for(List<Node> nodes:nodeEntryList){
                for(Node node:nodes){
                    int count = returnNumByNode(node);
                    if(count != 0){
                        comprehensionNum.getAndAdd(count);
                    }
                }
            }
        }
        {
            List<List<Node>> nodeEntryList = nodeEntryMap.get(vocabulary);
            for(List<Node> nodes:nodeEntryList){
                for(Node node:nodes){
                    int count = returnNumByNode(node);
                    if(count != 0){
                        vocabularyNum.getAndAdd(count);
                    }
                }
            }
        }
        {
            List<List<Node>> nodeEntryList = nodeEntryMap.get(grammar);
            for(List<Node> nodes:nodeEntryList){
                for(Node node:nodes){
                    int count = returnNumByNode(node);
                    if(count != 0){
                        grammarNum.getAndAdd(count);
                    }
                }
            }
        }
        examInfo.setGrammarNum(grammarNum.get());
        examInfo.setComprehensionNum(comprehensionNum.get());
        examInfo.setVocabularyNum(vocabularyNum.get());
        examInfo.setQuestionNum(grammarNum.get()+ comprehensionNum.get() + vocabularyNum.get());
        return examInfo;
    }
    private Integer returnNumByNode(Node node){
        Integer count = 0;
        if(node == null || Strings.isNotEmpty(node.getText())){
            return count;
        }
        switch (node.getType()){
            case "MulBlank":
                count = node.getAnswer().split(",").length;
                break;
            //匹配题
            case "Match":
                count = node.getAnswer().split(",").length;
                break;
            //imgFillContentList size
            case "ImgFill":
                count = node.getImgFillContentList().size();
                break;
            case "Blank":
                if("multi".equals(node.getChooseType())) {
                    count = node.getAnswer().split(",").length;
                }else{
                    count = 1;
                }
                break;
            case "Fill":
                count = node.getAnswer().split(",").length;
                break;
            //选择
           /* case "Choose":
                if("multi".equals(node.getChooseType())){
                    count = node.getAnswer().split(",").length;
                }else{
                    count = 1;
                }
                break;*/
            default:
                count = 1;
        }
        return count;
    }
    public void deleteExamInfoById(String examId) {
//        partService.deletePartByExamId(examId);
        examInfoMapper.deleteExamInfoById(examId);
    }

    public List<ExamInfo> getExamInfoListByIds(List<String> ids) {
        if (CollectionUtils.isNotEmpty(ids)) {
            List<ExamInfo> examInfoList = examInfoMapper.findExamInfoListByExamIds(ids);
            if (CollectionUtils.isNotEmpty(examInfoList)) {
                for (ExamInfo examInfo : examInfoList) {
                    if (StringUtils.isNotBlank(examInfo.getPartListJson())) {
                        List<Part> partList = JSON.parseArray(examInfo.getPartListJson(), Part.class);
                        examInfo.setPartList(partList);
                    }
                }
            }
            return examInfoList;
        } else {
            return Lists.newArrayList();
        }
    }

    public ExamInfo getExamInfo(String examId) {
        ExamInfo examInfo = examInfoMapper.findExamInfoByExamId(examId);
        if (examInfo != null) {
            if (StringUtils.isNotBlank(examInfo.getPartListJson())) {
                List<Part> partList = JSON.parseArray(examInfo.getPartListJson(), Part.class);
                examInfo.setPartList(partList);
            }
//            List<Part> partList = partService.getPartListByExamId(examInfo.getId());
//            if (CollectionUtils.isNotEmpty(partList)) {
//                examInfo.setPartList(partList);
//            }
        }
        return examInfo;
    }

    public List<ExamInfo> getExamInfoList() {
        List<ExamInfo> examInfoList = examInfoMapper.findExamInfoList();
        if (CollectionUtils.isNotEmpty(examInfoList)) {
            for (ExamInfo examInfo : examInfoList) {
                if (StringUtils.isNotBlank(examInfo.getPartListJson())) {
                    List<Part> partList = JSON.parseArray(examInfo.getPartListJson(), Part.class);
                    examInfo.setPartList(partList);
                }
            }
        }
        return examInfoList;
    }

    public Integer getExamInfoCount(ExamInfoCondition examInfoCondition) {
        return examInfoMapper.findExamInfoCount(examInfoCondition);
    }

    public List<ExamInfo> getExamInfoByPage(ExamInfoCondition examInfoCondition) {
        List<ExamInfo> examInfoList = examInfoMapper.findExamInfoByPage(examInfoCondition);
        if (CollectionUtils.isNotEmpty(examInfoList)) {
            for (ExamInfo examInfo : examInfoList) {
                if (StringUtils.isNotBlank(examInfo.getPartListJson())) {
                    List<Part> partList = JSON.parseArray(examInfo.getPartListJson(), Part.class);
                    examInfo.setPartList(partList);
                }
            }
        }
        return examInfoList;
    }
}
