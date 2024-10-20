package com.hustler.scalerschool.controller;

import com.hustler.scalerschool.model.Holiday;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
public class HolidaysController {

    @GetMapping("/holidays")
    public String displayHolidays(@RequestParam(required=false)boolean festival,
                                  @RequestParam(required=false) boolean federal, Model model){ //So here with the help of RequestParam annotation, we can accept the new query params that my frontend application is sending to my backend controller\
        //directly the displayHolidays method catches the query params when the /holidays got trigered respectively inthis method, (this is internally done by Spring MVC.)

        model.addAttribute("festival",festival); //  saving these inputs that I'm receiving from the getURL inside the model object, like I'm calling the addAttribute() , and I'm saving the values,Like if I receive true as a value, I will save that true value against the same attribute name. So here the attribute name,
        model.addAttribute("federal", federal);
        List<Holiday> holidays= Arrays.asList(
                new Holiday(" Jan 1 ","New Year's Day", Holiday.Type.FESTIVAL),
                new Holiday(" Oct 31 ","Halloween", Holiday.Type.FESTIVAL),
                new Holiday(" Nov 24 ","Thanksgiving Day", Holiday.Type.FESTIVAL),
                new Holiday(" Dec 25 ","Christmas", Holiday.Type.FESTIVAL),
                new Holiday(" Jan 17 ","Martin Luther King Jr. Day", Holiday.Type.FEDERAL),
                new Holiday(" July 4 ","Independence Day", Holiday.Type.FEDERAL),
                new Holiday(" Sep 5 ","Labor Day", Holiday.Type.FEDERAL),
                new Holiday(" Nov 11 ","Veterans Day", Holiday.Type.FEDERAL)

        );

        //sending data to UI, by separating the holidays based on type of holiday.
        Holiday.Type[] types= Holiday.Type.values(); // based on type of holiday.
        for(Holiday.Type type : types){ // lambda code.
            model.addAttribute(type.toString(),
                    (holidays.stream().filter(holiday -> holiday.getType().equals(type)).
                            collect(Collectors.toList())));
        }
        return "holidays.html";  // jus returning the view and we don't need to send this model object as thymleaf do this

    }
}
