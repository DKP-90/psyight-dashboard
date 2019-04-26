package com.dashboard.service;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

@Service
public class ImportImplementation implements ImportService {

	private static Logger logger = LogManager.getLogger(ImportImplementation.class);
	
	@Override
	public String ImportXlsxs(String userid)  {
				
		try
		{
	    File excelFile = new File("1.xlsx");
	    FileInputStream fis = new FileInputStream(excelFile);
	    XSSFWorkbook workbook = new XSSFWorkbook(fis);
	    XSSFSheet sheet = workbook.getSheetAt(0);
	    Iterator<Row> rowIt = sheet.iterator();

	    while(rowIt.hasNext()) {
	      Row row = rowIt.next();
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
		
	}
		
		return ("{Response:200,transaction:true,error-log:''}");
	}

	@Override
	public String UploadXlsx(String userid) {
		
		// TODO Auto-generated method stub
		return null;
	}

}
