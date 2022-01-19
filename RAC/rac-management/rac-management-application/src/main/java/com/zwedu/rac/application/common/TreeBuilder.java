package com.zwedu.rac.application.common;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zwedu.rac.sdk.common.TreeNode;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 树构造器
 *
 * @author qingchuan
 * @date 2020/12/20
 */
public class TreeBuilder {
    /**
     * 构建树
     *
     * @param nodeList 节点列表
     * @param <T>      泛型
     * @return 树形列表
     */
    public static <T extends TreeNode> List<T> buildTree(List<T> nodeList) {
        if (CollectionUtils.isEmpty(nodeList)) {
            return Lists.newArrayList();
        }
        // 获取排序的列表，保证层级顺序
        List<T> sortedList = nodeList.stream().sorted(Comparator.comparing(T::getLevel))
                .collect(Collectors.toList());
        Map<Long, T> idNode = Maps.newHashMap();
        List<T> retList = Lists.newLinkedList();
        for (T node : sortedList) {
            // 初始化子节点列表
            node.setChildList(Lists.newArrayList());
            // 把当前节点放入map中
            idNode.put(node.getId(), node);
            // 如果map中包含父节点，则把当前节点加入到父节点的子节点列表中
            if (idNode.containsKey(node.getParentId())) {
                idNode.get(node.getParentId()).getChildList().add(node);
            } else {
                // 否则则是根节点，直接加入到返回列表中
                retList.add(node);
            }
        }
        return retList;
    }
}
