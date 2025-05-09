package br.com.aceleramaker;

import br.com.aceleramaker.database.Database;
import br.com.aceleramaker.exception.DatabaseException;
import br.com.aceleramaker.queries.Queries;

import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Connection conn = null;
        Scanner sc = new Scanner(System.in);

        try (sc) {
            conn = Database.getConnection();
            Queries queries = new Queries(sc, conn);

            queries.updatePersonById();
        } catch (DatabaseException e) {
            System.out.println(e.getMessage());
        } finally {
            Database.closeStatement();
            Database.closeResultSet();
            Database.closeConnection();
        }
    }
}