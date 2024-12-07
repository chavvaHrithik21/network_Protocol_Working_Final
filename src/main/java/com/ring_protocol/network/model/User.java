package com.ring_protocol.network.model;

import jakarta.persistence.*;

@Entity
@Table(name = "User")
public class User {

    @Id
    @Column(name = "Username", length = 30, nullable = false)
    private String username;

    @Column(name = "FirstName", length = 45)
    private String firstName;

    @Column(name = "LastName", length = 45)
    private String lastName;

    @Column(name = "Email", length = 45, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "UserType", nullable = false)
    private UserRole role;

    // Enum for UserRole, representing roles
    public enum UserRole {
        ADMIN,
        OPERATOR
    }

    // Default constructor required by JPA
    public User() {}

    // Constructor with parameters
    public User(String username, String firstName, String lastName, String email, UserRole role) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
    }

    // Getters and Setters

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                '}';
    }
}
