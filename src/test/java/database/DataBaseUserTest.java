package database;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import user.User;

public class DataBaseUserTest {
    private static User testUser;
    private static final String username = "username";
    private static final String alias = "alias";
    private static final String password = "password";
    private static byte[] bytes = username.getBytes();

    private static Database db;

    @BeforeEach
    void setUp() {
        db = new Database("jdbc:sqlite:C:/sqlite/db/semdatabase.db");

        testUser = new User(username);
        testUser.setAlias(alias);
        testUser.setPassword(password);
        testUser.setSalt(bytes);
    }

    @AfterEach
    void tearDown() {
        db.removeUserByUsername(username);
    }

    @Test
    void testInsertUser() throws UnsupportedEncodingException {
        db.insertUser(testUser);

        User userFromDB = checkIfUserInDatabase(username);

        Assertions.assertEquals(testUser.getUsername(), userFromDB.getUsername());
        Assertions.assertEquals(testUser.getSaltAsString(), userFromDB.getSaltAsString());
        Assertions.assertEquals(testUser.getPassword(), userFromDB.getPassword());
    }

    @Test
    void testRemoveUser() {
        db.insertUser(testUser);

        checkIfUserInDatabase(username);

        Assertions.assertTrue(db.removeUserByUsername(username));
    }

    User checkIfUserInDatabase(String username) {
        User userFromDB;
        try {
            userFromDB = db.getUserByUsername(username);
            return userFromDB;
        } catch (SQLException e) {
            e.printStackTrace();
            Assertions.fail();
        }
        return null;
    }
}
