package com.returnsoft.recruitment.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="requirement_user")
@IdClass(RequirementUserPK.class)
public class RequirementUser {
	
	@Id
	@Column(name="requirement_id")
	private Integer requirementId;
	
	@Id
	@Column(name="user_id")
	private Integer userId;
	
	@Column(name="amount")
	private Integer amount;
	
	@OneToMany(mappedBy="requirementUser",fetch=FetchType.LAZY)
	private List<Interview> interviews;
	
	@ManyToOne
	@PrimaryKeyJoinColumn(name="requirement_id", referencedColumnName="id")
	private Requirement requirement;
	
	@ManyToOne
	@PrimaryKeyJoinColumn(name="user_id", referencedColumnName="id")
	private User user;

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Integer getRequirementId() {
		return requirementId;
	}

	public void setRequirementId(Integer requirementId) {
		this.requirementId = requirementId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Requirement getRequirement() {
		return requirement;
	}

	public void setRequirement(Requirement requirement) {
		this.requirement = requirement;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Interview> getInterviews() {
		return interviews;
	}

	public void setInterviews(List<Interview> interviews) {
		this.interviews = interviews;
	}
	
	
}
