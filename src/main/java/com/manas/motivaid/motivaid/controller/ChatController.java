package com.manas.motivaid.motivaid.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import com.manas.motivaid.motivaid.dto.chat.SendMessageRequest;
import com.manas.motivaid.motivaid.service.ChatService;

@Controller
public class ChatController {

    @Autowired
    private ChatService chatService;

    @MessageMapping("/send-message")
    public void sendMessage(
            SendMessageRequest request
    ) {

        chatService.sendMessage(request);
    }
}