package com.hustler.scalerschool.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller


public class LoginController {
    @RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST}) //The path that it is going to support is /login and the HTTP methods that it is going to support is both GET and POST.
    public  String displayLoginPage(@RequestParam (value = "error", required = false) String error,
                                    @RequestParam(value = "logout", required =false) String logout, Model model){
        String errorMessage=null;
        if(error!=null){ //it means there in login there is an error, and error==true.
             errorMessage="Username or password is incorrect";
        }
        if(logout!=null){// it means it logout successfully and logout==true.
            errorMessage="You have been successfully logged out ! ";
        }

        //so if it is failed to login then only it comes here and sends mesage from here to model object to view and redirects to login page
        //and if it succesfully logout then also it comes here and redirets to login page.

        //so if login is succcessful spring security in config  file only it redirects to dashbord.

        model.addAttribute("errorMessage", errorMessage);
        return "login.html";

    }
}
