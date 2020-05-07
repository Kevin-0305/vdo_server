package com.daily.english.app.service;

import com.daily.english.app.domain.Node;
import com.daily.english.app.domain.Part;
import com.daily.english.app.mapper.PartMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class PartService {

    @Autowired
    private NodeService nodeService;

    @Autowired
    private PartMapper partMapper;

    public void savePart(Part part) {
        if (part.getPartId() != null && part.getPartId() > 0) {
            partMapper.updatePart(part);
        } else {
            partMapper.insertPart(part);
        }
        List<Node> nodeList = part.getNodeList();
        if (CollectionUtils.isNotEmpty(nodeList)) {
            int orders = 1;
            for (Node node : nodeList) {
                Date date = new Date();
                node.setPartId(part.getPartId());
                node.setCreateTime(date);
                node.setUpdateTime(date);
                node.setOrders(orders++);
                node.setStatus(0);
                nodeService.saveNode(node);
            }
        }
    }

    public void deletePartByExamId(String examId) {
        List<Part> partList = partMapper.findPartListByExamId(examId);
        if (CollectionUtils.isNotEmpty(partList)) {
            for (Part part : partList) {
                nodeService.deleteNodeByPartId(part.getPartId());
            }
        }
        partMapper.deletePartByExamId(examId);
    }

//    public List<Part> getPartListByExamIds(List<String> ids) {
//        if (CollectionUtils.isNotEmpty(ids)) {
//            return partMapper.findPartListByExamIds(ids);
//        } else {
//            return Lists.newArrayList();
//        }
//    }

    public List<Part> getPartListByExamId(String examId) {
        List<Part> partList = partMapper.findPartListByExamId(examId);
        if (CollectionUtils.isNotEmpty(partList)) {
            List<Long> partIds = Lists.newArrayList();
            Map<Long, Part> partMap = Maps.newHashMap();
            partList.forEach(part -> {
                partIds.add(part.getPartId());
                partMap.put(part.getPartId(), part);
            });

            List<Node> nodeList = nodeService.getNodeListByIds(partIds);
            for (Node node : nodeList) {
                Part part = partMap.get(node.getPartId());
                List<Node> partNodeList = part.getNodeList();
                if (partNodeList == null) {
                    partNodeList = Lists.newArrayList();
                }
                partNodeList.add(node);
                part.setNodeList(partNodeList);
            }
        }
        return partList;
    }
}
