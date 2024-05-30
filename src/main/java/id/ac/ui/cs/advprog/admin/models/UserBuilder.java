package id.ac.ui.cs.advprog.admin.models;

import id.ac.ui.cs.advprog.admin.enums.UserRole;

import java.lang.IllegalArgumentException;

public class UserBuilder {
    private String email;
    private String username;
    private String role;
    private String password;

    public UserBuilder username(String username) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (!username.matches("^[a-zA-Z0-9_]*$")) {
            throw new IllegalArgumentException("Username can only contain letters, numbers, and underscore");
        }
        this.username = username;
        return this;
    }

    public UserBuilder email(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            throw new IllegalArgumentException("Invalid email");
        }
        this.email = email;
        return this;
    }

    public UserBuilder password(String password) {
        if (password.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters long");
        }
        this.password = password;
        return this;
    }

    public UserBuilder role(String role) {
        if (role == null || role.isEmpty()) {
            throw new IllegalArgumentException("Role cannot be empty");
        }
        if (!UserRole.contains(role)) {
            throw new IllegalArgumentException("Invalid role");
        }
        this.role = role;
        return this;
    }

    public User build() {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setRole(role);
        user.setPassword(password);
        return user;
    }
}