
package com.dashboard.response.readusergroup;

import org.springframework.stereotype.Component;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
@Component
public class Image {

    @SerializedName("iim")
    @Expose
    private Integer iim;
    @SerializedName("pid")
    @Expose
    private Integer pid;
    @SerializedName("filename")
    @Expose
    private String filename;
    @SerializedName("active")
    @Expose
    private Integer active;

    public Integer getIim() {
        return iim;
    }

    public void setIim(Integer iim) {
        this.iim = iim;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

}
