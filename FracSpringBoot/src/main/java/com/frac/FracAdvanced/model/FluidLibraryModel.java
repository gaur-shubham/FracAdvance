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
@Table
public class FluidLibraryModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="value", nullable=true)
	private String value;
	
	@Column(name="Parameter", nullable=true)
	private String parameter;
	
	@Column(name="type", nullable=true)
	private String type;
	
	@Column(name="fluidTypeSelected", nullable=true)
	private String fluidTypeSelected;
	
	
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH },
			fetch=FetchType.LAZY)	
	@JoinColumn(name="Project_ID", nullable=true)
	private ProjectDetails pidFL;
		
	public ProjectDetails getPidFL() {
		return pidFL;
	}

	public void setPidFL(ProjectDetails pidFL) {
		this.pidFL = pidFL;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	

	public String getFluidTypeSelected() {
		return fluidTypeSelected;
	}

	public void setFluidTypeSelected(String fluidTypeSelected) {
		this.fluidTypeSelected = fluidTypeSelected;
	}

	@Override
	public String toString() {
		return "FluidLibraryModel [id=" + id + ", value=" + value + ", parameter=" + parameter + ", type=" + type
				+ ", fluidTypeSelected=" + fluidTypeSelected + ", pidFL=" + pidFL + "]";
	}


	

	public FluidLibraryModel(Integer id, String value, String parameter, String type, String fluidTypeSelected,
			ProjectDetails pidFL) {
		super();
		this.id = id;
		this.value = value;
		this.parameter = parameter;
		this.type = type;
		this.fluidTypeSelected = fluidTypeSelected;
		this.pidFL = pidFL;
	}

	public FluidLibraryModel() {
		super();
		// TODO Auto-generated constructor stub
	}


}
