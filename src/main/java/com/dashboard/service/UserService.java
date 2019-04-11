package com.dashboard.service;

import org.springframework.http.ResponseEntity;

import com.dashboard.model.User;



public interface UserService {
	
	String ReadUser(String userid);
	void AddUser(String name,String password,String email,String organisation);
	void UpdateUser(String userid,String name,String password,String email,String organisation);
	void DeleteUser(int userid);
	
}
