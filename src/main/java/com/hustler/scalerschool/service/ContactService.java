package com.hustler.scalerschool.service;


import com.hustler.scalerschool.ScalerschoolApplication;
import com.hustler.scalerschool.constants.scalerschoolConstants;
import com.hustler.scalerschool.model.Contact;
import com.hustler.scalerschool.repository.ContactRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;

import java.time.LocalDateTime;
import java.util.List;

/*
@Slf4j, is a Lombok-provided annotation that will automatically generate an SLF4J
Logger static property in the class at compilation time.
* */
@Slf4j
@Service
//@RequestScope //scope of this bean.
//@SessionScope
//@ApplicationScope
public class ContactService {

    @Autowired
    private ContactRepository contactRepository; //autowired the variable called coontact repository so that we can call it and use the jdbc template in it so that from their we can persist the data into db.

    @Autowired
    private ScalerschoolApplication scalerschoolApplication;


    public ContactService(){//default constructor to initialised evrytime when the bean is created , so we logging the message.
        System.out.println("Contact Service Bean Initialized");
    }

    public boolean saveMessageDetails(Contact contact) {
//        Logger log = LoggerFactory.getLogger(ContactService.class); //using @Slf4j
        boolean isSaved = true;
//        log.info(contact.toString()); //jus printing the object into the console.

        // Need to persist the data into the DB table: //So this extra logic that we have inside our service layer. So these kind of logic, anything that is not related to validations and persistence or data layer,you can write inside your service layer.
        contact.setStatus(scalerschoolConstants.OPEN);
        contact.setCreatedBy(scalerschoolConstants.ANONYMOUS);  //contact page is set to anonymous,so anyone can access.
        contact.setCreatedAt(LocalDateTime.now());//current time

        int result=contactRepository.saveContactMsg(contact);//once am ready with all my details contact pojo class, I will call saveContactMessage() and I will pass the Contact pojo class.

        if(result>0){ //IT GIVES No of col inserted and if >0 means data is inserted.
            isSaved=true;
        }
        return isSaved; //returning to controller.
    }

    public List<Contact> findMsgsWithOpenStatus() {
        List<Contact> contactMsgs = contactRepository.findMsgsWithStatus(scalerschoolConstants.OPEN);
        return contactMsgs;
    }

    public boolean updateMsgStatus(int contactId, String updatedBy){
        boolean isUpdated =false;
        int result = contactRepository.updateMsgStatus(contactID,scalerschoolConstants.CLOSE,updatedBy);
        if(result>0){
            isUpdated=true;
        }
        return isUpdated;

    }
}
//NOTE: And you don't have to populate the contactId.
//The reason is the database is responsible to create that Id while executing.
