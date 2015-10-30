package com.returnsoft.resource.dto;

import java.io.Serializable;
import java.util.Date;

public class InterviewDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Date createdAt;
	private Date interviewedAt;
	private String comment;
	private CandidateDto candidate;
	private InterviewStateDto interviewState;
	private RecruiterDto recruiter;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public InterviewStateDto getInterviewState() {
		return interviewState;
	}
	public void setInterviewState(InterviewStateDto interviewState) {
		this.interviewState = interviewState;
	}
	
	public Date getInterviewedAt() {
		return interviewedAt;
	}
	public void setInterviewedAt(Date interviewedAt) {
		this.interviewedAt = interviewedAt;
	}
	public CandidateDto getCandidate() {
		return candidate;
	}
	public void setCandidate(CandidateDto candidate) {
		this.candidate = candidate;
	}
	public RecruiterDto getRecruiter() {
		return recruiter;
	}
	public void setRecruiter(RecruiterDto recruiter) {
		this.recruiter = recruiter;
	}

	
	
	

}
