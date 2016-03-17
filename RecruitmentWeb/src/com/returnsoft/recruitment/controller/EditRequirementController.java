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
import com.returnsoft.recruitment.entity.Requirement;
import com.returnsoft.recruitment.entity.RequirementUser;
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
public class EditRequirementController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 996612596618633756L;
	
	@Inject
	private FacesUtil facesUtil;
	
	@Inject
	private SessionBean sessionBean;
	
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
	private String amount;
	private String totalAmount;

	private Requirement requirementSelected;
	
	private List<SelectItem> months;
	private String monthSelected;
	private List<SelectItem> years;
	private String yearSelected;
	
	
	
	public String initialize(){
		try {
			
			if (sessionBean == null || sessionBean.getUser() == null || sessionBean.getUser().getId() == null) {
				throw new UserLoggedNotFoundException();
			}
			
			
			String requirementId = FacesContext.getCurrentInstance()
					.getExternalContext().getRequestParameterMap()
					.get("requirementId");
			
			if (requirementId==null || requirementId.length()==0) {
				throw new Exception("No se envío el requerimiento");
			}

			System.out.println("requirementId:" + requirementId);
			

			requirementSelected = requirementService.findById(Integer.parseInt(requirementId));
			
			if (requirementSelected==null) {
				throw new Exception("No se encontró el requerimiento.");
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
			
			
			subAreaSelected = requirementSelected.getArea().getId().toString();
			
			areaSelected = requirementSelected.getArea().getArea().getId().toString();
			
			searchSubAreas();
			
			yearSelected = requirementSelected.getPeriodYear().getId().toString();
			
			monthSelected = requirementSelected.getPeriodMonth().getId().toString();
			

			//requirementSelected = new Requirement();
			//requirementSelected.setUsers(new ArrayList<RequirementUser>());

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

		// System.out.println("ingreso a
		// searchsubdepartments"+departmentSelected);
		try {

			if (sessionBean == null || sessionBean.getUser() == null || sessionBean.getUser().getId() == null) {
				throw new UserLoggedNotFoundException();
			}

			if (areaSelected != null) {

				Integer areaId = Integer.parseInt(areaSelected);
				List<Area> areasEntity = areaService.findAreasChildActive(areaId);
				subAreas = new ArrayList<SelectItem>();
				for (Area areaEntity : areasEntity) {
					SelectItem item = new SelectItem();
					item.setValue(areaEntity.getId());
					item.setLabel(areaEntity.getName());
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
	
	
	public void addRecruiter() {

		try {

			if (sessionBean == null || sessionBean.getUser() == null || sessionBean.getUser().getId() == null) {
				throw new UserLoggedNotFoundException();
			}

			if (recruiterSelected != null && recruiterSelected.length() > 0) {

				Integer recruiterId = Integer.parseInt(recruiterSelected);

				if (amount != null && amount.length() > 0) {
					
					Integer amountInt = Integer.parseInt(amount);
					
					if (amountInt>0) {
						// VALIDA SI YA EXISTE
						Boolean exist = false;
						for (RequirementUser requirementUser : requirementSelected.getUsers()) {
							if (requirementUser.getUser().getId().equals(recruiterId)) {
								exist = true;
							}
						}

						if (exist) {
							facesUtil.sendErrorMessage("El reclutador ya esta agregado.");
						} else {
							User user = userService.findById(Integer.parseInt(recruiterSelected));
							RequirementUser ru = new RequirementUser();
							ru.setUser(user);
							ru.setRequirement(requirementSelected);
							ru.setRequirementId(requirementSelected.getId());
							ru.setUserId(user.getId());
							ru.setAmount(Integer.parseInt(amount));
							
							requirementSelected.getUsers().add(ru);
							recruiterSelected = "";
							amount = "";
							
							Integer total =0;
							for (RequirementUser requirementUser : requirementSelected.getUsers()) {
								total+=requirementUser.getAmount();
							}
							requirementSelected.setAmount(total);
							//totalAmount=total+"";
						}
					}else{
						facesUtil.sendErrorMessage("La cantidad debe ser mayor a cero.");	
					}
					
					

				} else {
					facesUtil.sendErrorMessage("Debe ingresar cantidad.");
				}

			} else {
				facesUtil.sendErrorMessage("Debe seleccionar reclutador.");
			}

		} catch (Exception e) {
			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getMessage());
		}
	}
	
	public void deleteRecruiter(RequirementUser ru){
		try {
			System.out.println("ingreso a deleteRecruiter");
			
			if (sessionBean == null || sessionBean.getUser() == null || sessionBean.getUser().getId() == null) {
				throw new UserLoggedNotFoundException();
			}
			
			//System.out.println(ru.getUser().getId());
			if (ru!=null) {
				for (RequirementUser requirementUser : requirementSelected.getUsers()) {
					if (requirementUser.getUser().getId().equals(ru.getUser().getId())) {
						requirementSelected.getUsers().remove(requirementUser);
						break;
					}
				}
				
				Integer total =0;
				for (RequirementUser requirementUser : requirementSelected.getUsers()) {
					total+=requirementUser.getAmount();
				}
				requirementSelected.setAmount(total);
				
			}else{
				facesUtil.sendErrorMessage("No se encontró reclutador.");	
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
			
			if (requirementSelected.getUsers().size()==0) {
				throw new Exception("Debe asignar al menos un reclutador.");
			}

			// if (trainingSelected != null && trainingSelected.getId() > 0) {

			System.out.println("Ingreso a Add");

			/*
			 * if (recruiterSelected!=null && recruiterSelected.length()>0) {
			 * Integer recruiterId = Integer.parseInt(recruiterSelected); User
			 * recruiter = new User(); recruiter.setId(recruiterId);
			 * requirementSelected.setRecruiter(recruiter); }
			 */

			if (subAreaSelected != null && subAreaSelected.length() > 0) {
				Integer subAreaId = Integer.parseInt(subAreaSelected);
				Area subArea = areaService.findById(subAreaId);
				requirementSelected.setArea(subArea);
			}
			
			if (monthSelected!=null && monthSelected.length()>0) {
				//System.out.println("monthSelected:"+monthSelected);
				Short monthId = Short.parseShort(monthSelected);
				MonthEnum monthEnum = MonthEnum.findById(monthId);
				requirementSelected.setPeriodMonth(monthEnum);
			}
			
			if (yearSelected!=null && yearSelected.length()>0) {
				//System.out.println("yearSelected:"+yearSelected);
				Short yearId = Short.parseShort(yearSelected);
				//System.out.println("yearId:"+yearId);
				YearEnum yearEnum = YearEnum.findById(yearId);
				//System.out.println("yearEnum:"+yearEnum.toString());
				requirementSelected.setPeriodYear(yearEnum);
			}
			
			Integer total =0;
			
			for (RequirementUser requirementUser : requirementSelected.getUsers()) {
				total+=requirementUser.getAmount();
			}
			
			requirementSelected.setAmount(total);
			
			requirementSelected = requirementService.edit(requirementSelected);
			
			// SE IMPRIME MENSAJE DE CONFIRMACION
			facesUtil.sendConfirmMessage("Se actualizó satisfactoriamente.");
			
			RequestContext.getCurrentInstance().closeDialog(requirementSelected);
			
			/*monthSelected="";
			yearSelected="";
			areaSelected="";
			subAreaSelected="";
			subAreas=new ArrayList<SelectItem>();
			totalAmount="";
			requirementSelected = new Requirement();
			requirementSelected.setUsers(new ArrayList<RequirementUser>());*/
			
					

			

			// return "search_requirement";

		} catch (Exception e) {
			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getMessage());
		}
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

	public String getRecruiterSelected() {
		return recruiterSelected;
	}

	public void setRecruiterSelected(String recruiterSelected) {
		this.recruiterSelected = recruiterSelected;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Requirement getRequirementSelected() {
		return requirementSelected;
	}

	public void setRequirementSelected(Requirement requirementSelected) {
		this.requirementSelected = requirementSelected;
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
	

}
