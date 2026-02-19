package com.javateam.util;

import java.sql.Connection;
import java.sql.Statement;

public class DatabaseInitializer {

    public static void initialize() {

        String sql = """
            CREATE TABLE IF NOT EXISTS person (
                idperson INTEGER PRIMARY KEY AUTOINCREMENT,
                lastname TEXT NOT NULL,
                firstname TEXT NOT NULL,
                nickname TEXT NOT NULL,
                phone_number TEXT,
                address TEXT,
                email_address TEXT,
                birth_date TEXT
            );
        """;

        try (Connection conn = DatabaseConnection.connect();
             Statement stmt = conn.createStatement()) {

            stmt.execute(sql);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}