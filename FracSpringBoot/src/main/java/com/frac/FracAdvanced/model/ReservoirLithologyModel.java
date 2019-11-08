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
@Table(name = "lithology")
public class ReservoirLithologyModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String tvd;
	private String md;
	private String layer;
	private String rock;
	private String perm;
	private String poro;
	private String leakoff;
	private String youngs;
	private String fracstage;
	public String getFracstage() {
		return fracstage;
	}
	public void setFracstage(String fracstage) {
		this.fracstage = fracstage;
	}
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
	public String getTvd() {
		return tvd;
	}
	public void setTvd(String tvd) {
		this.tvd = tvd;
	}
	public String getMd() {
		return md;
	}
	public void setMd(String md) {
		this.md = md;
	}
	public String getLayer() {
		return layer;
	}
	public void setLayer(String layer) {
		this.layer = layer;
	}
	public String getRock() {
		return rock;
	}
	public void setRock(String rock) {
		this.rock = rock;
	}
	public String getPerm() {
		return perm;
	}
	public void setPerm(String perm) {
		this.perm = perm;
	}
	public String getPoro() {
		return poro;
	}
	public void setPoro(String poro) {
		this.poro = poro;
	}
	public String getLeakoff() {
		return leakoff;
	}
	public void setLeakoff(String leakoff) {
		this.leakoff = leakoff;
	}
	public String getYoungs() {
		return youngs;
	}
	public void setYoungs(String youngs) {
		this.youngs = youngs;
	}
	public ProjectDetails getDetails() {
		return details;
	}
	public void setDetails(ProjectDetails details) {
		this.details = details;
	}
	public ReservoirLithologyModel(String tvd, String md, String layer, String rock, String perm, String poro,
			String leakoff, String youngs, String fracstage, ProjectDetails details) {
		super();
		this.tvd = tvd;
		this.md = md;
		this.layer = layer;
		this.rock = rock;
		this.perm = perm;
		this.poro = poro;
		this.leakoff = leakoff;
		this.youngs = youngs;
		this.fracstage = fracstage;
		this.details = details;
	}
	@Override
	public String toString() {
		return "ReservoirLithologyModel [id=" + id + ", tvd=" + tvd + ", md=" + md + ", layer=" + layer + ", rock="
				+ rock + ", perm=" + perm + ", poro=" + poro + ", leakoff=" + leakoff + ", youngs=" + youngs
				+ ", fracstage=" + fracstage + ", details=" + details + "]";
	}
	public ReservoirLithologyModel() {
		super();
	}
	
}
