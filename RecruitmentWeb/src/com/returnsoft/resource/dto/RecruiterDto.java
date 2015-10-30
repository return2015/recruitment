package com.returnsoft.resource.dto;

import java.io.Serializable;
import java.util.List;





public class RecruiterDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer userId;
	private String firstname;
	private String lastname;
	private String documentNumber;
	private String areasList;
	private String subAreasList;
	
	
	
	private List<AreaDto> areas;
	private List<AreaDto> subAreas;
	
	//private AreaDto area;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	
	/*public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}*/
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getDocumentNumber() {
		return documentNumber;
	}
	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}
	public String getAreasList() {
		return areasList;
	}
	public void setAreasList(String areasList) {
		this.areasList = areasList;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public List<AreaDto> getAreas() {
		return areas;
	}
	public void setAreas(List<AreaDto> areas) {
		this.areas = areas;
	}
	public List<AreaDto> getSubAreas() {
		return subAreas;
	}
	public void setSubAreas(List<AreaDto> subAreas) {
		this.subAreas = subAreas;
	}
	public String getSubAreasList() {
		return subAreasList;
	}
	public void setSubAreasList(String subAreasList) {
		this.subAreasList = subAreasList;
	}
	
	
	
	

}
