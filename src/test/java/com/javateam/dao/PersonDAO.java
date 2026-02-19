package com.javateam.dao;

import com.javateam.model.Person;
import com.javateam.util.DatabaseInitializer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PersonDAOTest {

    private static PersonDAO dao;

    @BeforeAll
    static void setup() {
        DatabaseInitializer.initialize();
        dao = new PersonDAO();
    }

    @Test
    void testAddPerson() {

        Person person = new Person(
                "JUnitLast",
                "JUnitFirst",
                "Nick",
                "123",
                "",
                "",
                LocalDate.now()
        );

        dao.addPerson(person);

        List<Person> persons = dao.getAllPersons();

        boolean exists = persons.stream()
                .anyMatch(p -> p.getLastname().equals("JUnitLast"));

        assertTrue(exists);
    }

    @Test
    void testDeletePerson() {

        Person person = new Person(
                "DeleteTest",
                "First",
                "Nick",
                "000",
                "",
                "",
                LocalDate.now()
        );

        dao.addPerson(person);

        List<Person> persons = dao.getAllPersons();

        Person inserted = persons.stream()
                .filter(p -> p.getLastname().equals("DeleteTest"))
                .findFirst()
                .orElse(null);

        assertNotNull(inserted);

        dao.deletePerson(inserted.getIdperson());

        List<Person> afterDelete = dao.getAllPersons();

        boolean stillExists = afterDelete.stream()
                .anyMatch(p -> p.getLastname().equals("DeleteTest"));

        assertFalse(stillExists);
    }
}