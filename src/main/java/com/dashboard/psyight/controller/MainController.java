package com.dashboard.psyight.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.dashboard.model.Products;
import com.dashboard.service.GroupsService;
import com.dashboard.service.ImportImplementation;
import com.dashboard.service.ImportService;
import com.dashboard.service.UserService;

import com.google.gson.Gson;


@RestController
public class MainController {

	////////////////////// USER SERVICES
	@Autowired
	UserService uobj;

	/// ADD USER
	@RequestMapping(value = "/user/add/", method = RequestMethod.POST, produces = "application/json")
	public String addUser(HttpServletRequest request) {
		return uobj.add(request.getParameter("name"), request.getParameter("password"), request.getParameter("email"),
				request.getParameter("organisation"));
	}

	/// USER LOGIN
	@RequestMapping(value = "/user/login/", method = RequestMethod.POST, produces = "application/json")
	public String loginUser(HttpServletRequest request) {
		return uobj.login(request.getParameter("email"), request.getParameter("password"));
	}

	/// READ USER WITH ID
	@RequestMapping(value = "/user/ReadUserFromId/{userid}/", method = RequestMethod.GET, produces = "application/json")
	public String readUserFromId(@PathVariable("userid") String userid) {
		return uobj.readFromId(userid);
	}

	/// UPDATE USER
	@RequestMapping(value = "/user/add/", method = RequestMethod.PUT, produces = "application/json")
	public String updateUser(HttpServletRequest request) {
		return uobj.update(request.getParameter("userid"), request.getParameter("name"),
				request.getParameter("password"), request.getParameter("email"), request.getParameter("organisation"));
	}

	/// DELETE USER WITH ID
	@RequestMapping(value = "/user/DeleteUserFromId/{userid}/", method = RequestMethod.DELETE, produces = "application/json")
	public String deleteUser(@PathVariable("userid") String userid) {
		return uobj.delete(userid);
	}
	
	
	////////////////////// GROUP SERVICES	
	
	
	@Autowired
	GroupsService gobj;
	

	

	/// ADD GROUP
	@RequestMapping(value = "/group/add/", method = RequestMethod.POST, produces = "application/json")
	public String addGroup(HttpServletRequest request) {
		return gobj.add(request.getParameter("groupname"), request.getParameter("definition"));		
	}


	/// READ GROUP WITH ID
	@RequestMapping(value = "/user/ReadGroupFromId/userid/{userid}/groupid/{gid}/", method = RequestMethod.GET, produces = "application/json")
	public String readGroupFromId(@PathVariable("userid") String userid,@PathVariable("gid") int gid) {
		return gobj.readFromId(userid, gid);
	}

	/// UPDATE GROUP
	@RequestMapping(value = "/group/add/", method = RequestMethod.PUT, produces = "application/json")
	public String updateGroup(HttpServletRequest request) {
		Gson gson = new Gson();
		String jsonInString = request.getParameter("products").toString();
		//String jsonInString = "{'gid': 1,'userid': 1,'active': 1,'definition':'sdgfdgd','product_name':'aaaaaaa'}";
		Products prd = gson.fromJson(jsonInString, Products.class);
		List<Products> prl=new ArrayList<Products>();
		prl.add(prd);			
		return gobj.update(Integer.parseInt(request.getParameter("gid")), request.getParameter("groupname"),prl);
	}

	/// DELETE GROUP WITH ID
	@RequestMapping(value = "/user/DeleteGroupFromId/{gid}/", method = RequestMethod.DELETE, produces = "application/json")
	public String deleteGroup(@PathVariable("gid") int gid) {
		return gobj.delete(gid);
	}
	

	



	
	
}