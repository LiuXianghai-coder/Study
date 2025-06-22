package com.example.binpacking.algorithmn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *@author lxh
 */
public class CompressDP
        extends AbstractLoadData {

    private final static Logger log = LoggerFactory.getLogger(CompressDP.class);

    private final static String DEFAULT_DP_SHIP_DATA_NAME = "dp_shipment.json";

    private final static ThreadLocal<List<Integer>> GROUP_MASK_LOCAL = new ThreadLocal<>();

    private final static ThreadLocal<Integer> DP_LOCAL = new ThreadLocal<>();

    public List<List<Map<Object, Object>>> dp(Map<Object, BigDecimal> limitMap) {
        List<Map<Object, Object>> dataList = loadShipLabelData(DEFAULT_DP_SHIP_DATA_NAME);
        return dp(dataList, limitMap);
    }

    public List<List<Map<Object, Object>>> dp(List<Map<Object, Object>> dataList,
                                              Map<Object, BigDecimal> limitMap) {
        if (dataList.size() > 20) {
            log.warn("执行状态压缩 DP 的数据量过大：{}", dataList.size());
            return new ArrayList<>();
        }
        DP_LOCAL.set(Integer.MAX_VALUE);
        GROUP_MASK_LOCAL.set(new ArrayList<>());
        dp(dataList, 0, new ArrayList<>(), new ArrayList<>(), limitMap);

        List<Integer> groupMaskList = GROUP_MASK_LOCAL.get();
        List<List<Map<Object, Object>>> ans = new ArrayList<>();
        for (Integer mask : groupMaskList) {
            List<Map<Object, Object>> tmpList = new ArrayList<>();
            for (int i = 0; i < dataList.size(); i++) {
                if (((mask >> i) & 1) == 0) continue;
                tmpList.add(dataList.get(i));
            }
            ans.add(tmpList);
        }
        log.info("状态压缩最少需要 [{}] 个分组，分组结果为: {}", DP_LOCAL.get(), ans);
        return ans;
    }

    private void dp(List<? extends Map<?, ?>> dataList, int index,
                    List<Integer> groupList,
                    List<Map<Object, BigDecimal>> accmulateList,
                    Map<Object, BigDecimal> limitMap) {
        int n = dataList.size();
        if (index >= n) {
            int cnt = groupList.size();
            if (check(n, groupList)) {
                Integer rawCnt = DP_LOCAL.get();
                if (cnt < rawCnt) {
                    DP_LOCAL.set(cnt);
                    List<Integer> list = new ArrayList<>(groupList);
                    GROUP_MASK_LOCAL.set(list);
                }
            }
            return;
        }

        @SuppressWarnings("unchecked")
        Map<Object, Object> item = (Map<Object, Object>) dataList.get(index);

        // 检查是否能够加入已有分组
        for (int i = 0; i < groupList.size(); i++) {
            int m = groupList.get(i);
            Map<Object, BigDecimal> valMap = accmulateList.get(i);
            if (!itemAddable(valMap, item, limitMap)) {
                continue;
            }
            int nm = m | (1 << index);
            groupList.set(i, nm);
            addItem(valMap, item, limitMap);
            dp(dataList, index + 1, groupList, accmulateList, limitMap);
            removeItem(valMap, item, limitMap);
            groupList.set(i, m);
        }

        // 新创建一个分组
        groupList.add(1 << index);
        HashMap<Object, BigDecimal> accumulate = new HashMap<>();
        accmulateList.add(accumulate);
        addItem(accumulate, item, limitMap);
        dp(dataList, index + 1, groupList, accmulateList, limitMap);
        groupList.remove(groupList.size() - 1);
    }

    private boolean check(int n, List<Integer> groupList) {
        int targetMask = (1 << n) - 1;
        int mask = 0;
        for (Integer m : groupList) {
            mask |= m;
        }
        return targetMask == mask;
    }
}
