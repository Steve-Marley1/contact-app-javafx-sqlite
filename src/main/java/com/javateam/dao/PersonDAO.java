package com.javateam.dao;

import com.javateam.model.Person;
import com.javateam.util.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PersonDAO {

    public int addPerson(Person person) {

        String sql = """
                INSERT INTO person
                (firstname, lastname, nickname, phone_number, email_address, address, birth_date)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, person.getFirstname());
            ps.setString(2, person.getLastname());
            ps.setString(3, person.getNickname());
            ps.setString(4, person.getPhoneNumber());
            ps.setString(5, person.getEmailAddress());
            ps.setString(6, person.getAddress());
            ps.setString(7, person.getBirthDate().toString());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return -1;
    }

    public void updatePerson(Person person) {

        String sql = """
                UPDATE person SET
                firstname = ?, lastname = ?, nickname = ?,
                phone_number = ?, email_address = ?, address = ?, birth_date = ?
                WHERE idperson = ?
                """;

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, person.getFirstname());
            ps.setString(2, person.getLastname());
            ps.setString(3, person.getNickname());
            ps.setString(4, person.getPhoneNumber());
            ps.setString(5, person.getEmailAddress());
            ps.setString(6, person.getAddress());
            ps.setString(7, person.getBirthDate().toString());
            ps.setInt(8, person.getIdperson());

            ps.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void deletePerson(int id) {

        String sql = "DELETE FROM person WHERE idperson = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Person> getAllPersons() {

        List<Person> persons = new ArrayList<>();

        String sql = """
                SELECT idperson, firstname, lastname, nickname,
                       phone_number, email_address, address, birth_date
                FROM person
                """;

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                persons.add(new Person(
                        rs.getInt("idperson"),
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getString("nickname"),
                        rs.getString("phone_number"),
                        rs.getString("email_address"),
                        rs.getString("address"),
                        LocalDate.parse(rs.getString("birth_date"))
                ));
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return persons;
    }
}