package com.returnsoft.recruitment.exception;

import java.io.Serializable;

public class SessionTypeInvalidException extends Exception implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1912783374641106987L;
	
	public SessionTypeInvalidException() {
		super("Tipo de sesi�n inv�lida");
	}

	

	

}
