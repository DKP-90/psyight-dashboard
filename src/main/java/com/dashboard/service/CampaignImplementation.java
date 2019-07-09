package com.dashboard.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import com.dashboard.model.Campaign;
import com.dashboard.model.Groups;
import com.dashboard.repository.HibernateUtil;
import com.dashboard.response.defaultresponse.DefaultResponse;
import com.google.gson.Gson;

public class CampaignImplementation implements CampaignService {

	private static Logger logger = LogManager.getLogger(GroupsImplementation.class);
	public SimpleDateFormat dateformat=new SimpleDateFormat();
	
	@Autowired
	DefaultResponse defaultresponse;
	
	@Override
	public String read(String userid, int start, int limit) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String readFromId(String userid, int cid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String addCampaign(int cid, String campaign_name, String campaign_desc, String campaign_start_date,String campaign_end_date, String json) {

		String response="";
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		try {
			Campaign cobj=new Campaign();

			cobj.setCampaign_name(campaign_name);
			cobj.setCampaign_desc(campaign_desc);
			cobj.setCampaign_start_date(dateformat.parse(campaign_start_date));
			cobj.setCampaign_end_date(dateformat.parse(campaign_end_date));
			cobj.setJson(json);
			session.save(cobj);
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
	public String deleteCampaign(int cid) {
		String response="";
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		try {

			Object c = session.get(Campaign.class, cid);
			Campaign cobj = (Campaign) c;
			cobj.setActive(0);

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

}
