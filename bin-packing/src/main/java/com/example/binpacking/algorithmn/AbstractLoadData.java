package com.example.binpacking.algorithmn;

import com.example.binpacking.entity.ShippingLabel;
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

    protected boolean itemAddable(Map<Object, BigDecimal> accumulate, Map<Object, Object> item, Map<Object, BigDecimal> limitMap) {
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

    protected void addItem(Map<Object, BigDecimal> accumulate, Map<Object, Object> item, Map<Object, BigDecimal> limitMap) {
        for (Map.Entry<Object, BigDecimal> entry : limitMap.entrySet()) {
            Object key = entry.getKey();
            BigDecimal curVal = accumulate.getOrDefault(key, BigDecimal.ZERO);
            if (!item.containsKey(key)) continue;
            BigDecimal itemVal = determineObjDecimalVal(item.get(key));
            accumulate.put(key, curVal.add(itemVal));
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
}
