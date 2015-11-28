package com.returnsoft.recruitment.eao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.returnsoft.recruitment.entity.Area;
import com.returnsoft.recruitment.entity.Candidate;
import com.returnsoft.recruitment.entity.CandidateState;
import com.returnsoft.recruitment.entity.User;
import com.returnsoft.recruitment.exception.EaoException;


@Stateless
public class CandidateEao {

	@PersistenceContext
	private EntityManager em;
	
	public Candidate findById(Integer candidateId) throws EaoException {
		try {
			Candidate candidate = em.find(Candidate.class, candidateId);
			
			return candidate;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e.getMessage());
		}
	}
	

	/*public Candidate findById(Integer candidateId) throws EaoException {
		try {
			
			

			String query = "SELECT c.id,c.document_type,c.document_number,c.firstname,"
					+ "c.lastname,c.mail,c.phone,c.cell_phone,"
					+ "c.born_date,c.entry_type,c.experience,"
					+ "c.created_at,c.scheduled_at,c.home_address,c.last_job,"
					+ "a.id,a.name,ap.id,ap.name,"
					+ "cs.id,cs.name,cs.is_pending,cs.is_goal,cs.state,"
					+ "r.id,p.firstname,p.lastname,"
					+ "co.id,co.name,pr.id,pr.name,de.id,de.name,di.id,di.name,"
					+ "rs.id,rs.name,el.id,el.name "
					+ "FROM recruitment.candidate c "
					+ "left join recruitment.candidate_state cs on cs.id=c.candidate_state_id "
					+ "left join recruitment.area a on a.id=c.area_id "
					+ "left join recruitment.area ap on ap.id=a.area_id "
					+ "left join recruitment.recruiter r on r.id=c.recruiter_id "
					+ "left join security.user u on u.id=r.user_id "
					+ "left join security.person p on p.id=u.person_id "
					+ "left join recruitment.country co on co.id=c.country_id "
					+ "left join recruitment.province pr on pr.id=c.province_id "
					+ "left join recruitment.department de on de.id=c.department_id "
					+ "left join recruitment.district di on di.id=c.district_id "
					+ "left join recruitment.recruiment_source rs on rs.id=c.recruiment_source_id "
					+ "left join recruitment.education_level el on el.id=c.education_level_id "
					+ "WHERE c.id= " + candidateId;

			Query q = em.createNativeQuery(query);

			Object[] candidateObject = (Object[]) q.getSingleResult();

			if (candidateObject.length > 0) {

				candidateId = (Integer) candidateObject[0];
				String candidateDocumentType = (String) candidateObject[1];
				String candidateDocumentNumber = (String) candidateObject[2];
				String candidateFirstname = (String) candidateObject[3];
				String candidateLastname = (String) candidateObject[4];
				String candidateMail = (String) candidateObject[5];
				String candidatePhone = (String) candidateObject[6];
				String candidateCellphone = (String) candidateObject[7];
				Date candidateBornDate = (Date) candidateObject[8];
				String candidateEntryType = (String) candidateObject[9];
				String candidateExperience = (String) candidateObject[10];
				Date candidateCreatedAt = (Date) candidateObject[11];
				Date candidateScheduledAt = (Date) candidateObject[12];
				String candidateHomeAddress = (String) candidateObject[13];
				String candidateLastJob = (String) candidateObject[14];

				Integer candidateAreaId = (Integer) candidateObject[15];
				String candidateAreaName = (String) candidateObject[16];
				Integer candidateAreaParentId = (Integer) candidateObject[17];
				String candidateAreaParentName = (String) candidateObject[18];
				Integer candidateStateId = (Integer) candidateObject[19];
				String candidateStateName = (String) candidateObject[20];
				
				Boolean candidateStateIsPending = (Boolean) candidateObject[21];
				Boolean candidateStateIsGoal = (Boolean) candidateObject[22];
				Boolean candidateStateState = (Boolean) candidateObject[23];
				
				Integer recruiterId = (Integer) candidateObject[24];
				String recruiterFirstname = (String) candidateObject[25];
				String recruiterLastname = (String) candidateObject[26];
				
				Integer countryId = (Integer) candidateObject[27];
				String countryName = (String) candidateObject[28];
				Integer provinceId = (Integer) candidateObject[29];
				String provinceName = (String) candidateObject[30];
				Integer departmentId = (Integer) candidateObject[31];
				String departmentName = (String) candidateObject[32];
				Integer districtId = (Integer) candidateObject[33];
				String districtName = (String) candidateObject[34];
				
				Integer recruitmentSourceId = (Integer) candidateObject[35];
				String recruitmentSourceName = (String) candidateObject[36];
				
				Integer educationLevelId = (Integer) candidateObject[37];
				String educationLevelName = (String) candidateObject[38];

				CandidateDto candidate = new CandidateDto();
				candidate.setId(candidateId);
				candidate.setDocumentType(candidateDocumentType);
				candidate.setDocumentNumber(candidateDocumentNumber);
				candidate.setFirstname(candidateFirstname);
				candidate.setLastname(candidateLastname);
				candidate.setMail(candidateMail);
				candidate.setPhone(candidatePhone);
				candidate.setCellPhone(candidateCellphone);
				candidate.setBornDate(candidateBornDate);
				candidate.setEntryType(candidateEntryType);
				candidate.setExperience(candidateExperience);
				candidate.setCreatedAt(candidateCreatedAt);
				candidate.setScheduledAt(candidateScheduledAt);
				candidate.setHomeAddress(candidateHomeAddress);
				candidate.setLastJob(candidateLastJob);
				if (candidateAreaId != null) {
					AreaDto area = new AreaDto();
					area.setId(candidateAreaId);
					area.setName(candidateAreaName);
					if (candidateAreaParentId != null) {
						AreaDto areaParent = new AreaDto();
						areaParent.setId(candidateAreaParentId);
						areaParent.setName(candidateAreaParentName);
						area.setArea(areaParent);
					}
					candidate.setArea(area);
				}
				if (candidateStateId != null) {
					CandidateStateDto state = new CandidateStateDto();
					state.setId(candidateStateId);
					state.setName(candidateStateName);
					state.setIsPending(candidateStateIsPending);
					state.setIsGoal(candidateStateIsGoal);
					state.setState(candidateStateState);
					candidate.setCandidateState(state);
				}
				if (recruiterId != null) {
					RecruiterDto recruiter = new RecruiterDto();
					recruiter.setId(recruiterId);
					recruiter.setFirstname(recruiterFirstname);
					recruiter.setLastname(recruiterLastname);
					candidate.setRecruiter(recruiter);
				}
				if (countryId != null) {
					CountryDto country = new CountryDto();
					country.setId(countryId);
					country.setName(countryName);
					candidate.setCountry(country);
				}
				if (provinceId != null) {
					ProvinceDto province = new ProvinceDto();
					province.setId(provinceId);
					province.setName(provinceName);
					candidate.setProvince(province);
				}
				
				if (departmentId != null) {
					DepartmentDto department = new DepartmentDto();
					department.setId(departmentId);
					department.setName(departmentName);
					candidate.setDepartment(department);
				}
				
				if (districtId != null) {
					DistrictDto district = new DistrictDto();
					district.setId(districtId);
					district.setName(districtName);
					candidate.setDistrict(district);
				}
				
				if (recruitmentSourceId !=null) {
					RecruimentSourceDto recruitmentSource = new RecruimentSourceDto();
					recruitmentSource.setId(recruitmentSourceId);
					recruitmentSource.setName(recruitmentSourceName);
					candidate.setRecruimentSource(recruitmentSource);
				}
				
				if (educationLevelId != null) {
					EducationLevelDto educationLevel = new EducationLevelDto();
					educationLevel.setId(educationLevelId);
					educationLevel.setName(educationLevelName);
					candidate.setEducationLevel(educationLevel);
				}

				return candidate;
			} else {
				return null;
			}

		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e.getMessage());
		}
	}*/
	
	//@SuppressWarnings("unchecked")
	public Candidate findByDocumentNumber(String documentNumber)
			throws EaoException {
		try {
			TypedQuery<Candidate> q = em
					.createQuery(
							"SELECT c FROM Candidate c WHERE c.documentNumber = :documentNumber",
							Candidate.class);
			q.setParameter("documentNumber", documentNumber);
			Candidate candidate = q.getSingleResult();
			
			return candidate;
			
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	public List<Candidate> findByDocumentNumberList(String documentNumber)
			throws EaoException {
		try {
			TypedQuery<Candidate> q = em
					.createQuery(
							"SELECT c FROM Candidate c WHERE c.documentNumber = :documentNumber",
							Candidate.class);
			q.setParameter("documentNumber", documentNumber);
			List<Candidate> candidates = q.getResultList();
			
			return candidates;
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e.getMessage());
		}
	}

	public void add(Candidate candidate) throws EaoException {
		try {
			
			em.persist(candidate);
			em.flush();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e.getMessage());
		}

	}
	
	public Candidate update(Candidate candidate) throws EaoException {
		try {
			candidate = em.merge(candidate);
			em.flush();
			return candidate;
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e.getMessage());
		}

	}
	

	@SuppressWarnings("unchecked")
	public List<Candidate> findList(List<Integer> areasId,
			List<Integer> subAreasId, Integer recruiterId, Integer interviewStateId,
			Integer trainingStateId,Integer ojtStateId,
			String scheduledAtFormatter, String createdAtFormatter,
			String documentNumber,
			String name) throws EaoException {

		try {
			
			

			String query = "SELECT c.id,c.document_number,c.firstname,"
					+ "c.lastname,c.scheduled_at,c.created_at,"
					+ "a.id,a.name,ap.id,ap.name,"
					+ "r.id,p.firstname,p.lastname,i.interviewed_at,"
					+ "ins.name,tr.training_at,trs.name,oj.ojt_at,ojs.name "
					+ "FROM recruitment.candidate c "
					+ "left join recruitment.area a on a.id=c.area_id "
					+ "left join recruitment.area ap on ap.id=a.area_id "
					+ "left join recruitment.recruiter r on r.id=c.recruiter_id "
					+ "left join security.user u on u.id=r.user_id "
					+ "left join security.person p on p.id=u.person_id "
					+ "left join recruitment.interview i on c.id=i.candidate_id " 
					+ "left join recruitment.interview_state ins on ins.id=i.interview_state_id "
					+ "left join recruitment.training tr on tr.interview_id=i.id "
					+ "left join recruitment.training_state trs on trs.id=tr.training_state_id "
					+ "left join recruitment.ojt oj on oj.training_id=tr.id "
					+ "left join recruitment.ojt_state ojs on ojs.id=oj.ojt_state_id "
					+ "WHERE c.id>0 "
					+ "and (i.id=(select max(id) from interview where candidate_id=c.id) "
					+ "or (select max(id) from interview where candidate_id=c.id) is null) ";

			if (scheduledAtFormatter != null
					&& !scheduledAtFormatter.trim().equals("")) {
				query += " and c.scheduled_at like '%" + scheduledAtFormatter
						+ "%' ";
			}
			
			if (createdAtFormatter != null
					&& !createdAtFormatter.trim().equals("")) {
				query += " and c.created_at like '%" + createdAtFormatter
						+ "%' ";
			}

			if (recruiterId != null && recruiterId > 0) {
				query += " and c.recruiter_id = " + recruiterId + " ";
			}
			
			if (interviewStateId != null && interviewStateId > 0) {
				query += " and ins.id = " + interviewStateId + " ";
			}
			
			if (trainingStateId != null && trainingStateId > 0) {
				query += " and trs.id = " + trainingStateId + " ";
			}
			
			if (ojtStateId != null && ojtStateId > 0) {
				query += " and ojs.id = " + ojtStateId + " ";
			}

			if (areasId != null && areasId.size() > 0) {
				query += " and ap.id in ";
				query += " ( ";
				for (int i = 0; i < areasId.size(); i++) {
					Integer areaId = areasId.get(i);
					if (i < areasId.size() - 1) {
						query += areaId + ",";
					} else {
						query += areaId;
					}
				}
				query += " ) ";
			} 
			  //else {  query += " and ap.id is null "; }
			 

			if (subAreasId != null && subAreasId.size() > 0) {
				query += " and a.id in ";
				query += " ( ";
				for (int i = 0; i < subAreasId.size(); i++) {
					Integer subAreaId = subAreasId.get(i);
					if (i < subAreasId.size() - 1) {
						query += subAreaId + ",";
					} else {
						query += subAreaId;
					}
				}
				query += " ) ";
			} 
			  //else { query += " and a.id is null "; } 
			 

			if (documentNumber != null && !documentNumber.trim().equals("")) {
				query += " and c.document_number like '" + documentNumber
						+ "%' ";
			}

			if (name != null && !name.trim().equals("")) {
				query += " and (c.firstname like '%" + name + "%' or c.lastname like '% " + name + "%')";
			}

			query += " group by c.id order by c.id desc limit 100";

			Query q = em.createNativeQuery(query);

			/*
			 * if (areasId != null && areasId.size() > 0) {
			 * q.setParameter("areasId", areasId); if (subAreasId != null &&
			 * subAreasId.size() > 0) { q.setParameter("subAreasId",
			 * subAreasId); } }
			 */

			List<Object[]> candidateObjectList = (List<Object[]>) q
					.getResultList();

			List<Candidate> candidates = new ArrayList<Candidate>();
			if (candidateObjectList.size() > 0) {
				for (Object[] candidateObject : candidateObjectList) {

					Integer candidateId = (Integer) candidateObject[0];
					String candidateDocumentNumber = (String) candidateObject[1];
					String candidateFirstname = (String) candidateObject[2];
					String candidateLastname = (String) candidateObject[3];
					Date candidateScheduledAt = (Date) candidateObject[4];
					Date candidateCreatedAt = (Date) candidateObject[5];

					Integer candidateAreaId = (Integer) candidateObject[6];
					String candidateAreaName = (String) candidateObject[7];
					Integer candidateAreaParentId = (Integer) candidateObject[8];
					String candidateAreaParentName = (String) candidateObject[9];

					Integer candidateRecruiterId = (Integer) candidateObject[10];
					String recruiterFirstname = (String) candidateObject[11];
					String recruiterLastname = (String) candidateObject[12];
					Date candidateInterviewedAt = (Date) candidateObject[13];
					String candidateInterviewedState = (String) candidateObject[14];
					Date candidateTrainingAt = (Date) candidateObject[15];
					String candidateTrainingState = (String) candidateObject[16];
					Date candidateOjtAt = (Date) candidateObject[17];
					String candidateOjtState = (String) candidateObject[18];

					Candidate candidate = new Candidate();
					candidate.setId(candidateId);
					candidate.setDocumentNumber(candidateDocumentNumber);
					candidate.setFirstname(candidateFirstname);
					candidate.setLastname(candidateLastname);
					candidate.setScheduledAt(candidateScheduledAt);
					candidate.setCreatedAt(candidateCreatedAt);
					/*candidate.setInterviewedAt(candidateInterviewedAt);
					candidate.setInterviewStateName(candidateInterviewedState);
					candidate.setTrainingAt(candidateTrainingAt);
					candidate.setTrainingStateName(candidateTrainingState);
					candidate.setOjtAt(candidateOjtAt);
					candidate.setOjtStateName(candidateOjtState);*/
					
					if (candidateAreaId != null) {
						Area area = new Area();
						area.setId(candidateAreaId);
						area.setName(candidateAreaName);
						if (candidateAreaParentId != null) {
							Area areaParent = new Area();
							areaParent.setId(candidateAreaParentId);
							areaParent.setName(candidateAreaParentName);
							area.setArea(areaParent);
						}
						candidate.setArea(area);
					}
					
					if (candidateRecruiterId != null) {
						User recruiter = new User();
						recruiter.setId(candidateRecruiterId);
						recruiter.setFirstname(recruiterFirstname);
						recruiter.setLastname(recruiterLastname);
						candidate.setRecruiter(recruiter);
					}
					candidates.add(candidate);

				}
			}
			return candidates;
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e.getMessage());
		}
	}

}
