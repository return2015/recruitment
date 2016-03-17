package com.returnsoft.recruitment.eao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.returnsoft.recruitment.entity.RequirementUser;
import com.returnsoft.recruitment.entity.RequirementUserPK;
import com.returnsoft.recruitment.exception.EaoException;

@LocalBean
@Stateless
public class RequirementUserEao {
	
	@PersistenceContext
	private EntityManager em;
	
	
	public void add(RequirementUser requirementUser) throws EaoException {
		try {
			
			em.persist(requirementUser);
			em.flush();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e.getMessage());
		}
	}
	
	public RequirementUser findById(Integer requirementId,Integer userId) throws EaoException{
		try {
			
			RequirementUserPK pk = new RequirementUserPK();
			pk.setRequirementId(requirementId);
			pk.setUserId(userId);
			
			RequirementUser ru = em.find(RequirementUser.class, pk);
			return ru;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e.getMessage());
		}
	}
	

}
