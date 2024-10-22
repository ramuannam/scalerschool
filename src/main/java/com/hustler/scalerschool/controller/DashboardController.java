package com.hustler.scalerschool.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class DashboardController {

    @RequestMapping("/dashboard")
    public String displayDashboard(Model model, Authentication authentication){ //sending who login and what are his roles to the dashboard page using model object.
        model.addAttribute("username",authentication.getName());
        model.addAttribute("roles",authentication.getAuthorities().toString());
//        throw new RuntimeException("It's been really a hectic day!!");
        return "dashboard.html";
    }
}
