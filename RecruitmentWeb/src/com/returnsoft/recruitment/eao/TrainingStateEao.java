package com.returnsoft.recruitment.eao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.returnsoft.recruitment.entity.TrainingState;
import com.returnsoft.recruitment.exception.EaoException;

@Stateless
public class TrainingStateEao {

	@PersistenceContext
	private EntityManager em;

	public List<TrainingState> findAll() throws EaoException {
		try {
			String query = "SELECT ts FROM TrainingState ts";
			TypedQuery<TrainingState> q = em.createQuery(query,
					TrainingState.class);

			List<TrainingState> entities = q.getResultList();
			return entities;
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e.getMessage());
		}
	}
	
	public List<TrainingState> findIsPending()  throws EaoException{
		try {
			String query = "SELECT ts FROM TrainingState ts where ts.isPending=true";
			TypedQuery<TrainingState> q = em.createQuery(query,
					TrainingState.class);
			List<TrainingState> entities = q.getResultList();
			return entities;
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e.getMessage());
		}
	}
	
	public TrainingState findById(Integer stateId) throws EaoException {
		try {
			TrainingState state = em.find(TrainingState.class, stateId);
			
			return state;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e.getMessage());
		}
	}
	

}
