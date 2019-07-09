package com.dashboard.response.campaignlist;

import org.springframework.stereotype.Component;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Component
public class CampaignList {

@SerializedName("status")
@Expose
private Boolean status;
@SerializedName("errormsg")
@Expose
private String errormsg;
@SerializedName("pagination")
@Expose
private Boolean pagination;
@SerializedName("CampaignListPayload")
@Expose
private CampaignListPayload campaignListPayload;

public Boolean getStatus() {
return status;
}

public void setStatus(Boolean status) {
this.status = status;
}

public String getErrormsg() {
return errormsg;
}

public void setErrormsg(String errormsg) {
this.errormsg = errormsg;
}

public Boolean getPagination() {
return pagination;
}

public void setPagination(Boolean pagination) {
this.pagination = pagination;
}

public CampaignListPayload getCampaignListPayload() {
return campaignListPayload;
}

public void setCampaignListPayload(CampaignListPayload campaignListPayload) {
this.campaignListPayload = campaignListPayload;
}

}