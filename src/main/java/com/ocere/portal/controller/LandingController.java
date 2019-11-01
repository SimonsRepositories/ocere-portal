package com.ocere.portal.controller;

import com.ocere.portal.model.Role;
import com.ocere.portal.model.User;
import com.ocere.portal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class LandingController {

    @GetMapping("/")
    public String landing() {

        return "index";
    }

}
