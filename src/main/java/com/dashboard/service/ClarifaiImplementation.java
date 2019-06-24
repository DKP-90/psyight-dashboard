package com.dashboard.service;

import java.io.IOException;
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
import com.dashboard.model.ProductImage;
import com.dashboard.model.Products;
import com.dashboard.repository.HibernateUtil;
import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import ch.qos.logback.classic.pattern.ClassOfCallerConverter;
import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.ClarifaiClient;
import clarifai2.api.ClarifaiResponse;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.model.ConceptModel;
import clarifai2.dto.model.ModelVersion;
import clarifai2.dto.model.output_info.ConceptOutputInfo;
import clarifai2.dto.prediction.Concept;





@Service
public class ClarifaiImplementation implements ClarifaiService {

	
	private static Logger logger = LogManager.getLogger(ClarifaiImplementation.class);
	final ClarifaiClient client = new ClarifaiBuilder("00ae75c236404f52bf701d3540b7efe7").buildSync();
	 OkHttpClient okclient = new OkHttpClient();
	 
	@Override
	public String train(int gid, String userid) {	
		
		
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		String json = null;
		List<Groups> list;
		
		try {
			Query<?> query = session.createQuery("FROM Groups G WHERE G.userid=" + userid +" AND G.gid=" + String.valueOf(gid));
			list = (List<Groups>) query.list();	
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
	
		List<Products> listp;	
		List<ProductImage> listm;
		
		listp= list.get(0).getProducts();
		listp.get(0).getPid();
		
		try
		{
			
			
			for ( Products prd: listp) {		        
		         listm=prd.getImages();
		         /// ADD CONCEPTS WITH IMAGE
		         for ( ProductImage prdi: listm) {
						client.addInputs()
					    .plus(
					        ClarifaiInput.forImage("http://localhost:8081/userid/"+userid+"/image/"+prdi.getFilename().toString())
					    //		 ClarifaiInput.forImage("https://media.zigcdn.com/media/model/2016/Feb/"+prdi.getFilename().toString())
					            .withConcepts(Concept.forID(String.valueOf(prd.getPid())))
					    )
					    .executeSync();						
			      }
		         /// ADD MODEL WITH CONCEPT
		        ClarifaiResponse<ConceptModel> resp= client.createModel(String.valueOf(gid))
		         .withOutputInfo(ConceptOutputInfo.forConcepts(
		             Concept.forID(String.valueOf(prd.getPid()))
		         ))
		         .executeSync();
		  
		      }
		    String response = new Gson().toJson(client.trainModel("{"+ String.valueOf(gid)+"}").executeSync());
		    {

			      Request request = new Request.Builder()
			        .url("http://localhost:8081/group/add/?groupname=&gid=" + String.valueOf(gid) + "&products=definition=&product_name=&response_create=&response_train="+response)
			        .put(null)
			        .addHeader("Accept", "*/*")
			        .addHeader("Cache-Control", "no-cache")
			        .addHeader("Host", "localhost:8081")
			        .addHeader("accept-encoding", "gzip, deflate")
			        .addHeader("content-length", "")
			        .addHeader("Connection", "keep-alive")
			        .addHeader("cache-control", "no-cache")
			        .build();

			     okclient.newCall(request).execute();
			      }
		}
		catch(Exception e)
		{}
		return ("{Response:200,transaction:true,error-log:''}");
	}

	@Override
	public String detect(String userid,String gid) {
		
		Groups gp=new Groups();

		  try {

		  		Request request = new Request.Builder()
		  		        .url("http://localhost:8081/user/ReadGroupFromId/userid/"+userid+"/groupid/"+gid+"/")
		  		        .put(null)
		  		        .addHeader("Accept", "*/*")
		  		        .addHeader("Cache-Control", "no-cache")
		  		        .addHeader("Host", "localhost:8081")
		  		        .addHeader("accept-encoding", "gzip, deflate")
		  		        .addHeader("content-length", "")
		  		        .addHeader("Connection", "keep-alive")
		  		        .addHeader("cache-control", "no-cache")
		  		        .build();
				Response response = okclient.newCall(request).execute();
				gp=new Gson().fromJson(response.toString(),Groups.class);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		      
		ModelVersion modelVersion = client.getModelVersionByID("MODEL_ID", "MODEL_VERSION_ID")
        .executeSync()
        .get();

		client.predict(String.valueOf(gp.getGid()))
        .withVersion(modelVersion)
        .withInputs(ClarifaiInput.forImage("https://samples.clarifai.com/metro-north.jpg"))
        .executeSync();

		return null;
	}

}
