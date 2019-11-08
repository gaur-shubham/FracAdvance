package com.frac.FracAdvanced.model;


import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="User")
public class userModel {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private String userName;
	 private String password;
	 private boolean active;
	 private String rolw;

	/* @OneToMany(cascade= {CascadeType.ALL},
				fetch=FetchType.LAZY,
				mappedBy="projectdetailtable") 
		private Set<ProjectDetails> mini=new HashSet<>(); */
	 
	 
	 public int getId() {
		return id;
		
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public String getRolw() {
		return rolw;
	}
	public void setRolw(String rolw) {
		this.rolw = rolw;
	}
	
	

}
