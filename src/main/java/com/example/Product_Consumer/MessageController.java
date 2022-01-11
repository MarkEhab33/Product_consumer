package com.example.Product_Consumer;
import  com.example.Product_Consumer.dto.Message;
import  com.example.Product_Consumer.dto.ResponseMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class MessageController {
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public ResponseMessage getMessage(final Message message) throws InterruptedException {
       Thread.sleep(1000);
        return new ResponseMessage("Hello, "+HtmlUtils.htmlEscape(message.getMessageContent()));
    }
}
