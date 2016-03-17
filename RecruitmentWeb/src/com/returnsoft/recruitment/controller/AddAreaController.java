package com.returnsoft.recruitment.controller;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import com.returnsoft.recruitment.entity.Area;
import com.returnsoft.recruitment.exception.UserLoggedNotFoundException;
import com.returnsoft.recruitment.service.AreaService;
import com.returnsoft.recruitment.util.FacesUtil;
import com.returnsoft.recruitment.util.SessionBean;
@ManagedBean
@ViewScoped
public class AddAreaController  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7507539457186341711L;
	
	
	@Inject
	private FacesUtil facesUtil;
	
	@Inject
	private SessionBean sessionBean;
	
	@EJB
	private AreaService areaService;
	
	private Area entitySelected;
	
	public String initialize(){
		try {
			if (sessionBean == null || sessionBean.getUser() == null || sessionBean.getUser().getId() == null) {
				throw new UserLoggedNotFoundException();
			}
			
			entitySelected=new Area();
			
			return null;
		} catch (UserLoggedNotFoundException e) {
			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getMessage());
			return "login.xhtml?faces-redirect=true";
		} catch (Exception e) {
			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getMessage());
			return null;
		}
	}
	
	
	public String add(){
		try {
			entitySelected.setIsActive(Boolean.TRUE);
			areaService.add(entitySelected);
			facesUtil.sendConfirmMessage("Se creó satisfactoriamente.");
			return "search_area.xhtml?faces-redirect=true";
		} catch (Exception e) {
			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getMessage());
			return null;
		}
	}

	public Area getEntitySelected() {
		return entitySelected;
	}

	public void setEntitySelected(Area entitySelected) {
		this.entitySelected = entitySelected;
	}
	

}
