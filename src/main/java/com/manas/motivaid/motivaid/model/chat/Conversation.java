package com.manas.motivaid.motivaid.model.chat;

import java.security.MessageDigest;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.manas.motivaid.motivaid.model.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="conversations")
@Data
public class Conversation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "user1_id")
	private User user1;
	
	@ManyToOne
	@JoinColumn(name="user2_id")
	private User user2;
	
	@OneToOne
	@JoinColumn(name="last_message_id")
	private Message lastMessage;
	
	private LocalDateTime lastMessageTime;
	@CreationTimestamp 
	private LocalDateTime  createdDatetime;
}
