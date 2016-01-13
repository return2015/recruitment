package com.returnsoft.recruitment.controller;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import com.returnsoft.recruitment.entity.Area;
import com.returnsoft.recruitment.exception.UserLoggedNotFoundException;
import com.returnsoft.recruitment.service.AreaService;
import com.returnsoft.recruitment.util.FacesUtil;
import com.returnsoft.recruitment.util.SessionBean;

@ManagedBean
@ViewScoped
public class EditAreaController implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -959482898829565581L;
	
	
	@Inject
	private FacesUtil facesUtil;
	
	@Inject
	private SessionBean sessionBean;
	
	@EJB
	private AreaService areaService;
	
	private Area entitySelected;
	
	private String stateSelected;
	
	//private List<SelectItem> states;
	
	public String initialize(){
		try {
			if (sessionBean == null || sessionBean.getUser() == null || sessionBean.getUser().getId() == null) {
				throw new UserLoggedNotFoundException();
			}
			
			String areaId = FacesContext.getCurrentInstance()
					.getExternalContext().getRequestParameterMap()
					.get("areaId");
			
			if (areaId==null || areaId.length()==0) {
				throw new Exception("No se envío el área");
			}

			System.out.println("areaId:" + areaId);
			
			entitySelected = areaService.findById(Integer.parseInt(areaId));
			
			if (entitySelected==null) {
				throw new Exception("No se encontró el área");
			}
			
			if (entitySelected.getIsActive()) {
				stateSelected="1";
			}else{
				stateSelected="0";
			}
			
			
			return null;
		} catch (UserLoggedNotFoundException e) {
			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getClass().getSimpleName(), e.getMessage());
			return "login.xhtml?faces-redirect=true";
		} catch (Exception e) {
			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getClass().getSimpleName(), e.getMessage());
			return null;
		}
	}
	
	
	public void edit(){
		
	}


	public Area getEntitySelected() {
		return entitySelected;
	}


	public void setEntitySelected(Area entitySelected) {
		this.entitySelected = entitySelected;
	}


	public String getStateSelected() {
		return stateSelected;
	}


	public void setStateSelected(String stateSelected) {
		this.stateSelected = stateSelected;
	}
	
	
	

}
