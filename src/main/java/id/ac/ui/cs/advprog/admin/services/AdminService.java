package id.ac.ui.cs.advprog.admin.services;

import id.ac.ui.cs.advprog.admin.models.User;

public class AdminService {
    public User createUser(String userType) {
        if (userType == null) {
            return null;
        }
        if (userType.equalsIgnoreCase("ADMIN")) {
            return new User();
        } else if (userType.equalsIgnoreCase("CUSTOMER")) {
            return new User();
        }
        return null;
    }
}