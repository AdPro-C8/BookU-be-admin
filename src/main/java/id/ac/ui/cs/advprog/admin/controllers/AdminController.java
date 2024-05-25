package id.ac.ui.cs.advprog.admin.controllers;

import id.ac.ui.cs.advprog.admin.models.User;
import id.ac.ui.cs.advprog.admin.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/dashboard/user")
    public CompletableFuture<ResponseEntity<List<User>>> getAllUsers() {
        return adminService.findAllUser().thenApply(ResponseEntity::ok);
    }
}