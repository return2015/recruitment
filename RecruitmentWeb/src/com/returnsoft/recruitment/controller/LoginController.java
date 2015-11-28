package com.returnsoft.recruitment.controller;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;


import com.returnsoft.recruitment.exception.SessionTypeInvalidException;
import com.returnsoft.recruitment.entity.User;

import com.returnsoft.recruitment.service.UserService;
import com.returnsoft.recruitment.util.FacesUtil;


@ManagedBean
@RequestScoped
public class LoginController implements Serializable {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	
	@EJB
	private UserService userService;
	
	private FacesUtil facesUtil;

	public LoginController() {
		
		facesUtil = new FacesUtil();
	}

	public String doLogin() {

		try {
			
			// BUSCA USUARIO Y CLAVE
			User user = userService.doLogin(username, password);
			
			SessionBean sessionBean = null;
			
			switch (user.getUserType()) {
			case ADMIN:
				sessionBean = new SessionBean();
				sessionBean.setUser(user);
				sessionBean.setIsAdmin(true);
				FacesContext.getCurrentInstance().getExternalContext()
				.getSessionMap().put("sessionBean", sessionBean);
				return "home?faces-redirect=true";
			case AGENT:
				sessionBean = new SessionBean();
				sessionBean.setUser(user);
				FacesContext.getCurrentInstance().getExternalContext()
				.getSessionMap().put("sessionBean", sessionBean);
				return "home?faces-redirect=true";
				
			default:
				throw new SessionTypeInvalidException();
			}



		} catch (Exception e) {

			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getClass().getSimpleName(),
					e.getMessage());
			return null;
		}

	}

	public String doLogout() {
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.invalidateSession();

			return "login.xhtml";
		} catch (Exception e) {

			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getClass().getSimpleName(),
					e.getMessage());
			return null;
		}

	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
