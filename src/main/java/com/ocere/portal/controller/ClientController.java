package com.ocere.portal.controller;

import com.ocere.portal.service.ClientService;
import com.ocere.portal.service.JobService;
import com.ocere.portal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class ClientController {
    private ClientService clientService;
    private UserService userService;
    private JobService jobService;

    @Autowired
    public ClientController(ClientService clientService, UserService userService, JobService jobService) {
        this.clientService = clientService;
        this.userService = userService;
        this.jobService = jobService;
    }

    @GetMapping("/clients")
    public String clientLanding(Model model, Principal principal) {
        model.addAttribute("clients", clientService.findAll());
        model.addAttribute("assigned", clientService.findAllByAssignedUser(userService.findByEmail(principal.getName())));
        model.addAttribute("created", clientService.findAllByAuthor(userService.findByEmail(principal.getName())));

        return "clients";
    }

    @GetMapping("clients/{id}")
    public String loadTicketView(Model model, @PathVariable int id) {
        model.addAttribute("client", this.clientService.getClientById(id));
        model.addAttribute("jobs", this.jobService.findAllJobsByClientId(id));
        return "clients-view";
    }

    @PostMapping("clients/delete/{id}")
    public String deleteClient(@PathVariable int id) {
        clientService.removeClientById(id);
        return "clients";
    }
}
