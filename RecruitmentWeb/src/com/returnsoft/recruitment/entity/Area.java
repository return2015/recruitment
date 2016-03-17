package com.returnsoft.recruitment.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
	
	@Column(name = "code")
	private String code;

	// bi-directional many-to-one association to Department
	@ManyToOne
	@JoinColumn(name="area_id")
	private Area area;
	
	@ManyToOne
	@JoinColumn(name="coordinator_id")
	private User coordinator;

	// bi-directional many-to-many association to Agent
	/*@ManyToMany(mappedBy = "areas")
	private List<User> recruiters;*/
	
	@Column(name = "is_active")
	private Boolean isActive;
	
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

	

	public User getCoordinator() {
		return coordinator;
	}

	public void setCoordinator(User coordinator) {
		this.coordinator = coordinator;
	}

	

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	

	
	
	

}
