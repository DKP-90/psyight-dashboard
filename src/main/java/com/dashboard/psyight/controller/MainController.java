package com.dashboard.psyight.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.dashboard.model.Products;
import com.dashboard.service.ClarifaiService;
import com.dashboard.service.GroupsService;
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
		Products prd = gson.fromJson(jsonInString, Products.class);
		List<Products> prl=new ArrayList<Products>();
		prl.add(prd);			
		return gobj.update(Integer.parseInt(request.getParameter("gid")), request.getParameter("groupname"),prl,request.getParameter("response_create"),request.getParameter("response_train"));
	}

	/// DELETE GROUP WITH ID
	@RequestMapping(value = "/user/DeleteGroupFromId/{gid}/", method = RequestMethod.DELETE, produces = "application/json")
	public String deleteGroup(@PathVariable("gid") int gid) {
		return gobj.delete(gid);
	}
	
	@Autowired
	ClarifaiService cs;
	
	/// READ GROUP WITH ID
	@RequestMapping(value = "/user/trainGroupFromId/userid/{userid}/groupid/{gid}/", method = RequestMethod.GET, produces = "application/json")
	public String trainGroupFromId(@PathVariable("userid") String userid,@PathVariable("gid") int gid) {
		return cs.train(gid, userid);
	}
	
	//READ IMAGES
	@RequestMapping(value = "/userid/{userid}/image/{img}", method = RequestMethod.GET,produces = MediaType.IMAGE_JPEG_VALUE)
	public @ResponseBody byte[] getImage(@PathVariable("img") String img,@PathVariable("userid") String userid) throws IOException {
		
		 BufferedImage bImage = ImageIO.read(new File("img/"+userid+"/"+img));
	      ByteArrayOutputStream bos = new ByteArrayOutputStream();
	      ImageIO.write(bImage, "jpg", bos );
	   
	    return bos.toByteArray();
	}
	

	
}