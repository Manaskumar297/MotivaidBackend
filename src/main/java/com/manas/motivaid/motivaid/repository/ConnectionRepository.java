package com.manas.motivaid.motivaid.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manas.motivaid.motivaid.model.Connection;
import com.manas.motivaid.motivaid.model.User;


public interface ConnectionRepository extends JpaRepository<Connection, Long>{
	List<Connection> findByFromUserOrToUser(User fromUser, User toUser);
	
}
