package com.springsecurity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class TemplateController {

    @GetMapping("login")
    public String getLogin() {
        return "login";  //this should be same as the name of your login page in templates folder
    }

    @GetMapping("courses")
    public String getCourses() {
        return "courses";
    }
}
