package br.com.aceleramaker;

import br.com.aceleramaker.database.Database;
import br.com.aceleramaker.exception.DatabaseException;

import java.sql.*;

public class Main {
    public static void main(String[] args) {
        Connection conn = null;

        try {
            conn = Database.getConnection();
        } catch (DatabaseException e) {
            System.out.println(e.getMessage());
        } finally {
            Database.closeConnection();
        }
    }
}