package com.ring_protocol.network.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Login")
public class Login {

    @Id
    @Column(name = "Username", nullable = false, length = 30)
    private String username;

    @Column(name = "User_Password", nullable = false, length = 100) // Adjust length for hashed password
    private String userPassword;

    // Default constructor required by JPA
    public Login() {}

    // Constructor for easier instantiation
    public Login(String username, String userPassword) {
        this.username = username;
        this.userPassword = userPassword;
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    // Override equals and hashCode for correct behavior in collections
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Login login = (Login) o;

        return username != null ? username.equals(login.username) : login.username == null;
    }

    @Override
    public int hashCode() {
        return username != null ? username.hashCode() : 0;
    }

    // toString method for debugging
    @Override
    public String toString() {
        return "Login{" +
                "username='" + username + '\'' +
                ", userPassword='[PROTECTED]'" +
                '}';
    }
}
