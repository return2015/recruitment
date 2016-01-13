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

import com.returnsoft.recruitment.entity.Area;
import com.returnsoft.recruitment.entity.User;
import com.returnsoft.recruitment.enumeration.UserTypeEnum;
import com.returnsoft.recruitment.exception.UserLoggedNotFoundException;
import com.returnsoft.recruitment.service.AreaService;
import com.returnsoft.recruitment.service.UserService;
import com.returnsoft.recruitment.util.FacesUtil;
import com.returnsoft.recruitment.util.SessionBean;
@ManagedBean
@ViewScoped
public class EditUserController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4090995353390576152L;
	
	@Inject
	private FacesUtil facesUtil;

	@Inject
	private SessionBean sessionBean;
	
	private List<SelectItem> userTypes;
	private String userTypeSelected;

	private User userSelected;

	private List<SelectItem> areas;
	private String areaSelected;

	private List<SelectItem> subAreas;
	private List<String> subAreasSelected;

	@EJB
	private AreaService areaService;

	@EJB
	private UserService userService;
	
	
	
	
	
	public String initialize() {
		try {

			if (sessionBean == null || sessionBean.getUser() == null || sessionBean.getUser().getId() == null) {
				throw new UserLoggedNotFoundException();
			}
			
			String userId = FacesContext.getCurrentInstance()
					.getExternalContext().getRequestParameterMap()
					.get("userId");
			
			if (userId==null || userId.length()==0) {
				throw new Exception("No se envío el usuario");
			}

			System.out.println("userId:" + userId);
			

			userSelected = userService.findById(Integer.parseInt(userId));
			
			if (userSelected==null) {
				throw new Exception("No se encontró el usuario");
			}
			
			userTypeSelected = userSelected.getUserType().getId().toString();
			
			//////////

			List<Area> areasDto = areaService.findAreasParent();

			areas = new ArrayList<SelectItem>();
			for (Area areaDto : areasDto) {
				SelectItem item = new SelectItem();
				item.setValue(areaDto.getId().toString());
				item.setLabel(areaDto.getName());
				areas.add(item);
			}

			userTypes = new ArrayList<SelectItem>();
			for (UserTypeEnum userType : UserTypeEnum.values()) {
				SelectItem item = new SelectItem();
				item.setLabel(userType.getName());
				item.setValue(userType.getId());
				userTypes.add(item);
			}


			

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
	
	public void searchSubAreas() {

		try {

			if (sessionBean == null || sessionBean.getUser() == null || sessionBean.getUser().getId() == null) {
				throw new UserLoggedNotFoundException();
			}

			if (areaSelected != null && !areaSelected.equals("")) {

				Integer areaId = Integer.parseInt(areaSelected);

				List<Area> areasEntity = areaService.findAreasChild(areaId);
				subAreas = new ArrayList<SelectItem>();
				for (Area areaDto : areasEntity) {
					SelectItem item = new SelectItem();
					item.setValue(areaDto.getId());
					item.setLabel(areaDto.getName());
					subAreas.add(item);
				}

			} else {
				subAreas = new ArrayList<SelectItem>();
			}

		} catch (Exception e) {

			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getMessage());
		}
	}
	
	
	public void edit() {
		try {

			if (sessionBean == null || sessionBean.getUser() == null || sessionBean.getUser().getId() == null) {
				throw new UserLoggedNotFoundException();
			}

			if (userTypeSelected != null && userTypeSelected.length() > 0) {
				UserTypeEnum userType = UserTypeEnum.findById(Short.parseShort(userTypeSelected));
				userSelected.setUserType(userType);
				userSelected.setIsActive(Boolean.TRUE);
				userSelected = userService.edit(userSelected);
				facesUtil.sendConfirmMessage("Se editó satisfactoriamente");
				//RequestContext.getCurrentInstance().closeDialog(userSelected);

			} else {
				facesUtil.sendErrorMessage("Debe seleccionar tipo de usuario");
			}

		} catch (Exception e) {
			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getMessage());
		}
	}
	
	public void addArea() {
		try {

			if (sessionBean == null || sessionBean.getUser() == null || sessionBean.getUser().getId() == null) {
				throw new UserLoggedNotFoundException();
			}

			if (subAreasSelected != null && subAreasSelected.size() > 0) {
				Boolean exist = false;
				for (String subAreaSelected : subAreasSelected) {
					Integer subAreaId = Integer.parseInt(subAreaSelected);
					for (Area area : userSelected.getAreas()) {
						if (area.getId().equals(subAreaId)) {
							exist = true;
							break;
						}
					}

				}
				if (exist) {
					facesUtil.sendErrorMessage("Area seleccionada ya está agregada.");
				} else {
					for (String subAreaSelected : subAreasSelected) {
						Integer subAreaId = Integer.parseInt(subAreaSelected);
						Area area = areaService.findById(subAreaId);
						userSelected.getAreas().add(area);
						subAreaSelected = "";

					}
				}

			} else {
				facesUtil.sendErrorMessage("Debe seleccionar subAreas");
			}

			/*
			 * if (subAreaSelected!=null && subAreaSelected.length()>0) {
			 * 
			 * Integer subAreaId = Integer.parseInt(subAreaSelected); Boolean
			 * exist=false; for (Area area : userSelected.getAreas()) { if
			 * (area.getId().equals(subAreaId)) { exist=true; break; } } if
			 * (exist) { facesUtil.sendErrorMessage(
			 * "Area seleccionada ya esta agregada."); }else{ Area area =
			 * areaService.findById(subAreaId);
			 * userSelected.getAreas().add(area); subAreaSelected=""; }
			 * 
			 * }else{ facesUtil.sendErrorMessage("Debe seleccionar subArea"); }
			 */

		} catch (Exception e) {
			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getMessage());
		}
	}

	public void deleteArea(Area area) {
		try {

			if (sessionBean == null || sessionBean.getUser() == null || sessionBean.getUser().getId() == null) {
				throw new UserLoggedNotFoundException();
			}

			if (area != null) {
				for (Area areaAssigned : userSelected.getAreas()) {
					if (areaAssigned.getId().equals(area.getId())) {
						userSelected.getAreas().remove(areaAssigned);
						break;
					}

				}
			} else {
				facesUtil.sendErrorMessage("El área ingresada esta vacío.");
			}

		} catch (Exception e) {
			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getMessage());
		}
	}

	public List<SelectItem> getUserTypes() {
		return userTypes;
	}

	public void setUserTypes(List<SelectItem> userTypes) {
		this.userTypes = userTypes;
	}

	public String getUserTypeSelected() {
		return userTypeSelected;
	}

	public void setUserTypeSelected(String userTypeSelected) {
		this.userTypeSelected = userTypeSelected;
	}

	public User getUserSelected() {
		return userSelected;
	}

	public void setUserSelected(User userSelected) {
		this.userSelected = userSelected;
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

	public List<SelectItem> getSubAreas() {
		return subAreas;
	}

	public void setSubAreas(List<SelectItem> subAreas) {
		this.subAreas = subAreas;
	}

	public List<String> getSubAreasSelected() {
		return subAreasSelected;
	}

	public void setSubAreasSelected(List<String> subAreasSelected) {
		this.subAreasSelected = subAreasSelected;
	}

	
	
	

}
