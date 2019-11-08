package com.frac.FracAdvanced.domain.security;

import javax.persistence.*;

import com.frac.FracAdvanced.model.ProjectDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "userold")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "username")
	private String userName;

	@Column(name = "password")
	private String password;

	@Column(name = "company_name")
	private String companyName;

	@Column(name = "account_locked")
	private Integer accountLocked;

	@Column(name = "account_expired")
	private Integer accountExpired;

	@Column(name = "password_expired")
	private Integer passwordExpired;

	@Column(name = "email")
	private String email;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Collection<Role> roles;

	@OneToMany(fetch = FetchType.LAZY, cascade={CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH})
	private List<ProjectDetails> projectDetails;
	
//
//	public void add(ProjectDetails details) {
//		if (details == null) {
//			projectDetails = new ArrayList<>();
//		}s
//		projectDetails.add(details);
//		details.setUser(this);
//	}

	public List<ProjectDetails> getProjectDetails() {
		return projectDetails;
	}

	public void setProjectDetails(List<ProjectDetails> projectDetails) {
		this.projectDetails = projectDetails;
	}

	public User() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Integer getAccountLocked() {
		return accountLocked;
	}

	public void setAccountLocked(Integer accountLocked) {
		this.accountLocked = accountLocked;
	}

	public Integer getAccountExpired() {
		return accountExpired;
	}

	public void setAccountExpired(Integer accountExpired) {
		this.accountExpired = accountExpired;
	}

	public Integer getPasswordExpired() {
		return passwordExpired;
	}

	public void setPasswordExpired(Integer passwordExpired) {
		this.passwordExpired = passwordExpired;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}

	public User(String userName, String password, String companyName, Integer accountLocked, Integer accountExpired,
			Integer passwordExpired, String email, Collection<Role> roles) {
		super();
		this.userName = userName;
		this.password = password;
		this.companyName = companyName;
		this.accountLocked = accountLocked;
		this.accountExpired = accountExpired;
		this.passwordExpired = passwordExpired;
		this.email = email;
		this.roles = roles;
	}

}
