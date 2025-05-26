package com.example.binpacking.algorithmn;

import java.math.BigDecimal;
import java.util.*;

/**
 *@author lxh
 */
public class NextFitDecrease
        extends AbstractLoadData {

    public List<List<Map<Object, Object>>> nextFitDecrease(Comparator<Map<Object, Object>> comparator,
                                                           Map<Object, BigDecimal> limitMap) {
        return nextFitDecrease(DEFAULT_SHIP_DATA_NAME, comparator, limitMap);
    }

    public List<List<Map<Object, Object>>> nextFitDecrease(String shipFileName,
                                                           Comparator<Map<Object, Object>> comparator,
                                                           Map<Object, BigDecimal> limitMap) {
        List<Map<Object, Object>> shipDataList = loadShipLabelData(shipFileName);
        shipDataList.sort(comparator);
        List<List<Map<Object, Object>>> ans = new ArrayList<>();
        Map<Object, BigDecimal> accumulate = new HashMap<>();
        List<Map<Object, Object>> groupList = new ArrayList<>();
        for (int i = shipDataList.size() - 1; i >= 0; --i) {
            Map<Object, Object> shipData = shipDataList.get(i);
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
