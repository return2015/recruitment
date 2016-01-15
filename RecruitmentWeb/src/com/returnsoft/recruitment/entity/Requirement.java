package com.returnsoft.recruitment.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
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

import com.returnsoft.recruitment.converter.MonthConverter;
import com.returnsoft.recruitment.converter.YearConverter;
import com.returnsoft.recruitment.enumeration.MonthEnum;
import com.returnsoft.recruitment.enumeration.YearEnum;

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
	
	/*@Temporal(TemporalType.TIMESTAMP)
	@Column(name="period")
	private Date period;*/
	
	@Column(name="code")
	private String code;
	
	@Column(name = "period_month")
	@Convert(converter = MonthConverter.class)
	private MonthEnum periodMonth;
	
	@Column(name = "period_year")
	@Convert(converter = YearConverter.class)
	private YearEnum periodYear;
	
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
	
	@OneToMany(mappedBy="requirement",cascade= CascadeType.ALL,orphanRemoval=true)
	 private List<RequirementUser> users;
	
	@Column(name = "is_active")
	private Boolean isActive;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at")
	private Date createdAt;
	
	@ManyToOne
	@JoinColumn(name = "created_by")
	private User createdBy;
	
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
	public MonthEnum getPeriodMonth() {
		return periodMonth;
	}

	public void setPeriodMonth(MonthEnum periodMonth) {
		this.periodMonth = periodMonth;
	}

	public YearEnum getPeriodYear() {
		return periodYear;
	}

	public void setPeriodYear(YearEnum periodYear) {
		this.periodYear = periodYear;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	



	
	
	
	

}
