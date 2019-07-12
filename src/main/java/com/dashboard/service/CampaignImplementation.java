package com.dashboard.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dashboard.model.Campaign;
import com.dashboard.model.CampaignProducts;
import com.dashboard.model.Groups;
import com.dashboard.repository.HibernateUtil;
import com.dashboard.response.campaigndetails.CampaignDetails;
import com.dashboard.response.campaigndetails.CampaignDetailsPayload;
import com.dashboard.response.campaigndetails.CampaignProduct;
import com.dashboard.response.campaignlist.CampaignList;
import com.dashboard.response.campaignlist.CampaignListList;
import com.dashboard.response.campaignlist.CampaignListPayload;
import com.dashboard.response.defaultresponse.DefaultResponse;
import com.google.gson.Gson;

@Service
public class CampaignImplementation implements CampaignService {

	private static Logger logger = LogManager.getLogger(GroupsImplementation.class);
	public SimpleDateFormat dateformat=new SimpleDateFormat();
	
	@Autowired
	DefaultResponse defaultresponse;
	@Autowired
	CampaignDetails CampaignDetails; 
	@Autowired
	CampaignDetailsPayload CampaignDetailsPayload;
	@Autowired
	CampaignProduct CampaignProduct;
	@Autowired
	CampaignList CampaignList;
	@Autowired
	CampaignListPayload CampaignListPayload;
	@Autowired
	CampaignListList CampaignListList;
	
	@Override
	public String read(String userid, int start, int limit) {
		String response="";
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		List<?> list;
		try {
			Query<?> query = session.createQuery("SELECT C FROM Campaign C  WHERE C.userid=" + userid );
			query.setFirstResult(start);
			query.setMaxResults(limit);
			List<Campaign> Campaign=  (List<Campaign>) query.list();
			List<CampaignListList> Clist= null;
			CampaignList.setErrormsg("");
			if(Campaign.size()<limit)
				CampaignList.setPagination(false);
			else
				CampaignList.setPagination(true);
			CampaignList.setStatus(true);
			
			for(Campaign item:Campaign )
			{							
				CampaignListList.setCid(item.getCid());
				CampaignListList.setCampaignName(item.getCampaign_name());
				CampaignListList.setCampaignDesc(item.getCampaign_desc());
				CampaignListList.setCampaignStartDate(item.getCampaign_start_date().toString());
				CampaignListList.setCampaignEndDate(item.getCampaign_end_date().toString());
				Clist.add(CampaignListList);
				
			}
			CampaignListPayload.setCampaignListList(Clist);
			CampaignList.setCampaignListPayload(CampaignListPayload);
			response = new Gson().toJson(CampaignDetails);

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
		return response;
	}


	@Override
	public String readFromId(String userid, int cid) {
		String response="";
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		List<?> list;
		try {
			Query<?> query = session.createQuery("SELECT C FROM Campaign C JOIN FETCH C.products P  WHERE C.userid=" + userid +" AND C.cid=" + cid);
			
			List<Campaign> Campaign=  (List<Campaign>) query.list();
			
			CampaignDetails.setErrormsg("");
			CampaignDetails.setPagination(false);
			CampaignDetails.setStatus(true);
			for(Campaign item:Campaign )
			{
				CampaignDetailsPayload.setCid(item.getCid());
				CampaignDetailsPayload.setCampaignName(item.getCampaign_name());
				CampaignDetailsPayload.setCampaignDesc(item.getCampaign_desc());
				CampaignDetailsPayload.setCampaignStartDate(item.getCampaign_start_date().toString());
				CampaignDetailsPayload.setCampaignEndDate(item.getCampaign_end_date().toString());
				CampaignDetailsPayload.setJson(item.getJson());
				CampaignDetailsPayload.setCampaignProducts(item.getCampaignProducts());
				
				
			}
			CampaignDetails.setPayload(CampaignDetailsPayload);
			response = new Gson().toJson(CampaignDetails);

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
		return response;
	}

	@Override
	public String addCampaign( String campaign_name, String campaign_desc, String campaign_start_date,String campaign_end_date, String json) {

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
	public String updateCampaign(int cid, String campaign_name, String campaign_desc, String campaign_start_date,String campaign_end_date, String json) {

		String response="";
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		try {
			
			Object c = session.get(Campaign.class, cid);			
			Campaign cobj = (Campaign) c;			

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


	@Override
	public String addProductsToCampaign(int cid, int pid) {
		String response="";
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		try {
			CampaignProducts cobj=new CampaignProducts();
			cobj.setPid(pid);
			cobj.setCid(cid);
			cobj.setActive(1);			
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
	public String deleteProductsFromCampaign(int cid, int pid) {
		String response="";
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		try {
			Query<?> query = session.createQuery("DELETE FROM CampaignProducts  WHERE C.pid=" + pid +" AND C.cid=" + cid);
			query.executeUpdate();
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
