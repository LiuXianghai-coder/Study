package org.xhliu.kafkaexample;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.xhliu.kafkaexample.vo.Message;

public class TestApplication {
    public static void main(String[] args) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        Message message = new Message(1, "This is a Message");
        String json = mapper.writer().writeValueAsString(message);
        System.out.println(json);

        Message value = mapper.readValue(json, Message.class);
    }
}
