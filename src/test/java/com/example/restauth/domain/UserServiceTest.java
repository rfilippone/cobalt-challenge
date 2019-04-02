package com.example.restauth.domain;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.junit.Before;
import org.junit.Test;

import com.example.restauth.infrastructure.InMemoryUserRepository;

public class UserServiceTest {
	private static final String USERNAME = "user";
	private static final String PASSWORD = "pwd";
	private static final String WRONG_PASSWORD = "wrong";
    private static final String NEW_PASSWORD = "newpwd";
	
	private InMemoryUserRepository repository;
    private UserService service;
	
	
	@Before
	public void before() {
		repository = new InMemoryUserRepository();
		service = new UserService(repository);
	}
	
	@Test
	public void testRegisterUser() throws NoSuchAlgorithmException, InvalidKeySpecException, UserAlreadyExistsException {
		UserRegistrationRequest request = new UserRegistrationRequest(USERNAME, PASSWORD);
		service.registerUser(request);
	}

	@Test(expected = UserAlreadyExistsException.class)
	public void testRejectDuplicateRegisterUser() throws NoSuchAlgorithmException, InvalidKeySpecException, UserAlreadyExistsException {
		UserRegistrationRequest request = new UserRegistrationRequest(USERNAME, PASSWORD);
		service.registerUser(request);
		service.registerUser(request);
	}
	
	@Test
	public void testAuthenticateUser() throws NoSuchAlgorithmException, InvalidKeySpecException, UserAlreadyExistsException {
		UserRegistrationRequest request = new UserRegistrationRequest(USERNAME, PASSWORD);
		service.registerUser(request);
		
		UserAuthenticationRequest authRequest =  new UserAuthenticationRequest(PASSWORD);
		assertNotNull(service.authenticateUser(USERNAME, authRequest));
	}

	@Test
	public void testDoesntAuthenticateUnknownUser() throws NoSuchAlgorithmException, InvalidKeySpecException, UserAlreadyExistsException {		
		UserAuthenticationRequest authRequest =  new UserAuthenticationRequest(PASSWORD);
		assertNull(service.authenticateUser(USERNAME, authRequest));
	}
	
	@Test
	public void testDoesntAuthenticateWithWrongPassword() throws NoSuchAlgorithmException, InvalidKeySpecException, UserAlreadyExistsException {
		UserRegistrationRequest request = new UserRegistrationRequest(USERNAME, PASSWORD);
		service.registerUser(request);
		
		UserAuthenticationRequest authRequest =  new UserAuthenticationRequest(WRONG_PASSWORD);
		assertNull(service.authenticateUser(USERNAME, authRequest));
	}
	
	@Test
	public void testChangePassword() throws NoSuchAlgorithmException, InvalidKeySpecException, UserAlreadyExistsException {
		UserRegistrationRequest request = new UserRegistrationRequest(USERNAME, PASSWORD);
		service.registerUser(request);
		
		PasswordResetRequest resetRequest = new PasswordResetRequest(PASSWORD, NEW_PASSWORD);
		assertTrue(service.resetPassword(USERNAME, resetRequest));
	}

	@Test
	public void testAuthenticateWithNewPassword() throws NoSuchAlgorithmException, InvalidKeySpecException, UserAlreadyExistsException {
		UserRegistrationRequest request = new UserRegistrationRequest(USERNAME, PASSWORD);
		service.registerUser(request);
		PasswordResetRequest resetRequest = new PasswordResetRequest(PASSWORD, NEW_PASSWORD);
		service.resetPassword(USERNAME, resetRequest);
		
		UserAuthenticationRequest authRequest =  new UserAuthenticationRequest(NEW_PASSWORD);
		assertNotNull(service.authenticateUser(USERNAME, authRequest));
	}
	
	@Test
	public void testCantChangePassword() throws NoSuchAlgorithmException, InvalidKeySpecException, UserAlreadyExistsException {
		UserRegistrationRequest request = new UserRegistrationRequest(USERNAME, PASSWORD);
		service.registerUser(request);
		
		PasswordResetRequest resetRequest = new PasswordResetRequest(WRONG_PASSWORD, NEW_PASSWORD);
		assertFalse(service.resetPassword(USERNAME, resetRequest));
	}
}
