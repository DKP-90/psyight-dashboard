package com.dashboard.psyight.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;




import com.dashboard.model.User;
import com.dashboard.service.UserService;




@RestController
public class MainController {
	
	@Autowired
	UserService uobj;
	
	@RequestMapping(value = "/",method=RequestMethod.GET, produces = "application/json")
	public String adduser()
	{
		uobj.AddUser("name", "password", "email", "organisation");
		
		//System.out.println("fgfsdgfdgd");
		//return ResponseEntity.ok().body(uobj.ReadUser("1"));
		return(uobj.ReadUser("1"));
	}

}