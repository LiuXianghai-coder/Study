package com.zwedu.rac.domain.common.util;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

/**
 * mapper创建者
 *
 * @author qingchuan
 * @date 2020/12/9
 */
public class ObjectMapperCreator {

    private ObjectMapperCreator() {
    }

    /**
     * 创建标准的对象映射
     *
     * @return 对象映射器
     */
    public static ObjectMapper createStanderObjectMapper() {
        List<JsonSerializer> serializerList = Lists.newArrayList(new JsonSerializer[]{DateSerializer.LOCAL_DATE_SERIALIZER, DateSerializer.LOCAL_DATE_TIME_SERIALIZER});
        List<JsonDeserializer> deserializerList = Lists.newArrayList(new JsonDeserializer[]{DateSerializer.LOCAL_DATE_DESERIALIZER, DateSerializer.LOCAL_DATE_TIME_DESERIALIZER});
        return createStanderObjectMapper(serializerList, deserializerList);
    }

    /**
     * 创建标准的对象映射器
     *
     * @param serializerList   序列化列表
     * @param deserializerList 反序列化列表
     * @return 对象映射器
     */
    public static ObjectMapper createStanderObjectMapper(List<JsonSerializer> serializerList, List<JsonDeserializer> deserializerList) {
        ObjectMapper standerMapper = new ObjectMapper();
        standerMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        standerMapper.configure(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS, true);
        standerMapper.configure(DeserializationFeature.USE_BIG_INTEGER_FOR_INTS, true);
        standerMapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true);
        standerMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
        standerMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        standerMapper.setSerializationInclusion(Include.NON_NULL);
        SimpleModule module = new SimpleModule();
        if (CollectionUtils.isNotEmpty(serializerList)) {
            serializerList.forEach((item) -> {
                module.addSerializer(item.handledType(), item);
            });
        }

        if (CollectionUtils.isNotEmpty(deserializerList)) {
            deserializerList.forEach((item) -> {
                module.addDeserializer(item.handledType(), item);
            });
        }

        standerMapper.registerModule(module);
        return standerMapper;
    }
}
