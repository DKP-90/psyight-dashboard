package com.dashboard.service;

import java.util.List;

public interface CampaignService {
	String read(String userid,int start,int limit);
	String readFromId(String userid, int cid);	
	String addCampaign(int cid,String campaign_name,String campaign_desc,String campaign_start_date,String campaign_end_date,String json);
	String deleteCampaign(int cid);
}
