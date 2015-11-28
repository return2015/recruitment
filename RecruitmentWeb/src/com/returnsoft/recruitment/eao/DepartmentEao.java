package com.returnsoft.recruitment.eao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.returnsoft.recruitment.entity.Department;
import com.returnsoft.recruitment.exception.EaoException;


@Stateless
public class DepartmentEao {

	@PersistenceContext
	private EntityManager em;

	public List<Department> findByCountry(Integer countryId)
			throws EaoException {
		try {
			String query = "SELECT d FROM Department d left join d.country c where c.id = :countryId";
			TypedQuery<Department> q = em.createQuery(query, Department.class);
			q.setParameter("countryId", countryId);
			List<Department> departments = q.getResultList();
			return departments;
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e.getMessage());
		}
	}

}
