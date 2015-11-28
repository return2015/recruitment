package com.returnsoft.recruitment.controller;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.model.SelectItem;

import com.returnsoft.recruitment.entity.Area;
import com.returnsoft.recruitment.entity.Candidate;
import com.returnsoft.recruitment.entity.Country;
import com.returnsoft.recruitment.entity.Department;
import com.returnsoft.recruitment.entity.District;
import com.returnsoft.recruitment.entity.EducationLevel;
import com.returnsoft.recruitment.entity.Interview;
import com.returnsoft.recruitment.entity.InterviewState;
import com.returnsoft.recruitment.entity.OjtState;
import com.returnsoft.recruitment.entity.Province;
import com.returnsoft.recruitment.entity.RecruimentSource;
import com.returnsoft.recruitment.entity.TrainingState;
import com.returnsoft.recruitment.entity.User;
import com.returnsoft.recruitment.enumeration.UserTypeEnum;
import com.returnsoft.recruitment.exception.UserLoggedNotFoundException;
import com.returnsoft.recruitment.exception.UserPermissionNotFoundException;
import com.returnsoft.recruitment.service.AreaService;
import com.returnsoft.recruitment.service.InterviewStateService;
import com.returnsoft.recruitment.service.OjtStateService;
import com.returnsoft.recruitment.service.TrainingStateService;
import com.returnsoft.recruitment.service.UserService;
import com.returnsoft.recruitment.util.FacesUtil;

@ManagedBean
@ViewScoped
public class SearchCandidateController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6738465693766075837L;

	private List<SelectItem> areas;
	private String areaSelected;
	private List<SelectItem> subAreas;
	private String subAreaSelected;
	private List<SelectItem> countries;
	private String countrySelected;
	private List<SelectItem> departments;
	private String departmentSelected;
	private List<SelectItem> provinces;
	private String provinceSelected;
	private List<SelectItem> districts;
	private String districtSelected;
	private List<SelectItem> recruimentSources;
	private String recruimentSourceSelected;
	private List<SelectItem> recruiters;
	private String recruiterSelected;

	private List<SelectItem> interviewStates;
	private String interviewStateSelected;

	private List<SelectItem> trainingStates;
	private String trainingStateSelected;

	private List<SelectItem> ojtStates;
	private String ojtStateSelected;

	private List<SelectItem> educationLevels;
	private String educationLevelSelected;

	private List<Candidate> candidates;
	private Candidate candidateSelected;

	private String candidateDocumentNumberSearch;
	private String candidateNameSearch;
	private Date scheduledAtSearch;
	private Date createdAtSearch;

	private Boolean isScheduled;
	private Boolean isPending;
	private Boolean inOnChange;

	private Date ojtAt;
	private Date trainingAt;
	private Date interviewedAt;
	private Date scheduledAt;

	private String age;
	private Date minDate;
	private Date maxDate;

	private Boolean isJobFair;
	private Boolean isFlier;
	private Boolean isReferred;

	private FacesUtil facesUtil;

	@EJB
	private UserService userService;

	@EJB
	private AreaService areaService;

	@EJB
	private TrainingStateService trainingStateService;

	@EJB
	private InterviewStateService interviewStateService;

	@EJB
	private OjtStateService ojtStateService;

	public SearchCandidateController() {
		System.out.println("ingreso al constructor candidateController");
		Date today = new Date();
		Calendar calendarMin = Calendar.getInstance();
		calendarMin.setTime(today);
		calendarMin.set(Calendar.YEAR, calendarMin.get(Calendar.YEAR) - 50);
		this.minDate = calendarMin.getTime();
		// System.out.println("minDate" + minDate);
		Calendar calendarMax = Calendar.getInstance();
		calendarMax.setTime(today);
		calendarMax.set(Calendar.YEAR, calendarMax.get(Calendar.YEAR) - 18);
		this.maxDate = calendarMax.getTime();
		// System.out.println("maxDate" + maxDate);
		candidateSelected = new Candidate();
		System.out.println("termino el constructor candidateController");

		facesUtil = new FacesUtil();
	}

	public String initialize() {
		try {
			
			System.out.println("ingreso a initialize de SearchCandidateController");

			SessionBean sessionBean = (SessionBean) FacesContext.getCurrentInstance().getExternalContext()
					.getSessionMap().get("sessionBean");

			if (sessionBean != null && sessionBean.getUser() != null && sessionBean.getUser().getId() > 0) {

				if (!sessionBean.getUser().getUserType().equals(UserTypeEnum.ADMIN)
						&& !sessionBean.getUser().getUserType().equals(UserTypeEnum.AGENT)) {
					throw new UserPermissionNotFoundException();
				}

				//////////////////////////////////////////////////////////////

				List<Area> areasDto = areaService.findAreasByRecruiter(sessionBean.getUser().getId());

				areas = new ArrayList<SelectItem>();
				for (Area areaDto : areasDto) {
					SelectItem item = new SelectItem();
					item.setValue(areaDto.getId().toString());
					item.setLabel(areaDto.getName());
					areas.add(item);
				}

				List<User> recruitersEntity = userService.findByUserType(UserTypeEnum.AGENT);
				recruiters = new ArrayList<SelectItem>();
				for (User dto : recruitersEntity) {
					SelectItem item = new SelectItem();
					item.setValue(dto.getId().toString());
					item.setLabel(dto.getFirstname() + " " + dto.getLastname());
					recruiters.add(item);
				}

				List<InterviewState> interviewStatesEntity = interviewStateService.findAll();
				interviewStates = new ArrayList<SelectItem>();
				for (InterviewState dto : interviewStatesEntity) {
					SelectItem item = new SelectItem();
					item.setValue(dto.getId().toString());
					item.setLabel(dto.getName());
					interviewStates.add(item);
				}

				List<TrainingState> trainingStatesEntity = trainingStateService.findAll();
				trainingStates = new ArrayList<SelectItem>();
				for (TrainingState dto : trainingStatesEntity) {
					SelectItem item = new SelectItem();
					item.setValue(dto.getId().toString());
					item.setLabel(dto.getName());
					trainingStates.add(item);
				}

				List<OjtState> ojtStatesEntity = ojtStateService.findAll();
				ojtStates = new ArrayList<SelectItem>();
				for (OjtState dto : ojtStatesEntity) {
					SelectItem item = new SelectItem();
					item.setValue(dto.getId().toString());
					item.setLabel(dto.getName());
					ojtStates.add(item);
				}

				candidateSelected = null;

				return null;

			} else {
				throw new UserLoggedNotFoundException();
			}

		} catch (UserLoggedNotFoundException e) {
			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getClass().getSimpleName(), e.getMessage());
			return "login.xhtml?faces-redirect=true";
		} catch (UserPermissionNotFoundException e) {
			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getClass().getSimpleName(), e.getMessage());
			return "login.xhtml?faces-redirect=true";
		} catch (Exception e) {
			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getClass().getSimpleName(), e.getMessage());
			return null;
		}
	}

	public void beforeSearch() {
		System.out.println("ingreso a beforeSearchCandidate");

		try {

			if (areas == null || areas.size() == 0) {

				/*
				 * SessionBean session = (SessionBean) FacesContext
				 * .getCurrentInstance().getExternalContext()
				 * .getSessionMap().get("sessionBean"); Integer recruiterId =
				 * session.getRecruiterId();
				 * 
				 * AreaInterface areaService = (AreaInterface) new
				 * InitialContext( Util.getInitProperties())
				 * .lookup("java:global/RecruitmentEAR/RecruitmentEJB/AreaBean"
				 * + "!com.returnsoft.resource.service.AreaInterface");
				 */

				/*
				 * List<Area> areasDto = areaService
				 * .findAreasByRecruiter(recruiterId);
				 */

				// areas = new ArrayList<SelectItem>();
				/*
				 * for (AreaDto areaDto : areasDto) { SelectItem item = new
				 * SelectItem(); item.setValue(areaDto.getId().toString());
				 * item.setLabel(areaDto.getName()); areas.add(item); }
				 */
			}

			if (recruiters == null || recruiters.size() == 0) {
				/*
				 * RecruiterInterface recruiterService = (RecruiterInterface)
				 * new InitialContext( Util.getInitProperties()) .lookup(
				 * "java:global/RecruitmentEAR/RecruitmentEJB/RecruiterBean" +
				 * "!com.returnsoft.resource.service.RecruiterInterface");
				 * List<RecruiterDto> dtos = recruiterService.findAll();
				 */
				recruiters = new ArrayList<SelectItem>();
				/*
				 * for (RecruiterDto dto : dtos) { SelectItem item = new
				 * SelectItem(); item.setValue(dto.getId().toString());
				 * item.setLabel(dto.getFirstname() + " " + dto.getLastname());
				 * recruiters.add(item); }
				 */
			}

			if (interviewStates == null || interviewStates.size() == 0) {
				/*
				 * InterviewStateInterface interviewStateService =
				 * (InterviewStateInterface) new InitialContext(
				 * Util.getInitProperties()) .lookup(
				 * "java:global/RecruitmentEAR/RecruitmentEJB/InterviewStateBean"
				 * +
				 * "!com.returnsoft.resource.service.InterviewStateInterface");
				 * List<InterviewStateDto> dtos =
				 * interviewStateService.findAll();
				 */
				interviewStates = new ArrayList<SelectItem>();
				/*
				 * for (InterviewStateDto dto : dtos) { SelectItem item = new
				 * SelectItem(); item.setValue(dto.getId().toString());
				 * item.setLabel(dto.getName()); interviewStates.add(item); }
				 */
			}

			if (trainingStates == null || trainingStates.size() == 0) {
				/*
				 * TrainingStateInterface trainingStateService =
				 * (TrainingStateInterface) new InitialContext(
				 * Util.getInitProperties()) .lookup(
				 * "java:global/RecruitmentEAR/RecruitmentEJB/TrainingStateBean"
				 * + "!com.returnsoft.resource.service.TrainingStateInterface");
				 * List<TrainingStateDto> dtos = trainingStateService.findAll();
				 */
				trainingStates = new ArrayList<SelectItem>();
				/*
				 * for (TrainingStateDto dto : dtos) { SelectItem item = new
				 * SelectItem(); item.setValue(dto.getId().toString());
				 * item.setLabel(dto.getName()); trainingStates.add(item); }
				 */
			}

			if (ojtStates == null || ojtStates.size() == 0) {
				/*
				 * OjtStateInterface ojtStateService = (OjtStateInterface) new
				 * InitialContext( Util.getInitProperties()) .lookup(
				 * "java:global/RecruitmentEAR/RecruitmentEJB/OjtStateBean" +
				 * "!com.returnsoft.resource.service.OjtStateInterface");
				 * List<OjtStateDto> dtos = ojtStateService.findAll();
				 */
				ojtStates = new ArrayList<SelectItem>();
				/*
				 * for (OjtStateDto dto : dtos) { SelectItem item = new
				 * SelectItem(); item.setValue(dto.getId().toString());
				 * item.setLabel(dto.getName()); ojtStates.add(item); }
				 */
			}

			if (candidates == null || candidates.size() == 0) {
				candidateSelected = null;
			}

			System.out.println("termino beforeSearchCandidate");

		} catch (Exception e) {
			/*
			 * if (!(e instanceof RecruitmentException) && !(e instanceof
			 * SecurityExcepcion)) { e.printStackTrace(); }
			 */
			FacesMessage msg = new FacesMessage(e.getMessage(), e.getMessage());
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	public void searchSubAreas() {

		System.out.println("ingreso a searchsubareas: " + areaSelected);

		try {

			if (areaSelected != null && !areaSelected.equals("")) {

				if (areaSelected.equals("t")) {

					if (areas != null && areas.size() > 0) {

						SessionBean session = (SessionBean) FacesContext.getCurrentInstance().getExternalContext()
								.getSessionMap().get("sessionBean");

						Integer recruiterId = session.getUser().getId();
						subAreas = new ArrayList<SelectItem>();

						for (SelectItem areaItem : areas) {
							Integer areaId = Integer.parseInt(areaItem.getValue().toString());
							List<Area> areasEntity = areaService.findSubAreasByRecruiter(areaId, recruiterId);
							for (Area areaDto : areasEntity) {
								SelectItem item = new SelectItem();
								item.setValue(areaDto.getId());
								item.setLabel(areaDto.getName());
								subAreas.add(item);
							}
						}

					} else {
						subAreas = new ArrayList<SelectItem>();
					}
				} else {

					Integer areaId = Integer.parseInt(areaSelected);
					SessionBean session = (SessionBean) FacesContext.getCurrentInstance().getExternalContext()
							.getSessionMap().get("sessionBean");
					Integer recruiterId = session.getUser().getId();

					List<Area> areasEntity = areaService.findSubAreasByRecruiter(areaId, recruiterId);
					subAreas = new ArrayList<SelectItem>();
					for (Area areaDto : areasEntity) {
						SelectItem item = new SelectItem();
						item.setValue(areaDto.getId());
						item.setLabel(areaDto.getName());
						subAreas.add(item);
					}
				}

			} else {
				subAreas = new ArrayList<SelectItem>();
			}

		} catch (Exception e) {
			/*
			 * if (!(e instanceof RecruitmentException) && !(e instanceof
			 * SecurityExcepcion)) { e.printStackTrace(); }
			 */
			FacesMessage msg = new FacesMessage(e.getMessage(), e.getMessage());
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	public void search() {

		// System.out.println("ingreso a search person");
		try {

			/*
			 * CandidateInterface candidateService = (CandidateInterface) new
			 * InitialContext( Util.getInitProperties())
			 * .lookup("java:global/RecruitmentEAR/RecruitmentEJB/CandidateBean"
			 * + "!com.returnsoft.resource.service.CandidateInterface");
			 */

			Integer recruiterId = null;
			if (recruiterSelected != null && !recruiterSelected.equals("")) {
				recruiterId = Integer.parseInt(recruiterSelected);
			}

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String scheduledAtFormatter = null;
			if (scheduledAtSearch != null) {
				scheduledAtFormatter = sdf.format(scheduledAtSearch);
			}

			String createdAtFormatter = null;
			if (createdAtSearch != null) {
				createdAtFormatter = sdf.format(createdAtSearch);
			}

			Integer interviewStateId = null;
			if (interviewStateSelected != null && !interviewStateSelected.equals("")) {
				interviewStateId = Integer.parseInt(interviewStateSelected);
			}

			Integer trainingStateId = null;
			if (trainingStateSelected != null && !trainingStateSelected.equals("")) {
				trainingStateId = Integer.parseInt(trainingStateSelected);
			}

			Integer ojtStateId = null;
			if (ojtStateSelected != null && !ojtStateSelected.equals("")) {
				ojtStateId = Integer.parseInt(ojtStateSelected);
			}

			List<Integer> areasId = new ArrayList<Integer>();
			if (areaSelected != null && !areaSelected.equals("")) {
				if (areaSelected.equals("t")) {
					if (areas != null && areas.size() > 0) {
						for (SelectItem item : areas) {
							areasId.add(Integer.parseInt(item.getValue().toString()));
						}
					} else {
						areasId.add(0);
					}
				} else {
					areasId.add(Integer.parseInt(areaSelected));
				}
			}

			List<Integer> subAreasId = new ArrayList<Integer>();
			// System.out.println("subAreaSelected:" + subAreaSelected);
			if (subAreaSelected != null && !subAreaSelected.equals("")) {
				if (subAreaSelected.equals("t")) {
					if (subAreas != null && subAreas.size() > 0) {
						for (SelectItem item : subAreas) {
							subAreasId.add(Integer.parseInt(item.getValue().toString()));
						}
					} else {
						subAreasId.add(0);
					}
					// System.out.println(subAreasId.size());
				} else {
					// System.out.println("subAreaSelected:" + subAreaSelected);
					subAreasId.add(Integer.parseInt(subAreaSelected));
				}
			}

			/*
			 * candidates = candidateService.findList(areasId,subAreasId,
			 * recruiterId, interviewStateId, trainingStateId, ojtStateId,
			 * scheduledAtFormatter, createdAtFormatter,
			 * candidateDocumentNumberSearch, candidateNameSearch);
			 */

			candidateSelected = null;

		} catch (Exception e) {
			/*
			 * if (!(e instanceof RecruitmentException) && !(e instanceof
			 * SecurityExcepcion)) { e.printStackTrace(); }
			 */
			FacesMessage msg = new FacesMessage(e.getMessage(), e.getMessage());
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	public void beforeAdd() {

		try {
			initialize();

			if (interviewStates == null || interviewStates.size() == 0) {
				/*
				 * InterviewStateInterface interviewStateService =
				 * (InterviewStateInterface) new InitialContext(
				 * Util.getInitProperties()) .lookup(
				 * "java:global/RecruitmentEAR/RecruitmentEJB/InterviewStateBean"
				 * +
				 * "!com.returnsoft.resource.service.InterviewStateInterface");
				 * List<InterviewStateDto> dtos = interviewStateService
				 * .findIsPending();
				 */
				interviewStates = new ArrayList<SelectItem>();
				/*
				 * for (InterviewStateDto dto : dtos) { SelectItem item = new
				 * SelectItem(); item.setValue(dto.getId().toString());
				 * item.setLabel(dto.getName()); interviewStates.add(item); }
				 */
			}

			if (areas == null || areas.size() == 0) {

				/*
				 * SessionBean session = (SessionBean) FacesContext
				 * .getCurrentInstance().getExternalContext()
				 * .getSessionMap().get("sessionBean"); Integer recruiterId =
				 * session.getRecruiterId();
				 * 
				 * AreaInterface areaService = (AreaInterface) new
				 * InitialContext( Util.getInitProperties())
				 * .lookup("java:global/RecruitmentEAR/RecruitmentEJB/AreaBean"
				 * + "!com.returnsoft.resource.service.AreaInterface");
				 * 
				 * List<AreaDto> areasDto = areaService
				 * .findAreasByRecruiter(recruiterId);
				 */

				areas = new ArrayList<SelectItem>();
				/*
				 * for (AreaDto areaDto : areasDto) { SelectItem item = new
				 * SelectItem(); item.setValue(areaDto.getId().toString());
				 * item.setLabel(areaDto.getName()); areas.add(item); }
				 */
			}

			if (countries == null || countries.size() == 0) {
				/*
				 * CountryInterface countryService = (CountryInterface) new
				 * InitialContext( Util.getInitProperties()) .lookup(
				 * "java:global/RecruitmentEAR/RecruitmentEJB/CountryBean" +
				 * "!com.returnsoft.resource.service.CountryInterface");
				 * List<CountryDto> countriesDto = countryService.findAll();
				 */
				countries = new ArrayList<SelectItem>();
				/*
				 * for (CountryDto countryDto : countriesDto) { SelectItem item
				 * = new SelectItem();
				 * item.setValue(countryDto.getId().toString());
				 * item.setLabel(countryDto.getName()); countries.add(item); }
				 */
			}

			if (recruimentSources == null || recruimentSources.size() == 0) {
				/*
				 * RecruimentSourceInterface recruimentSourceService =
				 * (RecruimentSourceInterface) new InitialContext(
				 * Util.getInitProperties()) .lookup(
				 * "java:global/RecruitmentEAR/RecruitmentEJB/RecruimentSourceBean"
				 * +
				 * "!com.returnsoft.resource.service.RecruimentSourceInterface")
				 * ; List<RecruimentSourceDto> recruimentSourcesDto =
				 * recruimentSourceService .findAll();
				 */
				recruimentSources = new ArrayList<SelectItem>();
				/*
				 * for (RecruimentSourceDto recruimentSourceDto :
				 * recruimentSourcesDto) { SelectItem item = new SelectItem();
				 * item.setValue(recruimentSourceDto.getId().toString());
				 * item.setLabel(recruimentSourceDto.getName());
				 * recruimentSources.add(item); }
				 */
			}

			if (educationLevels == null || educationLevels.size() == 0) {
				/*
				 * EducationLevelInterface educationLevelService =
				 * (EducationLevelInterface) new InitialContext(
				 * Util.getInitProperties()) .lookup(
				 * "java:global/RecruitmentEAR/RecruitmentEJB/EducationLevelBean"
				 * +
				 * "!com.returnsoft.resource.service.EducationLevelInterface");
				 * List<EducationLevelDto> educationLevelsDto =
				 * educationLevelService .findAll();
				 */
				educationLevels = new ArrayList<SelectItem>();
				/*
				 * for (EducationLevelDto educationLevelDto :
				 * educationLevelsDto) { SelectItem item = new SelectItem();
				 * item.setValue(educationLevelDto.getId().toString());
				 * item.setLabel(educationLevelDto.getName());
				 * educationLevels.add(item); }
				 */
			}

			// candidateSelected = new CandidateDto();
			// interviewSelected = new InterviewDto();

		} catch (Exception e) {
			/*
			 * if (!(e instanceof RecruitmentException) && !(e instanceof
			 * SecurityExcepcion)) { e.printStackTrace(); }
			 */
			FacesMessage msg = new FacesMessage(e.getMessage(), e.getMessage());
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}

	}

	public void searchDepartments() {

		// System.out.println("ingreso a
		// searchsubdepartments"+departmentSelected);
		try {
			Integer countryId = Integer.parseInt(countrySelected);
			// /System.out.println(departmentId);
			if (countryId > 0) {
				/*
				 * DepartmentInterface departmentService = (DepartmentInterface)
				 * new InitialContext( Util.getInitProperties()) .lookup(
				 * "java:global/RecruitmentEAR/RecruitmentEJB/DepartmentBean" +
				 * "!com.returnsoft.resource.service.DepartmentInterface");
				 */
				/*
				 * List<DepartmentDto> departmentsDto = departmentService
				 * .findByCountry(countryId);
				 */
				/*
				 * departments = new ArrayList<SelectItem>(); for (DepartmentDto
				 * departmentDto : departmentsDto) { SelectItem item = new
				 * SelectItem(); item.setValue(departmentDto.getId());
				 * item.setLabel(departmentDto.getName());
				 * departments.add(item); }
				 */
				provinces = new ArrayList<SelectItem>();
				districts = new ArrayList<SelectItem>();
			} else {
				departments = new ArrayList<SelectItem>();
				provinces = new ArrayList<SelectItem>();
				districts = new ArrayList<SelectItem>();
			}

		} catch (Exception e) {
			/*
			 * if (!(e instanceof RecruitmentException) && !(e instanceof
			 * SecurityExcepcion)) { e.printStackTrace(); }
			 */
			FacesMessage msg = new FacesMessage(e.getMessage(), e.getMessage());
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	public void searchProvinces() {

		// System.out.println("ingreso a
		// searchsubdepartments"+departmentSelected);
		try {
			Integer departmentId = Integer.parseInt(departmentSelected);
			// /System.out.println(departmentId);
			if (departmentId > 0) {
				/*
				 * ProvinceInterface provinceService = (ProvinceInterface) new
				 * InitialContext( Util.getInitProperties()) .lookup(
				 * "java:global/RecruitmentEAR/RecruitmentEJB/ProvinceBean" +
				 * "!com.returnsoft.resource.service.ProvinceInterface");
				 * List<ProvinceDto> provincesDto = provinceService
				 * .findByDepartment(departmentId);
				 */
				provinces = new ArrayList<SelectItem>();
				/*
				 * for (ProvinceDto provinceDto : provincesDto) { SelectItem
				 * item = new SelectItem(); item.setValue(provinceDto.getId());
				 * item.setLabel(provinceDto.getName()); provinces.add(item); }
				 */
				districts = new ArrayList<SelectItem>();
			} else {
				provinces = new ArrayList<SelectItem>();
				districts = new ArrayList<SelectItem>();
			}

		} catch (Exception e) {
			/*
			 * if (!(e instanceof RecruitmentException) && !(e instanceof
			 * SecurityExcepcion)) { e.printStackTrace(); }
			 */
			FacesMessage msg = new FacesMessage(e.getMessage(), e.getMessage());
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	public void searchDistricts() {

		// System.out.println("ingreso a
		// searchsubdepartments"+departmentSelected);
		try {
			Integer provinceId = Integer.parseInt(provinceSelected);
			// /System.out.println(departmentId);
			if (provinceId > 0) {
				/*
				 * DistrictInterface districtService = (DistrictInterface) new
				 * InitialContext( Util.getInitProperties()) .lookup(
				 * "java:global/RecruitmentEAR/RecruitmentEJB/DistrictBean" +
				 * "!com.returnsoft.resource.service.DistrictInterface");
				 * List<DistrictDto> districtsDto = districtService
				 * .findByProvince(provinceId);
				 */
				districts = new ArrayList<SelectItem>();
				/*
				 * for (DistrictDto districtDto : districtsDto) { SelectItem
				 * item = new SelectItem(); item.setValue(districtDto.getId());
				 * item.setLabel(districtDto.getName()); districts.add(item); }
				 */
			} else {
				districts = new ArrayList<SelectItem>();
			}

		} catch (Exception e) {
			/*
			 * if (!(e instanceof RecruitmentException) && !(e instanceof
			 * SecurityExcepcion)) { e.printStackTrace(); }
			 */
			FacesMessage msg = new FacesMessage(e.getMessage(), e.getMessage());
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	public void calculateAge() {
		try {

			System.out.println("ingreso a calculate Age");

			if (candidateSelected != null && candidateSelected.getBornDate() != null) {

				Date current = new Date();
				Long difference = current.getTime() - candidateSelected.getBornDate().getTime();
				Calendar differenceCalendar = Calendar.getInstance();
				differenceCalendar.setTimeInMillis(difference);
				age = (differenceCalendar.get(Calendar.YEAR) - 1970) + " años.";

			}

		} catch (Exception e) {
			/*
			 * if (!(e instanceof RecruitmentException) && !(e instanceof
			 * SecurityExcepcion)) { e.printStackTrace(); }
			 */
			FacesMessage msg = new FacesMessage(e.getMessage(), e.getMessage());
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	public String add() {
		try {

			if (countrySelected != null && countrySelected.length() > 0) {
				Integer countryId = Integer.parseInt(countrySelected);
				Country country = new Country();
				country.setId(countryId);
				candidateSelected.setCountry(country);
			}

			if (departmentSelected != null && departmentSelected.length() > 0) {
				Integer departmentId = Integer.parseInt(departmentSelected);
				Department department = new Department();
				department.setId(departmentId);
				candidateSelected.setDepartment(department);
			}

			if (provinceSelected != null && provinceSelected.length() > 0) {
				Integer provinceId = Integer.parseInt(provinceSelected);
				Province province = new Province();
				province.setId(provinceId);
				candidateSelected.setProvince(province);
			}

			if (districtSelected != null && districtSelected.length() > 0) {
				Integer districtId = Integer.parseInt(districtSelected);
				District district = new District();
				district.setId(districtId);
				candidateSelected.setDistrict(district);
			}

			if (recruimentSourceSelected != null && recruimentSourceSelected.length() > 0) {
				Integer recruimentSourceId = Integer.parseInt(recruimentSourceSelected);
				RecruimentSource recruimentSource = new RecruimentSource();
				recruimentSource.setId(recruimentSourceId);
				candidateSelected.setRecruimentSource(recruimentSource);
			}

			if (educationLevelSelected != null && educationLevelSelected.length() > 0) {
				Integer educationLevelId = Integer.parseInt(educationLevelSelected);
				EducationLevel educationLevel = new EducationLevel();
				educationLevel.setId(educationLevelId);
				candidateSelected.setEducationLevel(educationLevel);
			}

			if (subAreaSelected != null && subAreaSelected.length() > 0) {
				Integer subAreaId = Integer.parseInt(subAreaSelected);
				Area subArea = new Area();
				subArea.setId(subAreaId);
				candidateSelected.setArea(subArea);
			}

			if (interviewStateSelected != null && interviewStateSelected.length() > 0
					&& interviewStateSelected.equals("a")) {
				candidateSelected.setScheduledAt(scheduledAt);
			}

			SessionBean session = (SessionBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("sessionBean");
			Integer recruiterId = session.getUser().getId();
			User recruiter = new User();
			recruiter.setId(recruiterId);
			candidateSelected.setRecruiter(recruiter);
			candidateSelected.setCreatedAt(new Date());

			/*
			 * CandidateInterface candidateService = (CandidateInterface) new
			 * InitialContext( Util.getInitProperties())
			 * .lookup("java:global/RecruitmentEAR/RecruitmentEJB/CandidateBean"
			 * + "!com.returnsoft.resource.service.CandidateInterface");
			 * candidateSelected = candidateService.add(candidateSelected);
			 */

			String confirmMessage = "Se creó satisfactoriamente el candidato con id:" + candidateSelected.getId();

			// CandidateStateDto state = new CandidateStateDto();
			if (interviewStateSelected != null && interviewStateSelected.length() > 0
					&& !interviewStateSelected.equals("a")) {
				Interview interview = new Interview();

				interview.setRecruiter(recruiter);
				interview.setCandidate(candidateSelected);
				interview.setCreatedAt(new Date());
				interview.setInterviewedAt(interviewedAt);
				InterviewState interviewStateDto = new InterviewState();
				interviewStateDto.setId(Integer.parseInt(interviewStateSelected));
				interview.setInterviewState(interviewStateDto);

				/*
				 * InterviewInterface interviewService = (InterviewInterface)
				 * new InitialContext( Util.getInitProperties()) .lookup(
				 * "java:global/RecruitmentEAR/RecruitmentEJB/InterviewBean" +
				 * "!com.returnsoft.resource.service.InterviewInterface");
				 * interview = interviewService.add(interview);
				 */

				confirmMessage += " y entrevista con id:" + interview.getId();

			}

			// SE IMPRIME MENSAJE DE CONFIRMACION
			FacesMessage msg = new FacesMessage(confirmMessage);
			msg.setSeverity(FacesMessage.SEVERITY_INFO);
			FacesContext.getCurrentInstance().addMessage(null, msg);

			return "search_candidate";

		} catch (Exception e) {
			/*
			 * if (!(e instanceof RecruitmentException) && !(e instanceof
			 * SecurityExcepcion)) { e.printStackTrace(); }
			 */
			FacesMessage msg = new FacesMessage(e.getMessage());
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return null;
		}
	}

	public void beforeEdit(ComponentSystemEvent event) {

		System.out.println(event.toString());

		System.out.println("ingreso a beforeEditCandidate");
		try {

			// if (!FacesContext.getCurrentInstance().isPostback()) {

			// if (candidateSelected == null || candidateSelected.getId() == 0)
			// {

			Map<String, String> map = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
			String candidateIdRequest = (String) map.get("candidateId");

			if (candidateIdRequest != null && !candidateIdRequest.equals("")) {

				Integer candidateId = Integer.parseInt(candidateIdRequest);

				/*
				 * CandidateInterface candidateService = (CandidateInterface)
				 * new InitialContext( Util.getInitProperties()) .lookup(
				 * "java:global/RecruitmentEAR/RecruitmentEJB/CandidateBean" +
				 * "!com.returnsoft.resource.service.CandidateInterface");
				 * 
				 * candidateSelected = candidateService.findById(candidateId);
				 */

				if (areas == null || areas.size() == 0) {

					SessionBean session = (SessionBean) FacesContext.getCurrentInstance().getExternalContext()
							.getSessionMap().get("sessionBean");
					Integer recruiterId = session.getUser().getId();

					/*
					 * AreaInterface areaService = (AreaInterface) new
					 * InitialContext( Util.getInitProperties()) .lookup(
					 * "java:global/RecruitmentEAR/RecruitmentEJB/AreaBean" +
					 * "!com.returnsoft.resource.service.AreaInterface");
					 * 
					 * List<AreaDto> areasDto = areaService
					 * .findAreasByRecruiter(recruiterId);
					 */

					areas = new ArrayList<SelectItem>();
					/*
					 * for (AreaDto areaDto : areasDto) { SelectItem item = new
					 * SelectItem(); item.setValue(areaDto.getId().toString());
					 * item.setLabel(areaDto.getName()); areas.add(item); }
					 */
				}

				Boolean isSubAreaPermission = Boolean.FALSE;
				Boolean isAreaPermission = Boolean.FALSE;

				if (candidateSelected.getArea() != null && candidateSelected.getArea().getId() > 0) {
					if (candidateSelected.getArea().getArea() != null
							&& candidateSelected.getArea().getArea().getId() > 0) {

						// Boolean isAreaPermission=Boolean.FALSE;
						for (SelectItem areaItem : areas) {
							System.out.println("area:" + candidateSelected.getArea().getArea().getId());
							System.out.println("item:" + areaItem.getValue());
							if (areaItem.getValue().toString()
									.equals(candidateSelected.getArea().getArea().getId().toString())) {
								isAreaPermission = Boolean.TRUE;
								System.out.println("INGRESO!");
							}
						}

						if (isAreaPermission) {
							areaSelected = candidateSelected.getArea().getArea().getId().toString();
							searchSubAreas();

							// Boolean isSubAreaPermission=Boolean.FALSE;
							for (SelectItem subAreaItem : subAreas) {
								if (subAreaItem.getValue().toString()
										.equals(candidateSelected.getArea().getId().toString())) {
									isSubAreaPermission = Boolean.TRUE;
								}
							}

							if (isSubAreaPermission) {
								subAreaSelected = candidateSelected.getArea().getId().toString();
							} else {
								// MENSAJE DE ERROR, NO TIENE PERMISOS PARA EL
								// SUBAREA
								FacesMessage msg = new FacesMessage(
										"No tiene permisos para el subarea " + candidateSelected.getArea().getName());
								msg.setSeverity(FacesMessage.SEVERITY_ERROR);
								FacesContext.getCurrentInstance().addMessage(null, msg);
								candidateSelected = null;
							}

						} else {
							// MENSAJE DE ERROR, NO TIENE PERMISOS PARA EL AREA
							FacesMessage msg = new FacesMessage("No tiene permisos para el area "
									+ candidateSelected.getArea().getArea().getName());
							msg.setSeverity(FacesMessage.SEVERITY_ERROR);
							FacesContext.getCurrentInstance().addMessage(null, msg);
							candidateSelected = null;
						}

					}
				}

				if (isSubAreaPermission && isAreaPermission) {

					if (interviewStates == null || interviewStates.size() == 0) {
						/*
						 * InterviewStateInterface interviewStateService =
						 * (InterviewStateInterface) new InitialContext(
						 * Util.getInitProperties()) .lookup(
						 * "java:global/RecruitmentEAR/RecruitmentEJB/InterviewStateBean"
						 * +
						 * "!com.returnsoft.resource.service.InterviewStateInterface"
						 * ); List<InterviewStateDto> dtos =
						 * interviewStateService .findIsPending();
						 */
						interviewStates = new ArrayList<SelectItem>();
						/*
						 * for (InterviewStateDto dto : dtos) { SelectItem item
						 * = new SelectItem();
						 * item.setValue(dto.getId().toString());
						 * item.setLabel(dto.getName());
						 * interviewStates.add(item); }
						 */
					}

					if (countries == null || countries.size() == 0) {
						/*
						 * CountryInterface countryService = (CountryInterface)
						 * new InitialContext( Util.getInitProperties())
						 * .lookup(
						 * "java:global/RecruitmentEAR/RecruitmentEJB/CountryBean"
						 * +
						 * "!com.returnsoft.resource.service.CountryInterface");
						 * List<CountryDto> countriesDto =
						 * countryService.findAll();
						 */
						countries = new ArrayList<SelectItem>();
						/*
						 * for (CountryDto countryDto : countriesDto) {
						 * SelectItem item = new SelectItem();
						 * item.setValue(countryDto.getId().toString());
						 * item.setLabel(countryDto.getName());
						 * countries.add(item); }
						 */
					}

					if (recruimentSources == null || recruimentSources.size() == 0) {
						/*
						 * RecruimentSourceInterface recruimentSourceService =
						 * (RecruimentSourceInterface) new InitialContext(
						 * Util.getInitProperties()) .lookup(
						 * "java:global/RecruitmentEAR/RecruitmentEJB/RecruimentSourceBean"
						 * +
						 * "!com.returnsoft.resource.service.RecruimentSourceInterface"
						 * ); List<RecruimentSourceDto> recruimentSourcesDto =
						 * recruimentSourceService .findAll();
						 */
						recruimentSources = new ArrayList<SelectItem>();
						/*
						 * for (RecruimentSourceDto recruimentSourceDto :
						 * recruimentSourcesDto) { SelectItem item = new
						 * SelectItem();
						 * item.setValue(recruimentSourceDto.getId().toString())
						 * ; item.setLabel(recruimentSourceDto.getName());
						 * recruimentSources.add(item); }
						 */
					}

					if (educationLevels == null || educationLevels.size() == 0) {
						/*
						 * EducationLevelInterface educationLevelService =
						 * (EducationLevelInterface) new InitialContext(
						 * Util.getInitProperties()) .lookup(
						 * "java:global/RecruitmentEAR/RecruitmentEJB/EducationLevelBean"
						 * +
						 * "!com.returnsoft.resource.service.EducationLevelInterface"
						 * ); List<EducationLevelDto> educationLevelsDto =
						 * educationLevelService .findAll();
						 */
						educationLevels = new ArrayList<SelectItem>();
						/*
						 * for (EducationLevelDto educationLevelDto :
						 * educationLevelsDto) { SelectItem item = new
						 * SelectItem();
						 * item.setValue(educationLevelDto.getId().toString());
						 * item.setLabel(educationLevelDto.getName());
						 * educationLevels.add(item); }
						 */
					}

					initialize();

					calculateAge();

					// VERIFICA SI ES AGENDADO
					if (candidateSelected.getScheduledAt() != null) {
						isScheduled = Boolean.TRUE;
						scheduledAt = candidateSelected.getScheduledAt();
						interviewStateSelected = "a";
					} else {

						// VERIFICA ENTREVISTA EN PROCESO
						/*
						 * InterviewInterface interviewService =
						 * (InterviewInterface) new InitialContext(
						 * Util.getInitProperties()) .lookup(
						 * "java:global/RecruitmentEAR/RecruitmentEJB/InterviewBean"
						 * +
						 * "!com.returnsoft.resource.service.InterviewInterface"
						 * ); List<InterviewDto> interviewsFound =
						 * interviewService
						 * .findPendingsByCandidateId(candidateId);
						 */
						/*
						 * if (interviewsFound != null && interviewsFound.size()
						 * > 0) { isPending = Boolean.TRUE; interviewedAt =
						 * interviewsFound.get(0) .getInterviewedAt();
						 * interviewStateSelected = interviewsFound.get(0)
						 * .getInterviewState().getId().toString(); } else {
						 * 
						 * // VERIFICA CAPACITACION EN PROCESO TrainingInterface
						 * trainingService = (TrainingInterface) new
						 * InitialContext( Util.getInitProperties()) .lookup(
						 * "java:global/RecruitmentEAR/RecruitmentEJB/TrainingBean"
						 * +
						 * "!com.returnsoft.resource.service.TrainingInterface")
						 * ; List<TrainingDto> trainingsFound = trainingService
						 * .findPendingsByCandidateId(candidateId); if
						 * (trainingsFound != null && trainingsFound.size() > 0)
						 * { isPending = Boolean.TRUE; trainingAt =
						 * trainingsFound.get(0) .getTrainingAt();
						 * 
						 * TrainingStateInterface trainingStateService =
						 * (TrainingStateInterface) new InitialContext(
						 * Util.getInitProperties()) .lookup(
						 * "java:global/RecruitmentEAR/RecruitmentEJB/TrainingStateBean"
						 * +
						 * "!com.returnsoft.resource.service.TrainingStateInterface"
						 * ); List<TrainingStateDto> dtos = trainingStateService
						 * .findIsPending(); interviewStates = new
						 * ArrayList<SelectItem>(); for (TrainingStateDto dto :
						 * dtos) { SelectItem item = new SelectItem();
						 * item.setValue(dto.getId().toString());
						 * item.setLabel(dto.getName());
						 * interviewStates.add(item); } interviewStateSelected =
						 * trainingsFound.get(0)
						 * .getTrainingState().getId().toString();
						 * 
						 * } else { // VERIFICA OJT EN PROCESO OjtInterface
						 * ojtService = (OjtInterface) new InitialContext(
						 * Util.getInitProperties()) .lookup(
						 * "java:global/RecruitmentEAR/RecruitmentEJB/OjtBean" +
						 * "!com.returnsoft.resource.service.OjtInterface");
						 * List<OjtDto> ojtsFound = ojtService
						 * .findPendingsByCandidateId(candidateId); if
						 * (ojtsFound != null && ojtsFound.size() > 0) {
						 * isPending = Boolean.TRUE; ojtAt =
						 * ojtsFound.get(0).getOjtAt();
						 * 
						 * OjtStateInterface ojtStateService =
						 * (OjtStateInterface) new InitialContext(
						 * Util.getInitProperties()) .lookup(
						 * "java:global/RecruitmentEAR/RecruitmentEJB/OjtStateBean"
						 * +
						 * "!com.returnsoft.resource.service.OjtStateInterface")
						 * ; List<OjtStateDto> dtos = ojtStateService
						 * .findIsPending(); interviewStates = new
						 * ArrayList<SelectItem>(); for (OjtStateDto dto : dtos)
						 * { SelectItem item = new SelectItem();
						 * item.setValue(dto.getId().toString());
						 * item.setLabel(dto.getName());
						 * interviewStates.add(item); } interviewStateSelected =
						 * ojtsFound.get(0) .getOjtState().getId().toString();
						 * 
						 * } }
						 * 
						 * }
						 */

					}

					if (candidateSelected.getCountry() != null && candidateSelected.getCountry().getId() > 0) {
						// SELECCIONA PAIS
						System.out.println("countrySelected:" + candidateSelected.getCountry().getId());
						countrySelected = candidateSelected.getCountry().getId().toString();
						// BUSCA DEPARTAMENTOS
						searchDepartments();
						if (candidateSelected.getDepartment() != null
								&& candidateSelected.getDepartment().getId() > 0) {
							// SELECCIONA DEPARTAMENTO
							departmentSelected = candidateSelected.getDepartment().getId().toString();
							// BUSCA PROVINCIAS
							searchProvinces();
							if (candidateSelected.getProvince() != null
									&& candidateSelected.getProvince().getId() > 0) {

								// SELECCIONA PROVINCIA
								provinceSelected = candidateSelected.getProvince().getId().toString();
								// BUSCA DISTRITOS
								searchDistricts();
								if (candidateSelected.getDistrict() != null
										&& candidateSelected.getDistrict().getId() > 0) {
									// SELECCIONA DISTRITO
									districtSelected = candidateSelected.getDistrict().getId().toString();
								}
							}

						}

					}

					if (candidateSelected.getRecruimentSource() != null
							&& candidateSelected.getRecruimentSource().getId() > 0) {
						recruimentSourceSelected = candidateSelected.getRecruimentSource().getId().toString();
						onChangeRecruitmentSource();
					}

					if (candidateSelected.getEducationLevel() != null
							&& candidateSelected.getEducationLevel().getId() > 0) {
						educationLevelSelected = candidateSelected.getEducationLevel().getId().toString();
					}
				}

				/*
				 * if (candidateSelected.getArea() != null &&
				 * candidateSelected.getArea().getId() > 0) { areaSelected =
				 * candidateSelected.getArea().getArea() .getId().toString();
				 * searchSubAreas(); subAreaSelected =
				 * candidateSelected.getArea().getId() .toString(); }
				 */

			} /*
				 * else { FacesMessage msg = new FacesMessage(
				 * "No existe un postulante seleccionado.");
				 * msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				 * FacesContext.getCurrentInstance().addMessage(null, msg); }
				 */
			// }

		} catch (Exception e) {
			/*
			 * if (!(e instanceof RecruitmentException) && !(e instanceof
			 * SecurityExcepcion)) { e.printStackTrace(); }
			 */
			FacesMessage msg = new FacesMessage(e.getMessage(), e.getMessage());
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	public String edit() {
		try {

			System.out.println("Ingreso a edit candidate");

			if (candidateSelected != null && candidateSelected.getId() > 0) {

				// SE EDITA POSTULANTE

				if (countrySelected != null && countrySelected.length() > 0) {
					Integer countryId = Integer.parseInt(countrySelected);
					Country country = new Country();
					country.setId(countryId);
					candidateSelected.setCountry(country);
				}

				if (departmentSelected != null && departmentSelected.length() > 0) {
					Integer departmentId = Integer.parseInt(departmentSelected);
					Department department = new Department();
					department.setId(departmentId);
					candidateSelected.setDepartment(department);
				}

				if (provinceSelected != null && provinceSelected.length() > 0) {
					Integer provinceId = Integer.parseInt(provinceSelected);
					Province province = new Province();
					province.setId(provinceId);
					candidateSelected.setProvince(province);
				}

				if (districtSelected != null && districtSelected.length() > 0) {
					Integer districtId = Integer.parseInt(districtSelected);
					District district = new District();
					district.setId(districtId);
					candidateSelected.setDistrict(district);
				}

				if (recruimentSourceSelected != null && recruimentSourceSelected.length() > 0) {
					Integer recruimentSourceId = Integer.parseInt(recruimentSourceSelected);
					RecruimentSource recruimentSource = new RecruimentSource();
					recruimentSource.setId(recruimentSourceId);
					candidateSelected.setRecruimentSource(recruimentSource);
				}

				if (educationLevelSelected != null && educationLevelSelected.length() > 0) {
					Integer educationLevelId = Integer.parseInt(educationLevelSelected);
					EducationLevel educationLevel = new EducationLevel();
					educationLevel.setId(educationLevelId);
					candidateSelected.setEducationLevel(educationLevel);
				}

				if (subAreaSelected != null && subAreaSelected.length() > 0) {
					Integer subAreaId = Integer.parseInt(subAreaSelected);
					Area subArea = new Area();
					subArea.setId(subAreaId);
					candidateSelected.setArea(subArea);
				}

				// VERIFICA SI ES AGENDADO
				if (interviewStateSelected != null && interviewStateSelected.length() > 0
						&& !interviewStateSelected.equals("a")) {

					candidateSelected.setScheduledAt(null);

				} else {
					candidateSelected.setScheduledAt(scheduledAt);

				}

				/*
				 * CandidateInterface candidateService = (CandidateInterface)
				 * new InitialContext( Util.getInitProperties()) .lookup(
				 * "java:global/RecruitmentEAR/RecruitmentEJB/CandidateBean" +
				 * "!com.returnsoft.resource.service.CandidateInterface");
				 * 
				 * candidateSelected = candidateService.edit(candidateSelected);
				 */

				String confirmMessage = "Se editó satisfactoriamente el postulante con id:" + candidateSelected.getId();

				// //////////////////////////

				if (interviewStateSelected != null && interviewStateSelected.length() > 0
						&& !interviewStateSelected.equals("a")) {

					// VERIFICA ENTREVISTA EN PROCESO
					/*
					 * InterviewInterface interviewService =
					 * (InterviewInterface) new InitialContext(
					 * Util.getInitProperties()) .lookup(
					 * "java:global/RecruitmentEAR/RecruitmentEJB/InterviewBean"
					 * + "!com.returnsoft.resource.service.InterviewInterface");
					 * List<InterviewDto> interviewsFound = interviewService
					 * .findPendingsByCandidateId(candidateSelected .getId());
					 */

					/*
					 * if (interviewsFound == null || interviewsFound.size() ==
					 * 0) { // VERIFICA CAPACITACION EN PROCESO
					 * TrainingInterface trainingService = (TrainingInterface)
					 * new InitialContext( Util.getInitProperties()) .lookup(
					 * "java:global/RecruitmentEAR/RecruitmentEJB/TrainingBean"
					 * + "!com.returnsoft.resource.service.TrainingInterface");
					 * List<TrainingDto> trainingsFound = trainingService
					 * .findPendingsByCandidateId(candidateSelected .getId());
					 * if (trainingsFound == null || trainingsFound.size() == 0)
					 * { // VERIFICA OJT EN PROCESO OjtInterface ojtService =
					 * (OjtInterface) new InitialContext(
					 * Util.getInitProperties()) .lookup(
					 * "java:global/RecruitmentEAR/RecruitmentEJB/OjtBean" +
					 * "!com.returnsoft.resource.service.OjtInterface");
					 * List<OjtDto> ojtsFound = ojtService
					 * .findPendingsByCandidateId(candidateSelected .getId());
					 * if (ojtsFound == null || ojtsFound.size() == 0) { // SE
					 * CREA ENTREVISTA InterviewDto interview = new
					 * InterviewDto(); SessionBean session = (SessionBean)
					 * FacesContext .getCurrentInstance()
					 * .getExternalContext().getSessionMap()
					 * .get("sessionBean"); Integer recruiterId =
					 * session.getRecruiterId(); RecruiterDto recruiter = new
					 * RecruiterDto(); recruiter.setId(recruiterId);
					 * interview.setRecruiter(recruiter);
					 * interview.setCandidate(candidateSelected);
					 * interview.setCreatedAt(new Date());
					 * interview.setInterviewedAt(interviewedAt);
					 * InterviewStateDto interviewState = new
					 * InterviewStateDto(); interviewState.setId(Integer
					 * .parseInt(interviewStateSelected));
					 * interview.setInterviewState(interviewState);
					 * 
					 * 
					 * interview = interviewService.add(interview);
					 * 
					 * confirmMessage += " y se creo entrevista con id:" +
					 * interview.getId();
					 * 
					 * } } }
					 */
				}

				// SE IMPRIME MENSAJE DE CONFIRMACION
				FacesMessage msg = new FacesMessage(confirmMessage);
				msg.setSeverity(FacesMessage.SEVERITY_INFO);
				FacesContext.getCurrentInstance().addMessage(null, msg);

			} else {
				// SE IMPRIME MENSAJE DE CONFIRMACION
				FacesMessage msg = new FacesMessage("Seleccione un postulante.");
				msg.setSeverity(FacesMessage.SEVERITY_WARN);
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}
			return "search_candidate";

		} catch (Exception e) {
			/*
			 * if (!(e instanceof RecruitmentException) && !(e instanceof
			 * SecurityExcepcion)) { e.printStackTrace(); }
			 */
			FacesMessage msg = new FacesMessage(e.getMessage(), e.getMessage());
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return null;
		}
	}

	public void onChangeState() {
		try {

			System.out.println("ingreso a onChangeState");
			System.out.println(interviewStateSelected);
			if (interviewStateSelected != null && !interviewStateSelected.equals("")) {

				if (interviewStateSelected.equals("a")) {
					isScheduled = Boolean.TRUE;
					isPending = Boolean.FALSE;
					inOnChange = Boolean.TRUE;
				} else {
					isScheduled = Boolean.FALSE;
					isPending = Boolean.TRUE;
					inOnChange = Boolean.TRUE;
				}

			} else {
				isScheduled = Boolean.FALSE;
				isPending = Boolean.FALSE;
				inOnChange = Boolean.FALSE;
			}

		} catch (Exception e) {
			/*
			 * if (!(e instanceof RecruitmentException) && !(e instanceof
			 * SecurityExcepcion)) { e.printStackTrace(); }
			 */
			FacesMessage msg = new FacesMessage(e.getMessage(), e.getMessage());
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	public void onChangeRecruitmentSource() {
		try {

			System.out.println("ingreso a onChangeRecruitmentSource");
			System.out.println(recruimentSourceSelected);

			Integer recruitmentSourceId = Integer.parseInt(recruimentSourceSelected);

			/*
			 * RecruimentSourceInterface recruimentSourceService =
			 * (RecruimentSourceInterface) new InitialContext(
			 * Util.getInitProperties()) .lookup(
			 * "java:global/RecruitmentEAR/RecruitmentEJB/RecruimentSourceBean"
			 * + "!com.returnsoft.resource.service.RecruimentSourceInterface");
			 * RecruimentSourceDto recruimentSourcesDto =
			 * recruimentSourceService.findById(recruitmentSourceId);
			 */
			/*
			 * if (recruimentSourcesDto.getIsFlier()!=null &&
			 * recruimentSourcesDto.getIsFlier()) {
			 * 
			 * isFlier=Boolean.TRUE; isJobFair=Boolean.FALSE;
			 * isReferred=Boolean.FALSE;
			 * 
			 * }else if(recruimentSourcesDto.getIsJobFair()!=null &&
			 * recruimentSourcesDto.getIsJobFair()){ isFlier=Boolean.FALSE;
			 * isJobFair=Boolean.TRUE; isReferred=Boolean.FALSE;
			 * 
			 * }else if(recruimentSourcesDto.getIsReferred()!=null &&
			 * recruimentSourcesDto.getIsReferred()){ isFlier=Boolean.FALSE;
			 * isJobFair=Boolean.FALSE; isReferred=Boolean.TRUE;
			 * 
			 * }else{ isFlier=Boolean.FALSE; isJobFair=Boolean.FALSE;
			 * isReferred=Boolean.FALSE; }
			 */

		} catch (Exception e) {
			/*
			 * if (!(e instanceof RecruitmentException) && !(e instanceof
			 * SecurityExcepcion)) { e.printStackTrace(); }
			 */
			FacesMessage msg = new FacesMessage(e.getMessage(), e.getMessage());
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	public List<SelectItem> getCountries() {
		return countries;
	}

	public void setCountries(List<SelectItem> countries) {
		this.countries = countries;
	}

	public List<SelectItem> getProvinces() {
		return provinces;
	}

	public void setProvinces(List<SelectItem> provinces) {
		this.provinces = provinces;
	}

	public List<SelectItem> getDistricts() {
		return districts;
	}

	public void setDistricts(List<SelectItem> districts) {
		this.districts = districts;
	}

	public String getCountrySelected() {
		return countrySelected;
	}

	public void setCountrySelected(String countrySelected) {
		this.countrySelected = countrySelected;
	}

	public String getProvinceSelected() {
		return provinceSelected;
	}

	public void setProvinceSelected(String provinceSelected) {
		this.provinceSelected = provinceSelected;
	}

	public String getDistrictSelected() {
		return districtSelected;
	}

	public void setDistrictSelected(String districtSelected) {
		this.districtSelected = districtSelected;
	}

	public List<SelectItem> getRecruimentSources() {
		return recruimentSources;
	}

	public void setRecruimentSources(List<SelectItem> recruimentSources) {
		this.recruimentSources = recruimentSources;
	}

	public String getRecruimentSourceSelected() {
		return recruimentSourceSelected;
	}

	public void setRecruimentSourceSelected(String recruimentSourceSelected) {
		this.recruimentSourceSelected = recruimentSourceSelected;
	}

	public List<Candidate> getCandidates() {
		return candidates;
	}

	public void setCandidates(List<Candidate> candidates) {
		this.candidates = candidates;
	}

	public Candidate getCandidateSelected() {
		return candidateSelected;
	}

	public void setCandidateSelected(Candidate candidateSelected) {
		this.candidateSelected = candidateSelected;
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

	public String getSubAreaSelected() {
		return subAreaSelected;
	}

	public void setSubAreaSelected(String subAreaSelected) {
		this.subAreaSelected = subAreaSelected;
	}

	public String getAreaSelected() {
		return areaSelected;
	}

	public void setAreaSelected(String areaSelected) {
		this.areaSelected = areaSelected;
	}

	public List<SelectItem> getDepartments() {
		return departments;
	}

	public void setDepartments(List<SelectItem> departments) {
		this.departments = departments;
	}

	public String getDepartmentSelected() {
		return departmentSelected;
	}

	public void setDepartmentSelected(String departmentSelected) {
		this.departmentSelected = departmentSelected;
	}

	public String getCandidateDocumentNumberSearch() {
		return candidateDocumentNumberSearch;
	}

	public void setCandidateDocumentNumberSearch(String candidateDocumentNumberSearch) {
		this.candidateDocumentNumberSearch = candidateDocumentNumberSearch;
	}

	/*
	 * public String getBornDateFormatted() { return bornDateFormatted; }
	 * 
	 * public void setBornDateFormatted(String bornDateFormatted) {
	 * this.bornDateFormatted = bornDateFormatted; }
	 */

	public String getCandidateNameSearch() {
		return candidateNameSearch;
	}

	public void setCandidateNameSearch(String candidateNameSearch) {
		this.candidateNameSearch = candidateNameSearch;
	}

	public String getAge() {
		return age;
	}

	public Date getMinDate() {
		return minDate;
	}

	public void setMinDate(Date minDate) {
		this.minDate = minDate;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public Date getMaxDate() {
		return maxDate;
	}

	public void setMaxDate(Date maxDate) {
		this.maxDate = maxDate;
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

	/*
	 * public List<SelectItem> getStates() { return states; }
	 * 
	 * public void setStates(List<SelectItem> states) { this.states = states; }
	 * 
	 * public String getStateSelected() { return stateSelected; }
	 * 
	 * public void setStateSelected(String stateSelected) { this.stateSelected =
	 * stateSelected; }
	 */

	public Date getScheduledAtSearch() {
		return scheduledAtSearch;
	}

	public void setScheduledAtSearch(Date scheduledAtSearch) {
		this.scheduledAtSearch = scheduledAtSearch;
	}

	/*
	 * public InterviewDto getInterviewSelected() { return interviewSelected; }
	 * 
	 * public void setInterviewSelected(InterviewDto interviewSelected) {
	 * this.interviewSelected = interviewSelected; }
	 */

	public List<SelectItem> getEducationLevels() {
		return educationLevels;
	}

	public void setEducationLevels(List<SelectItem> educationLevels) {
		this.educationLevels = educationLevels;
	}

	public String getEducationLevelSelected() {
		return educationLevelSelected;
	}

	public void setEducationLevelSelected(String educationLevelSelected) {
		this.educationLevelSelected = educationLevelSelected;
	}

	public List<SelectItem> getInterviewStates() {
		return interviewStates;
	}

	public void setInterviewStates(List<SelectItem> interviewStates) {
		this.interviewStates = interviewStates;
	}

	public String getInterviewStateSelected() {
		return interviewStateSelected;
	}

	public void setInterviewStateSelected(String interviewStateSelected) {
		this.interviewStateSelected = interviewStateSelected;
	}

	public List<SelectItem> getTrainingStates() {
		return trainingStates;
	}

	public void setTrainingStates(List<SelectItem> trainingStates) {
		this.trainingStates = trainingStates;
	}

	public String getTrainingStateSelected() {
		return trainingStateSelected;
	}

	public void setTrainingStateSelected(String trainingStateSelected) {
		this.trainingStateSelected = trainingStateSelected;
	}

	public List<SelectItem> getOjtStates() {
		return ojtStates;
	}

	public void setOjtStates(List<SelectItem> ojtStates) {
		this.ojtStates = ojtStates;
	}

	public String getOjtStateSelected() {
		return ojtStateSelected;
	}

	public void setOjtStateSelected(String ojtStateSelected) {
		this.ojtStateSelected = ojtStateSelected;
	}

	public Date getInterviewedAt() {
		return interviewedAt;
	}

	public void setInterviewedAt(Date interviewedAt) {
		this.interviewedAt = interviewedAt;
	}

	public Date getScheduledAt() {
		return scheduledAt;
	}

	public void setScheduledAt(Date scheduledAt) {
		this.scheduledAt = scheduledAt;
	}

	public Boolean getIsScheduled() {
		return isScheduled;
	}

	public void setIsScheduled(Boolean isScheduled) {
		this.isScheduled = isScheduled;
	}

	public Boolean getIsPending() {
		return isPending;
	}

	public void setIsPending(Boolean isPending) {
		this.isPending = isPending;
	}

	public Date getOjtAt() {
		return ojtAt;
	}

	public void setOjtAt(Date ojtAt) {
		this.ojtAt = ojtAt;
	}

	public Date getTrainingAt() {
		return trainingAt;
	}

	public void setTrainingAt(Date trainingAt) {
		this.trainingAt = trainingAt;
	}

	public Boolean getInOnChange() {
		return inOnChange;
	}

	public void setInOnChange(Boolean inOnChange) {
		this.inOnChange = inOnChange;
	}

	public Boolean getIsJobFair() {
		return isJobFair;
	}

	public void setIsJobFair(Boolean isJobFair) {
		this.isJobFair = isJobFair;
	}

	public Boolean getIsFlier() {
		return isFlier;
	}

	public void setIsFlier(Boolean isFlier) {
		this.isFlier = isFlier;
	}

	public Boolean getIsReferred() {
		return isReferred;
	}

	public void setIsReferred(Boolean isReferred) {
		this.isReferred = isReferred;
	}

	public Date getCreatedAtSearch() {
		return createdAtSearch;
	}

	public void setCreatedAtSearch(Date createdAtSearch) {
		this.createdAtSearch = createdAtSearch;
	}

}
