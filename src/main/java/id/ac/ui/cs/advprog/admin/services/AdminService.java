package id.ac.ui.cs.advprog.admin.services;

import id.ac.ui.cs.advprog.admin.models.User;
import id.ac.ui.cs.advprog.admin.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

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

    @Async
    public CompletableFuture<List<User>> findAllUser() {
        List<User> allUsers = adminRepository.findAll();
        return CompletableFuture.completedFuture(allUsers);
    }
}