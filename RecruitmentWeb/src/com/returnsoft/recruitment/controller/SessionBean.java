package com.returnsoft.recruitment.controller;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.returnsoft.recruitment.entity.User;

@ManagedBean
@SessionScoped
public class SessionBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//private Integer userId;
	//private Integer recruiterId;
	//private Integer trainerId;
	private Boolean isAdmin;
	//private String name;
	//private String username;
	
	private User user;

	

	

	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	


}
