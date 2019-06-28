
package com.dashboard.response.readuser;

import org.springframework.stereotype.Component;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Component
public class ReadUser {

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
    private ReadUserPayload payload;

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

    public ReadUserPayload getPayload() {
        return payload;
    }

    public void setPayload(ReadUserPayload payload) {
        this.payload = payload;
    }

}
