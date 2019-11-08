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

@Entity
@Table(name="wellDataTable")
public class WellDataModel {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="Parameter", nullable=true)
	private String parameter;
	
	@Column(name="value", nullable=true)
	private String value;
	
	@Column(name="wellType", nullable=true)
	private String wellType;
	
	@Column(name="CompletionType", nullable=true)
	private String CompletionType;

	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH },
			fetch=FetchType.LAZY)	
	@JoinColumn(name="proDetail_id", nullable=true)
	private ProjectDetails pid;
	
	public ProjectDetails getPid() {
		return pid;
	}

	public void setPid(ProjectDetails pid) {
		this.pid = pid;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getWellType() {
		return wellType;
	}

	public void setWellType(String wellType) {
		this.wellType = wellType;
	}

	public String getCompletionType() {
		return CompletionType;
	}

	public void setCompletionType(String completionType) {
		CompletionType = completionType;
	}

	@Override
	public String toString() {
		return "WellDataModel [id=" + id + ", parameter=" + parameter + ", value=" + value + ", wellType=" + wellType
				+ ", CompletionType=" + CompletionType + "]";
	}

	public WellDataModel(Integer id, String parameter, String value, String wellType, String completionType) {
		super();
		this.id = id;
		this.parameter = parameter;
		this.value = value;
		this.wellType = wellType;
		CompletionType = completionType;
	}

	public WellDataModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
