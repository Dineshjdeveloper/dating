package com.google.dating.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.google.dating.entity.User;
import com.google.dating.util.UserGender;

public interface UserRepository extends JpaRepository<User, Integer> {

	List<User> findByGender(UserGender male);


	Optional<User> findByName(String name);

	
    @Query("select u from User u where u.name like ?1")
	List<User> searchByName(String strin);

    @Query("select u from User u where u.email like ?1")
	List<User> searchByEmail(String letters);

}
