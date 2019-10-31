package com.ocere.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FileFEController {

    @GetMapping("/filetest")
    public String fileTest() {
        return "filetest";
    }
}
