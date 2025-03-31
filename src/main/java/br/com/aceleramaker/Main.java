package br.com.aceleramaker;

import br.com.aceleramaker.database.Database;
import br.com.aceleramaker.exception.DatabaseException;

import java.sql.*;

public class Main {
    public static void main(String[] args) {
        Connection conn = null;

        try {
            conn = Database.getConnection();

            String query = """
                           CREATE TABLE IF NOT EXISTS people(
                                id INT AUTO_INCREMENT PRIMARY KEY,
                                name VARCHAR(80) NOT NULL
                           )
                           """;

            Database.createStatement(query);
        } catch (DatabaseException e) {
            System.out.println(e.getMessage());
        } finally {
            Database.closeConnection();
            Database.closeStatement();
        }
    }
}