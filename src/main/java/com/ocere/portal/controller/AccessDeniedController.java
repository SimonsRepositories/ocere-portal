package com.ocere.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AccessDeniedController
{
    @RequestMapping(value = "/access-denied", method = RequestMethod.GET)
    public String showAccessDeniedPage() {
        return "access-denied";
    }

    /*@RequestMapping(value = "/error", RequestMethod.GET)
    public String showErrorPage() {
        return "error-page";
    }*/
}
