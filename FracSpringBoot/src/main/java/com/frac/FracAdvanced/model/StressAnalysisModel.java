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
@Table(name = "stressAnalysis")
public class StressAnalysisModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String ratio;
	private String density;
	private String depth;
	private String pore;
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
	public String getRatio() {
		return ratio;
	}
	public void setRatio(String ratio) {
		this.ratio = ratio;
	}
	public String getDensity() {
		return density;
	}
	public void setDensity(String density) {
		this.density = density;
	}
	public String getDepth() {
		return depth;
	}
	public void setDepth(String depth) {
		this.depth = depth;
	}
	public String getPore() {
		return pore;
	}
	public void setPore(String pore) {
		this.pore = pore;
	}
	public ProjectDetails getDetails() {
		return details;
	}
	public void setDetails(ProjectDetails details) {
		this.details = details;
	}
	@Override
	public String toString() {
		return "StressAnalysisModel [id=" + id + ", ratio=" + ratio + ", density=" + density + ", depth=" + depth
				+ ", pore=" + pore + ", details=" + details + "]";
	}
	public StressAnalysisModel(String ratio, String density, String depth, String pore, ProjectDetails details) {
		super();
		this.ratio = ratio;
		this.density = density;
		this.depth = depth;
		this.pore = pore;
		this.details = details;
	}
	public StressAnalysisModel() {
		super();
	}
	
}
