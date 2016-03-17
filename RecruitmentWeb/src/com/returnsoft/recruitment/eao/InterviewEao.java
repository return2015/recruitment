package com.returnsoft.recruitment.eao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.returnsoft.recruitment.entity.Interview;
import com.returnsoft.recruitment.exception.EaoException;

@Stateless
public class InterviewEao {
	
	@PersistenceContext
	private EntityManager em;
	
	public Interview edit(Interview interview) throws EaoException {
		try {
			
			interview = em.merge(interview);
			em.flush();
			
			return interview;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e.getMessage());
		}

	}
	
	
	public Interview findById(Long interviewId) throws EaoException {
		try {
			
			Interview interview = em.find(Interview.class, interviewId);
			return interview;

		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e.getMessage());
		}
	}
	
	
	public void add(Interview interview) throws EaoException {
		try {
			
			em.persist(interview);
			em.flush();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e.getMessage());
		}

	}
	
	public List<Interview> findByRequirementId(Integer requirementId)
			throws EaoException {
		try {
			

			String query = "select i "
					+ "from Interview i "
					+ "left join i.requirementUser ru "
					+ "left join ru.requirement r "
					+ "where r.id=:requirementId ";

			TypedQuery<Interview> q = em.createQuery(query,Interview.class);
			q.setParameter("requirementId", requirementId);
			return q.getResultList();
			
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e.getMessage());
		}
	}
	
	public List<Interview> findByCandidateId(Long candidateId)
			throws EaoException {
		try {

			String query = "select i "
					+ "from Interview i "
					+ "left join Candidate c "
					+ "where c.id=:candidateId ";

			TypedQuery<Interview> q = em.createQuery(query,Interview.class);
			q.setParameter("candidateId", candidateId);
			return q.getResultList();

			//List<Object[]> objectsList = (List<Object[]>) q.getResultList();
			
			//997139919 - 

			//List<InterviewDto> entities = new ArrayList<InterviewDto>();
//			if (objectsList.size() > 0) {
//				for (Object[] object : objectsList) {
//
//					Integer id = (Integer) object[0];
//					Date interviewedAt = (Date) object[1];
//					Date createdAt = (Date) object[2];
//					String comment = (String) object[3];
//					Integer stateId = (Integer) object[4];
//					String stateName = (String) object[5];

					/*candidateId = (Integer) object[6];
					String candidateTypeDocumentNumber = (String) object[7];
					String candidateDocumentNumber = (String) object[8];
					String candidateFirstname = (String) object[9];
					String candidateLastname = (String) object[10];*/

					/*Integer recruiterId = (Integer) object[11];
					String recruiterFirstname = (String) object[12];
					String recruiterLastname = (String) object[13];*/

//					InterviewDto entity = new InterviewDto();
//					entity.setId(id);
//					entity.setComment(comment);
//					entity.setInterviewedAt(interviewedAt);
//					entity.setCreatedAt(createdAt);
//					if (stateId != null && stateId > 0) {
//						InterviewStateDto state = new InterviewStateDto();
//						state.setId(stateId);
//						state.setName(stateName);
//						entity.setInterviewState(state);
//					}
					/*if (recruiterId != null && recruiterId > 0) {
						RecruiterDto recruiter = new RecruiterDto();
						recruiter.setId(recruiterId);
						recruiter.setFirstname(recruiterFirstname);
						recruiter.setLastname(recruiterLastname);
						entity.setRecruiter(recruiter);
					}*/
					/*if (candidateId != null && candidateId > 0) {
						CandidateDto candidate = new CandidateDto();
						candidate.setId(candidateId);
						candidate.setDocumentType(candidateTypeDocumentNumber);
						candidate.setDocumentNumber(candidateDocumentNumber);
						candidate.setFirstname(candidateFirstname);
						candidate.setLastname(candidateLastname);
						entity.setCandidate(candidate);
					}*/

//					entities.add(entity);
//
//				}
//			}

			//return entities;
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e.getMessage());
		}
	}
	
	
	public List<Interview> findListLimit(List<Integer> areasId,
			List<Integer> subAreasId, Integer interviewStateId,
			Date interviewedAt, Date scheduledAt, Date createdAt,
			String documentNumber,
			String names, Integer userId, Integer first, Integer limit ) throws EaoException {

		try {
			

			String query = "SELECT i "
					+ "from Interview i " 
					+ "left join i.interviewState ins "
					+ "left join i.requirementUser ru "
					+ "left join ru.requirement r "
					+ "left join r.area a "
					+ "left join a.area ap "
					+ "left join i.candidate c "
					+ "left join i.createdBy u "
					+ "WHERE i.id>0 ";

			if (interviewedAt != null ) {
				query += " and i.interviewedAt between :interviewedAtStart and :interviewedAtEnd ";
			}
			
			if (scheduledAt != null ) {
				query += " and i.scheduledAt between :scheduledAtStart and :scheduledAtEnd ";
			}
			
			if (createdAt != null) {
				query += " and i.createdAt between :createdAtStart and :createdAtEnd ";
			}

			if (interviewStateId != null && interviewStateId > 0) {
				query += " and ins.id =:interviewStateId ";
			}

			if (areasId != null && areasId.size() > 0) {
				query += " and ap.id in :areasId ";
			} 
			 
			if (subAreasId != null && subAreasId.size() > 0) {
				query += " and a.id in :subAreasId ";
			} 

			if (documentNumber != null && !documentNumber.trim().equals("")) {
				query += " and c.documentNumber like :documentNumber";
			}

			if (names != null && names.trim().length()>0) {
				query += " and (c.firstname like :names or c.lastname like :names)";
			}
			
			if (userId!=null && userId>0) {
				query += " and u.id=:userId ";
			}

			TypedQuery<Interview> q = em.createQuery(query,Interview.class);
			
			if (scheduledAt != null ) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				q.setParameter("scheduledAtStart", sdf2.parse(sdf.format(scheduledAt)+" 00:00:00"));
				q.setParameter("scheduledAtEnd", sdf2.parse(sdf.format(scheduledAt)+" 23:59:59"));
			}
			
			if (interviewedAt != null ) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				q.setParameter("interviewedAtStart", sdf2.parse(sdf.format(interviewedAt)+" 00:00:00"));
				q.setParameter("interviewedAtEnd", sdf2.parse(sdf.format(interviewedAt)+" 23:59:59"));
			}
			
			if (createdAt != null) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				q.setParameter("createdAtStart", sdf2.parse(sdf.format(createdAt)+" 00:00:00"));
				q.setParameter("createdAtEnd", sdf2.parse(sdf.format(createdAt)+" 23:59:59"));
			}

			if (interviewStateId != null && interviewStateId > 0) {
				q.setParameter("interviewStateId", interviewStateId);
			}
			
			if (areasId != null && areasId.size() > 0) {
				q.setParameter("areasId", areasId);
			} 
			 
			if (subAreasId != null && subAreasId.size() > 0) {
				q.setParameter("subAreasId", subAreasId);
			} 

			if (documentNumber != null && !documentNumber.trim().equals("")) {
				q.setParameter("documentNumber", documentNumber);
			}

			if (names != null && names.trim().length()>0) {
				q.setParameter("names", names);
			}
			
			if (userId!=null && userId>0) {
				q.setParameter("userId", userId);
			}
			
			
			q.setFirstResult(first);
			q.setMaxResults(limit);
			

			List<Interview> interviews = q.getResultList();
			
			return interviews;
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e.getMessage());
		}
	}
	public Integer findListCount(List<Integer> areasId,
			List<Integer> subAreasId, Integer interviewStateId,
			Date interviewedAt, Date scheduledAt, Date createdAt,
			String documentNumber,
			String names,Integer userId ) throws EaoException {

		try {
			

			String query = "SELECT count(i.id) "
					+ "from Interview i " 
					+ "left join i.interviewState ins "
					+ "left join i.requirementUser ru "
					+ "left join ru.requirement r "
					+ "left join r.area a "
					+ "left join a.area ap "
					+ "left join i.candidate c "
					+ "left join i.createdBy u "
					+ "WHERE i.id>0 ";
					/*+ "and (i.id=(select max(id) from interview where candidate_id=c.id) "
					+ "or (select max(id) from interview where candidate_id=c.id) is null) ";*/

			if (interviewedAt != null ) {
				query += " and i.interviewedAt between :interviewedAtStart and :interviewedAtEnd ";
			}
			
			if (scheduledAt != null ) {
				query += " and i.scheduledAt between :scheduledAtStart and :scheduledAtEnd ";
			}
			
			if (createdAt != null) {
				query += " and i.createdAt between :createdAtStart and :createdAtEnd ";
			}

			if (interviewStateId != null && interviewStateId > 0) {
				query += " and ins.id =:interviewStateId ";
			}

			if (areasId != null && areasId.size() > 0) {
				query += " and ap.id in :areasId ";
			} 
			 
			if (subAreasId != null && subAreasId.size() > 0) {
				query += " and a.id in :subAreasId ";
			} 

			if (documentNumber != null && !documentNumber.trim().equals("")) {
				query += " and c.documentNumber like :documentNumber";
			}

			if (names != null && names.trim().length()>0) {
				query += " and (c.firstname like :names or c.lastname like :names)";
			}
			
			if (userId!=null && userId>0) {
				query += " and u.id=:userId ";
			}

			Query q = em.createQuery(query);
			
			if (scheduledAt != null ) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				q.setParameter("scheduledAtStart", sdf2.parse(sdf.format(scheduledAt)+" 00:00:00"));
				q.setParameter("scheduledAtEnd", sdf2.parse(sdf.format(scheduledAt)+" 23:59:59"));
			}
			
			if (interviewedAt != null ) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				q.setParameter("interviewedAtStart", sdf2.parse(sdf.format(interviewedAt)+" 00:00:00"));
				q.setParameter("interviewedAtEnd", sdf2.parse(sdf.format(interviewedAt)+" 23:59:59"));
			}
			
			if (createdAt != null) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				q.setParameter("createdAtStart", sdf2.parse(sdf.format(createdAt)+" 00:00:00"));
				q.setParameter("createdAtEnd", sdf2.parse(sdf.format(createdAt)+" 23:59:59"));
			}

			if (interviewStateId != null && interviewStateId > 0) {
				q.setParameter("interviewStateId", interviewStateId);
			}
			
			if (areasId != null && areasId.size() > 0) {
				q.setParameter("areasId", areasId);
			} 
			 
			if (subAreasId != null && subAreasId.size() > 0) {
				q.setParameter("subAreasId", subAreasId);
			} 

			if (documentNumber != null && !documentNumber.trim().equals("")) {
				q.setParameter("documentNumber", documentNumber);
			}

			if (names != null && names.trim().length()>0) {
				q.setParameter("names", names);
			}
			
			if (userId!=null && userId>0) {
				q.setParameter("userId", userId);
			}
						
			Long interviewsCount = (Long)q.getSingleResult();
			 
			
			return interviewsCount.intValue();
			
		} catch (NoResultException e) {
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e.getMessage());
		}
	}
	
	

}
