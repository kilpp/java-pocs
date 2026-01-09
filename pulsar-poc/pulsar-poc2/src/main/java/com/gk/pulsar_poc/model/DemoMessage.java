package com.gk.pulsar_poc.model;

import java.time.LocalDateTime;

public class DemoMessage {

    private String id;
    private String content;
    private LocalDateTime timestamp;

    public DemoMessage() {
    }

    public DemoMessage(String id, String content) {
        this.id = id;
        this.content = content;
        this.timestamp = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "DemoMessage{" +
                "id='" + id + '\'' +
                ", content='" + content + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}

