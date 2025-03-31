package br.com.aceleramaker.database;

import br.com.aceleramaker.exception.DatabaseException;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class Database {
    private static Connection conn = null;

    public static Connection getConnection() {
        if (conn == null) {
            try {
                Properties props = loadProperties();
                String dbUrl = props.getProperty("db.url");
                String dbUser = props.getProperty("db.user");
                String dbPassword = props.getProperty("db.password");
                conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);

            } catch (SQLException e) {
                throw new DatabaseException(e.getMessage());
            }
        }
        System.out.println("Database connected!");
        return conn;
    }

    public static void closeConnection() {
        try {
            if (conn != null) {
                conn.close();
                System.out.println("Connection closed");
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());

        }
    }

    private static Properties loadProperties() {
        try(FileInputStream fs = new FileInputStream("src/main/resources/db.properties")) {
            Properties props = new Properties();
            props.load(fs);
            return props;
        } catch (IOException e) {
            throw new DatabaseException(e.getMessage());
        }
    }
}
