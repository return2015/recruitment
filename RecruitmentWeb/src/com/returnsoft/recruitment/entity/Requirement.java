package com.returnsoft.recruitment.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="requirement")
public class Requirement implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="period")
	private Date period;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="start_training")
	private Date startTraining;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="end_training")
	private Date endTraining;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="start_ojt")
	private Date startOjt;
	
	@Column(name="amount")
	private Integer amount;

	@ManyToOne
	@JoinColumn(name="area_id")
	private Area area;
	
	@OneToMany(mappedBy="requirement")
	 private List<RequirementUser> users;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getPeriod() {
		return period;
	}

	public void setPeriod(Date period) {
		this.period = period;
	}

	public Date getStartTraining() {
		return startTraining;
	}

	public void setStartTraining(Date startTraining) {
		this.startTraining = startTraining;
	}

	public Date getEndTraining() {
		return endTraining;
	}

	public void setEndTraining(Date endTraining) {
		this.endTraining = endTraining;
	}

	public Date getStartOjt() {
		return startOjt;
	}

	public void setStartOjt(Date startOjt) {
		this.startOjt = startOjt;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public List<RequirementUser> getUsers() {
		return users;
	}

	public void setUsers(List<RequirementUser> users) {
		this.users = users;
	}

	



	
	
	
	

}
