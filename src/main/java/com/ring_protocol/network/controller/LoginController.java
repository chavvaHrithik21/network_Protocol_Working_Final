package com.ring_protocol.network.controller;

import com.ring_protocol.network.model.Login;
import com.ring_protocol.network.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    /**
     * Authenticates a user based on username and password.
     *
     * @param loginRequest a Login object containing username and password
     * @return a ResponseEntity indicating success or failure of authentication
     */
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Login loginRequest) {
        boolean isAuthenticated = loginService.authenticate(loginRequest.getUsername(), loginRequest.getUserPassword());
        if (isAuthenticated) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(401).body("Invalid username or password");
        }
    }

    /**
     * Registers a new user with a username and password.
     *
     * @param loginRequest a Login object containing username and password
     * @return a ResponseEntity containing the created Login object or an error message
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Login loginRequest) {
        try {
            Login registeredUser = loginService.register(loginRequest);
            return ResponseEntity.ok(registeredUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Username already exists");
        }
    }

    /**
     * Changes the password of an existing user.
     *
     * @param username    the username of the user whose password is to be changed
     * @param newPassword the new password to set
     * @return a ResponseEntity indicating success or failure of the password change
     */
    @PutMapping("/{username}/change-password")
    public ResponseEntity<String> changePassword(@PathVariable String username, @RequestParam String newPassword) {
        try {
            loginService.changePassword(username, newPassword);
            return ResponseEntity.ok("Password changed successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
