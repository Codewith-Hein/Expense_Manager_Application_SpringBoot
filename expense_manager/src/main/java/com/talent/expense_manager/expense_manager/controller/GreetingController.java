package com.talent.expense_manager.expense_manager.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GreetingController {

    @GetMapping("/hello")
    public String hello(Model model) {
        model.addAttribute("name", "Alice");
        return "greeting";
    }

}