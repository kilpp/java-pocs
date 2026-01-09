package com.gk.pulsar_poc.controller;

import com.gk.pulsar_poc.model.DemoMessage;
import com.gk.pulsar_poc.service.MessageProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageProducer messageProducer;

    public MessageController(MessageProducer messageProducer) {
        this.messageProducer = messageProducer;
    }

    @PostMapping("/send")
    public ResponseEntity<Map<String, String>> sendMessage(@RequestBody Map<String, String> request) {
        String content = request.getOrDefault("content", "Default message");
        String id = UUID.randomUUID().toString();

        DemoMessage message = new DemoMessage(id, content);
        messageProducer.sendMessage(message);

        Map<String, String> response = new HashMap<>();
        response.put("status", "Message sent");
        response.put("messageId", id);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/send-with-key")
    public ResponseEntity<Map<String, String>> sendMessageWithKey(@RequestBody Map<String, String> request) {
        String content = request.getOrDefault("content", "Default message");
        String key = request.getOrDefault("key", "default-key");
        String id = UUID.randomUUID().toString();

        DemoMessage message = new DemoMessage(id, content);
        messageProducer.sendMessageWithKey(message, key);

        Map<String, String> response = new HashMap<>();
        response.put("status", "Message sent with key");
        response.put("messageId", id);
        response.put("key", key);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/test")
    public ResponseEntity<Map<String, String>> testEndpoint() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "API is working");
        response.put("message", "Use POST /api/messages/send to send messages to Pulsar");

        return ResponseEntity.ok(response);
    }
}

