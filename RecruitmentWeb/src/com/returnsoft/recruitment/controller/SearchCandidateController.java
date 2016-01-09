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

import org.primefaces.model.LazyDataModel;

import com.returnsoft.recruitment.entity.Area;
import com.returnsoft.recruitment.entity.Candidate;
import com.returnsoft.recruitment.entity.Interview;
import com.returnsoft.recruitment.entity.InterviewState;
import com.returnsoft.recruitment.entity.OjtState;
import com.returnsoft.recruitment.entity.TrainingState;
import com.returnsoft.recruitment.entity.User;
import com.returnsoft.recruitment.enumeration.UserTypeEnum;
import com.returnsoft.recruitment.exception.UserLoggedNotFoundException;
import com.returnsoft.recruitment.service.AreaService;
import com.returnsoft.recruitment.service.CandidateService;
import com.returnsoft.recruitment.service.InterviewService;
import com.returnsoft.recruitment.service.InterviewStateService;
import com.returnsoft.recruitment.service.OjtStateService;
import com.returnsoft.recruitment.service.TrainingStateService;
import com.returnsoft.recruitment.service.UserService;
import com.returnsoft.recruitment.util.CandidateLazyModel;
import com.returnsoft.recruitment.util.FacesUtil;
import com.returnsoft.recruitment.util.SessionBean;

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

	private LazyDataModel<Candidate> candidates;
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

	@Inject
	private FacesUtil facesUtil;
	
	@Inject
	private SessionBean sessionBean;

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
	
	@EJB
	private CandidateService candidateService;
	
	@EJB
	private InterviewService interviewService;
	
	
	private List<Interview> interviews;
	
	

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

		
	}

	public String initialize() {
		try {
			
			//System.out.println("ingreso a initialize de SearchCandidateController");
			
			if (sessionBean == null || sessionBean.getUser() == null || sessionBean.getUser().getId() == null) {
				throw new UserLoggedNotFoundException();
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

				List<User> recruitersEntity = userService.findByUserType(UserTypeEnum.RECRUITER);
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

	

	public void searchSubAreas() {

		System.out.println("ingreso a searchsubareas: " + areaSelected);

		try {
			
			if (sessionBean == null || sessionBean.getUser() == null || sessionBean.getUser().getId() == null) {
				throw new UserLoggedNotFoundException();
			}
			

			if (areaSelected != null && !areaSelected.equals("")) {

				if (areaSelected.equals("t")) {

					if (areas != null && areas.size() > 0) {

						Integer recruiterId = sessionBean.getUser().getId();
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
					Integer recruiterId = sessionBean.getUser().getId();

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
			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getMessage());
		}
	}

	public void search() {

		// System.out.println("ingreso a search person");
		try {
			
			if (sessionBean == null || sessionBean.getUser() == null || sessionBean.getUser().getId() == null) {
				throw new UserLoggedNotFoundException();
			}
			

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

			//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			//String scheduledAtFormatter = null;
			//if (scheduledAtSearch != null) {
				//scheduledAtFormatter = sdf.format(scheduledAtSearch);
			//}

			///String createdAtFormatter = null;
			//if (createdAtSearch != null) {
			//	createdAtFormatter = sdf.format(createdAtSearch);
			//}

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

			
			  candidates = new CandidateLazyModel(candidateService,areasId,subAreasId,
			  interviewStateId, trainingStateId, ojtStateId,
			  scheduledAtSearch, createdAtSearch,
			  candidateDocumentNumberSearch, candidateNameSearch);
			 

			candidateSelected = null;

		} catch (Exception e) {
			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getMessage());
		}
	}

	
	public void searchDepartments() {

		// System.out.println("ingreso a
		// searchsubdepartments"+departmentSelected);
		try {
			if (sessionBean == null || sessionBean.getUser() == null || sessionBean.getUser().getId() == null) {
				throw new UserLoggedNotFoundException();
			}
			
			
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
			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getMessage());
		}
	}

	public void searchProvinces() {

		// System.out.println("ingreso a
		// searchsubdepartments"+departmentSelected);
		try {
			
			if (sessionBean == null || sessionBean.getUser() == null || sessionBean.getUser().getId() == null) {
				throw new UserLoggedNotFoundException();
			}
			
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
			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getMessage());
		}
	}

	public void searchDistricts() {

		// System.out.println("ingreso a
		// searchsubdepartments"+departmentSelected);
		try {
			
			if (sessionBean == null || sessionBean.getUser() == null || sessionBean.getUser().getId() == null) {
				throw new UserLoggedNotFoundException();
			}
			
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
			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getMessage());
		}
	}



	public void onChangeState() {
		try {
			
			if (sessionBean == null || sessionBean.getUser() == null || sessionBean.getUser().getId() == null) {
				throw new UserLoggedNotFoundException();
			}

			//System.out.println("ingreso a onChangeState");
			//System.out.println(interviewStateSelected);
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
			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getMessage());
		}
	}

	public void onChangeRecruitmentSource() {
		try {
			
			if (sessionBean == null || sessionBean.getUser() == null || sessionBean.getUser().getId() == null) {
				throw new UserLoggedNotFoundException();
			}
			

			//System.out.println("ingreso a onChangeRecruitmentSource");
			//System.out.println(recruimentSourceSelected);

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
			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getMessage());
		}
	}
	
	public void showInterviews() {

		try {
			
			if (sessionBean == null || sessionBean.getUser() == null || sessionBean.getUser().getId() < 1) {
				throw new UserLoggedNotFoundException();
			}

			interviews = interviewService.findByCandidate(candidateSelected.getId());

		} catch (Exception e) {
			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getClass().getSimpleName(), e.getMessage());
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

	

	public LazyDataModel<Candidate> getCandidates() {
		return candidates;
	}

	public void setCandidates(LazyDataModel<Candidate> candidates) {
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

	public List<Interview> getInterviews() {
		return interviews;
	}

	public void setInterviews(List<Interview> interviews) {
		this.interviews = interviews;
	}
	
	

}
