package com.dashboard.model;


import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
@Table(name="Campaign")
public class Campaign {

	@Id
	@GeneratedValue
	private int cid;
	private int userid;	
	private String campaign_name;
	private String campaign_desc;
	@Temporal(TemporalType.TIMESTAMP)
	private Date campaign_start_date;
	@Temporal(TemporalType.TIMESTAMP)
	private Date campaign_end_date;
	@Column(columnDefinition="LONGTEXT")
	private String json;
	private int active;
	
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinColumn(name="cid", nullable = false, insertable = false)
	private Set<CampaignProducts> CampaignProducts;

	public int getCid() {
		return cid;
	}


	public void setCid(int cid) {
		this.cid = cid;
	}
	public int getUserid() {
		return userid;
	}


	public int getActive() {
		return active;
	}


	public void setActive(int active) {
		this.active = active;
	}


	public Set<CampaignProducts> getCampaignProducts() {
		return CampaignProducts;
	}


	public void setCampaignProducts(Set<CampaignProducts> campaignProducts) {
		CampaignProducts = campaignProducts;
	}


	public void setUserid(int userid) {
		this.userid = userid;
	}


	public String getCampaign_name() {
		return campaign_name;
	}


	public void setCampaign_name(String campaign_name) {
		this.campaign_name = campaign_name;
	}


	public String getCampaign_desc() {
		return campaign_desc;
	}


	public void setCampaign_desc(String campaign_desc) {
		this.campaign_desc = campaign_desc;
	}


	public Date getCampaign_start_date() {
		return campaign_start_date;
	}


	public void setCampaign_start_date(Date campaign_start_date) {
		this.campaign_start_date = campaign_start_date;
	}


	public Date getCampaign_end_date() {
		return campaign_end_date;
	}


	public void setCampaign_end_date(Date campaign_end_date) {
		this.campaign_end_date = campaign_end_date;
	}


	public String getJson() {
		return json;
	}


	public void setJson(String json) {
		this.json = json;
	}



	
	
}
