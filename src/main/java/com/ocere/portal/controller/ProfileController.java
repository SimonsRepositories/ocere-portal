package com.ocere.portal.controller;

import com.ocere.portal.model.User;
import com.ocere.portal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class ProfileController {
    private UserService userService;

    @Autowired
    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String profileOverview(Model model, Principal principal) {
        User value = userService.findByEmail(principal.getName());
        model.addAttribute("user", value);
        return "profile-view";
    }

    @RequestMapping(value = "/profile/change-password/{id}", method = RequestMethod.GET)
    public String showChangePasswordProfilePage(@PathVariable("id") int id, Model model) {
        User value = userService.getUserById(id).get();
        model.addAttribute("user", value);
        return "changePassword";
    }

    @RequestMapping(value = "/profile/change-password/{id}", method = RequestMethod.POST)
    public String changePasswordProfile(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, ModelMap modelMap, Model model, @PathVariable("id") int id) {
        User newuser = userService.getUserById(id).get();
        if(bindingResult.hasErrors()) {
            System.out.println("has errors: " + bindingResult.toString());
            modelMap.addAttribute("bindingResult", bindingResult);
            return "changePassword";
        }
        //save the user password if no binding errors
        else if(!userService.isUserAlreadyPresent(user)) {
            model.addAttribute("successMessage", "User already exists!");
        }
        else {
            userService.saveUserById(user, newuser.getId(), user.getRoles());
        }
        model.addAttribute("successMessage", "Your new password is saved!");
        return "index";
    }
}
