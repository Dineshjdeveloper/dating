package com.google.dating.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.dating.entity.User;
import com.google.dating.repository.UserRepository;
import com.google.dating.util.UserGender;

@Repository
public class UserDao {
	@Autowired
	UserRepository userRepository;

	
	public User saveData(User user) {
		return userRepository.save(user);

		
	}


	public List<User> findAllMaleUsers() {
		
		return userRepository.findByGender(UserGender.MALE);
	}


	public List<User> findAllFemaleUsers() {
		return userRepository.findByGender(UserGender.FEMALE);
		
	}


	public Optional<User> bestMatching(int id) {
		return userRepository.findById(id);
	}


	public Optional<User> findName(String name) {
		return userRepository.findByName(name);
	}


	public List<User> searchByName(String strin) {
		
		return userRepository.searchByName(strin);
	}


	public List<User> searchByEmail(String letters) {
		return userRepository.searchByEmail(letters);
	}


}


























