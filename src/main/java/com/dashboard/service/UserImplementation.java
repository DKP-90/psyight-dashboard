package com.dashboard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dashboard.model.User;
import com.dashboard.repository.HibernateUtil;
import com.dashboard.repository.UserRepo;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;

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

	@Override
	public String add(String name, String password, String email, String organisation) {

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
			tx.commit();
			session.close();

		} catch (Exception ex) {
			logger.error(ex.toString());
			return ("{Response:200,transaction:false,error-log:" + ex.toString() + "}");
		} finally {
			try {
				if (session != null)
					session.close();
				tx.commit();
			} catch (Exception ex) {
			}
		}
		return ("{Response:200,transaction:true,error-log:''}");
	}

	@Override
	public String update(String userid, String name, String password, String email, String organisation) {

		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		try {
			Object o = session.get(User.class, userid);
			User uobj = (User) o;
			uobj.setOrganisation(organisation);
			uobj.setName(name);
			uobj.setPassword(MD5(password));

			tx.commit();
			session.close();
		} catch (Exception ex) {
			logger.error(ex.toString());
			return ("{Response:200,transaction:false,error-log:" + ex.toString() + "}");
		} finally {
			try {
				if (session != null)
					session.close();
				tx.commit();
			} catch (Exception ex) {
			}
		}
		return ("{Response:200,transaction:true,error-log:''}");
	}

	@Override
	public String delete(String userid) {

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
			return ("{Response:200,transaction:false,error-log:" + ex.toString() + "}");
		} finally {
			try {
				if (session != null)
					session.close();
				tx.commit();
			} catch (Exception ex) {
			}
		}
		return ("{Response:200,transaction:true,error-log:''}");
	}

	@Override
	public String read(int start, int limit) {

		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		String json = null;
		List<?> list;
		try {
			Query<?> query = session.createQuery("FROM User");
			query.setFirstResult(start);
			query.setMaxResults(limit);
			list = query.list();

			json = new Gson().toJson(list);
			tx.commit();
			session.close();
		} catch (Exception ex) {
			logger.error(ex.toString());
			return ("{Response:200,transaction:false,error-log:" + ex.toString() + "}");
		} finally {
			try {
				if (session != null)
					session.close();
				tx.commit();
			} catch (Exception ex) {
			}
		}
		return json;

	}

	@Override
	public String readFromId(String userid) {

		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		String json = null;
		List<?> list;
		try {
			Query<?> query = session.createQuery("FROM User U WHERE U.userid=" + userid);
			list = query.list();

			json = new Gson().toJson(list);

			tx.commit();
			session.close();
		} catch (Exception ex) {
			logger.error(ex.toString());
			return ("{Response:200,transaction:false,error-log:" + ex.toString() + "}");
		} finally {
			try {
				if (session != null)
					session.close();
				tx.commit();
			} catch (Exception ex) {
			}
		}
		return json;

	}

	@Override
	public String login(String email, String password) {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		String json = null;
		List<?> list;
		try {			

			Criteria cr = session.createCriteria(User.class).setProjection(Projections.projectionList()
				      .add(Projections.property("userid"), "userid")
				      .add(Projections.property("name"), "name"))
				    .setResultTransformer(Transformers.aliasToBean(User.class));
			cr.add(Restrictions.eq("email", email));
			cr.add(Restrictions.eq("password", MD5(password)));
			 list = cr.list();
			
			if (list.size() > 0)
			{
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("data",list);
				map.put("login",true);
				Gson gson = new Gson();
				json = gson.toJson(map);
				
			}
			else
			{
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("data",list);
				map.put("login",false);
				Gson gson = new Gson();
				json = gson.toJson(map);
			}
			tx.commit();
			session.close();
		} catch (Exception ex) {
			logger.error(ex.toString());
			return ("{Response:200,transaction:false,error-log:" + ex.toString() + "}");
		} finally {
			try {
				if (session != null)
					session.close();
				tx.commit();
			} catch (Exception ex) {
			}
		}
		return json;
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

}
