package dao;

import org.springframework.data.jpa.repository.JpaRepository;

import domain.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Integer>{

}