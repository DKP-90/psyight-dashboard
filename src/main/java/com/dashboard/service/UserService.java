package com.dashboard.service;


public interface UserService {
	
	String readUser(String userid);
	String readUserFromId(String userid);
	String addUser(String name,String password,String email,String organisation);
	String updateUser(String userid,String name,String password,String email,String organisation);
	String deleteUser(String userid);
	
}
