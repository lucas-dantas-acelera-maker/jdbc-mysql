package br.com.aceleramaker.queries;

import br.com.aceleramaker.exception.DatabaseException;
import br.com.aceleramaker.model.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
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

    public void getPeople(Connection conn) {
        try {
            String sql = "SELECT * FROM people";

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            List<Person> people = new ArrayList<>();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                people.add(new Person(id, name));
            }

            people.forEach(person -> System.out.printf("%d - %s%n", person.getId(), person.getName()));

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public void getPersonByName(Connection conn) {
        try {
            System.out.print("Word to search for: ");
            String userIn = sc.nextLine();

            String sql = "SELECT * FROM people WHERE name LIKE ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, "%" + userIn + "%");
            ResultSet rs = st.executeQuery();

            List<Person> people = new ArrayList<>();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                people.add(new Person(id, name));
            }

            System.out.println("Users found: ");
            people.forEach(person -> System.out.printf("%d - %s%n", person.getId(), person.getName()));

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }
}
