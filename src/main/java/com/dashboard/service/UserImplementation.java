package com.dashboard.service;


import org.springframework.beans.factory.annotation.Autowired;
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
	public String addUser(String name, String password, String email, String organisation) {
		
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();	
		 try {
		User uobj=new User();		
		uobj.setEmail(email);
		uobj.setOrganisation(organisation);
		uobj.setName(name);
		uobj.setPassword(MD5(password));
		session.save(uobj);		
		tx.commit();
		session.close();
		
		 } catch(Exception ex) 
		 { 
			 logger.error(ex.toString());
			 return("{Response:200,transaction:false,error-log:"+ex.toString()+"}");
          } finally {        	  
         try {
        if(session != null)
        	session.close();
         	tx.commit();         	
         } catch(Exception ex) {}
     }
		 return("{Response:200,transaction:true,error-log:''}");
	}

	@Override
	public String updateUser(String userid, String name, String password, String email, String organisation) {

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
			 return("{Response:200,transaction:false,error-log:"+ex.toString()+"}");
          } finally {        	  
         try {
        if(session != null)
        	session.close();
         	tx.commit();         	
         } catch(Exception ex) {}
     }
		 return("{Response:200,transaction:true,error-log:''}");
	}

	@Override
	public String deleteUser(String userid) {

		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();		
		Transaction tx = session.beginTransaction();	
		 try {
		User uobj=new User();
		
		uobj.setUserid(Integer.parseInt(userid));
		session.delete(uobj);
		
		tx.commit();
		session.close();
		 } catch(Exception ex) 
		 { 
			 logger.error(ex.toString());
			 return("{Response:200,transaction:false,error-log:"+ex.toString()+"}");
          } finally {        	  
         try {
        if(session != null)
        	session.close();
         	tx.commit();         	
         } catch(Exception ex) {}
     }
		return("{Response:200,transaction:true,error-log:''}");
	}

	@Override
	public String readUser(String userid) {

		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		String json=null ;
		 List<?> list ;
		 try {
			 Query<?> query = session.createQuery("FROM User");
		     list = query.list();			 
			
		     json = new Gson().toJson(list);			
		tx.commit();
		session.close();
		 } catch(Exception ex) 
		 { 
			 logger.error(ex.toString());
			 return("{Response:200,transaction:false,error-log:"+ex.toString()+"}");
          } finally {        	  
         try {
        if(session != null)
        	session.close();
         	tx.commit();         	
         } catch(Exception ex) {}
     }
       return json;

		
	}

	@Override
	public String readUserFromId(String userid) {

		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		String json=null ;
		 List<?> list ;
		 try {
			 Query<?> query = session.createQuery("FROM User U WHERE U.userid="+userid);
		     list = query.list();			     
			
		     json = new Gson().toJson(list);
		
		tx.commit();
		session.close();
		 } catch(Exception ex) 
		 { 
			 logger.error(ex.toString());
			 return("{Response:200,transaction:false,error-log:"+ex.toString()+"}");
          } finally {        	  
         try {
        if(session != null)
        	session.close();
         	tx.commit();         	
         } catch(Exception ex) {}
     }
       return json;

		
	}
	
	public String MD5(String md5) {
		   try {
		        java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
		        byte[] array = md.digest(md5.getBytes());
		        StringBuffer sb = new StringBuffer();
		        for (int i = 0; i < array.length; ++i) {
		          sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
		       }
		        return sb.toString();
		    } catch (java.security.NoSuchAlgorithmException e) {
		    }
		    return null;
		}

}
