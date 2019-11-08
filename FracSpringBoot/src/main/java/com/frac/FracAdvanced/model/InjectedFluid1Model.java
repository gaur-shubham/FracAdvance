/**
 * 
 */
package com.frac.FracAdvanced.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author ShubhamGaur
 *
 */

@Entity
@Table(name="injectedFluid1")
public class InjectedFluid1Model {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String param;
	private String Value;
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH },
			fetch=FetchType.LAZY)
	@JoinColumn(name="pId", nullable=true)
	private ProjectDetails details;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	public String getValue() {
		return Value;
	}
	public void setValue(String value) {
		Value = value;
	}
	public ProjectDetails getDetails() {
		return details;
	}
	public void setDetails(ProjectDetails details) {
		this.details = details;
	}
	@Override
	public String toString() {
		return "InjectedFluid1Model [id=" + id + ", param=" + param + ", Value=" + Value + ", details=" + details + "]";
	}
	public InjectedFluid1Model() {
		super();
	}
	public InjectedFluid1Model(String param, String value, ProjectDetails details) {
		super();
		this.param = param;
		Value = value;
		this.details = details;
	}
	
}
