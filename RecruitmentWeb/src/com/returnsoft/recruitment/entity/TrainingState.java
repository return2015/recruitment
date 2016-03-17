package com.returnsoft.recruitment.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the training_state database table.
 * 
 */
@Entity
@Table(name="training_state")
//@NamedQuery(name="TrainingState.findAll", query="SELECT t FROM TrainingState t")
public class TrainingState implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	@Column(name="name")
	private String name;
	
	@Column(name="is_pending")
	private Boolean isPending;
	
	@Column(name="is_goal")
	private Boolean isGoal;
	
	@Column(name="is_ready")
	private Boolean isReady;

	public TrainingState() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getIsPending() {
		return isPending;
	}

	public void setIsPending(Boolean isPending) {
		this.isPending = isPending;
	}

	public Boolean getIsGoal() {
		return isGoal;
	}

	public void setIsGoal(Boolean isGoal) {
		this.isGoal = isGoal;
	}

	public Boolean getIsReady() {
		return isReady;
	}

	public void setIsReady(Boolean isReady) {
		this.isReady = isReady;
	}

	
}