package com.zwedu.rac.domain.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.*;

/**
 * 简单json装饰器
 *
 * @author qingchuan
 * @date 2020/12/9
 */
public final class SimpleJacksonWrapper {
    private static ObjectMapper DEFAULT_OBJECT_MAPPER = ObjectMapperCreator.createStanderObjectMapper();

    private SimpleJacksonWrapper() {
    }

    private static void assertNotNull(Object obj, String errorMsg) {
        if (Objects.isNull(obj)) {
            throw new IllegalArgumentException(errorMsg);
        }
    }

    public static <T> String toJson(T obj) {
        return toJson(obj, DEFAULT_OBJECT_MAPPER);
    }

    public static <T> String toJson(T obj, ObjectMapper objectMapper) {
        assertNotNull(objectMapper, "the parameter objectMapper cannot be null");

        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException var3) {
            throw new RuntimeException("toJson error.", var3);
        }
    }

    public static <T> T toObject(String json, Class<T> clazz) {
        return toObject(json, clazz, DEFAULT_OBJECT_MAPPER);
    }

    public static <T> T toObject(String json, Class<T> clazz, ObjectMapper objectMapper) {
        assertNotNull(objectMapper, "the parameter objectMapper cannot be null");

        try {
            return objectMapper.readValue(json, clazz);
        } catch (IOException var4) {
            throw new RuntimeException("toObject error.", var4);
        }
    }

    public static <T> T toGenericObject(String json, TypeReference<T> typeReference) {
        return toGenericObject(json, typeReference, DEFAULT_OBJECT_MAPPER);
    }

    public static <T> T toGenericObject(String json, TypeReference<T> typeReference, ObjectMapper objectMapper) {
        assertNotNull(objectMapper, "the parameter objectMapper cannot be null");
        assertNotNull(objectMapper, "the parameter typeReference cannot be null");

        try {
            return typeReference.getType().equals(String.class) ? (T) json : objectMapper.readValue(json, typeReference);
        } catch (Exception var4) {
            throw new RuntimeException("toGenericObject error.", var4);
        }
    }

    public static <T> T toCollection(String json, Class<? extends Collection> collectionClass, Class<?> elementClass) {
        return toCollection(json, collectionClass, elementClass, DEFAULT_OBJECT_MAPPER);
    }

    public static <T> T toCollection(String json, Class<? extends Collection> collectionClass, Class<?> elementClass, ObjectMapper objectMapper) {
        assertNotNull(objectMapper, "the parameter objectMapper cannot be null");

        try {
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(collectionClass, new Class[]{elementClass});
            return objectMapper.readValue(json, javaType);
        } catch (Exception var5) {
            throw new RuntimeException("toCollection error.", var5);
        }
    }

    public static <T> List<T> toList(String json, Class<T> elementClass) {
        return toList(json, elementClass, DEFAULT_OBJECT_MAPPER);
    }

    public static <T> List<T> toList(String json, Class<T> elementClass, ObjectMapper objectMapper) {
        return (List) toCollection(json, ArrayList.class, elementClass, objectMapper);
    }

    public static Map<String, Object> toMap(String json) {
        return toMap(json, DEFAULT_OBJECT_MAPPER);
    }

    public static Map<String, Object> toMap(String json, ObjectMapper objectMapper) {
        assertNotNull(objectMapper, "the parameter objectMapper cannot be null");
        return (Map) toGenericObject(json, new TypeReference<LinkedHashMap<String, Object>>() {
        }, objectMapper);
    }
}
