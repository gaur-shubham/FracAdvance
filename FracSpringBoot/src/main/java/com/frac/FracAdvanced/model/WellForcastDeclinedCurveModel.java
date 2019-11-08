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
@Table(name = "DeclineCurve")
public class WellForcastDeclinedCurveModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String bm;
	private String param;
	private String value;
	private String time;
	private String Rate;
	private String yearlyprodunction;
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
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getRate() {
		return Rate;
	}
	public void setRate(String rate) {
		Rate = rate;
	}
	public String getYearlyprodunction() {
		return yearlyprodunction;
	}
	public void setYearlyprodunction(String yearlyprodunction) {
		this.yearlyprodunction = yearlyprodunction;
	}
	public ProjectDetails getDetails() {
		return details;
	}
	public void setDetails(ProjectDetails details) {
		this.details = details;
	}
	public WellForcastDeclinedCurveModel(String bm, String param, String value, String time, String rate,
			String yearlyprodunction, ProjectDetails details) {
		super();
		this.bm = bm;
		this.param = param;
		this.value = value;
		this.time = time;
		Rate = rate;
		this.yearlyprodunction = yearlyprodunction;
		this.details = details;
	}
	public String getBm() {
		return bm;
	}
	public void setBm(String bm) {
		this.bm = bm;
	}
	public WellForcastDeclinedCurveModel() {
		super();
	}
	
	
}
