package com.returnsoft.security.service;

import java.util.List;

import javax.ejb.Remote;

import com.returnsoft.security.dto.UserDto;
import com.returnsoft.security.exception.SecurityExcepcion;

//@Remote
public interface UserInterface {
	
	public UserDto doLogin(String username, String password)
			throws SecurityExcepcion;

	
	
		
}
