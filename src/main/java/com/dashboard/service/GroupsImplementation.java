package com.dashboard.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dashboard.model.Groups;
import com.dashboard.model.User;
import com.dashboard.repository.HibernateUtil;
import com.google.gson.Gson;

@Service
public class GroupsImplementation implements GroupsService {

	private static Logger logger = LogManager.getLogger(UserImplementation.class);

	
	@Override
	public String add(String groupname, String definition) {

		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		try {
			Groups gobj=new Groups();
			gobj.setGroup_name(groupname);
			gobj.setDefinition(definition);		
			session.save(gobj);
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
	public String update(int gid,String groupname, List products) {

		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		try {
			Object g = session.get(Groups.class, gid);
			Groups gobj = (Groups) g;
			//gobj.setGroup_name(groupname);
			gobj.setProducts(products);			

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
	public String delete(int gid) {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		try {

			Object g = session.get(Groups.class, 1);
			Groups gobj = (Groups) g;
			gobj.setActive(0);

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
	public String read(String userid, int start, int limit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String readFromId(String userid, int gid) {

		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		String json = null;
		List<?> list;
		try {
			Query<?> query = session.createQuery("FROM Groups G WHERE G.userid=" + userid +" AND G.gid=" + gid);
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
	




}
