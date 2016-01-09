package com.returnsoft.recruitment.exception;

public class UserAlreadyExistException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1035516858933770298L;

	public UserAlreadyExistException() {
		super("El usuario ya existe.");
	}
	
	

}
