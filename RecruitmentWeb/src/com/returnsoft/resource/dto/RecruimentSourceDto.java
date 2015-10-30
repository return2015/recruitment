package com.returnsoft.resource.dto;

import java.io.Serializable;


public class RecruimentSourceDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private Boolean isJobFair;
	private Boolean isFlier;
	private Boolean isReferred;
	
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
	public Boolean getIsJobFair() {
		return isJobFair;
	}
	public void setIsJobFair(Boolean isJobFair) {
		this.isJobFair = isJobFair;
	}
	public Boolean getIsFlier() {
		return isFlier;
	}
	public void setIsFlier(Boolean isFlier) {
		this.isFlier = isFlier;
	}
	public Boolean getIsReferred() {
		return isReferred;
	}
	public void setIsReferred(Boolean isReferred) {
		this.isReferred = isReferred;
	}
	
	

}
