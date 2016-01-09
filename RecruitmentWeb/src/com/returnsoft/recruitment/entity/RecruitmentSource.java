package com.returnsoft.recruitment.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the recruiment_source database table.
 * 
 */
@Entity
@Table(name="recruitment_source")
//@NamedQuery(name="RecruimentSource.findAll", query="SELECT r FROM RecruimentSource r")
public class RecruitmentSource implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String name;
	
	@Column(name = "is_job_fair")
	private Boolean isJobFair;
	
	@Column(name = "is_flier")
	private Boolean isFlier;
	
	@Column(name = "is_referred")
	private Boolean isReferred;


	public RecruitmentSource() {
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