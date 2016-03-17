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
import org.primefaces.model.LazyDataModel;

import com.returnsoft.recruitment.entity.Area;
import com.returnsoft.recruitment.entity.Interview;
import com.returnsoft.recruitment.entity.InterviewState;
import com.returnsoft.recruitment.entity.User;
import com.returnsoft.recruitment.enumeration.UserTypeEnum;
import com.returnsoft.recruitment.exception.UserLoggedNotFoundException;
import com.returnsoft.recruitment.service.AreaService;
import com.returnsoft.recruitment.service.InterviewService;
import com.returnsoft.recruitment.service.InterviewStateService;
import com.returnsoft.recruitment.service.UserService;
import com.returnsoft.recruitment.util.FacesUtil;
import com.returnsoft.recruitment.util.InterviewLazyModel;
import com.returnsoft.recruitment.util.SessionBean;

@ManagedBean
@ViewScoped
public class SearchInterviewController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3358920352127811619L;
	
	@Inject
	private FacesUtil facesUtil;
	
	@Inject
	private SessionBean sessionBean;
	
	private List<SelectItem> interviewStates;
	private String interviewStateSelected;
	
	private List<SelectItem> areas;
	private String areaSelected;
	
	private List<SelectItem> subAreas;
	private String subAreaSelected;
	
	private List<SelectItem> recruiters;
	private String recruiterSelected;
	
	private Interview interviewSelected;
	
	
	@EJB
	private UserService userService;

	@EJB
	private AreaService areaService;

	@EJB
	private InterviewStateService interviewStateService;
	
	@EJB
	private InterviewService interviewService;


	private LazyDataModel<Interview> interviews;
	
	private String documentNumber;
	private String names;
	private Date interviewedAt;
	private Date createdAt;
	private Date scheduledAt;
	
	/*private List<SelectItem> states;
	private String stateSelected;*/
	
	
	
	public String initialize() {
		try {
			
			//System.out.println("ingreso a initialize de SearchCandidateController");
			
			if (sessionBean == null || sessionBean.getUser() == null || sessionBean.getUser().getId() == null) {
				throw new UserLoggedNotFoundException();
			}

		
				//////////////////////////////////////////////////////////////

				List<Area> areasDto = areaService.findAreasParentActive();
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

				interviewSelected = null;

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

		System.out.println("ingreso a searchsubareas: " + areaSelected);

		try {
			
			if (sessionBean == null || sessionBean.getUser() == null || sessionBean.getUser().getId() == null) {
				throw new UserLoggedNotFoundException();
			}
			

			if (areaSelected != null && !areaSelected.equals("")) {

				//if (areaSelected.equals("t")) {

					if (areas != null && areas.size() > 0) {

						//Integer recruiterId = sessionBean.getUser().getId();
						subAreas = new ArrayList<SelectItem>();

						for (SelectItem areaItem : areas) {
							Integer areaId = Integer.parseInt(areaItem.getValue().toString());
							List<Area> areasEntity = areaService.findAreasChildActive(areaId);
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
				/*} else {

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
				}*/

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

			Integer userId = null;
			if (recruiterSelected != null && !recruiterSelected.equals("")) {
				userId = Integer.parseInt(recruiterSelected);
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

			List<Integer> areasId = new ArrayList<Integer>();
			if (areaSelected != null && !areaSelected.equals("")) {
				/*if (areaSelected.equals("t")) {
					if (areas != null && areas.size() > 0) {
						for (SelectItem item : areas) {
							areasId.add(Integer.parseInt(item.getValue().toString()));
						}
					} else {
						areasId.add(0);
					}
				} else {*/
					areasId.add(Integer.parseInt(areaSelected));
				//}
			}

			List<Integer> subAreasId = new ArrayList<Integer>();
			// System.out.println("subAreaSelected:" + subAreaSelected);
			if (subAreaSelected != null && !subAreaSelected.equals("")) {
				/*if (subAreaSelected.equals("t")) {
					if (subAreas != null && subAreas.size() > 0) {
						for (SelectItem item : subAreas) {
							subAreasId.add(Integer.parseInt(item.getValue().toString()));
						}
					} else {
						subAreasId.add(0);
					}
					// System.out.println(subAreasId.size());
				} else {*/
					// System.out.println("subAreaSelected:" + subAreaSelected);
					subAreasId.add(Integer.parseInt(subAreaSelected));
				//}
			}

			
			  interviews = new InterviewLazyModel(interviewService,areasId,subAreasId,
			  interviewStateId,interviewedAt,scheduledAt, createdAt,documentNumber, names,userId);
			 

			interviewSelected = null;

		} catch (Exception e) {
			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getMessage());
		}
	}
	
	public void edit() {
		try {
			
			if (sessionBean == null || sessionBean.getUser() == null || sessionBean.getUser().getId() < 1) {
				throw new UserLoggedNotFoundException();
			}
			
			System.out.println("Ingreso a edit");
			
			if (!interviewSelected.getCreatedBy().getId().equals(sessionBean.getUser().getId())) {
				throw new Exception("Solo puede editar sus entrevistas.");
			}
			
			Map<String, Object> options = new HashMap<String, Object>();
			options.put("modal", true);
			options.put("draggable", true);
			options.put("resizable", true);
			options.put("contentHeight", 500);
			options.put("contentWidth", 1000);

			/*return "edit_user?faces-redirect=true&userId="
					+ userSelected.getId();*/
			Map<String, List<String>> paramMap = new HashMap<String, List<String>>();
			ArrayList<String> paramList = new ArrayList<>();
			paramList.add(String.valueOf(interviewSelected.getId()));
			paramMap.put("interviewId", paramList);
			RequestContext.getCurrentInstance().openDialog("edit_interview", options, paramMap);

		} catch (Exception e) {
			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getMessage());
			//return null;
		}
	}
	
	public void afterEdit(SelectEvent event){
		try {
			Interview interviewReturn = (Interview) event.getObject();
			
			//int i = 0;
			//for (Requirement requirement : interviews.) {
				//Sale sale = sales.get(i);
				//if (requirement.getId().equals(requirementReturn.getId())) {
					//requirements.set(i, requirementReturn);
					interviewSelected = interviewReturn;
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

	public Interview getInterviewSelected() {
		return interviewSelected;
	}

	public void setInterviewSelected(Interview interviewSelected) {
		this.interviewSelected = interviewSelected;
	}

	

	public LazyDataModel<Interview> getInterviews() {
		return interviews;
	}

	public void setInterviews(LazyDataModel<Interview> interviews) {
		this.interviews = interviews;
	}

	public String getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	public String getNames() {
		return names;
	}

	public void setNames(String names) {
		this.names = names;
	}

	public Date getInterviewedAt() {
		return interviewedAt;
	}

	public void setInterviewedAt(Date interviewedAt) {
		this.interviewedAt = interviewedAt;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/*public List<SelectItem> getStates() {
		return states;
	}

	public void setStates(List<SelectItem> states) {
		this.states = states;
	}

	public String getStateSelected() {
		return stateSelected;
	}

	public void setStateSelected(String stateSelected) {
		this.stateSelected = stateSelected;
	}*/

	public Date getScheduledAt() {
		return scheduledAt;
	}

	public void setScheduledAt(Date scheduledAt) {
		this.scheduledAt = scheduledAt;
	}
	

}
