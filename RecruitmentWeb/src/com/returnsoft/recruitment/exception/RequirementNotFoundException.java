package com.returnsoft.recruitment.exception;

public class RequirementNotFoundException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7504430283220299572L;
	
	public RequirementNotFoundException() {
		super("No se encontró el requerimiento");
	}
	

}
