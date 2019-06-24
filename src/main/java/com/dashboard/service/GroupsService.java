package com.dashboard.service;

import java.util.List;

public interface GroupsService {
	String read(String userid,int start,int limit);
	String readFromId(String userid, int gid);	
	String add(String groupname,String definition);
	String update(int gid,String groupname,List<?> products,String response_create,String response_train);
	String delete(int gid);
	
	String ImportXlsx(String userid);
}
