package com.frac.FracAdvanced.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
@Table(name="miniFrac")
public class MiniFracModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="pressure", nullable=true)
	private String pressure;
	
	@Column(name="time", nullable=true)
	private String time;
	
	@Column(name="pumptime", nullable=true)
	private String pumptime;
	
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH },
			fetch=FetchType.LAZY)
	@JoinColumn(name="proDetail_id", nullable=true)
	private ProjectDetails details;
	
	public ProjectDetails getDetails() {
		return details;
	}
	public void setDetails(ProjectDetails details) {
		this.details = details;
	}
	public String getPumptime() {
		return pumptime;
	}
	public void setPumptime(String pumptime) {
		this.pumptime = pumptime;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPressure() {
		return pressure;
	}
	public void setPressure(String pressure) {
		this.pressure = pressure;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public MiniFracModel() {
		super();
	}
	public MiniFracModel(Integer id, String pressure, String time, String pumptime) {
		super();
		this.id = id;
		this.pressure = pressure;
		this.time = time;
		this.pumptime = pumptime;
	}
	@Override
	public String toString() {
		return "MiniFracModel [id=" + id + ", pressure=" + pressure + ", time=" + time + ", pumptime=" + pumptime + "]";
	}
}
