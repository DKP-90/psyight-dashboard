package com.dashboard.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import com.dashboard.model.Groups;
import com.dashboard.model.ProductImage;
import com.dashboard.repository.HibernateUtil;

@Service
public class ProductImageImplementation implements ProductImageService {
	
	private static Logger logger = LogManager.getLogger(ProductImageImplementation.class);
	@Override
	public String addimage(String filename, int pid) {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		try {
			ProductImage pobj=new ProductImage();

			pobj.setPid(pid);
			pobj.setFilename(filename);
			pobj.setActive(1);
			session.save(pobj);
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
	public String deleteimage(int iim) {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		try {

			Object p = session.get(ProductImage.class, 1);
			ProductImage pobj = (ProductImage) p;
			pobj.setActive(0);

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

}
