package com.returnsoft.security.exception;

import java.io.Serializable;

public class ConversionExcepcion extends Exception implements Serializable {

	private static final long serialVersionUID = 1L;

	public ConversionExcepcion() {
		super();
	}

	public ConversionExcepcion(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public ConversionExcepcion(String arg0) {
		super(arg0);
	}

	public ConversionExcepcion(Throwable arg0) {
		super(arg0);
	}	
}
