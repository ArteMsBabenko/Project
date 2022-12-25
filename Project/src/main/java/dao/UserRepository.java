package dao;

import org.springframework.data.jpa.repository.JpaRepository;

import domain.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	User findByEmail(String email);
	
	User findByActivationCode(String code);
	
}
