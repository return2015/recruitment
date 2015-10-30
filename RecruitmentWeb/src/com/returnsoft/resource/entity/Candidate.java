package com.returnsoft.resource.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "candidate")
public class Candidate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "document_type")
	private String documentType;

	@Column(name = "document_number")
	private String documentNumber;

	private String firstname;

	private String lastname;

	private String mail;

	private String phone;

	@Column(name = "cell_phone")
	private String cellPhone;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "born_date")
	private Date bornDate;

	@Column(name = "entry_type")
	private String entryType;

	private String experience;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at")
	private Date createdAt;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "scheduled_at")
	private Date scheduledAt;

	// bi-directional many-to-one association to Department
	@ManyToOne
	private Area area;

	@ManyToOne
	@JoinColumn(name = "candidate_state_id")
	private CandidateState candidateState;

	@ManyToOne
	@JoinColumn(name = "recruiter_id")
	private Recruiter recruiter;

	@ManyToOne
	private Country country;

	// bi-directional many-to-one association to District
	@ManyToOne
	private Department department;

	// bi-directional many-to-one association to District
	@ManyToOne
	private District district;

	// bi-directional many-to-one association to Province
	@ManyToOne
	private Province province;

	// bi-directional many-to-one association to RecruimentSource
	@ManyToOne
	@JoinColumn(name = "recruiment_source_id")
	private RecruimentSource recruimentSource;

	// bi-directional many-to-one association to RecruimentSource
	@ManyToOne
	@JoinColumn(name = "education_level_id")
	private EducationLevel educationLevel;

	@Column(name = "home_address")
	private String homeAddress;

	@Column(name = "last_job")
	private String lastJob;
	
	@Column(name = "agent_name")
	private String agentName;
	
	@Column(name = "agent_document_number")
	private String agentDocumentNumber;
	
	@Column(name = "agent_campaign")
	private String agentCampaign;
	
	@Column(name = "job_fair_name")
	private String jobFairName;

	public Candidate() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getBornDate() {
		return this.bornDate;
	}

	public void setBornDate(Date bornDate) {
		this.bornDate = bornDate;
	}

	public String getCellPhone() {
		return this.cellPhone;
	}

	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}

	public String getDocumentNumber() {
		return this.documentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	public String getDocumentType() {
		return this.documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public String getEntryType() {
		return this.entryType;
	}

	public void setEntryType(String entryType) {
		this.entryType = entryType;
	}

	public String getExperience() {
		return this.experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	public String getFirstname() {
		return this.firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return this.lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getMail() {
		return this.mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Country getCountry() {
		return this.country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public District getDistrict() {
		return this.district;
	}

	public void setDistrict(District district) {
		this.district = district;
	}

	public Province getProvince() {
		return this.province;
	}

	public void setProvince(Province province) {
		this.province = province;
	}

	public RecruimentSource getRecruimentSource() {
		return this.recruimentSource;
	}

	public void setRecruimentSource(RecruimentSource recruimentSource) {
		this.recruimentSource = recruimentSource;
	}

	public Recruiter getRecruiter() {
		return recruiter;
	}

	public void setRecruiter(Recruiter recruiter) {
		this.recruiter = recruiter;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Date getScheduledAt() {
		return scheduledAt;
	}

	public void setScheduledAt(Date scheduledAt) {
		this.scheduledAt = scheduledAt;
	}

	public CandidateState getCandidateState() {
		return candidateState;
	}

	public void setCandidateState(CandidateState candidateState) {
		this.candidateState = candidateState;
	}

	public EducationLevel getEducationLevel() {
		return educationLevel;
	}

	public void setEducationLevel(EducationLevel educationLevel) {
		this.educationLevel = educationLevel;
	}

	public String getHomeAddress() {
		return homeAddress;
	}

	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}

	public String getLastJob() {
		return lastJob;
	}

	public void setLastJob(String lastJob) {
		this.lastJob = lastJob;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getAgentDocumentNumber() {
		return agentDocumentNumber;
	}

	public void setAgentDocumentNumber(String agentDocumentNumber) {
		this.agentDocumentNumber = agentDocumentNumber;
	}

	public String getAgentCampaign() {
		return agentCampaign;
	}

	public void setAgentCampaign(String agentCampaign) {
		this.agentCampaign = agentCampaign;
	}

	public String getJobFairName() {
		return jobFairName;
	}

	public void setJobFairName(String jobFairName) {
		this.jobFairName = jobFairName;
	}
	
	

}
