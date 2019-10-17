package com.ocere.portal.controller;

import com.ocere.portal.enums.Status;
import com.ocere.portal.model.Ticket;
import com.ocere.portal.service.Impl.TicketServiceImpl;
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

import java.security.Principal;
import java.sql.Timestamp;

@Controller
@RequestMapping("/tickets")
public class TicketController {

    private UserService userService;
    private UsergroupService usergroupService;
    private TicketService ticketService;
    private TicketServiceImpl ticketServiceImpl;

    @Autowired
    public TicketController(UserService userService, UsergroupService usergroupService, TicketService ticketService, TicketServiceImpl ticketServiceImpl) {
        this.userService = userService;
        this.usergroupService = usergroupService;
        this.ticketService = ticketService;
        this.ticketServiceImpl = ticketServiceImpl;
    }

    @GetMapping("/create")
    public String loadCreateTicketView(Model model) {
        model.addAttribute("ticket", new Ticket());
        model.addAttribute("users", this.userService.findAll());
        model.addAttribute("groups", this.usergroupService.findAll());
        return "add-ticket";
    }

    @PostMapping("/create")
    public String createTicket(@ModelAttribute Ticket ticket, Principal principal) {
        ticket.setAssignedUser(this.userService.getUserById(ticket.getAssignedUser().getId()));
        ticket.setAssignedGroup(this.usergroupService.findUsergroupById(ticket.getAssignedGroup().getId()).get());
        ticket.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        ticket.setAuthor(this.userService.findByEmail(principal.getName()));
        this.ticketService.saveTicket(ticket);
        return "redirect:/tickets/dashboard";
    }

    @GetMapping("/dashboard")
    public String allTickets(Model model, Principal principal) {
        Ticket ticket = new Ticket();
        model.addAttribute("ticket", ticket);
        model.addAttribute("users", userService.findAll());
        model.addAttribute("groups", usergroupService.findAll());
        model.addAttribute("tickets", ticketService.findAll());
        model.addAttribute("open", ticketService.findAllByStatus(Status.OPEN));
        model.addAttribute("overdue", ticketServiceImpl.findAllByTurnaround());
        model.addAttribute("assigned", ticketService.findAllByAssignedUser(userService.findByEmail(principal.getName())));
        model.addAttribute("assignedOpen", ticketService.findAllByAssignedUserAndStatus(userService.findByEmail(principal.getName()), Status.OPEN));
        model.addAttribute("assignedOverdue", ticketServiceImpl.findAllByAssignedUserAndTurnaround(userService.findByEmail(principal.getName())));
        return "ticket";
    }
}
