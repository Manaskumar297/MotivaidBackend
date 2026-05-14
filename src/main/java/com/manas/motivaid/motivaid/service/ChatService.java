package com.manas.motivaid.motivaid.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.manas.motivaid.motivaid.dto.chat.ChatMessageResponse;
import com.manas.motivaid.motivaid.dto.chat.SendMessageRequest;
import com.manas.motivaid.motivaid.enums.MessageStatus;
import com.manas.motivaid.motivaid.model.User;
import com.manas.motivaid.motivaid.model.chat.Conversation;
import com.manas.motivaid.motivaid.model.chat.Message;
import com.manas.motivaid.motivaid.repository.UserRepository;
import com.manas.motivaid.motivaid.repository.chatRepo.ConversationRepository;
import com.manas.motivaid.motivaid.repository.chatRepo.MessageRepository;

@Service
public class ChatService {
	
	@Autowired
	private ConversationRepository conversationRepository;
	@Autowired
	private MessageRepository messageRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AuthService authService;
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	
	public void sendMessage(SendMessageRequest sendMessageRequest) {
		
		User sender= authService.getAuthenticatedUser();
		User receiver= userRepository.findById(sendMessageRequest.getReceiverId()).orElseThrow();
		Optional<Conversation> existingConversation=conversationRepository.findByUser1AndUser2OrUser1AndUser2(sender, receiver, receiver, sender);
		
		Conversation conversation;
		
		if (existingConversation.isPresent()) {
			conversation=existingConversation.get();
			
		}else {
			conversation = new Conversation();
			
			conversation.setUser1(sender);
			conversation.setUser2(receiver);
			conversation= conversationRepository.save(conversation);
			
		}
		
		Message message=new Message();
		
		message.setConversation(conversation);
		message.setSender(sender);
		message.setReceiver(receiver);
		message.setMessage(sendMessageRequest.getMessage());
		message.setMessageType(sendMessageRequest.getMessageType());
		message.setMessageStatus(MessageStatus.SENT);
		message.setIsRead(false);

		message=messageRepository.save(message);
		
		conversation.setLastMessage(message);
		conversation.setLastMessageTime(LocalDateTime.now());
		conversationRepository.save(conversation);
		
		 ChatMessageResponse response =
	                new ChatMessageResponse();

	        response.setMessageId(message.getId());
	        response.setSenderId(sender.getId());
	        response.setReceiverId(receiver.getId());
	        response.setMessage(message.getMessage());
	        response.setMessageStatus(message.getMessageStatus());
	        response.setCreatedDateTime(message.getCreatedDateTime());
	        
	        simpMessagingTemplate.convertAndSendToUser(
	                receiver.getId().toString(),
	                "/queue/messages",
	                response
	        );
	        
	} 

}
