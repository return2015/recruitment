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
public class SearchAreaController implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -174406964333832198L;
	
	@Inject
	private FacesUtil facesUtil;
	
	@Inject
	private SessionBean sessionBean;
	
	@EJB
	private AreaService areaService;
	
	private List<Area> areas;
	
	private Area areaSelected;
	
	public String initialize(){
		try {
			if (sessionBean == null || sessionBean.getUser() == null || sessionBean.getUser().getId() == null) {
				throw new UserLoggedNotFoundException();
			}
			
			areas = areaService.findAreasParentAll();
			
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
	
	public void edit(){
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

			/*return "edit_user?faces-redirect=true&userId="
					+ userSelected.getId();*/
			Map<String, List<String>> paramMap = new HashMap<String, List<String>>();
			ArrayList<String> paramList = new ArrayList<>();
			paramList.add(String.valueOf(areaSelected.getId()));
			paramMap.put("areaId", paramList);
			RequestContext.getCurrentInstance().openDialog("edit_area", options, paramMap);

		} catch (Exception e) {
			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getMessage());
			//return null;
		}
	}
	
	public void afterEdit(SelectEvent event){
		try {
			Area areaReturn = (Area) event.getObject();
			
			/*System.out.println("after.."+areaReturn.getUsername());
			System.out.println("after.."+userReturn.getFirstname());
			System.out.println("after.."+userReturn.getLastname());*/
			int i = 0;
			for (Area area : areas) {
				//Area areaF = sales.get(i);
				if (area.getId().equals(areaReturn.getId())) {
					areas.set(i, areaReturn);
					areaSelected = areaReturn;
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

	public List<Area> getAreas() {
		return areas;
	}

	public void setAreas(List<Area> areas) {
		this.areas = areas;
	}

	public Area getAreaSelected() {
		return areaSelected;
	}

	public void setAreaSelected(Area areaSelected) {
		this.areaSelected = areaSelected;
	}

	
	
	
	

}
