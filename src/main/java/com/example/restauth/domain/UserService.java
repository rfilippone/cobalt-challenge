package com.example.restauth.domain;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Domain Service to manage the authentication operations
 */
@Service
public class UserService {
	private UserRepository repository;
	
	@Autowired
	public UserService(UserRepository repository) {
		this.repository = repository;
	}
	
	/**
	 * Registers a user and stores it in the repository
	 */
	public void registerUser(UserRegistrationRequest request) throws NoSuchAlgorithmException, InvalidKeySpecException, UserAlreadyExistsException {
		User user = new User(request.getUsername(), request.getPassword());
		
		repository.storeUser(user);
	}
	
	/**
	 * AUthenticate a user against stored information
	 */
	public String authenticateUser(String username, UserAuthenticationRequest request) throws InvalidKeySpecException, NoSuchAlgorithmException {
		User user = repository.findByUsername(username);
		if (user == null || !user.doesPasswordMatch(request.getPassword())) {
			return null;
		}
		
		return UUID.randomUUID().toString();
	}

	/**
	 * Resets the user password
	 * Executes first an authentication on the fly with the old password
	 */
	public boolean resetPassword(String username, PasswordResetRequest request) throws InvalidKeySpecException, NoSuchAlgorithmException {
		User user = repository.findByUsername(username);
		if (user == null || !user.doesPasswordMatch(request.getOldPassword())) {
			return false;
		}
		
		user.resetPassword(request.getNewPassword());
		return true;
	}
}
