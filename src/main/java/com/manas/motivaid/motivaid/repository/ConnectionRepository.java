package com.manas.motivaid.motivaid.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.manas.motivaid.motivaid.enums.ConnectionStatus;
import com.manas.motivaid.motivaid.model.Connection;
import com.manas.motivaid.motivaid.model.User;


public interface ConnectionRepository extends JpaRepository<Connection, Long>{
	List<Connection> findByFromUserOrToUser(User fromUser, User toUser);
	Optional<Connection>findByFromUserAndToUserOrFromUserAndToUser( User fromUser1,User toUser1,User fromUser2,User toUser2);
	Optional<Connection>findOptionalByFromUserAndToUser(User fromUser,User toUser);
	Page<Connection>findByToUserAndStatus(User toUser,ConnectionStatus status, Pageable pageable);
	
}
