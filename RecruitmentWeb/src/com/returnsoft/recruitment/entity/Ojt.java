package com.returnsoft.recruitment.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

/**
 * The persistent class for the ojt database table.
 * 
 */
@Entity
@Table(name="ojt")
public class Ojt implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	//@Column(name="id")
	private Long id;

	/*@Column(name="comment")
	private String comment;*/

	@MapsId
	@OneToOne
	@JoinColumn(name="id")
	private Training training;
	
	
	// bi-directional many-to-one association to OjtState
	@ManyToOne
	@JoinColumn(name = "ojt_state_id")
	private OjtState ojtState;

	// bi-directional many-to-one association to Recruiter
	/*@ManyToOne
	@JoinColumn(name = "recruiter_id")
	private Recruiter recruiter;*/

	/*@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ojt_at")
	private Date ojtAt;*/
	
	/*@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ended_at")
	private Date endedAt;*/

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at")
	private Date createdAt;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_at")
	private Date updatedAt;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="updated_by")
	private User updatedBy;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="created_by")
	private User createdBy;

	public Ojt() {
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

	

	public OjtState getOjtState() {
		return this.ojtState;
	}

	public void setOjtState(OjtState ojtState) {
		this.ojtState = ojtState;
	}

	public Training getTraining() {
		return training;
	}

	public void setTraining(Training training) {
		this.training = training;
	}


	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
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

	/*public Date getOjtAt() {
		return ojtAt;
	}

	public void setOjtAt(Date ojtAt) {
		this.ojtAt = ojtAt;
	}*/

	/*public Date getEndedAt() {
		return endedAt;
	}

	public void setEndedAt(Date endedAt) {
		this.endedAt = endedAt;
	}*/
	
	

}