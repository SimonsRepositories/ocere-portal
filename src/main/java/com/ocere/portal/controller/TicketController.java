package com.ocere.portal.controller;

import com.ocere.portal.model.Ticket;
import com.ocere.portal.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/tickets")
public class TicketController {
    @Autowired
    TicketService ticketService;

    @GetMapping("/dashboard")
    public String allTickets(Model model) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        List<Ticket> tickets = ticketService.findAll();
        List<Ticket> overdue = new ArrayList<>();

        //
        for (Ticket ticket : tickets) {
            if (ticket.getTurnaround().after(timestamp)) {
                overdue.add(ticket);
            }
        }
        model.addAttribute("all", tickets);
        return "ticket";
    }

}
