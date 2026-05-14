package com.manas.motivaid.motivaid.repository.chatRepo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manas.motivaid.motivaid.model.User;
import com.manas.motivaid.motivaid.model.chat.Conversation;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {
	Optional<Conversation> findByUser1AndUser2OrUser1AndUser2(
			User user1,
			User user2,
			User user3,
			User user4);
	

}
