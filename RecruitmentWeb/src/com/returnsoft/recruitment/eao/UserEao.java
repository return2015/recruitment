package com.returnsoft.recruitment.eao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.returnsoft.recruitment.entity.User;
import com.returnsoft.recruitment.enumeration.UserTypeEnum;
import com.returnsoft.recruitment.exception.EaoException;

@Stateless
public class UserEao{
	
	@PersistenceContext
	private EntityManager em;

	public User findByPersonId(Integer personId) throws EaoException {
		try {
			TypedQuery<User> q = em
					.createQuery(
							"SELECT u FROM User u left join u.person p WHERE p.id = :personId",
							User.class);
			q.setParameter("personId", personId);
			User user = q.getSingleResult();

			return user;
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e.getMessage());
		}
	}
	
	public User findByDocumentNumber(String documentNumber) throws EaoException {
		try {
			TypedQuery<User> q = em
					.createQuery(
							"SELECT u FROM User u join u.person p WHERE p.documentNumber = :documentNumber",
							User.class);
			q.setParameter("documentNumber", documentNumber);
			User user = q.getSingleResult();

			return user;
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e.getMessage());
		}
	}
	
	
	public User findByUsername(String username) throws EaoException {
		try {
			TypedQuery<User> q = em.createQuery(
					"SELECT u FROM User u WHERE u.username = :username", User.class);
			q.setParameter("username", username);
			User user = q.getSingleResult();

			return user;

		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e.getMessage());
		}

	}
	
	public List<User> findByUserType(UserTypeEnum userType) throws EaoException {
		
		try {
			
			TypedQuery<User> q = em.createQuery(
					"SELECT u FROM User u WHERE u.userType = :userType", User.class);
			q.setParameter("userType", userType);
			
			List<User> users = q.getResultList();

			return users;
			
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoException(e.getMessage());
		}
		
	}



	
}
