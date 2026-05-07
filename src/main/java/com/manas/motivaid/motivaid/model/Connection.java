package com.manas.motivaid.motivaid.model;

import com.manas.motivaid.motivaid.enums.ConnectionStatus;

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
@Data
@Table(name="connections")
public class Connection  {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="from_user_id")
	private User fromUser;
	
	@ManyToOne
	@JoinColumn(name="to_user_id")
	private User toUser;
	
	@Enumerated(EnumType.STRING)
    @Column(nullable = false)
	private ConnectionStatus status;
	
}
