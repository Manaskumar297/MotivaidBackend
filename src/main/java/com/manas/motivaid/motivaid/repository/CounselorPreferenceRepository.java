package com.manas.motivaid.motivaid.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manas.motivaid.motivaid.model.CounselorPreference;
import com.manas.motivaid.motivaid.model.User;

public interface CounselorPreferenceRepository extends JpaRepository<CounselorPreference, Long> {
	Optional<CounselorPreference> findByUser(User user);

}
