package com.hustler.scalerschool.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.security.PublicKey;
/*
@ControllerAdvice is a specialization of the @Component annotation which allows to handle
exceptions across the whole application in one global handling component. It can be viewed
as an interceptor of exceptions thrown by methods annotated with @RequestMapping and similar.
* */

@Slf4j
@ControllerAdvice
public class GlobalExceptionController {
    /*
 @ExceptionHandler will register the given method for a given
 exception type, so that ControllerAdvice can invoke this method
 logic if a given exception type is thrown inside the web application.
 * */
    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(Exception exception){
        ModelAndView errorPage=new ModelAndView(); //Created a new object called ModelAndView So this ModelAndView object will let you allow to define both the view name along with the model data that you want to send to the UI application.
        errorPage.setViewName("error");//And inside that I'm setting what is a view that I want to display to the end user. The view name that I want to display to the end user is error and inside the same ModelAndView object.
        errorPage.addObject("errormsg",exception.getMessage());//I'm sending an information to the UI page with an attribute name error message and inside this attribute error message I'm sending, what is the reason that this particular exception being thrown inside my web application.
        return errorPage;
    }

}
