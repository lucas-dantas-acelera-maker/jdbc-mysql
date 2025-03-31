package br.com.aceleramaker.queries;

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

    public String addNewPerson() {
        System.out.print("Name: ");
        String name = sc.nextLine();
        return String.format(
                """
                INSERT INTO people (name)
                VALUES ('%s')
                """, name);
    }
}
