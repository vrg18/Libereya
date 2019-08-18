package edu.vrg18.libereya.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping({"/", "/welcome"})
    public String welcomePage(Model model) {
        model.addAttribute("title", "Libereya");
        model.addAttribute("message", "Добро пожаловать в библиотеку Либерея!");
        return "welcomePage";
    }
}
