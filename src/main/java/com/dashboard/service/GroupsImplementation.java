package com.dashboard.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Service;

import com.dashboard.model.Groups;
import com.dashboard.repository.HibernateUtil;
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
			gobj.setGroup_name(groupname);
			gobj.setProducts(products);			
			session.persist(gobj);
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
	

	@Override
	public String ImportXlsx(String userid)  {
		
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
		
	}catch (Exception e) {
		// TODO: handle exception
	}
		
		return ("{Response:200,transaction:true,error-log:''}");
	}



}
