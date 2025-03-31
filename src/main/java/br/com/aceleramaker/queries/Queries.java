package br.com.aceleramaker.queries;

import br.com.aceleramaker.exception.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Queries {
    private final Scanner sc;

    public Queries(Scanner sc) {
        this.sc = sc;
    }

    public String createTable() {
        return """
                CREATE TABLE IF NOT EXISTS people(
                     id INT AUTO_INCREMENT PRIMARY KEY,
                     name VARCHAR(80) NOT NULL
                )
                """;
    }

    public void addNewPerson(Connection conn) {
        try {
            System.out.print("Name: ");
            String name = sc.nextLine();

            String query =
                    """
                    INSERT INTO people (name)
                    VALUES (?)
                    """;

            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, name);
            st.execute();
            System.out.println("Person successfully added!");
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }
}
