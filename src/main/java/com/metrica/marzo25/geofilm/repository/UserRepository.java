package com.metrica.marzo25.geofilm.repository;

import com.metrica.marzo25.geofilm.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUsername(String username);

	Optional<User> findByEmail(String email);

	@Query(value = "SELECT u FROM User u WHERE u.email = :email AND u.password = :password")
	User loginWithEmailAndPassword(@Param("email") String email, @Param("password") String password);

	@Query(value = "INSERT INTO User u (u.email, u.password, u.username) VALUES (:email , :password , :username)")
	User registerUser(@Param("email") String email, @Param("password") String password, @Param("username") String username);
}