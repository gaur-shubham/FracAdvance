package com.frac.FracAdvanced.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.frac.FracAdvanced.domain.security.User;


/**
 * @author ShubhamGaur
 *
 */
@Entity
@Table(name="project_details")
public class ProjectDetails implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	@Column(name="project_name", nullable=true, unique=false)
	private String projectName;
	
	@Column(name="well_name", nullable=true, unique=false)
	private String wellName;
	
	@Column(name="cmpany_name", nullable=true, unique=false)
	private String companyName;
	
	@Column(name="date_Created", nullable=true, unique=false)
	private String dateCreated;
	
	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinColumn(name = "user_id")
	private User user;
	
	@OneToMany(cascade= {CascadeType.ALL},
			fetch=FetchType.LAZY,
			mappedBy="details") 
	private Set<MiniFracModel> mini=new HashSet<>();   
	
	
	@OneToMany(cascade= {CascadeType.ALL},
	fetch=FetchType.LAZY,
	mappedBy="prodetails") 
	private List<OutputMiniFrac> output;
	
	@OneToMany(cascade= {CascadeType.ALL},
			fetch=FetchType.LAZY,
			mappedBy="details") 
			private List<InjectionPlanModel> injection;
	
	@OneToMany(cascade= {CascadeType.ALL},
			fetch=FetchType.LAZY,
			mappedBy="details") 
			private List<InjectedFluidModel> injected;
	
	@OneToMany(cascade= {CascadeType.ALL},
			fetch=FetchType.LAZY,
			mappedBy="details") 
			private List<InjectedFluid1Model> injected1;
	
	@OneToMany(cascade= {CascadeType.ALL},
			fetch=FetchType.LAZY,
			mappedBy="pid") 
	private List<WellDataModel> wd;
	
	@OneToMany(cascade= {CascadeType.ALL},
			fetch=FetchType.LAZY,
			mappedBy="pidFL") 
	private List<FluidLibraryModel> FLibrary;
	
	@OneToMany(cascade= {CascadeType.ALL},
			fetch=FetchType.LAZY,
			mappedBy="details") 
			private List<PumpingModel> pumping;
	
	@OneToMany(cascade= {CascadeType.ALL},
			fetch=FetchType.LAZY,
			mappedBy="details") 
			private List<RockPropertiesModel> rock;
	
	@OneToMany(cascade= {CascadeType.ALL},
			fetch=FetchType.LAZY,
			mappedBy="details") 
			private List<ReservoirFluidModel> fluidModels;
	
	@OneToMany(cascade= {CascadeType.ALL},
			fetch=FetchType.LAZY,
			mappedBy="details") 
			private List<StressAnalysisModel> stress;
	
	@OneToMany(cascade= {CascadeType.ALL},
			fetch=FetchType.LAZY,
			mappedBy="details") 
			private List<ProppantModel> proppant;
	
	@OneToMany(cascade= {CascadeType.ALL},
			fetch=FetchType.LAZY,
			mappedBy="details") 
			private List<OutputStressModel> outStress;
	
	@OneToMany(cascade= {CascadeType.ALL},
			fetch=FetchType.LAZY,
			mappedBy="details") 
			private List<WellForcastModel> forcast;
	
	@OneToMany(cascade= {CascadeType.ALL},
			fetch=FetchType.LAZY,
			mappedBy="details") 
			private List<ReservoirLithologyModel> lithology;
	
	@OneToMany(cascade= {CascadeType.ALL},
			fetch=FetchType.LAZY,
			mappedBy="details") 
			private List<NonDarcyModel> nondarcy;
	
	@OneToMany(cascade= {CascadeType.ALL},
			fetch=FetchType.LAZY,
			mappedBy="details") 
			private List<MainFracGraphModel> MiniFracGraph;
	
	@OneToMany(cascade= {CascadeType.ALL},
			fetch=FetchType.LAZY,
			mappedBy="details") 
			private List<ReportParamModel> reportParam;
	
	@OneToMany(cascade= {CascadeType.ALL},
			fetch=FetchType.LAZY,
			mappedBy="pid") 
	private List<ReportMakingModel> rmd;
	
	@OneToMany(cascade= {CascadeType.ALL},
			fetch=FetchType.LAZY,
			mappedBy="details") 
	private List<PhasingParamModel> pp;
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getWellName() {
		return wellName;
	}

	public void setWellName(String wellName) {
		this.wellName = wellName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}
	
    public ProjectDetails() {
		// TODO Auto-generated constructor stub
	}

	public ProjectDetails(String projectName, String wellName, String companyName, String dateCreated) {
		this.projectName = projectName;
		this.wellName = wellName;
		this.companyName = companyName;
		this.dateCreated = dateCreated;
	}
}
