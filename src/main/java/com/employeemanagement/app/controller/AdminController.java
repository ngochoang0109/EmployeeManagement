package com.employeemanagement.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
    @GetMapping("/signin")
    public String loginPage(Model model) {
        return "login";
    }

    @GetMapping("/")
    public String loginPageForward(Model model) {
        return "redirect:/signin";
    }

    @GetMapping("/admin/employee")
    public String Employee(Model model) {
        return "admin/EmployeeManagement";
    }

    @GetMapping("/admin/audit")
    public String Audit(Model model) {
        return "admin/Audit";
    }
}
