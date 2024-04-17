package id.ac.ui.cs.advprog.bookubeadmin.services;

import id.ac.ui.cs.advprog.bookubeadmin.models.User;
import id.ac.ui.cs.advprog.bookubeadmin.repositories.AdminRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;

    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public List<User> findAllUsers() {
        return adminRepository.findAll();
    }
}
