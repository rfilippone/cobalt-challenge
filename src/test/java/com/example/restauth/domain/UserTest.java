package com.example.restauth.domain;

import static org.junit.Assert.*;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.junit.Test;

public class UserTest {

	@Test
	public void testGetUsername() throws NoSuchAlgorithmException, InvalidKeySpecException {
		User user = new User("user", "pwd");
		assertEquals("user", user.getUsername());
	}

	@Test
	public void testPasswordMatch() throws NoSuchAlgorithmException, InvalidKeySpecException {
		User user = new User("user", "pwd");
		assertTrue(user.doesPasswordMatch("pwd"));
	}
	
	@Test
	public void testPasswordDoesntMatch() throws NoSuchAlgorithmException, InvalidKeySpecException {
		User user = new User("user", "pwd");
		assertFalse(user.doesPasswordMatch("qwe"));
	}

}
