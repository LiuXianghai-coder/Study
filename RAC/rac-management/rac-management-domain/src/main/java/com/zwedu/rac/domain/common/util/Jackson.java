package com.zwedu.rac.domain.common.util;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Jackson工具类 提供json和对象互相转换的基本方法
 * 定义了一个默认的ObjectMapper,如果默认的maaper不满足需求，可以根据需要自定义ObjectMapper
 *
 * @author qingchuan
 * @date 2020/12/9
 */
public final class Jackson {

    private Jackson() {
    }

    /**
     * 对象转换成json字符串,使用默认的objectMapper
     *
     * @param obj 需要转换的对象
     * @param <T>
     * @return json String
     */
    public static <T> String toJson(T obj) {
        return SimpleJacksonWrapper.toJson(obj);
    }


    /**
     * 对象转换成json字符串
     *
     * @param obj          需要转换的对象
     * @param objectMapper 自定义的mapper
     * @param <T>
     * @return json String
     */
    public static <T> String toJson(T obj, ObjectMapper objectMapper) {
        return SimpleJacksonWrapper.toJson(obj, objectMapper);
    }

    /**
     * json字符串转简单对象
     *
     * @param <T>
     * @param json  json
     * @param clazz 需要从json转换成的对象类型
     * @return 转换成的对象
     */
    public static <T> T toObject(String json, Class<T> clazz) {
        return SimpleJacksonWrapper.toObject(json, clazz);
    }

    /**
     * json字符串转简单对象
     *
     * @param <T>
     * @param json         json
     * @param clazz        需要从json转换成的对象类型
     * @param objectMapper 自定义的mapper
     * @return 转换成的对象
     */
    public static <T> T toObject(String json, Class<T> clazz, ObjectMapper objectMapper) {
        return SimpleJacksonWrapper.toObject(json, clazz, objectMapper);
    }

    /**
     * json字符串转复杂的泛型对象
     *
     * @param json          json
     * @param typeReference 例如：new TypeReference<List<User>>(){}
     * @param <T>           例如：List<User>
     * @return 转换成的对象
     */
    public static <T> T toObject(String json, TypeReference<T> typeReference) {
        return SimpleJacksonWrapper.toGenericObject(json, typeReference);
    }

    /**
     * json字符串转复杂的泛型对象
     *
     * @param json          json
     * @param typeReference 例如：new TypeReference<List<User>>(){}
     * @param <T>           例如：List<User>
     * @param objectMapper  自定义的mapper
     * @return
     */
    public static <T> T toObject(String json, TypeReference<T> typeReference, ObjectMapper objectMapper) {
        return SimpleJacksonWrapper.toGenericObject(json, typeReference, objectMapper);
    }

    /**
     * json字符串转指定集合类型和元素类型的集合
     *
     * @param json            json
     * @param collectionClass 想转换的集合类型,例如:List.class|Set.class|ArrayList.class ..
     * @param elementClass    集合中的元素类型，例如：User
     * @param <T>
     * @return 指定类型的集合，例如 List<User>
     */
    public static <T> T toCollection(String json, Class<? extends Collection> collectionClass, Class<?>
            elementClass) {
        return SimpleJacksonWrapper.toCollection(json, collectionClass, elementClass);
    }

    /**
     * json字符串转指定集合类型和元素类型的集合
     *
     * @param json            json
     * @param collectionClass 想转换的集合类型,例如:List.class|Set.class|ArrayList.class ..
     * @param elementClass    集合中的元素类型，例如：User
     * @param objectMapper    自定义的mapper
     * @param <T>
     * @return 指定类型的集合，例如 List<User>
     */
    public static <T> T toCollection(String json, Class<? extends Collection> collectionClass, Class<?>
            elementClass, ObjectMapper objectMapper) {
        return SimpleJacksonWrapper.toCollection(json, collectionClass, elementClass, objectMapper);
    }

    /**
     * json字符串转指定元素类型的List
     *
     * @param json         json
     * @param elementClass 集合中的元素类型，例如：User
     * @param <T>
     * @return 例如 List<User>
     */
    public static <T> List<T> toList(String json, Class<T> elementClass) {
        return StringUtils.isBlank(json) ? Lists.newArrayList() : SimpleJacksonWrapper.toList(json, elementClass);
    }

    /**
     * json字符串转指定元素类型的List
     *
     * @param json         json
     * @param elementClass 集合中的元素类型，例如：User
     * @param objectMapper 自定义的mapper
     * @param <T>
     * @return 例如 List<User>
     */
    public static <T> List<T> toList(String json, Class<T> elementClass, ObjectMapper objectMapper) {
        return StringUtils.isBlank(json) ? Lists.newArrayList() :
                SimpleJacksonWrapper.toList(json, elementClass, objectMapper);
    }

    /**
     * json字符串转成map
     *
     * @param json json
     * @return map
     */
    public static Map<String, Object> toMap(String json) {
        return SimpleJacksonWrapper.toMap(json);
    }

    /**
     * json字符串转成map
     *
     * @param json         json
     * @param objectMapper 自定义的mapper
     * @return map
     */
    public static Map<String, Object> toMap(String json, ObjectMapper objectMapper) {
        return SimpleJacksonWrapper.toMap(json, objectMapper);
    }
}
