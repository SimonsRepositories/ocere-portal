package com.ocere.portal.controller;

import com.ocere.portal.service.ClientService;
import com.ocere.portal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class ClientController {
    private ClientService clientService;
    private UserService userService;

    @Autowired
    public ClientController(ClientService clientService, UserService userService) {
        this.clientService = clientService;
        this.userService = userService;
    }

    @GetMapping("/clients")
    public String clientLanding(Model model, Principal principal) {
        model.addAttribute("clients", clientService.findAll());
        model.addAttribute("assigned", clientService.findAllByAssignedUser(userService.findByEmail(principal.getName())));
        model.addAttribute("created", clientService.findAllByAuthor(userService.findByEmail(principal.getName())));

        return "clients";
    }
}
