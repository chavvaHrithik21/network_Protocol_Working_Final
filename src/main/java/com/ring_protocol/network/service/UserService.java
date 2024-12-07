package com.ring_protocol.network.service;

import com.ring_protocol.network.model.User;
import com.ring_protocol.network.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Creates or registers a new user.
     *
     * @param user the User object containing user details
     * @return the saved User object
     * @throws IllegalArgumentException if the username already exists
     */
    public User registerUser(User user) {
        if (userRepository.existsById(user.getUsername())) {
            throw new IllegalArgumentException("Username already exists.");
        }
        return userRepository.save(user);
    }

    /**
     * Retrieves a user by their username.
     *
     * @param username the username of the user to retrieve
     * @return an Optional containing the User if found
     */
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findById(username);
    }

    /**
     * Retrieves all users in the system.
     *
     * @return list of all User objects
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Updates an existing user's information.
     *
     * @param username the username of the user to update
     * @param updatedUser the updated User object containing new details
     * @return the updated User object
     * @throws IllegalArgumentException if the user does not exist
     */
    public User updateUser(String username, User updatedUser) {
        if (!userRepository.existsById(username)) {
            throw new IllegalArgumentException("User with username " + username + " does not exist.");
        }
        updatedUser.setUsername(username); // Ensure the username remains the same
        return userRepository.save(updatedUser);
    }

    /**
     * Deletes a user by their username.
     *
     * @param username the username of the user to delete
     * @throws IllegalArgumentException if the user does not exist
     */
    public void deleteUser(String username) {
        if (!userRepository.existsById(username)) {
            throw new IllegalArgumentException("User with username " + username + " does not exist.");
        }
        userRepository.deleteById(username);
    }

    /**
     * Changes the role of a user.
     *
     * @param username the username of the user
     * @param role the new role to assign to the user
     * @return the updated User object with the new role
     * @throws IllegalArgumentException if the user does not exist
     */
    public User changeUserRole(String username, User.UserRole role) {
        Optional<User> userOptional = userRepository.findById(username);
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("User with username " + username + " does not exist.");
        }
        User user = userOptional.get();
        user.setRole(role);
        return userRepository.save(user);
    }
}
