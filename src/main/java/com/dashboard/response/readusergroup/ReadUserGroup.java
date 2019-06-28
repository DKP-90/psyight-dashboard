
package com.dashboard.response.readusergroup;

import org.springframework.stereotype.Component;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Component
public class ReadUserGroup {

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
    private Payload payload;

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

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

}
