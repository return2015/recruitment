package com.returnsoft.recruitment.controller;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.naming.InitialContext;


//@ManagedBean
//@ViewScoped
public class RequirementController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<SelectItem> areas;
	private String areaSelected;
	private List<SelectItem> subAreas;
	private String subAreaSelected;
	private List<SelectItem> recruiters;
	private String recruiterSelected;
	
	private List<RequirementDto> requirements;
	private RequirementDto requirementSelected;
	
	private Date periodSearch;


	public RequirementController() {
		System.out.println("ingreso al constructor");
	}

	public void beforeSearch() {
		// System.out.println("ingreso a beforeSearchPerson");
		try {

			if (areas == null || areas.size() == 0) {

				AreaInterface areaService = (AreaInterface) new InitialContext(
						Util.getInitProperties())
						.lookup("java:global/RecruitmentEAR/RecruitmentEJB/AreaBean"
								+ "!com.returnsoft.resource.service.AreaInterface");
				List<AreaDto> areasDto = areaService
						.findAreasParent();

				areas = new ArrayList<SelectItem>();
				for (AreaDto areaDto : areasDto) {
					SelectItem item = new SelectItem();
					item.setValue(areaDto.getId().toString());
					item.setLabel(areaDto.getName());
					areas.add(item);
				}
			}
			
			if (recruiters == null || recruiters.size() == 0) {
				RecruiterInterface recruiterService = (RecruiterInterface) new InitialContext(
						Util.getInitProperties())
						.lookup("java:global/RecruitmentEAR/RecruitmentEJB/RecruiterBean"
								+ "!com.returnsoft.resource.service.RecruiterInterface");
				List<RecruiterDto> dtos = recruiterService.findAll();
				recruiters = new ArrayList<SelectItem>();
				for (RecruiterDto dto : dtos) {
					SelectItem item = new SelectItem();
					item.setValue(dto.getId().toString());
					item.setLabel(dto.getFirstname() + " " + dto.getLastname());
					recruiters.add(item);
				}
			}
			

			initialize();
			
			
		} catch (Exception e) {
			if (!(e instanceof RecruitmentException)
					&& !(e instanceof SecurityExcepcion)) {
				e.printStackTrace();
			}
			FacesMessage msg = new FacesMessage(e.getMessage(), e.getMessage());
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	public void searchSubAreas() {

		// System.out.println("ingreso a searchsubdepartments"+departmentSelected);
		try {
			Integer areaId = Integer.parseInt(areaSelected);
			// /System.out.println(departmentId);
			if (areaId > 0) {
				AreaInterface areaService = (AreaInterface) new InitialContext(
						Util.getInitProperties())
						.lookup("java:global/RecruitmentEAR/RecruitmentEJB/AreaBean"
								+ "!com.returnsoft.resource.service.AreaInterface");
				List<AreaDto> areasDto = areaService.findAreasChild(areaId);
				subAreas = new ArrayList<SelectItem>();
				for (AreaDto areaDto : areasDto) {
					SelectItem item = new SelectItem();
					item.setValue(areaDto.getId());
					item.setLabel(areaDto.getName());
					subAreas.add(item);
				}
			} else {
				subAreas = new ArrayList<SelectItem>();
			}

		} catch (Exception e) {
			if (!(e instanceof RecruitmentException)
					&& !(e instanceof SecurityExcepcion)) {
				e.printStackTrace();
			}
			FacesMessage msg = new FacesMessage(e.getMessage(), e.getMessage());
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	public void search() {

		try {

			

			Integer recruiterId = null;
			if (recruiterSelected != null && !recruiterSelected.equals("")) {
				recruiterId = Integer.parseInt(recruiterSelected);
			}


			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

			String periodFormatter = null;
			if (periodSearch != null) {
				periodFormatter = sdf.format(periodSearch);
			}

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
			Integer subAreaId=null;
			if (subAreaSelected != null && !subAreaSelected.equals("")) {
				subAreaId=Integer.parseInt(subAreaSelected);
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

			}
			Integer areaId=null;
			if (areaSelected != null && !areaSelected.equals("")) {
				areaId=Integer.parseInt(areaSelected);
			}
			
			RequirementService requirementService = (RequirementService) new InitialContext(
					Util.getInitProperties())
					.lookup("java:global/RecruitmentEAR/RecruitmentEJB/RequirementBean"
							+ "!com.returnsoft.resource.service.RequirementInterface");

			requirements = requirementService.findList(periodFormatter,areaId, subAreaId, recruiterId);

		} catch (Exception e) {
			if (!(e instanceof RecruitmentException)
					&& !(e instanceof SecurityExcepcion)) {
				e.printStackTrace();
			}
			FacesMessage msg = new FacesMessage(e.getMessage(), e.getMessage());
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	public void initialize() {

		try {

			/*if (states == null || states.size() == 0) {
				OjtStateInterface ojtStateService = (OjtStateInterface) new InitialContext(
						Util.getInitProperties())
						.lookup("java:global/RecruitmentEAR/RecruitmentEJB/OjtStateBean"
								+ "!com.returnsoft.resource.service.OjtStateInterface");
				List<OjtStateDto> dtos = ojtStateService.findAll();
				states = new ArrayList<SelectItem>();
				for (OjtStateDto dto : dtos) {
					SelectItem item = new SelectItem();
					item.setValue(dto.getId().toString());
					item.setLabel(dto.getName());
					states.add(item);
				}
			}*/

			

		} catch (Exception e) {
			if (!(e instanceof RecruitmentException)
					&& !(e instanceof SecurityExcepcion)) {
				e.printStackTrace();
			}
			FacesMessage msg = new FacesMessage(e.getMessage(), e.getMessage());
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}

	}

	public void beforeAdd() {

		try {

			initialize();
			
			if (areas == null || areas.size() == 0) {

				AreaInterface areaService = (AreaInterface) new InitialContext(
						Util.getInitProperties())
						.lookup("java:global/RecruitmentEAR/RecruitmentEJB/AreaBean"
								+ "!com.returnsoft.resource.service.AreaInterface");
				List<AreaDto> areasDto = areaService
						.findAreasParent();

				areas = new ArrayList<SelectItem>();
				for (AreaDto areaDto : areasDto) {
					SelectItem item = new SelectItem();
					item.setValue(areaDto.getId().toString());
					item.setLabel(areaDto.getName());
					areas.add(item);
				}
			}
			
			if (recruiters == null || recruiters.size() == 0) {
				RecruiterInterface recruiterService = (RecruiterInterface) new InitialContext(
						Util.getInitProperties())
						.lookup("java:global/RecruitmentEAR/RecruitmentEJB/RecruiterBean"
								+ "!com.returnsoft.resource.service.RecruiterInterface");
				List<RecruiterDto> dtos = recruiterService.findAll();
				recruiters = new ArrayList<SelectItem>();
				for (RecruiterDto dto : dtos) {
					SelectItem item = new SelectItem();
					item.setValue(dto.getId().toString());
					item.setLabel(dto.getFirstname() + " " + dto.getLastname());
					recruiters.add(item);
				}
			}
			
			requirementSelected = new RequirementDto();

		} catch (Exception e) {
			if (!(e instanceof RecruitmentException)
					&& !(e instanceof SecurityExcepcion)) {
				e.printStackTrace();
			}
			FacesMessage msg = new FacesMessage(e.getMessage(), e.getMessage());
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}

	}

	/*
	 * public void searchTrainingByDocumentNumber() { try {
	 * 
	 * if (candidateDocumentNumberSearch != null &&
	 * !candidateDocumentNumberSearch.equals("")) { TrainingInterface
	 * trainingService = (TrainingInterface) new InitialContext(
	 * Util.getInitProperties())
	 * .lookup("java:global/RecruitmentEAR/RecruitmentEJB/TrainingBean" +
	 * "!com.returnsoft.resource.service.TrainingInterface");
	 * 
	 * trainings = trainingService
	 * .findByDocumentNumber(candidateDocumentNumberSearch);
	 * 
	 * trainingSelected = null;
	 * 
	 * } else {
	 * 
	 * FacesMessage msg = new FacesMessage("Debe ingresar DNI",
	 * "Debe ingresar DNI"); msg.setSeverity(FacesMessage.SEVERITY_ERROR);
	 * FacesContext.getCurrentInstance().addMessage(null, msg);
	 * 
	 * }
	 * 
	 * } catch (Exception e) { if (!(e instanceof RecruitmentException) && !(e
	 * instanceof SecurityExcepcion)) { e.printStackTrace(); } FacesMessage msg
	 * = new FacesMessage(e.getMessage(), e.getMessage());
	 * msg.setSeverity(FacesMessage.SEVERITY_ERROR);
	 * FacesContext.getCurrentInstance().addMessage(null, msg); } }
	 */

	public String add() {
		try {

			//if (trainingSelected != null && trainingSelected.getId() > 0) {

				System.out.println("Ingreso a Add");

				if (recruiterSelected!=null && recruiterSelected.length()>0) {
					Integer recruiterId = Integer.parseInt(recruiterSelected);
					RecruiterDto recruiter = new RecruiterDto();
					recruiter.setId(recruiterId);
					requirementSelected.setRecruiter(recruiter);
				}
				
				if (subAreaSelected!=null && subAreaSelected.length()>0) {
					Integer subAreaId = Integer.parseInt(subAreaSelected);
					AreaDto subArea = new AreaDto();
					subArea.setId(subAreaId);
					requirementSelected.setArea(subArea);
				}



				RequirementService requirementService = (RequirementService) new InitialContext(
						Util.getInitProperties())
						.lookup("java:global/RecruitmentEAR/RecruitmentEJB/RequirementBean"
								+ "!com.returnsoft.resource.service.RequirementInterface");

				requirementSelected = requirementService.add(requirementSelected);

				// SE IMPRIME MENSAJE DE CONFIRMACION
				FacesMessage msg = new FacesMessage(
						"Se creó satisfactoriamente el requerimiento con id:"
								+ requirementSelected.getId());
				msg.setSeverity(FacesMessage.SEVERITY_INFO);
				FacesContext.getCurrentInstance().addMessage(null, msg);

				return "search_requirement";
			

		} catch (Exception e) {
			if (!(e instanceof RecruitmentException)
					&& !(e instanceof SecurityExcepcion)) {
				e.printStackTrace();
			}
			FacesMessage msg = new FacesMessage(e.getMessage(), e.getMessage());
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return null;
		}
	}

	/*
	 * public String navigateForEdit(){
	 * 
	 * 
	 * Map<String, String> map = FacesContext.getCurrentInstance()
	 * .getExternalContext().getRequestParameterMap(); String dtoIdRequest =
	 * (String) map.get("trainingId"); Set<String> keys = map.keySet();
	 * System.out.println(keys.size());
	 * 
	 * for (String key : keys) { System.out.println(key);
	 * System.out.println(map.get(key)); } System.out.println(keys.size());
	 * 
	 * 
	 * System.out.println(dtoIdRequest);
	 * 
	 * return
	 * "edit_training.xhtml?faces-redirect=true&trainingId="+dtoIdRequest; }
	 */

	public void beforeEdit() {

		
		try {

			Map<String, String> map = FacesContext.getCurrentInstance()
					.getExternalContext().getRequestParameterMap();
			String dtoIdRequest = (String) map.get("requirementId");

			System.out.println(dtoIdRequest);

			if (dtoIdRequest != null && !dtoIdRequest.equals("")) {

				Integer dtoId = Integer.parseInt(dtoIdRequest);

				RequirementService service = (RequirementService) new InitialContext(
						Util.getInitProperties())
						.lookup("java:global/RecruitmentEAR/RecruitmentEJB/RequirementBean"
								+ "!com.returnsoft.resource.service.RequirementInterface");

				requirementSelected = service.findById(dtoId);
				
				if (areas == null || areas.size() == 0) {

					AreaInterface areaService = (AreaInterface) new InitialContext(
							Util.getInitProperties())
							.lookup("java:global/RecruitmentEAR/RecruitmentEJB/AreaBean"
									+ "!com.returnsoft.resource.service.AreaInterface");
					List<AreaDto> areasDto = areaService
							.findAreasParent();

					areas = new ArrayList<SelectItem>();
					for (AreaDto areaDto : areasDto) {
						SelectItem item = new SelectItem();
						item.setValue(areaDto.getId().toString());
						item.setLabel(areaDto.getName());
						areas.add(item);
					}
				}
				
				if (recruiters == null || recruiters.size() == 0) {
					RecruiterInterface recruiterService = (RecruiterInterface) new InitialContext(
							Util.getInitProperties())
							.lookup("java:global/RecruitmentEAR/RecruitmentEJB/RecruiterBean"
									+ "!com.returnsoft.resource.service.RecruiterInterface");
					List<RecruiterDto> dtos = recruiterService.findAll();
					recruiters = new ArrayList<SelectItem>();
					for (RecruiterDto dto : dtos) {
						SelectItem item = new SelectItem();
						item.setValue(dto.getId().toString());
						item.setLabel(dto.getFirstname() + " " + dto.getLastname());
						recruiters.add(item);
					}
				}
				
				if (requirementSelected.getArea()!=null && requirementSelected.getArea().getId()>0) {
					if (requirementSelected.getArea().getArea()!=null && requirementSelected.getArea().getArea().getId()>0) {
						areaSelected=requirementSelected.getArea().getArea().getId().toString();
						searchSubAreas();
						subAreaSelected=requirementSelected.getArea().getId().toString();
					}
				}
				
				if (requirementSelected.getRecruiter()!=null && requirementSelected.getRecruiter().getId()>0) {
					recruiterSelected=requirementSelected.getRecruiter().getId().toString();
				}
				
				
				initialize();

				
			}

		} catch (Exception e) {
			if (!(e instanceof RecruitmentException)
					&& !(e instanceof SecurityExcepcion)) {
				e.printStackTrace();
			}
			FacesMessage msg = new FacesMessage(e.getMessage(), e.getMessage());
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	public String edit() {

		try {
			// System.out.println(dtoIdRequest);
			if (requirementSelected != null && requirementSelected.getId() > 0) {
				

				RequirementService service = (RequirementService) new InitialContext(
						Util.getInitProperties())
						.lookup("java:global/RecruitmentEAR/RecruitmentEJB/RequirementBean"
								+ "!com.returnsoft.resource.service.RequirementInterface");
				
				if (recruiterSelected!=null && recruiterSelected.length()>0) {
					Integer recruiterId = Integer.parseInt(recruiterSelected);
					RecruiterDto recruiter = new RecruiterDto();
					recruiter.setId(recruiterId);
					requirementSelected.setRecruiter(recruiter);
				}
				
				if (subAreaSelected!=null && subAreaSelected.length()>0) {
					Integer subAreaId = Integer.parseInt(subAreaSelected);
					AreaDto subArea = new AreaDto();
					subArea.setId(subAreaId);
					requirementSelected.setArea(subArea);
				}

				requirementSelected = service.add(requirementSelected);

				// resetAttributes();

				// SE IMPRIME MENSAJE DE CONFIRMACION
				FacesMessage msg = new FacesMessage(
						"Se editó satisfactoriamente el requerimiento con id:"
								+ requirementSelected.getId());
				msg.setSeverity(FacesMessage.SEVERITY_INFO);
				FacesContext.getCurrentInstance().addMessage(null, msg);

				return "search_requirement";
			} else {

				// SE IMPRIME MENSAJE DE CONFIRMACION
				FacesMessage msg = new FacesMessage(
						"No se encontró un requerimiento seleccionado.");
				msg.setSeverity(FacesMessage.SEVERITY_WARN);
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return "search_requirement";
			}

		} catch (Exception e) {
			if (!(e instanceof RecruitmentException)
					&& !(e instanceof SecurityExcepcion)) {
				e.printStackTrace();
			}
			FacesMessage msg = new FacesMessage(e.getMessage(), e.getMessage());
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return null;
		}

	}



	/*public List<TrainingDto> getTrainings() {
		return trainings;
	}

	public void setTrainings(List<TrainingDto> trainings) {
		this.trainings = trainings;
	}*/



	/*public TrainingDto getTrainingSelected() {
		return trainingSelected;
	}

	public void setTrainingSelected(TrainingDto trainingSelected) {
		this.trainingSelected = trainingSelected;
	}*/

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

	public String getSubAreaSelected() {
		return subAreaSelected;
	}

	public void setSubAreaSelected(String subAreaSelected) {
		this.subAreaSelected = subAreaSelected;
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

	public List<RequirementDto> getRequirements() {
		return requirements;
	}

	public void setRequirements(List<RequirementDto> requirements) {
		this.requirements = requirements;
	}

	public RequirementDto getRequirementSelected() {
		return requirementSelected;
	}

	public void setRequirementSelected(RequirementDto requirementSelected) {
		this.requirementSelected = requirementSelected;
	}

	public Date getPeriodSearch() {
		return periodSearch;
	}

	public void setPeriodSearch(Date periodSearch) {
		this.periodSearch = periodSearch;
	}

	
	

}
