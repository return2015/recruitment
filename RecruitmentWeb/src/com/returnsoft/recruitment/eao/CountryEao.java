package com.returnsoft.recruitment.eao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.returnsoft.recruitment.entity.Country;
import com.returnsoft.recruitment.exception.EaoException;



@Stateless
public class CountryEao {

	@PersistenceContext
	private EntityManager em;

	public List<Country> findAll() throws EaoException {
		try {
			String query = "SELECT c FROM Country c";
			TypedQuery<Country> q = em.createQuery(query, Country.class);

			List<Country> countries = q.getResultList();
			return countries;
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e.getMessage());
		}
	}

}
