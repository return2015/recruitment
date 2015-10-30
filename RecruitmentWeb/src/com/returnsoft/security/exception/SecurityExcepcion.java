package com.returnsoft.security.exception;

public class SecurityExcepcion extends Exception {

	private static final long serialVersionUID = 1L;

	public SecurityExcepcion() {
		super();
	}

	public SecurityExcepcion(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public SecurityExcepcion(String arg0) {
		super(arg0);
	}

	public SecurityExcepcion(Throwable arg0) {
		super(arg0);
	}	
}
