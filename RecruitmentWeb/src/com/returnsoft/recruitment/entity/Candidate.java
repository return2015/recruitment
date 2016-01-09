package com.returnsoft.recruitment.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
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
	@Column(name = "id")
	private Long id;

	@Column(name = "document_type")
	private String documentType;

	@Column(name = "document_number")
	private String documentNumber;

	@Column(name = "firstname")
	private String firstname;

	@Column(name = "lastname")
	private String lastname;

	@Column(name = "mail")
	private String mail;

	@Column(name = "phone")
	private String phone;

	@Column(name = "cell_phone")
	private String cellPhone;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "born_date")
	private Date bornDate;

	@Column(name = "entry_type")
	private String entryType;

	@Column(name = "experience")
	private String experience;

	

	/*@ManyToOne
	@JoinColumn(name = "area_id")
	private Area area;*/

	/*@ManyToOne
	@JoinColumn(name = "candidate_state_id")
	private CandidateState candidateState;*/

	@ManyToOne
	@JoinColumn(name = "country_id")
	private Country country;

	// bi-directional many-to-one association to District
	@ManyToOne
	@JoinColumn(name = "department_id")
	private Department department;

	// bi-directional many-to-one association to District
	@ManyToOne
	@JoinColumn(name = "district_id")
	private District district;

	// bi-directional many-to-one association to Province
	@ManyToOne
	@JoinColumn(name = "province_id")
	private Province province;

	// bi-directional many-to-one association to RecruimentSource
	/*@ManyToOne
	@JoinColumn(name = "recruiment_source_id")
	private RecruimentSource recruimentSource;*/

	// bi-directional many-to-one association to RecruimentSource
	@ManyToOne
	@JoinColumn(name = "education_level_id")
	private EducationLevel educationLevel;

	@Column(name = "home_address")
	private String homeAddress;

	@Column(name = "last_job")
	private String lastJob;
	
	/*@Column(name = "agent_name")
	private String agentName;
	
	@Column(name = "agent_document_number")
	private String agentDocumentNumber;
	
	@Column(name = "agent_campaign")
	private String agentCampaign;
	
	@Column(name = "job_fair_name")
	private String jobFairName;*/
	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at")
	private Date createdAt;
	
	@ManyToOne
	@JoinColumn(name = "created_by")
	private User createdBy;

	/*@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "scheduled_at")
	private Date scheduledAt;*/
	
	/*@ManyToOne
	@JoinColumn(name = "scheduled_by")
	private User scheduledBy;*/
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "interview_id")
	private Interview interview;
	

	public Candidate() {
	}

	

	public Long getId() {
		return id;
	}



	public void setId(Long id) {
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

	

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
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

	
	public Interview getInterview() {
		return interview;
	}

	public void setInterview(Interview interview) {
		this.interview = interview;
	}
	
	

}
