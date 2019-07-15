package com.dashboard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dashboard.model.User;
import com.dashboard.repository.HibernateUtil;
import com.dashboard.repository.UserRepo;
import com.dashboard.response.defaultresponse.DefaultResponse;
import com.dashboard.response.readuser.ReadUser;
import com.dashboard.response.readuser.ReadUserPayload;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.logging.log4j.*;

import org.apache.logging.log4j.LogManager;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;

@Service
public class UserImplementation implements UserService {

	private static Logger logger = LogManager.getLogger(UserImplementation.class);

	@Autowired
	UserRepo urobj;
	@Autowired
	ReadUser readuser;
	@Autowired
	ReadUserPayload readuserpayload;
	@Autowired
	DefaultResponse defaultresponse;
	@Autowired
	CommonService comservice;
	
	@Override
	public String add(String name, String password, String email, String organisation) {

		String response="";Integer userid=0;
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		try {
			User uobj = new User();
			uobj.setEmail(email);
			uobj.setOrganisation(organisation);
			uobj.setName(name);
			uobj.setPassword(MD5(password));
			uobj.setActive(1);
			session.save(uobj);
			userid=uobj.getUserid();
			defaultresponse.setId(userid.toString());
			tx.commit();
			session.close();

		} catch (Exception ex) {
			logger.error(ex.toString());	
			defaultresponse.setErrormsg(ex.toString());
			defaultresponse.setStatus(false);
			response = new Gson().toJson(defaultresponse);
			return (response);
		} finally {
			try {
				if (session != null)
					session.close();
				tx.commit();
			} catch (Exception ex) {
			}
		}
		defaultresponse.setErrormsg("");
		defaultresponse.setStatus(true);
		response = new Gson().toJson(defaultresponse);
		return (response);
	}

	@Override
	public String update(String userid, String name, String email, String organisation) {

		String response="";
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		try {
			Object o = session.get(User.class, userid);
			User uobj = (User) o;
			uobj.setOrganisation(organisation);
			uobj.setName(name);			

			tx.commit();
			session.close();
		} catch (Exception ex) {
			logger.error(ex.toString());	
			defaultresponse.setErrormsg(ex.toString());
			defaultresponse.setStatus(false);
			response = new Gson().toJson(defaultresponse);
			return (response);
		} finally {
			try {
				if (session != null)
					session.close();
				tx.commit();
			} catch (Exception ex) {
			}
		}
		defaultresponse.setErrormsg("");
		defaultresponse.setStatus(true);
		response = new Gson().toJson(defaultresponse);
		return (response);
	}

	@Override
	public String delete(String userid) {

		String response="";
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		try {

			Object o = session.get(User.class, 1);
			User uobj = (User) o;
			uobj.setActive(0);

			tx.commit();
			session.close();
		} catch (Exception ex) {
			logger.error(ex.toString());	
			defaultresponse.setErrormsg(ex.toString());
			defaultresponse.setStatus(false);
			response = new Gson().toJson(defaultresponse);
			return (response);
		} finally {
			try {
				if (session != null)
					session.close();
				tx.commit();
			} catch (Exception ex) {
			}
		}
		defaultresponse.setErrormsg("");
		defaultresponse.setStatus(true);
		response = new Gson().toJson(defaultresponse);
		return (response);
	}

	@Override
	public String read(int start, int limit) {

		String response="";
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		List<?> list;
		try {
			Query<?> query = session.createQuery("FROM User");
			query.setFirstResult(start);
			query.setMaxResults(limit);
			list = query.list();

			response = new Gson().toJson(list);
			tx.commit();
			session.close();
		} catch (Exception ex) {
			logger.error(ex.toString());	
			defaultresponse.setErrormsg(ex.toString());
			defaultresponse.setStatus(false);
			response = new Gson().toJson(defaultresponse);
			return (response);
		} finally {
			try {
				if (session != null)
					session.close();
				tx.commit();
			} catch (Exception ex) {
			}
		}
		return response;

	}

	@Override
	public String readFromId(String userid) {

		
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		String response="";
		List<?> list;
		try {
			Query<?> query = session.createQuery("SELECT U FROM User U  WHERE U.userid=" + userid);			
			List<User> user =  (List<User>) query.getResultList();
			
			readuser.setErrormsg("");
			readuser.setPagination(false);
			readuser.setStatus(true);
			
			for(User item:user )
			{
				readuserpayload.setName(item.getName());
				readuserpayload.setEmail(item.getEmail());
				readuserpayload.setOrganisation(item.getOrganisation());
				readuserpayload.setUserid(item.getUserid());
				readuserpayload.setActive(item.getActive());
			}
			readuser.setPayload(readuserpayload);
			
//			User u= session.get(User.class, 1);
//			System.out.println(u);
			
//			CriteriaBuilder builder=session.getCriteriaBuilder();
//			CriteriaQuery<User> criteriaquery= builder.createQuery(User.class);
//			Root<User> root= criteriaquery.from(User.class);
//		//	root.fetch("groups");
//			root.fetch("products");
//			criteriaquery.where(builder.equal(root.get("userid"), userid));
//			Query<User> query=session.createQuery(criteriaquery);
//			List<User> user =  (List<User>) query.getResultList();
			
			response = new Gson().toJson(readuser);
			
			tx.commit();
			session.close();
			
		} catch (Exception ex) {
			logger.error(ex.toString());	
			defaultresponse.setErrormsg(ex.toString());
			defaultresponse.setStatus(false);
			response = new Gson().toJson(defaultresponse);
			return (response);
		} finally {
			try {
				if (session != null)
					session.close();
				tx.commit();
			} catch (Exception ex) {
			}
		}
		return response;

	}

	@Override
	public String login(String email, String password) {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		String response="";
		List<?> list;
		try {			

			Query<?> query = session.createQuery("SELECT U FROM User U  WHERE U.email='" + email +"' AND U.password='"+ MD5(password) +"'");			
			List<User> user =  (List<User>) query.getResultList();
			
			if (!user.isEmpty())
				{
				readuser.setErrormsg("");
				readuser.setPagination(false);
				readuser.setStatus(true);
				for(User item:user )
				{
					readuserpayload.setName(item.getName());
					readuserpayload.setEmail(item.getEmail());
					readuserpayload.setOrganisation(item.getOrganisation());
					readuserpayload.setUserid(item.getUserid());
					readuserpayload.setActive(item.getActive());
				}
				readuser.setPayload(readuserpayload);				
				}
			else
				{
				readuser.setErrormsg("Password-wrong");
				readuser.setPagination(false);
				readuser.setStatus(false);			
				readuser.setPayload(null);						
				GsonBuilder gsonBuilder = new GsonBuilder();  
				gsonBuilder.serializeNulls();  
				Gson gson = gsonBuilder.create();
				response = gson.toJson(readuser);

				}

//			Criteria cr = session.createCriteria(User.class).setProjection(Projections.projectionList()
//				      .add(Projections.property("userid"), "userid")
//				      .add(Projections.property("name"), "name"))
//				    .setResultTransformer(Transformers.aliasToBean(User.class));
//			cr.add(Restrictions.eq("email", email));
//			cr.add(Restrictions.eq("password", MD5(password)));
//			 list = cr.list();
			
//			if (!list.isEmpty())
//			{
//				HashMap<String, Object> map = new HashMap<String, Object>();
//				map.put("data",list);
//				map.put("login",true);
//				Gson gson = new Gson();
//				response = gson.toJson(map);
//				
//			}
//			else
//			{
//				HashMap<String, Object> map = new HashMap<String, Object>();
//				map.put("data",list);
//				map.put("login",false);
//				Gson gson = new Gson();
//				response = gson.toJson(map);
//			}
			response = new Gson().toJson(readuser);
			tx.commit();
			session.close();
		} catch (Exception ex) {
			
			logger.error(ex.toString());	
			defaultresponse.setErrormsg(ex.toString());
			defaultresponse.setStatus(false);
			response = new Gson().toJson(defaultresponse);
			return (response);
			
		} finally {
			try {
				if (session != null)
					session.close();
				tx.commit();
			} catch (Exception ex) {
			}
		}
		return response;
	}

	public String MD5(String md5) {
		try {
			java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
			byte[] array = md.digest(md5.getBytes());
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < array.length; ++i) {
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
			}
			return sb.toString();
		} catch (java.security.NoSuchAlgorithmException e) {
		}
		return null;
	}


	@Override
	public String forgotPassword(String email) {
		
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		String response="";
		List<?> list;
		try {
			Query<?> query = session.createQuery("SELECT U FROM User U  WHERE U.email=" + email);			
			List<User> user =  (List<User>) query.getResultList();
			
		
			
			for(User item:user )
			{
				Object o = session.get(User.class, item.getUserid());
				User uobj = (User) o;				
					uobj.setPassword("sfasf64675");
					defaultresponse.setErrormsg("");
					defaultresponse.setStatus(true);
					
					 Mail mail = new Mail();
				        mail.setFrom("no-reply@psyight.com");
				        mail.setTo(email);
				        mail.setSubject("Password Reset");
				        mail.setContent("Your password has been reset to sfasf64675");	        
				        comservice.sendSimpleMessage(mail);
				
			}
			
			tx.commit();
			session.close();
			
		} catch (Exception ex) {
			logger.error(ex.toString());	
			defaultresponse.setErrormsg(ex.toString());
			defaultresponse.setStatus(false);
			response = new Gson().toJson(defaultresponse);
			return (response);
		} finally {
			try {
				if (session != null)
					session.close();
				tx.commit();
			} catch (Exception ex) {
			}
		}
		
		return (response);   
	}

	@Override
	public String changePassword(String userid, String newPassword, String oldPassword) {
		String response="";
		defaultresponse.setErrormsg("");
		defaultresponse.setStatus(false);
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		try {
			Object o = session.get(User.class, userid);
			User uobj = (User) o;
			if(oldPassword==uobj.getPassword())
				{
				uobj.setPassword(newPassword);
				defaultresponse.setErrormsg("");
				defaultresponse.setStatus(true);
				}
			else
			{
				uobj.setPassword(newPassword);
				defaultresponse.setErrormsg("password does not match");
				defaultresponse.setStatus(false);
				}

			tx.commit();
			session.close();
		} catch (Exception ex) {
			logger.error(ex.toString());	
			defaultresponse.setErrormsg(ex.toString());
			defaultresponse.setStatus(false);
			response = new Gson().toJson(defaultresponse);
			return (response);
		} finally {
			try {
				if (session != null)
					session.close();
				tx.commit();
			} catch (Exception ex) {
			}
		}
		
		response = new Gson().toJson(defaultresponse);
		return (response);
	}

}
