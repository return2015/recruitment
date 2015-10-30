package com.returnsoft.resource.exception;

import java.io.Serializable;

public class EaoExcepcion extends Exception implements Serializable {

	private static final long serialVersionUID = 1L;

	public EaoExcepcion() {
		super();
	}

	public EaoExcepcion(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public EaoExcepcion(String arg0) {
		super(arg0);
	}

	public EaoExcepcion(Throwable arg0) {
		super(arg0);
	}	
}
