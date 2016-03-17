package com.returnsoft.recruitment.eao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.returnsoft.recruitment.entity.Area;
import com.returnsoft.recruitment.entity.Requirement;
import com.returnsoft.recruitment.entity.RequirementDto;
import com.returnsoft.recruitment.entity.User;
import com.returnsoft.recruitment.enumeration.MonthEnum;
import com.returnsoft.recruitment.enumeration.YearEnum;
import com.returnsoft.recruitment.exception.EaoException;

//@LocalBean
@Stateless
public class RequirementEao {

	@PersistenceContext
	private EntityManager em;

	public List<Requirement> findByRecruiter(Integer recruiterId) throws EaoException {
		try {
			String query = "SELECT rq " + "FROM Requirement rq " + "left join rq.users ru " + "left join ru.user u "
					+ "where u.id=:recruiterId and rq.isActive=1";

			TypedQuery<Requirement> q = em.createQuery(query, Requirement.class);

			q.setParameter("recruiterId", recruiterId);

			List<Requirement> requirements = q.getResultList();

			return requirements;

		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e.getMessage());
		}
	}

	public List<RequirementDto> findListDetail(YearEnum yearEnum, MonthEnum monthEnum, Integer areaId,
			Integer subAreaId, Integer recruiterId) {

		String query = "select "
				+ "rq.id,rq.code,rq.periodMonth,rq.periodYear,rq.amount,rq.startTraining,rq.endTraining,rq.startOjt, "
				+ "rqu.amount," + "u.id,u.firstname,u.lastname, "
				+ "a.id,a.name,ap.id,ap.name,c.id,c.firstname,c.lastname,"
				+ "count(ip.id), "
				+ "sum(case when ips.isGoal=true then 1 else 0 end), "
				+ "count(t.id), "
				+ "sum(case when ts.isGoal=true then 1 else 0 end), "
				+ "count(oj.id), "
				+ "sum(case when ojs.isGoal=true then 1 else 0 end) "
				+ "from RequirementUser rqu "
				+ "left join rqu.requirement rq " 
				+ "left join rqu.user u " 
				+ "left join rq.area a "
				+ "left join a.area ap " 
				+ "left join ap.coordinator c " 
				+ "left join rqu.interviews ip "
				+ "left join ip.interviewState ips "
				+ "left join ip.training t "
				+ "left join t.trainingState ts "
				+ "left join t.ojt oj "
				+ "left join oj.ojtState ojs "
				+ "where rqu.requirementId>0 ";

		if (areaId != null && areaId > 0) {
			query += " and ap.id=:areaId ";
		}

		if (subAreaId != null && subAreaId > 0) {
			query += " and a.id=:subAreaId";
		}

		if (yearEnum != null) {
			query += " and rq.periodYear=:periodYear ";
		}

		if (monthEnum != null) {
			query += " and rq.periodMonth=:periodMonth ";
		}

		if (recruiterId != null && recruiterId > 0) {
			query += " and u.id=:recruiterId";
		}

		query += " group by rqu.requirementId,rqu.userId ";

		Query q = em.createQuery(query);

		if (areaId != null && areaId > 0) {
			q.setParameter("areaId", areaId);
		}

		if (subAreaId != null && subAreaId > 0) {
			q.setParameter("subAreaId", subAreaId);
		}

		if (yearEnum != null) {
			q.setParameter("periodYear", yearEnum);
		}

		if (monthEnum != null) {
			q.setParameter("periodMonth", monthEnum);
		}

		if (recruiterId != null && recruiterId > 0) {
			q.setParameter("recruiterId", recruiterId);
		}

		// List<Object[]> objectList = (List<Object[]>) q.getResultList();

		// List<Object[]> objectList = (List<Object[]>)rawList;

		List<Object[]> objectList = (List<Object[]>) q.getResultList();

		List<RequirementDto> requirements = new ArrayList<RequirementDto>();
		if (objectList.size() > 0) {
			System.out.println("la lista es mayor a cero");
			for (Object[] object : objectList) {
				// System.out.println("en el for");
				// System.out.println(object[0]);
				//System.out.println("inter pen :" + object[19]);
				//System.out.println("inter gol :" + object[20]);
				// System.out.println(object[1]);
				// System.out.println(object[2]);

				// Object[] object = (Object[])obj;

				Integer requirementId = (Integer) object[0];
				String requirementCode = (String) object[1];
				MonthEnum requirementPeriodMonth = (MonthEnum) object[2];
				YearEnum requirementPeriodYear = (YearEnum) object[3];
				Integer requirementAmount = (Integer) object[4];
				Date startTraining = (Date) object[5];
				Date endTraining = (Date) object[6];
				Date startOjt = (Date) object[7];

				Integer requirementUserAmount = (Integer) object[8];
				Integer requirementRecruiterId = (Integer) object[9];

				String recruiterFirstname = (String) object[10];
				String recruiterLastname = (String) object[11];
				Integer requirementAreaId = (Integer) object[12];
				String requirementAreaName = (String) object[13];

				Integer requirementAreaParentId = (Integer) object[14];
				String requirementAreaParentName = (String) object[15];

				Integer requirementRecruiterAreaId = (Integer) object[16];
				String recruiterAreaFirstname = (String) object[17];
				String recruiterAreaLastname = (String) object[18];

				Long amountStartInterview = (Long) object[19];
				BigDecimal amountEndInterview = (BigDecimal) object[20];
				
				Long amountStartTraining = (Long) object[21];
				BigDecimal amountEndTraining = (BigDecimal) object[22];
				
				Long amountStartOjt = (Long) object[23];
				BigDecimal amountEndOjt = (BigDecimal) object[24];

				if (requirementId != null && requirementId > 0) {

					RequirementDto requirement = new RequirementDto();
					requirement.setId(requirementId);
					requirement.setCode(requirementCode);
					requirement.setPeriodYear(requirementPeriodYear);
					requirement.setPeriodMonth(requirementPeriodMonth);
					requirement.setAmount(requirementAmount);
					requirement.setStartTraining(startTraining);
					requirement.setEndTraining(endTraining);
					requirement.setStartOjt(startOjt);
					requirement.setAmountStartInterview(amountStartInterview.intValue());
					requirement.setAmountEndInterview(amountEndInterview.intValue());
					requirement.setAmountStartTraining(amountStartTraining.intValue());
					requirement.setAmountEndTraining(amountEndTraining.intValue());
					requirement.setAmountStartOjt(amountStartOjt.intValue());
					requirement.setAmountEndOjt(amountEndOjt.intValue());

					// if (requirementRecruiterId!=null &&
					// requirementRecruiterId>0) {

					User recruiter = new User();
					recruiter.setId(requirementRecruiterId);
					recruiter.setFirstname(recruiterFirstname);
					recruiter.setLastname(recruiterLastname);
					requirement.setRecruiter(recruiter);
					requirement.setRecruiterAmount(requirementUserAmount);
					System.out.println(requirementUserAmount);
					// }

					// if (requirementAreaId!=null && requirementAreaId>0) {
					Area area = new Area();
					area.setId(requirementAreaId);
					area.setName(requirementAreaName);
					if (requirementAreaParentId != null && requirementAreaId > 0) {
						Area areaParent = new Area();
						areaParent.setId(requirementAreaParentId);
						areaParent.setName(requirementAreaParentName);
						if (requirementRecruiterAreaId != null && requirementRecruiterAreaId > 0) {
							User recruiterArea = new User();
							recruiterArea.setId(requirementRecruiterAreaId);
							recruiterArea.setFirstname(recruiterAreaFirstname);
							recruiterArea.setLastname(recruiterAreaLastname);
							areaParent.setCoordinator(recruiterArea);
						}
						area.setArea(areaParent);
					}
					requirement.setArea(area);
					// }

					requirements.add(requirement);
				}
			}
		}

		return requirements;
		// return null;

	}
	
	public List<RequirementDto> findListByRecruiter(YearEnum yearEnum, MonthEnum monthEnum, Integer areaId,
			Integer subAreaId, Integer recruiterId) {

		String query = "select "
				//+ "rq.id,rq.code,rq.periodMonth,rq.periodYear,rq.amount,rq.startTraining,rq.endTraining,rq.startOjt, "
				+ "sum(rqu.amount)," + "u.id,u.firstname,u.lastname, "
				//+ "a.id,a.name,ap.id,ap.name,c.id,c.firstname,c.lastname,"
				+ "count(ip.id), "
				+ "sum(case when ips.isGoal=true then 1 else 0 end), "
				+ "count(t.id), "
				+ "sum(case when ts.isGoal=true then 1 else 0 end), "
				+ "count(oj.id), "
				+ "sum(case when ojs.isGoal=true then 1 else 0 end) "
				+ "from RequirementUser rqu "
				+ "left join rqu.requirement rq " 
				+ "left join rqu.user u " 
				/*+ "left join rq.area a "
				+ "left join a.area ap " */
				//+ "left join ap.coordinator c " 
				+ "left join rqu.interviews ip "
				+ "left join ip.interviewState ips "
				+ "left join ip.training t "
				+ "left join t.trainingState ts "
				+ "left join t.ojt oj "
				+ "left join oj.ojtState ojs "
				+ "where rqu.requirementId>0 ";

		if (areaId != null && areaId > 0) {
			query += " and ap.id=:areaId ";
		}

		if (subAreaId != null && subAreaId > 0) {
			query += " and a.id=:subAreaId";
		}

		if (yearEnum != null) {
			query += " and rq.periodYear=:periodYear ";
		}

		if (monthEnum != null) {
			query += " and rq.periodMonth=:periodMonth ";
		}

		if (recruiterId != null && recruiterId > 0) {
			query += " and u.id=:recruiterId";
		}

		query += " group by rqu.userId ";

		Query q = em.createQuery(query);

		if (areaId != null && areaId > 0) {
			q.setParameter("areaId", areaId);
		}

		if (subAreaId != null && subAreaId > 0) {
			q.setParameter("subAreaId", subAreaId);
		}

		if (yearEnum != null) {
			q.setParameter("periodYear", yearEnum);
		}

		if (monthEnum != null) {
			q.setParameter("periodMonth", monthEnum);
		}

		if (recruiterId != null && recruiterId > 0) {
			q.setParameter("recruiterId", recruiterId);
		}

		// List<Object[]> objectList = (List<Object[]>) q.getResultList();

		// List<Object[]> objectList = (List<Object[]>)rawList;

		List<Object[]> objectList = (List<Object[]>) q.getResultList();

		List<RequirementDto> requirements = new ArrayList<RequirementDto>();
		if (objectList.size() > 0) {
			System.out.println("la lista es mayor a cero");
			for (Object[] object : objectList) {
				// System.out.println("en el for");
				// System.out.println(object[0]);
				//System.out.println("inter pen :" + object[19]);
				//System.out.println("inter gol :" + object[20]);
				// System.out.println(object[1]);
				// System.out.println(object[2]);

				// Object[] object = (Object[])obj;

				/*Integer requirementId = (Integer) object[0];
				String requirementCode = (String) object[1];
				MonthEnum requirementPeriodMonth = (MonthEnum) object[2];
				YearEnum requirementPeriodYear = (YearEnum) object[3];
				Integer requirementAmount = (Integer) object[4];
				Date startTraining = (Date) object[5];
				Date endTraining = (Date) object[6];
				Date startOjt = (Date) object[7];*/

				Long requirementUserAmount = (Long) object[0];
				Integer requirementRecruiterId = (Integer) object[1];

				String recruiterFirstname = (String) object[2];
				String recruiterLastname = (String) object[3];
				//Integer requirementAreaId = (Integer) object[12];
				//String requirementAreaName = (String) object[13];

				//Integer requirementAreaParentId = (Integer) object[14];
				//String requirementAreaParentName = (String) object[15];

				//Integer requirementRecruiterAreaId = (Integer) object[16];
				//String recruiterAreaFirstname = (String) object[17];
				//String recruiterAreaLastname = (String) object[18];

				Long amountStartInterview = (Long) object[4];
				BigDecimal amountEndInterview = (BigDecimal) object[5];
				
				Long amountStartTraining = (Long) object[6];
				BigDecimal amountEndTraining = (BigDecimal) object[7];
				
				Long amountStartOjt = (Long) object[8];
				BigDecimal amountEndOjt = (BigDecimal) object[9];

				//if (requirementId != null && requirementId > 0) {

					RequirementDto requirement = new RequirementDto();
					/*requirement.setId(requirementId);
					requirement.setCode(requirementCode);
					requirement.setPeriodYear(requirementPeriodYear);
					requirement.setPeriodMonth(requirementPeriodMonth);
					requirement.setAmount(requirementAmount);
					requirement.setStartTraining(startTraining);
					requirement.setEndTraining(endTraining);
					requirement.setStartOjt(startOjt);*/
					requirement.setAmountStartInterview(amountStartInterview.intValue());
					requirement.setAmountEndInterview(amountEndInterview.intValue());
					requirement.setAmountStartTraining(amountStartTraining.intValue());
					requirement.setAmountEndTraining(amountEndTraining.intValue());
					requirement.setAmountStartOjt(amountStartOjt.intValue());
					requirement.setAmountEndOjt(amountEndOjt.intValue());

					// if (requirementRecruiterId!=null &&
					// requirementRecruiterId>0) {

					User recruiter = new User();
					recruiter.setId(requirementRecruiterId);
					recruiter.setFirstname(recruiterFirstname);
					recruiter.setLastname(recruiterLastname);
					requirement.setRecruiter(recruiter);
					requirement.setRecruiterAmount(requirementUserAmount.intValue());
					//System.out.println(requirementUserAmount);
					// }

					// if (requirementAreaId!=null && requirementAreaId>0) {
					/*Area area = new Area();
					area.setId(requirementAreaId);
					area.setName(requirementAreaName);
					if (requirementAreaParentId != null && requirementAreaId > 0) {
						Area areaParent = new Area();
						areaParent.setId(requirementAreaParentId);
						areaParent.setName(requirementAreaParentName);
						if (requirementRecruiterAreaId != null && requirementRecruiterAreaId > 0) {
							User recruiterArea = new User();
							recruiterArea.setId(requirementRecruiterAreaId);
							recruiterArea.setFirstname(recruiterAreaFirstname);
							recruiterArea.setLastname(recruiterAreaLastname);
							areaParent.setCoordinator(recruiterArea);
						}
						area.setArea(areaParent);
					}
					requirement.setArea(area);*/
					// }

					requirements.add(requirement);
				//}
			}
		}

		return requirements;
		// return null;

	}
	
	public List<RequirementDto> findListByCoordinator(YearEnum yearEnum, MonthEnum monthEnum, Integer areaId,
			Integer subAreaId, Integer recruiterId) {

		String query = "select "
				//+ "rq.id,rq.code,rq.periodMonth,rq.periodYear,rq.amount,rq.startTraining,rq.endTraining,rq.startOjt, "
				+ "sum(rqu.amount)," //+ "u.id,u.firstname,u.lastname, "
				+ "ap.id,ap.name,c.id,c.firstname,c.lastname,"
				+ "count(ip.id), "
				+ "sum(case when ips.isGoal=true then 1 else 0 end), "
				+ "count(t.id), "
				+ "sum(case when ts.isGoal=true then 1 else 0 end), "
				+ "count(oj.id), "
				+ "sum(case when ojs.isGoal=true then 1 else 0 end) "
				+ "from RequirementUser rqu "
				+ "left join rqu.requirement rq " 
				+ "left join rqu.user u " 
				+ "left join rq.area a "
				+ "left join a.area ap " 
				+ "left join ap.coordinator c " 
				+ "left join rqu.interviews ip "
				+ "left join ip.interviewState ips "
				+ "left join ip.training t "
				+ "left join t.trainingState ts "
				+ "left join t.ojt oj "
				+ "left join oj.ojtState ojs "
				+ "where rqu.requirementId>0 ";

		if (areaId != null && areaId > 0) {
			query += " and ap.id=:areaId ";
		}

		if (subAreaId != null && subAreaId > 0) {
			query += " and a.id=:subAreaId";
		}

		if (yearEnum != null) {
			query += " and rq.periodYear=:periodYear ";
		}

		if (monthEnum != null) {
			query += " and rq.periodMonth=:periodMonth ";
		}

		if (recruiterId != null && recruiterId > 0) {
			query += " and u.id=:recruiterId";
		}

		query += " group by ap.id ";

		Query q = em.createQuery(query);

		if (areaId != null && areaId > 0) {
			q.setParameter("areaId", areaId);
		}

		if (subAreaId != null && subAreaId > 0) {
			q.setParameter("subAreaId", subAreaId);
		}

		if (yearEnum != null) {
			q.setParameter("periodYear", yearEnum);
		}

		if (monthEnum != null) {
			q.setParameter("periodMonth", monthEnum);
		}

		if (recruiterId != null && recruiterId > 0) {
			q.setParameter("recruiterId", recruiterId);
		}

		// List<Object[]> objectList = (List<Object[]>) q.getResultList();

		// List<Object[]> objectList = (List<Object[]>)rawList;

		List<Object[]> objectList = (List<Object[]>) q.getResultList();

		List<RequirementDto> requirements = new ArrayList<RequirementDto>();
		if (objectList.size() > 0) {
			System.out.println("la lista es mayor a cero");
			for (Object[] object : objectList) {
				// System.out.println("en el for");
				// System.out.println(object[0]);
				//System.out.println("inter pen :" + object[19]);
				//System.out.println("inter gol :" + object[20]);
				// System.out.println(object[1]);
				// System.out.println(object[2]);

				// Object[] object = (Object[])obj;

				/*Integer requirementId = (Integer) object[0];
				String requirementCode = (String) object[1];
				MonthEnum requirementPeriodMonth = (MonthEnum) object[2];
				YearEnum requirementPeriodYear = (YearEnum) object[3];
				Integer requirementAmount = (Integer) object[4];
				Date startTraining = (Date) object[5];
				Date endTraining = (Date) object[6];
				Date startOjt = (Date) object[7];*/

				Long requirementUserAmount = (Long) object[0];
				//Integer requirementRecruiterId = (Integer) object[1];

				//String recruiterFirstname = (String) object[2];
				//String recruiterLastname = (String) object[3];
				//Integer requirementAreaId = (Integer) object[1];
				//String requirementAreaName = (String) object[2];

				Integer requirementAreaParentId = (Integer) object[1];
				String requirementAreaParentName = (String) object[2];

				Integer requirementRecruiterAreaId = (Integer) object[3];
				String recruiterAreaFirstname = (String) object[4];
				String recruiterAreaLastname = (String) object[5];

				Long amountStartInterview = (Long) object[6];
				BigDecimal amountEndInterview = (BigDecimal) object[7];
				
				Long amountStartTraining = (Long) object[8];
				BigDecimal amountEndTraining = (BigDecimal) object[9];
				
				Long amountStartOjt = (Long) object[10];
				BigDecimal amountEndOjt = (BigDecimal) object[11];

				//if (requirementId != null && requirementId > 0) {

					RequirementDto requirement = new RequirementDto();
					/*requirement.setId(requirementId);
					requirement.setCode(requirementCode);
					requirement.setPeriodYear(requirementPeriodYear);
					requirement.setPeriodMonth(requirementPeriodMonth);
					requirement.setAmount(requirementAmount);
					requirement.setStartTraining(startTraining);
					requirement.setEndTraining(endTraining);
					requirement.setStartOjt(startOjt);*/
					requirement.setAmountStartInterview(amountStartInterview.intValue());
					requirement.setAmountEndInterview(amountEndInterview.intValue());
					requirement.setAmountStartTraining(amountStartTraining.intValue());
					requirement.setAmountEndTraining(amountEndTraining.intValue());
					requirement.setAmountStartOjt(amountStartOjt.intValue());
					requirement.setAmountEndOjt(amountEndOjt.intValue());

					// if (requirementRecruiterId!=null &&
					// requirementRecruiterId>0) {

					/*User recruiter = new User();
					recruiter.setId(requirementRecruiterId);
					recruiter.setFirstname(recruiterFirstname);
					recruiter.setLastname(recruiterLastname);
					requirement.setRecruiter(recruiter);
					requirement.setRecruiterAmount(requirementUserAmount);*/
					requirement.setRecruiterAmount(requirementUserAmount.intValue());
					//System.out.println(requirementUserAmount);
					// }

					// if (requirementAreaId!=null && requirementAreaId>0) {
					Area area = new Area();
					area.setId(requirementAreaParentId);
					area.setName(requirementAreaParentName);
					//if (requirementAreaParentId != null && requirementAreaId > 0) {
						//Area areaParent = new Area();
						//areaParent.setId(requirementAreaParentId);
						//areaParent.setName(requirementAreaParentName);
						if (requirementRecruiterAreaId != null && requirementRecruiterAreaId > 0) {
							User recruiterArea = new User();
							recruiterArea.setId(requirementRecruiterAreaId);
							recruiterArea.setFirstname(recruiterAreaFirstname);
							recruiterArea.setLastname(recruiterAreaLastname);
							area.setCoordinator(recruiterArea);
						}
						//area.setArea(areaParent);
					//}
					requirement.setArea(area);
					// }

					requirements.add(requirement);
				//}
			}
		}

		return requirements;
		// return null;

	}

	public List<Requirement> findList(YearEnum periodYear, MonthEnum periodMonth, Integer areaId, Integer subAreaId,
			Integer recruiterId) throws EaoException {

		try {
			String query = "SELECT rq " + "FROM Requirement rq " + "left join rq.users ru " + "left join ru.user u "
					+ "left join rq.area a " + "left join a.area ap " + "where rq.id>0 ";

			if (areaId != null && areaId > 0) {
				query += " and ap.id=:areaId ";
			}

			if (subAreaId != null && subAreaId > 0) {
				query += " and a.id=:subAreaId";
			}

			if (periodYear != null) {
				query += " and rq.periodYear=:periodYear ";
			}

			if (periodMonth != null) {
				query += " and rq.periodMonth=:periodMonth ";
			}

			if (recruiterId != null && recruiterId > 0) {
				query += " and u.id=:recruiterId";
			}

			query += " group by rq.id";

			TypedQuery<Requirement> q = em.createQuery(query, Requirement.class);

			if (areaId != null && areaId > 0) {
				q.setParameter("areaId", areaId);
			}

			if (subAreaId != null && subAreaId > 0) {
				q.setParameter("subAreaId", subAreaId);
			}

			if (periodYear != null) {
				q.setParameter("periodYear", periodYear);
			}

			if (periodMonth != null) {
				q.setParameter("periodMonth", periodMonth);
			}

			if (recruiterId != null && recruiterId > 0) {
				q.setParameter("recruiterId", recruiterId);
			}

			List<Requirement> requirements = q.getResultList();

			return requirements;
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e.getMessage());
		}

	}

	public RequirementDto findRequirementDetail(Date startTraining, Date endTraining, Date startOjt, Integer subAreaId,
			Integer recruiterId) {

		String query = "select " + "cast(count(t.id) as char) as total_training, "
				+ "cast(sum(CASE WHEN (ts.is_goal=true) THEN 1 else 0 end) as char) as total_training_fin, "
				+ "cast(count(o.id) as char) as total_ojt, "
				+ "cast(sum(CASE WHEN (os.is_goal=true) THEN 1 else 0 end) as char) as total_ojt_fin "
				// +"cast(sum(CASE WHEN (o.id>0 and
				// (datediff(o.ended_at,o.ojt_at)<=5 or
				// datediff(o.ended_at,o.ojt_at) is null)) THEN 1 else 0 end) as
				// char) as total_ojt_menor, "
				// +"cast(sum(CASE WHEN (o.id>0 and
				// datediff(o.ended_at,o.ojt_at)>5) THEN 1 else 0 end) as char)
				// as total_ojt_mayor "
				+ "from recruitment.training t "
				+ "left join recruitment.training_state ts on ts.id=t.training_state_id "
				+ "left join recruitment.ojt o on o.training_id=t.id and o.ojt_at>= '" + startOjt + "' "
				+ "left join recruitment.ojt_state os on os.id=o.ojt_state_id "
				+ "left join recruitment.interview i on i.id=t.interview_id "
				+ "left join recruitment.candidate c on c.id=i.candidate_id "
				+ "left join recruitment.area a on a.id=c.area_id "
				+ "left join recruitment.area ap on ap.id=a.area_id "
				+ "left join recruitment.recruiter r on r.id=t.recruiter_id "
				+ "left join security.user u on u.id=r.user_id " + "left join security.person p on p.id=u.person_id "
				+ "where t.training_at between '" + startTraining + "' and '" + endTraining + "' "
				+ "and t.recruiter_id=" + recruiterId + " and c.area_id=" + subAreaId + " ";

		Query q = em.createNativeQuery(query);

		Object[] object = (Object[]) q.getSingleResult();

		if (object.length > 0) {

			String totalStartTraining = (String) object[0];
			String totalEndTraining = (String) object[1];
			String totalStartOjt = (String) object[2];
			String totalEndOjt = (String) object[3];
			// String totalStartOjtLesser = (String) object[3];
			// System.out.println("totalStartOjtLesser"+totalStartOjtLesser);
			// String totalStartOjtGreater = (String) object[4];
			// System.out.println("totalStartOjtGreater"+totalStartOjtGreater);

			RequirementDto requirement = new RequirementDto();
			if (totalStartTraining != null) {
				requirement.setAmountStartTraining(Integer.parseInt(totalStartTraining));
			} else {
				requirement.setAmountStartTraining(0);
			}

			if (totalEndTraining != null) {
				requirement.setAmountEndTraining(Integer.parseInt(totalEndTraining));
			} else {
				requirement.setAmountEndTraining(0);
			}

			if (totalStartOjt != null) {
				requirement.setAmountStartOjt(Integer.parseInt(totalStartOjt));
			} else {
				requirement.setAmountStartOjt(0);
			}

			if (totalEndOjt != null) {
				requirement.setAmountEndOjt(Integer.parseInt(totalEndOjt));
			} else {
				requirement.setAmountEndOjt(0);
			}

			/*
			 * if (totalStartOjtLesser!=null) {
			 * requirement.setAmountStartOjtLesser(Integer.parseInt(
			 * totalStartOjtLesser)); }else{
			 * requirement.setAmountStartOjtLesser(0); }
			 * 
			 * if (totalStartOjtGreater!=null) {
			 * requirement.setAmountStartOjtGreater(Integer.parseInt(
			 * totalStartOjtGreater)); }else{
			 * requirement.setAmountStartOjtGreater(0); }
			 */

			return requirement;
		} else {
			return null;
		}

	}

	public void add(Requirement requirement) throws EaoException {
		try {

			Long newCorrelative = generateNewCorrelative(requirement.getPeriodYear(), requirement.getArea().getCode());
			String code = newCorrelative.toString();
			while (code.length() < 3) {
				code = "0" + code;
			}

			code = requirement.getPeriodYear().getId() + "-" + requirement.getPeriodMonth().getCode() + "-"
					+ requirement.getArea().getCode() + "-" + code;

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

	public Long generateNewCorrelative(YearEnum periodYear, String areaCode) throws EaoException {
		try {

			String query = "SELECT max(cast(substring(r.code,13) as signed)) "
					+ "FROM Requirement r left join r.area a " + "where r.periodYear=:periodYear and a.code=:areaCode";

			Query q = em.createQuery(query);
			q.setParameter("periodYear", periodYear);
			q.setParameter("areaCode", areaCode);

			Long maxCorrelative = (Long) q.getSingleResult();

			System.out.println("maxCorrelative:" + maxCorrelative);

			Long newCorrelative;

			if (maxCorrelative != null && maxCorrelative > 0) {
				newCorrelative = maxCorrelative + 1;
			} else {
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
