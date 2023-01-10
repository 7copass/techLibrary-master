package com.techLead.library.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Entity not found")
public class ObjectNotFounException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ObjectNotFounException(String message) {
		super(message);
	}

}
