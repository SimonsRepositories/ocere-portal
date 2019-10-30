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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;

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

    @RequestMapping(value="/users/edit-user/{id}", method = RequestMethod.POST)
    public ModelAndView editUser(@Valid @ModelAttribute User user, BindingResult bindingResult, ModelMap modelMap, Model model, @PathVariable("id") int id)
    {
        ModelAndView modelAndView = new ModelAndView();
        User user_new = userService.getUserById(id).get();
        //Check for the validation
        if (bindingResult.hasErrors()) {
            System.out.println("has errors: " + bindingResult.toString());
            modelMap.addAttribute("bindingResult", bindingResult);
            modelAndView.addObject("listOfRoles", roleService.findAll());
            modelAndView.setViewName("redirect:/users/edit-user/" + user_new.getId());
            return modelAndView;
        }
        //save the user if no binding errors
        else if (!userService.isUserAlreadyPresent(user)) {
            modelAndView.addObject("successMessage", "User already exists!");
        }else {
            if (user.getRoles() != null) {
                userService.saveUserById(user, user_new.getId(), user.getRoles());
            }
        }
        modelAndView.addObject("listOfUsers", userService.findAll());
        modelAndView.setViewName("redirect:/users");
        return modelAndView;
    }

    @RequestMapping(value = "/users/change-password/{id}", method = RequestMethod.GET)
    public String showChangePasswordPage(@PathVariable("id") int id, Model model) {
        //model.addAttribute("user", userService.findByEmail(principal.getName()));
        User value = userService.getUserById(id).get();
        model.addAttribute("user", value);
        return "user-changePassword";
    }

    @RequestMapping(value = "/users/change-password/{id}", method = RequestMethod.POST)
    public String changePasswordUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, ModelMap modelMap, Model model, @PathVariable("id") int id) {
        User newuser = userService.getUserById(id).get();
        if(bindingResult.hasErrors()) {
            System.out.println("has errors: " + bindingResult.toString());
            modelMap.addAttribute("bindingResult", bindingResult);
            return "user-changePassword";
        }
        //save the user password if no binding errors
        else if(!userService.isUserAlreadyPresent(user)) {
            model.addAttribute("successMessage", "User already exists!");
        }
        else {
            userService.saveUserById(user, newuser.getId(), user.getRoles());
        }
        model.addAttribute("successMessage", "Your new password is saved!");
        model.addAttribute("listOfRoles", roleService.findAll());
        return "users-form";
    }

    @RequestMapping(value = "/users/change-mailpassword/{id}", method = RequestMethod.GET)
    public String showMailPasswordPage(@PathVariable("id") int id, Model model) {
        //model.addAttribute("user", userService.findByEmail(principal.getName()));
        model.addAttribute("user", userService.getUserById(id).get());
        return "user-changeMailPassword";
    }

    @RequestMapping(value = "/users/change-mailpassword/{id}", method = RequestMethod.POST)
    public String changeMailPassword(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, ModelMap modelMap, Model model, @PathVariable("id") int id) {
        User newuser = userService.getUserById(id).get();
        if(bindingResult.hasErrors()) {
            System.out.println("has Errors" + bindingResult.toString());
            modelMap.addAttribute("bindingResult", bindingResult);
            return "user-changeMailPassword";
        }
        //save the user mail password i no binding errors
        else if(!userService.isUserAlreadyPresent(user)) {
            model.addAttribute("successMessage", "User already exists!");
        } else {
            userService.saveUserById(user, newuser.getId(), user.getRoles());
        }
        model.addAttribute("successMessage", "Your new mail password is saved!");
        model.addAttribute("listOfRoles", roleService.findAll());
        return "users-form";
    }
}
