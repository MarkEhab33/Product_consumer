package com.example.Product_Consumer.dto;

import org.apache.logging.log4j.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping("/test")
@CrossOrigin
public class WSController {
    @Autowired
    private WSService service;
    public void sendMessage(@RequestBody final String message){
        System.out.println(message);
        service.notifyFrontend(message);
    }
}
