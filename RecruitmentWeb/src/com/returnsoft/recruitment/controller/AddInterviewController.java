package com.returnsoft.recruitment.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

import com.returnsoft.recruitment.entity.Candidate;
import com.returnsoft.recruitment.entity.Country;
import com.returnsoft.recruitment.entity.Department;
import com.returnsoft.recruitment.entity.District;
import com.returnsoft.recruitment.entity.EducationLevel;
import com.returnsoft.recruitment.entity.Interview;
import com.returnsoft.recruitment.entity.InterviewState;
import com.returnsoft.recruitment.entity.Province;
import com.returnsoft.recruitment.entity.RecruitmentSource;
import com.returnsoft.recruitment.entity.Requirement;
import com.returnsoft.recruitment.exception.UserLoggedNotFoundException;
import com.returnsoft.recruitment.service.CandidateService;
import com.returnsoft.recruitment.service.CountryService;
import com.returnsoft.recruitment.service.DepartmentService;
import com.returnsoft.recruitment.service.DistrictService;
import com.returnsoft.recruitment.service.EducationLevelService;
import com.returnsoft.recruitment.service.InterviewStateService;
import com.returnsoft.recruitment.service.ProvinceService;
import com.returnsoft.recruitment.service.RecruitmentSourceService;
import com.returnsoft.recruitment.service.RequirementService;
import com.returnsoft.recruitment.util.FacesUtil;
import com.returnsoft.recruitment.util.SessionBean;

@ManagedBean
@ViewScoped
public class AddInterviewController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8268384884754834040L;

	@Inject
	private FacesUtil facesUtil;

	@Inject
	private SessionBean sessionBean;

	private Interview interviewSelected;

	private List<SelectItem> interviewStates;
	private String interviewStateSelected;

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
	private List<SelectItem> educationLevels;
	private String educationLevelSelected;

	@EJB
	private InterviewStateService interviewStateService;

	@EJB
	private CountryService countryService;

	@EJB
	private RecruitmentSourceService recruimentSourceService;

	@EJB
	private EducationLevelService educationLevelService;

	@EJB
	private DepartmentService departmentService;

	@EJB
	private ProvinceService provinceService;

	@EJB
	private DistrictService districtService;
	
	@EJB
	private CandidateService candidateService;
	
	@EJB
	private RequirementService requirementService;

	private String age;
	private Date minDate;
	private Date maxDate;

	private Boolean isJobFair;
	private Boolean isFlier;
	private Boolean isReferred;

	private Boolean isScheduled;
	private Boolean isPending;
	private Boolean inOnChange;
	
	private String info;
	private String documentNumber;
	
	
	///////
	
	private List<SelectItem> requirements;
	private String requirementSelected;
	
	
	private String areaSelected;
	private String subAreaSelected;
	
	/////
	
	
	

	public String initialize() {
		try {

			if (sessionBean == null || sessionBean.getUser() == null || sessionBean.getUser().getId() == null) {
				throw new UserLoggedNotFoundException();
			}

			List<InterviewState> interviewStatesEntity = interviewStateService.findIsPendingAndScheduled();
			interviewStates = new ArrayList<SelectItem>();
			for (InterviewState dto : interviewStatesEntity) {
				SelectItem item = new SelectItem();
				item.setValue(dto.getId().toString());
				item.setLabel(dto.getName());
				interviewStates.add(item);
			}

			List<Country> countriesDto = countryService.findAll();
			System.out.println("countries: " + countriesDto.size());
			countries = new ArrayList<SelectItem>();
			for (Country countryDto : countriesDto) {
				SelectItem item = new SelectItem();
				item.setValue(countryDto.getId().toString());
				item.setLabel(countryDto.getName());
				countries.add(item);
			}

			List<RecruitmentSource> recruimentSourcesDto = recruimentSourceService.findAll();
			recruimentSources = new ArrayList<SelectItem>();
			for (RecruitmentSource recruimentSourceDto : recruimentSourcesDto) {
				SelectItem item = new SelectItem();
				item.setValue(recruimentSourceDto.getId().toString());
				item.setLabel(recruimentSourceDto.getName());
				recruimentSources.add(item);
			}

			List<EducationLevel> educationLevelsDto = educationLevelService.findAll();
			educationLevels = new ArrayList<SelectItem>();
			for (EducationLevel educationLevelDto : educationLevelsDto) {
				SelectItem item = new SelectItem();
				item.setValue(educationLevelDto.getId().toString());
				item.setLabel(educationLevelDto.getName());
				educationLevels.add(item);
			}
			
			List<Requirement> requirementsDto = requirementService.findByRecruiter(sessionBean.getUser().getId());
			requirements = new ArrayList<SelectItem>();
			for (Requirement requirementDto : requirementsDto) {
				SelectItem item = new SelectItem();
				item.setValue(requirementDto.getId().toString());
				item.setLabel(requirementDto.getCode());
				requirements.add(item);
			}
			

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

			interviewSelected = new Interview();
			interviewSelected.setCandidate(new Candidate());

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
	
	public void searchCandidate(){
		try {
			
			System.out.println("ingreso a searchCandidate");
			
			if (sessionBean == null || sessionBean.getUser() == null || sessionBean.getUser().getId() == null) {
				throw new UserLoggedNotFoundException();
			}
			
			if (documentNumber!=null && documentNumber.length()>0) {
				Candidate candidateFound = candidateService.findByDocumentNumber(documentNumber);
				if (candidateFound!=null) {
					info = "Postulante "+candidateFound.getId();
					interviewSelected.setCandidate(candidateFound);
					calculateAge();
					if (candidateFound.getCountry()!=null) {
						countrySelected=candidateFound.getCountry().getId().toString();
					}
					if (candidateFound.getDepartment()!=null) {
						departmentSelected=candidateFound.getDepartment().getId().toString();
					}
					if (candidateFound.getProvince()!=null) {
						provinceSelected=candidateFound.getProvince().getId().toString();
					}
					if (candidateFound.getDistrict()!=null) {
						districtSelected=candidateFound.getDistrict().getId().toString();
					}
					if (candidateFound.getEducationLevel()!=null) {
						educationLevelSelected=candidateFound.getEducationLevel().getId().toString();
					}
					
				}else{
					info = "Nuevo Postulante.";
					interviewSelected.setCandidate(new Candidate());
					countrySelected="";
					departmentSelected="";
					provinceSelected="";
					districtSelected="";
					educationLevelSelected="";
				}
			}else{
				facesUtil.sendErrorMessage("Ingrese número de documento.");	
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getClass().getSimpleName(), e.getMessage());
		}
	}

	public void searchDepartments() {

		// System.out.println("ingreso a
		// searchsubdepartments"+departmentSelected);
		try {

			if (sessionBean == null || sessionBean.getUser() == null || sessionBean.getUser().getId() == null) {
				throw new UserLoggedNotFoundException();
			}

			if (countrySelected != null) {

				Integer countryId = Integer.parseInt(countrySelected);
				// /System.out.println(departmentId);
				// if (countryId > 0) {

				/*
				 * DepartmentInterface departmentService = (DepartmentInterface)
				 * new InitialContext( Util.getInitProperties()) .lookup(
				 * "java:global/RecruitmentEAR/RecruitmentEJB/DepartmentBean" +
				 * "!com.returnsoft.resource.service.DepartmentInterface");
				 */

				List<Department> departmentsDto = departmentService.findByCountry(countryId);

				departments = new ArrayList<SelectItem>();
				for (Department departmentDto : departmentsDto) {
					SelectItem item = new SelectItem();
					item.setValue(departmentDto.getId());
					item.setLabel(departmentDto.getName());
					departments.add(item);
				}

				provinces = new ArrayList<SelectItem>();
				districts = new ArrayList<SelectItem>();
				// }

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
			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getClass().getSimpleName(), e.getMessage());
		}
	}

	public void searchProvinces() {

		// System.out.println("ingreso a
		// searchsubdepartments"+departmentSelected);
		try {

			if (sessionBean == null || sessionBean.getUser() == null || sessionBean.getUser().getId() == null) {
				throw new UserLoggedNotFoundException();
			}

			// /System.out.println(departmentId);
			if (departmentSelected != null) {

				Integer departmentId = Integer.parseInt(departmentSelected);

				/*
				 * ProvinceInterface provinceService = (ProvinceInterface) new
				 * InitialContext( Util.getInitProperties()) .lookup(
				 * "java:global/RecruitmentEAR/RecruitmentEJB/ProvinceBean" +
				 * "!com.returnsoft.resource.service.ProvinceInterface");
				 */
				List<Province> provincesDto = provinceService.findByDepartment(departmentId);

				provinces = new ArrayList<SelectItem>();

				for (Province provinceDto : provincesDto) {
					SelectItem item = new SelectItem();
					item.setValue(provinceDto.getId());
					item.setLabel(provinceDto.getName());
					provinces.add(item);
				}

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
			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getClass().getSimpleName(), e.getMessage());
		}
	}

	public void searchDistricts() {

		// System.out.println("ingreso a
		// searchsubdepartments"+departmentSelected);
		try {

			if (sessionBean == null || sessionBean.getUser() == null || sessionBean.getUser().getId() == null) {
				throw new UserLoggedNotFoundException();
			}

			// /System.out.println(departmentId);
			if (provinceSelected != null) {

				Integer provinceId = Integer.parseInt(provinceSelected);
				/*
				 * DistrictInterface districtService = (DistrictInterface) new
				 * InitialContext( Util.getInitProperties()) .lookup(
				 * "java:global/RecruitmentEAR/RecruitmentEJB/DistrictBean" +
				 * "!com.returnsoft.resource.service.DistrictInterface");
				 */
				List<District> districtsDto = districtService.findByProvince(provinceId);

				districts = new ArrayList<SelectItem>();

				for (District districtDto : districtsDto) {
					SelectItem item = new SelectItem();
					item.setValue(districtDto.getId());
					item.setLabel(districtDto.getName());
					districts.add(item);
				}

			} else {
				districts = new ArrayList<SelectItem>();
			}

		} catch (Exception e) {
			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getClass().getSimpleName(), e.getMessage());
		}
	}

	public void calculateAge() {
		try {

			System.out.println("ingreso a calculate Age");

			if (sessionBean == null || sessionBean.getUser() == null || sessionBean.getUser().getId() == null) {
				throw new UserLoggedNotFoundException();
			}

			if (interviewSelected != null && interviewSelected.getCandidate() != null
					&& interviewSelected.getCandidate().getBornDate() != null) {

				Date current = new Date();
				Long difference = current.getTime() - interviewSelected.getCandidate().getBornDate().getTime();
				Calendar differenceCalendar = Calendar.getInstance();
				differenceCalendar.setTimeInMillis(difference);
				age = (differenceCalendar.get(Calendar.YEAR) - 1970) + " años.";

			} else {
				age = "";
			}

		} catch (Exception e) {
			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getClass().getSimpleName(), e.getMessage());
		}
	}
	
	public void onChangeRequirement(){
		try {
			if (requirementSelected!=null && requirementSelected.length()>0) {
				Integer reqId = Integer.parseInt(requirementSelected);
				Requirement req = requirementService.findById(reqId);
				areaSelected =  req.getArea().getArea().getName();
				subAreaSelected =  req.getArea().getName();
			}else{
				areaSelected ="";
				subAreaSelected = "";
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getClass().getSimpleName(), e.getMessage());
		}
		
	}

	public void add() {
		try {

			if (sessionBean == null || sessionBean.getUser() == null || sessionBean.getUser().getId() == null) {
				throw new UserLoggedNotFoundException();
			}

			/*
			 * if (countrySelected != null && countrySelected.length() > 0) {
			 * Integer countryId = Integer.parseInt(countrySelected); Country
			 * country = new Country(); country.setId(countryId);
			 * candidateSelected.setCountry(country); }
			 * 
			 * if (departmentSelected != null && departmentSelected.length() >
			 * 0) { Integer departmentId = Integer.parseInt(departmentSelected);
			 * Department department = new Department();
			 * department.setId(departmentId);
			 * candidateSelected.setDepartment(department); }
			 * 
			 * if (provinceSelected != null && provinceSelected.length() > 0) {
			 * Integer provinceId = Integer.parseInt(provinceSelected); Province
			 * province = new Province(); province.setId(provinceId);
			 * candidateSelected.setProvince(province); }
			 * 
			 * if (districtSelected != null && districtSelected.length() > 0) {
			 * Integer districtId = Integer.parseInt(districtSelected); District
			 * district = new District(); district.setId(districtId);
			 * candidateSelected.setDistrict(district); }
			 */

			/*
			 * if (recruimentSourceSelected != null &&
			 * recruimentSourceSelected.length() > 0) { Integer
			 * recruimentSourceId = Integer.parseInt(recruimentSourceSelected);
			 * RecruimentSource recruimentSource = new RecruimentSource();
			 * recruimentSource.setId(recruimentSourceId);
			 * candidateSelected.setRecruimentSource(recruimentSource); }
			 */

			/*
			 * if (educationLevelSelected != null &&
			 * educationLevelSelected.length() > 0) { Integer educationLevelId =
			 * Integer.parseInt(educationLevelSelected); EducationLevel
			 * educationLevel = new EducationLevel();
			 * educationLevel.setId(educationLevelId);
			 * candidateSelected.setEducationLevel(educationLevel); }
			 */

			/*
			 * if (subAreaSelected != null && subAreaSelected.length() > 0) {
			 * Integer subAreaId = Integer.parseInt(subAreaSelected); Area
			 * subArea = new Area(); subArea.setId(subAreaId);
			 * candidateSelected.setArea(subArea); }
			 */

			/*
			 * if (interviewStateSelected != null &&
			 * interviewStateSelected.length() > 0 &&
			 * interviewStateSelected.equals("a")) {
			 * candidateSelected.setScheduledAt(scheduledAt); }
			 */

			/*
			 * User user = sessionBean.getUser();
			 * candidateSelected.setCreatedBy(user);
			 * candidateSelected.setCreatedAt(new Date());
			 */

			// candidateService.add(candidateSelected);

			// String confirmMessage = "Se creó satisfactoriamente el candidato
			// con id:" + candidateSelected.getId();

			// CandidateStateDto state = new CandidateStateDto();
			/*
			 * if (interviewStateSelected != null &&
			 * interviewStateSelected.length() > 0 &&
			 * !interviewStateSelected.equals("a")) { Interview interview = new
			 * Interview(); interview.setCreatedBy(user);
			 * interview.setCandidate(candidateSelected);
			 * interview.setCreatedAt(new Date());
			 * interview.setInterviewedAt(interviewedAt); InterviewState
			 * interviewStateDto = new InterviewState();
			 * interviewStateDto.setId(Integer.parseInt(interviewStateSelected))
			 * ; interview.setInterviewState(interviewStateDto);
			 * candidateSelected.setInterview(interview); //confirmMessage +=
			 * " y entrevista con id:" + interview.getId(); }
			 */

			// candidateService.add(candidateSelected);

			// SE IMPRIME MENSAJE DE CONFIRMACION
			facesUtil.sendConfirmMessage("Se creó satisfactoriamente.");

			// return "search_candidate";

		} catch (Exception e) {
			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getClass().getSimpleName(), e.getMessage());
		}
	}

	public void onChangeState() {
		try {

			if (sessionBean == null || sessionBean.getUser() == null || sessionBean.getUser().getId() == null) {
				throw new UserLoggedNotFoundException();
			}

			System.out.println("ingreso a onChangeState");
			System.out.println(interviewStateSelected);
			if (interviewStateSelected != null && interviewStateSelected.length()>0) {
				
				Integer interviewStateId = Integer.parseInt(interviewStateSelected);
				
				InterviewState interviewState = interviewStateService.findById(interviewStateId);

				if (interviewState.getIsScheduled()) {
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
			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getClass().getSimpleName(), e.getMessage());
		}
	}

	public void onChangeRecruitmentSource() {
		try {

			if (sessionBean == null || sessionBean.getUser() == null || sessionBean.getUser().getId() == null) {
				throw new UserLoggedNotFoundException();
			}

			System.out.println("ingreso a onChangeRecruitmentSource");
			System.out.println(recruimentSourceSelected);

			if (recruimentSourceSelected != null && recruimentSourceSelected.length()>0) {

				Integer recruitmentSourceId = Integer.parseInt(recruimentSourceSelected);

				RecruitmentSource recruimentSourcesDto = recruimentSourceService.findById(recruitmentSourceId);

				if (recruimentSourcesDto.getIsFlier() != null && recruimentSourcesDto.getIsFlier()) {

					isFlier = Boolean.TRUE;
					isJobFair = Boolean.FALSE;
					isReferred = Boolean.FALSE;

				} else if (recruimentSourcesDto.getIsJobFair() != null && recruimentSourcesDto.getIsJobFair()) {
					isFlier = Boolean.FALSE;
					isJobFair = Boolean.TRUE;
					isReferred = Boolean.FALSE;

				} else if (recruimentSourcesDto.getIsReferred() != null && recruimentSourcesDto.getIsReferred()) {
					isFlier = Boolean.FALSE;
					isJobFair = Boolean.FALSE;
					isReferred = Boolean.TRUE;

				} else {
					isFlier = Boolean.FALSE;
					isJobFair = Boolean.FALSE;
					isReferred = Boolean.FALSE;
				}

			} else {
				isFlier = Boolean.FALSE;
				isJobFair = Boolean.FALSE;
				isReferred = Boolean.FALSE;
			}

		} catch (Exception e) {
			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getClass().getSimpleName(), e.getMessage());
		}
	}

	public Interview getInterviewSelected() {
		return interviewSelected;
	}

	public void setInterviewSelected(Interview interviewSelected) {
		this.interviewSelected = interviewSelected;
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

	public List<SelectItem> getCountries() {
		return countries;
	}

	public void setCountries(List<SelectItem> countries) {
		this.countries = countries;
	}

	public String getCountrySelected() {
		return countrySelected;
	}

	public void setCountrySelected(String countrySelected) {
		this.countrySelected = countrySelected;
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

	public List<SelectItem> getProvinces() {
		return provinces;
	}

	public void setProvinces(List<SelectItem> provinces) {
		this.provinces = provinces;
	}

	public String getProvinceSelected() {
		return provinceSelected;
	}

	public void setProvinceSelected(String provinceSelected) {
		this.provinceSelected = provinceSelected;
	}

	public List<SelectItem> getDistricts() {
		return districts;
	}

	public void setDistricts(List<SelectItem> districts) {
		this.districts = districts;
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

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public Date getMinDate() {
		return minDate;
	}

	public void setMinDate(Date minDate) {
		this.minDate = minDate;
	}

	public Date getMaxDate() {
		return maxDate;
	}

	public void setMaxDate(Date maxDate) {
		this.maxDate = maxDate;
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

	public Boolean getInOnChange() {
		return inOnChange;
	}

	public void setInOnChange(Boolean inOnChange) {
		this.inOnChange = inOnChange;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	public List<SelectItem> getRequirements() {
		return requirements;
	}

	public void setRequirements(List<SelectItem> requirements) {
		this.requirements = requirements;
	}

	public String getRequirementSelected() {
		return requirementSelected;
	}

	public void setRequirementSelected(String requirementSelected) {
		this.requirementSelected = requirementSelected;
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
	
	

}
