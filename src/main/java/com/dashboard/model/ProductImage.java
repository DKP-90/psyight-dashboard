package com.dashboard.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="product_image")
public class ProductImage {
	
	@Id
	@GeneratedValue
	private int iim;
	private int pid;
	private String filename;
	private int active;
	
	
	public int getIim() {
		return iim;
	}
	public void setIim(int iim) {
		this.iim = iim;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public int getActive() {
		return active;
	}
	public void setActive(int active) {
		this.active = active;
	}
	
	

}
