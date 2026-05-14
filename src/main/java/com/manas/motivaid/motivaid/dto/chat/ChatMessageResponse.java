package com.manas.motivaid.motivaid.dto.chat;

import java.time.LocalDateTime;

import com.manas.motivaid.motivaid.enums.MessageStatus;
import com.manas.motivaid.motivaid.enums.MessageType;

import lombok.Data;

@Data
public class ChatMessageResponse {

	private Long messageId;
	private Long senderId;
	private Long receiverId;
	private String message;
	private MessageType messageType;
	private MessageStatus messageStatus;
	private LocalDateTime createdDateTime;
}
