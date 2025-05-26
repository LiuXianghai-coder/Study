package com.example.binpacking.algotrithmn;

import com.example.binpacking.algorithmn.AbstractLoadData;
import com.example.binpacking.algorithmn.FirstFit;
import com.example.binpacking.algorithmn.NextFitDecrease;
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
        List<List<Map<Object, Object>>> groupList = firstFit.firstFit(limitMap);
        for (List<Map<Object, Object>> group : groupList) {
            Assertions.assertTrue(check(group));
        }
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
        List<List<Map<Object, Object>>> groupList = nfdh.nextFitDecrease(comp, limitMap);
        for (List<Map<Object, Object>> group : groupList) {
            Assertions.assertTrue(check(group));
        }
        log.info("Nfdh group cnt: {}", groupList.size());
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
}
