package ru.psu.fvt.moipevm.taskmanager.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/")
    public String getHome(){
        return "home";
    }

    @GetMapping("/pageNotFound")
    public String getNotFoundPage(){
        return "pageNotFound";
    }
}
