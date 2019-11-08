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
@Table(name = "phasingParam")
public class PhasingParamModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	
	private String angle;
	private String alpha;
	private String c1;
	private String c2;
	private String a1;
	private String a2;
	private String b1;
	private String b2;
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
	
	public String getAngle() {
		return angle;
	}
	public void setAngle(String angle) {
		this.angle = angle;
	}
	public String getAlpha() {
		return alpha;
	}
	public void setAlpha(String alpha) {
		this.alpha = alpha;
	}
	public String getC1() {
		return c1;
	}
	public void setC1(String c1) {
		this.c1 = c1;
	}
	public String getC2() {
		return c2;
	}
	public void setC2(String c2) {
		this.c2 = c2;
	}
	public String getA1() {
		return a1;
	}
	public void setA1(String a1) {
		this.a1 = a1;
	}
	public String getA2() {
		return a2;
	}
	public void setA2(String a2) {
		this.a2 = a2;
	}
	public String getB1() {
		return b1;
	}
	public void setB1(String b1) {
		this.b1 = b1;
	}
	public String getB2() {
		return b2;
	}
	public void setB2(String b2) {
		this.b2 = b2;
	}
	public ProjectDetails getDetails() {
		return details;
	}
	public void setDetails(ProjectDetails details) {
		this.details = details;
	}
	public PhasingParamModel(String angle, String alpha, String c1, String c2, String a1, String a2, String b1,
			String b2, ProjectDetails details) {
		super();
		this.angle = angle;
		this.alpha = alpha;
		this.c1 = c1;
		this.c2 = c2;
		this.a1 = a1;
		this.a2 = a2;
		this.b1 = b1;
		this.b2 = b2;
		this.details = details;
	}
	@Override
	public String toString() {
		return "PhasingParamModel [id=" + id + ", angle=" + angle + ", alpha=" + alpha + ", c1=" + c1 + ", c2=" + c2
				+ ", a1=" + a1 + ", a2=" + a2 + ", b1=" + b1 + ", b2=" + b2 + ", details=" + details + "]";
	}
	public PhasingParamModel() {
		super();
	}
	
}
