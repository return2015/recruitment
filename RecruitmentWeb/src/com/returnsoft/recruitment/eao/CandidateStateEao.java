package com.returnsoft.recruitment.eao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.returnsoft.recruitment.entity.CandidateState;
import com.returnsoft.recruitment.exception.EaoException;


@Stateless
public class CandidateStateEao {

	@PersistenceContext
	private EntityManager em;

	public List<CandidateState> findAll() throws EaoException {
		try {
			String query = "SELECT cs FROM CandidateState cs";
			TypedQuery<CandidateState> q = em.createQuery(query,
					CandidateState.class);
			List<CandidateState> entities = q.getResultList();
			return entities;
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e.getMessage());
		}
	}
	
	public List<CandidateState> findEnablesList() throws EaoException {
		try {
			String query = "SELECT cs FROM CandidateState cs where cs.state=1";
			TypedQuery<CandidateState> q = em.createQuery(query,
					CandidateState.class);
			List<CandidateState> entities = q.getResultList();
			return entities;
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e.getMessage());
		}
	}
	
	public CandidateState findById(Integer stateId) throws EaoException {
		try {
			CandidateState state = em.find(CandidateState.class, stateId);
			
			return state;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e.getMessage());
		}
	}
	
	

}
