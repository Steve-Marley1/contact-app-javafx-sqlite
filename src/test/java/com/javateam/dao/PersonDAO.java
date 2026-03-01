package com.javateam.dao;

import com.javateam.model.Person;
import com.javateam.util.DatabaseInitializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PersonDAOTest {

    private PersonDAO dao;

    @BeforeEach
    void setup() {
        DatabaseInitializer.initialize();
        DatabaseInitializer.clearTable();
        dao = new PersonDAO();
    }

    @Test
    void addPerson_shouldInsertAndReturnId() {
        Person p = new Person(
                "Test",
                "User",
                "TU",
                "0612345678",
                "test@mail.com",
                "10 rue test",
                LocalDate.of(2000, 1, 1)
        );

        int id = dao.addPerson(p);
        assertTrue(id > 0);

        List<Person> persons = dao.getAllPersons();
        assertEquals(1, persons.size());
        assertEquals("test@mail.com", persons.get(0).getEmailAddress());
        assertEquals("10 rue test", persons.get(0).getAddress());
    }
}