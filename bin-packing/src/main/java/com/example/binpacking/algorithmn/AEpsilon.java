package com.example.binpacking.algorithmn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *@author lxh
 */
public class AEpsilon
        extends AbstractLoadData {

    private final static Logger log = LoggerFactory.getLogger(AEpsilon.class);

    private final static FirstFit FIRST_FIT = new FirstFit();

    private final static CompressDP COMPRESS_DP = new CompressDP();

    public List<List<Map<Object, Object>>> aEpsilon(BigDecimal epsilon,
                                                    Map<Object, BigDecimal> limitMap) {
        return aEpsilon(epsilon, loadShipLabelData(), limitMap);
    }

    public List<List<Map<Object, Object>>> aEpsilon(BigDecimal epsilon,
                                                    List<Map<Object, Object>> dataList,
                                                    Map<Object, BigDecimal> limitMap) {
        Map<Object, BigDecimal> firstFitFilter = new HashMap<>();
        for (Map.Entry<Object, BigDecimal> entry : limitMap.entrySet()) {
            firstFitFilter.put(entry.getKey(), entry.getValue().multiply(epsilon));
        }

        // 较大的则可以采用状态压缩的方式进行处理，较小的则采用 first-fit 进行填充
        List<Map<Object, Object>> biggestItemList = new ArrayList<>();
        List<Map<Object, Object>> smallerItemList = new ArrayList<>();
        final Map<Object, BigDecimal> emptyAcc = new HashMap<>();

        // 按照 epsilon 将物件分散到不同的物件处理列表中
        for (Map<Object, Object> shipData : dataList) {
            boolean addable = itemAddable(emptyAcc, shipData, firstFitFilter);
            if (addable) {
                smallerItemList.add(shipData);
            } else {
                biggestItemList.add(shipData);
            }
        }
        List<List<Map<Object, Object>>> partitionList = partition(epsilon, biggestItemList, limitMap);
        return FIRST_FIT.firstFit(smallerItemList, partitionList, limitMap);
    }

    protected List<List<Map<Object, Object>>> partition(BigDecimal epsilon,
                                                        List<Map<Object, Object>> dataList,
                                                        Map<Object, BigDecimal> limitMap) {
        int groupCnt = BigDecimal.ONE.divide(epsilon.pow(2), RoundingMode.FLOOR).intValue();
        if (groupCnt == 0) {
            throw new IllegalArgumentException("epsilon应当位于 (0,1) 区间范围内：" + epsilon);
        }
        if (dataList == null || dataList.isEmpty()) {
            log.debug("待分组的数据列表为空，不执行 Epsilon 的分组");
            return new ArrayList<>();
        }
        Map<Map<Object, Object>, Integer> roundMap = new HashMap<>();
        Map<Object, List<BigDecimal>> intervalListMap = createIntervalList(epsilon, dataList, limitMap);

        for (int i = 0; i < dataList.size(); i++) {
            Map<Object, Object> item = dataList.get(i);
            Map<Object, Object> roundItem = roundItem(item, limitMap, intervalListMap);
            roundMap.put(roundItem, i);
        }

        List<List<Map<Object, Object>>> ans = new ArrayList<>();
        List<List<Map<Object, Object>>> groupedList =
                COMPRESS_DP.dp(new ArrayList<>(roundMap.keySet()), limitMap);
        for (List<Map<Object, Object>> groupList : groupedList) {
            List<Map<Object, Object>> tmpList = new ArrayList<>();
            for (Map<Object, Object> objectMap : groupList) {
                if (!roundMap.containsKey(objectMap)) {
                    log.error("分组后的结果中，存在原有列表不包含的数据：{}", objectMap);
                    continue;
                }
                tmpList.add(dataList.get(roundMap.get(objectMap)));
            }
            ans.add(tmpList);
        }
        return ans;
    }

    private Map<Object, List<BigDecimal>> createIntervalList(BigDecimal epsilon,
                                                             List<Map<Object, Object>> dataList,
                                                             Map<Object, BigDecimal> limitMap) {
        // 计算每个属性的最大值和最小值
        Map<Object, BigDecimal> minMap = new HashMap<>();
        Map<Object, BigDecimal> maxMap = new HashMap<>();
        for (Map<Object, Object> item : dataList) {
            for (Map.Entry<Object, BigDecimal> entry : limitMap.entrySet()) {
                Object key = entry.getKey();
                BigDecimal itemVal = determineObjDecimalVal(item.get(key));
                minMap.put(key, min(itemVal, minMap.getOrDefault(key, BigDecimal.valueOf(Long.MAX_VALUE))));
                maxMap.put(key, max(itemVal, maxMap.getOrDefault(key, BigDecimal.valueOf(Long.MIN_VALUE))));
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("minMap={}, maxMap={}", minMap, maxMap);
        }
        // 最大值和最小值计算结束。。。。。
        Map<Object, List<BigDecimal>> intervalListMap = new HashMap<>();
        for (Map.Entry<Object, BigDecimal> entry : limitMap.entrySet()) {
            Object key = entry.getKey();
            BigDecimal minVal = minMap.get(key), maxVal = maxMap.get(key);
            intervalListMap.put(key, genIntervalList(minVal, maxVal, epsilon));
        }
        return intervalListMap;
    }

    private Map<Object, Object> roundItem(Map<Object, Object> item,
                                          Map<Object, BigDecimal> limitMap,
                                          Map<Object, List<BigDecimal>> intervalListMap) {
        Map<Object, Object> ans = new HashMap<>();
        for (Map.Entry<Object, Object> entry : item.entrySet()) {
            Object key = entry.getKey(), val = entry.getValue();
            List<BigDecimal> intervalList = intervalListMap.get(key);
            if (limitMap.containsKey(key) && val instanceof BigDecimal) {
                ans.put(key, search(intervalList, (BigDecimal) val));
            } else {
                ans.put(key, val);
            }
        }
        return ans;
    }

    private List<BigDecimal> genIntervalList(BigDecimal min, BigDecimal max, BigDecimal epsilon) {
        List<BigDecimal> ans = new ArrayList<>();
        BigDecimal cnt = BigDecimal.ONE.divide(epsilon, RoundingMode.CEILING);
        BigDecimal unit = max.subtract(min).divide(cnt, RoundingMode.CEILING);
        ans.add(min);
        for (BigDecimal val = min; val.compareTo(max) < 0; ) {
            BigDecimal next = min(val.add(unit), max);
            ans.add(next);
            val = next;
        }
        return ans;
    }

    private BigDecimal search(List<BigDecimal> intervalList, BigDecimal target) {
        int lo = 0, hi = intervalList.size();
        while (lo <= hi) {
            int mid = lo + ((hi - lo) >> 1);
            BigDecimal val = intervalList.get(mid);
            int comp = val.compareTo(target);
            if (comp == 0) {
                return val;
            }
            if (comp < 0) {
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }
        return intervalList.get(lo);
    }
}
