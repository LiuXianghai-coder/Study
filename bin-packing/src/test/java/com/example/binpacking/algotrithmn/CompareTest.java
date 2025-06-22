package com.example.binpacking.algotrithmn;

import com.example.binpacking.algorithmn.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *@author lxh
 */
@ExtendWith(MockitoExtension.class)
public class CompareTest
        extends AbstractLoadData {

    private final static Logger log = LoggerFactory.getLogger(CompareTest.class);

    final Map<Object, BigDecimal> limitMap = new HashMap<>();

    @BeforeEach
    public void init() {
        limitMap.put("weight", BigDecimal.valueOf(1000));
        limitMap.put("length", BigDecimal.valueOf(5000));
        limitMap.put("width", BigDecimal.valueOf(2000));
        limitMap.put("height", BigDecimal.valueOf(1000));
    }

    @Test
    public void firstFitTest() {
        FirstFit firstFit = new FirstFit();
        Map<Object, BigDecimal> ffLimitMap = new HashMap<>();
        ffLimitMap.put("weight", BigDecimal.valueOf(100));
        List<List<Map<Object, Object>>> groupList = firstFit.firstFit(ffLimitMap);
        for (List<Map<Object, Object>> group : groupList) {
            Assertions.assertTrue(check(group));
        }
        Assertions.assertEquals(loadShipLabelData().size(), itemSum(groupList));
        log.info("First Fit group cnt: {}", groupList.size());
    }

    @Test
    public void nfdhTest() {
        NextFitDecrease nfdh = new NextFitDecrease();
        Comparator<Map<Object, Object>> comp = new Comparator<Map<Object, Object>>() {
            @Override
            public int compare(Map<Object, Object> o1, Map<Object, Object> o2) {
                int c1 = Double.compare((Double) o1.get("height"), (Double) o2.get("height"));
                if (c1 != 0) return c1;
                int c2 = Double.compare((Double) o1.get("width"), (Double) o2.get("width"));
                if (c2 != 0) return c2;

                int c3 = Double.compare((Double) o1.get("length"), (Double) o2.get("length"));
                if (c3 != 0) return c3;

                return Double.compare((Double) o1.get("weight"), (Double) o2.get("weight"));
            }
        };
        Map<Object, BigDecimal> nfdhLimitMap = new HashMap<>();
        nfdhLimitMap.put("weight", BigDecimal.valueOf(100));
        List<List<Map<Object, Object>>> groupList = nfdh.nextFitDecrease(comp, nfdhLimitMap);
        for (List<Map<Object, Object>> group : groupList) {
            Assertions.assertTrue(check(group));
        }
        Assertions.assertEquals(loadShipLabelData().size(), itemSum(groupList));
        log.info("Nfdh group cnt: {}", groupList.size());
    }

    @Test
    public void compressDpTest() {
        CompressDP compressDP = new CompressDP();
        Map<Object, BigDecimal> dpLimitMap = new HashMap<>();
        dpLimitMap.put("weight", BigDecimal.valueOf(100));
        List<List<Map<Object, Object>>> groupedList = compressDP.dp(dpLimitMap);
        for (List<Map<Object, Object>> group : groupedList) {
            Assertions.assertTrue(check(group));
        }
        log.info("{}", groupedList);
    }

    @Test
    public void aEpsilonTest() {
        AEpsilon aEpsilon = new AEpsilon();
        Map<Object, BigDecimal> epsilonLimitMap = new HashMap<>();
        epsilonLimitMap.put("weight", BigDecimal.valueOf(100));
        List<List<Map<Object, Object>>> groupList = aEpsilon.aEpsilon(new BigDecimal("0.999"), epsilonLimitMap);
        for (List<Map<Object, Object>> group : groupList) {
            Assertions.assertTrue(check(group));
        }
        Assertions.assertEquals(loadShipLabelData().size(), itemSum(groupList));
        log.info("AEpsilon group cnt: {}", groupList.size());
    }

    protected boolean check(List<Map<Object, Object>> dataList) {
        Map<Object, BigDecimal> acc = new HashMap<>();
        for (Map<Object, Object> data : dataList) {
            for (Map.Entry<Object, BigDecimal> entry : limitMap.entrySet()) {
                Object key = entry.getKey();
                acc.putIfAbsent(key, BigDecimal.ZERO);
                acc.put(key, acc.get(key).add(determineObjDecimalVal(data.get(key))));
                if (acc.get(key).compareTo(entry.getValue()) > 0) {
                    return false;
                }
            }
        }
        return true;
    }

    protected int itemSum(List<List<Map<Object, Object>>> dataList) {
        int ans = 0;
        for (List<Map<Object, Object>> groupData : dataList) {
            ans += groupData.size();
        }
        return ans;
    }
}
