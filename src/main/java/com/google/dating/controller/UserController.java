package com.google.dating.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.google.dating.entity.User;
import com.google.dating.service.UserService;

@Controller
public class UserController {
	@Autowired
	UserService userService;
	
	@PostMapping("/users")
	public ResponseEntity<?> saveTheData(@RequestBody User user){
		return userService.saveData(user);
	}
	
	@GetMapping("/users/gender/male")
	public ResponseEntity<?> findAllMaleUsers(){
		return userService.findAllMaleUsers();
	}

	
	
	
	@GetMapping("/users/gender/female")
	public ResponseEntity<?> findAllFemaleUsers(){
		return userService.findAllFemaleUsers();
	}
	
	@GetMapping("/users/best_matches/{id}/{top}")
	public ResponseEntity<?> bestMathes(@PathVariable int id,@PathVariable int top){
		return userService.bestMatches(id,top);
	}
	
	@GetMapping("/users/{name}")
	public ResponseEntity<?> findByName(@PathVariable String name){
		return userService.findTheName(name);
	}

}
