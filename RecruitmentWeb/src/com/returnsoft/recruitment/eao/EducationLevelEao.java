package com.returnsoft.recruitment.eao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.returnsoft.recruitment.entity.EducationLevel;
import com.returnsoft.recruitment.exception.EaoException;



@Stateless
public class EducationLevelEao {

	@PersistenceContext
	private EntityManager em;

	public List<EducationLevel> findAll() throws EaoException {
		try {
			String query = "SELECT el FROM EducationLevel el";
			TypedQuery<EducationLevel> q = em.createQuery(query,
					EducationLevel.class);
			List<EducationLevel> entities = q.getResultList();
			return entities;
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e.getMessage());
		}
	}
	
	

}
