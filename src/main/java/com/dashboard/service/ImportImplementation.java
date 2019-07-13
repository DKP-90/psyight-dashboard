package com.dashboard.service;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;





@Service
public class ImportImplementation implements ImportService {

	private static Logger logger = LogManager.getLogger(ImportImplementation.class);
	 OkHttpClient client = new OkHttpClient();
	 
	@Override
	public String ImportXlsxs(String userid,String gid,String groupname)  {
			
		try
		{
	    File excelFile = new File("/var/www/html/psyight_images/"+userid + ".xlsx");
	    FileInputStream fis = new FileInputStream(excelFile);
	    XSSFWorkbook workbook = new XSSFWorkbook(fis);
	    XSSFSheet sheet = workbook.getSheetAt(0);
	    Iterator<Row> rowIt = sheet.iterator();	   
	    String products=",'userid':" + userid + ",'active':1",definition="",pid,product_name="";	
	    List<String> cmlist=new ArrayList<String>();
	    while(rowIt.hasNext()) {
	      Row row = rowIt.next();
	      Iterator<Cell> cellIterator = row.cellIterator();

	      while (cellIterator.hasNext()) {
	    
	    Cell cell = cellIterator.next();
	    if(row.getRowNum()==0)
	    {	        
	    	cmlist.add(cell.toString());  
	    }
	    else
	    {	    	
	    	 if(cell.getColumnIndex()==0)
	        	 pid=cell.toString();
	        else if(cell.getColumnIndex()==1)
	        	product_name=cell.toString();
	        else if(cellIterator.hasNext())
	        	definition= definition + "'" + cmlist.get(cell.getColumnIndex()).toString() + "':'" + cell.toString() + "',";
	        else
	        	definition= definition + "'" + cmlist.get(cell.getColumnIndex()).toString() + "':'" + cell.toString() + "'";
	    }

	      }
	      if(row.getRowNum()!=0)
	      {

	      Request request = new Request.Builder()
	        .url("http://localhost:8081/group/add/?groupname=" + groupname + "&gid=" + gid + "&products=%7Bdefinition=%22" + definition + "%22,product_name='"+ product_name +"','gid':" + gid + products + "%7D")
	        .put(null)
	        .addHeader("Accept", "*/*")
	        .addHeader("Cache-Control", "no-cache")
	        .addHeader("Host", "localhost:8081")
	        .addHeader("accept-encoding", "gzip, deflate")
	        .addHeader("content-length", "")
	        .addHeader("Connection", "keep-alive")
	        .addHeader("cache-control", "no-cache")
	        .build();

	      Response response = client.newCall(request).execute();
	      }
	      definition="";
	      
	      System.out.println();
	    }

	    workbook.close();
	fis.close();
		
	}catch (Exception e) {
		System.out.println(e.toString());
	}
		
		return ("{Response:200,transaction:true,error-log:''}");
	}

	@Override
	public String UploadXlsx(String userid) {
		
		// TODO Auto-generated method stub
		return null;
	}

}
