package com.returnsoft.recruitment.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import com.returnsoft.recruitment.entity.Area;
import com.returnsoft.recruitment.exception.UserLoggedNotFoundException;
import com.returnsoft.recruitment.service.AreaService;
import com.returnsoft.recruitment.util.FacesUtil;
import com.returnsoft.recruitment.util.SessionBean;

@ManagedBean
@ViewScoped
public class SearchSubAreaController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9194439553456531094L;

	@Inject
	private FacesUtil facesUtil;

	@Inject
	private SessionBean sessionBean;

	@EJB
	private AreaService areaService;

	/*
	 * private List<Area> areas; private String areaSelected;
	 */

	private List<Area> subAreas;
	private Area subAreaSelected;

	public String initialize() {
		try {
			if (sessionBean == null || sessionBean.getUser() == null || sessionBean.getUser().getId() == null) {
				throw new UserLoggedNotFoundException();
			}

			subAreas = areaService.findAreasChildAll();

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

			if (sessionBean == null || sessionBean.getUser() == null || sessionBean.getUser().getId() < 1) {
				throw new UserLoggedNotFoundException();
			}

			System.out.println("Ingreso a edit");

			Map<String, Object> options = new HashMap<String, Object>();
			options.put("modal", true);
			options.put("draggable", true);
			options.put("resizable", true);
			options.put("contentHeight", 200);
			options.put("contentWidth", 400);

			/*
			 * return "edit_user?faces-redirect=true&userId=" +
			 * userSelected.getId();
			 */
			Map<String, List<String>> paramMap = new HashMap<String, List<String>>();
			ArrayList<String> paramList = new ArrayList<>();
			paramList.add(String.valueOf(subAreaSelected.getId()));
			paramMap.put("subAreaId", paramList);
			RequestContext.getCurrentInstance().openDialog("edit_subarea", options, paramMap);

		} catch (Exception e) {
			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getMessage());
			// return null;
		}
	}

	public void afterEdit(SelectEvent event) {
		try {
			Area subAreaReturn = (Area) event.getObject();
			
			/*System.out.println("after.."+areaReturn.getUsername());
			System.out.println("after.."+userReturn.getFirstname());
			System.out.println("after.."+userReturn.getLastname());*/
			int i = 0;
			for (Area subArea : subAreas) {
				//Area areaF = sales.get(i);
				if (subArea.getId().equals(subAreaReturn.getId())) {
					subAreas.set(i, subAreaReturn);
					subAreaSelected = subAreaReturn;
					break;
				}
				i++;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getMessage());
			//return null;
		}
	}

	public String add() {
		return null;
	}

	public List<Area> getSubAreas() {
		return subAreas;
	}

	public void setSubAreas(List<Area> subAreas) {
		this.subAreas = subAreas;
	}

	public Area getSubAreaSelected() {
		return subAreaSelected;
	}

	public void setSubAreaSelected(Area subAreaSelected) {
		this.subAreaSelected = subAreaSelected;
	}

}
