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

import javax.validation.Valid;
import java.util.Arrays;
import java.util.HashSet;

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
        modelAndView.addObject("user", user);
        model.addAttribute("role", role);
        model.addAttribute("listOfRoles", roleService.findAll());
        modelAndView.addObject("listOfUsers", userService.findAll());
        modelAndView.setViewName("register"); //resources/templates/register.html
        return modelAndView;
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index"); //resources/templates/index.html
        return modelAndView;
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public ModelAndView adminHome() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("listOfUsers", userService.findAll());
        modelAndView.setViewName("admin"); // resources/template/admin.html
        return modelAndView;
    }

    @RequestMapping(value="/register", method = RequestMethod.POST)
    public ModelAndView registerUser(@ModelAttribute("role") Role role, @Valid User user, BindingResult bindingResult, ModelMap modelMap) {
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
            userService.saveUser(user, role);
            modelAndView.addObject("successMessage", "User is registered successfully");
        }
        modelAndView.addObject("user", new User());
        modelAndView.setViewName("register");
        return modelAndView;
    }
    @RequestMapping(value="/admin/delete-user", method = RequestMethod.GET)
    public ModelAndView deleteUser(@RequestParam(name="id", required = true) int id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("listOfUsers", userService.findAll());
        userService.removeUserById(id);

        modelAndView.setViewName("redirect:/admin");
        return modelAndView;
    }

    @RequestMapping(value="/admin/editUser/{id}{role}", method = RequestMethod.GET)
    public ModelAndView editUser(@PathVariable("id") int id, @PathVariable("role") Role role) {
        ModelAndView modelAndView = new ModelAndView();
        User value = userService.getUserById(id).get();
        Role roleValue = roleService.findByRole(role.getRole());
        modelAndView.addObject("role", roleValue);
        modelAndView.addObject("user", value);
        modelAndView.setViewName("editUser");
        return modelAndView;
    }

    @RequestMapping(value="/admin/editUser", method = RequestMethod.POST)
    public ModelAndView editUser(User user, Role role) {
        ModelAndView modelAndView = new ModelAndView();
        userService.saveUserById(user, user.getId(), role);
        modelAndView.addObject("listOfUsers", userService.findAll());
        modelAndView.setViewName("admin");
        return modelAndView;
    }
}
