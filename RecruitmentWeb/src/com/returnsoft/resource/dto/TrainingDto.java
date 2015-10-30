package com.returnsoft.resource.dto;

import java.io.Serializable;
import java.util.Date;

public class TrainingDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Date createdAt;
	private Date trainingAt;
	private String comment;
	private InterviewDto interview;
	private TrainingStateDto trainingState;
	private TrainerDto trainer;
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
	public Date getTrainingAt() {
		return trainingAt;
	}
	public void setTrainingAt(Date trainingAt) {
		this.trainingAt = trainingAt;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public InterviewDto getInterview() {
		return interview;
	}
	public void setInterview(InterviewDto interview) {
		this.interview = interview;
	}
	public TrainingStateDto getTrainingState() {
		return trainingState;
	}
	public void setTrainingState(TrainingStateDto trainingState) {
		this.trainingState = trainingState;
	}
	public TrainerDto getTrainer() {
		return trainer;
	}
	public void setTrainer(TrainerDto trainer) {
		this.trainer = trainer;
	}
	public RecruiterDto getRecruiter() {
		return recruiter;
	}
	public void setRecruiter(RecruiterDto recruiter) {
		this.recruiter = recruiter;
	}
	
	

}
