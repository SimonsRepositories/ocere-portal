package com.ocere.portal.controller;

import com.ocere.portal.model.Ticket;
import com.ocere.portal.service.TicketService;
import com.ocere.portal.service.UserService;
import com.ocere.portal.service.UsergroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TicketController {

    private UserService userService;
    private UsergroupService usergroupService;
    private TicketService ticketService;

    @Autowired
    public TicketController(UserService userService, UsergroupService usergroupService, TicketService ticketService) {
        this.userService = userService;
        this.usergroupService = usergroupService;
        this.ticketService = ticketService;
    }

    @GetMapping("/createTicket")
    public String loadCreateTicketView(Model model) {
        model.addAttribute("ticket", new Ticket());
        model.addAttribute("users", this.userService.findAll());
        model.addAttribute("groups", this.usergroupService.findAll());
        return "newTicket";
    }

    @PostMapping("/createTicket")
    public String createTicket(@ModelAttribute Ticket ticket) {
        ticket.setAssignedUser(this.userService.getUserById(ticket.getAssignedUser().getId()));
        ticket.setAssignedGroup(this.usergroupService.findUsergroupById(ticket.getAssignedGroup().getId()).get());
        this.ticketService.saveTicket(ticket);
        return "redirect:/tickets";
    }

    @RequestMapping("/tickets")
    public ModelAndView ticketOverview() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("ticket"); //resources/templates/ticket.html
        return modelAndView;
    }
}
