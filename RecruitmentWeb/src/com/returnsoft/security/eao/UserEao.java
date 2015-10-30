package com.returnsoft.security.eao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.returnsoft.security.dto.UserDto;

import com.returnsoft.security.entity.User;
import com.returnsoft.security.exception.EaoExcepcion;

@LocalBean
@Stateless
public class UserEao implements Serializable{
	private static final long serialVersionUID = 1L;
	@PersistenceContext
	private EntityManager em;

	public User findByPersonId(Integer personId) throws EaoExcepcion {
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
			throw new EaoExcepcion(e.getMessage());
		}
	}
	
	public User findByDocumentNumber(String documentNumber) throws EaoExcepcion {
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
			throw new EaoExcepcion(e.getMessage());
		}
	}
	
	
	public UserDto findByUsername(String username) throws EaoExcepcion {
		try {
			System.out.println("en el eao: inicia findByUsername");
			/*TypedQuery<User> q = em.createQuery(
					"SELECT u FROM User u WHERE u.username = :username", User.class);
			q.setParameter("username", username);
			User user = q.getSingleResult();*/
			
			String query = "SELECT u.id,u.username,u.password,u.isActive "
					+ "FROM User u "
					+ "WHERE u.username = :username ";
			//System.out.println("username"+username);
			Query q = em.createQuery(query);
			q.setParameter("username", username);
			Object[] userObject = (Object[]) q.getSingleResult();
			

			Integer id = (Integer) userObject[0];
			username = (String) userObject[1];
			String password = (String) userObject[2];
			Boolean isActive = (Boolean) userObject[3];
			

			UserDto user = new UserDto();
			user.setId(id);
			user.setUsername(username);
			user.setPassword(password);
			user.setIsActive(isActive);
			
			
			
			System.out.println("en el eao: termina findByUsername");

			return user;

		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new EaoExcepcion(e.getMessage());
		}

	}



	
}
