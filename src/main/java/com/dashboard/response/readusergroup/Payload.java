
package com.dashboard.response.readusergroup;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.dashboard.model.Products;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
@Component
public class Payload {

    @SerializedName("gid")
    @Expose
    private Integer gid;
    @SerializedName("userid")
    @Expose
    private Integer userid;
    @SerializedName("group_name")
    @Expose
    private String groupName;
    @SerializedName("active")
    @Expose
    private Integer active;
    @SerializedName("products")
    @Expose
    private Set<Products> products = null;

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

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Set<Products> getProducts() {
        return products;
    }

    public void setProducts(Set<Products> set) {
        this.products = set;
    }

}
