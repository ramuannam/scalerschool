package com.hustler.scalerschool.rowmappers;

import com.hustler.scalerschool.model.Contact;
import org.springframework.jdbc.core.RowMapper;


import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactRowMapper implements RowMapper<Contact> { //You always need to make sure you are implementing a RowMapper interface, and whenever you're implementing this interface, make sure you are passing the pojo class of your table, which is contact.

    @Override
    public Contact mapRow(ResultSet rs, int rowNum)throws SQLException{ //ResultSet can have 100 records or 1000 records.So what your spring JDBC framework will do is for each record, it is going to invoke this mapRow() method.
      //So inside this method we need to make sure we are writing all the business logic that we want.
        Contact contact = new Contact(); //creating an empty Contact Pojo class object, so based on fileds like contactId, name, mobile number, email, subject, message, createdBy, createdAt So based upon these values that I'm getting from the ResultSet, I'm setting into the Contact Pojo class.
        contact.setContactId(rs.getInt("CONTACT_ID"));
        contact.setName(rs.getString("NAME"));
        contact.setMobileNum(rs.getString("MOBILE_NUM"));
        contact.setEmail(rs.getString("EMAIL"));
        contact.setSubject(rs.getString("SUBJECT"));
        contact.setMessage(rs.getString("MESSAGE"));
        contact.setStatus(rs.getString("STATUS"));
        contact.setCreatedAt(rs.getTimestamp("CREATED_AT").toLocalDateTime());//
        contact.setCreatedBy(rs.getString("CREATED_BY"));

        if(null!=rs.getTimestamp("UPDATED_AT")){
            contact.setUpdatedAt(rs.getTimestamp("UPDATED_AT").toLocalDateTime());
        }
        contact.setUpdatedBy(rs.getString("UPDATED_BY"));
        return contact;
    }
}


//1.So inside this method we need to make sure we are writing all the business logic that we want.
//2.creating an empty Contact Pojo class object, so based on fileds like contactId, name, mobile number, email, subject, message, createdBy, createdAt So based upon these values that I'm getting from the ResultSet, I'm setting into the Contact Pojo class.
// 3.So whenever you are trying to fetch a value from the column, definitely you should use these methods like ResultSet.getInt("What is a column name").
//4.And if you see this line of createdAt() so whatever date that I get from the database whenever I use this getTimestamp() will be in the Timestamp data type, but my setter which is present inside my BaseEntity, will accept LocalDateTime data type.
//So that's why we need to do some small conversion here.
//So in order to convert the Timestamp to LocalDateTime.
//So I'm just invoking a method toLocalDateTime() once I have the Timestamp from the database.
// and since for updated at this value sometimes can be null.
//I'm checking a null check here, if it is not null only I'm trying to invoke this logic.
//Otherwise, whenever you call this to local date time method you will get a NullPointerException.

//So this is a simple logic that we have written inside our RowMapper class to tell spring JDBC framework
//how it has to map the ResultSet that is coming from the database to a particular pojo class.

//in comparison table of spring jdbc and developer,
//If you can recall our discussion about the responsibilities of Spring JDBC and developer, you can see
//it is the responsibility of the spring JDBC to set up a loop and iterate all the results that are available.Inside a ResultSet object.
//But the responsibility of the developer is to define the work what needs to be done for each iteration.
//So this work we are defining with the help of RowMapper.

//So like you can see what is a RowMapper.
//RowMapper is a simple interface which will allow you to provide a code defining how to map a particular row that is coming from the database to a instance of user defined pojo class.
//So basically your spring JDBC will iterate internally all the ResultSets available and add it to the collection class that you are going to return from the RowMapper class.