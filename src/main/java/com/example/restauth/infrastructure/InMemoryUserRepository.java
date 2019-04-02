package com.example.restauth.infrastructure;

import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.example.restauth.domain.User;
import com.example.restauth.domain.UserAlreadyExistsException;
import com.example.restauth.domain.UserRepository;

/**
 * 
 * In memory implementation of the UserRepository interface
 *
 */
@Service
public class InMemoryUserRepository implements UserRepository {
	private HashMap<String, User> userStore;

	public InMemoryUserRepository() {
		this.userStore = new HashMap<String, User>();
	}

	
	/**
	 * Adds a user to the repository,  throws UserAlreadyExistsException if the user already exists
	 */
	@Override
	public void storeUser(User user) throws UserAlreadyExistsException {
		if (this.userStore.containsKey(user.getUsername())) {
			throw new UserAlreadyExistsException();
		}

		this.userStore.put(user.getUsername(), user);
	}

	/**
	 * Retrieves the user with the specified username
	 */
	@Override
	public User findByUsername(String userName) {
		return this.userStore.get(userName);
	}
}
