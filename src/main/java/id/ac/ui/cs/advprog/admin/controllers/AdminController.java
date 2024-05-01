package id.ac.ui.cs.advprog.admin.controllers;

import id.ac.ui.cs.advprog.admin.models.User;
import id.ac.ui.cs.advprog.admin.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;
    @GetMapping("/dashboardadmin")
    public String productListPage(Model model){
        List<User> allUsers = adminService.findAll();
        model.addAttribute("users", allUsers);
        return "dashboardadmin";
    }
}
