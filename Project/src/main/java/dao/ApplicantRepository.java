package dao;

import org.springframework.data.jpa.repository.JpaRepository;

import domain.Applicant;

public interface ApplicantRepository extends JpaRepository<Applicant, Integer>{

}
