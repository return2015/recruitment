package com.returnsoft.recruitment.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "name")
	private String name;

	// bi-directional many-to-one association to Department
	@ManyToOne
	@JoinColumn(name="area_id")
	private Area area;
	
	@ManyToOne
	@JoinColumn(name="recruiter_id")
	private User recruiter;

	// bi-directional many-to-many association to Agent
	@ManyToMany(mappedBy = "areas")
	private List<User> recruiters;
	
	/*@ManyToMany(mappedBy = "subAreas")
	private Set<Recruiter> subRecruiters;*/
	
	/*@ManyToMany(mappedBy = "areas")
	private Set<Trainer> trainers;*/

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

	public User getRecruiter() {
		return recruiter;
	}

	public void setRecruiter(User recruiter) {
		this.recruiter = recruiter;
	}

	public List<User> getRecruiters() {
		return recruiters;
	}

	public void setRecruiters(List<User> recruiters) {
		this.recruiters = recruiters;
	}

	

	
	
	

}
