package com.returnsoft.resource.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the interview_state database table.
 * 
 */
@Entity
@Table(name="interview_state")
//@NamedQuery(name="InterviewState.findAll", query="SELECT i FROM InterviewState i")
public class InterviewState implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String name;
	
	@Column(name="is_pending")
	private Boolean isPending;
	
	@Column(name="is_goal")
	private Boolean isGoal;


	public InterviewState() {
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

	

}