package com.returnsoft.resource.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

/**
 * The persistent class for the ojt database table.
 * 
 */
@Entity
@Table(name="ojt")
//@NamedQuery(name = "Ojt.findAll", query = "SELECT o FROM Ojt o")
public class Ojt implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String comment;

	@ManyToOne
	private Training training;

	// bi-directional many-to-one association to OjtState
	@ManyToOne
	@JoinColumn(name = "ojt_state_id")
	private OjtState ojtState;

	// bi-directional many-to-one association to Recruiter
	@ManyToOne
	@JoinColumn(name = "recruiter_id")
	private Recruiter recruiter;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ojt_at")
	private Date ojtAt;
	
	/*@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ended_at")
	private Date endedAt;*/

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at")
	private Date createdAt;

	public Ojt() {
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

	

	public Recruiter getRecruiter() {
		return recruiter;
	}

	public void setRecruiter(Recruiter recruiter) {
		this.recruiter = recruiter;
	}

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

	public Date getOjtAt() {
		return ojtAt;
	}

	public void setOjtAt(Date ojtAt) {
		this.ojtAt = ojtAt;
	}

	/*public Date getEndedAt() {
		return endedAt;
	}

	public void setEndedAt(Date endedAt) {
		this.endedAt = endedAt;
	}*/
	
	

}