package com.ocere.portal.controller;

import com.ocere.portal.model.Client;
import com.ocere.portal.service.ClientService;
import com.ocere.portal.service.JobService;
import com.ocere.portal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/client")
public class ClientViewController {
    private ClientService clientService;
    private UserService userService;
    private JobService jobService;

    @Autowired
    public ClientViewController(ClientService clientService, UserService userService, JobService jobService) {
        this.clientService = clientService;
        this.userService = userService;
        this.jobService = jobService;
    }

    @GetMapping
    public String clientIndex(Model model, Principal principal) {
        Client client;
        try {
            client = clientService.findClientByContact(userService.findByEmail(principal.getName()));
        } catch (Exception e) {
            return "access-denied";
        }
        model.addAttribute("client", client);
        model.addAttribute("client", jobService.findAllJobsByClientId(client.getId()));

        return "client";
    }
}
