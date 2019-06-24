package com.dashboard.service;

public interface ClarifaiService {

	String train(int gid,String userid);
	String detect(String userid,String gid);
}
