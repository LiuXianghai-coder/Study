package org.xhliu.kafkaexample.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;
import org.xhliu.kafkaexample.vo.Message;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public class MineSerializer implements Serializer<Message> {
    private final static ObjectMapper mapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Serializer.super.configure(configs, isKey);
    }

    @Override
    public byte[] serialize(String topic, Message data) {
        try {
            String value = mapper.writer().writeValueAsString(data);
            return value.getBytes(StandardCharsets.UTF_8);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public byte[] serialize(String topic, Headers headers, Message data) {
        return Serializer.super.serialize(topic, headers, data);
    }

    @Override
    public void close() {
        Serializer.super.close();
    }
}
