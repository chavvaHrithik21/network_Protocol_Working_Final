package com.ring_protocol.network.service;

import com.ring_protocol.network.model.Login;
import com.ring_protocol.network.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {

    private final LoginRepository loginRepository;

    @Autowired
    public LoginService(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    /**
     * Authenticates a user based on username and password.
     *
     * @param username the username of the user
     * @param password the password of the user
     * @return true if the credentials are valid, false otherwise
     */
    public boolean authenticate(String username, String password) {
        Optional<Login> loginOptional = loginRepository.findById(username);
        if (loginOptional.isPresent()) {
            Login login = loginOptional.get();
            return login.getUserPassword().equals(password); // Compare stored password with input
        }
        return false;
    }

    /**
     * Registers a new user by saving login details.
     *
     * @param login the Login object containing username and password
     * @return the saved Login object
     */
    public Login register(Login login) {
        if (loginRepository.existsById(login.getUsername())) {
            throw new IllegalArgumentException("Username already exists.");
        }
        return loginRepository.save(login);
    }

    /**
     * Changes the password of an existing user.
     *
     * @param username    the username of the user
     * @param newPassword the new password to be set
     * @return the updated Login object if user exists
     */
    public Login changePassword(String username, String newPassword) {
        Optional<Login> loginOptional = loginRepository.findById(username);
        if (loginOptional.isPresent()) {
            Login login = loginOptional.get();
            login.setUserPassword(newPassword);
            return loginRepository.save(login);
        } else {
            throw new IllegalArgumentException("User not found.");
        }
    }
}
