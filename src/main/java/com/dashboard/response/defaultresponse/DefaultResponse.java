package com.dashboard.response.defaultresponse;

import org.springframework.stereotype.Component;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Component
public class DefaultResponse {

	    @SerializedName("status")
	    @Expose
	    private Boolean status;
	    @SerializedName("errormsg")
	    @Expose
	    private String errormsg;

	    
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


}
