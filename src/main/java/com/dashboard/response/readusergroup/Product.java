
package com.dashboard.response.readusergroup;

import java.util.List;

import org.springframework.stereotype.Component;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Component
public class Product {

    @SerializedName("pid")
    @Expose
    private Integer pid;
    @SerializedName("gid")
    @Expose
    private Integer gid;
    @SerializedName("userid")
    @Expose
    private Integer userid;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("active")
    @Expose
    private Integer active;
    @SerializedName("definition")
    @Expose
    private String definition;
    @SerializedName("images")
    @Expose
    private List<Image> images = null;

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getGid() {
        return gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

}
