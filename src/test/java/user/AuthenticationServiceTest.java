package user;

import database.Database;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AuthenticationServiceTest {

    private static User attemptedUser;
    private static User userFromDB;
    private static AuthenticationService authService = new AuthenticationService();
    private static byte[] expectedPwEncrypted;
    private static final byte[] saltBytes = "salt".getBytes();
    private static byte[] pw;

    private static final String username = "username";
    private static final String password = "password";

    @BeforeEach
    void setUp() throws InvalidKeySpecException,
            NoSuchAlgorithmException,
            UnsupportedEncodingException {

        attemptedUser = new User(username);
        pw = password.getBytes();
        attemptedUser.setPassword(pw);

        userFromDB = new User(username);
        expectedPwEncrypted = authService.encryptPassword(saltBytes, pw);
        userFromDB.setPassword(expectedPwEncrypted);
        userFromDB.setSalt(saltBytes);
    }

    @Test
    void authenticateUserRandomSalt() throws NoSuchAlgorithmException,
            InvalidKeySpecException,
            UnsupportedEncodingException {
        byte[] salt = authService.generateSalt();
        userFromDB.setSalt(salt);

        expectedPwEncrypted = authService.encryptPassword(salt, pw);
        userFromDB.setPassword(expectedPwEncrypted);

        Assertions.assertTrue(authService.authenticate(attemptedUser, userFromDB));
    }

    @Test
    void authenticateUserNotRetrievedFromDatabaseCorrect() {
        Assertions.assertTrue(authService.authenticate(attemptedUser, userFromDB));
    }

    @Test
    void authenticateUserNotRetrievedFromDatabaseWrongPassword() {
        User wrongpw = new User(username);
        wrongpw.setPassword("wrongPassword".getBytes());

        Assertions.assertFalse(authService.authenticate(wrongpw, userFromDB));
    }

    @Test
    void encryptPasswordTest() throws InvalidKeySpecException,
            NoSuchAlgorithmException,
            UnsupportedEncodingException {
        byte[] encrypted = authService.encryptPassword(saltBytes, pw);
        byte[] encryptedOtherSalt = authService.encryptPassword("othersalt".getBytes(), pw);
        byte[] encryptedOtherPassword = authService
                .encryptPassword(saltBytes, "otherpassword".getBytes());

        Assertions.assertArrayEquals(encrypted, expectedPwEncrypted);
        Assertions.assertFalse(Arrays.equals(encrypted, encryptedOtherSalt));
        Assertions.assertFalse(Arrays.equals(encrypted, encryptedOtherPassword));
    }

    @Test
    void insertUser() {
        Database db = new Database();

        db.insertUser(userFromDB);
    }

    @Test
    void generateSalt() throws NoSuchAlgorithmException {
        // salts should be random
        byte[] salt1 = authService.generateSalt();
        byte[] salt2 = authService.generateSalt();

        Assertions.assertFalse(Arrays.equals(salt1, salt2));
    }
}