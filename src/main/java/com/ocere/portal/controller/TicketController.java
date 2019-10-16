package com.ocere.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TicketController {
    @RequestMapping("/tickets")
    public ModelAndView ticketOverview() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("ticket"); //resources/templates/ticket.html
        return modelAndView;
    }
}
