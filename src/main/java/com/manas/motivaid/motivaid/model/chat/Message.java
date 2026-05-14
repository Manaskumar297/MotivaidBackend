package com.manas.motivaid.motivaid.model.chat;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.manas.motivaid.motivaid.enums.MessageStatus;
import com.manas.motivaid.motivaid.enums.MessageType;
import com.manas.motivaid.motivaid.model.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
@Entity
@Table(name="messages")
@Data
public class Message {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="conversation_id")
	private Conversation conversation;
	
	@ManyToOne
	@JoinColumn(name="sender_id")
	private User sender;
	
	
	@ManyToOne
	@JoinColumn(name="receiver_id")
	private User receiver;
	
	@Column(columnDefinition = "TEXT")
	private String message;
	
	private String fileUrl;
	
	@Enumerated(EnumType.STRING)
	private MessageType messageType;
	
	@Enumerated(EnumType.STRING)
	private MessageStatus messageStatus;
	
	private Boolean isRead;
	
	@CreationTimestamp
	private LocalDateTime createdDateTime;
	
	
}
