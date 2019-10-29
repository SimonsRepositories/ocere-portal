package com.ocere.portal.controller;

import com.ocere.portal.enums.ProductType;
import com.ocere.portal.enums.Status;
import com.ocere.portal.model.*;
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
    private PredefinedTicketCollectionService predefinedTicketCollectionService;

    @Autowired
    public TicketController(UserService userService,
                            UsergroupService usergroupService,
                            TicketService ticketService,
                            TemplateService templateService,
                            TurnaroundService turnaroundService,
                            JobService jobService,
                            PredefinedTicketCollectionService predefinedTicketCollectionService) {
        this.userService = userService;
        this.usergroupService = usergroupService;
        this.ticketService = ticketService;
        this.templateService = templateService;
        this.turnaroundService = turnaroundService;
        this.jobService = jobService;
        this.predefinedTicketCollectionService = predefinedTicketCollectionService;
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
        return "tickets_form";
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
        return "tickets_form";
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
        return "tickets_form";
    }

    @GetMapping("/predefined")
    public String loadPredefinedTicketsListView(Model model) {
        model.addAttribute("seoTickets", predefinedTicketCollectionService.findByProductType(ProductType.SEO).getSortedTickets());
        model.addAttribute("linkTickets", predefinedTicketCollectionService.findByProductType(ProductType.LinkBuilding).getSortedTickets());
        model.addAttribute("ppcTickets", predefinedTicketCollectionService.findByProductType(ProductType.PPC).getSortedTickets());
        model.addAttribute("contentTickets", predefinedTicketCollectionService.findByProductType(ProductType.Content).getSortedTickets());

        return "predefined-tickets_list";
    }

    @GetMapping("/predefined/{id}")
    public String loadPredefinedTicketView(Model model, @PathVariable int id) {
        model.addAttribute("ticket", this.ticketService.findTicketById(id));
        return "predefined-tickets-view";
    }

    @GetMapping("/predefined/create")
    public String loadPredefinedTicketsCreationView(Model model) {
        model.addAttribute("siteTitle", "New Predefined Ticket");
        model.addAttribute("action", "predefined/create");
        model.addAttribute("submitText", "Create");
        model.addAttribute("cancelPage", "/tickets/predefined");

        model.addAttribute("ticket", new Ticket());
        model.addAttribute("predefinedTicketCollections", this.predefinedTicketCollectionService.findAll());
        model.addAttribute("users", this.userService.findAll());
        model.addAttribute("groups", this.usergroupService.findAll());
        model.addAttribute("turnaroundTimes", this.turnaroundService.findAll());

        return "predefined-tickets_form";
    }

    @GetMapping("/predefined/edit/{id}")
    public String loadPredefinedTicketEditView(Model model, @PathVariable int id) {
        model.addAttribute("siteTitle", "Edit Predefined Ticket");
        model.addAttribute("action", "predefined/save/" + id);
        model.addAttribute("submitText", "Save");
        model.addAttribute("cancelPage", "/tickets/predefined/" + id);

        model.addAttribute("ticket", this.ticketService.findTicketById(id));
        model.addAttribute("predefinedTicketCollections", this.predefinedTicketCollectionService.findAll());
        model.addAttribute("users", this.userService.findAll());
        model.addAttribute("groups", this.usergroupService.findAll());
        model.addAttribute("turnaroundTimes", this.turnaroundService.findAll());
        return "predefined-tickets_form";
    }

    /*
        ACTIONS
     */

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

    @PostMapping("/templates/create")
    public String createTemplate(@ModelAttribute Ticket template, Principal principal) {
        fillTicketReferencesById(template);

        template.setTemplate(true);
        template.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        template.setAuthor(this.userService.findByEmail(principal.getName()));

        this.templateService.saveTemplate(template);
        return "redirect:/tickets/templates";
    }

    @PostMapping("/predefined/create")
    public String createPredefinedTicket(@ModelAttribute Ticket ticket, Principal principal) {
        fillTicketReferencesById(ticket);

        Optional<PredefinedTicketCollection> predefinedTicketCollection =
                this.predefinedTicketCollectionService.findById(ticket.getPredefinedTicketCollection().getId());
        predefinedTicketCollection.ifPresentOrElse(ticket::setPredefinedTicketCollection,
                () -> ticket.setPredefinedTicketCollection(null));

        ticket.setTemplate(false);
        ticket.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        ticket.setAuthor(this.userService.findByEmail(principal.getName()));

        this.ticketService.saveTicket(ticket);

        return "redirect:/tickets/predefined";
    }

    @PostMapping("/save/{id}")
    public String saveTicket(@PathVariable int id, @ModelAttribute Ticket ticket) throws Exception {
        fillTicketReferencesById(ticket);
        mapUneditedValuesToTicket(ticket, id);

        this.ticketService.updateTicket(ticket, id);
        return "redirect:/tickets/" + id;
    }

    @PostMapping("/templates/save/{id}")
    public String saveTemplate(@PathVariable int id, @ModelAttribute Ticket template) throws Exception {
        fillTicketReferencesById(template);
        mapUneditedValuesToTemplate(template, id);

        this.templateService.updateTemplate(template, id);
        return "redirect:/tickets/templates/" + id;
    }

    @PostMapping("/predefined/save/{id}")
    public String savePredefinedTicket(@PathVariable int id, @ModelAttribute Ticket ticket) throws Exception {
        fillTicketReferencesById(ticket);
        mapUneditedValuesToTicket(ticket, id);

        this.ticketService.updateTicket(ticket, id);
        return "redirect:/tickets/predefined/" + id;
    }

    @PostMapping("/delete/{id}")
    public String deleteTicket(@PathVariable int id) {
        this.ticketService.removeTicketById(id);
        return "redirect:/tickets";
    }

    @PostMapping("/templates/delete/{id}")
    public String deleteTemplate(@PathVariable int id) {
        this.templateService.removeTemplateById(id);
        return "redirect:/tickets/templates";
    }

    @PostMapping("/predefined/delete/{id}")
    public String deletePredefinedTicket(@PathVariable int id) {
        this.ticketService.removeTicketById(id);
        return "redirect:/tickets/predefined";
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
        ticket.setCreatedAt(dbTicket.getCreatedAt());
        ticket.setJob(dbTicket.getJob());
        ticket.setFiles(dbTicket.getFiles());
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
        template.setCreatedAt(dbTemplate.getCreatedAt());
        template.setJob(dbTemplate.getJob());
        template.setFiles(dbTemplate.getFiles());
    }
}
