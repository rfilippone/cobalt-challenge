package com.example.restauth.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DTO for password reset request
 */
public class PasswordResetRequest {
	
	private String oldPassword;
	private String newPassword;
	
	@JsonCreator
	public PasswordResetRequest(@JsonProperty("oldPassword") String oldPassword, @JsonProperty("newPassword") String newPassword) {
		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
	}

	public String getOldPassword() {
		return this.oldPassword;
	}
	
	public String getNewPassword() {
		return this.newPassword;
	}
}
