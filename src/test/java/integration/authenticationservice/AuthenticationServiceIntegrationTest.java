package integration.authenticationservice;

import database.Database;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import user.AuthenticationService;
import user.User;

public class AuthenticationServiceIntegrationTest {

    private static User testUser;
    private static AuthenticationService authService = new AuthenticationService();
    // TODO change this to something more robus
    private static final String username = "UserNotInDatabase";
    private static Database db = new Database();
    private static byte[] pwEncrypted;
    private static final String password = "password";

    @BeforeAll
    static void init() {
        Database.createDatabase();
    }

    @BeforeEach
    void setUp() throws NoSuchAlgorithmException,
            UnsupportedEncodingException,
            InvalidKeySpecException {
        testUser = new User(username);

        byte[] salt = "salt".getBytes();
        pwEncrypted = authService.encryptPassword(salt, password.getBytes());

        testUser.setSalt(salt);
        testUser.setPassword(pwEncrypted);
    }

    @Test
    void authenticateNull() {
        testUser.setUsername(null);

        Assertions.assertFalse(authService.authenticate(testUser));
    }

    @Test
    void authenticateValidUser() {
        db.insertUser(testUser);

        testUser.setPassword(password.getBytes());

        Assertions.assertTrue(authService.authenticate(testUser));

        db.removeUserByUsername(testUser.getUsername());
    }
}
