package com.dashboard.response.campaigndetails;

import java.util.List;

import org.springframework.stereotype.Component;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Component
public class CampaignDetailsPayload {

@SerializedName("cid")
@Expose
private Integer cid;
@SerializedName("userid")
@Expose
private Integer userid;
@SerializedName("campaign_name")
@Expose
private String campaignName;
@SerializedName("campaign_desc")
@Expose
private String campaignDesc;
@SerializedName("campaign_start_date")
@Expose
private String campaignStartDate;
@SerializedName("campaign_end_date")
@Expose
private String campaignEndDate;
@SerializedName("json")
@Expose
private String json;
@SerializedName("active")
@Expose
private Integer active;
@SerializedName("CampaignProducts")
@Expose
private List<CampaignProduct> campaignProducts = null;

public Integer getCid() {
return cid;
}

public void setCid(Integer cid) {
this.cid = cid;
}

public Integer getUserid() {
return userid;
}

public void setUserid(Integer userid) {
this.userid = userid;
}

public String getCampaignName() {
return campaignName;
}

public void setCampaignName(String campaignName) {
this.campaignName = campaignName;
}

public String getCampaignDesc() {
return campaignDesc;
}

public void setCampaignDesc(String campaignDesc) {
this.campaignDesc = campaignDesc;
}

public String getCampaignStartDate() {
return campaignStartDate;
}

public void setCampaignStartDate(String campaignStartDate) {
this.campaignStartDate = campaignStartDate;
}

public String getCampaignEndDate() {
return campaignEndDate;
}

public void setCampaignEndDate(String campaignEndDate) {
this.campaignEndDate = campaignEndDate;
}

public String getJson() {
return json;
}

public void setJson(String json) {
this.json = json;
}

public Integer getActive() {
return active;
}

public void setActive(Integer active) {
this.active = active;
}

public List<CampaignProduct> getCampaignProducts() {
return campaignProducts;
}

public void setCampaignProducts(List<CampaignProduct> campaignProducts) {
this.campaignProducts = campaignProducts;
}

}
