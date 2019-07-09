package com.dashboard.response.campaigndetails;

import org.springframework.stereotype.Component;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Component
public class CampaignProduct {

@SerializedName("pid")
@Expose
private Integer pid;
@SerializedName("product_name")
@Expose
private String productName;

public Integer getPid() {
return pid;
}

public void setPid(Integer pid) {
this.pid = pid;
}

public String getProductName() {
return productName;
}

public void setProductName(String productName) {
this.productName = productName;
}

}

