package com.returnsoft.security.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
//import javax.jws.WebService;


import com.returnsoft.security.dto.UserDto;

import com.returnsoft.security.eao.UserEao;

import com.returnsoft.security.entity.User;
import com.returnsoft.security.exception.ConversionExcepcion;
import com.returnsoft.security.exception.EaoExcepcion;
import com.returnsoft.security.exception.SecurityExcepcion;


/**
 * Session Bean implementation class UserBean
 */
@Stateless
public class UserBean implements UserInterface {

	
	@EJB
	private UserEao userEao;

	/*@EJB
	private Conversion conv;*/

	@Override
	public UserDto doLogin(String username, String password) throws SecurityExcepcion {
		try {
			// OBTIENE EL USER
			
			UserDto user = userEao.findByUsername(username);
			
			if (user != null && user.getId() > 0) {

				if (user.getPassword().equals(password)) {
					if (user.getIsActive()) {
						return user;
					} else {
						throw new SecurityExcepcion(
								"El usuario se encuentra inactivo.");
					}
				} else {
					throw new SecurityExcepcion(
							"El password del usuario es incorrecto.");
				}
			} else {
				throw new SecurityExcepcion(
						"El usuario " + username + " no existe.");
			}

		} catch (EaoExcepcion e) {
			throw new SecurityExcepcion(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new SecurityExcepcion(e.getMessage());
		}
	}

	
}
