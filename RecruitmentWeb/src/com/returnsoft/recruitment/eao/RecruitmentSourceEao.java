package com.returnsoft.recruitment.eao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.returnsoft.recruitment.entity.RecruimentSource;
import com.returnsoft.recruitment.exception.EaoException;


@Stateless
public class RecruitmentSourceEao {

	@PersistenceContext
	private EntityManager em;

	public List<RecruimentSource> findAll() throws EaoException {
		try {
			TypedQuery<RecruimentSource> q = em.createQuery(
					"SELECT rs FROM RecruimentSource rs",
					RecruimentSource.class);
			List<RecruimentSource> recruimentSources = q.getResultList();
			return recruimentSources;
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e.getMessage());
		}
	}
	
	public RecruimentSource findById(Integer recruitmentSourceId) throws EaoException {

		try {

			RecruimentSource recruimentSource = em.find(RecruimentSource.class, recruitmentSourceId);

			return recruimentSource;

		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw new EaoException(e.getMessage());
		}

	}
	

}
