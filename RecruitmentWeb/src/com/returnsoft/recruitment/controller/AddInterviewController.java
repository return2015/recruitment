package com.returnsoft.recruitment.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

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
import com.returnsoft.recruitment.entity.RequirementUser;
import com.returnsoft.recruitment.exception.UserLoggedNotFoundException;
import com.returnsoft.recruitment.service.CandidateService;
import com.returnsoft.recruitment.service.CountryService;
import com.returnsoft.recruitment.service.DepartmentService;
import com.returnsoft.recruitment.service.DistrictService;
import com.returnsoft.recruitment.service.EducationLevelService;
import com.returnsoft.recruitment.service.InterviewService;
import com.returnsoft.recruitment.service.InterviewStateService;
import com.returnsoft.recruitment.service.ProvinceService;
import com.returnsoft.recruitment.service.RecruitmentSourceService;
import com.returnsoft.recruitment.service.RequirementService;
import com.returnsoft.recruitment.service.RequirementUserService;
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
	
	@EJB
	private RequirementUserService requirementUserService;

	@EJB
	private InterviewService interviewService;

	private String age;
	private Date minDate;
	private Date maxDate;

	//private String info;
	private String documentNumber;
	//private String areaSelected;
	//private String subAreaSelected;
	private Boolean validation;
	private String passwordSupervisor;

	///////

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
	private List<SelectItem> recruitmentSources;
	private String recruitmentSourceSelected;
	private List<SelectItem> educationLevels;
	private String educationLevelSelected;
	private List<SelectItem> requirements;
	private String requirementSelected;

	/////

	public String initialize() {
		try {

			if (sessionBean == null || sessionBean.getUser() == null || sessionBean.getUser().getId() == null) {
				throw new UserLoggedNotFoundException();
			}

			List<InterviewState> interviewStatesEntity = interviewStateService.findIsPending();
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
			recruitmentSources = new ArrayList<SelectItem>();
			for (RecruitmentSource recruimentSourceDto : recruimentSourcesDto) {
				SelectItem item = new SelectItem();
				item.setValue(recruimentSourceDto.getId().toString());
				item.setLabel(recruimentSourceDto.getName());
				recruitmentSources.add(item);
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
			Calendar calendarMax = Calendar.getInstance();
			calendarMax.setTime(today);
			calendarMax.set(Calendar.YEAR, calendarMax.get(Calendar.YEAR) - 18);
			this.maxDate = calendarMax.getTime();

			reset();

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

	public void searchCandidate() {
		try {

			System.out.println("ingreso a searchCandidate");
			// System.out.println(interviewSelected.getCandidate().getDocumentNumber());

			if (sessionBean == null || sessionBean.getUser() == null || sessionBean.getUser().getId() == null) {
				throw new UserLoggedNotFoundException();
			}

			System.out.println("documentNumber:" + documentNumber);

			if (documentNumber != null && documentNumber.length() > 0) {
				// throw new Exception("Ingrese número de documento");

				Candidate candidateFound = candidateService.findByDocumentNumber(documentNumber);
				if (candidateFound != null) {

					System.out.println("Ingreso aqui1");

					if (candidateFound.getInterview() != null
							&& candidateFound.getInterview().getInterviewState() != null
							&& (candidateFound.getInterview().getInterviewState().getIsPending()
									|| candidateFound.getInterview().getInterviewState().getIsScheduled())) {

						validation = Boolean.FALSE;
						
						/* -- */

						throw new Exception("Tiene entrevistas pendientes.");

					} else {
						
						//RequestContext context = RequestContext.getCurrentInstance();
						//context.execute("PF('passSuperDialog').show();");
						
						Map<String, Object> options = new HashMap<String, Object>();
						options.put("modal", true);
						options.put("draggable", true);
						options.put("resizable", true);
						options.put("contentHeight", 200);
						options.put("contentWidth", 300);

						/*return "edit_user?faces-redirect=true&userId="
								+ userSelected.getId();*/
						/*Map<String, List<String>> paramMap = new HashMap<String, List<String>>();
						ArrayList<String> paramList = new ArrayList<>();
						paramList.add(String.valueOf(interviewSelected.getId()));
						paramMap.put("interviewId", paramList);*/
						RequestContext.getCurrentInstance().openDialog("validate_password_supervisor", options, null);
						
						
						
						/*validation = Boolean.TRUE;
						interviewSelected.setCandidate(candidateFound);
						calculateAge();
						if (candidateFound.getCountry() != null) {
							countrySelected = candidateFound.getCountry().getId().toString();
						}
						if (candidateFound.getDepartment() != null) {
							departmentSelected = candidateFound.getDepartment().getId().toString();
						}
						if (candidateFound.getProvince() != null) {
							provinceSelected = candidateFound.getProvince().getId().toString();
						}
						if (candidateFound.getDistrict() != null) {
							districtSelected = candidateFound.getDistrict().getId().toString();
						}
						if (candidateFound.getEducationLevel() != null) {
							educationLevelSelected = candidateFound.getEducationLevel().getId().toString();
						}*/
					}

				} else {
					validation = Boolean.TRUE;
					interviewSelected.getCandidate().setDocumentNumber(documentNumber);
				}

			} else {
				validation = Boolean.FALSE;
				throw new Exception("Ingrese Número de Documento.");
			}

		} catch (Exception e) {
			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getMessage());
		}
	}
	
	public void afterValidation(SelectEvent event){
		try {
			Boolean responseReturn = (Boolean) event.getObject();
			
			if (responseReturn) {
				validation = Boolean.TRUE;
				Candidate candidateFound = candidateService.findByDocumentNumber(documentNumber);
				interviewSelected.setCandidate(candidateFound);
				calculateAge();
				if (candidateFound.getCountry() != null) {
					countrySelected = candidateFound.getCountry().getId().toString();
				}
				if (candidateFound.getDepartment() != null) {
					departmentSelected = candidateFound.getDepartment().getId().toString();
				}
				if (candidateFound.getProvince() != null) {
					provinceSelected = candidateFound.getProvince().getId().toString();
				}
				if (candidateFound.getDistrict() != null) {
					districtSelected = candidateFound.getDistrict().getId().toString();
				}
				if (candidateFound.getEducationLevel() != null) {
					educationLevelSelected = candidateFound.getEducationLevel().getId().toString();
				}
			}else{
				facesUtil.sendErrorMessage("¡Password Supervisor Incorrecto!");
			}
			
			//int i = 0;
			//for (Requirement requirement : interviews.) {
				//Sale sale = sales.get(i);
				//if (requirement.getId().equals(requirementReturn.getId())) {
					//requirements.set(i, requirementReturn);
					//interviewSelected = interviewReturn;
					//break;
				//}
				//i++;
			//}
			
		} catch (Exception e) {
			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getMessage());
			//return null;
		}
		
	}
	
	public void changeDocumentNumber(){
		try {
			
			if (sessionBean == null || sessionBean.getUser() == null || sessionBean.getUser().getId() == null) {
				throw new UserLoggedNotFoundException();
			}
			
			validation=Boolean.FALSE;
			
		} catch (Exception e) {
			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getMessage());
		}
	}

	public void searchDepartments() {

		try {

			if (sessionBean == null || sessionBean.getUser() == null || sessionBean.getUser().getId() == null) {
				throw new UserLoggedNotFoundException();
			}

			countrySelected = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
					.get("form:country_input");

			System.out.println("countrySelected:" + countrySelected);

			if (countrySelected != null && countrySelected.length() > 0) {

				Integer countryId = Integer.parseInt(countrySelected);

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

		try {

			if (sessionBean == null || sessionBean.getUser() == null || sessionBean.getUser().getId() == null) {
				throw new UserLoggedNotFoundException();
			}

			if (departmentSelected != null) {

				Integer departmentId = Integer.parseInt(departmentSelected);

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

			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getMessage());
		}
	}

	public void searchDistricts() {

		try {

			if (sessionBean == null || sessionBean.getUser() == null || sessionBean.getUser().getId() == null) {
				throw new UserLoggedNotFoundException();
			}

			if (provinceSelected != null) {

				Integer provinceId = Integer.parseInt(provinceSelected);
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
			facesUtil.sendErrorMessage(e.getMessage());
		}
	}

	public void calculateAge() {
		try {

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
			facesUtil.sendErrorMessage(e.getMessage());
		}
	}

	public void onChangeRequirement() {
		try {

			requirementSelected = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
					.get("form:requirement_input");

			if (requirementSelected != null && requirementSelected.length() > 0) {
				Integer reqId = Integer.parseInt(requirementSelected);
				RequirementUser reqUser = requirementUserService.findById(reqId,sessionBean.getUser().getId());
				interviewSelected.setRequirementUser(reqUser);
				/*areaSelected = req.getArea().getArea().getName();
				subAreaSelected = req.getArea().getName();*/
			} else {
				interviewSelected.setRequirementUser(null);
				//areaSelected = "";
				//subAreaSelected = "";
			}

		} catch (Exception e) {
			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getMessage());
		}

	}

	public void reset() {

		interviewSelected = new Interview();
		interviewSelected.setCandidate(new Candidate());
		countrySelected = "";
		departmentSelected = "";
		departments = null;
		provinceSelected = "";
		provinces = null;
		districtSelected = "";
		districts = null;
		educationLevelSelected = "";

		//

		requirementSelected = null;
		//info = "";
		age = "";
		interviewStateSelected = "";
		recruitmentSourceSelected = "";
		
		
		
		validation=Boolean.FALSE;

	}

	public void add() {
		try {

			if (sessionBean == null || sessionBean.getUser() == null || sessionBean.getUser().getId() == null) {
				throw new UserLoggedNotFoundException();
			}

			if (interviewSelected != null && interviewSelected.getCandidate() != null) {

				////

				if (interviewSelected.getCandidate().getDocumentNumber() != null
						&& interviewSelected.getCandidate().getDocumentNumber().length() > 0) {
					Candidate candidateFound = candidateService
							.findByDocumentNumber(interviewSelected.getCandidate().getDocumentNumber());
					if (candidateFound != null) {
						if (candidateFound.getInterview() != null
								&& candidateFound.getInterview().getInterviewState() != null
								&& (candidateFound.getInterview().getInterviewState().getIsPending()
										|| candidateFound.getInterview().getInterviewState().getIsScheduled())) {

							throw new Exception("El postulante tiene entrevistas pendientes");

						}

					}
				}

				if (countrySelected != null && countrySelected.length() > 0) {
					Integer countryId = Integer.parseInt(countrySelected);
					Country country = new Country();
					country.setId(countryId);
					interviewSelected.getCandidate().setCountry(country);
				}

				if (departmentSelected != null && departmentSelected.length() > 0) {
					Integer departmentId = Integer.parseInt(departmentSelected);
					Department department = new Department();
					department.setId(departmentId);
					interviewSelected.getCandidate().setDepartment(department);
				}

				if (provinceSelected != null && provinceSelected.length() > 0) {
					Integer provinceId = Integer.parseInt(provinceSelected);
					Province province = new Province();
					province.setId(provinceId);
					interviewSelected.getCandidate().setProvince(province);
				}

				if (districtSelected != null && districtSelected.length() > 0) {
					Integer districtId = Integer.parseInt(districtSelected);
					District district = new District();
					district.setId(districtId);
					interviewSelected.getCandidate().setDistrict(district);
				}

				if (educationLevelSelected != null && educationLevelSelected.length() > 0) {
					Integer educationLevelId = Integer.parseInt(educationLevelSelected);
					EducationLevel educationLevel = new EducationLevel();
					educationLevel.setId(educationLevelId);
					interviewSelected.getCandidate().setEducationLevel(educationLevel);
				}

				///

				if (requirementSelected != null && requirementSelected.length() > 0) {
					Integer reqId = Integer.parseInt(requirementSelected);
					RequirementUser reqUser = requirementUserService.findById(reqId,sessionBean.getUser().getId());
					interviewSelected.setRequirementUser(reqUser);
					
					/*Integer requirementId = Integer.parseInt(requirementSelected);
					Requirement requirement = new Requirement();
					requirement.setId(requirementId);
					interviewSelected.setRequirement(requirement);*/
				}

				if (recruitmentSourceSelected != null && recruitmentSourceSelected.length() > 0) {
					Integer recruitmentSourceId = Integer.parseInt(recruitmentSourceSelected);
					RecruitmentSource recruitmentSource = new RecruitmentSource();
					recruitmentSource.setId(recruitmentSourceId);
					interviewSelected.setRecruitmentSource(recruitmentSource);
				}

				if (interviewStateSelected != null && interviewStateSelected.length() > 0) {
					Integer interviewStateId = Integer.parseInt(interviewStateSelected);
					InterviewState interviewState = new InterviewState();
					interviewState.setId(interviewStateId);
					interviewSelected.setInterviewState(interviewState);
				}
				
				if (interviewSelected.getCandidate().getId()!=null) {
					System.out.println("interviewSelected.getCandidate().getId():"+interviewSelected.getCandidate().getId());
				}else{
					System.out.println("es nulo");
				}

				interviewSelected.setCreatedAt(new Date());
				interviewSelected.setCreatedBy(sessionBean.getUser());
				interviewService.add(interviewSelected);

				
				reset();
				documentNumber="";
				/*interviewSelected = new Interview();
				interviewSelected.setCandidate(new Candidate());

				countrySelected = "";
				departmentSelected = "";
				provinceSelected = "";
				districtSelected = "";
				educationLevelSelected = "";*/

				// SE IMPRIME MENSAJE DE CONFIRMACION
				facesUtil.sendConfirmMessage("Se creó satisfactoriamente.");

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

			interviewStateSelected = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
					.get("form:interviewState_input");

			if (interviewStateSelected != null && interviewStateSelected.length() > 0) {
				Integer interviewStateId = Integer.parseInt(interviewStateSelected);
				InterviewState interviewState = interviewStateService.findById(interviewStateId);
				interviewSelected.setInterviewState(interviewState);
			} else {
				interviewSelected.setInterviewState(null);
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

			recruitmentSourceSelected = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
					.get("form:recruitmentSource_input");

			if (recruitmentSourceSelected != null && recruitmentSourceSelected.length() > 0) {
				Integer recruitmentSourceId = Integer.parseInt(recruitmentSourceSelected);
				RecruitmentSource recruimentSource = recruimentSourceService.findById(recruitmentSourceId);
				interviewSelected.setRecruitmentSource(recruimentSource);
			} else {
				interviewSelected.setRecruitmentSource(null);
			}

		} catch (Exception e) {
			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getMessage());
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

	public List<SelectItem> getRecruitmentSources() {
		return recruitmentSources;
	}

	public void setRecruitmentSources(List<SelectItem> recruitmentSources) {
		this.recruitmentSources = recruitmentSources;
	}

	public String getRecruitmentSourceSelected() {
		return recruitmentSourceSelected;
	}

	public void setRecruitmentSourceSelected(String recruitmentSourceSelected) {
		this.recruitmentSourceSelected = recruitmentSourceSelected;
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

	/*public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}*/

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



	public Boolean getValidation() {
		return validation;
	}

	public void setValidation(Boolean validation) {
		this.validation = validation;
	}

	public String getPasswordSupervisor() {
		return passwordSupervisor;
	}

	public void setPasswordSupervisor(String passwordSupervisor) {
		this.passwordSupervisor = passwordSupervisor;
	}
	
	

}
