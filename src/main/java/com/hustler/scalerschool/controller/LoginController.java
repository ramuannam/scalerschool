package com.hustler.scalerschool.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
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

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication(); //getting existing authentication object.
        if(auth!=null){
            //use SecurityContextLogoutHandler to log the user out.
            SecurityContextLogoutHandler logoutHandler=new SecurityContextLogoutHandler();
            logoutHandler.logout(request,response,auth);//I'm just invoking the logout() method present inside the SecurityContext folder. And to this logout method I'm going to pass this HttpServletRequest() object and the HttpServletResponse() along with the Authentication object, WHEN Authentication object is not null.
            //in logout() if you see its implementation, it just invalidates the session.
        }
        return "redirect:/login?logout=true";  //at last I'm going to redirect the end user /login, obviously the above method(displayLoginPage) will get invoked. And based upon this logout parameter, the end user will see this message, which is you have been successfully logged out.
    }
}


//There are two approaches to get the current session/existing authentication details.
//1. The very first one is we can directly inject this authentication object as a method parameter so that
//my spring security framework will provide all the current Authentication details during the runtime.

//2. The second approach that we can follow is to fetch the current Authentication details with the help
//of SecurityContextHolder.getContext().getAuthentication(). Because whenever the successful
//authentication happens during the login operation.
//Those Authentication details will be saved inside the SecurityContext folder.