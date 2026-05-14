package com.manas.motivaid.motivaid.repository.chatRepo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.manas.motivaid.motivaid.model.chat.Conversation;
import com.manas.motivaid.motivaid.model.chat.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
	Page<Message> findByConversation(Conversation conversation,Pageable pageable);

}
