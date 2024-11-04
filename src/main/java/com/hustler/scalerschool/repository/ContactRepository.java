package com.hustler.scalerschool.repository;

import com.hustler.scalerschool.model.Contact;
import com.hustler.scalerschool.rowmappers.ContactRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/*
@Repository stereotype annotation is used to add a bean of this class
type to the Spring context and indicate that given Bean is used to perform
DB related operations and
* */


@Repository
public class ContactRepository {
    private final JdbcTemplate jdbcTemplate;

    public ContactRepository(JdbcTemplate jdbcTemplate) { //directly injecting the jdbc template object which is created by spring jdbc, as per the properties provided in application.properties.
        this.jdbcTemplate = jdbcTemplate;
    }

    public int saveContactMsg(Contact contact) {
        String sql = "INSERT INTO CONTACT_MSG(NAME, MOBILE_NUM, EMAIL, SUBJECT, MESSAGE, STATUS, CREATED_AT, CREATED_BY) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?)"; //You can see this is an insert statement where I'm saying insert into contact message table and these are the column names and I have eight quotation marks indicating that I need to pass the eight parameters to my JdbcTemplate indicating what are the values that it needs to consider while executing that query against a database.
        return jdbcTemplate.update(sql,    //we have update method in jdbc template obj.// You know, for any insert, update, delete, we can call the update(), first parameter I'm passing, which is SQL statement. And next you can see I'm passing all the values for this question mark placeholders that I have inside my SQL statement.
                contact.getName(),
                contact.getMobileNum(),
                contact.getEmail(),
                contact.getSubject(),
                contact.getMessage(),
                contact.getStatus(),
                contact.getCreatedAt(),
                contact.getCreatedBy());
        //you can expect in this scenario only one row will get inserted. That's why I'll get an int as an return value for this method.
    }

    public List<Contact> findMsgsWithStatus(String status){
        String sql="SELECT * FROM CONATCT_MSG WHERE STATUS = ?";
        return jdbcTemplate.query(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setString(1,status);
            }
        },new ContactRowMapper()); //So this is the third parameter that I need to pass to this query() method. So based upon this ContactRowMapper, my spring JDBC will return the list of contact records.
    }

}
//you can see I'm not handling any exceptions because my Spring dbc template internally will catch
//that SQLException and it will convert that into an unchecked exception.
//So since that will be an unchecked exception, it is not mandatory for me as a developer to catch that.
//So that will also reduce some code inside my method.



//in findMsgsWthStatus():\
//So here I want to use the query() method of the jdbc object.\
//So the first parameter that we need to pass is what is the SQL query post that we need to pass the second parameter, which is an implementation of PreparedStatement setup.\
//So if you see this is an PreparedStatement, I want to populate the what is a parameter value against this question mark.\
//So that's where you can see I'm just creating the new object of PreparedStatement setter and inside that we have a method which we need to override.\
//The method name is setValues().\
//So against this PreparedStatement parameter that I have for setValues(), I'm setting the what is the parameter value that my spring JDBC needs to consider whenever it is trying to execute this query.\
//Since we have only one question mark here we are using setString(1,what is the parameter value here) The status is the parameter value that we need to pass.\

//So if we have multiple question marks, we need to set multiple values against the same object by invoking setString() or setInt() based upon the data type.\
//But we need to make sure this value we are incrementing like 1, 2, 3 because this value indicates the placeholder of your question mark inside the query.