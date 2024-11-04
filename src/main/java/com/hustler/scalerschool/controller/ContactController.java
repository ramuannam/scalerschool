package com.hustler.scalerschool.controller;

import com.hustler.scalerschool.model.Contact;
import com.hustler.scalerschool.service.ContactService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class ContactController { //created new controller for  contact as some few more details and info to handle like with db etc.. so created separate controller.

    private static Logger log= LoggerFactory.getLogger(ContactController.class); //to log/ print the info to the console, we need a log object.

   // DI
    private final ContactService contactService;
    @Autowired
     public ContactController (ContactService contactService){
         this.contactService=contactService;
     }

    @RequestMapping("/contact")
    public String displayContactPage( Model model){
        model.addAttribute("contact", new Contact());
         return "contact.html";
     }

//     @PostMapping("/saveMsg")  // OR//@RequestMapping(value="/saveMsg", method = POST)
//     public ModelAndView saveMessage(@RequestParam String name, @RequestParam String mobileNum, @RequestParam String email,
//                                     @RequestParam String subject, @RequestParam String message ){
//        // you can use either @RequstParam or @PathVariable . NOte: here the varible name should match with the names used in html.(then only the magic of sending the info from ui to Backend happens.)
//        // or you can use diff variable names in method but you need to use @PathVaribale("name") String username.
//        log.info("Name : " + name);
//        log.info("Mobile Number "+ mobileNum);
//        log.info("Email Address" + subject);
//        log.info("Subject "+ subject);
//        log.info("Message "+ message);
//        return new ModelAndView("redirect:/contact"); //so  here after the data submitted to backend, here we are creating new ModelAndView Object and  in this we jus redirecting to contact page without changing any attributes in this object, the user will redirect to contact page again with fresh page.
//     }

    //so instead of above approach, a lot of Parameters in the method that we are accepting from the front-end through @RequestParam, instead  we used a simple pojo class object.
    @RequestMapping(value = "/saveMsg", method = POST)  // OR SIMPLY @PostMapping("/saveMsg")
    public String saveMsg(@Valid @ModelAttribute("contact") Contact contact, Errors errors){ // by using POJO object all the attributes from client gets mapped to the object and assigned to the object.
        if(errors.hasErrors()){
            log.error("Contact form validation failed due to : "+ errors.toString());
            return "contact.html";//But with the return statement where we are saying contact.html, we are indicating to the spring MVC don't invoke the action again. Instead just display the contact.html along with the errors if there are any available to te frontEnd, fE needs to catch these errors as well.
        }
         contactService.saveMessageDetails(contact); // taking the given object to service layer to do some logic.
//         contactService.setCounter(contactService.getCounter()+1);//jus simply increasing the counter to +1 in contactService.
//         log.info("Number of times the contact for is submitted : "+contactService.getCounter());//logging it.
        return  "redirect:/contact"; //So whenever we are saying redirect, we are telling to spring MVC, go ahead and invoke the contact action again from the starting.
    }

    //mvc way for display messgaes at admin dashboard.
    @RequestMapping("/displayMessages")
    public ModelAndView displayMessages(Model model){
        List<Contact> contactMsgs=contactService.findMsgsWithOpenStatus();
        ModelAndView modelAndView=new ModelAndView("messages.html"); //telling model and view to display messages.html page, when user invokes ths displaymessages method/ endpoint.
        modelAndView.addObject("contactMsgs",contactMsgs); //adding the list of conatct msgs to model and view object.(to ineract with front end.)
        return modelAndView;

    }

    @RequestMapping("/closeMsg",method=GET) //accepting a method of GET.
    public  String closeMsg(@RequestParam int id, Authentication authentication){ //So here I'm catching my query param with the help of request param annotation and the param name is id.I'm also asking my spring security for the authentication object.
        contactService.updateMsgStatus(id, authentication.getName()); //So now we have to go to the ContactService class and introduce this new method updateMessageStatus().
        return "redirect:/displayMessages";
    }
}