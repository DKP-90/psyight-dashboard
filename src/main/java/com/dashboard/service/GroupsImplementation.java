package com.dashboard.service;

import java.util.List;
import java.util.Set;

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
import com.dashboard.response.defaultresponse.DefaultResponse;
import com.dashboard.response.readusergroup.Payload;
import com.dashboard.response.readusergroup.Product;
import com.dashboard.response.readusergroup.ReadUserGroup;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@Service
public class GroupsImplementation implements GroupsService {

	private static Logger logger = LogManager.getLogger(GroupsImplementation.class);

@Autowired
DefaultResponse defaultresponse;
@Autowired
ReadUserGroup readusergroup;
@Autowired
Product product;
@Autowired
Payload payload;
	@Override
	public String add(String groupname, String definition,String userid) {
		String response="";Integer gid=0;
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		try {
			Groups gobj=new Groups();

			gobj.setGroup_name(groupname);
			gobj.setDefinition(definition);
			gobj.setUserid(Integer.parseInt(userid));
			session.save(gobj);
			gid=gobj.getGid();
			defaultresponse.setId(gid.toString());
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
	public String update(int gid,String groupname, Set products,String response_create,String response_train) {
		String response="";
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		try {
			Object g = session.get(Groups.class, gid);			
			Groups gobj = (Groups) g;
			if(groupname!=null)
			gobj.setGroup_name(groupname);
			
			gobj.setProducts(products);			
			session.persist(gobj);
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
	public String delete(int gid) {
		String response="";
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		try {

			Object g = session.get(Groups.class, gid);
			Groups gobj = (Groups) g;
			gobj.setActive(0);

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
	public String read(String userid, int start, int limit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String readFromId(String userid, int gid) {

		String response="";
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		
		try {
			//Query<?> query = session.createQuery("SELECT G FROM Groups G JOIN FETCH G.products P JOIN FETCH P.images WHERE G.userid=" + userid +" AND G.gid=" + gid);
			Query<?> query = session.createQuery("SELECT G FROM Groups G  WHERE G.userid=" + userid +" AND G.gid=" + gid);

			List<Groups> group=  (List<Groups>) query.list();
			
			readusergroup.setErrormsg("");
			readusergroup.setPagination(false);
			readusergroup.setStatus(true);
			for(Groups item:group )
			{
				payload.setGid(item.getGid());
				payload.setGroupName(item.getGroup_name());
				payload.setUserid(item.getUserid());
				payload.setActive(item.getActive());
				payload.setProducts(item.getProducts());
				
			}
			readusergroup.setPayload(payload);
			response = new Gson().toJson(readusergroup);

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
	public String ImportXlsx(String userid)  {
		String response="";
		try
		{
	    File excelFile = new File("1.xlsx");
	    FileInputStream fis = new FileInputStream(excelFile);

	    // we create an XSSF Workbook object for our XLSX Excel File
	    XSSFWorkbook workbook = new XSSFWorkbook(fis);
	    // we get first sheet
	    XSSFSheet sheet = workbook.getSheetAt(0);

	    // we iterate on rows
	    Iterator<Row> rowIt = sheet.iterator();

	    while(rowIt.hasNext()) {
	      Row row = rowIt.next();

	      // iterate on cells for the current row
	      Iterator<Cell> cellIterator = row.cellIterator();

	      while (cellIterator.hasNext()) {
	        Cell cell = cellIterator.next();
	        System.out.print(cell.toString() + ";");
	      }

	      System.out.println();
	    }

	    workbook.close();
	fis.close();
		
	}catch (Exception ex) {
		logger.error(ex.toString());	
		defaultresponse.setErrormsg(ex.toString());
		defaultresponse.setStatus(false);
		response = new Gson().toJson(defaultresponse);
		return (response);
	}
		
		defaultresponse.setErrormsg("");
		defaultresponse.setStatus(true);
		response = new Gson().toJson(defaultresponse);
		return (response);
	}



}
