package dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import domain.Speciality;

public interface SpecialityRepository extends JpaRepository<Speciality, Integer>{
	
	List<Speciality> findByRecruitmentCompletedFalse();
	
	Optional<Speciality> findByTitle(String title);
}
