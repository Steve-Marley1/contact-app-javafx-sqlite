package com.javateam.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DatabaseInitializer {

    private static final String URL = "jdbc:sqlite:database.db";

    public static void initialize() {

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {

            String sql = """
                    CREATE TABLE IF NOT EXISTS person (
                        idperson INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
                        lastname VARCHAR(45) NOT NULL,
                        firstname VARCHAR(45) NOT NULL,
                        nickname VARCHAR(45) NOT NULL,
                        phone_number VARCHAR(15),
                        address VARCHAR(200),
                        email_address VARCHAR(150),
                        birth_date DATE
                    );
                    """;

            stmt.execute(sql);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
