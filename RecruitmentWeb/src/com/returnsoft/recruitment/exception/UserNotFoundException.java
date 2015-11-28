package com.returnsoft.recruitment.exception;

import java.io.Serializable;

public class UserNotFoundException extends Exception implements Serializable {
	
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -6793254603900438499L;

	public UserNotFoundException() {
		super("No se encontr� el usuario");
	}


}
