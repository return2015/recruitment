package com.returnsoft.resource.dto;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

public class OjtDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Date createdAt;
	private String comment;
	private TrainingDto training;
	private OjtStateDto ojtState;
	private Date ojtAt;
	//private Date endedAt;
	private RecruiterDto recruiter;
	//private Integer numberOfDays;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public TrainingDto getTraining() {
		return training;
	}
	public void setTraining(TrainingDto training) {
		this.training = training;
	}
	public OjtStateDto getOjtState() {
		return ojtState;
	}
	public void setOjtState(OjtStateDto ojtState) {
		this.ojtState = ojtState;
	}
	public RecruiterDto getRecruiter() {
		return recruiter;
	}
	public void setRecruiter(RecruiterDto recruiter) {
		this.recruiter = recruiter;
	}
	public Date getOjtAt() {
		return ojtAt;
	}
	public void setOjtAt(Date ojtAt) {
		this.ojtAt = ojtAt;
	}
	/*public Date getEndedAt() {
		return endedAt;
	}
	public void setEndedAt(Date endedAt) {
		this.endedAt = endedAt;
	}*/
	/*public Integer getNumberOfDays() {
		if (ojtAt!=null && endedAt!=null) {
			DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
			String ojtAtString = df.format(ojtAt);
			try {
				ojtAt = df.parse(ojtAtString);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String endedAtString = df.format(endedAt);
			try {
			endedAt = df.parse(endedAtString);
			}
			catch (ParseException ex) {
			}
			long fechaInicialMs = ojtAt.getTime();
			long fechaFinalMs = endedAt.getTime();
			long diferencia = fechaFinalMs - fechaInicialMs;
			double dias = Math.floor(diferencia / (1000 * 60 * 60 * 24));
			return ( (int) dias);
		}else{
			System.out.println("una de las fechas es nula.");
			return 0;
		}
		
	}
	public void setNumberOfDays(Integer numberOfDays) {
		this.numberOfDays = numberOfDays;
	}*/
	
	
	

}
