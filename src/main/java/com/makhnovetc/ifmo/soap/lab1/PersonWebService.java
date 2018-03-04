package com.makhnovetc.ifmo.soap.lab1;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.sql.DataSource;
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
    public List<Person> findPeople(String id, String name, String middlename, String surname, String dob, String sex) {
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
}