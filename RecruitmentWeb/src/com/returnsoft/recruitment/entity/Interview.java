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
	private Integer id;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="interviewed_at")
	private Date interviewedAt;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_at")
	private Date createdAt;

	private String comment;

	@ManyToOne
	private User recruiter;

	@ManyToOne
	@JoinColumn(name="interview_state_id")
	private InterviewState interviewState;

	//bi-directional many-to-one association to Person
	@ManyToOne
	private Candidate candidate;

	public Interview() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
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

	

	public User getRecruiter() {
		return recruiter;
	}

	public void setRecruiter(User recruiter) {
		this.recruiter = recruiter;
	}

	public Candidate getCandidate() {
		return candidate;
	}

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}
	
	

}