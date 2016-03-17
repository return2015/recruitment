package com.returnsoft.recruitment.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;




/**
 * The persistent class for the interview database table.
 * 
 */
@Entity
@Table(name="interview")
//@NamedQuery(name="Interview.findAll", query="SELECT i FROM Interview i")
public class Interview implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="interviewed_at")
	private Date interviewedAt;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_at")
	private Date createdAt;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="created_by")
	private User createdBy;
	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated_at")
	private Date updatedAt;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="updated_by")
	private User updatedBy;
	

	@Column(name="comment")
	private String comment;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="interview_state_id")
	private InterviewState interviewState;

	//bi-directional many-to-one association to Person
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="candidate_id")
	private Candidate candidate;
	
	//@OneToOne(cascade = CascadeType.ALL, optional = false, fetch = FetchType.LAZY)
	//@PrimaryKeyJoinColumn
	@OneToOne(cascade = CascadeType.ALL, mappedBy="interview",fetch=FetchType.LAZY)
	private Training training;
	
	/*@ManyToOne
	@JoinColumn(name="user_id")
	private User user;*/
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "scheduled_at")
	private Date scheduledAt;
	
	@Column(name = "agent_name")
	private String agentName;
	
	@Column(name = "agent_document_number")
	private String agentDocumentNumber;
	
	@Column(name = "agent_campaign")
	private String agentCampaign;
	
	@Column(name = "job_fair_name")
	private String jobFairName;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "recruitment_source_id")
	private RecruitmentSource recruitmentSource;
	
	@ManyToOne(fetch=FetchType.LAZY)
	//@JoinColumn(name = "requirement_id")
	 @JoinColumns({
	        @JoinColumn(name="user_id", referencedColumnName="user_id"),
	        @JoinColumn(name="requirement_id", referencedColumnName="requirement_id")
	    })
	private RequirementUser requirementUser;
	
	

	public Interview() {
	}

	

	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	

	

	public InterviewState getInterviewState() {
		return this.interviewState;
	}

	public void setInterviewState(InterviewState interviewState) {
		this.interviewState = interviewState;
	}

	

	public Date getInterviewedAt() {
		return interviewedAt;
	}

	public void setInterviewedAt(Date interviewedAt) {
		this.interviewedAt = interviewedAt;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	



	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public Candidate getCandidate() {
		return candidate;
	}

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}

	public Training getTraining() {
		return training;
	}

	public void setTraining(Training training) {
		this.training = training;
	}

	

	public Date getScheduledAt() {
		return scheduledAt;
	}

	public void setScheduledAt(Date scheduledAt) {
		this.scheduledAt = scheduledAt;
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


	public RecruitmentSource getRecruitmentSource() {
		return recruitmentSource;
	}



	public void setRecruitmentSource(RecruitmentSource recruitmentSource) {
		this.recruitmentSource = recruitmentSource;
	}



	/*public Requirement getRequirement() {
		return requirement;
	}

	public void setRequirement(Requirement requirement) {
		this.requirement = requirement;
	}*/



	public Date getUpdatedAt() {
		return updatedAt;
	}



	public RequirementUser getRequirementUser() {
		return requirementUser;
	}



	public void setRequirementUser(RequirementUser requirementUser) {
		this.requirementUser = requirementUser;
	}



	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}



	public User getUpdatedBy() {
		return updatedBy;
	}



	public void setUpdatedBy(User updatedBy) {
		this.updatedBy = updatedBy;
	}
	
	

}