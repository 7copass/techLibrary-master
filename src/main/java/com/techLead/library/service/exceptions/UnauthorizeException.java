package com.techLead.library.service.exceptions;

public class UnauthorizeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UnauthorizeException(String message) {
		super(message);
	}

}
