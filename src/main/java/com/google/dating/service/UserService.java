package com.google.dating.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.google.dating.dao.UserDao;
import com.google.dating.dto.MatchingUser;
import com.google.dating.dto.UserSorting;
import com.google.dating.entity.User;
import com.google.dating.util.UserGender;

@Service
public class UserService {
	@Autowired
	UserDao userDao;

	public ResponseEntity<?> saveData(User user) {
		User saved=userDao.saveData(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(saved);
	}
   //////////////
	public ResponseEntity<?> findAllMaleUsers() {
		List<User> maleusers=userDao.findAllMaleUsers();
		if(maleusers.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No users present in DB");
		}
		else {
			return ResponseEntity.status(HttpStatus.OK).body(maleusers);
		}
		
	}
  //////////////
	public ResponseEntity<?> findAllFemaleUsers() {
		List<User> femaleusers=userDao.findAllFemaleUsers();
		if(femaleusers.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No users present in DB");
		}
		else {
			return ResponseEntity.status(HttpStatus.OK).body(femaleusers);
		}
	}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public ResponseEntity<?> bestMatches(int id, int top) {
	  Optional<User> optional=userDao.bestMatching(id);
	  if(optional.isEmpty()) {
		  return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ID not found in the DB");
	  }
	  User user=optional.get();
	  List<User> users=null;
	  if(user.getGender().equals(UserGender.MALE)) {
		  users=userDao.findAllFemaleUsers();
	  }
	  else {
		  users=userDao.findAllMaleUsers();
	  }

	  List<MatchingUser> matchingUsers=new ArrayList<>();
	  
	  for(User u:users) {
		  MatchingUser mu=new MatchingUser();
		  mu.setId(u.getId());
		  mu.setName(u.getName());
		  mu.setEmail(u.getEmail());
		  mu.setPhone(u.getPhone());
		  mu.setAge(u.getAge());
		  mu.setIntrests(u.getIntrests());
		  mu.setGender(u.getGender());
		  
		  mu.setAgeDiff(Math.abs(user.getAge()-u.getAge()));
		  
		  List<String> intrest1=user.getIntrests();
		  List<String> intrest2=u.getIntrests();
		  
		  int mic=0;
		  for(String s:intrest1) {
			  if(intrest2.contains(s)) {
				  mic++;
			  }
		  }
		  mu.setMic(mic);
		  matchingUsers.add(mu);
	  }
	  Comparator<MatchingUser> c=new UserSorting();
	  Collections.sort(matchingUsers,c);
	  List<MatchingUser> result= new ArrayList<>();
	  for (MatchingUser muu:matchingUsers) {
		  if(top==0) {
			  break;
		  }else {
			  result.add(muu);
			  top--;
		  }
	  }
	  return ResponseEntity.status(HttpStatus.OK).body(result); 
	}
 ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public ResponseEntity<?> findTheName(String name) {
		Optional<User> optional=userDao.findName(name);
		if(optional.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(optional);
		}
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid User name");
		}
		
	}
	public ResponseEntity<?> searchByName(String letters) {
		List<User> users=userDao.searchByName("%"+letters+"%");   //HQL
		
		if(users.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No user found with letters :"+letters);
		}
		else {
		    return ResponseEntity.status(HttpStatus.OK).body(users);

		}
	}
	public ResponseEntity<?> searchByEmail(String letters) {
		List<User> users=userDao.searchByEmail("%"+letters+"%");
		if (users.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No user found with this letters :"+letters);
		}
		else {
			return ResponseEntity.status(HttpStatus.OK).body(users);
		}
	}

	
}






















