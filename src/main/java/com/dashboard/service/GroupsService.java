package com.dashboard.service;

import java.util.List;
import java.util.Set;

import com.dashboard.model.Products;

public interface GroupsService {
	String read(String userid,int start,int limit);
	String readFromId(String userid, int gid);	
	String add(String groupname,String definition,String userid);
	String update(int gid,String groupname,Set<Products> prl,String response_create,String response_train);
	String delete(int gid);
	
	String ImportXlsx(String userid);
}
