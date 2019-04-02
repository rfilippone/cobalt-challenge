package com.example.restauth.domain;

/**
 * User repository abstraction
 */
public interface UserRepository {
	/**
	 * Stores a user
	 */
	void storeUser(User user) throws UserAlreadyExistsException;
	
	/**
	 * Finds a user by username
	 */
	User findByUsername(String userName);
}