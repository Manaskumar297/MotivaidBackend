package com.manas.motivaid.motivaid.dto.chat;


import com.manas.motivaid.motivaid.enums.MessageType;

import lombok.Data;

@Data
public class SendMessageRequest {
	
	private Long receiverId;
	private String message;
	private MessageType messageType;

}
