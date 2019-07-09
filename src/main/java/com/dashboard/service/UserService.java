package com.dashboard.service;


public interface UserService {
	
	String read(int start,int limit);
	String readFromId(String userid);
	String login(String email,String password);
	String add(String name,String password,String email,String organisation);
	String update(String userid,String name,String email,String organisation);
	String delete(String userid);
	
	String forgotPassword(String email);
	String changePassword(String userid, String newPassword, String oldPassword);
}
