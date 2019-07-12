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
import com.dashboard.model.Products;
import com.dashboard.repository.HibernateUtil;
import com.dashboard.response.productdetails.ProductDetails;
import com.dashboard.response.productdetails.Productdetailspayload;
import com.google.gson.Gson;

@Service
public class ProductImplementation implements ProductService {

	@Autowired
	ProductDetails ProductDetails;
	@Autowired
	Productdetailspayload Productdetailspayload;
	private static Logger logger = LogManager.getLogger(GroupsImplementation.class);

	
	@Override
	public String readFromId(String userid, int pid) {
		String response="";
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		
		try {
			//Query<?> query = session.createQuery("SELECT G FROM Groups G JOIN FETCH G.products P JOIN FETCH P.images WHERE G.userid=" + userid +" AND G.gid=" + gid);
			Query<?> query = session.createQuery("SELECT P FROM Products P  WHERE P.userid="+ userid +" AND P.pid=" + pid);

			List<Products> products=  (List<Products>) query.list();
			
			ProductDetails.setErrormsg("");
			ProductDetails.setPagination(false);
			ProductDetails.setStatus(true);
			for(Products item:products )
			{
				Productdetailspayload.setGid(item.getGid());
				Productdetailspayload.setPid(item.getPid());
				Productdetailspayload.setUserid(item.getUserid());
				Productdetailspayload.setProductName(item.getProduct_name());
				Productdetailspayload.setActive(item.getActive());
				Productdetailspayload.setDefinition(item.getDefinition());
			}
			ProductDetails.setProductdetailspayload(Productdetailspayload);
			response = new Gson().toJson(ProductDetails);

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


}
