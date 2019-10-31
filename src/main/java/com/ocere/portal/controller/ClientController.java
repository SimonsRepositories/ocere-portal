package com.ocere.portal.controller;

import com.ocere.portal.enums.ClientStatus;
import com.ocere.portal.enums.Tier;
import com.ocere.portal.model.Client;
import com.ocere.portal.model.Role;
import com.ocere.portal.model.User;
import com.ocere.portal.service.*;
import com.ocere.portal.service.Impl.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("clients")
public class ClientController {

    private static final String dic = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*_=+-/";

    private ClientService clientService;
    private UserService userService;
    private JobService jobService;
    private MailService mailService;
    private RoleService roleService;
    private ContactService contactService;

    @Autowired
    public ClientController(ClientService clientService,
                            UserService userService,
                            JobService jobService,
                            MailService mailService,
                            RoleService roleService,
                            ContactService contactService) {
        this.clientService = clientService;
        this.userService = userService;
        this.jobService = jobService;
        this.mailService = mailService;
        this.roleService = roleService;
        this.contactService = contactService;
    }

    @GetMapping
    public String clientLanding(Model model, Principal principal) {
        model.addAttribute("clients", clientService.findAll());
        model.addAttribute("created", clientService.findAllByAuthor(userService.findByEmail(principal.getName())));
        model.addAttribute("contacts", contactService.findAll());

        return "clients";
    }

    @GetMapping("{id}")
    public String loadTicketView(Model model, @PathVariable int id) {
        model.addAttribute("client", this.clientService.findClientById(id));
        model.addAttribute("jobs", this.jobService.findAllJobsByClientId(id));
        return "clients-view";
    }

    @GetMapping("edit/{id}")
    public String editClient(Model model, @PathVariable int id) {
        model.addAttribute("client", this.clientService.findClientById(id));

        model.addAttribute("siteTitle", "Edit Client");
        model.addAttribute("action", "save/" + id);
        model.addAttribute("submitText", "Save");
        model.addAttribute("cancelPage", "/clients/" + id);
        return "clients-form";
    }

    @PostMapping("delete/{id}")
    public String deleteClient(@PathVariable int id) throws Exception {
        clientService.deleteClientById(id);
        return "clients";
    }

    @GetMapping("create")
    public String createClient(Model model) {
        Client client = new Client();
        model.addAttribute("clients", clientService.findAll());
        model.addAttribute("client", client);
        model.addAttribute("users", userService.findAll());

        model.addAttribute("siteTitle", "New Client");
        model.addAttribute("action", "create");
        model.addAttribute("submitText", "Create");
        model.addAttribute("cancelPage", "/clients");

        return "clients-form";
    }

    @PostMapping("create")
    public String saveNewClient(@ModelAttribute Client client, Principal principal) {
        Set<Role> roles = new HashSet<>();
        Role clientRole = roleService.findById(4);
        roles.add(clientRole);

        // Create User account with generated credentials and mail them to user
        User user = new User();
        user.setFirstname(client.getContact().getFirst_name());
        user.setLastname(client.getContact().getLast_name());
        user.setEmail(client.getContact().getEmail());
        user.setRoles(new HashSet<>(4));
        user.setPassword(generatePassword(12));
        user.setClient(true);
        this.userService.saveUser(user, roles);

        client.getContact().setUser(user);
        client.setStatus(ClientStatus.DEAD);
        client.setTier(Tier.C);

        try {
            mailService.sendMail(principal.getName(), userService.findByEmail(principal.getName()).getMailpassword(), user.getEmail(), "Ocere login credentials",
                    "Authentication credentials for http://localhost:8080\n" +
                            "Username: " + user.getEmail() + "\n" +
                            "Password: " + user.getPassword());
        } catch (Exception e) {
            System.out.println("mail sending isn't possible with your email and mailpassword");
        }

        this.clientService.saveClient(client);

        return "redirect:/clients";
    }

    @PostMapping("save/{id}")
    public String saveClient(@PathVariable int id, @ModelAttribute Client client) throws Exception {
        mapUneditedValuesToClient(client, id);

        this.clientService.updateClient(client, id);
        return "redirect:/clients/" + id;
    }

    private static SecureRandom random = new SecureRandom();

    private static String generatePassword(int len) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < len; i++) {
            int index = random.nextInt(dic.length());
            result.append(dic.charAt(index));
        }
        return result.toString();
    }

    /**
     * Needs to be done in order to keep the changes
     *
     * @param client
     * @param clientId
     */
    private void mapUneditedValuesToClient(Client client, int clientId) {
        Client dbClient = clientService.findClientById(clientId);
        client.setAuthor(dbClient.getAuthor());
    }
}
