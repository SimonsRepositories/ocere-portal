package com.ocere.portal.controller;

import com.ocere.portal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("profile")
public class ProfileController {
    private UserService userService;

    @Autowired
    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String profileOverview(Model model, Principal principal) {
        model.addAttribute("user", this.userService.findByEmail(principal.getName()));

        return "profile-view";
    }

    @GetMapping("/change-password")
    public String loadChangePassword(Model model, Principal principal) {
        model.addAttribute("user", userService.findByEmail(principal.getName()));

        return "changePassword";
    }
}
