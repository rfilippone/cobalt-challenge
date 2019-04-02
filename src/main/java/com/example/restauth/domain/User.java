package com.example.restauth.domain;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * User domain model
 *
 */
public class User {
	
	private String username;
	private byte[] salt;
	private byte[] hash;
	
	public User(String username, String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
		this.username = username;
		this.salt = new byte[16];
			
		setPassword(password);
	}
	

	public String getUsername() {
		return username;
	}
	
	/**
	 * Checks if a password matches against the stored information
	 * The check is done by hashing the password with a salt and comparing against the stored hash
	 */
	public boolean doesPasswordMatch(String password) throws InvalidKeySpecException, NoSuchAlgorithmException {
		byte[] newHash = this.hashPassword(password, salt);
		if (newHash.length != this.hash.length) {
			return false;
		}
		
	    for (int i = 0; i < newHash.length; i++) {
			if (newHash[i] != this.hash[i]) {
				return false;
			}
		}
	    
	    return true;
	}
	
	/**
	 * Resets the password to a new value
	 */
	public void resetPassword(String password) throws InvalidKeySpecException, NoSuchAlgorithmException {
		setPassword(password);
	}
	
	/**
	 * Sets the password as a hashed and salted value 
	 */
	private void setPassword(String password) throws InvalidKeySpecException, NoSuchAlgorithmException  {
		//Generate salt for password hashing
		SecureRandom random = new SecureRandom();
		random.nextBytes(salt);
		
		this.hash = this.hashPassword(password, salt);
	}

	/**
	 * Hash a password by using PBKDF2 and the specified salt
	 */
	private byte[] hashPassword(String password, byte[] salt) throws InvalidKeySpecException, NoSuchAlgorithmException {
		KeySpec spec = new PBEKeySpec(password.toCharArray(), this.salt, 65536, 128);
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1"); 
		return factory.generateSecret(spec).getEncoded();
	}
}
