package com.returnsoft.recruitment.eao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.returnsoft.recruitment.entity.District;
import com.returnsoft.recruitment.exception.EaoException;


@Stateless
public class DistrictEao {

	@PersistenceContext
	private EntityManager em;

	public List<District> findByProvince(Integer provinceId)
			throws EaoException {
		try {
			String query = "SELECT d FROM District d left join d.province p where p.id = :provinceId";
			TypedQuery<District> q = em.createQuery(query, District.class);
			q.setParameter("provinceId", provinceId);
			List<District> districts = q.getResultList();
			return districts;
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e.getMessage());
		}
	}

}
