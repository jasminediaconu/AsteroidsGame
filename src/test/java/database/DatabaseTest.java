package database;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import user.User;

class DatabaseTest {

    private static Database db;

    @BeforeEach
    void setUp() {
        db = new Database("jdbc:sqlite:C:/sqlite/db/semdatabase.db");
    }

    @Test
    void getUrl() {
        assertEquals("jdbc:sqlite:C:/sqlite/db/semdatabase.db", db.getUrl());
    }

    @Test
    void setUrl() {
        assertEquals("jdbc:sqlite:C:/sqlite/db/semdatabase.db", db.getUrl());

        db.setUrl("testurl");

        assertEquals("testurl", db.getUrl());
    }

    @Test
    void testUsersfromFile() throws FileNotFoundException {
        String filepath = "src/main/resources/database/standard_data/users.txt";
        ArrayList<User> users = Database.makeUsersFromFile(filepath);

        Scanner sc = new Scanner(new File(filepath)).useDelimiter(",|\\n");
        // first int in the text file is the number of users specified in the file
        int length = sc.nextInt();

        assertEquals(length, users.size());
    }
}