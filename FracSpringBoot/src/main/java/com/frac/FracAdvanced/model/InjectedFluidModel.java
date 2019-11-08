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
@Table(name="injectedFluid")
public class InjectedFluidModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String rate;
	private String coeff;
	private String loss;
	private String skin;
	private String pi;
	private String margin;
	private String stress;
	
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH },
			fetch=FetchType.LAZY)
	@JoinColumn(name="pId", nullable=true)
	private ProjectDetails details;
	
	public ProjectDetails getDetails() {
		return details;
	}
	public void setDetails(ProjectDetails details) {
		this.details = details;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getCoeff() {
		return coeff;
	}
	public void setCoeff(String coeff) {
		this.coeff = coeff;
	}
	public String getLoss() {
		return loss;
	}
	public void setLoss(String loss) {
		this.loss = loss;
	}
	public String getSkin() {
		return skin;
	}
	public void setSkin(String skin) {
		this.skin = skin;
	}
	public String getPi() {
		return pi;
	}
	public void setPi(String pi) {
		this.pi = pi;
	}
	public String getMargin() {
		return margin;
	}
	public void setMargin(String margin) {
		this.margin = margin;
	}
	public String getStress() {
		return stress;
	}
	public void setStress(String stress) {
		this.stress = stress;
	}
	public InjectedFluidModel(String rate, String coeff, String loss, String skin, String pi, String margin,
			String stress) {
		super();
		this.rate = rate;
		this.coeff = coeff;
		this.loss = loss;
		this.skin = skin;
		this.pi = pi;
		this.margin = margin;
		this.stress = stress;
	}
	public InjectedFluidModel() {
		super();
	}
	@Override
	public String toString() {
		return "InjectedFluidModel [id=" + id + ", rate=" + rate + ", coeff=" + coeff + ", loss=" + loss + ", skin="
				+ skin + ", pi=" + pi + ", margin=" + margin + ", stress=" + stress + "]";
	}
	
}
