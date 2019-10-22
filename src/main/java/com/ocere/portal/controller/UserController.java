package com.ocere.portal.controller;

import com.ocere.portal.model.Role;
import com.ocere.portal.model.User;
import com.ocere.portal.service.RoleService;
import com.ocere.portal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

    @RequestMapping(value="/users/{id}{role}", method = RequestMethod.GET)
    public ModelAndView showUser(@PathVariable("id") int id, @PathVariable("role") int roleId) {
        ModelAndView modelAndView = new ModelAndView();
        User value = userService.getUserById(id).get();
        Role role = roleService.findById(roleId);
        modelAndView.addObject("role", role);
        modelAndView.addObject("user", value);
        modelAndView.setViewName("users-view");
        return modelAndView;
    }

    @RequestMapping(value="/users/edit-user/{id}{role}", method = RequestMethod.GET)
    public ModelAndView editUser(@PathVariable("id") int id, @PathVariable("role") Role role) {
        ModelAndView modelAndView = new ModelAndView();
        User value = userService.getUserById(id).get();
        Role roleValue = roleService.findById(role.getId());
        modelAndView.addObject("role", roleValue);
        modelAndView.addObject("user", value);
        modelAndView.setViewName("users-form");
        return modelAndView;
    }

    @RequestMapping(value="/users/edit-user", method = RequestMethod.POST)
    public ModelAndView editUser(User user, Role role) {
        ModelAndView modelAndView = new ModelAndView();
        //userService.saveUserById(user, user.getId(), role.getId());
        modelAndView.addObject("listOfUsers", userService.findAll());
        modelAndView.setViewName("redirect:/users");
        return modelAndView;
    }
}
