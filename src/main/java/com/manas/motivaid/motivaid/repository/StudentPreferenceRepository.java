package com.manas.motivaid.motivaid.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manas.motivaid.motivaid.model.StudentPreference;
import com.manas.motivaid.motivaid.model.User;

public interface StudentPreferenceRepository extends JpaRepository<StudentPreference, Long>{
	Optional<StudentPreference> findByUser(User user);

}
