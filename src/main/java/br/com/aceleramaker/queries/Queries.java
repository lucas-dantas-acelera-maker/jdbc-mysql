package br.com.aceleramaker.queries;

import br.com.aceleramaker.exception.DatabaseException;
import br.com.aceleramaker.model.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Queries {
    private final Scanner sc;
    Connection conn;

    public Queries(Scanner sc, Connection conn) {
        this.sc = sc;
        this.conn = conn;
    }

    public String createTable() {
        return """
                CREATE TABLE IF NOT EXISTS people(
                     id INT AUTO_INCREMENT PRIMARY KEY,
                     name VARCHAR(80) NOT NULL
                )
                """;
    }

    public void addNewPerson() {
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

    public void getPeople() {
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

    public void getPersonByName() {
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

    public void updatePersonById() {
        try {
            System.out.println("---------UPDATE NAME BY ID---------");
            System.out.println("List of people: ");
            getPeople();

            System.out.print("Select person ID to be updated: ");
            int inId = sc.nextInt();
            sc.nextLine();

            System.out.print("New name: ");
            String inName = sc.nextLine();

            String sql =
                    """
                    UPDATE people
                    SET name = ?
                    WHERE id = ?
                    """;
            PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setString(1, inName);
            st.setInt(2, inId);

            int rowsAffected = st.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Updated List: ");
                getPeople();
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }

    }
}
