package com.javateam.util;

import java.sql.Connection;
import java.sql.Statement;

public final class DatabaseInitializer {

    private DatabaseInitializer() {}

    public static void initialize() {
        String sql = """
            CREATE TABLE IF NOT EXISTS person (
                idperson INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
                lastname VARCHAR(45) NOT NULL,
                firstname VARCHAR(45) NOT NULL,
                nickname VARCHAR(45) NOT NULL,
                phone_number VARCHAR(15) NULL,
                address VARCHAR(200) NULL,
                email_address VARCHAR(150) NULL,
                birth_date TEXT NULL
            );
            """;

        try (Connection conn = DatabaseConnection.connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (Exception e) {
            throw new RuntimeException("Database initialization failed", e);
        }
    }

    public static void clearTable() {
        try (Connection conn = DatabaseConnection.connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM person;");
            stmt.execute("DELETE FROM sqlite_sequence WHERE name='person';"); // reset auto-increment
        } catch (Exception e) {
            throw new RuntimeException("Clear table failed", e);
        }
    }
}