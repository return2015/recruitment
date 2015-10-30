package com.returnsoft.resource.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "trainer")
//@NamedQuery(name = "Recruiter.findAll", query = "SELECT r FROM Recruiter r")
public class Trainer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name="user_id")
	private Integer userId;

	// bi-directional many-to-many association to Campaign
	@ManyToMany
	@JoinTable(name = "trainer_area", joinColumns = { @JoinColumn(name = "trainer_id") }, inverseJoinColumns = { @JoinColumn(name = "area_id") })
	private Set<Area> areas;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Set<Area> getAreas() {
		return areas;
	}

	public void setAreas(Set<Area> areas) {
		this.areas = areas;
	}

	
	
	

}
