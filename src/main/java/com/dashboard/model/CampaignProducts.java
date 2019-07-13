package com.dashboard.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
@Table(name="CampaignProducts")
@IdClass(CampaignProductsPK.class)
public class CampaignProducts implements Serializable  {
	@Id
	private int cid;
	@Id
	private int pid;
	private int active;
//	@ManyToOne(cascade=CascadeType.ALL,fetch=FetchType.LAZY,targetEntity=Products.class)
//	@JoinColumn(name="pid", nullable = false, insertable = false,updatable=false)
//	private Set<Products> Products;
	
//	@ManyToOne(cascade=CascadeType.ALL,fetch=FetchType.LAZY,targetEntity=Campaign.class)
//	@JoinColumn(name="cid", nullable = false, insertable = false,updatable=false)
//	private Campaign Campaign;
	
	public int getActive() {
		return active;
	}
	public void setActive(int active) {
		this.active = active;
	}
//	public Set<Products> getProducts() {
//		return Products;
//	}
//	public void setProducts(Set<Products> products) {
//		Products = products;
//	}
//	public Campaign getCampaign() {
//		return Campaign;
//	}
//	public void setCampaign(Campaign campaign) {
//		Campaign = campaign;
//	}
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}

	
	
}



