package com.ocere.portal.controller;

import com.ocere.portal.enums.Status;
import com.ocere.portal.model.Ticket;
import com.ocere.portal.model.Turnaround;
import com.ocere.portal.model.User;
import com.ocere.portal.model.Usergroup;
import com.ocere.portal.service.*;
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
    private TemplateService templateService;
    private TurnaroundService turnaroundService;

    @Autowired
    public TicketController(UserService userService,
                            UsergroupService usergroupService,
                            TicketService ticketService,
                            TemplateService templateService,
                            TurnaroundService turnaroundService) {
        this.userService = userService;
        this.usergroupService = usergroupService;
        this.ticketService = ticketService;
        this.templateService = templateService;
        this.turnaroundService = turnaroundService;
    }

    @GetMapping
    public String allTicketListView(Model model, Principal principal) {
        model.addAttribute("tickets", ticketService.findAllTickets());
        model.addAttribute("open", ticketService.findAllTicketsByStatus(Status.OPEN));
        model.addAttribute("overdue", ticketService.findAllTicketsByTurnaroundInFuture());
        model.addAttribute("assigned", ticketService.findAllTicketsByAssignedUser(userService.findByEmail(principal.getName())));
        model.addAttribute("assignedOpen", ticketService.findAllTicketsByAssignedUserAndStatus(userService.findByEmail(principal.getName()), Status.OPEN));
        model.addAttribute("assignedOverdue", ticketService.findAllTicketsByAssignedUserAndTurnaroundInFuture(userService.findByEmail(principal.getName())));
        model.addAttribute("submitted", ticketService.findAllTicketsByAuthor(userService.findByEmail(principal.getName())));
        return "tickets-list";
    }

    @GetMapping("{id}")
    public String loadTicketView(Model model, @PathVariable int id) {
        model.addAttribute("ticket", this.ticketService.findTicketById(id));
        return "tickets-view";
    }

    @GetMapping("/create")
    public String loadCreateTicketView(Model model, @RequestParam(name = "id", defaultValue = "-1") int id) {
        model.addAttribute("siteTitle", "New Ticket");
        model.addAttribute("action", "create");
        model.addAttribute("submitText", "Create");
        model.addAttribute("cancelPage", "/tickets");

        Ticket ticket;
        if (id == -1) {
            ticket = new Ticket();
        } else {
            ticket = this.templateService.findTemplateById(id);
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
        model.addAttribute("cancelPage", "/tickets/templates");

        model.addAttribute("ticket", new Ticket());
        model.addAttribute("users", this.userService.findAll());
        model.addAttribute("groups", this.usergroupService.findAll());
        model.addAttribute("turnaroundTimes", this.turnaroundService.findAll());
        return "tickets_form";
    }

    @GetMapping("/templates")
    public String loadTemplateListView(Model model) {
        model.addAttribute("templates", this.templateService.findAllTemplates());
        return "tickets_templates-list";
    }

    @GetMapping("/templates/{id}")
    public String loadTemplateView(Model model, @PathVariable int id) {
        model.addAttribute("template", this.templateService.findTemplateById(id));
        return "tickets_templates-view";
    }

    @GetMapping("/templates/edit/{id}")
    public String loadTemplateEditView(Model model, @PathVariable int id) {
        model.addAttribute("siteTitle", "Edit Template");
        model.addAttribute("action", "templates/save/" + id);
        model.addAttribute("submitText", "Save");
        model.addAttribute("cancelPage", "/tickets/templates");

        model.addAttribute("ticket", this.templateService.findTemplateById(id));
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

        ticket.setTemplate(true);
        ticket.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        ticket.setAuthor(this.userService.findByEmail(principal.getName()));

        this.ticketService.saveTicket(ticket);
        return "redirect:/tickets/templates";
    }

    @PostMapping("/save/{id}")
    public String saveTicket(@PathVariable int id, @ModelAttribute Ticket ticket) throws Exception {
        fillTicketReferencesById(ticket);

        this.ticketService.updateTicket(ticket, id);
        return "redirect:/tickets";
    }

    @PostMapping("/templates/save/{id}")
    public String saveTemplate(@PathVariable int id, @ModelAttribute Ticket ticket) throws Exception {
        fillTicketReferencesById(ticket);

        this.ticketService.updateTicket(ticket, id);
        return "redirect:/tickets/templates";
    }

    @PostMapping("/delete/{id}")
    public String deleteTicket(@PathVariable int id) {
        this.ticketService.removeTicketById(id);
        return "redirect:/tickets";
    }

    @PostMapping("/templates/delete/{id}")
    public String deleteTemplate(@PathVariable int id) {
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
