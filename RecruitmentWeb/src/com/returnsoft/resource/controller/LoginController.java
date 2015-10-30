package com.returnsoft.resource.controller;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.naming.InitialContext;


import com.returnsoft.resource.exception.RecruitmentException;
import com.returnsoft.security.dto.UserDto;
import com.returnsoft.security.exception.SecurityExcepcion;
import com.returnsoft.security.service.UserBean;
import com.returnsoft.security.service.UserInterface;

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
	private UserInterface userService;

	/*private final String PERMISSION_RECRUITER = "recruitment_recruiter";
	private final String PERMISSION_TRAINER = "recruitment_trainer";
	private final String PERMISSION_ADMIN = "recruitment_admin";*/

	//private final List<String> PERMISSIONS = new ArrayList<String>();

	public LoginController() {
		/*PERMISSIONS.add(PERMISSION_ADMIN);
		PERMISSIONS.add(PERMISSION_TRAINER);
		PERMISSIONS.add(PERMISSION_RECRUITER);*/
	}

	public String doLogin() {

		try {
			// INICIALIZA BEAN
			/*UserInterface userService = (UserInterface) new InitialContext(
					Util.getInitProperties())
					.lookup("java:global/SecurityEAR/SecurityEJB/UserBean"
							+ "!com.returnsoft.security.service.UserInterface");*/

			// BUSCA USUARIO Y CLAVE
			UserDto user = userService.doLogin(username, password);

			SessionBean sessionBean = new SessionBean();
			sessionBean.setName(user.getUsername());
			sessionBean.setUsername(username);
			sessionBean.setUserId(user.getId());

			sessionBean.setIsAdmin(false);
			sessionBean.setRecruiterId(user.getId());
			
			
	
			FacesContext.getCurrentInstance().getExternalContext()
					.getSessionMap().put("sessionBean", sessionBean);

			return "resource.xhtml";

		} catch (Exception e) {
			if (!(e instanceof RecruitmentException)
					&& !(e instanceof SecurityExcepcion)) {
				e.printStackTrace();
			}
			FacesMessage msg = new FacesMessage(e.getMessage(), e.getMessage());
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return null;
		}

	}

	public String doLogout() {
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.invalidateSession();

			return "login.xhtml";
		} catch (Exception e) {
			if (!(e instanceof RecruitmentException)
					&& !(e instanceof SecurityExcepcion)) {
				e.printStackTrace();
			}
			FacesMessage msg = new FacesMessage(e.getMessage(), e.getMessage());
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, msg);
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
