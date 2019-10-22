package com.ocere.portal.controller;

import com.ocere.portal.model.Job;
import com.ocere.portal.service.ClientService;
import com.ocere.portal.service.JobService;
import com.ocere.portal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.sql.Timestamp;

@Controller
@RequestMapping("jobs")
public class JobController {

    private JobService jobService;
    private UserService userService;
    private ClientService clientService;

    @Autowired
    public JobController(JobService jobService, UserService userService, ClientService clientService) {
        this.jobService = jobService;
        this.userService = userService;
        this.clientService = clientService;
    }

    @GetMapping("create")
    public String loadCreateJobView(Model model, @RequestParam(name="clientId") int clientId) {
        model.addAttribute("siteTitle", "Create Job");
        model.addAttribute("action", "create");
        model.addAttribute("submitText", "Create");
        model.addAttribute("cancelPage", "/clients/" + clientId);

        Job job = new Job();
        job.setClient(clientService.getClientById(clientId));

        model.addAttribute("job", job);
        return "jobs_form";
    }

    @GetMapping("edit/{id}")
    public String loadJobEditView(Model model, @PathVariable int id) {
        Job job = jobService.findJobById(id).get();

        model.addAttribute("siteTitle", "Edit Job");
        model.addAttribute("action", "save/" + id);
        model.addAttribute("submitText", "Save");
        model.addAttribute("cancelPage", "/clients/" + job.getClient().getId());

        model.addAttribute("job", job);
        return "jobs_form";
    }

    /*
        ACTIONS
     */

    @PostMapping("/create")
    public String createJob(@ModelAttribute Job job, Principal principal) {
        job.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        job.setAuthor(this.userService.findByEmail(principal.getName()));

        this.jobService.saveJob(job);
        return "redirect:/clients/" + job.getClient().getId();
    }

    @PostMapping("/save/{id}")
    public String saveTicket(@PathVariable int id, @ModelAttribute Job job) throws Exception {
        mapUneditedValuesToJob(job, id);
        jobService.updateJob(job, id);
        return "redirect:/clients/" + job.getClient().getId();
    }

    @PostMapping("/delete/{id}")
    public String deleteTicket(@PathVariable int id) throws Exception {
        int clientId = jobService.findJobById(id).get().getClient().getId();
        jobService.deleteJobById(id);
        return "redirect:/clients/" + clientId;
    }

    private void mapUneditedValuesToJob(Job job, int id) {
        Job dbJob = jobService.findJobById(id).get();
        job.setCreatedAt(dbJob.getCreatedAt());
        job.setAuthor(dbJob.getAuthor());
    }
}
