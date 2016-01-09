package com.returnsoft.recruitment.eao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.returnsoft.recruitment.entity.RecruitmentSource;
import com.returnsoft.recruitment.exception.EaoException;


@Stateless
public class RecruitmentSourceEao {

	@PersistenceContext
	private EntityManager em;

	public List<RecruitmentSource> findAll() throws EaoException {
		try {
			TypedQuery<RecruitmentSource> q = em.createQuery(
					"SELECT rs FROM RecruitmentSource rs",
					RecruitmentSource.class);
			List<RecruitmentSource> recruimentSources = q.getResultList();
			return recruimentSources;
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e.getMessage());
		}
	}
	
	public RecruitmentSource findById(Integer recruitmentSourceId) throws EaoException {

		try {

			RecruitmentSource recruimentSource = em.find(RecruitmentSource.class, recruitmentSourceId);

			return recruimentSource;

		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw new EaoException(e.getMessage());
		}

	}
	

}
