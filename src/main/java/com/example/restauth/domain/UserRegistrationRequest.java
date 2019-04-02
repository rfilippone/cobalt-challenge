package com.example.restauth.domain;


/**
 * DTO for user registration request
 */
public class UserRegistrationRequest {

	private String username;
	private String password;
	
	public UserRegistrationRequest(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return this.username;
	}

	public String getPassword() {
		return this.password;
	}
}
