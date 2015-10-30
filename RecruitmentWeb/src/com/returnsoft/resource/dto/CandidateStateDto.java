package com.returnsoft.resource.dto;

import java.io.Serializable;

public class CandidateStateDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private Boolean isPending;
	private Boolean isGoal;
	private Boolean state;
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
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

	public Boolean getState() {
		return state;
	}

	public void setState(Boolean state) {
		this.state = state;
	}

	
	

}