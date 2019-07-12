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
@Table(name="Groups")
public class Groups {
	
	@Id
	@GeneratedValue
	private int gid;
	private int userid;
	private String group_name;
	private String definition;
	private int active;
	private String response_create;
	private String response_train;
	
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinColumn(name="gid", nullable = false, insertable = false)
	private Set<Products> products;
	
	public Set<Products> getProducts() {
		return products;
	}
	public void setProducts(Set<Products> products) {
		this.products = (Set<Products>) products;
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
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getGroup_name() {
		return group_name;
	}
	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}
	public String getDefinition() {
		return definition;
	}
	public void setDefinition(String definition) {
		this.definition = definition;
	}
	public int getActive() {
		return active;
	}
	public void setActive(int active) {
		this.active = active;
	}
	public String getResponse_create() {
		return response_create;
	}
	public void setResponse_create(String response_create) {
		this.response_create = response_create;
	}
	public String getResponse_train() {
		return response_train;
	}
	public void setResponse_train(String response_train) {
		this.response_train = response_train;
	}
	
	

}
