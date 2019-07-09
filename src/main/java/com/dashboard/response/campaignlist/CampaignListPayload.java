package com.dashboard.response.campaignlist;

import java.util.List;

import org.springframework.stereotype.Component;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Component
public class CampaignListPayload {

@SerializedName("CampaignListList")
@Expose
private List<CampaignListList> campaignListList = null;

public List<CampaignListList> getCampaignListList() {
return campaignListList;
}

public void setCampaignListList(List<CampaignListList> campaignListList) {
this.campaignListList = campaignListList;
}

}