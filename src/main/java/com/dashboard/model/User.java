package com.dashboard.model;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Fetch;

@Entity
@DynamicUpdate
@Table(name="user")
public class User 
{
@Id
@GeneratedValue
private int userid;
private String name;
private String password;
@Column(unique = true)
private String email;
private String organisation;
private int active;
@CreationTimestamp
private LocalDateTime timestamp;

@Override
public String toString() {
	return "User [userid=" + userid + ", name=" + name + ", password=" + password + ", email=" + email
			+ ", organisation=" + organisation + ", active=" + active + ", timestamp=" + timestamp +  "]";
}
@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
@JoinColumn(name="userid")
private Set<Groups> groups;

public Set<Groups> getGroups() {
	return groups;
}
public void setGroups(List<Groups> groups) {
	this.groups = (Set<Groups>) groups;
}
public Set<Products> getProducts() {
	return products;
}
public void setProducts(List<Products> products) {
	this.products = (Set<Products>) products;
}
@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
@JoinColumn(name="userid")
private Set<Products> products;


public int getUserid() {
	return userid;
}
public void setUserid(int userid) {
	this.userid = userid;
}
public String getName() {
	return name;
}
public int getActive() {
	return active;
}
public void setActive(int active) {
	this.active = active;
}
public void setName(String name) {
	this.name = name;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getOrganisation() {
	return organisation;
}
public void setOrganisation(String organisation) {
	this.organisation = organisation;
}



	
}
