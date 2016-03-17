package com.returnsoft.recruitment.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;


/**
 * The persistent class for the training database table.
 * 
 */
@Entity
@Table(name="training")
public class Training implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	//@Column(name="id")
	private Long id;

	/*@Column(name="comment")
	private String comment;*/

	@MapsId
	@OneToOne
	@JoinColumn(name="id")
	private Interview interview;

	//bi-directional many-to-one association to Recruiter
	/*@ManyToOne
	@JoinColumn(name="trainer_id")
	private User trainer;*/
	
	//bi-directional many-to-one association to TrainingState
	@ManyToOne
	@JoinColumn(name="training_state_id")
	private TrainingState trainingState;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_at")
	private Date createdAt;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated_at")
	private Date updatedAt;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="updated_by")
	private User updatedBy;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="created_by")
	private User createdBy;
	
	
	/*@Temporal(TemporalType.TIMESTAMP)
	@Column(name="training_at")
	private Date trainingAt;*/
	
	//@OneToOne(cascade = CascadeType.ALL, optional = false, fetch = FetchType.EAGER, orphanRemoval = true)
	//@PrimaryKeyJoinColumn
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY,mappedBy="training")
	private Ojt ojt;
	
	
	/*@ManyToOne
	private Recruiter recruiter;*/
	

	public Training() {
	}

	

	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	/*public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}*/
	

	/*public Date getTrainingAt() {
		return trainingAt;
	}

	public void setTrainingAt(Date trainingAt) {
		this.trainingAt = trainingAt;
	}*/

	

	public TrainingState getTrainingState() {
		return this.trainingState;
	}

	public void setTrainingState(TrainingState trainingState) {
		this.trainingState = trainingState;
	}

	public Interview getInterview() {
		return interview;
	}

	public void setInterview(Interview interview) {
		this.interview = interview;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/*public User getTrainer() {
		return trainer;
	}

	public void setTrainer(User trainer) {
		this.trainer = trainer;
	}*/

	public Ojt getOjt() {
		return ojt;
	}

	public void setOjt(Ojt ojt) {
		this.ojt = ojt;
	}



	public Date getUpdatedAt() {
		return updatedAt;
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



	public User getCreatedBy() {
		return createdBy;
	}



	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	
	
	

}