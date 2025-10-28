package com.gk.springaipoc;

import org.springframework.ai.chat.messages.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/chat")
    public ResponseEntity<Message> receiveMessage(@RequestBody String message) {
        System.out.println("Received message: " + message);
        Message response = chatService.sendChatMessage(message);
        return ResponseEntity.ok(response);
    }
}