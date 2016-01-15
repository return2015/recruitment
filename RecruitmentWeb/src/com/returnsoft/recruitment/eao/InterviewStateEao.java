package com.returnsoft.recruitment.eao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.returnsoft.recruitment.entity.InterviewState;
import com.returnsoft.recruitment.exception.EaoException;


@Stateless
public class InterviewStateEao {

	@PersistenceContext
	private EntityManager em;

	public List<InterviewState> findAll() throws EaoException {
		try {
			String query = "SELECT si FROM InterviewState si";
			TypedQuery<InterviewState> q = em.createQuery(query,
					InterviewState.class);

			List<InterviewState> entities = q.getResultList();
			return entities;
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e.getMessage());
		}
	}
	
	public List<InterviewState> findIsPendingAndScheduled()  throws EaoException{
		try {
			String query = "SELECT si FROM InterviewState si where si.isPending=1 or si.isScheduled=1 ";
			TypedQuery<InterviewState> q = em.createQuery(query,
					InterviewState.class);
			List<InterviewState> entities = q.getResultList();
			return entities;
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e.getMessage());
		}
	}
	
	public InterviewState findById(Integer stateId) throws EaoException {
		try {
			InterviewState state = em.find(InterviewState.class, stateId);
			
			return state;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e.getMessage());
		}
	}

}
