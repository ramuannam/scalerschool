package com.hustler.scalerschool.service;


import com.hustler.scalerschool.model.Contact;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;

/*
@Slf4j, is a Lombok-provided annotation that will automatically generate an SLF4J
Logger static property in the class at compilation time.
* */
@Slf4j
@Service
//@RequestScope //scope of this bean.
//@SessionScope
@ApplicationScope
public class ContactService {
    private  int counter=0;

    public ContactService(){//default constructor to initialised evrytime when the bean is created , so we logging the message.
        System.out.println("Contact Service Bean Initialized");
    }

    public boolean saveMessageDetails(Contact contact) {
//        Logger log = LoggerFactory.getLogger(ContactService.class); //using @Slf4j
        boolean isSaved = true;
        //TODO: Need to persist the data into the DB table
        log.info(contact.toString()); //jus printing the object into the console.
        return isSaved;
    }

    public int getCounter(){
        return counter;
    }

    public void setCounter(int counter){
        this.counter=counter;
    }
}

