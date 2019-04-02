package com.example.restauth.domain;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(code = HttpStatus.CONFLICT, reason = UserAlreadyExistsException.MESSAGE)
public class UserAlreadyExistsException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3231792460360337874L;
	public static final String MESSAGE = "User is already registered";
}
