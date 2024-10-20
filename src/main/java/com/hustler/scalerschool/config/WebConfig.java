package com.hustler.scalerschool.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {//here we are registering the view controller that creates a direct mapping between the url, and the view name using the ViewControllerRegistry.
    //this way, there's is no need for any controller between two.

    @Override  //overriding the addViewControllers method. i,.e. this method adds a particular view to the url path, without the need of writing a separate controller bean/class.
    public void addViewControllers(ViewControllerRegistry registry){
         registry.addViewController("/courses").setViewName("courses");//in setview need to add same name as the html page, where the mapping happens b/t view and path.
        registry.addViewController("/about").setViewName("about");
        //can be any as these act like static pages, without any dynamic data.
     }
}


//this is way of writing code directky without controller is good when you don't have lot of business logic in the controller.
// that is jus static pages, with the use of webMvcConfigurer interface.
