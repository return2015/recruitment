package com.returnsoft.recruitment.eao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.returnsoft.recruitment.entity.OjtState;
import com.returnsoft.recruitment.exception.EaoException;

@Stateless
public class OjtStateEao {

	@PersistenceContext
	private EntityManager em;

	public List<OjtState> findAll() throws EaoException {
		try {
			String query = "SELECT os FROM OjtState os";
			TypedQuery<OjtState> q = em.createQuery(query, OjtState.class);

			List<OjtState> entities = q.getResultList();
			return entities;
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e.getMessage());
		}
	}
	
	public List<OjtState> findIsPending()  throws EaoException{
		try {
			String query = "SELECT os FROM OjtState os where os.isPending=true";
			TypedQuery<OjtState> q = em.createQuery(query,
					OjtState.class);
			List<OjtState> entities = q.getResultList();
			return entities;
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e.getMessage());
		}
	}

}
