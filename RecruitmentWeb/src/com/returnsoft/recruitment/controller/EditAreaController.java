package com.returnsoft.recruitment.controller;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.context.RequestContext;

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
	
	private Area areaSelected;
	
	/*private String stateSelected;*/
	
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
			
			areaSelected = areaService.findById(Integer.parseInt(areaId));
			
			if (areaSelected==null) {
				throw new Exception("No se encontró el área");
			}
			
			System.out.println("isActive:"+areaSelected.getIsActive());
			
			/*if (areaSelected.getIsActive()) {
				stateSelected="1";
			}else{
				stateSelected="0";
			}*/
			
			
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
		
		try {
			
			if (sessionBean == null || sessionBean.getUser() == null || sessionBean.getUser().getId() == null) {
				throw new UserLoggedNotFoundException();
			}
			
			/*if (stateSelected!=null && stateSelected.length()>0) {
				if (stateSelected.equals("1")) {
					areaSelected.setIsActive(Boolean.TRUE);	
				}else{
					areaSelected.setIsActive(Boolean.FALSE);
				}
			}*/
			
			areaSelected =areaService.edit(areaSelected);
			
			facesUtil.sendConfirmMessage("Se editó satisfactoriamente");
			
			RequestContext.getCurrentInstance().closeDialog(areaSelected);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getMessage());
		}
		
	}


	


	public Area getAreaSelected() {
		return areaSelected;
	}


	public void setAreaSelected(Area areaSelected) {
		this.areaSelected = areaSelected;
	}

	
	

}
