package com.example.binpacking.algorithmn;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 *@author lxh
 */
public abstract class AbstractLoadData {

    private final ObjectMapper objectMapper = new ObjectMapper();

    protected final static String DEFAULT_SHIP_DATA_NAME = "shipment_1.json";

    protected List<Map<Object, Object>> loadShipLabelData() {
        return loadShipLabelData(DEFAULT_SHIP_DATA_NAME);
    }

    protected List<Map<Object, Object>> loadShipLabelData(String fileName) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try {
            URL url = classLoader.getResource(fileName);
            return objectMapper.readValue(url, new TypeReference<List<Map<Object, Object>>>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected boolean itemAddable(Map<Object, BigDecimal> accumulate,
                                  Map<Object, Object> item,
                                  Map<Object, BigDecimal> limitMap) {
        for (Map.Entry<Object, BigDecimal> entry : limitMap.entrySet()) {
            Object key = entry.getKey();
            BigDecimal curVal = accumulate.getOrDefault(key, BigDecimal.ZERO);
            if (!item.containsKey(key)) continue;
            BigDecimal itemVal = determineObjDecimalVal(item.get(key));
            if (curVal.add(itemVal).compareTo(entry.getValue()) > 0) {
                return false;
            }
        }
        return true;
    }

    protected Map<Object, BigDecimal> accumulateGroup(List<Map<Object, Object>> groupData,
                                                      Map<Object, BigDecimal> limitMap) {
        Map<Object, BigDecimal> ans = new HashMap<>();
        for (Map<Object, Object> data : groupData) {
            for (Map.Entry<Object, BigDecimal> entry : limitMap.entrySet()) {
                Object key = entry.getKey();
                BigDecimal dataVal = determineObjDecimalVal(data.getOrDefault(key, BigDecimal.ZERO));
                ans.put(key, ans.getOrDefault(key, BigDecimal.ZERO).add(dataVal));
            }
        }
        return ans;
    }

    protected void addItem(Map<Object, BigDecimal> accumulate,
                           Map<Object, Object> item,
                           Map<Object, BigDecimal> limitMap) {
        for (Map.Entry<Object, BigDecimal> entry : limitMap.entrySet()) {
            Object key = entry.getKey();
            BigDecimal curVal = accumulate.getOrDefault(key, BigDecimal.ZERO);
            if (!item.containsKey(key)) continue;
            BigDecimal itemVal = determineObjDecimalVal(item.get(key));
            accumulate.put(key, curVal.add(itemVal));
        }
    }

    protected void removeItem(Map<Object, BigDecimal> accumulate,
                              Map<Object, Object> item,
                              Map<Object, BigDecimal> limitMap) {
        for (Map.Entry<Object, BigDecimal> entry : limitMap.entrySet()) {
            Object key = entry.getKey();
            BigDecimal curVal = accumulate.getOrDefault(key, BigDecimal.ZERO);
            if (!item.containsKey(key)) continue;
            BigDecimal itemVal = determineObjDecimalVal(item.get(key));
            accumulate.put(key, curVal.subtract(itemVal));
        }
    }

    protected BigDecimal determineObjDecimalVal(Object obj) {
        if (Objects.isNull(obj)) {
            return BigDecimal.ZERO;
        }
        if (obj instanceof BigDecimal) {
            return (BigDecimal) obj;
        }
        if (obj instanceof String) {
            return new BigDecimal((String) obj);
        }
        if (obj instanceof Number) {
            return BigDecimal.valueOf(((Number) obj).doubleValue());
        }
        return BigDecimal.ZERO;
    }

    protected List<Map<Object, Object>> shuffList(List<Map<Object, Object>> listData) {
        List<Map<Object, Object>> data = new ArrayList<>(listData.size());
        TreeMap<Integer, Map<Object, Object>> index2Map = new TreeMap<>();
        for (int i = 0; i < listData.size(); i++) {
            index2Map.put(i, listData.get(i));
        }
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 0; i < listData.size(); i++) {
            int j = random.nextInt(index2Map.ceilingKey(Integer.MIN_VALUE),
                    index2Map.floorKey(Integer.MAX_VALUE) + 1);
            data.add(index2Map.get(j));
        }
        assert data.size() == listData.size();
        return data;
    }

    protected static BigDecimal min(BigDecimal b1, BigDecimal b2) {
        return compareObj(b1, b2)  < 0 ? b1 : b2;
    }

    protected static BigDecimal max(BigDecimal b1, BigDecimal b2) {
        return compareObj(b1, b2) < 0 ? b2 : b1;
    }

    protected static <T extends Comparable<T>> int compareObj(T o1, T o2) {
        if (o1 == null && o2 == null) {
            return 0;
        }
        if (o1 == null) return 1;
        if (o2 == null) return -1;
        return o1.compareTo(o2);
    }
}
