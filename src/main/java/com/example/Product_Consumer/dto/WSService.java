package com.example.Product_Consumer.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WSService {
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    public WSService(SimpMessagingTemplate messagingTemplate){
        this.messagingTemplate = messagingTemplate;
    }
    public void notifyFrontend(final String message){
        String response="hiiii " + message;
        messagingTemplate.convertAndSend("/topic/greetings",response);
    }
}
