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
@Table(name = "outputStress")
public class OutputStressModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String depth;
	private String breakdown;
	private String horizontal;
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
	public String getDepth() {
		return depth;
	}
	public void setDepth(String depth) {
		this.depth = depth;
	}
	public String getBreakdown() {
		return breakdown;
	}
	public void setBreakdown(String breakdown) {
		this.breakdown = breakdown;
	}
	public String getHorizontal() {
		return horizontal;
	}
	public void setHorizontal(String horizontal) {
		this.horizontal = horizontal;
	}
	public ProjectDetails getDetails() {
		return details;
	}
	public void setDetails(ProjectDetails details) {
		this.details = details;
	}
	@Override
	public String toString() {
		return "OutputStressModel [id=" + id + ", depth=" + depth + ", breakdown=" + breakdown + ", horizontal="
				+ horizontal + ", details=" + details + "]";
	}
	public OutputStressModel(String depth, String breakdown, String horizontal, ProjectDetails details) {
		super();
		this.depth = depth;
		this.breakdown = breakdown;
		this.horizontal = horizontal;
		this.details = details;
	}
	public OutputStressModel() {
		super();
	}
	
	
	
}
