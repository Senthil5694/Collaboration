package com.Controller;

import java.util.Date;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.Model.Message;
import com.Model.OutputMessage;

@Controller
public class ChatController {
	
	@MessageMapping("/chat")
	@SendTo("/topic/message")
	public OutputMessage sendMessage(Message message){
		System.out.println("Inside chat contoller of back end...");
		return new OutputMessage(message,new Date());
	}

}
