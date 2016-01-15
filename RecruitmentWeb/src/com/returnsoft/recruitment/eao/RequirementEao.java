package com.returnsoft.recruitment.eao;

import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.returnsoft.recruitment.entity.Requirement;
import com.returnsoft.recruitment.entity.RequirementDto;
import com.returnsoft.recruitment.enumeration.MonthEnum;
import com.returnsoft.recruitment.enumeration.YearEnum;
import com.returnsoft.recruitment.exception.EaoException;

@LocalBean
@Stateless
public class RequirementEao {
	
	@PersistenceContext
	private EntityManager em;
	
	
	public List<Requirement> findByRecruiter(Integer recruiterId) throws EaoException{
		try {
			String query=
			    	"SELECT rq "
			    	+"FROM Requirement rq "
			    	+"left join rq.users ru "
			    	+"left join ru.user u " 
			    	+"where u.id=:recruiterId and rq.isActive=1";
			
			TypedQuery<Requirement> q = em.createQuery(query,Requirement.class);
			
	    	q.setParameter("recruiterId", recruiterId);
	    	
	    	List<Requirement> requirements = q.getResultList();
	    	
			return requirements;
			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e.getMessage());
		}
	}
	
	public List<Requirement> findList(YearEnum periodYear, MonthEnum periodMonth, Integer areaId, Integer subAreaId, Integer recruiterId) throws EaoException{
		
		try {
			String query=
			    	"SELECT rq "
			    	+"FROM Requirement rq "
			    	+"left join rq.users ru "
			    	+"left join ru.user u " 
			    	+"left join rq.area a "
			    	+"left join a.area ap "
			    	+"where rq.id>0 ";
			    	
			    	if (areaId!=null && areaId>0) {
						query+=" and ap.id=:areaId ";
					}
			    	
			    	if (subAreaId!=null && subAreaId>0) {
			    		query+=" and a.id=:subAreaId";
					}
			    	
			    	if (periodYear!=null) {
			    		query+=" and rq.periodYear=:periodYear ";
					}
			    	
			    	if (periodMonth!=null) {
			    		query+=" and rq.periodMonth=:periodMonth ";
					}
			    	
			    	if (recruiterId!=null && recruiterId>0) {
			    		query+=" and u.id=:recruiterId";
					}
			    	
			    	query+=" group by rq.id";
			    	
			    	TypedQuery<Requirement> q = em.createQuery(query,Requirement.class);
			    	
			    	if (areaId!=null && areaId>0) {
			    		q.setParameter("areaId", areaId);
					}
			    	
			    	if (subAreaId!=null && subAreaId>0) {
			    		q.setParameter("subAreaId", subAreaId);
					}
			    	
			    	if (periodYear!=null) {
			    		q.setParameter("periodYear", periodYear);
					}
			    	
			    	if (periodMonth!=null) {
			    		q.setParameter("periodMonth", periodMonth);
					}
			    	
			    	if (recruiterId!=null && recruiterId>0) {
			    		q.setParameter("recruiterId", recruiterId);
					}
			    	
			    	List<Requirement> requirements = q.getResultList();
			    	
			    	
					return requirements;
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e.getMessage());
		}
		
		
		    	
	}
	
	
	
	public RequirementDto findRequirementDetail(Date startTraining, Date endTraining,Date startOjt, Integer subAreaId, Integer recruiterId){
		
		String query=
				"select "
				+"cast(count(t.id) as char) as total_training, "
				+"cast(sum(CASE WHEN (ts.is_goal=true) THEN 1 else 0 end) as char) as total_training_fin, "
				+"cast(count(o.id) as char) as total_ojt, "
				+"cast(sum(CASE WHEN (os.is_goal=true) THEN 1 else 0 end) as char) as total_ojt_fin "
				//+"cast(sum(CASE WHEN (o.id>0 and (datediff(o.ended_at,o.ojt_at)<=5 or datediff(o.ended_at,o.ojt_at) is null)) THEN 1 else 0 end) as char) as total_ojt_menor, " 
				//+"cast(sum(CASE WHEN (o.id>0 and datediff(o.ended_at,o.ojt_at)>5) THEN 1 else 0 end) as char) as total_ojt_mayor "
				+"from recruitment.training t "
				+"left join recruitment.training_state ts on ts.id=t.training_state_id "
				+"left join recruitment.ojt o on o.training_id=t.id and o.ojt_at>= '"+startOjt+"' "
				+"left join recruitment.ojt_state os on os.id=o.ojt_state_id "
				+"left join recruitment.interview i on i.id=t.interview_id "
				+"left join recruitment.candidate c on c.id=i.candidate_id "
				+"left join recruitment.area a on a.id=c.area_id "
				+"left join recruitment.area ap on ap.id=a.area_id "
				+"left join recruitment.recruiter r on r.id=t.recruiter_id " 
				+"left join security.user u on u.id=r.user_id "
				+"left join security.person p on p.id=u.person_id "
				+"where t.training_at between '"+startTraining+"' and '"+endTraining+"' "
				+"and t.recruiter_id="+recruiterId+" and c.area_id="+subAreaId+" ";
				
		    	
		    	
		    	Query q = em.createNativeQuery(query);
		    	
		    	Object[] object= (Object[]) q.getSingleResult();
		    	
		    	if (object.length > 0) {
					
						String totalStartTraining = (String) object[0];
						String totalEndTraining = (String) object[1];
						String totalStartOjt = (String) object[2];
						String totalEndOjt = (String) object[3];
						//String totalStartOjtLesser = (String) object[3];
						//System.out.println("totalStartOjtLesser"+totalStartOjtLesser);
						//String totalStartOjtGreater = (String) object[4];
						//System.out.println("totalStartOjtGreater"+totalStartOjtGreater);
						
						RequirementDto requirement= new RequirementDto();
						if (totalStartTraining!=null) {
							requirement.setAmountStartTraining(Integer.parseInt(totalStartTraining));	
						}else{
							requirement.setAmountStartTraining(0);
						}
						
						if (totalEndTraining!=null) {
							requirement.setAmountEndTraining(Integer.parseInt(totalEndTraining));	
						}else{
							requirement.setAmountEndTraining(0);
						}
						
						if (totalStartOjt!=null) {
							requirement.setAmountStartOjt(Integer.parseInt(totalStartOjt));	
						}else{
							requirement.setAmountStartOjt(0);
						}
						
						if (totalEndOjt!=null) {
							requirement.setAmountEndOjt(Integer.parseInt(totalEndOjt));
						}else{
							requirement.setAmountEndOjt(0);
						}
						
						/*if (totalStartOjtLesser!=null) {
							requirement.setAmountStartOjtLesser(Integer.parseInt(totalStartOjtLesser));
						}else{
							requirement.setAmountStartOjtLesser(0);
						}
						
						if (totalStartOjtGreater!=null) {
							requirement.setAmountStartOjtGreater(Integer.parseInt(totalStartOjtGreater));
						}else{
							requirement.setAmountStartOjtGreater(0);
						}*/
						
						return requirement;	
				}else{
					return null;
				}
		    	
				
		    	
	}
	
	
	
	public void add(Requirement requirement) throws EaoException {
		try {
			
			Long newCorrelative = generateNewCorrelative();
			String code = newCorrelative.toString(); 
			while (code.length()<3) {
				code="0"+code;
			}
		
			code= requirement.getPeriodYear().getId()+requirement.getPeriodMonth().getCode() +"-"+ requirement.getArea().getCode() +"-"+ code; 
			
			requirement.setCode(code);
			em.persist(requirement);

			em.flush();

			
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e.getMessage());
		}
	}
	
	public Requirement edit(Requirement requirement) throws EaoException {
		try {
			
			requirement = em.merge(requirement);
			em.flush();
			
			return requirement;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e.getMessage());
		}

	}
	
	
	
	public Long generateNewCorrelative() throws EaoException{
		try {
			
			String query = "SELECT max(cast(substring(a.code,12) as signed)) "
					+ "FROM Requirement a ";
			
			Query q = em.createQuery(query);

			Long maxCorrelative = (Long)q.getSingleResult();
			
			System.out.println("maxCorrelative:"+maxCorrelative);
			
			Long newCorrelative;
			
			if (maxCorrelative!=null && maxCorrelative>0) {
				newCorrelative = maxCorrelative+1;
			}else{
				newCorrelative = new Long(1); 
			}

			return newCorrelative;
			
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e.getMessage());
		}
	}
	
	
	
	public Requirement findById(Integer requirementId) throws EaoException {
		try {
			
			Requirement requirement = em.find(Requirement.class, requirementId);
			return requirement;

		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e.getMessage());
		}
	}


}
