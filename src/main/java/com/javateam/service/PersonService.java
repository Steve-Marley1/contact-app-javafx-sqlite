package com.javateam.service;

import com.javateam.dao.PersonDAO;
import com.javateam.model.Person;

import java.util.List;

public class PersonService {

    private final PersonDAO dao;

    public PersonService() {
        this.dao = new PersonDAO();
    }

    public List<Person> getAllPersons() {
        return dao.getAllPersons();
    }

    public void addPerson(Person person) {

        // Validation minimale professionnelle
        if (person.getFirstname() == null || person.getFirstname().isBlank()) {
            throw new IllegalArgumentException("Firstname cannot be empty");
        }

        if (person.getLastname() == null || person.getLastname().isBlank()) {
            throw new IllegalArgumentException("Lastname cannot be empty");
        }

        dao.addPerson(person);
    }

    public void deletePerson(int id) {
        dao.deletePerson(id);
    }

    public void updatePerson(Person person) {

        if (person.getIdperson() <= 0) {
            throw new IllegalArgumentException("Invalid ID");
        }

        dao.updatePerson(person);
    }
}