package com.returnsoft.resource.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="area")
public class Area implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	private String name;

	// bi-directional many-to-one association to Department
	@ManyToOne
	private Area area;
	
	@ManyToOne
	private Recruiter recruiter;

	// bi-directional many-to-many association to Agent
	@ManyToMany(mappedBy = "areas")
	private Set<Recruiter> recruiters;
	
	@ManyToMany(mappedBy = "subAreas")
	private Set<Recruiter> subRecruiters;
	
	@ManyToMany(mappedBy = "areas")
	private Set<Trainer> trainers;

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

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public Set<Recruiter> getRecruiters() {
		return recruiters;
	}

	public void setRecruiters(Set<Recruiter> recruiters) {
		this.recruiters = recruiters;
	}

	public Set<Trainer> getTrainers() {
		return trainers;
	}

	public void setTrainers(Set<Trainer> trainers) {
		this.trainers = trainers;
	}

	public Set<Recruiter> getSubRecruiters() {
		return subRecruiters;
	}

	public void setSubRecruiters(Set<Recruiter> subRecruiters) {
		this.subRecruiters = subRecruiters;
	}

	public Recruiter getRecruiter() {
		return recruiter;
	}

	public void setRecruiter(Recruiter recruiter) {
		this.recruiter = recruiter;
	}
	
	
	

}
