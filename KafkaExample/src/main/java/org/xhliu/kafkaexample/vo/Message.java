package org.xhliu.kafkaexample.vo;

public class Message {
    private int id;
    private String body;

    public Message(){}

    public Message(int id, String body) {
        this.id = id;
        this.body = body;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getId() {
        return id;
    }

    public String getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", body='" + body + '\'' +
                '}';
    }
}
