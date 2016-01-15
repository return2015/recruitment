package com.returnsoft.recruitment.eao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.returnsoft.recruitment.entity.Area;
import com.returnsoft.recruitment.exception.EaoException;


@Stateless
public class AreaEao {

	@PersistenceContext
	private EntityManager em;
	
	public void add(Area area) throws EaoException {
		try {
			
			Long newCorrelative = generateNewCorrelative();
			String code = newCorrelative.toString(); 
			while (code.length()<3) {
				code="0"+code;
			}
			
			area.setCode(code);
			
			em.persist(area);
			em.flush();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e.getMessage());
		}
	}
	
	
	public Long generateNewCorrelative() throws EaoException{
		try {
			
			String query = "SELECT max(cast(a.code as signed)) "
					+ "FROM Area a ";
			
			Query q = em.createQuery(query);

			Long maxCorrelative = (Long)q.getSingleResult();
			
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
	
	public Area edit(Area area) throws EaoException {
		try {
			
			area = em.merge(area);
			em.flush();
			
			return area;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e.getMessage());
		}

	}
	
	
	public Area findById(Integer areaId) throws EaoException {
		try {
			Area area = em.find(Area.class, areaId);
			
			return area;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e.getMessage());
		}
	}
	
	public List<Area> findAreasParentActive() throws EaoException {
		try {
			
			String query = "SELECT a FROM Area a left join a.area ac where a.isActive=1 and ac.id is null";
			TypedQuery<Area> q = em.createQuery(query, Area.class);

			List<Area> areas = q.getResultList();

			return areas;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e.getMessage());
		}
	}
	

	public List<Area> findAreasParent() throws EaoException {
		try {
			
			String query = "SELECT a FROM Area a left join a.area ac where ac.id is null";
			TypedQuery<Area> q = em.createQuery(query, Area.class);

			List<Area> areas = q.getResultList();

			return areas;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e.getMessage());
		}
	}
	
	
	public List<Area> findAreasChildActive(Integer areaId) throws EaoException {
		try {
			
			String query = "SELECT a FROM Area a left join a.area ac where a.isActive=1 and ac.id = :areaId";
			TypedQuery<Area> q = em.createQuery(query, Area.class);
			q.setParameter("areaId", areaId);
			List<Area> areas = q.getResultList();
			
			return areas;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e.getMessage());
		}
	}

	public List<Area> findAreasChild() throws EaoException {
		try {
			
			String query = "SELECT a FROM Area a left join a.area ac where ac.id is not null";
			TypedQuery<Area> q = em.createQuery(query, Area.class);
			List<Area> areas = q.getResultList();
			
			return areas;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e.getMessage());
		}
	}

	public List<Area> findAreasByRecruiter(Integer recruiterId) throws EaoException {
		try {
			
			String query =
					
					"SELECT a FROM Area a "
					+"left join a.recruiters r "
					+"where r.id=:recruiterId and a.area is null and a.isActive=1";
			
			TypedQuery<Area> q = em.createQuery(query, Area.class);
			q.setParameter("recruiterId", recruiterId);
			List<Area> areas = q.getResultList();
			return areas;
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e.getMessage());
		}
	}
	
	
	public List<Area> findSubAreasByRecruiter(Integer areaParentId, Integer recruiterId) throws EaoException {
		try {
			
			String query =
					"SELECT a FROM Area a "
					+"left join a.recruiters r "
					+"left join a.area sa "
					+"where r.id=:recruiterId and sa.id=:areaId and a.isActive=1";
			
			TypedQuery<Area> q = em.createQuery(query, Area.class);
			q.setParameter("recruiterId", recruiterId);
			q.setParameter("areaId", areaParentId);
			List<Area> subAreas = q.getResultList();

			/*String query =
					"select sa.id,sa.name "
					+ "from recruitment.recruiter_subarea rsa "
					+"left join recruitment.area sa on sa.id=rsa.area_id "
					+"where rsa.recruiter_id="+recruiterId+" and sa.area_id="+areaParentId;
			Query q = em.createNativeQuery(query);
			List<Object[]> objectList = (List<Object[]>) q
					.getResultList();
			List<Area> subAreas = new ArrayList<Area>();
			if (objectList.size() > 0) {
				for (Object[] object : objectList) {
					Integer subAreaId = (Integer) object[0];
					String subAreaName = (String) object[1];
					if (subAreaId!=null &&subAreaId>0) {
					Area subArea = new Area();
					subArea.setId(subAreaId);
					subArea.setName(subAreaName);
					subAreas.add(subArea);
					}
				}
			}*/
			
			return subAreas;
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e.getMessage());
		}
	}
	

}
