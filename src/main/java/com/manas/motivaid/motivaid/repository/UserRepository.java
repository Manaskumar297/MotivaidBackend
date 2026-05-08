package com.manas.motivaid.motivaid.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manas.motivaid.motivaid.model.Role;
import com.manas.motivaid.motivaid.model.User;

public interface UserRepository extends JpaRepository<User,Long> {
	Optional<User> findByEmailId(String email_id);
	List<User> findByRolesContaining(Role role);
}
 