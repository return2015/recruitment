package com.returnsoft.recruitment.eao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.returnsoft.recruitment.entity.Province;
import com.returnsoft.recruitment.exception.EaoException;


@Stateless
public class ProvinceEao {

	@PersistenceContext
	private EntityManager em;

	public List<Province> findByDepartment(Integer departmentId)
			throws EaoException {
		try {
			String query = "SELECT p FROM Province p left join p.department d where d.id = :departmentId";
			TypedQuery<Province> q = em.createQuery(query, Province.class);
			q.setParameter("departmentId", departmentId);
			List<Province> provinces = q.getResultList();
			return provinces;
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e.getMessage());
		}
	}

}
