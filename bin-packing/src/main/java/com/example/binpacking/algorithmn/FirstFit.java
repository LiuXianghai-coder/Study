package com.example.binpacking.algorithmn;

import java.math.BigDecimal;
import java.util.*;

/**
 *@author lxh
 */
public class FirstFit
        extends AbstractLoadData {

    public List<List<Map<Object, Object>>> firstFit(Map<Object, BigDecimal> limitMap) {
        return firstFit(DEFAULT_SHIP_DATA_NAME, limitMap);
    }

    public List<List<Map<Object, Object>>> firstFit(String shipFileName, Map<Object, BigDecimal> limitMap) {
        // 随机打乱物件排列顺序
        List<Map<Object, Object>> shipDataList = shuffList(loadShipLabelData(shipFileName));
        return firstFit(shipDataList, new ArrayList<>(), limitMap);
    }

    public List<List<Map<Object, Object>>> firstFit(List<Map<Object, Object>> dataList,
                                                    List<List<Map<Object, Object>>> groupDataList,
                                                    Map<Object, BigDecimal> limitMap) {
        List<List<Map<Object, Object>>> ans = new ArrayList<>(groupDataList);
        Map<Object, BigDecimal> accumulate = new HashMap<>();
        List<Map<Object, Object>> groupList = new ArrayList<>();
        label:
        for (Map<Object, Object> shipData : dataList) {
            for (List<Map<Object, Object>> groupData : groupDataList) {
                Map<Object, BigDecimal> accumulateGroup = accumulateGroup(groupData, limitMap);
                boolean addable = itemAddable(accumulateGroup, shipData, limitMap);
                if (addable) {
                    groupData.add(shipData);
                    continue label;
                }
            }

            boolean addable = itemAddable(accumulate, shipData, limitMap);
            if (addable) {
                addItem(accumulate, shipData, limitMap);
                groupList.add(shipData);
                continue;
            }

            // 创建新的分组
            ans.add(new ArrayList<>(groupList));
            groupList = new ArrayList<>();
            accumulate.clear();

            addable = itemAddable(accumulate, shipData, limitMap);
            if (!addable && accumulate.isEmpty()) {
                throw new IllegalArgumentException("单个物件:[" + shipData + "]已超过最大限制");
            }
            addItem(accumulate, shipData, limitMap);
            groupList.add(shipData);
        }
        if (!groupList.isEmpty()) {
            ans.add(new ArrayList<>(groupList));
        }
        return ans;
    }
}
