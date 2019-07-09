
package com.dashboard.response.campaigndetails;

import org.springframework.stereotype.Component;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Component
public class CampaignDetails {

@SerializedName("status")
@Expose
private Boolean status;
@SerializedName("errormsg")
@Expose
private String errormsg;
@SerializedName("pagination")
@Expose
private Boolean pagination;
@SerializedName("payload")
@Expose
private CampaignDetailsPayload CampaignDetailsPayload;

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

public CampaignDetailsPayload getPayload() {
return CampaignDetailsPayload;
}

public void setPayload(CampaignDetailsPayload CampaignDetailsPayload) {
this.CampaignDetailsPayload = CampaignDetailsPayload;
}

}