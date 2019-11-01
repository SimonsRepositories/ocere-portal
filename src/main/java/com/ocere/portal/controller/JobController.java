package com.ocere.portal.controller;

import com.ocere.portal.enums.ClientStatus;
import com.ocere.portal.enums.ProductType;
import com.ocere.portal.model.*;
import com.ocere.portal.service.*;
import com.ocere.portal.service.Impl.DBFileStorageService;
import com.ocere.portal.service.Impl.MailService;
import com.ocere.portal.tasks.NextJobTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("jobs")
public class JobController {

    private JobService jobService;
    private UserService userService;
    private ClientService clientService;
    private DBFileStorageService dbFileStorageService;
    private TicketService ticketService;
    private DefticketService defticketService;
    private TaskScheduler taskScheduler;
    private MailService mailService;

    @Autowired
    public JobController(JobService jobService,
                         UserService userService,
                         ClientService clientService,
                         DBFileStorageService dbFileStorageService,
                         TicketService ticketService,
                         DefticketService defticketService,
                         TaskScheduler taskScheduler,
                         MailService mailService) {
        this.jobService = jobService;
        this.userService = userService;
        this.clientService = clientService;
        this.dbFileStorageService = dbFileStorageService;
        this.ticketService = ticketService;
        this.defticketService = defticketService;
        this.taskScheduler = taskScheduler;
        this.mailService = mailService;
    }

    @GetMapping
    public String loadJobListView(Model model) {
        model.addAttribute("jobs", this.jobService.findAll());
        return "jobs-list";
    }

    @GetMapping("{id}")
    public String loadTicketView(Model model, @PathVariable int id) {
        model.addAttribute("job", this.jobService.findJobById(id).get());
        model.addAttribute("tickets", this.ticketService.findAllTicketsByJobId(id));
        return "jobs-view";
    }

    @GetMapping("create")
    public String loadCreateJobView(Model model, @RequestParam(name = "clientId") int clientId) {
        model.addAttribute("siteTitle", "Create Job");
        model.addAttribute("action", "create");
        model.addAttribute("submitText", "Create");
        model.addAttribute("cancelPage", "/clients/" + clientId);

        model.addAttribute("owners", userService.findAll());

        Job job = new Job();
        job.setClient(clientService.findClientById(clientId));

        model.addAttribute("job", job);
        return "jobs-form";
    }

    @GetMapping("edit/{id}")
    public String loadJobEditView(Model model, @PathVariable int id) {
        Job job = jobService.findJobById(id).get();

        model.addAttribute("siteTitle", "Edit Job");
        model.addAttribute("action", "save/" + id);
        model.addAttribute("submitText", "Save");
        model.addAttribute("cancelPage", "/jobs/" + job.getId());

        model.addAttribute("owners", userService.findAll());

        model.addAttribute("job", job);
        return "jobs-form";
    }

    @GetMapping("/clone")
    public String loadCloneJobView(@RequestParam(name = "jobId") int jobId, Principal principal, Model model) {
        Job job = this.jobService.findJobById(jobId).get();

        model.addAttribute("siteTitle", "Clone Job");
        model.addAttribute("action", "create");
        model.addAttribute("submitText", "Clone");
        model.addAttribute("cancelPage", "/clients/" + job.getClient().getId());

        model.addAttribute("owners", userService.findAll());

        model.addAttribute("job", new Job(job, this.userService.findByEmail(principal.getName())));
        return "jobs-form";
    }

    /*
        ACTIONS
     */

    @PostMapping("/create")
    public String createJob(@ModelAttribute Job job, Principal principal) throws Exception {
        fillJobReferencesById(job);

        User author = this.userService.findByEmail(principal.getName());
        job.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        job.setAuthor(author);

        Set<Ticket> initialTickets = new HashSet<>();
        for (Ticket defticket: this.defticketService.findAllDeftickets()) {
            for (ProductType productType: job.getProductTypes()) {
                if (defticket.getDefProducts().contains(productType)) {
                    initialTickets.add(defticket);
                }
            }
        }
        initialTickets = initialTickets.stream().map(ticket -> new Ticket(ticket, job, author)).collect(Collectors.toSet());
        job.setTickets(initialTickets);

        Client client = clientService.findClientById(job.getClient().getId());
        //set spending
        client.setTotalSpending(client.getTotalSpending() + job.getTotalValue());
        client.setMonthlySpending(client.getMonthlySpending() + job.getTotalValue());

        clientService.updateTier(client.getId());
        client.setStatus(ClientStatus.ACTIVE);

        this.clientService.updateClient(client, client.getId());

        Job savedJob = this.jobService.saveJob(job);
        NextJobTask nextJobTask = new NextJobTask(mailService, savedJob.getOwner(), savedJob);
        taskScheduler.schedule(nextJobTask, savedJob.getEndDate());

        return "redirect:/jobs/" + job.getId();
    }

    @PostMapping("/save/{id}")
    public String saveTicket(@PathVariable int id, @ModelAttribute Job job) throws Exception {
        fillJobReferencesById(job);

        mapUneditedValuesToJob(job, id);
        jobService.updateJob(job, id);

        //Set spending
        job.getClient().setTotalSpending(job.getClient().getTotalSpending() + job.getTotalValue());
        job.getClient().setMonthlySpending(job.getClient().getMonthlySpending() + job.getTotalValue());

        clientService.updateTier(job.getClient().getId());

        return "redirect:/jobs/" + job.getId();
    }

    @PostMapping("/delete/{id}")
    public String deleteTicket(@PathVariable int id) throws Exception {
        int clientId = jobService.findJobById(id).get().getClient().getId();
        Client client = clientService.findClientById(clientId);
        Job job = jobService.findJobById(id).get();
        client.setTotalSpending(client.getTotalSpending() - job.getTotalValue());

        //Set spending
        job.getClient().setTotalSpending(job.getClient().getTotalSpending() - job.getTotalValue());
        job.getClient().setMonthlySpending(job.getClient().getMonthlySpending() - job.getTotalValue());

        clientService.updateTier(job.getClient().getId());

        jobService.deleteJobById(id);

        return "redirect:/clients/" + clientId;
    }

    @PostMapping("/{id}/uploadFiles")
    public String uploadFile(@PathVariable int id, @RequestParam("files") MultipartFile[] files, Principal principal) throws Exception {
        Job job = jobService.findJobById(id).get();
        Set<DBFile> dbFiles = job.getFiles();
        Arrays.stream(files)
                .map(file -> dbFileStorageService.storeFileWithPrincipal(file, principal))
                .forEach(dbFiles::add);
        job.saveFiles(dbFiles);
        jobService.updateJob(job, id);

        return "redirect:/jobs/" + id;
    }

    private void mapUneditedValuesToJob(Job job, int id) {
        Job dbJob = jobService.findJobById(id).get();
        job.setCreatedAt(dbJob.getCreatedAt());
        job.setAuthor(dbJob.getAuthor());
    }

    private void fillJobReferencesById(Job job) {
        Optional<User> owner = this.userService.getUserById(job.getOwner().getId());
        owner.ifPresentOrElse(job::setOwner, () -> job.setOwner(null));

        if (job.getOrderFormFile().getId().equals("")) {
            job.setOrderFormFile(null);
        } else {
            DBFile dbFile = this.dbFileStorageService.getFile(job.getOrderFormFile().getId());
            job.saveOrderFormFile(dbFile);
        }
    }
}
