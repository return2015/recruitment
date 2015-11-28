package com.returnsoft.recruitment.entity;

import java.io.Serializable;
import java.util.Date;

public class RequirementDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Date period;
	private Date startTraining;
	private Date endTraining;
	private Date startOjt;
	private String area;
	private String recruiter;
	private Integer amount;
	
	private Integer amountStartTraining;
	private Integer amountEndTraining;
	private Integer amountStartOjt;
	private Integer amountEndOjt;
	//private Integer amountStartOjtLesser;
	//private Integer amountStartOjtGreater;
	
	private Integer amountOfRequirements;
	
	private Double startTrainingRequisition;
	private Double endowmentIndicator;
	private Double endowmentIndicatorReached;
	private Double rotationAllowed;
	private Double rotationAllowedReached;
	private Double rotationIndicator;
	private Double rotationIndicatorReached;
	private Double startOjtStartTraining;
	private Double endOjtRequisition;
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*public Double getStartTrainingRequisition() {
		System.out.println("Ingreso a getStartTrainingRequisition");
		System.out.println(amountStartTraining);
		System.out.println(amount);
		startTrainingRequisition=amountStartTraining.doubleValue()/amount.doubleValue();
		System.out.println(startTrainingRequisition);
		return startTrainingRequisition;
	}
	public Double getEndowmentIndicator() {
		System.out.println("Ingreso a getEndowmentIndicator");
		endowmentIndicator=(getStartTrainingRequisition()*100)/180;
		System.out.println(endowmentIndicator);
		return endowmentIndicator;
	}
	public Double getEndowmentIndicatorReached() {
		System.out.println("Ingreso a getEndowmentIndicatorReached");
		endowmentIndicatorReached=getEndowmentIndicator()*0.7;
		System.out.println(endowmentIndicatorReached);
		return endowmentIndicatorReached;
	}
	public Double getRotationAllowed() {
		rotationAllowed=amountStartTraining.doubleValue()*0.3;
		return rotationAllowed;
	}
	public Double getRotationAllowedReached() {
		rotationAllowedReached=amountStartTraining.doubleValue()-getRotationAllowed();
		return rotationAllowedReached;
	}
	public Double getRotationIndicator() {
		rotationIndicator=amountEndTraining.doubleValue()/getRotationAllowedReached();
		if (rotationIndicator.isNaN()) {
			rotationIndicator=0.0;
		}
		return rotationIndicator;
	}
	public Double getRotationIndicatorReached() {
		rotationIndicatorReached=getRotationIndicator()*0.1;
		return rotationIndicatorReached;
	}
	public Double getStartOjtStartTraining() {
		startOjtStartTraining=amountStartOjtLesser.doubleValue()/amountStartTraining.doubleValue();
		if (startOjtStartTraining.isNaN()) {
			startOjtStartTraining=0.0;
		}
		return startOjtStartTraining;
	}
	public Double getEndOjtRequisition() {
		endOjtRequisition=amountStartOjtGreater.doubleValue()/amount.doubleValue();
		return endOjtRequisition;
	}*/
	

	
	
	
	
	public Double getStartTrainingRequisition() {
		return startTrainingRequisition;
	}
	public void setStartTrainingRequisition(Double startTrainingRequisition) {
		this.startTrainingRequisition = startTrainingRequisition;
	}
	public Double getEndowmentIndicator() {
		return endowmentIndicator;
	}
	public void setEndowmentIndicator(Double endowmentIndicator) {
		this.endowmentIndicator = endowmentIndicator;
	}
	public Double getEndowmentIndicatorReached() {
		return endowmentIndicatorReached;
	}
	public void setEndowmentIndicatorReached(Double endowmentIndicatorReached) {
		this.endowmentIndicatorReached = endowmentIndicatorReached;
	}
	public Double getRotationAllowed() {
		return rotationAllowed;
	}
	public void setRotationAllowed(Double rotationAllowed) {
		this.rotationAllowed = rotationAllowed;
	}
	public Double getRotationAllowedReached() {
		return rotationAllowedReached;
	}
	public void setRotationAllowedReached(Double rotationAllowedReached) {
		this.rotationAllowedReached = rotationAllowedReached;
	}
	public Double getRotationIndicator() {
		return rotationIndicator;
	}
	public void setRotationIndicator(Double rotationIndicator) {
		this.rotationIndicator = rotationIndicator;
	}
	public Double getRotationIndicatorReached() {
		return rotationIndicatorReached;
	}
	public void setRotationIndicatorReached(Double rotationIndicatorReached) {
		this.rotationIndicatorReached = rotationIndicatorReached;
	}
	public Double getStartOjtStartTraining() {
		return startOjtStartTraining;
	}
	public void setStartOjtStartTraining(Double startOjtStartTraining) {
		this.startOjtStartTraining = startOjtStartTraining;
	}
	public Double getEndOjtRequisition() {
		return endOjtRequisition;
	}
	public void setEndOjtRequisition(Double endOjtRequisition) {
		this.endOjtRequisition = endOjtRequisition;
	}
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
	public Integer getAmountStartTraining() {
		return amountStartTraining;
	}
	public void setAmountStartTraining(Integer amountStartTraining) {
		this.amountStartTraining = amountStartTraining;
	}
	public Integer getAmountEndTraining() {
		return amountEndTraining;
	}
	public void setAmountEndTraining(Integer amountEndTraining) {
		this.amountEndTraining = amountEndTraining;
	}
	
	public Integer getAmountStartOjt() {
		return amountStartOjt;
	}
	public void setAmountStartOjt(Integer amountStartOjt) {
		this.amountStartOjt = amountStartOjt;
	}
	public Integer getAmountOfRequirements() {
		return amountOfRequirements;
	}
	public void setAmountOfRequirements(Integer amountOfRequirements) {
		this.amountOfRequirements = amountOfRequirements;
	}
	public Integer getAmountEndOjt() {
		return amountEndOjt;
	}
	public void setAmountEndOjt(Integer amountEndOjt) {
		this.amountEndOjt = amountEndOjt;
	}
	
	
	
	

}
