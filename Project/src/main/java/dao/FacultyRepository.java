package dao;

import org.springframework.data.jpa.repository.JpaRepository;

import domain.Faculty;

public interface FacultyRepository extends JpaRepository<Faculty, Integer>{

}
