package com.returnsoft.recruitment.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.naming.InitialContext;

import com.returnsoft.recruitment.entity.Area;
import com.returnsoft.recruitment.entity.Requirement;
import com.returnsoft.recruitment.entity.User;
import com.returnsoft.recruitment.enumeration.UserTypeEnum;
import com.returnsoft.recruitment.exception.UserLoggedNotFoundException;
import com.returnsoft.recruitment.service.AreaService;
import com.returnsoft.recruitment.service.RequirementService;
import com.returnsoft.recruitment.service.UserService;
import com.returnsoft.recruitment.util.FacesUtil;
import com.returnsoft.recruitment.util.SessionBean;

@ManagedBean
@ViewScoped
public class SearchRequirementController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -589293811008754857L;
	
	@Inject
	private FacesUtil facesUtil;
	
	@Inject
	private SessionBean sessionBean;
	
	
	
	
	
	private List<Requirement> requirements;
	
	@EJB
	private RequirementService requirementService;
	
	@EJB
	private AreaService areaService;
	
	@EJB
	private UserService userService;
	
	private String areaSelected;
	private String subAreaSelected;
	
	private List<SelectItem> areas;
	private List<SelectItem> subAreas;
	
	private List<SelectItem> recruiters;
	private String recruiterSelected;
	
	private Date period;
	
	private Requirement requirementSelected;
	
	
	public String initialize(){
		try {
			
			if (sessionBean == null || sessionBean.getUser() == null || sessionBean.getUser().getId() == null) {
				throw new UserLoggedNotFoundException();
			}
			
			List<Area> areasEntity = areaService.findAreasParent();
			areas = new ArrayList<SelectItem>();
			for (Area areaEntity : areasEntity) {
				SelectItem item = new SelectItem();
				item.setValue(areaEntity.getId().toString());
				item.setLabel(areaEntity.getName());
				areas.add(item);
			}
			
			List<User> recruitersEntity = userService.findByUserType(UserTypeEnum.RECRUITER);
			recruiters = new ArrayList<SelectItem>();
			for (User recruiterEntity : recruitersEntity) {
				SelectItem item = new SelectItem();
				item.setValue(recruiterEntity.getId().toString());
				item.setLabel(recruiterEntity.getFirstname() + " " + recruiterEntity.getLastname());
				recruiters.add(item);
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
	
	public void search() {

		try {

			Integer recruiterId = null;
			if (recruiterSelected != null && !recruiterSelected.equals("")) {
				recruiterId = Integer.parseInt(recruiterSelected);
			}

			/*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

			String periodFormatter = null;
			if (period != null) {
				periodFormatter = sdf.format(period);
			}*/

			//List<Integer> areasId = new ArrayList<Integer>();
			// System.out.println("areaSelected:" + areaSelected);
			/*if (areaSelected != null && !areaSelected.equals("")) {
				if (areaSelected.equals("0")) {
					if (areas != null) {
						for (SelectItem item : areas) {
							areasId.add(Integer.parseInt(item.getValue()
									.toString()));
						}
					}
					// System.out.println(areasId.size());
				} else {
					// System.out.println("areaSelected:" + areaSelected);
					areasId.add(Integer.parseInt(areaSelected));
				}

			}*/

			//List<Integer> subAreasId = new ArrayList<Integer>();
			// System.out.println("subAreaSelected:" + subAreaSelected);
			//Integer subAreaId=null;
			//if (subAreaSelected != null && !subAreaSelected.equals("")) {
				//subAreaId=Integer.parseInt(subAreaSelected);
				/*if (subAreaSelected.equals("0")) {
					if (subAreas != null) {
						for (SelectItem item : subAreas) {
							subAreasId.add(Integer.parseInt(item.getValue()
									.toString()));
						}
					}
					// System.out.println(subAreasId.size());
				} else {
					// System.out.println("subAreaSelected:" + subAreaSelected);
					subAreasId.add(Integer.parseInt(subAreaSelected));
				}*/

			//}
			Integer areaId=null;
			if (areaSelected != null && !areaSelected.equals("")) {
				areaId=Integer.parseInt(areaSelected);
			}
			
			Integer subAreaId=null;
			if (subAreaSelected != null && !subAreaSelected.equals("")) {
				subAreaId=Integer.parseInt(subAreaSelected);
			}


			requirements = requirementService.findList(period,areaId, subAreaId, recruiterId);

		} catch (Exception e) {
			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getClass().getSimpleName(), e.getMessage());
		}
	}
	
	public void searchSubAreas() {

		// System.out.println("ingreso a searchsubdepartments"+departmentSelected);
		try {
			
			if (areaSelected!=null) {
				
				Integer areaId = Integer.parseInt(areaSelected);
				List<Area> areasEntity = areaService.findAreasChild(areaId);
				subAreas = new ArrayList<SelectItem>();
				for (Area areaEntity : areasEntity) {
					SelectItem item = new SelectItem();
					item.setValue(areaEntity.getId());
					item.setLabel(areaEntity.getName());
					subAreas.add(item);
				}
			}else{
				subAreas = new ArrayList<SelectItem>();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getClass().getSimpleName(), e.getMessage());
		}
	}
	

	public String getRecruiterSelected() {
		return recruiterSelected;
	}

	public void setRecruiterSelected(String recruiterSelected) {
		this.recruiterSelected = recruiterSelected;
	}

	public Date getPeriod() {
		return period;
	}

	public void setPeriod(Date period) {
		this.period = period;
	}

	public List<Requirement> getRequirements() {
		return requirements;
	}

	public void setRequirements(List<Requirement> requirements) {
		this.requirements = requirements;
	}

	public String getAreaSelected() {
		return areaSelected;
	}

	public void setAreaSelected(String areaSelected) {
		this.areaSelected = areaSelected;
	}

	public String getSubAreaSelected() {
		return subAreaSelected;
	}

	public void setSubAreaSelected(String subAreaSelected) {
		this.subAreaSelected = subAreaSelected;
	}

	public List<SelectItem> getAreas() {
		return areas;
	}

	public void setAreas(List<SelectItem> areas) {
		this.areas = areas;
	}

	public List<SelectItem> getSubAreas() {
		return subAreas;
	}

	public void setSubAreas(List<SelectItem> subAreas) {
		this.subAreas = subAreas;
	}

	public List<SelectItem> getRecruiters() {
		return recruiters;
	}

	public void setRecruiters(List<SelectItem> recruiters) {
		this.recruiters = recruiters;
	}

	public Requirement getRequirementSelected() {
		return requirementSelected;
	}

	public void setRequirementSelected(Requirement requirementSelected) {
		this.requirementSelected = requirementSelected;
	}
	
	
	

}
