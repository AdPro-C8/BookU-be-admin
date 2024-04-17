package id.ac.ui.cs.advprog.admin.services;

import id.ac.ui.cs.advprog.admin.models.User;
import id.ac.ui.cs.advprog.admin.repositories.AdminRepository;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class AdminService {


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

    public List<User> findAll(){
        Iterator<User> userIterator = adminRepository.findAll();
        List<User> allProduct = new ArrayList<>();
        userIterator.forEachRemaining(allProduct::add);
        return allProduct;
    }
}