package com.ocere.portal.controller;

import com.ocere.portal.model.Client;
import com.ocere.portal.model.Role;
import com.ocere.portal.model.User;
import com.ocere.portal.service.ClientService;
import com.ocere.portal.service.Impl.MailService;
import com.ocere.portal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.security.SecureRandom;
import java.util.HashSet;

@Controller
public class ClientController {
    private ClientService clientService;
    private UserService userService;
    private MailService mailService;
    private static final String dic = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*_=+-/";


    @Autowired
    public ClientController(ClientService clientService, UserService userService, MailService mailService) {
        this.clientService = clientService;
        this.userService = userService;
        this.mailService = mailService;
    }

    @GetMapping("/clients")
    public String clientLanding(Model model, Principal principal) {
        model.addAttribute("clients", clientService.findAll());
        model.addAttribute("assigned", clientService.findAllByAssignedUser(userService.findByEmail(principal.getName())));
        model.addAttribute("created", clientService.findAllByAuthor(userService.findByEmail(principal.getName())));

        return "clients";
    }

    @GetMapping("clients/{id}")
    public String loadTicketView(Model model, @PathVariable int id) {
        model.addAttribute("client", this.clientService.getClientById(id));
        return "clients-view";
    }

    @GetMapping("clients/edit/{id}")
    public String editClient(Model model, @PathVariable int id) {
        model.addAttribute("client", clientService.getClientById(id));
        return "clients-edit";
    }

    @PostMapping("clients/delete/{id}")
    public String deleteClient(@PathVariable int id) {
        clientService.removeClientById(id);
        return "clients";
    }

    @GetMapping("clients/create")
    public String createClient(Model  model) {
        Client newClient = new Client();
        model.addAttribute("clients", clientService.findAll());
        model.addAttribute("newClient", newClient);
        model.addAttribute("users", userService.findAll());
        return "clients-create";
    }

    @PostMapping("clients/save")
    public String saveClient(@ModelAttribute Client client, Principal principal) throws UnsupportedEncodingException, MessagingException {
        // this.clientService.saveClient(client);

        // Create User account with generated credentials and mail them to user
        User user = new User();
        user.setFirstname(client.getContactFirstName());
        user.setLastname(client.getContactLastName());
        user.setEmail(client.getEmail());
        user.setRoles(new HashSet<Role>(4));
        user.setPassword(generatePassword(12));

        String mailpw = "";
        mailService.sendMail(principal.getName(), mailpw, client.getEmail(), "Ocere login credentials",
                "Authentication credentials for http://localhost:8080\n" +
                        "Username: " + user.getEmail()+ "\n" +
                        "Password: " + user.getPassword());
        return "redirect:/clients";
    }

    private static SecureRandom random = new SecureRandom();

    public static String generatePassword(int len) {
        String result = "";
        for (int i = 0; i < len; i++) {
            int index = random.nextInt(dic.length());
            result += dic.charAt(index);
        }
        return result;
    }
}
