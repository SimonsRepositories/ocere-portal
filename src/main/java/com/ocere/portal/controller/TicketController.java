package com.ocere.portal.controller;

import com.ocere.portal.enums.Status;
import com.ocere.portal.model.Ticket;
import com.ocere.portal.model.Turnaround;
import com.ocere.portal.model.User;
import com.ocere.portal.model.Usergroup;
import com.ocere.portal.service.TicketService;
import com.ocere.portal.service.TurnaroundService;
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
import java.util.Optional;

@Controller
@RequestMapping("/tickets")
public class TicketController {

    private UserService userService;
    private UsergroupService usergroupService;
    private TicketService ticketService;
    private TurnaroundService turnaroundService;

    @Autowired
    public TicketController(UserService userService, UsergroupService usergroupService, TicketService ticketService, TurnaroundService turnaroundService) {
        this.userService = userService;
        this.usergroupService = usergroupService;
        this.ticketService = ticketService;
        this.turnaroundService = turnaroundService;
    }

    @GetMapping("/create")
    public String loadCreateTicketView(Model model) {
        model.addAttribute("ticket", new Ticket());
        model.addAttribute("users", this.userService.findAll());
        model.addAttribute("groups", this.usergroupService.findAll());
        model.addAttribute("turnaroundTimes", this.turnaroundService.findAll());
        return "add-ticket";
    }

    @PostMapping("/create")
    public String createTicket(@ModelAttribute Ticket ticket, Principal principal) {
        Optional<User> user = this.userService.getUserById(ticket.getAssignedUser().getId());
        user.ifPresentOrElse(ticket::setAssignedUser, () -> ticket.setAssignedUser(null));

        Optional<Usergroup> usergroup = this.usergroupService.findUsergroupById(ticket.getAssignedGroup().getId());
        usergroup.ifPresentOrElse(ticket::setAssignedGroup, () -> ticket.setAssignedGroup(null));

        Optional<Turnaround> turnaround = this.turnaroundService.findTurnaroundById(ticket.getTurnaround().getId());
        turnaround.ifPresentOrElse(ticket::setTurnaround, () -> ticket.setTurnaround(null));

        ticket.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        ticket.setAuthor(this.userService.findByEmail(principal.getName()));

        this.ticketService.saveTicket(ticket);
        return "redirect:/tickets/dashboard";
    }

    @GetMapping("/dashboard")
    public String allTickets(Model model, Principal principal) {
        model.addAttribute("tickets", ticketService.findAll());
        model.addAttribute("open", ticketService.findAllByStatus(Status.OPEN));
        model.addAttribute("overdue", ticketService.findAllByTurnaround());
        model.addAttribute("assigned", ticketService.findAllByAssignedUser(userService.findByEmail(principal.getName())));
        model.addAttribute("assignedOpen", ticketService.findAllByAssignedUserAndStatus(userService.findByEmail(principal.getName()), Status.OPEN));
        model.addAttribute("assignedOverdue", ticketService.findAllByAssignedUserAndTurnaround(userService.findByEmail(principal.getName())));
        model.addAttribute("submitted", ticketService.findAllByAuthor(userService.findByEmail(principal.getName())));
        return "ticket";
    }
}
