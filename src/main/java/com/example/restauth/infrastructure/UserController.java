package com.example.restauth.infrastructure;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.restauth.domain.PasswordResetRequest;
import com.example.restauth.domain.UserAlreadyExistsException;
import com.example.restauth.domain.UserAuthenticationRequest;
import com.example.restauth.domain.UserRegistrationRequest;
import com.example.restauth.domain.UserService;

/**
 * 
 * Main REST controller
 * 
 * Provide the following resource endpoints:
 * - POST to /users to create (register) a new user. The location header in case of HTTP CREATED response is the URI 
 *   of the created resource (access to the created reource, i.e. GET request to it, is not implemented)
 *   
 * - POST to /user/{username}/sessions to create and an authenticated "session" and abstract an authentication process as 
 *   a resource. The location header represents a unique identifier that can be used as an authenticated session identifier in
 *   the server side frontend part of an application
 * 
 * - PUT to the /user/{username}/password suberesource to modify the user password. The request body for this resource operation
 *   conatins both new and old password
 */
@RestController
public class UserController {
	@Autowired
	UserService service;

	/**
	 * User Registration endpoint 
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/users")
	public ResponseEntity<String> registerUser(@RequestBody UserRegistrationRequest request) throws NoSuchAlgorithmException, InvalidKeySpecException, UserAlreadyExistsException, URISyntaxException {
		service.registerUser(request);
		return ResponseEntity.created(new URI("/user/" + request.getUsername())).build();		
	}
	
	/**
	 * Authentication endpoint
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/user/{username}/sessions")
	public ResponseEntity<String> authenticateUser(@PathVariable(name="username") String username, @RequestBody UserAuthenticationRequest request) throws InvalidKeySpecException, NoSuchAlgorithmException, URISyntaxException {	
		String session = service.authenticateUser(username, request);
		
		if (session == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		
		return ResponseEntity.created(new URI("/user/" + username + "/session/" + session)).build();
	}
	
	/**
	 * Password reset endpoint
	 */
	@RequestMapping(method = RequestMethod.PUT, path = "/user/{username}/password")
	public Object resetPassword(@PathVariable(name="username") String username, @RequestBody PasswordResetRequest request) throws InvalidKeySpecException, NoSuchAlgorithmException {
		if (!service.resetPassword(username, request)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
