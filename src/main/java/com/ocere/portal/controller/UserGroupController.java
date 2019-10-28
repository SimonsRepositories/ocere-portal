package com.ocere.portal.controller;

import com.ocere.portal.model.Usergroup;
import com.ocere.portal.service.UsergroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class UserGroupController
{
    private UsergroupService usergroupService;

    @Autowired
    public UserGroupController(UsergroupService usergroupService) {
        this.usergroupService = usergroupService;
    }

    @GetMapping("/groups")
    public String showGroupsPage(Model model) {
        model.addAttribute("listOfGroups", usergroupService.findAll());
        return "groups-list";
    }

    @RequestMapping(value="/groups/delete-group", method = RequestMethod.GET)
    public ModelAndView deleteUser(@RequestParam(name="id", required = true) int id) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("listOfGroups", usergroupService.findAll());
        usergroupService.deleteUsergroupById(id);
        modelAndView.setViewName("redirect:/groups");
        return modelAndView;
    }

    @RequestMapping(value="/groups/{id}", method = RequestMethod.GET)
    public String showUser(Model model, @PathVariable("id") int id) {
        Usergroup value = usergroupService.findUsergroupById(id).get();
        model.addAttribute("usergroup", value);
        return "groups-view";
    }

    @RequestMapping(value="/groups/edit-group/{id}", method = RequestMethod.GET)
    public ModelAndView editUser(Model model, @PathVariable("id") int id) {
        ModelAndView modelAndView = new ModelAndView();
        Usergroup value = usergroupService.findUsergroupById(id).get();
        model.addAttribute("action", "groups/edit-group/" + id);
        model.addAttribute("usergroup", value);
        modelAndView.setViewName("groups-form");
        return modelAndView;
    }

    @RequestMapping(value="/groups/edit-group", method = RequestMethod.POST)
    public ModelAndView editUser(@Valid @ModelAttribute Usergroup usergroup, BindingResult bindingResult, ModelMap modelMap) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        //Check for the validation
        if (bindingResult.hasErrors()) {
            System.out.println("has errors: " + bindingResult.toString());
            modelMap.addAttribute("bindingResult", bindingResult);
            modelAndView.setViewName("groups-form");
            return modelAndView;
        }
        //save the group if no binding errors
        else if(!usergroup.isEmpty())
        {
            usergroupService.updateUsergroup(usergroup, usergroup.getId());
        }
        modelAndView.addObject("listOfGroups", usergroupService.findAll());
        modelAndView.setViewName("groups-list");
        return modelAndView;
    }

    @GetMapping(value = "/groups/create-group")
    public ModelAndView register(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        Usergroup usergroup = new Usergroup();
        model.addAttribute("action", "groups/create-group");
        modelAndView.addObject("usergroup", usergroup);
        modelAndView.addObject("listOfGroups", usergroupService.findAll());
        modelAndView.setViewName("groups-form");
        return modelAndView;
    }

    @PostMapping(value="/groups/create-group")
    public ModelAndView registerUser(@Valid @ModelAttribute Usergroup usergroup, BindingResult bindingResult, ModelMap modelMap) {
        ModelAndView modelAndView = new ModelAndView();
        // Check for the validation
        if (bindingResult.hasErrors()) {
            //modelAndView.addObject("successMessage", "Please correct the errors in form!");
            System.out.println("has errors: " + bindingResult.toString());
            modelMap.addAttribute("bindingResult", bindingResult);
            modelAndView.setViewName("groups-form");
            return modelAndView;
        }
        //save the user if no binding errors
        else {
            usergroupService.createUsergroup(usergroup);
        }
        modelAndView.setViewName("groups-list");
        return modelAndView;
    }
}
