package com.returnsoft.recruitment.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

import org.primefaces.context.RequestContext;

import com.returnsoft.recruitment.entity.Area;
import com.returnsoft.recruitment.exception.UserLoggedNotFoundException;
import com.returnsoft.recruitment.service.AreaService;
import com.returnsoft.recruitment.util.FacesUtil;
import com.returnsoft.recruitment.util.SessionBean;

@ManagedBean
@ViewScoped
public class EditSubAreaController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5616589889978914173L;

	@Inject
	private FacesUtil facesUtil;

	@Inject
	private SessionBean sessionBean;

	@EJB
	private AreaService areaService;

	private Area subAreaSelected;

	private List<SelectItem> areas;
	private String areaSelected;

	private Boolean state;

	public String initialize() {
		try {
			if (sessionBean == null || sessionBean.getUser() == null || sessionBean.getUser().getId() == null) {
				throw new UserLoggedNotFoundException();
			}

			String subAreaId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
					.get("subAreaId");

			if (subAreaId == null || subAreaId.length() == 0) {
				throw new Exception("No se envío el sub-área");
			}

			System.out.println("subAreaId:" + subAreaId);

			subAreaSelected = areaService.findById(Integer.parseInt(subAreaId));

			if (subAreaSelected == null) {
				throw new Exception("No se encontró el sub-área");
			}
			
			if (subAreaSelected.getArea() ==null) {
				throw new Exception("No se encontró área");
			}
			
			areaSelected = subAreaSelected.getArea().getId().toString();
			
			
			List<Area> areasDto = areaService.findAreasParentAll();
			areas = new ArrayList<SelectItem>();
			for (Area areaDto : areasDto) {
				SelectItem item = new SelectItem();
				item.setValue(areaDto.getId().toString());
				item.setLabel(areaDto.getName());
				areas.add(item);
			}
			

			/*
			 * if (subAreaSelected.getIsActive()) { stateSelected="1"; }else{
			 * stateSelected="0"; }
			 */

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

	public void edit() {

		try {

			if (sessionBean == null || sessionBean.getUser() == null || sessionBean.getUser().getId() == null) {
				throw new UserLoggedNotFoundException();
			}

			/*
			 * if (stateSelected!=null && stateSelected.length()>0) { if
			 * (stateSelected.equals("1")) {
			 * areaSelected.setIsActive(Boolean.TRUE); }else{
			 * areaSelected.setIsActive(Boolean.FALSE); } }
			 */
			
			if (areaSelected!=null && areaSelected.length()>0) {
				Integer areaId = Integer.parseInt(areaSelected);
				Area area = new Area();
				area.setId(areaId);
				subAreaSelected.setArea(area);
			}

			subAreaSelected = areaService.edit(subAreaSelected);

			facesUtil.sendConfirmMessage("Se editó satisfactoriamente");

			RequestContext.getCurrentInstance().closeDialog(subAreaSelected);

		} catch (Exception e) {
			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getMessage());
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

	public Boolean getState() {
		return state;
	}

	public void setState(Boolean state) {
		this.state = state;
	}

}
