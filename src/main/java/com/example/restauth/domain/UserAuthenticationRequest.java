package com.example.restauth.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DTO for user authentication request
 */
public class UserAuthenticationRequest {
	
	private String password;
	
	@JsonCreator
	public UserAuthenticationRequest(@JsonProperty("password") String password) {
		this.password = password;
	}

	public String getPassword() {
		return this.password;
	}
}
