package br.com.aceleramaker;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        try {
            Properties props = new Properties();

            props.load(new FileInputStream("src/main/resources/db.properties"));
            String dbUrl = props.getProperty("db.url");
            String dbUser = props.getProperty("db.user");
            String dbPassword = props.getProperty("db.password");

            Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            System.out.println("Database connected!");

            Statement st = conn.createStatement();
            st.execute("CREATE DATABASE IF NOT EXISTS jdbc_course");

            conn.close();
            System.out.println("Connection closed");

            } catch (SQLException | IOException e) {

            System.out.println("Error: " + e.getMessage());
        }

    }
}