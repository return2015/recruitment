package com.returnsoft.recruitment.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import com.returnsoft.recruitment.entity.Area;
import com.returnsoft.recruitment.entity.Requirement;
import com.returnsoft.recruitment.entity.User;
import com.returnsoft.recruitment.enumeration.MonthEnum;
import com.returnsoft.recruitment.enumeration.UserTypeEnum;
import com.returnsoft.recruitment.enumeration.YearEnum;
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
	
	private List<SelectItem> years;
	private String yearSelected;
	
	private List<SelectItem> months;
	private String monthSelected;
	
	private Date period;
	
	private Requirement requirementSelected;
	
	
	public String initialize(){
		try {
			
			if (sessionBean == null || sessionBean.getUser() == null || sessionBean.getUser().getId() == null) {
				throw new UserLoggedNotFoundException();
			}
			
			List<Area> areasEntity = areaService.findAreasParentActive();
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
			
			
			years = new ArrayList<SelectItem>();
			for (YearEnum yearEnum : YearEnum.values()) {
				SelectItem item = new SelectItem();
				item.setValue(yearEnum.getId());
				item.setLabel(yearEnum.getName());
				years.add(item);
			}
			
			months = new ArrayList<SelectItem>();
			for (MonthEnum monthEnum : MonthEnum.values()) {
				SelectItem item = new SelectItem();
				item.setValue(monthEnum.getId());
				item.setLabel(monthEnum.getName());
				months.add(item);
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
			
			YearEnum yearEnum=null;
			if (yearSelected!=null && yearSelected.length()>0) {
				Short idYear = Short.parseShort(yearSelected);
				yearEnum =  YearEnum.findById(idYear);
			}
			
			
			MonthEnum monthEnum=null;
			if (monthSelected!=null && monthSelected.length()>0) {
				Short idMonth = Short.parseShort(monthSelected);
				monthEnum =  MonthEnum.findById(idMonth);
			}


			requirements = requirementService.findList(yearEnum, monthEnum,areaId, subAreaId, recruiterId);
			
			requirementSelected = null;

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
				List<Area> areasEntity = areaService.findAreasChildActive(areaId);
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
			options.put("contentHeight", 400);
			options.put("contentWidth", 1000);

			/*return "edit_user?faces-redirect=true&userId="
					+ userSelected.getId();*/
			Map<String, List<String>> paramMap = new HashMap<String, List<String>>();
			ArrayList<String> paramList = new ArrayList<>();
			paramList.add(String.valueOf(requirementSelected.getId()));
			paramMap.put("requirementId", paramList);
			RequestContext.getCurrentInstance().openDialog("edit_requirement", options, paramMap);

		} catch (Exception e) {
			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getClass().getSimpleName(), e.getMessage());
			//return null;
		}
	}
	
	public void afterEdit(SelectEvent event){
		try {
			Requirement requirementReturn = (Requirement) event.getObject();
			
			int i = 0;
			for (Requirement requirement : requirements) {
				//Sale sale = sales.get(i);
				if (requirement.getId().equals(requirementReturn.getId())) {
					requirements.set(i, requirementReturn);
					requirementSelected = requirementReturn;
					break;
				}
				i++;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getClass().getSimpleName(), e.getMessage());
			//return null;
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

	public List<SelectItem> getYears() {
		return years;
	}

	public void setYears(List<SelectItem> years) {
		this.years = years;
	}

	public String getYearSelected() {
		return yearSelected;
	}

	public void setYearSelected(String yearSelected) {
		this.yearSelected = yearSelected;
	}

	public List<SelectItem> getMonths() {
		return months;
	}

	public void setMonths(List<SelectItem> months) {
		this.months = months;
	}

	public String getMonthSelected() {
		return monthSelected;
	}

	public void setMonthSelected(String monthSelected) {
		this.monthSelected = monthSelected;
	}
	
	
	

}
