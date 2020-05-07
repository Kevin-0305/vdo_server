package com.daily.english.app.service;

import com.alibaba.fastjson.JSON;
import com.daily.english.app.domain.FillContent;
import com.daily.english.app.domain.Node;
import com.daily.english.app.mapper.NodeMapper;
import com.github.pagehelper.util.StringUtil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class NodeService {

    @Autowired
    private NodeMapper nodeMapper;

    public void saveNode(Node node) {
        if (StringUtils.isNotBlank(node.getText())) {
            if (node.getNodeId() != null && node.getNodeId() > 0) {
                nodeMapper.updateNode(node);
            } else {
                nodeMapper.insertNode(node);
            }
        } else {
            List<String> leftList = node.getLeft();
            if (CollectionUtils.isNotEmpty(leftList)) {
                node.setLeftJson(JSON.toJSONString(leftList));
            }
            List<String> rightList = node.getRight();
            if (CollectionUtils.isNotEmpty(rightList)) {
                node.setRightJson(JSON.toJSONString(rightList));
            }
            List<String> optionList = node.getOptionList();
            if (CollectionUtils.isNotEmpty(optionList)) {
                node.setOptionListJson(JSON.toJSONString(optionList));
            }
            List<FillContent> imgFillContentList = node.getImgFillContentList();
            if (CollectionUtils.isNotEmpty(imgFillContentList)) {
                node.setImgFillContentListJson(JSON.toJSONString(imgFillContentList));
            }
            if (node.getNodeId() != null && node.getNodeId() > 0) {
                nodeMapper.updateNode(node);
            } else {
                nodeMapper.insertNode(node);
            }
        }
    }

    public void deleteNodeByPartId(Long id) {
        nodeMapper.deleteNodeByPartId(id);
    }

    public List<Node> getNodeListByIds(List<Long> ids) {
        if (CollectionUtils.isNotEmpty(ids)) {
            List<Node> nodeList = nodeMapper.findNodeListByPartIds(ids);
            for (Node node : nodeList) {
                if (StringUtils.isEmpty(node.getText())) {
                    String leftJson = node.getLeftJson();
                    if (StringUtil.isNotEmpty(leftJson)) {
                        node.setLeft(JSON.parseArray(leftJson, String.class));
                    }
                    String rightJson = node.getRightJson();
                    if (StringUtil.isNotEmpty(rightJson)) {
                        node.setRight(JSON.parseArray(rightJson, String.class));
                    }
                    String optionListJson = node.getOptionListJson();
                    if (StringUtil.isNotEmpty(optionListJson)) {
                        node.setOptionList(JSON.parseArray(optionListJson, String.class));
                    }
                    String imgFillContentJson = node.getImgFillContentListJson();
                    if (StringUtil.isNotEmpty(imgFillContentJson)) {
                        node.setImgFillContentList(JSON.parseArray(imgFillContentJson, FillContent.class));
                    }
                }
            }
            return nodeList;
        } else {
            return Lists.newArrayList();
        }
    }

//    public Node getNode(String PartId) {
//        Node Node = nodeMapper.findNodeByPartId(PartId);
//        return nodeMapper.findNodeByPartId(PartId);
//    }
}
