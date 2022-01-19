package com.zwedu.rac.domain.common.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Collection 工具类
 *
 * @author qingchuan
 * @date 2020/12/11
 */
public class Collections2 {

    /**
     * 把集合中的对象 转换成Map
     *
     * @param <T>  需要解析的数据类型
     * @param <K>  key的数据类型
     * @param list 集合列表
     * @param key  获取key方法
     * @return map集合
     */
    public static <T, K> Map<K, T> toMap(Collection<T> list,
                                         Function<T, K> key) {
        if (CollectionUtils.isEmpty(list)) {
            return Maps.newHashMap();
        }
        Map<K, T> map = Maps.newHashMap();
        for (T t : list) {
            map.put(key.apply(t), t);
        }
        return map;
    }

    /**
     * 把集合中的对象 转换成Map
     *
     * @param <T>   需要解析的数据类型
     * @param <K>   key的数据类型
     * @param <V>   value的数据类型
     * @param list  集合列表
     * @param key   获取key方法
     * @param value 获取值值
     * @return map集合
     */
    public static <T, K, V> Map<K, V> toMap(Collection<T> list,
                                            Function<T, K> key, Function<T, V> value) {
        if (CollectionUtils.isEmpty(list)) {
            return Maps.newHashMap();
        }
        Map<K, V> map = new HashMap<K, V>();
        for (T t : list) {
            map.put(key.apply(t), value.apply(t));
        }
        return map;
    }

    /**
     * 把查询出来的List<Map>转换成key-value形势的map
     *
     * @param <K>         key的数据类型
     * @param <V>         value的数据类型
     * @param dataList    查询出来的原始数据结构，每个map代表一行数据
     * @param keyColumn   代表key的数据column
     * @param valueColumn 代表value的数据column
     * @param keyClass    key的class类型
     * @param valueClass  value的class类型
     * @return 转换成key-value形势的map
     */
    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, V> toMap(List<Map<String, Object>> dataList,
                                         String keyColumn, String valueColumn, Class<K> keyClass,
                                         Class<V> valueClass) {
        if (CollectionUtils.isEmpty(dataList)) {
            return Maps.newHashMap();
        }
        Map<K, V> resultMap = new HashMap<K, V>();
        if (dataList == null || dataList.size() == 0) {
            return resultMap;
        }
        for (Map<String, Object> item : dataList) {
            Object keyObject = item.get(keyColumn);
            Object valueObject = item.get(valueColumn);
            if (keyObject != null && valueObject != null) {
                resultMap.put((K) keyObject, (V) valueObject);
            }
        }
        return resultMap;
    }

    /**
     * 把集合中的对象 转换成Map
     *
     * @param <T>  需要解析的数据类型
     * @param <K>  key的数据类型
     * @param list 集合列表
     * @param key  获取key方法
     * @return map集合
     */
    public static <T, K> Map<K, T> toLinkedMap(Collection<T> list,
                                               Function<T, K> key) {
        if (CollectionUtils.isEmpty(list)) {
            return Maps.newLinkedHashMap();
        }
        Map<K, T> map = Maps.newLinkedHashMap();
        for (T t : list) {
            map.put(key.apply(t), t);
        }
        return map;
    }

    /**
     * 把集合中的对象 转换成Map
     *
     * @param <T>   需要解析的数据类型
     * @param <K>   key的数据类型
     * @param <V>   value的数据类型
     * @param list  集合列表
     * @param key   获取key方法
     * @param value 获取值值
     * @return map集合
     */
    public static <T, K, V> Map<K, V> toLinkedMap(Collection<T> list,
                                                  Function<T, K> key, Function<T, V> value) {
        if (CollectionUtils.isEmpty(list)) {
            return Maps.newLinkedHashMap();
        }
        Map<K, V> map = Maps.newLinkedHashMap();
        for (T t : list) {
            map.put(key.apply(t), value.apply(t));
        }
        return map;
    }

    /**
     * 把集合中的对象 转换成Map
     *
     * @param <T>   需要解析的数据类型
     * @param <K>   key的数据类型
     * @param <V>   value的数据类型
     * @param list  集合列表
     * @param key   获取key方法
     * @param value 获取值值
     * @return map集合
     */
    public static <T, K, V> Map<K, List<V>> toMapForListValue(
            Collection<T> list, Function<T, K> key, Function<T, V> value) {
        if (CollectionUtils.isEmpty(list)) {
            return Maps.newHashMap();
        }
        Map<K, List<V>> map = new HashMap<K, List<V>>();
        for (T input : list) {
            if (map.containsKey(key.apply(input))) {
                map.get(key.apply(input)).add(value.apply(input));
                continue;
            }
            List<V> values = new ArrayList<V>();
            values.add(value.apply(input));
            map.put(key.apply(input), values);
        }
        return map;
    }


    /**
     * 把集合中的对象 转换成Map
     *
     * @param <F>  需要解析的数据类型
     * @param <T>  结果类型
     * @param list 集合列表
     * @return map集合
     */
    public static <F, T> List<T> toList(List<F> list, Function<F, T> function) {
        if (CollectionUtils.isEmpty(list)) {
            return Lists.newArrayList();
        }
        return list.stream().map(input -> function.apply(input)).collect(Collectors.toList());
    }

    /**
     * 把集合中的对象 转换成Map
     *
     * @param <F>  需要解析的数据类型
     * @param <T>  结果类型
     * @param list 集合列表
     * @return map集合
     */
    public static <F, T> Set<T> toSet(List<F> list, Function<F, T> function) {
        if (CollectionUtils.isEmpty(list)) {
            return Sets.newHashSet();
        }
        return list.stream().map(input -> function.apply(input)).collect(Collectors.toSet());
    }


    /**
     * 把集合中的对象 转换成Map
     *
     * @param <T>  需要解析的数据类型
     * @param <K>  key的数据类型
     * @param <K1> value的数据类型
     * @param list 集合列表
     * @param key  获取key方法
     * @param key1 第二个key
     * @return map集合
     */
    public static <T, K, K1> Map<K, Map<K1, T>> toValueMap(
            Collection<T> list, Function<T, K> key, Function<T, K1> key1) {
        if (CollectionUtils.isEmpty(list)) {
            return Maps.newHashMap();
        }
        Map<K, Map<K1, T>> map = new HashMap<K, Map<K1, T>>();
        for (T input : list) {
            if (map.containsKey(key.apply(input))) {
                map.get(key.apply(input)).put(key1.apply(input), input);
                continue;
            }
            Map<K1, T> values = Maps.newHashMap();
            values.put(key1.apply(input), input);
            map.put(key.apply(input), values);
        }
        return map;
    }

    /**
     * 字符串转列表
     *
     * @param str       字符串
     * @param separator 分隔符
     * @return 列表
     */
    public static List<Long> toLongList(String str, char separator) {
        if (StringUtils.isEmpty(str)) {
            return Lists.newArrayList();
        }
        String[] itemList = StringUtils.split(str, separator);
        List<Long> retList = Lists.newArrayList();
        for (String item : itemList) {
            retList.add(Long.parseLong(item));
        }
        return retList;
    }

    /**
     * 字符串转列表
     *
     * @param str       字符串
     * @param separator 分隔符
     * @return 列表
     */
    public static List<String> toStringList(String str, char separator) {
        if (StringUtils.isEmpty(str)) {
            return Lists.newArrayList();
        }
        String[] itemList = StringUtils.split(str, separator);
        List<String> retList = Lists.newArrayList();
        for (String item : itemList) {
            retList.add(item);
        }
        return retList;
    }


    /**
     * 列表收集
     *
     * @param collection 集合
     * @return 列表
     */
    public static <F, T> List<T> collect(Collection<F> collection, Function<F, Collection<T>> function) {
        if (CollectionUtils.isEmpty(collection)) {
            return Lists.newArrayList();
        }
        List<T> retList = Lists.newArrayList();
        Iterator<F> iterator = collection.iterator();
        while (iterator.hasNext()) {
            retList.addAll(function.apply(iterator.next()));
        }
        return retList;
    }

    /**
     * 根据KEY值升序排序
     *
     * @param <K> key的数据类型
     * @param <V> value的数据类型
     * @return map集合
     */
    public static <K extends Comparable<? super K>, V> Map<K, V> sortByKeyAsc(Map<K, V> map) {
        if (MapUtils.isEmpty(map)) {
            return Maps.newLinkedHashMap();
        }
        return map.entrySet().stream().sorted(Map.Entry.<K, V>comparingByKey()).collect(
                Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new));
    }

    /**
     * 根据KEY值降序排序
     *
     * @param <K> key的数据类型
     * @param <V> value的数据类型
     * @return map集合
     */
    public static <K extends Comparable<? super K>, V> Map<K, V> sortByKeyDesc(Map<K, V> map) {
        if (MapUtils.isEmpty(map)) {
            return Maps.newLinkedHashMap();
        }
        return map.entrySet().stream().sorted(Map.Entry.<K, V>comparingByKey().reversed()).collect(
                Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new));
    }

    /**
     * 根据VALUE值降序排序
     *
     * @param <K> key的数据类型
     * @param <V> value的数据类型
     * @return map集合
     */
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValueDesc(Map<K, V> map) {
        if (MapUtils.isEmpty(map)) {
            return Maps.newLinkedHashMap();
        }
        return map.entrySet().stream().sorted(Map.Entry.<K, V>comparingByValue().reversed()).collect(
                Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new));
    }

    /**
     * 根据VALUE值升序排序
     *
     * @param <K> key的数据类型
     * @param <V> value的数据类型
     * @return map集合
     */
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValueAsc(Map<K, V> map) {
        if (MapUtils.isEmpty(map)) {
            return Maps.newLinkedHashMap();
        }
        return map.entrySet().stream().sorted(Map.Entry.comparingByValue()).collect(Collectors.toMap(
                Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }

    /**
     * 转化空列表
     *
     * @param list 列表
     * @param <T>  类型
     * @return 结果
     */
    public static <T> Collection<T> trimNull(List<T> list) {
        return CollectionUtils.isEmpty(list) ? Lists.newArrayList() : list;
    }

    /**
     * 转化空列表
     *
     * @param set 集合
     * @param <T> 类型
     * @return 集合
     */
    public static <T> Collection<T> trimNull(Set<T> set) {
        return CollectionUtils.isEmpty(set) ? Sets.newHashSet() : set;
    }

    /**
     * 转化string集合
     *
     * @param content 内容
     * @param comma   分割符
     * @return string set
     */
    public static Set<String> toStringSet(String content, char comma) {
        if (StringUtils.isEmpty(content)) {
            return Sets.newHashSet();
        }
        String[] itemList = StringUtils.split(content, comma);
        Set<String> retSet = Sets.newHashSet();
        for (String item : itemList) {
            retSet.add(item);
        }
        return retSet;
    }
}
