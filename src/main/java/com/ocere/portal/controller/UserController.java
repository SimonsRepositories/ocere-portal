package com.ocere.portal.controller;

import com.ocere.portal.model.Role;
import com.ocere.portal.model.User;
import com.ocere.portal.service.RoleService;
import com.ocere.portal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController
{
    private UserService userService;
    private RoleService roleService;

    //CRUD for Users with any kind of roles

    @Autowired
    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ModelAndView adminHome() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("listOfUsers", userService.findAll());
        modelAndView.setViewName("users"); // resources/template/admin.html
        return modelAndView;
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @RequestMapping(value="/users/delete-user", method = RequestMethod.GET)
    public ModelAndView deleteUser(@RequestParam(name="id", required = true) int id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("listOfUsers", userService.findAll());
        userService.removeUserById(id);
        modelAndView.setViewName("redirect:/users");
        return modelAndView;
    }

    @RequestMapping(value="/users/{id}", method = RequestMethod.GET)
    public String showUser(Model model, @PathVariable("id") int id) {
        ModelAndView modelAndView = new ModelAndView();
        User value = userService.getUserById(id).get();
        model.addAttribute("listOfRoles", roleService.findAll());
        model.addAttribute("user", value);
        return "users-view";
    }

    @RequestMapping(value="/users/edit-user/{id}", method = RequestMethod.GET)
    public ModelAndView editUser(Model model, @PathVariable("id") int id) {
        ModelAndView modelAndView = new ModelAndView();
        User value = userService.getUserById(id).get();
        Role role = new Role();
        int[] idRoles = new int[4];
        model.addAttribute("idRoles", idRoles);
        model.addAttribute("role", role);
        model.addAttribute("user", value);
        model.addAttribute("listOfRoles", roleService.findAll());
        modelAndView.setViewName("users-form");
        return modelAndView;
    }

    @RequestMapping(value="/users/edit-user", method = RequestMethod.POST)
    public ModelAndView editUser(@ModelAttribute User user, Model model) {
        ModelAndView modelAndView = new ModelAndView();
        if(user.getRoles() != null) {
            userService.saveUserById(user, user.getId(), user.getRoles());
        }

        model.addAttribute("listOfUsers", userService.findAll());
        modelAndView.setViewName("redirect:/users");
        return modelAndView;
    }
}
