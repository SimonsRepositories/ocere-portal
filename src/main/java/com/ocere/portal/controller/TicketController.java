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
    private JobService jobService;
    private DefticketService defticketService;

    @Autowired
    public TicketController(UserService userService,
                            UsergroupService usergroupService,
                            TicketService ticketService,
                            TemplateService templateService,
                            TurnaroundService turnaroundService,
                            JobService jobService,
                            DefticketService defticketService) {
        this.userService = userService;
        this.usergroupService = usergroupService;
        this.ticketService = ticketService;
        this.templateService = templateService;
        this.turnaroundService = turnaroundService;
        this.jobService = jobService;
        this.defticketService = defticketService;
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
    public String loadCreateTicketView(Model model,
                                       @RequestParam(name = "templateId", defaultValue = "-1") int id,
                                       @RequestParam(name = "jobId", defaultValue = "-1") int jobId) {
        model.addAttribute("siteTitle", "New Ticket");
        model.addAttribute("action", "create");
        model.addAttribute("submitText", "Create");
        model.addAttribute("cancelPage", "/tickets");
        model.addAttribute("templates", templateService.findAllTemplates());

        Ticket ticket;
        if (id == -1) {
            ticket = new Ticket();
        } else {
            ticket = this.templateService.findTemplateById(id);
        }

        if (jobId != -1) {
            ticket.setJob(jobService.findJobById(jobId).get());
        }

        model.addAttribute("ticket", ticket);
        model.addAttribute("users", this.userService.findAll());
        model.addAttribute("groups", this.usergroupService.findAll());
        model.addAttribute("turnaroundTimes", this.turnaroundService.findAll());
        return "tickets-form";
    }

    @GetMapping("/edit/{id}")
    public String loadTicketEditView(Model model, @PathVariable int id) {
        model.addAttribute("siteTitle", "Edit Ticket");
        model.addAttribute("action", "save/" + id);
        model.addAttribute("submitText", "Save");
        model.addAttribute("cancelPage", "/tickets/" + id);

        model.addAttribute("ticket", this.ticketService.findTicketById(id));
        model.addAttribute("users", this.userService.findAll());
        model.addAttribute("groups", this.usergroupService.findAll());
        model.addAttribute("turnaroundTimes", this.turnaroundService.findAll());
        return "tickets-form";
    }

    /*
        TEMPLATES
     */

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

    @GetMapping("/templates/create")
    public String loadCreateTemplateView(Model model) {
        model.addAttribute("siteTitle", "New Template");
        model.addAttribute("action", "templates/create");
        model.addAttribute("submitText", "Create");
        model.addAttribute("cancelPage", "/tickets/templates");

        Ticket ticket = new Ticket();
        ticket.setTemplate(true);

        model.addAttribute("ticket", ticket);
        model.addAttribute("users", this.userService.findAll());
        model.addAttribute("groups", this.usergroupService.findAll());
        model.addAttribute("turnaroundTimes", this.turnaroundService.findAll());
        return "tickets-form";
    }

    @GetMapping("/templates/edit/{id}")
    public String loadTemplateEditView(Model model, @PathVariable int id) {
        model.addAttribute("siteTitle", "Edit Template");
        model.addAttribute("action", "templates/save/" + id);
        model.addAttribute("submitText", "Save");
        model.addAttribute("cancelPage", "/tickets/templates/" + id);

        model.addAttribute("ticket", this.templateService.findTemplateById(id));
        model.addAttribute("users", this.userService.findAll());
        model.addAttribute("groups", this.usergroupService.findAll());
        model.addAttribute("turnaroundTimes", this.turnaroundService.findAll());
        return "tickets-form";
    }

    /*
        DEFTICKETS
     */

    @GetMapping("/deftickets")
    public String loadDefticketListView(Model model) {
        model.addAttribute("deftickets", this.defticketService.findAllDeftickets());
        return "tickets_deftickets-list";
    }

    @GetMapping("/deftickets/{id}")
    public String loadDefticketView(Model model, @PathVariable int id) {
        model.addAttribute("defticket", this.defticketService.findDefticketById(id));
        return "tickets_deftickets-view";
    }

    @GetMapping("/deftickets/create")
    public String loadCreateDefticketView(Model model) {
        model.addAttribute("siteTitle", "New Defticket");
        model.addAttribute("action", "deftickets/create");
        model.addAttribute("submitText", "Create");
        model.addAttribute("cancelPage", "/tickets/deftickets");

        Ticket ticket = new Ticket();
        ticket.setDefticket(true);

        model.addAttribute("ticket", ticket);
        model.addAttribute("users", this.userService.findAll());
        model.addAttribute("groups", this.usergroupService.findAll());
        model.addAttribute("turnaroundTimes", this.turnaroundService.findAll());
        return "tickets-form";
    }

    @GetMapping("/deftickets/edit/{id}")
    public String loadDefticketEditView(Model model, @PathVariable int id) {
        model.addAttribute("siteTitle", "Edit Generation Ticket");
        model.addAttribute("action", "deftickets/save/" + id);
        model.addAttribute("submitText", "Save");
        model.addAttribute("cancelPage", "/tickets/deftickets/" + id);

        model.addAttribute("ticket", this.defticketService.findDefticketById(id));
        model.addAttribute("users", this.userService.findAll());
        model.addAttribute("groups", this.usergroupService.findAll());
        model.addAttribute("turnaroundTimes", this.turnaroundService.findAll());
        return "tickets-form";
    }

    /*-----------------
          ACTIONS
    -----------------*/

    @PostMapping("/create")
    public String createTicket(@ModelAttribute Ticket ticket, Principal principal) {
        fillTicketReferencesById(ticket);

        ticket.setTemplate(false);
        ticket.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        ticket.setAuthor(this.userService.findByEmail(principal.getName()));

        this.ticketService.saveTicket(ticket);

        String redirectUrl = "redirect:/tickets";
        if (ticket.getJob() != null) {
            redirectUrl = "redirect:/jobs/" + ticket.getJob().getId();
        }
        return redirectUrl;
    }

    @PostMapping("/save/{id}")
    public String saveTicket(@PathVariable int id, @ModelAttribute Ticket ticket) throws Exception {
        fillTicketReferencesById(ticket);
        mapUneditedValuesToTicket(ticket, id);

        this.ticketService.updateTicket(ticket, id);
        return "redirect:/tickets/" + id;
    }

    @PostMapping("/delete/{id}")
    public String deleteTicket(@PathVariable int id) {
        this.ticketService.removeTicketById(id);
        return "redirect:/tickets";
    }

    /*
        TEMPLATES
     */

    @PostMapping("/templates/create")
    public String createTemplate(@ModelAttribute Ticket template, Principal principal) {
        fillTicketReferencesById(template);

        template.setTemplate(true);
        template.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        template.setAuthor(this.userService.findByEmail(principal.getName()));

        this.templateService.saveTemplate(template);
        return "redirect:/tickets/templates";
    }

    @PostMapping("/templates/save/{id}")
    public String saveTemplate(@PathVariable int id, @ModelAttribute Ticket template) throws Exception {
        fillTicketReferencesById(template);
        mapUneditedValuesToTemplate(template, id);

        this.templateService.updateTemplate(template, id);
        return "redirect:/tickets/templates/" + id;
    }

    @PostMapping("/templates/delete/{id}")
    public String deleteTemplate(@PathVariable int id) {
        this.templateService.removeTemplateById(id);
        return "redirect:/tickets/templates";
    }

    /*
        DEFTICKETS
     */

    @PostMapping("/deftickets/create")
    public String createDeftickets(@ModelAttribute Ticket defticket, Principal principal) {
        fillTicketReferencesById(defticket);

        defticket.setDefticket(true);
        defticket.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        defticket.setAuthor(this.userService.findByEmail(principal.getName()));

        this.defticketService.saveDefticket(defticket);
        return "redirect:/tickets/deftickets";
    }

    @PostMapping("/deftickets/save/{id}")
    public String saveDefticket(@PathVariable int id, @ModelAttribute Ticket defticket) throws Exception {
        fillTicketReferencesById(defticket);
        mapUneditedValuesToDefticket(defticket, id);

        this.defticketService.updateDefticket(defticket, id);
        return "redirect:/tickets/deftickets/" + id;
    }

    @PostMapping("/deftickets/delete/{id}")
    public String deleteDefticket(@PathVariable int id) {
        this.defticketService.removeDefticketById(id);
        return "redirect:/tickets/deftickets";
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

    /**
     * Needs to be done in order to keep the changes
     * @param ticket
     * @param ticketId
     */
    private void mapUneditedValuesToTicket(Ticket ticket, int ticketId) {
        Ticket dbTicket = ticketService.findTicketById(ticketId);
        ticket.setAuthor(dbTicket.getAuthor());
        ticket.setTemplate(dbTicket.isTemplate());
        ticket.setDefticket(dbTicket.isDefticket());
        ticket.setCreatedAt(dbTicket.getCreatedAt());
        ticket.setJob(dbTicket.getJob());
        ticket.setFiles(dbTicket.getFiles());
        ticket.setDynamicTurnaround(dbTicket.getDynamicTurnaround());
    }

    /**
     * Needs to be done in order to keep the changes
     * @param template
     * @param templateId
     */
    private void mapUneditedValuesToTemplate(Ticket template, int templateId) {
        Ticket dbTemplate = templateService.findTemplateById(templateId);
        template.setAuthor(dbTemplate.getAuthor());
        template.setTemplate(dbTemplate.isTemplate());
        template.setDefticket(dbTemplate.isDefticket());
        template.setCreatedAt(dbTemplate.getCreatedAt());
        template.setJob(dbTemplate.getJob());
        template.setFiles(dbTemplate.getFiles());
        template.setDynamicTurnaround(dbTemplate.getDynamicTurnaround());
    }

    /**
     * Needs to be done in order to keep the changes
     * @param defticket
     * @param defticketId
     */
    private void mapUneditedValuesToDefticket(Ticket defticket, int defticketId) {
        Ticket dbDefticket = defticketService.findDefticketById(defticketId);
        defticket.setAuthor(dbDefticket.getAuthor());
        defticket.setTemplate(dbDefticket.isTemplate());
        defticket.setDefticket(dbDefticket.isDefticket());
        defticket.setCreatedAt(dbDefticket.getCreatedAt());
        defticket.setJob(dbDefticket.getJob());
        defticket.setFiles(dbDefticket.getFiles());
    }
}
