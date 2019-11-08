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
@Table(name="injectionPlan")
public class InjectionPlanModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String step;
	private String fluidtype;
	private String stagetype;
	private String rate;
	private String cleanvol;
	private String slurryvol;
	private String cumslurry;
	private String begprop;
	private String endprop;
	private String stageprop;
	private String cumprop;
	private String steptime;
	private String cumStepTime;
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
	
	public String getCumStepTime() {
		return cumStepTime;
	}
	public void setCumStepTime(String cumStepTime) {
		this.cumStepTime = cumStepTime;
	}
	public String getStep() {
		return step;
	}
	public void setStep(String step) {
		this.step = step;
	}
	public String getFluidtype() {
		return fluidtype;
	}
	public void setFluidtype(String fluidtype) {
		this.fluidtype = fluidtype;
	}
	public String getStagetype() {
		return stagetype;
	}
	public void setStagetype(String stagetype) {
		this.stagetype = stagetype;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getCleanvol() {
		return cleanvol;
	}
	public void setCleanvol(String cleanvol) {
		this.cleanvol = cleanvol;
	}
	public String getSlurryvol() {
		return slurryvol;
	}
	public void setSlurryvol(String slurryvol) {
		this.slurryvol = slurryvol;
	}
	public String getCumslurry() {
		return cumslurry;
	}
	public void setCumslurry(String cumslurry) {
		this.cumslurry = cumslurry;
	}
	public String getBegprop() {
		return begprop;
	}
	public void setBegprop(String begprop) {
		this.begprop = begprop;
	}
	public String getEndprop() {
		return endprop;
	}
	public void setEndprop(String endprop) {
		this.endprop = endprop;
	}
	public String getStageprop() {
		return stageprop;
	}
	public void setStageprop(String stageprop) {
		this.stageprop = stageprop;
	}
	public String getCumprop() {
		return cumprop;
	}
	public void setCumprop(String cumprop) {
		this.cumprop = cumprop;
	}
	public String getSteptime() {
		return steptime;
	}
	public void setSteptime(String steptime) {
		this.steptime = steptime;
	}
	public ProjectDetails getDetails() {
		return details;
	}
	public void setDetails(ProjectDetails details) {
		this.details = details;
	}
	@Override
	public String toString() {
		return "InjectionPlanModel [id=" + id + ", step=" + step + ", fluidtype=" + fluidtype + ", stagetype="
				+ stagetype + ", rate=" + rate + ", cleanvol=" + cleanvol + ", slurryvol=" + slurryvol + ", cumslurry="
				+ cumslurry + ", begprop=" + begprop + ", endprop=" + endprop + ", stageprop=" + stageprop
				+ ", cumprop=" + cumprop + ", steptime=" + steptime + ", cumStepTime=" + cumStepTime + ", details="
				+ details + "]";
	}
	
	
	public InjectionPlanModel(String step, String fluidtype, String stagetype, String rate, String cleanvol,
			String slurryvol, String cumslurry, String begprop, String endprop, String stageprop, String cumprop,
			String steptime, String cumStepTime, ProjectDetails details) {
		super();
		this.step = step;
		this.fluidtype = fluidtype;
		this.stagetype = stagetype;
		this.rate = rate;
		this.cleanvol = cleanvol;
		this.slurryvol = slurryvol;
		this.cumslurry = cumslurry;
		this.begprop = begprop;
		this.endprop = endprop;
		this.stageprop = stageprop;
		this.cumprop = cumprop;
		this.steptime = steptime;
		this.cumStepTime = cumStepTime;
		this.details = details;
	}
	public InjectionPlanModel() {
		super();
	}
	
}
