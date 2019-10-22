package com.ocere.portal.controller;

import com.ocere.portal.model.Role;
import com.ocere.portal.service.RoleService;
import com.ocere.portal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.ocere.portal.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Controller
public class AuthenticationController
{
    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @RequestMapping(value = { "/page/login" } , method = RequestMethod.GET)
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login"); //resources/templates/login.html
        return modelAndView;
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView register(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        Role role = new Role();
        int[] idRoles = new int[4];
        model.addAttribute("idRoles", idRoles);
        modelAndView.addObject("user", user);
        model.addAttribute("role", role);
        model.addAttribute("listOfRoles", roleService.findAll());
        modelAndView.addObject("listOfUsers", userService.findAll());
        modelAndView.setViewName("register");
        return modelAndView;
    }

    @RequestMapping(value="/register", method = RequestMethod.POST)
    public ModelAndView registerUser(@ModelAttribute @Valid User user, BindingResult bindingResult, ModelMap modelMap) {
        ModelAndView modelAndView = new ModelAndView();
        // Check for the validation
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("successMessage", "Please correct the errors in form!");
            modelMap.addAttribute("bindingResult", bindingResult);
        }
        //save the user if no binding errors
        else if(userService.isUserAlreadyPresent(user)){
            modelAndView.addObject("successMessage", "User already exists!");
        } else {
            if(user.getRoles() != null) {
                for(Role idrole : user.getRoles()) {
                    System.out.println("<<" + idrole.getId() + ">>");
                }
                userService.saveUser(user, user.getRoles());
            }
            modelAndView.addObject("successMessage", "User is registered successfully");
        }
        modelAndView.addObject("user", new User());
        modelAndView.setViewName("register");
        return modelAndView;
    }
}
