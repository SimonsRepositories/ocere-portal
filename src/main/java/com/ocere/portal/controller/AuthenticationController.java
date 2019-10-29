package com.ocere.portal.controller;

import com.ocere.portal.model.Role;
import com.ocere.portal.model.User;
import com.ocere.portal.service.RoleService;
import com.ocere.portal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class AuthenticationController
{
    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @GetMapping(value = { "/login" })
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login"); //resources/templates/login.html
        return modelAndView;
    }

    @GetMapping(value = "/register")
    public ModelAndView register(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        Role role = new Role();
        modelAndView.addObject("user", user);
        model.addAttribute("role", role);
        model.addAttribute("listOfRoles", roleService.findAll());
        modelAndView.addObject("listOfUsers", userService.findAll());
        modelAndView.setViewName("register");
        return modelAndView;
    }

    @PostMapping(value="/register")
    public String registerUser(Model model, @Valid @ModelAttribute User user, BindingResult bindingResult, ModelMap modelMap) {
        // Check for the validation
        if (bindingResult.hasErrors()) {
            //modelAndView.addObject("successMessage", "Please correct the errors in form!");
            System.out.println("has errors: " + bindingResult.toString());
            modelMap.addAttribute("bindingResult", bindingResult);
            model.addAttribute("listOfRoles", roleService.findAll());
            return "register";
        }
        //save the user if no binding errors
        else if(userService.isUserAlreadyPresent(user)){
            model.addAttribute("successMessage", "User already exists!");
        } else {
            if(user.getRoles() != null) {
                for (Role role : user.getRoles()) {
                    if (role.getRole().equals("CLIENT")) {
                        user.setClient(true);
                    }
                }
                userService.saveUser(user, user.getRoles());
            }
            model.addAttribute("successMessage", "User is registered successfully");
        }
        return "index";
    }
}
