package com.returnsoft.resource.dto;

import java.io.Serializable;

public class AreaDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private AreaDto area;
	private RecruiterDto recruiter;
	
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
	public AreaDto getArea() {
		return area;
	}
	public void setArea(AreaDto area) {
		this.area = area;
	}
	public RecruiterDto getRecruiter() {
		return recruiter;
	}
	public void setRecruiter(RecruiterDto recruiter) {
		this.recruiter = recruiter;
	}
	
	
	

}
