package com.returnsoft.resource.dto;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;



public class CandidateDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Date bornDate;
	private Date scheduledAt;
	private String cellPhone;
	private String documentNumber;
	private String documentType;
	private String entryType;
	private String experience;
	private String firstname;
	private String lastname;
	private String mail;
	private String phone;
	private CountryDto country;
	private AreaDto area;
	private DepartmentDto department;
	private DistrictDto district;
	private ProvinceDto province;
	private RecruimentSourceDto recruimentSource;
	private RecruiterDto recruiter;
	private CandidateStateDto candidateState;
	private Date createdAt;
	private Integer age;
	
	private EducationLevelDto educationLevel;
	private String homeAddress;
	private String lastJob;
	
	private String agentName;
	private String agentDocumentNumber;
	private String agentCampaign;
	private String jobFairName;
	
	private Date interviewedAt;
	private String interviewStateName;
	private Date trainingAt;
	private String trainingStateName;
	private Date ojtAt;
	private String ojtStateName;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getBornDate() {
		return bornDate;
	}
	public void setBornDate(Date bornDate) {
		this.bornDate = bornDate;
	}
	public String getCellPhone() {
		return cellPhone;
	}
	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}
	public String getDocumentNumber() {
		return documentNumber;
	}
	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}
	public String getDocumentType() {
		return documentType;
	}
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
	public String getEntryType() {
		return entryType;
	}
	public void setEntryType(String entryType) {
		this.entryType = entryType;
	}
	public String getExperience() {
		return experience;
	}
	public void setExperience(String experience) {
		this.experience = experience;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public CountryDto getCountry() {
		return country;
	}
	public void setCountry(CountryDto country) {
		this.country = country;
	}
	
	public AreaDto getArea() {
		return area;
	}
	public void setArea(AreaDto area) {
		this.area = area;
	}
	public DistrictDto getDistrict() {
		return district;
	}
	public void setDistrict(DistrictDto district) {
		this.district = district;
	}
	public ProvinceDto getProvince() {
		return province;
	}
	public void setProvince(ProvinceDto province) {
		this.province = province;
	}
	public RecruimentSourceDto getRecruimentSource() {
		return recruimentSource;
	}
	public void setRecruimentSource(RecruimentSourceDto recruimentSource) {
		this.recruimentSource = recruimentSource;
	}
	
	public RecruiterDto getRecruiter() {
		return recruiter;
	}
	public void setRecruiter(RecruiterDto recruiter) {
		this.recruiter = recruiter;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public DepartmentDto getDepartment() {
		return department;
	}
	public void setDepartment(DepartmentDto department) {
		this.department = department;
	}
	public Integer getAge() {
		if (getBornDate()!=null) {
			Date current = new Date();
			Long difference = current.getTime()
					- getBornDate().getTime();
			Calendar differenceCalendar = Calendar.getInstance();
			differenceCalendar.setTimeInMillis(difference);
			age = differenceCalendar.get(Calendar.YEAR) - 1970;	
		}
		
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public Date getScheduledAt() {
		return scheduledAt;
	}
	public void setScheduledAt(Date scheduledAt) {
		this.scheduledAt = scheduledAt;
	}
	public CandidateStateDto getCandidateState() {
		return candidateState;
	}
	public void setCandidateState(CandidateStateDto candidateState) {
		this.candidateState = candidateState;
	}
	public EducationLevelDto getEducationLevel() {
		return educationLevel;
	}
	public void setEducationLevel(EducationLevelDto educationLevel) {
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
	public Date getInterviewedAt() {
		return interviewedAt;
	}
	public void setInterviewedAt(Date interviewedAt) {
		this.interviewedAt = interviewedAt;
	}
	public String getInterviewStateName() {
		return interviewStateName;
	}
	public void setInterviewStateName(String interviewStateName) {
		this.interviewStateName = interviewStateName;
	}
	public Date getTrainingAt() {
		return trainingAt;
	}
	public void setTrainingAt(Date trainingAt) {
		this.trainingAt = trainingAt;
	}
	public String getTrainingStateName() {
		return trainingStateName;
	}
	public void setTrainingStateName(String trainingStateName) {
		this.trainingStateName = trainingStateName;
	}
	public Date getOjtAt() {
		return ojtAt;
	}
	public void setOjtAt(Date ojtAt) {
		this.ojtAt = ojtAt;
	}
	public String getOjtStateName() {
		return ojtStateName;
	}
	public void setOjtStateName(String ojtStateName) {
		this.ojtStateName = ojtStateName;
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
