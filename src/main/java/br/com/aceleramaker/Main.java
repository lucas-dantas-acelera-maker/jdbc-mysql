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
            Queries queries = new Queries(sc);
            String st;
            conn = Database.getConnection();

            st = queries.addNewPerson();

            Database.createStatement(st);
        } catch (DatabaseException e) {
            System.out.println(e.getMessage());
        } finally {
            Database.closeConnection();
            Database.closeStatement();
        }
    }
}