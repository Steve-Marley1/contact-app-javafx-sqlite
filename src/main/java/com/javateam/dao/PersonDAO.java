package com.javateam.dao;

import com.javateam.model.Person;
import com.javateam.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonDAO {

    // =========================
    // READ - Get all persons
    // =========================
    public List<Person> getAllPersons() {

        List<Person> persons = new ArrayList<>();
        String sql = "SELECT * FROM person";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Person p = new Person(
                        rs.getInt("idperson"),
                        rs.getString("lastname"),
                        rs.getString("firstname"),
                        rs.getString("nickname"),
                        rs.getString("phone_number"),
                        rs.getString("address"),
                        rs.getString("email_address"),
                        rs.getString("birth_date")
                );

                persons.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return persons;
    }

    // =========================
    // CREATE - Add person
    // =========================
    public void addPerson(Person p) {

        String sql = """
                INSERT INTO person
                (lastname, firstname, nickname, phone_number, address, email_address, birth_date)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, p.getLastname());
            stmt.setString(2, p.getFirstname());
            stmt.setString(3, p.getNickname());
            stmt.setString(4, p.getPhoneNumber());
            stmt.setString(5, p.getAddress());
            stmt.setString(6, p.getEmailAddress());
            stmt.setString(7, p.getBirthDate());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // =========================
    // DELETE
    // =========================
    public void deletePerson(int id) {

        String sql = "DELETE FROM person WHERE idperson = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // =========================
    // UPDATE
    // =========================
    public void updatePerson(Person p) {

        String sql = """
                UPDATE person SET
                lastname = ?,
                firstname = ?,
                nickname = ?,
                phone_number = ?,
                address = ?,
                email_address = ?,
                birth_date = ?
                WHERE idperson = ?
                """;

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, p.getLastname());
            stmt.setString(2, p.getFirstname());
            stmt.setString(3, p.getNickname());
            stmt.setString(4, p.getPhoneNumber());
            stmt.setString(5, p.getAddress());
            stmt.setString(6, p.getEmailAddress());
            stmt.setString(7, p.getBirthDate());
            stmt.setInt(8, p.getIdperson());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}