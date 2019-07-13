package com.dashboard.model;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
@Table(name="products")
public class Products {

	@Id
	@GeneratedValue
	private int pid;
	private int gid;
	private int userid;
	private String product_name;
	private int active;
	private String definition;
	
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinColumn(name="pid", nullable = false, insertable = false)
	private Set<ProductImage> images;
	
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinColumn(name="pid", nullable = false, insertable = false)
	private Set<CampaignProducts> CampaignProducts;
	
//	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
//	@JoinColumn(name="cid", nullable = false, insertable = false)
//	private Set<Campaign> Campaign;
	
//	public Set<Campaign> getCampaign() {
//		return Campaign;
//	}
//	public void setCampaign(Set<Campaign> campaign) {
//		Campaign = campaign;
//	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public int getGid() {
		return gid;
	}
	public void setGid(int gid) {
		this.gid = gid;
	}
	public int getUserid() {
		return userid;
	}
	public Set<ProductImage> getImages() {
		return  images;
	}
	public void setImages(List<ProductImage> images) {
		this.images = (Set<ProductImage>) images;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getProduct_name() {
		return product_name;
	}
	public Set<CampaignProducts> getCampaignProducts() {
		return CampaignProducts;
	}
	public void setCampaignProducts(Set<CampaignProducts> campaignProducts) {
		CampaignProducts = campaignProducts;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public int getActive() {
		return active;
	}
	public void setActive(int active) {
		this.active = active;
	}
	public String getDefinition() {
		return definition;
	}
	public void setDefinition(String definition) {
		this.definition = definition;
	}
	
	
}
