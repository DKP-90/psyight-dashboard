package com.dashboard.service;

import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
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
	public String ImportXlsxs(String userid, String gid, String groupname) {

		try {
			File excelFile = new File("xls//" + userid + ".xlsx");
			FileInputStream fis = new FileInputStream(excelFile);
			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			XSSFSheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rowIt = sheet.iterator();
			String products = ",'userid':" + userid + ",'active':1", definition = "", product_name = "";
			List<String> cmlist = new ArrayList<String>();

			while (rowIt.hasNext()) {

				Row row = rowIt.next();
				Iterator<Cell> cellIterator = row.cellIterator();

				while (cellIterator.hasNext()) {

					Cell cell = cellIterator.next();
					if (row.getRowNum() == 0) {
						cmlist.add(cell.toString());
					} else {

						if (cell.getColumnIndex() == 0)
							definition = definition + "'id':'" + URLEncoder.encode(cell.toString(), "UTF-8") + "',";
						else if (cell.getColumnIndex() == 1)
							product_name = cell.toString();
						else if (cellIterator.hasNext())
							definition = definition + "'"
									+ URLEncoder.encode(cmlist.get(cell.getColumnIndex()).toString(), "UTF-8") + "':'"
									+ URLEncoder.encode(cell.toString(), "UTF-8") + "',";
						else
							definition = definition + "'"
									+ URLEncoder.encode(cmlist.get(cell.getColumnIndex()).toString(), "UTF-8") + "':'"
									+ URLEncoder.encode(cell.toString(), "UTF-8") + "'";

					}

				}
				if (row.getRowNum() != 0) {

					Request request = new Request.Builder()
							.url("http://localhost:8081/group/add/?groupname=" + URLEncoder.encode(groupname, "UTF-8")
									+ "&gid=" + gid + "&products=%7Bdefinition=%22%7B" + definition
									+ "%7D%22,product_name='" + URLEncoder.encode(product_name, "UTF-8") + "','gid':"
									+ gid + products + "%7D")
							.put(null).addHeader("Accept", "*/*").addHeader("Cache-Control", "no-cache")
							.addHeader("Host", "localhost:8081").addHeader("accept-encoding", "gzip, deflate")
							.addHeader("content-length", "").addHeader("Connection", "keep-alive")
							.addHeader("cache-control", "no-cache").build();
					try {
						Response response = client.newCall(request).execute();
					} catch (Exception e) {

					}
				}
				definition = "";

			}

			workbook.close();
			fis.close();

		} catch (Exception e) {
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
