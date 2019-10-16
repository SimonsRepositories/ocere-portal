package com.ocere.portal.controller;

import com.ocere.portal.model.Ticket;
import com.ocere.portal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FileFEController {

    private UserService userService;

    @Autowired
    public FileFEController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/filetest")
    public String fileTest() {
        return "filetest";
    }

    @GetMapping("/createTicket")
    public String newTicket(Model model) {
        model.addAttribute("ticket", new Ticket());
        model.addAttribute("users", this.userService.findAll());
        return "newTicket";
    }
}
