package com.makhnovetc.ifmo.soap.lab1;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.sql.DataSource;

import com.makhnovetc.ifmo.soap.lab1.Exception.ExceptionBean;
import com.makhnovetc.ifmo.soap.lab1.Exception.NullFieldException;
import com.makhnovetc.ifmo.soap.lab1.Person;

@WebService(serviceName = "PersonService")
public class PersonWebService {
    private void PersonWebService() {};
    private List<Person> persons;

    @Resource(lookup = "jdbc/__default")
    private DataSource dataSource;

    @WebMethod(operationName = "getPersons")
    public List<Person> getPersons() {
        PostgreSQLDAO dao = getConnection();
        persons = dao.getPersons();
        return persons;
    }
    @WebMethod(operationName = "getPersonsQuery")
    public List<Person> getPersons(String query)  {
        PostgreSQLDAO dao = getConnection();
        persons = dao.getPerson(query) ;
        return persons;
    }
    @WebMethod(operationName = "findPeople")
    public List<Person> findPeople(String id, String name, String middlename, String surname, String dob, String sex) throws NullFieldException {
        checkField(name);
        checkField(middlename);
        checkField(surname);
        checkField(dob);
        checkField(sex);
        PostgreSQLDAO dao = getConnection();
        persons = dao.findPeople(id, name, middlename, surname, dob, sex) ;
        return persons;
    }

    private PostgreSQLDAO getConnection(){
        PostgreSQLDAO dao = null;
        try{
            dao = new PostgreSQLDAO(dataSource.getConnection());
        } catch (SQLException ex){
            Logger.getLogger(PersonWebService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dao;
    }

    private void checkField(String field) throws NullFieldException {
        if (field == null || field.isEmpty()) {
            ExceptionBean fault = new ExceptionBean();
            throw new NullFieldException("Error entering " + field.toString() + " : " + field.toString() + " is null or empty", fault);
        }
    }

    public boolean checkDate(String dateToValidate) {
        String dateFormat = "yyyy-MM-dd";
        if (dateToValidate == null) {
            return false;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        sdf.setLenient(false);
        try {

            Date date = sdf.parse(dateToValidate);
            System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}