package com.dashboard.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dashboard.model.User;
import com.dashboard.repository.HibernateUtil;
import com.dashboard.repository.UserRepo;
import com.google.gson.Gson;

import java.util.List;

import org.apache.logging.log4j.*;

import org.apache.logging.log4j.LogManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;


@Service
public class UserImplementation implements UserService {

	private static Logger logger= LogManager.getLogger(UserImplementation.class);
	 
	@Autowired
	UserRepo urobj;
	@Override
	public void AddUser(String name, String password, String email, String organisation) {
		
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();	
		 try {
		User uobj=new User();		
		uobj.setEmail(email);
		uobj.setOrganisation(organisation);
		uobj.setName(name);
		uobj.setPassword(password);
		session.save(uobj);		
		tx.commit();
		session.close();
		
		 } catch(Exception ex) 
		 { 
			 logger.error(ex.toString());
          } finally {        	  
         try {
        if(session != null)
        	session.close();
         	tx.commit();         	
         } catch(Exception ex) {}
     }
	 
	}

	@Override
	public void UpdateUser(String userid, String name, String password, String email, String organisation) {

		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();	
		 try {
		Object o=session.get(User.class, 1);
		User uobj=(User)o;
		uobj.setName("usr");
		
		tx.commit();
		session.close();
		 } catch(Exception ex) 
		 { 
			 logger.error(ex.toString());
          } finally {        	  
         try {
        if(session != null)
        	session.close();
         	tx.commit();         	
         } catch(Exception ex) {}
     }
	}

	@Override
	public void DeleteUser(int userid) {

		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();		
		Transaction tx = session.beginTransaction();	
		 try {
		User uobj=new User();
		
		uobj.setUserid(userid);
		session.delete(uobj);
		
		tx.commit();
		session.close();
		 } catch(Exception ex) 
		 { 
			 logger.error(ex.toString());
          } finally {        	  
         try {
        if(session != null)
        	session.close();
         	tx.commit();         	
         } catch(Exception ex) {}
     }
		
	}

	@Override
	public String ReadUser(String userid) {

		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		User uobj=new User();
		String json=null ;
		 List list ;
		 try {
			 Query query = session.createQuery("from User");
		     list = query.list();			 
			
		     json = new Gson().toJson(list);
			// uobj = (User) session.load(User.class, 1);
			System.out.println(json);
		tx.commit();
		session.close();
		 } catch(Exception ex) 
		 { 
			 logger.error(ex.toString());
          } finally {        	  
         try {
        if(session != null)
        	session.close();
         	tx.commit();         	
         } catch(Exception ex) {}
     }
       return json;

		
	}

}
