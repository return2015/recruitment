package com.returnsoft.recruitment.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

import com.returnsoft.recruitment.entity.Area;
import com.returnsoft.recruitment.exception.UserLoggedNotFoundException;
import com.returnsoft.recruitment.service.AreaService;
import com.returnsoft.recruitment.util.FacesUtil;
import com.returnsoft.recruitment.util.SessionBean;

@ManagedBean
@ViewScoped
public class AddSubAreaController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1742975189967781995L;
	
	
	@Inject
	private FacesUtil facesUtil;
	
	@Inject
	private SessionBean sessionBean;
	
	@EJB
	private AreaService areaService;
	
	private Area subAreaSelected;
	
	
	private List<SelectItem> areas;
	private String areaSelected;
	
	
	public String initialize(){
		try {
			if (sessionBean == null || sessionBean.getUser() == null || sessionBean.getUser().getId() == null) {
				throw new UserLoggedNotFoundException();
			}
			
			subAreaSelected=new Area();
			
			List<Area> areasDto = areaService.findAreasParentAll();
			areas = new ArrayList<SelectItem>();
			for (Area areaDto : areasDto) {
				SelectItem item = new SelectItem();
				item.setValue(areaDto.getId().toString());
				item.setLabel(areaDto.getName());
				areas.add(item);
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
	
	public String add(){
		try {
			
			if (areaSelected!=null && areaSelected.length()>0) {
				Integer areaId = Integer.parseInt(areaSelected);
				Area area = new Area();
				area.setId(areaId);
				subAreaSelected.setArea(area);
				subAreaSelected.setIsActive(Boolean.TRUE);
				areaService.add(subAreaSelected);
				facesUtil.sendConfirmMessage("Se creó satisfactoriamente.");
			}
			
			return "search_subarea.xhtml?faces-redirect=true";
			
		} catch (Exception e) {
			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getMessage());
			return null;
		}
	}


	public Area getSubAreaSelected() {
		return subAreaSelected;
	}


	public void setSubAreaSelected(Area subAreaSelected) {
		this.subAreaSelected = subAreaSelected;
	}


	


	public List<SelectItem> getAreas() {
		return areas;
	}

	public void setAreas(List<SelectItem> areas) {
		this.areas = areas;
	}

	public String getAreaSelected() {
		return areaSelected;
	}


	public void setAreaSelected(String areaSelected) {
		this.areaSelected = areaSelected;
	}
	

}
