package com.javateam.service;

import com.javateam.dao.PersonDAO;
import com.javateam.model.Person;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PersonServiceTest {

    static class FakePersonDAO extends PersonDAO {
        @Override public int addPerson(Person person) { return 1; }
        @Override public void updatePerson(Person person) {}
        @Override public void deletePerson(int id) {}
        @Override public List<Person> getAllPersons() { return Collections.emptyList(); }
    }

    @Test
    void addPerson_shouldThrow_whenEmailInvalid() {
        PersonService service = new PersonService(new FakePersonDAO());

        Person p = new Person(
                "Hidaya",
                "Elmamo",
                "Hidou",
                "0612345678",
                "hgsd",                
                "10 bis rue",
                LocalDate.of(2004, 3, 20)
        );

        assertThrows(IllegalArgumentException.class, () -> service.addPerson(p));
    }

    @Test
    void addPerson_shouldPass_whenEmailValid() {
        PersonService service = new PersonService(new FakePersonDAO());

        Person p = new Person(
                "Hidaya",
                "Elmamo",
                "Hidou",
                "0612345678",
                "hidaya@gmail.com",     
                "10 bis rue",
                LocalDate.of(2004, 3, 20)
        );

        assertDoesNotThrow(() -> service.addPerson(p));
    }
}