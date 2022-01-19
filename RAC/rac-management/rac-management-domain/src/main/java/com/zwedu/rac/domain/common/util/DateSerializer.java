package com.zwedu.rac.domain.common.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 日期序列化
 *
 * @author qingchuan
 * @date 2020/12/9
 */
public class DateSerializer {
    public static JsonSerializer<LocalDate> LOCAL_DATE_SERIALIZER = new JsonSerializer<LocalDate>() {
        public void serialize(LocalDate value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            if (value != null) {
                gen.writeString(String.valueOf(DateTimeUtils.convertToTimeMillis(value)));
            }

        }

        public Class<LocalDate> handledType() {
            return LocalDate.class;
        }
    };
    public static JsonDeserializer<LocalDate> LOCAL_DATE_DESERIALIZER = new JsonDeserializer<LocalDate>() {
        public LocalDate deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            String times = p.getText();
            LocalDate result = null;
            if (StringUtils.isNotEmpty(times)) {
                result = DateTimeUtils.convertToLocalDate(Long.valueOf(times));
            }

            return result;
        }

        public Class<LocalDate> handledType() {
            return LocalDate.class;
        }
    };
    public static JsonSerializer<LocalDateTime> LOCAL_DATE_TIME_SERIALIZER = new JsonSerializer<LocalDateTime>() {
        public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            if (value != null) {
                gen.writeString(String.valueOf(DateTimeUtils.convertToTimeMillis(value)));
            }

        }

        public Class<LocalDateTime> handledType() {
            return LocalDateTime.class;
        }
    };
    public static JsonDeserializer<LocalDateTime> LOCAL_DATE_TIME_DESERIALIZER = new JsonDeserializer<LocalDateTime>() {
        public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            String times = p.getText();
            LocalDateTime result = null;
            if (StringUtils.isNotEmpty(times)) {
                result = DateTimeUtils.convertToLocalDateTime(Long.valueOf(times));
            }

            return result;
        }

        public Class<LocalDateTime> handledType() {
            return LocalDateTime.class;
        }
    };

    public DateSerializer() {
    }
}
