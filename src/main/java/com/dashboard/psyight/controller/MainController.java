package com.dashboard.psyight.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dashboard.service.UserService;

@RestController
public class MainController {

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
	@RequestMapping(value = "/user/ReadUserFromId/{userid}", method = RequestMethod.GET, produces = "application/json")
	public String readUserFromId(@PathVariable("userid") String userid) {
		return uobj.readFromId(userid);
	}

	/// UPDATE USER
	@RequestMapping(value = "/user/add", method = RequestMethod.PUT, produces = "application/json")
	public String updateUser(HttpServletRequest request) {
		return uobj.update(request.getParameter("userid"), request.getParameter("name"),
				request.getParameter("password"), request.getParameter("email"), request.getParameter("organisation"));
	}

	/// DELETE USER WITH ID
	@RequestMapping(value = "/user/ReadUserFromId/{userid}", method = RequestMethod.DELETE, produces = "application/json")
	public String deleteUser(@PathVariable("userid") String userid) {
		return uobj.delete(userid);
	}
}