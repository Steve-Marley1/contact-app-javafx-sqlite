package com.javateam.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DatabaseConnection {

    private static final String URL = "jdbc:sqlite:database.db";

    private DatabaseConnection() {}

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}