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

    @GetMapping("/users")
    public ModelAndView adminHome() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("listOfUsers", userService.findAll());
        modelAndView.setViewName("users"); // resources/template/admin.html
        return modelAndView;
    }

    @GetMapping("/home")
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @GetMapping("/users/delete-user")
    public ModelAndView deleteUser(@RequestParam(name="id", required = true) int id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("listOfUsers", userService.findAll());
        userService.removeUserById(id);
        modelAndView.setViewName("redirect:/users");
        return modelAndView;
    }

    @GetMapping("/users/{id}")
    public String showUser(Model model, @PathVariable("id") int id) {
        ModelAndView modelAndView = new ModelAndView();
        User value = userService.getUserById(id).get();
        model.addAttribute("listOfRoles", roleService.findAll());
        model.addAttribute("user", value);
        return "users-view";
    }

    @GetMapping("/users/edit-user/{id}")
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

    @PostMapping("/users/edit-user")
    public ModelAndView editUser(@Valid @ModelAttribute User user, BindingResult bindingResult, ModelMap modelMap, Model model)
    {
        ModelAndView modelAndView = new ModelAndView();
        /*System.out.println(user.getId());
        User tmpValue = new User();
        if (user.getPassword().equals("")) {
            for (int i = 0; i < userService.findAll().size(); i++) {
                tmpValue = userService.findAll().get(i);
                user.setPassword(tmpValue.getPassword());
                user.setId(tmpValue.getId());
                if(user.getMailpassword().equals("")) {
                    user.setMailpassword(tmpValue.getMailpassword());
                }
            }
        }*/
        //Check for the validation
        if (bindingResult.hasErrors()) {
            System.out.println("has errors: " + bindingResult.toString());
            modelMap.addAttribute("bindingResult", bindingResult);
            modelAndView.addObject("listOfRoles", roleService.findAll());
            modelAndView.setViewName("users-form");
            return modelAndView;
        }
        //save the user if no binding errors
        else if (userService.isUserAlreadyPresent(user)) {
            modelAndView.addObject("successMessage", "User already exists!");
        } else {
            if (user.getRoles() != null) {
                userService.saveUserById(user, user.getId(), user.getRoles());
            }
        }
        modelAndView.addObject("listOfUsers", userService.findAll());
        modelAndView.setViewName("redirect:/users");
        return modelAndView;
    }
}
