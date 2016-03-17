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
	
	public OjtState findIsPending()  throws EaoException{
		try {
			String query = "SELECT os FROM OjtState os where os.isPending=1";
			TypedQuery<OjtState> q = em.createQuery(query,
					OjtState.class);
			OjtState entity = q.getSingleResult();
			return entity;
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e.getMessage());
		}
	}
	
	public OjtState findById(Integer stateId) throws EaoException {
		try {
			OjtState state = em.find(OjtState.class, stateId);
			
			return state;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e.getMessage());
		}
	}

}
