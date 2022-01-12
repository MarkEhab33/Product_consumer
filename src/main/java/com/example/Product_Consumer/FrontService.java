package com.example.Product_Consumer;

import com.example.Product_Consumer.dto.WSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class FrontService {
    WSService service;
    @Autowired
    public FrontService(SimpMessagingTemplate simpMessagingTemplate){
        this.service=new WSService(simpMessagingTemplate);
    }

    public void sendToFront(String json) throws InterruptedException {
        this.service.notifyFrontend(json);
    }
}
