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
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public String allTickets(Model model, Principal principal) {
        model.addAttribute("tickets", ticketService.findAll());
        model.addAttribute("open", ticketService.findAllByStatus(Status.OPEN));
        model.addAttribute("overdue", ticketService.findAllByTurnaround());
        model.addAttribute("assigned", ticketService.findAllByAssignedUser(userService.findByEmail(principal.getName())));
        model.addAttribute("assignedOpen", ticketService.findAllByAssignedUserAndStatus(userService.findByEmail(principal.getName()), Status.OPEN));
        model.addAttribute("assignedOverdue", ticketService.findAllByAssignedUserAndTurnaround(userService.findByEmail(principal.getName())));
        model.addAttribute("submitted", ticketService.findAllByAuthor(userService.findByEmail(principal.getName())));
        return "tickets-list";
    }

    @GetMapping("/create")
    public String loadCreateTicketView(Model model, @RequestParam(name="id", defaultValue = "-1") int id) {
        model.addAttribute("siteTitle", "New Ticket");
        model.addAttribute("action", "create");
        model.addAttribute("submitText", "Create");

        Ticket ticket;
        if (id == -1) {
            ticket = new Ticket();
        } else {
            ticket = this.ticketService.findTemplateById(id);
        }
        model.addAttribute("ticket", ticket);
        model.addAttribute("users", this.userService.findAll());
        model.addAttribute("groups", this.usergroupService.findAll());
        model.addAttribute("turnaroundTimes", this.turnaroundService.findAll());
        return "tickets_form";
    }

    @GetMapping("/templates/create")
    public String loadCreateTicketView(Model model) {
        model.addAttribute("siteTitle", "New Template");
        model.addAttribute("action", "templates/create");
        model.addAttribute("submitText", "Create");

        Ticket ticket = new Ticket();
        ticket.setTemplate(true);

        model.addAttribute("ticket", ticket);
        model.addAttribute("users", this.userService.findAll());
        model.addAttribute("groups", this.usergroupService.findAll());
        model.addAttribute("turnaroundTimes", this.turnaroundService.findAll());
        return "tickets_form";
    }

    @GetMapping("/templates")
    public String loadTemplateListView(Model model) {
        model.addAttribute("templates", this.ticketService.findAllTemplates());
        return "tickets_templates-list";
    }

    @GetMapping("/templates/{id}")
    public String loadTemplateView(Model model, @PathVariable int id) {
        model.addAttribute("template", this.ticketService.findTemplateById(id));
        return "tickets_templates-view";
    }

    @GetMapping("/templates/edit/{id}")
    public String loadTemplateEditView(Model model, @PathVariable int id) {
        model.addAttribute("siteTitle", "Edit Template");
        model.addAttribute("action", "save");
        model.addAttribute("submitText", "Save");

        model.addAttribute("ticket", this.ticketService.findTemplateById(id));
        model.addAttribute("users", this.userService.findAll());
        model.addAttribute("groups", this.usergroupService.findAll());
        model.addAttribute("turnaroundTimes", this.turnaroundService.findAll());
        return "tickets_form";
    }

    /*
        ACTIONS
     */

    @PostMapping("/create")
    public String createTicket(@ModelAttribute Ticket ticket, Principal principal) {
        fillTicketReferencesById(ticket);

        ticket.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        ticket.setAuthor(this.userService.findByEmail(principal.getName()));

        this.ticketService.saveTicket(ticket);
        return "redirect:/tickets";
    }

    @PostMapping("/templates/create")
    public String createTemplate(@ModelAttribute Ticket ticket, Principal principal) {
        fillTicketReferencesById(ticket);

        ticket.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        ticket.setAuthor(this.userService.findByEmail(principal.getName()));

        this.ticketService.saveTicket(ticket);
        return "redirect:/tickets/templates";
    }

    @PostMapping("/save")
    public String saveTicket(@ModelAttribute Ticket ticket) throws Exception{
        fillTicketReferencesById(ticket);

        this.ticketService.saveTicketById(ticket, ticket.getId());
        return "redirect:/tickets";
    }

    @PostMapping("/templates/save")
    public String saveTemplate(@ModelAttribute Ticket ticket) throws Exception{
        fillTicketReferencesById(ticket);

        this.ticketService.saveTicketById(ticket, ticket.getId());
        return "redirect:/tickets/templates";
    }

    @PostMapping("/delete/{id}")
    public String deleteTicket(@PathVariable int id) throws Exception {
        this.ticketService.removeTicketById(id);
        return "redirect:/tickets/";
    }

    @PostMapping("/templates/delete/{id}")
    public String deleteTemplate(@PathVariable int id) throws Exception {
        this.ticketService.removeTicketById(id);
        return "redirect:/tickets/templates";
    }

    /*
        PRIVATE
     */

    private void fillTicketReferencesById(Ticket ticket) {
        Optional<User> user = this.userService.getUserById(ticket.getAssignedUser().getId());
        user.ifPresentOrElse(ticket::setAssignedUser, () -> ticket.setAssignedUser(null));

        Optional<Usergroup> usergroup = this.usergroupService.findUsergroupById(ticket.getAssignedGroup().getId());
        usergroup.ifPresentOrElse(ticket::setAssignedGroup, () -> ticket.setAssignedGroup(null));

        Optional<Turnaround> turnaround = this.turnaroundService.findTurnaroundById(ticket.getTurnaround().getId());
        turnaround.ifPresentOrElse(ticket::setTurnaround, () -> ticket.setTurnaround(null));
    }
}
