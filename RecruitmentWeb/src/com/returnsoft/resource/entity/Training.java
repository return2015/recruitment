package com.returnsoft.resource.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;


/**
 * The persistent class for the training database table.
 * 
 */
@Entity
@Table(name="training")
//@NamedQuery(name="Training.findAll", query="SELECT t FROM Training t")
public class Training implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String comment;
	
	@ManyToOne
	private Interview interview;

	//bi-directional many-to-one association to Recruiter
	@ManyToOne
	@JoinColumn(name="trainer_id")
	private Trainer trainer;
	
	//bi-directional many-to-one association to TrainingState
	@ManyToOne
	@JoinColumn(name="training_state_id")
	private TrainingState trainingState;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_at")
	private Date createdAt;
	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="training_at")
	private Date trainingAt;
	
	@ManyToOne
	private Recruiter recruiter;
	

	public Training() {
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
	

	public Date getTrainingAt() {
		return trainingAt;
	}

	public void setTrainingAt(Date trainingAt) {
		this.trainingAt = trainingAt;
	}

	

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

	public Trainer getTrainer() {
		return trainer;
	}

	public void setTrainer(Trainer trainer) {
		this.trainer = trainer;
	}

	public Recruiter getRecruiter() {
		return recruiter;
	}

	public void setRecruiter(Recruiter recruiter) {
		this.recruiter = recruiter;
	}
	
	

}