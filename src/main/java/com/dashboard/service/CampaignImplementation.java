package com.dashboard.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import com.dashboard.model.Campaign;
import com.dashboard.model.CampaignProducts;
import com.dashboard.model.ProductImage;
import com.dashboard.model.Products;
import com.dashboard.repository.HibernateUtil;
import com.dashboard.response.campaigndetails.CampaignDetailProduct;
import com.dashboard.response.campaigndetails.CampaignDetails;
import com.dashboard.response.campaigndetails.CampaignDetailsPayload;
import com.dashboard.response.campaignlist.CampaignList;
import com.dashboard.response.campaignlist.CampaignListList;
import com.dashboard.response.campaignlist.CampaignListPayload;
import com.dashboard.response.defaultresponse.DefaultResponse;
import com.google.gson.Gson;

@Service
public class CampaignImplementation implements CampaignService {

	private static Logger logger = LogManager.getLogger(GroupsImplementation.class);
	public SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");

	@Autowired
	DefaultResponse defaultresponse;
	@Autowired
	CampaignDetails CampaignDetails;
	@Autowired
	CampaignDetailsPayload CampaignDetailsPayload;
	@Autowired
	CampaignDetailProduct CampaignDetailProduct;
	@Autowired
	CampaignList CampaignList;
	@Autowired
	CampaignListPayload CampaignListPayload;
	@Autowired
	CampaignListList CampaignListList;

	CampaignProducts CampaignProducts = new CampaignProducts();

	@Override
	public String read(String userid, int start, int limit) {
		String response = "";
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		List<?> list;
		try {
			Query<?> query = session.createQuery("SELECT C FROM Campaign C  WHERE C.userid=" + userid);
			query.setFirstResult(start);
			query.setMaxResults(limit);

			List<CampaignListList> Clist = new ArrayList<CampaignListList>();
			List<Campaign> Campaign = (List<Campaign>) query.list();
			CampaignList.setErrormsg("");
			if (Campaign.size() < limit)
				CampaignList.setPagination(false);
			else
				CampaignList.setPagination(true);
			CampaignList.setStatus(true);
					

			for (Campaign item : Campaign) {
				AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(CampaignListList.class);
				CampaignListList cll = context.getBean(CampaignListList.class);
				cll.setCid(item.getCid());
				cll.setCampaignName(item.getCampaign_name());
				cll.setCampaignDesc(item.getCampaign_desc());
				cll.setCampaignStartDate(item.getCampaign_start_date().toString());
				cll.setCampaignEndDate(item.getCampaign_end_date().toString());
				Clist.add(cll);				
				
			}
			CampaignListPayload.setCampaignListList(Clist);
			CampaignList.setCampaignListPayload(CampaignListPayload);
			response = new Gson().toJson(CampaignList);

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
		String response = "";
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();

		List<?> list;
		try {
			// Query<?> query = session.createQuery("SELECT C FROM Campaign C JOIN FETCH
			// C.products P WHERE C.userid=" + userid +" AND C.cid=" + cid);
			Query<?> query = session
					.createQuery("SELECT C FROM Campaign C WHERE C.userid=" + userid + " AND C.cid=" + cid);
			List<Campaign> Campaign = (List<Campaign>) query.list();
			Set<CampaignProducts> cps = null;
			Set<CampaignDetailProduct> cdps = new HashSet<CampaignDetailProduct>();
			Set <ProductImage> pi=new HashSet<ProductImage>();
			List<ProductImage> imagelist= null;
			Products pobj;
			Object p;
			CampaignDetails.setErrormsg("");
			CampaignDetails.setPagination(false);
			CampaignDetails.setStatus(true);
			for (Campaign item : Campaign) {
				CampaignDetailsPayload.setCid(item.getCid());
				CampaignDetailsPayload.setCampaignName(item.getCampaign_name());
				CampaignDetailsPayload.setCampaignDesc(item.getCampaign_desc());
				CampaignDetailsPayload.setCampaignStartDate(item.getCampaign_start_date().toString());
				CampaignDetailsPayload.setCampaignEndDate(item.getCampaign_end_date().toString());
				CampaignDetailsPayload.setJson(item.getJson());
				cps = item.getCampaignProducts();

				for (CampaignProducts cpsitem : cps) {
					//CampaignDetailProduct cdp=new CampaignDetailProduct();
					AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(CampaignDetailProduct.class);
					CampaignDetailProduct cdp = context.getBean(CampaignDetailProduct.class);
					p = session.get(Products.class, cpsitem.getPid());
					pobj = (Products) p;
					cdp.setPid(cpsitem.getPid());
					cdp.setProductName(pobj.getProduct_name());
					pi=pobj.getImages();
					imagelist = new ArrayList<>(pi);
					cdp.setProduct_image(imagelist.get(0));
					cdps.add(cdp);
				}

			}
			CampaignDetailsPayload.setCampaignProducts(cdps);
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
	public String addCampaign(String campaign_name, String campaign_desc, String campaign_start_date,
			String campaign_end_date, String json, int userid) {

		String response = "";
		Integer cid = 0;
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		try {
			Campaign cobj = new Campaign();

			cobj.setCampaign_name(campaign_name);
			cobj.setCampaign_desc(campaign_desc);
			cobj.setCampaign_start_date(dateformat.parse(campaign_start_date));
			cobj.setCampaign_end_date(dateformat.parse(campaign_end_date));
			cobj.setUserid(userid);
			cobj.setActive(1);
			cobj.setJson(json);
			session.save(cobj);
			cid = cobj.getCid();
			defaultresponse.setId(cid.toString());
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
	public String updateCampaign(int cid, String campaign_name, String campaign_desc, String campaign_start_date,
			String campaign_end_date, String json) {

		String response = "";
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
		String response = "";
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
		String response = "";
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		try {
			CampaignProducts cobj = new CampaignProducts();
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
		String response = "";
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		try {
			Query<?> query = session
					.createQuery("DELETE FROM CampaignProducts C WHERE C.pid=" + pid + " AND C.cid=" + cid);
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
